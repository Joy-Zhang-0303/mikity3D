package org.mklab.mikity.view.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;
import org.mklab.mikity.view.gui.editor.TransformationEditor;

/**
 * シーングラフを表すクラスです。
 */
public class SceneGraphTree {
  /** 親コンポーネント。 */
  Composite parentComposite;
  /** シーングラフツリー。 */
  Tree tree;
  
  /** モデラー。 */
  JoglModeler modeler;

  /** シーンモデル。 */
  SceneModel model;
  /** ルートグループ。 */
  GroupModel rootGroup;

  /** 選択されているオブジェクト。 */
  Object selectedObject = null;
  /** 選択されているグループ。 */
  GroupModel selectedGroup = null;

  /** 記憶されたオブジェクト。 */
  ObjectModel bufferedObject = null;
  /** 記憶されたグループ。 */
  GroupModel bufferedGroup = null;
  
  /** オブジェクトを修正中ならばtrue */
  boolean isModifyingObject = false;

  /**
   * 新しく生成された<code>SceneGraphTree</code>オブジェクトを初期化します。
   * 
   * @param parent 親コンポーネント
   * @param modeler モデラー
   * @param model モデル
   */
  public SceneGraphTree(final Composite parent, final JoglModeler modeler, final SceneModel model) {
    this.parentComposite = parent;
    this.model = model;
    this.modeler = modeler;
    this.rootGroup = new GroupModel("scene"); //$NON-NLS-1$
    this.rootGroup.setGroups(model.getGroups());

    this.selectedGroup = this.rootGroup;
    this.selectedObject= this.rootGroup;

    createTree();
  }

  /**
   * ツリーを生成します。
   * 
   * @param parent 親
   */
  private void createTree() {
    this.parentComposite.setLayout(new GridLayout(1, true));
    this.tree = new Tree(this.parentComposite, SWT.NONE);
    final GridData data = new GridData(GridData.FILL_BOTH);
    this.tree.setLayoutData(data);

    bindModelToTree();

    // TODO
    // new DragAndDropEnabler(this.xmlTree);

    this.tree.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        updatetSelectedObject();
        SceneGraphTree.this.modeler.updateRenderer(); 
        SceneGraphTree.this.modeler.updatePropertyEditor();
      }
    });

    this.tree.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent arg0) {
        if (arg0.keyCode == SWT.DEL) {
          removeSelectedItem();
        }
      }
    });

    createPopupMenu();
  }

  /**
   * 選択されたitemを削除します。 
   */
  void removeSelectedItem() {
    final TreeItem[] selectedItems = SceneGraphTree.this.tree.getSelection();
    if (selectedItems.length == 0) {
      return;
    }
    
    final TreeItem selectedItem = selectedItems[0];
    final Object selectedData = selectedItem.getData();          

    if (selectedItem.getText().equals("scene")) { //$NON-NLS-1$
      return;
    }

    final TreeItem parentItem = selectedItem.getParentItem();

    if (parentItem.getText().equals("scene")) { //$NON-NLS-1$
      final MessageBox message = new MessageBox(this.parentComposite.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
      message.setMessage(Messages.getString("SceneGraphTree.29")); //$NON-NLS-1$
      final int result = message.open();
      if (result == SWT.NO) {
        return;
      }
      
      SceneGraphTree.this.model.removeGroup((GroupModel)selectedData);
      selectedItem.dispose();
    } else {
      final GroupModel group = (GroupModel)parentItem.getData();
      if (removeObject(group, selectedData)) {
        selectedItem.dispose();
      }
    }
    
    SceneGraphTree.this.modeler.updateRenderer();
    SceneGraphTree.this.modeler.updatePropertyEditor();
  }
  
  /**
   * ポップアップメニューを生成します。
   * 
   * @param parent コンポジット
   */
  private void createPopupMenu() {
    final Menu menu = new Menu(this.parentComposite.getShell(), SWT.POP_UP);
    this.tree.setMenu(menu);

    final MenuItem addGroup = new MenuItem(menu, SWT.POP_UP);
    addGroup.setText(Messages.getString("SceneGraphTree.7")); //$NON-NLS-1$
    addGroup.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final GroupModel group = new GroupModel(Messages.getString("SceneGraphTree.37")); //$NON-NLS-1$
        if (SceneGraphTree.this.selectedGroup == SceneGraphTree.this.rootGroup) {
          SceneGraphTree.this.model.addGroup(group);
        } else {
          SceneGraphTree.this.selectedGroup.add(group);
        }
        SceneGraphTree.this.selectedGroup = group;
        SceneGraphTree.this.selectedObject = group;
        
        updateTree();
      }
    });

    final MenuItem addBox = new MenuItem(menu, SWT.POP_UP);
    addBox.setText(Messages.getString("SceneGraphTree.4")); //$NON-NLS-1$
    addBox.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final ObjectModel object = BoxModel.createDefault();
        SceneGraphTree.this.selectedGroup.add(object);
        SceneGraphTree.this.selectedObject = object;
        updateTree();
      }
    });

    final MenuItem addCylinder = new MenuItem(menu, SWT.POP_UP);
    addCylinder.setText(Messages.getString("SceneGraphTree.0")); //$NON-NLS-1$
    addCylinder.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final ObjectModel object = CylinderModel.createDefault();
        SceneGraphTree.this.selectedGroup.add(object);
        SceneGraphTree.this.selectedObject = object;
        updateTree();
      }
    });

    final MenuItem addSphere = new MenuItem(menu, SWT.POP_UP);
    addSphere.setText(Messages.getString("SceneGraphTree.28")); //$NON-NLS-1$
    addSphere.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final ObjectModel object = SphereModel.createDefault();
        SceneGraphTree.this.selectedGroup.add(object);
        SceneGraphTree.this.selectedObject = object;
        updateTree();
      }
    });

    final MenuItem addCone = new MenuItem(menu, SWT.POP_UP);
    addCone.setText(Messages.getString("SceneGraphTree.31"));  //$NON-NLS-1$
    addCone.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final ObjectModel object = ConeModel.createDefault();
        SceneGraphTree.this.selectedGroup.add(object);
        SceneGraphTree.this.selectedObject = object;
        updateTree();
      }
    });
    
    final MenuItem addCapsule = new MenuItem(menu, SWT.POP_UP);
    addCapsule.setText(Messages.getString("SceneGraphTree.35")); //$NON-NLS-1$
    addCapsule.addSelectionListener(new SelectionAdapter() {

      @Override     
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final ObjectModel object = CapsuleModel.createDefault();
        SceneGraphTree.this.selectedGroup.add(object);
        SceneGraphTree.this.selectedObject = object;
        updateTree();
      }
    });

    final MenuItem addTriangle = new MenuItem(menu, SWT.POP_UP);
    addTriangle.setText(Messages.getString("SceneGraphTree.5")); //$NON-NLS-1$
    addTriangle.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final ObjectModel object = TriangleModel.createDefault();
        SceneGraphTree.this.selectedGroup.add(object);
        SceneGraphTree.this.selectedObject = object;
        updateTree();
      }
    });

    final MenuItem addQuadrangle = new MenuItem(menu, SWT.POP_UP);
    addQuadrangle.setText(Messages.getString("SceneGraphTree.6")); //$NON-NLS-1$
    addQuadrangle.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        final ObjectModel object = QuadrangleModel.createDefault();
        SceneGraphTree.this.selectedGroup.add(object);
        SceneGraphTree.this.selectedObject = object;
        updateTree();
      }
    });
    
    final MenuItem transform = new MenuItem(menu, SWT.POP_UP);
    transform.setText(Messages.getString("SceneGraphTree.39")); //$NON-NLS-1$
    transform.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        if (SceneGraphTree.this.selectedObject instanceof CompositionModel) {
          final TransformationEditor dialog = new TransformationEditor(SceneGraphTree.this.parentComposite.getShell(), (CompositionModel)SceneGraphTree.this.selectedObject, SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        }
      }
    });

    final MenuItem cut = new MenuItem(menu, SWT.POP_UP);
    cut.setText(Messages.getString("SceneGraphTree.32")); //$NON-NLS-1$
    cut.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        if (SceneGraphTree.this.selectedObject instanceof GroupModel) {
          SceneGraphTree.this.bufferedGroup = SceneGraphTree.this.selectedGroup;
          SceneGraphTree.this.bufferedObject = null;
        } else {
          SceneGraphTree.this.bufferedGroup = null;
          SceneGraphTree.this.bufferedObject = (ObjectModel)SceneGraphTree.this.selectedObject;
        }
        
        removeSelectedItem();
      }
    });

    final MenuItem copy = new MenuItem(menu, SWT.POP_UP);
    copy.setText(Messages.getString("SceneGraphTree.33")); //$NON-NLS-1$
    copy.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        if (SceneGraphTree.this.selectedObject instanceof GroupModel) {
          SceneGraphTree.this.bufferedGroup = SceneGraphTree.this.selectedGroup;
          SceneGraphTree.this.bufferedObject = null;
        } else {
          SceneGraphTree.this.bufferedGroup = null;
          SceneGraphTree.this.bufferedObject = (ObjectModel)SceneGraphTree.this.selectedObject;
        }
      }
    });

    final MenuItem paste = new MenuItem(menu, SWT.POP_UP);
    paste.setText(Messages.getString("SceneGraphTree.34")); //$NON-NLS-1$
    paste.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        if ((SceneGraphTree.this.selectedObject instanceof GroupModel) == false) {
          return;
        }
        
        if (SceneGraphTree.this.bufferedGroup != null) {
          if (SceneGraphTree.this.selectedGroup == SceneGraphTree.this.rootGroup) {
            SceneGraphTree.this.model.addGroup(SceneGraphTree.this.bufferedGroup.clone());
          } else {
            SceneGraphTree.this.selectedGroup.add(SceneGraphTree.this.bufferedGroup.clone());
          }
          updateTree();
        }
        
        if (SceneGraphTree.this.bufferedObject != null) {
          SceneGraphTree.this.selectedGroup.add(SceneGraphTree.this.bufferedObject.createClone());
          updateTree();
        }
      }
    });

    final MenuItem delete = new MenuItem(menu, SWT.POP_UP);
    delete.setText(Messages.getString("SceneGraphTree.13")); //$NON-NLS-1$
    delete.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(@SuppressWarnings("unused") SelectionEvent e) {
        removeSelectedItem();
      }
    });
    
    menu.addMenuListener(new MenuAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void menuShown(@SuppressWarnings("unused") MenuEvent e) {
        final Object clickedObject = SceneGraphTree.this.tree.getSelection()[0].getData();
        
        if (clickedObject == SceneGraphTree.this.rootGroup) {
          addBox.setEnabled(false);
          addCylinder.setEnabled(false);
          addSphere.setEnabled(false);
          addCone.setEnabled(false);
          addCapsule.setEnabled(false);
          addTriangle.setEnabled(false);
          addQuadrangle.setEnabled(false);
          addGroup.setEnabled(true);
          copy.setEnabled(false);
          cut.setEnabled(false);
          if (SceneGraphTree.this.bufferedGroup != null || SceneGraphTree.this.bufferedObject != null) {
            paste.setEnabled(true);
          } else {
            paste.setEnabled(false);
          }
          delete.setEnabled(false);
          return;
        } 
        
        if (clickedObject instanceof GroupModel) {
          addBox.setEnabled(true);
          addCylinder.setEnabled(true);
          addSphere.setEnabled(true);
          addCone.setEnabled(true);
          addCapsule.setEnabled(true);
          addTriangle.setEnabled(true);
          addQuadrangle.setEnabled(true);
          addGroup.setEnabled(true);
          copy.setEnabled(true);
          cut.setEnabled(true);
          if (SceneGraphTree.this.bufferedGroup != null || SceneGraphTree.this.bufferedObject != null) {
            paste.setEnabled(true);
          } else {
            paste.setEnabled(false);
          }
          delete.setEnabled(true);
          return;
        }

        addBox.setEnabled(false);
        addCylinder.setEnabled(false);
        addSphere.setEnabled(false);
        addCone.setEnabled(false);
        addCapsule.setEnabled(false);
        addTriangle.setEnabled(false);
        addQuadrangle.setEnabled(false);
        addGroup.setEnabled(false);
        copy.setEnabled(true);
        cut.setEnabled(true);
        if (SceneGraphTree.this.bufferedGroup != null || SceneGraphTree.this.bufferedObject != null) {
          paste.setEnabled(true);
        } else {
          paste.setEnabled(false);
        }
        delete.setEnabled(true);
      }
    });
  }

  /**
   * 選択されているオブジェクトを更新します。
   */
  public void updatetSelectedObject() {
    if (this.tree.getSelectionCount() == 0) {
      return;
    }

    this.selectedObject = this.tree.getSelection()[0].getData();

    if (this.selectedObject instanceof GroupModel) {
      this.selectedGroup = (GroupModel)this.selectedObject;
    } else {
      this.selectedGroup = (GroupModel)this.tree.getSelection()[0].getParentItem().getData();
    }
    
    makeObjectNontransparent(this.selectedObject);
  }

  /**
   * 指定されたオブジェクトのみを非透過に設定します。
   * 
   * @param object オブジェクト
   */
  private void makeObjectNontransparent(Object object) {
    if (object == this.rootGroup) {
      for (final GroupModel group : this.model.getGroups()) {
        setAllTransparent(group, false);
      }
      return;
    }

    for (final GroupModel group : this.model.getGroups()) {
      setAllTransparent(group, true);
    }
    
    if (object instanceof ObjectModel) {
      ((ObjectModel)object).setTransparent(false);
    } else if (object instanceof GroupModel) {
      final GroupModel group = (GroupModel)object;
      setTransparent(group, false);
    }
  }

  /**
   * 指定されたグループ以下に含まれる全てのプリミティブの透過性を設定する。
   * 
   * @param group グループ
   * @param transparent 透過ならばtrue
   */
  public void setAllTransparent(final GroupModel group, boolean transparent) {
    setTransparent(group, transparent);

    for (GroupModel childGroup : group.getGroups()) {
      setAllTransparent(childGroup, transparent);
    }
  }
  
  /**
   * グループに含まれる全てのプリミティブの透過性を設定する。
   * 
   * @param group グループ
   * @param transparent 透過ならばtrue
   */
  public void setTransparent(final GroupModel group, boolean transparent) {
    for (ObjectModel primitive : group.getObjects()) {
      if (primitive instanceof NullModel) {
        continue;
      }
      primitive.setTransparent(transparent);
    }
  }

  /**
   * 選択されているグループを返します。
   * 
   * @return 選択されているグループ
   */
  public GroupModel getSelectedGroup() {
    return this.selectedGroup;
  }
  
  /**
   * 選択されているグループを設定します。
   * 
   * @param selectedGroup 選択されているグループ
   */
  public void setSelectedGroup(GroupModel selectedGroup) {
    this.selectedGroup = selectedGroup;
  }
  
  /**
   * 選択されているオブジェクトを設定します。
   * 
   * @param selectedObject 選択されているオブジェクト
   */
  public void setSelectedObject(Object selectedObject) {
    this.selectedObject = selectedObject;
  }
  
  /**
   * 選択されているオブジェクトを返します。
   * 
   * @return 選択されているオブジェクト
   */
  public Object getSelectedObject() {
    return this.selectedObject;
  }

  /**
   * ツリーとビューアを更新します。
   */
  public void updateTree() {
    bindModelToTree();
    this.modeler.updateRenderer();
    this.modeler.updatePropertyEditor();
  }

  /**
   * グループからモデルを削除します。
   * 
   * @param group グループ
   * @param object プリミティブ
   * 
   * @return モデルを削除したかどうか。（削除したとき:true,削除されなかったとき:false）
   */
  boolean removeObject(GroupModel group, Object object) {
    if (object instanceof ObjectModel) {
      group.remove((ObjectModel)object);
    } else if (object instanceof GroupModel) {
      final MessageBox message = new MessageBox(this.parentComposite.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
      message.setMessage(Messages.getString("SceneGraphTree.29")); //$NON-NLS-1$
      message.setText(Messages.getString("SceneGraphTree.30")); //$NON-NLS-1$
      int result = message.open();
      if (result == SWT.NO) {
        return false;
      }
      group.remove((GroupModel)object);
    }
    
    return true;
  }

  /**
   * ツリーにオブジェクトを追加します。
   * @param parent 親
   * @param groups グループ
   */
  private void addItemToTree(TreeItem parent, List<GroupModel> groups) {
    for (final GroupModel group : groups) {
      final TreeItem groupItem;
      if (parent.getText().equals("scene")) { //$NON-NLS-1$
        groupItem = new TreeItem(parent, SWT.NONE);
        groupItem.setText(group.getName());
      } else {
        groupItem = new TreeItem(parent, SWT.NONE);
        groupItem.setText(group.toShortString());
      }
      groupItem.setData(group);
      if (group == SceneGraphTree.this.selectedObject) {
        SceneGraphTree.this.tree.select(groupItem);
      }

      final List<ObjectModel> objects = group.getObjects();
      final boolean groupHasAnyObject = objects.size() > 0 && (objects.get(0) instanceof NullModel) == false;
      
      if (groupHasAnyObject) {
        final TreeItem objectItems = new TreeItem(groupItem, SWT.NONE);
        objectItems.setText(Messages.getString("SceneGraphTree.38")); //$NON-NLS-1$
        objectItems.setData(group);

        for (final ObjectModel object :  objects) {
          if (object instanceof NullModel) {
            continue;
          }
          
          final TreeItem child = new TreeItem(objectItems, SWT.NONE);
          child.setText(object.toShortString());
          child.setData(object);
          if (object == SceneGraphTree.this.selectedObject) {
            SceneGraphTree.this.tree.select(child);
          }
        }
      }
      
      addItemToTree(groupItem, group.getGroups());
    }
    
    if (parent != null) {
      parent.setExpanded(true);
    }
  }

  /**
   * モデルをシーングラフにバインドし(結びつけ)ます。
   */
  public void bindModelToTree() {
    clearTree();
    
    final TreeItem rootItem = new TreeItem(this.tree, SWT.NONE);
    rootItem.setText("scene"); //$NON-NLS-1$
    rootItem.setData(this.rootGroup);
    
    addItemToTree(rootItem, this.model.getGroups());
  }

  /**
   * モデルデータを返します。
   * 
   * @return モデルデータ
   */
  public SceneModel getModel() {
    return this.model;
  }

  /**
   * ツリーから全てのItemを消去します。
   */
  public void clearTree() {
    if (this.tree.getItemCount() == 0) {
      return;
    }
    
    for (final TreeItem item : this.tree.getItems()) {
      removeItemsFromTree(item);
      item.dispose();
    }
  }

  /**
   * ツリーからItemを削除します。
   * 
   * @param items Item郡
   */
  private void removeItemsFromTree(TreeItem items) {
    for (final TreeItem item : items.getItems()) {
      if (item.getItemCount() != 0) {
        removeItemsFromTree(item);
      }
      item.dispose();
    }
  }

  /**
   * モデルを設定します。
   * 
   * @param model モデル
   */
  public void setModel(SceneModel model) {
    this.model = model;
    bindModelToTree();
  }

  /**
   * @return XML tree
   */
  public Tree getXMLTree() {
    return this.tree;
  }

  /**
   * モデルを修正中であるか設定します。
   * 
   * @param isModifyingObject モデルを修正中ならばtrue
   */
  public void setIsModifyingObject(boolean isModifyingObject) {
    this.isModifyingObject = isModifyingObject;
  }
}