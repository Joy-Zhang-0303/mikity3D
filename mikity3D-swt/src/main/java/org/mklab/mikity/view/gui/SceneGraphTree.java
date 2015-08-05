package org.mklab.mikity.view.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
import org.mklab.mikity.view.gui.dialog.AddGroupDialog;
import org.mklab.mikity.view.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.view.gui.dialog.AddQuadPolygonDialog;
import org.mklab.mikity.view.gui.dialog.AddTrianglePolygonDialog;
import org.mklab.mikity.view.gui.dialog.EditGroupDialog;
import org.mklab.mikity.view.gui.dialog.EditPrimitiveDialog;
import org.mklab.mikity.view.gui.dialog.EditQuadPolygonDialog;
import org.mklab.mikity.view.gui.dialog.EditTrianglePolygonDialog;


/**
 * シーングラフを表すクラスです。
 */
public class SceneGraphTree {
  /** */
  Tree tree;
  /** */
  SceneModel model;
  /** */
  Composite composite;

  /** 選択されているオブジェクト。 */
  Object targetObject = null;
  /** 選択されているグループ。 */
  GroupModel targetGroup = null;
  /** 選択されている物の親グループ。 */
  GroupModel targetParentGroup = null;
  
  /** シーンを表すグループ。 */
  GroupModel scene;
  
  /** */
  JoglModeler modeler;

  /** 記憶されたオブジェクト。 */
  Object bufferedObject = null;
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
   * @param composite コンポジット
   * @param modeler モデラー
   * @param model モデル
   */
  public SceneGraphTree(final Composite composite, final JoglModeler modeler, final SceneModel model) {
    this.model = model;
    this.targetGroup = this.model.getGroup(0);
    this.targetObject= this.model.getGroup(0);
    this.scene = new GroupModel();
    this.scene.setName("scene"); //$NON-NLS-1$
    this.modeler = modeler;
    createTree(composite);
    this.composite = composite;
  }

  /**
   * ツリーを生成する
   * 
   * @param composite
   */
  private void createTree(final Composite composite) {
    composite.setLayout(new GridLayout(1, true));
    this.tree = new Tree(composite, SWT.SINGLE | SWT.BORDER);
    final GridData data2 = new GridData(GridData.FILL_BOTH);
    this.tree.setLayoutData(data2);

    fillTree();

    // TODO
    // new DragAndDropEnabler(this.xmlTree);

    this.tree.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseDoubleClick(MouseEvent arg0) {
        final Object clickObject = SceneGraphTree.this.tree.getSelection()[0].getData();

        if (SceneGraphTree.this.isModifyingObject) {
          return;
        }
       
        if (clickObject == SceneGraphTree.this.scene) {
          return;
        }
        
        if (clickObject == null) {
          return;
        }

        if (clickObject instanceof GroupModel) {
          final EditGroupDialog dialog = new EditGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup, true, SceneGraphTree.this, SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else if (clickObject instanceof TrianglePolygonModel) {
          final EditTrianglePolygonDialog dialog = new EditTrianglePolygonDialog(composite.getShell(), (TrianglePolygonModel)clickObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this,
              SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else if (clickObject instanceof QuadPolygonModel) {
          final EditQuadPolygonDialog dialog = new EditQuadPolygonDialog(composite.getShell(), (QuadPolygonModel)clickObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this,
              SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else {
          final EditPrimitiveDialog dialog = new EditPrimitiveDialog(composite.getShell(), clickObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this, SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        }
      }
    });

    this.tree.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        setSelectedObjectAsTarget();
        SceneGraphTree.this.modeler.updateRenderer(); 
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

    createPopupMenu(composite);
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
      final MessageBox message = new MessageBox(this.composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
      message.setMessage(Messages.getString("SceneGraphTree.29")); //$NON-NLS-1$
      //message.setText(Messages.getString("SceneGraphTree.30")); //$NON-NLS-1$
      final int result = message.open();
      if (result == SWT.NO) {
        return;
      }
      
      SceneGraphTree.this.model.removeGroup((GroupModel)selectedData);
      selectedItem.dispose();
      
      if (parentItem.getItemCount() == 0) {
        final GroupModel objectGroup = new GroupModel();
        objectGroup.setName("object"); //$NON-NLS-1$
        SceneGraphTree.this.model.addGroup(objectGroup);
        SceneGraphTree.this.targetGroup = objectGroup;
        fillTree();
      }
    } else {
      final GroupModel group = (GroupModel)parentItem.getData();
      if (removeObject(group, selectedData)) {
        selectedItem.dispose();
      }
    }
    
    SceneGraphTree.this.modeler.updateRenderer();
  }
  
  /**
   * ポップアップメニューを生成します。
   * 
   * @param composite コンポジット
   */
  private void createPopupMenu(final Composite composite) {
    final Menu menu = new Menu(composite.getShell(), SWT.POP_UP);
    this.tree.setMenu(menu);

    final MenuItem addBox = new MenuItem(menu, SWT.POP_UP);
    addBox.setText(Messages.getString("SceneGraphTree.4")); //$NON-NLS-1$

    final MenuItem addCylinder = new MenuItem(menu, SWT.POP_UP);
    addCylinder.setText(Messages.getString("SceneGraphTree.0")); //$NON-NLS-1$

    final MenuItem addSphere = new MenuItem(menu, SWT.POP_UP);
    addSphere.setText(Messages.getString("SceneGraphTree.28")); //$NON-NLS-1$

    final MenuItem addCone = new MenuItem(menu, SWT.POP_UP);
    addCone.setText(Messages.getString("SceneGraphTree.31"));  //$NON-NLS-1$

    final MenuItem addTrianglePolygon = new MenuItem(menu, SWT.POP_UP);
    addTrianglePolygon.setText(Messages.getString("SceneGraphTree.5")); //$NON-NLS-1$

    final MenuItem addQuadPolygon = new MenuItem(menu, SWT.POP_UP);
    addQuadPolygon.setText(Messages.getString("SceneGraphTree.6")); //$NON-NLS-1$

    final MenuItem addGroup = new MenuItem(menu, SWT.POP_UP);
    addGroup.setText(Messages.getString("SceneGraphTree.7")); //$NON-NLS-1$

    final MenuItem cut = new MenuItem(menu, SWT.POP_UP);
    cut.setText(Messages.getString("SceneGraphTree.32")); //$NON-NLS-1$

    final MenuItem copy = new MenuItem(menu, SWT.POP_UP);
    copy.setText(Messages.getString("SceneGraphTree.33")); //$NON-NLS-1$

    final MenuItem paste = new MenuItem(menu, SWT.POP_UP);
    paste.setText(Messages.getString("SceneGraphTree.34")); //$NON-NLS-1$

    final MenuItem edit = new MenuItem(menu, SWT.POP_UP);
    edit.setText(Messages.getString("SceneGraphTree.12")); //$NON-NLS-1$

    final MenuItem delete = new MenuItem(menu, SWT.POP_UP);
    delete.setText(Messages.getString("SceneGraphTree.13")); //$NON-NLS-1$
    
    addBox.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddPrimitiveDialog dialog = new AddPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetGroup, AddPrimitiveDialog.BOX);
        dialog.open();
        updateTree();
      }
    });

    addCylinder.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddPrimitiveDialog dialog = new AddPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetGroup, AddPrimitiveDialog.CYLINDER);
        dialog.open();
        updateTree();
      }
    });

    addSphere.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddPrimitiveDialog dialog = new AddPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetGroup, AddPrimitiveDialog.SPHERE);
        dialog.open();
        updateTree();
      }
    });

    addCone.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddPrimitiveDialog dialog = new AddPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetGroup, AddPrimitiveDialog.CONE);
        dialog.open();
        updateTree();
      }
    });
    
    addTrianglePolygon.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddTrianglePolygonDialog dialog = new AddTrianglePolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        dialog.open();
        updateTree();
      }
    });

    addQuadPolygon.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddQuadPolygonDialog dialog = new AddQuadPolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        dialog.open();
        updateTree();
      }
    });

    addGroup.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddGroupDialog dialog = new AddGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        dialog.open();
        updateTree();
      }
    });

    edit.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObject instanceof GroupModel) {
          final EditGroupDialog dialog = new EditGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup, true, SceneGraphTree.this, SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else if (SceneGraphTree.this.targetObject instanceof TrianglePolygonModel) {
          final EditTrianglePolygonDialog dialog = new EditTrianglePolygonDialog(composite.getShell(), (TrianglePolygonModel)SceneGraphTree.this.targetObject, SceneGraphTree.this.targetGroup,
              SceneGraphTree.this, SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else if (SceneGraphTree.this.targetObject instanceof QuadPolygonModel) {
          final EditQuadPolygonDialog dialog = new EditQuadPolygonDialog(composite.getShell(), (QuadPolygonModel)SceneGraphTree.this.targetObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this,
              SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else {
          final EditPrimitiveDialog dialog = new EditPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this,
              SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        }
      }
    });

    cut.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObject instanceof GroupModel) {
          SceneGraphTree.this.bufferedGroup = SceneGraphTree.this.targetGroup;
          SceneGraphTree.this.bufferedObject = null;
        } else {
          SceneGraphTree.this.bufferedGroup = null;
          SceneGraphTree.this.bufferedObject = SceneGraphTree.this.targetObject;
        }
        
        removeSelectedItem();
      }
    });
    
    copy.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObject instanceof GroupModel) {
          SceneGraphTree.this.bufferedGroup = SceneGraphTree.this.targetGroup;
          SceneGraphTree.this.bufferedObject = null;
        } else {
          SceneGraphTree.this.bufferedGroup = null;
          SceneGraphTree.this.bufferedObject = SceneGraphTree.this.targetObject;
        }
      }
    });
    
    
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
          if (SceneGraphTree.this.bufferedObject instanceof BoxModel) {
            SceneGraphTree.this.targetGroup.add(((BoxModel)SceneGraphTree.this.bufferedObject).clone());
          }
          if (SceneGraphTree.this.bufferedObject instanceof CylinderModel) {
            SceneGraphTree.this.targetGroup.add(((CylinderModel)SceneGraphTree.this.bufferedObject).clone());
          }
          if (SceneGraphTree.this.bufferedObject instanceof ConeModel) {
            SceneGraphTree.this.targetGroup.add(((ConeModel)SceneGraphTree.this.bufferedObject).clone());
          }
          if (SceneGraphTree.this.bufferedObject instanceof SphereModel) {
            SceneGraphTree.this.targetGroup.add(((SphereModel)SceneGraphTree.this.bufferedObject).clone());
          }
          if (SceneGraphTree.this.bufferedObject instanceof TrianglePolygonModel) {
            SceneGraphTree.this.targetGroup.add(((TrianglePolygonModel)SceneGraphTree.this.bufferedObject).clone());
          }
          if (SceneGraphTree.this.bufferedObject instanceof QuadPolygonModel) {
            SceneGraphTree.this.targetGroup.add(((QuadPolygonModel)SceneGraphTree.this.bufferedObject).clone());
          }
          updateTree();
        }
      }
    });
    
    delete.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        removeSelectedItem();
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
          addTrianglePolygon.setEnabled(false);
          addQuadPolygon.setEnabled(false);
          addGroup.setEnabled(false);
          copy.setEnabled(false);
          cut.setEnabled(false);
          paste.setEnabled(false);
          edit.setEnabled(false);
          delete.setEnabled(false);
          return;
        } 
        
        if (clickedObject instanceof GroupModel) {
          addBox.setEnabled(true);
          addCylinder.setEnabled(true);
          addSphere.setEnabled(true);
          addCone.setEnabled(true);
          addTrianglePolygon.setEnabled(true);
          addQuadPolygon.setEnabled(true);
          addGroup.setEnabled(true);
          copy.setEnabled(true);
          cut.setEnabled(true);
          paste.setEnabled(true);
          edit.setEnabled(true);
          delete.setEnabled(true);
          return;
        }

        addBox.setEnabled(false);
        addCylinder.setEnabled(false);
        addSphere.setEnabled(false);
        addCone.setEnabled(false);
        addTrianglePolygon.setEnabled(false);
        addQuadPolygon.setEnabled(false);
        addGroup.setEnabled(false);
        copy.setEnabled(true);
        cut.setEnabled(true);
        paste.setEnabled(true);
        edit.setEnabled(true);
        delete.setEnabled(true);
      }
    });
  }

  /**
   * 選択されているオブジェクトを設定します。
   */
  void setSelectedObjectAsTarget() {
    if (this.tree.getSelectionCount() == 0) {
      return;
    }

    this.targetObject = this.tree.getSelection()[0].getData();
    
    if (this.targetObject == this.scene) {
      this.targetGroup = (GroupModel)this.targetObject;
      setTarget(this.targetGroup);
      return;
    } 
    
    if (this.targetObject instanceof GroupModel) {
      this.targetGroup = (GroupModel)this.targetObject;
      this.targetParentGroup = (GroupModel)this.tree.getSelection()[0].getParentItem().getData();
      setTarget(this.targetGroup);
      return;
    }

    this.targetGroup = (GroupModel)this.tree.getSelection()[0].getParentItem().getData();
    this.targetParentGroup = null;
    setTarget(this.targetObject);
  }

  /**
   * 選択されているオブジェクトを設定します。
   * 
   * @param object
   */
  private void setTarget(Object object) {
    if (object == this.scene) {
      for (GroupModel group : this.model.getGroups()) {
        setAllTransparent(group, false);
      }
      return;
    }

    for (GroupModel group : this.model.getGroups()) {
      setAllTransparent(group, true);
    }
    
    if (object instanceof BoxModel) {
      ((BoxModel)object).setTransparent(false);
    } else if (object instanceof ConeModel) {
      ((ConeModel)object).setTransparent(false);
    } else if (object instanceof CylinderModel) {
      ((CylinderModel)object).setTransparent(false);
    } else if (object instanceof SphereModel) {
      ((SphereModel)object).setTransparent(false);
    } else if (object instanceof TrianglePolygonModel) {
      ((TrianglePolygonModel)object).setTransparent(false);
    } else if (object instanceof QuadPolygonModel) {
      ((QuadPolygonModel)object).setTransparent(false);
    } else if (object instanceof GroupModel) {
      final GroupModel group = (GroupModel)object;
      setAllTransparent(group, false);
    }
  }

  /**
   * 全透過性を設定する。
   * 
   * @param group グループ
   * @param transparent トランスピアレント
   */
  public void setAllTransparent(final GroupModel group, boolean transparent) {
    final BoxModel[] boxes = group.getBoxes();
    final CylinderModel[] cylinders = group.getCylinders();
    final SphereModel[] spheres = group.getSpheres();
    final ConeModel[] cones = group.getCones();
    final TrianglePolygonModel[] trianglePolygons = group.getTrianglePolygons();
    final QuadPolygonModel[] quadPolygons = group.getQuadPolygons();

    for (int i = 0; i < boxes.length; i++) {
      boxes[i].setTransparent(transparent);
    }
    for (int i = 0; i < cylinders.length; i++) {
      cylinders[i].setTransparent(transparent);
    }
    for (int i = 0; i < spheres.length; i++) {
      spheres[i].setTransparent(transparent);
    }
    for (int i = 0; i < cones.length; i++) {
      cones[i].setTransparent(transparent);
    }
    for (int i = 0; i < trianglePolygons.length; i++) {
      trianglePolygons[i].setTransparent(transparent);
    }
    for (int i = 0; i < quadPolygons.length; i++) {
      quadPolygons[i].setTransparent(transparent);
    }

    final GroupModel[] groups = group.getGroups();

    for (int i = 0; i < groups.length; i++) {
      setAllTransparent(groups[i], transparent);
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

//  /**
//   * グループが編集可能か判定する。
//   * 
//   * @return editable 編集可能ならばtrue
//   */
//  public boolean isGroupEditable() {
//    return this.editable;
//  }

  /**
   * ツリーとビューアを更新します。
   */
  public void updateTree() {
    fillTree();
    this.modeler.updateRenderer();
  }

  /**
   * グループからモデルを削除します。
   * 
   * @param group グループ
   * @param object プリミティブ
   * 
   * @return モデルを削除したかどうか。（削除したとき:true,削除されなかったとき:false）
   */
  protected boolean removeObject(GroupModel group, Object object) {
    if (object instanceof BoxModel) {
      group.remove((BoxModel)object);
    } else if (object instanceof ConeModel) {
      group.remove((ConeModel)object);
    } else if (object instanceof CylinderModel) {
      group.remove((CylinderModel)object);
    } else if (object instanceof SphereModel) {
      group.remove((SphereModel)object);
    } else if (object instanceof TrianglePolygonModel) {
      group.remove((TrianglePolygonModel)object);
    } else if (object instanceof QuadPolygonModel) {
      group.remove((QuadPolygonModel)object);
    } else if (object instanceof GroupModel) {
      MessageBox message = new MessageBox(this.composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
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
  private void addTreeItem(TreeItem parent, GroupModel[] groups) {
    for (final GroupModel group : groups) {
      final TreeItem groupItem;
      if (parent.getText().equals("scene")) { //$NON-NLS-1$
        groupItem = new TreeItem(parent, SWT.NONE);
        groupItem.setText(group.getName());
      } else {
        groupItem = new TreeItem(parent, SWT.NONE);
        if (group.hasAnimation()) {
          for (final AnimationModel animation: group.getAnimations()) {
            if (animation.exists()) {
              groupItem.setText(group.getName() + " (animationTarget=" +   animation.getTarget() + ", sourceId=" + animation.getSource().getId() + ", sourceNumber=" + animation.getSource().getNumber() + ")");   //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            }
          }
        } else {
          groupItem.setText(group.getName() + " (group)"); //$NON-NLS-1$
        }
      }
      groupItem.setData(group);

      final BoxModel[] boxes = group.getBoxes();
      for (final BoxModel box :  boxes) {
        final TreeItem child = new TreeItem(groupItem, SWT.NONE);
        child.setText("box (width=" + box.getWidth() + ", height=" + box.getHeight() + ", depth=" + box.getDepth() + ", color=" + box.getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        child.setData(box);
      }

      final CylinderModel[] cylinders = group.getCylinders();
      for (final CylinderModel cylinder :  cylinders) {
        final TreeItem item = new TreeItem(groupItem, SWT.NONE);
        item.setText("cylinder (radius=" + cylinder.getRadius() + ", height=" + cylinder.getHeight() + ", division=" + cylinder.getDivision() + ", color=" + cylinder.getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        item.setData(cylinder);
      }

      final SphereModel[] spheres = group.getSpheres();
      for (final SphereModel sphere : spheres) {
        final TreeItem item = new TreeItem(groupItem, SWT.NONE);
        item.setText("sphere (radius=" + sphere.getRadius() + ", division=" + sphere.getDivision() + ", color=" + sphere.getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        item.setData(sphere);
      }

      final ConeModel[] cones = group.getCones();
      for (final ConeModel cone :  cones) {
        final TreeItem item = new TreeItem(groupItem, SWT.NONE);
        item.setText("cone (radius=" + cone.getRadisu() + ", height=" + cone.getHeight() + ", divison=" + cone.getDivision() + ", color=" + cone.getColor() + ")"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        item.setData(cone);
      }

      final TrianglePolygonModel[] trianglePolygons = group.getTrianglePolygons();
      for (final TrianglePolygonModel trianglePolygon : trianglePolygons) {
        final TreeItem item = new TreeItem(groupItem, SWT.NONE);
        item.setText("trianglePolygon (color=" + trianglePolygon.getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        item.setData(trianglePolygon);
      }

      final QuadPolygonModel[] quadPolygons = group.getQuadPolygons();
      for (final QuadPolygonModel quadPolygon :  quadPolygons) {
        final TreeItem item = new TreeItem(groupItem, SWT.NONE);
        item.setText("quadPolygon (color=" + quadPolygon.getColor() + ")"); //$NON-NLS-1$//$NON-NLS-2$
        item.setData(quadPolygon);
      }

//      if (parent != null) {
//        parent.setExpanded(true);
//      }
      final GroupModel[] childrenGroups = group.getGroups();
      addTreeItem(groupItem, childrenGroups);
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

  //  /**
  //   * 抽出したデータを返します。
  //   * 
  //   * @return xmlTree.getSelection()[0].getData()
  //   */
  //  public Object getSelectedData() {
  //    if (this.xmlTree.getSelectionCount() == 0) {
  //      return null;
  //    } 
  //    
  //    if (this.xmlTree.getSelection()[0].getData() instanceof Group) {
  //      return null;
  //    }
  //    
  //    return this.xmlTree.getSelection()[0].getData();
  //  }

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