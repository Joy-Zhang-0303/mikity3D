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
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;

/**
 * シーングラフを表すクラスです。
 */
public class SceneGraphTree {
  /** */
  Tree tree;
  /** */
  SceneModel model;

  /** 選択されているオブジェクト。 */
  Object targetObject = null;
  /** 選択されているグループ。 */
  GroupModel targetGroup = null;
  
  /** シーンを表すグループ。 */
  GroupModel scene;
  
  /** */
  JoglModeler modeler;

  /** 記憶されたオブジェクト。 */
  ObjectModel bufferedObject = null;
  /** 記憶されたグループ。 */
  GroupModel bufferedGroup = null;
  
  /** オブジェクトを修正中ならばtrue */
  boolean isModifyingObject = false;

  /**
   * コンストラクター
   */
  public SceneGraphTree() {
    // nothing to do
  }

  /**
   * 新しく生成された<code>SceneGraphTree</code>オブジェクトを初期化します。
   * 
   * @param parent コンポジット
   * @param modeler モデラー
   * @param model モデル
   */
  public SceneGraphTree(final Composite parent, final JoglModeler modeler, final SceneModel model) {
    this.model = model;
    this.modeler = modeler;
    this.scene = new GroupModel("scene"); //$NON-NLS-1$
    this.scene.setGroups(model.getGroups());

    this.targetGroup = this.scene;
    this.targetObject= this.scene;

    createTree(parent);
  }

  /**
   * ツリーを生成する
   * 
   * @param parent
   */
  private void createTree(final Composite parent) {
    parent.setLayout(new GridLayout(1, true));
    this.tree = new Tree(parent, SWT.NONE);
    final GridData data = new GridData(GridData.FILL_BOTH);
    this.tree.setLayoutData(data);

    fillTree();

    // TODO
    // new DragAndDropEnabler(this.xmlTree);

    this.tree.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        setSelectedObjectAsTarget();
        SceneGraphTree.this.modeler.updateRenderer(); 
        SceneGraphTree.this.modeler.updatePropertyEditor();
      }
    });

    this.tree.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent arg0) {
        if (arg0.keyCode == SWT.DEL) {
          removeSelectedItem(parent);
        }
      }
    });

    createPopupMenu(parent);
  }

  /**
   * 選択されたitemを削除します。 
   */
  void removeSelectedItem(Composite parent) {
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
      final MessageBox message = new MessageBox(parent.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
      message.setMessage(Messages.getString("SceneGraphTree.29")); //$NON-NLS-1$
      final int result = message.open();
      if (result == SWT.NO) {
        return;
      }
      
      SceneGraphTree.this.model.removeGroup((GroupModel)selectedData);
      selectedItem.dispose();
    } else {
      final GroupModel group = (GroupModel)parentItem.getData();
      if (removeObject(group, selectedData, parent)) {
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
  private void createPopupMenu(final Composite parent) {
    final Menu menu = new Menu(parent.getShell(), SWT.POP_UP);
    this.tree.setMenu(menu);

    final MenuItem addGroup = new MenuItem(menu, SWT.POP_UP);
    addGroup.setText(Messages.getString("SceneGraphTree.7")); //$NON-NLS-1$
    addGroup.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final GroupModel group = new GroupModel(Messages.getString("SceneGraphTree.37")); //$NON-NLS-1$
        if (SceneGraphTree.this.targetGroup == SceneGraphTree.this.scene) {
          SceneGraphTree.this.model.addGroup(group);
        } else {
          SceneGraphTree.this.targetGroup.add(group);
        }
        updateTree();
      }
    });

    final MenuItem addBox = new MenuItem(menu, SWT.POP_UP);
    addBox.setText(Messages.getString("SceneGraphTree.4")); //$NON-NLS-1$
    addBox.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final ObjectModel primitive = BoxModel.createDefault();
        SceneGraphTree.this.targetGroup.add(primitive);
        updateTree();
      }
    });

    final MenuItem addCylinder = new MenuItem(menu, SWT.POP_UP);
    addCylinder.setText(Messages.getString("SceneGraphTree.0")); //$NON-NLS-1$
    addCylinder.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final ObjectModel primitive = CylinderModel.createDefault();
        SceneGraphTree.this.targetGroup.add(primitive);
        updateTree();
      }
    });

    final MenuItem addSphere = new MenuItem(menu, SWT.POP_UP);
    addSphere.setText(Messages.getString("SceneGraphTree.28")); //$NON-NLS-1$
    addSphere.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final ObjectModel primitive = SphereModel.createDefault();
        SceneGraphTree.this.targetGroup.add(primitive);
        updateTree();
      }
    });

    final MenuItem addCone = new MenuItem(menu, SWT.POP_UP);
    addCone.setText(Messages.getString("SceneGraphTree.31"));  //$NON-NLS-1$
    addCone.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final ObjectModel primitive = ConeModel.createDefault();
        SceneGraphTree.this.targetGroup.add(primitive);
        updateTree();
      }
    });
    
    final MenuItem addCapsule = new MenuItem(menu, SWT.POP_UP);
    addCapsule.setText(Messages.getString("SceneGraphTree.35")); //$NON-NLS-1$
    addCapsule.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final ObjectModel primitive = CapsuleModel.createDefault();
        SceneGraphTree.this.targetGroup.add(primitive);
        updateTree();
      }
    });

    final MenuItem addTriangle = new MenuItem(menu, SWT.POP_UP);
    addTriangle.setText(Messages.getString("SceneGraphTree.5")); //$NON-NLS-1$
    addTriangle.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final ObjectModel primitive = TriangleModel.createDefault();
        SceneGraphTree.this.targetGroup.add(primitive);
        updateTree();
      }
    });

    final MenuItem addQuadrangle = new MenuItem(menu, SWT.POP_UP);
    addQuadrangle.setText(Messages.getString("SceneGraphTree.6")); //$NON-NLS-1$
    addQuadrangle.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final ObjectModel primitive = QuadrangleModel.createDefault();
        SceneGraphTree.this.targetGroup.add(primitive);
        updateTree();
      }
    });

    final MenuItem cut = new MenuItem(menu, SWT.POP_UP);
    cut.setText(Messages.getString("SceneGraphTree.32")); //$NON-NLS-1$
    cut.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObject instanceof GroupModel) {
          SceneGraphTree.this.bufferedGroup = SceneGraphTree.this.targetGroup;
          SceneGraphTree.this.bufferedObject = null;
        } else {
          SceneGraphTree.this.bufferedGroup = null;
          SceneGraphTree.this.bufferedObject = (ObjectModel)SceneGraphTree.this.targetObject;
        }
        
        removeSelectedItem(parent);
      }
    });

    final MenuItem copy = new MenuItem(menu, SWT.POP_UP);
    copy.setText(Messages.getString("SceneGraphTree.33")); //$NON-NLS-1$
    copy.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObject instanceof GroupModel) {
          SceneGraphTree.this.bufferedGroup = SceneGraphTree.this.targetGroup;
          SceneGraphTree.this.bufferedObject = null;
        } else {
          SceneGraphTree.this.bufferedGroup = null;
          SceneGraphTree.this.bufferedObject = (ObjectModel)SceneGraphTree.this.targetObject;
        }
      }
    });

    final MenuItem paste = new MenuItem(menu, SWT.POP_UP);
    paste.setText(Messages.getString("SceneGraphTree.34")); //$NON-NLS-1$
    paste.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if ((SceneGraphTree.this.targetObject instanceof GroupModel) == false) {
          return;
        }
        
        if (SceneGraphTree.this.bufferedGroup != null) {
          if (SceneGraphTree.this.targetGroup == SceneGraphTree.this.scene) {
            SceneGraphTree.this.model.addGroup(SceneGraphTree.this.bufferedGroup.clone());
          } else {
            SceneGraphTree.this.targetGroup.add(SceneGraphTree.this.bufferedGroup.clone());
          }
          updateTree();
        }
        
        if (SceneGraphTree.this.bufferedObject != null) {
          SceneGraphTree.this.targetGroup.add(SceneGraphTree.this.bufferedObject.createClone());
          updateTree();
        }
      }
    });

    final MenuItem delete = new MenuItem(menu, SWT.POP_UP);
    delete.setText(Messages.getString("SceneGraphTree.13")); //$NON-NLS-1$
    delete.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        removeSelectedItem(parent);
      }
    });
    
    menu.addMenuListener(new MenuAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void menuShown(MenuEvent e) {
        final Object clickedObject = SceneGraphTree.this.tree.getSelection()[0].getData();
        
        if (clickedObject == SceneGraphTree.this.scene) {
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
   * 選択されているオブジェクトをターゲットとして設定します。
   */
  public void setSelectedObjectAsTarget() {
    if (this.tree.getSelectionCount() == 0) {
      return;
    }

    this.targetObject = this.tree.getSelection()[0].getData();

    if (this.targetObject instanceof GroupModel) {
      this.targetGroup = (GroupModel)this.targetObject;
    } else {
      this.targetGroup = (GroupModel)this.tree.getSelection()[0].getParentItem().getData();
    }
    
    makeObjectNontransparent(this.targetObject);
  }

  /**
   * 指定されたオブジェクトのみを非透過に設定します。
   * 
   * @param object オブジェクト
   */
  private void makeObjectNontransparent(Object object) {
    if (object == this.scene) {
      for (GroupModel group : this.model.getGroups()) {
        setAllTransparent(group, false);
      }
      return;
    }

    for (GroupModel group : this.model.getGroups()) {
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
  public GroupModel getTargetGroup() {
    return this.targetGroup;
  }
  
  /**
   * 選択されているオブジェクトを返します。
   * 
   * @return 選択されているオブジェクト
   */
  public Object getTargetObject() {
    return this.targetObject;
  }

  /**
   * ツリーとビューアを更新します。
   */
  public void updateTree() {
    fillTree();
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
  protected boolean removeObject(GroupModel group, Object object, Composite parent) {
    if (object instanceof ObjectModel) {
      group.remove((ObjectModel)object);
    } else if (object instanceof GroupModel) {
      final MessageBox message = new MessageBox(parent.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
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
  private void addTreeItem(TreeItem parent, List<GroupModel> groups) {
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

      final List<ObjectModel> primitives = group.getObjects();
      final boolean groupHasAnyPrimitive = primitives.size() > 0 && (primitives.get(0) instanceof NullModel) == false;
      
      if (groupHasAnyPrimitive) {
        final TreeItem primitiveItems = new TreeItem(groupItem, SWT.NONE);
        primitiveItems.setText(Messages.getString("SceneGraphTree.38")); //$NON-NLS-1$
        primitiveItems.setData(group);

        for (final ObjectModel primitive :  primitives) {
          if (primitive instanceof NullModel) {
            continue;
          }
          
          final TreeItem child = new TreeItem(primitiveItems, SWT.NONE);
          child.setText(primitive.toShortString());
          child.setData(primitive);
        }
      }
      
      addTreeItem(groupItem, group.getGroups());
    }
    
    if (parent != null) {
      parent.setExpanded(true);
    }
  }

  /**
   * シーングラフツリーにモデルを追加します。
   */
  public void fillTree() {
    clearTree();
    
    TreeItem sceneItem = new TreeItem(this.tree, SWT.NONE);
    sceneItem.setText("scene"); //$NON-NLS-1$
    sceneItem.setData(this.scene);
    
    addTreeItem(sceneItem, this.model.getGroups());
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
   * ツリーを消去します。
   */
  public void clearTree() {
    if (this.tree.getItemCount() == 0) {
      return;
    }
    
    for (TreeItem item : this.tree.getItems()) {
      clearTreeItems(item);
      item.dispose();
    }
  }

  /**
   * ツリーのノードを削除します。
   * 
   * @param items ノード郡
   */
  private void clearTreeItems(TreeItem items) {
    for (TreeItem item : items.getItems()) {
      if (item.getItemCount() != 0) {
        clearTreeItems(item);
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
    fillTree();
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