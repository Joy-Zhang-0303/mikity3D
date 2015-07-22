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
import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.view.gui.dialog.AddGroupDialog;
import org.mklab.mikity.view.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.view.gui.dialog.AddQuadPolygonDialog;
import org.mklab.mikity.view.gui.dialog.AddTrianglePolygonDialog;
import org.mklab.mikity.view.gui.dialog.EditGroupDialog;
import org.mklab.mikity.view.gui.dialog.EditPrimitiveDialog;
import org.mklab.mikity.view.gui.dialog.EditQuadPolygonDialog;
import org.mklab.mikity.view.gui.dialog.EditTrianglePolygonDialog;
import org.mklab.mikity.view.gui.dnd.DragAndDropEnabler;


/**
 * シーングラフを表すクラスです。
 */
public class SceneGraphTree {

  /** */
  Tree xmlTree;
  /** */
  Mikity3dModel model;
  /** */
  Composite comp;
  /** */
  TreeItem selectItem = null;
  /** 選択されているオブジェクト。 */
  Object targetObject = null;
  /** 選択されているグループ。 */
  Group targetGroup = null;
  /** 選択されている物の親グループ。 */
  Group targetParentGroup = null;
  /** */
  Group root = null;
  /** */
  boolean editable = true;
  /** */
  JoglModeler modeler;

  /** オブジェクトを修正中ならばtrue */
  static boolean isModifyingObject = false;

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
  public SceneGraphTree(final Composite composite, final JoglModeler modeler, final Mikity3dModel model) {
    this.model = model;
    this.modeler = modeler;
    // ファイルの読み込みを行う
    createTree(composite);
    this.comp = composite;
  }

  /**
   * ツリーを生成する
   * 
   * @param composite
   */
  private void createTree(final Composite composite) {
    composite.setLayout(new GridLayout(1, true));
    //final GridData data1 = new GridData(GridData.FILL_BOTH);
    this.xmlTree = new Tree(composite, SWT.SINGLE | SWT.BORDER);
    final GridData data2 = new GridData(GridData.FILL_BOTH);
    this.xmlTree.setLayoutData(data2);

    fillTree();

    new DragAndDropEnabler(this.xmlTree);

    this.xmlTree.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseDoubleClick(MouseEvent arg0) {
        SceneGraphTree.this.editable = true;

        final Object clickObject = SceneGraphTree.this.xmlTree.getSelection()[0].getData();

        if (SceneGraphTree.isModifyingObject) {
          return;
        }
        if (clickObject == null) {
          return;
        }

        if (clickObject instanceof Group) {
          final EditGroupDialog dialog = new EditGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable, SceneGraphTree.this, SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else if (clickObject instanceof XMLTrianglePolygon) {
          final EditTrianglePolygonDialog dialog = new EditTrianglePolygonDialog(composite.getShell(), (XMLTrianglePolygon)clickObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this,
              SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else if (clickObject instanceof XMLQuadPolygon) {
          final EditQuadPolygonDialog dialog = new EditQuadPolygonDialog(composite.getShell(), (XMLQuadPolygon)clickObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this,
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

    this.xmlTree.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        setSelectedObjectAsTarget();
      }
    });

    this.xmlTree.addKeyListener(new KeyAdapter() {

      @Override
      public void keyPressed(KeyEvent arg0) {
        if (arg0.keyCode == SWT.DEL) {
          SceneGraphTree.this.selectItem = SceneGraphTree.this.xmlTree.getSelection()[0];
          final TreeItem groupNode = SceneGraphTree.this.selectItem.getParentItem();
          // groupNodeがnullならrootノードなので削除不可能
          if (groupNode == null) {
            return;
          }
          final Object primitive = SceneGraphTree.this.selectItem.getData();
          final Group group = (Group)groupNode.getData();
          if (removeObject(group, primitive)) {
            // 中身が消されれば、表示も削除
            SceneGraphTree.this.selectItem.dispose();
          }
          SceneGraphTree.this.modeler.createViewer();
        }
      }
    });

    createPopupMenu(composite);
  }

  /**
   * ポップアップメニューを生成します。
   * 
   * @param composite コンポジット
   */
  private void createPopupMenu(final Composite composite) {
    final Menu menu = new Menu(composite.getShell(), SWT.POP_UP);
    this.xmlTree.setMenu(menu);

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
        if (SceneGraphTree.this.targetObject instanceof Group) {
          final EditGroupDialog dialog = new EditGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable, SceneGraphTree.this, SceneGraphTree.this.modeler);
          dialog.open();
        } else if (SceneGraphTree.this.targetObject instanceof XMLTrianglePolygon) {
          final EditTrianglePolygonDialog dialog = new EditTrianglePolygonDialog(composite.getShell(), (XMLTrianglePolygon)SceneGraphTree.this.targetObject, SceneGraphTree.this.targetGroup,
              SceneGraphTree.this, SceneGraphTree.this.modeler);
          dialog.open();
          updateTree();
        } else if (SceneGraphTree.this.targetObject instanceof XMLQuadPolygon) {
          final EditQuadPolygonDialog dialog = new EditQuadPolygonDialog(composite.getShell(), (XMLQuadPolygon)SceneGraphTree.this.targetObject, SceneGraphTree.this.targetGroup, SceneGraphTree.this,
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

    delete.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObject instanceof Group) {
          if (SceneGraphTree.this.targetParentGroup == null) {
            final MessageBox message = new MessageBox(composite.getShell(), SWT.ICON_INFORMATION);
            message.setText(Messages.getString("SceneGraphTree.16")); //$NON-NLS-1$
            message.setMessage(Messages.getString("SceneGraphTree.17")); //$NON-NLS-1$
            message.open();
          } else {
            removeObject(SceneGraphTree.this.targetParentGroup, SceneGraphTree.this.targetGroup);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            updateTree();
          }
        } else if (SceneGraphTree.this.targetObject instanceof XMLTrianglePolygon || SceneGraphTree.this.targetObject instanceof XMLQuadPolygon) {
          final MessageBox message = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("SceneGraphTree.18")); //$NON-NLS-1$
          message.setText(Messages.getString("SceneGraphTree.19")); //$NON-NLS-1$
          int result = message.open();
          if (result == SWT.YES) {
            removeObject(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObject);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            updateTree();
          }
        } else {
          final MessageBox message = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("SceneGraphTree.20")); //$NON-NLS-1$
          message.setText(Messages.getString("SceneGraphTree.21")); //$NON-NLS-1$
          int result = message.open();
          if (result == SWT.YES) {
            removeObject(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObject);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            updateTree();
          }
        }
      }
    });
    
    menu.addMenuListener(new MenuAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void menuShown(MenuEvent e) {
        final Object clickedObject = SceneGraphTree.this.xmlTree.getSelection()[0].getData();
        
        if (clickedObject instanceof Group) {
          addBox.setEnabled(true);
          addCylinder.setEnabled(true);
          addSphere.setEnabled(true);
          addCone.setEnabled(true);
          addTrianglePolygon.setEnabled(true);
          addQuadPolygon.setEnabled(true);
          addGroup.setEnabled(true);
          edit.setEnabled(true);
          delete.setEnabled(true);
        } else {
          addBox.setEnabled(false);
          addCylinder.setEnabled(false);
          addSphere.setEnabled(false);
          addCone.setEnabled(false);
          addTrianglePolygon.setEnabled(false);
          addQuadPolygon.setEnabled(false);
          addGroup.setEnabled(false);
          edit.setEnabled(true);
          delete.setEnabled(true);
        }

      }
    });
  }

  /**
   * 選択されているオブジェクトを設定します。
   */
  void setSelectedObjectAsTarget() {
    this.targetObject = null;
    this.targetGroup = null;

    this.targetParentGroup = null;
    if (this.xmlTree.getSelectionCount() == 0) {
      // 何も選択されていないとき
      setAllTransparent(this.model.getGroup(1), true);
      return;
    }

    this.targetObject = this.xmlTree.getSelection()[0].getData();
    if (this.targetObject instanceof Group) {
      this.targetGroup = (Group)this.targetObject;
      // targetObj = null;
      setTarget(this.targetGroup);
      if (this.xmlTree.getSelection()[0].getText().startsWith("root")) { //$NON-NLS-1$
        // 選択されたものがrootであるとき
        this.editable = false;
        this.targetParentGroup = null;
      } else {
        this.editable = true;
        // 選択されたグループがルートグループでなければparentGroupを登録
        this.targetParentGroup = (Group)this.xmlTree.getSelection()[0].getParentItem().getData();
      }
    } else {
      // 選択されたものがプリミティブのとき
      this.editable = true;
      this.targetGroup = (Group)this.xmlTree.getSelection()[0].getParentItem().getData();
      this.targetParentGroup = null;
      setTarget(this.targetObject);
    }
    this.modeler.createViewer();
  }

  /**
   * 選択されているオブジェクトを設定します。
   * 
   * @param object
   */
  private void setTarget(Object object) {
    setAllTransparent(this.model.getGroup(0), true);

    if (object instanceof XMLBox) {
      ((XMLBox)object).setTransparent(false);
    } else if (object instanceof XMLCone) {
      ((XMLCone)object).setTransparent(false);
    } else if (object instanceof XMLCylinder) {
      ((XMLCylinder)object).setTransparent(false);
    } else if (object instanceof XMLSphere) {
      ((XMLSphere)object).setTransparent(false);
    } else if (object instanceof XMLTrianglePolygon) {
      ((XMLTrianglePolygon)object).setTransparent(false);
    } else if (object instanceof XMLQuadPolygon) {
      ((XMLQuadPolygon)object).setTransparent(false);
    } else if (object instanceof Group) {
      Group group = (Group)object;
      final XMLBox[] boxes = group.getXMLBoxes();
      for (int i = 0; i < boxes.length; i++) {
        boxes[i].setTransparent(false);
      }
      final XMLCylinder[] cylinders = group.getXMLCylinders();
      for (int i = 0; i < cylinders.length; i++) {
        cylinders[i].setTransparent(false);
      }
      final XMLSphere[] spheres = group.getXMLSpheres();
      for (int i = 0; i < spheres.length; i++) {
        spheres[i].setTransparent(false);
      }
      final XMLCone[] cones = group.getXMLCones();
      for (int i = 0; i < cones.length; i++) {
        cones[i].setTransparent(false);
      }
      final XMLTrianglePolygon[] trianglePolygons = group.getXMLTrianglePolygons();
      for (int i = 0; i < trianglePolygons.length; i++) {
        trianglePolygons[i].setTransparent(false);
      }
      final XMLQuadPolygon[] quadPolygons = group.getXMLQuadPolygons();
      for (int i = 0; i < quadPolygons.length; i++) {
        quadPolygons[i].setTransparent(false);
      }
    }
  }

  /**
   * 全透過性を設定する。
   * 
   * @param group グループ
   * @param transparent トランスピアレント
   */
  public void setAllTransparent(final Group group, boolean transparent) {
    final XMLBox[] boxes = group.getXMLBoxes();
    final XMLCylinder[] cylinders = group.getXMLCylinders();
    final XMLSphere[] spheres = group.getXMLSpheres();
    final XMLCone[] cones = group.getXMLCones();
    final XMLTrianglePolygon[] trianglePolygons = group.getXMLTrianglePolygons();
    final XMLQuadPolygon[] quadPolygons = group.getXMLQuadPolygons();

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

    Group[] groups = group.getGroups();

    for (int i = 0; i < groups.length; i++) {
      setAllTransparent(groups[i], transparent);
    }
  }

  /**
   * 選択されているグループを返します。
   * 
   * @return 選択されているグループ
   */
  public Group getTargetGroup() {
    return this.targetGroup;
  }

  /**
   * グループが編集可能か判定する。
   * 
   * @return editable 編集可能ならばtrue
   */
  public boolean isGroupEditable() {
    return this.editable;
  }

  /**
   * ツリーとビューアを更新します。
   */
  public void updateTree() {
    fillTree();
    this.modeler.createViewer();
  }

  /**
   * グループからPrimitiveを削除します。
   * 
   * @param group グループ
   * @param primitive プリミティブ
   * 
   * @return ノードを削除したかどうか。（削除したとき:true,削除されなかったとき:false）
   */
  protected boolean removeObject(Group group, Object primitive) {
    if (primitive instanceof XMLBox) {
      group.removeXMLBox((XMLBox)primitive);
    } else if (primitive instanceof XMLCone) {
      group.removeXMLCone((XMLCone)primitive);
    } else if (primitive instanceof XMLCylinder) {
      group.removeXMLCylinder((XMLCylinder)primitive);
    } else if (primitive instanceof XMLSphere) {
      group.removeXMLSphere((XMLSphere)primitive);
    } else if (primitive instanceof XMLTrianglePolygon) {
      group.removeXMLTrianglePolygon((XMLTrianglePolygon)primitive);
    } else if (primitive instanceof XMLQuadPolygon) {
      group.removeXMLQuadPolygon((XMLQuadPolygon)primitive);
    } else if (primitive instanceof Group) {
      MessageBox box = new MessageBox(this.comp.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
      box.setMessage(Messages.getString("SceneGraphTree.29")); //$NON-NLS-1$
      box.setText(Messages.getString("SceneGraphTree.30")); //$NON-NLS-1$
      int result = box.open();
      if (result == SWT.NO) {
        return false;
      }
      group.removeGroup((Group)primitive);
    }
    return true;
  }

  /**
   * 
   * @param item
   * @param groups
   */
  private void addTreeItem(TreeItem item, Group[] groups) {
    for (int i = 0; i < groups.length; i++) {
      // 子となるTreeItem、childに名前をつけて接続
      TreeItem child = null;
      if (item == null) {
        child = new TreeItem(this.xmlTree, SWT.NONE);
        child.setText("rootGroup : " + groups[i].getName()); //$NON-NLS-1$
      } else {
        child = new TreeItem(item, SWT.NONE);
        child.setText("group : " + groups[i].getName()); //$NON-NLS-1$
      }
      child.setData(groups[i]);

      final XMLBox[] boxes = groups[i].getXMLBoxes();
      for (int j = 0; j < boxes.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("box (" + boxes[j].getWidth() + ", " + boxes[j].getHeight() + ", " + boxes[j].getDepth() + ", " + boxes[j].getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(boxes[j]);
      }

      final XMLCylinder[] cylinders = groups[i].getXMLCylinders();
      for (int j = 0; j < cylinders.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("cylinder (" + cylinders[j].getRadius() + ", " + cylinders[j].getHeight() + ", " + cylinders[j].getDivision() + ", " + cylinders[j].getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cylinders[j]);
      }

      final XMLSphere[] spheres = groups[i].getXMLSpheres();
      for (int j = 0; j < spheres.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("sphere (" + spheres[j].getRadius() + ", " + spheres[j].getDivision() + ", " + spheres[j].getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        boxChild.setData(spheres[j]);
      }

      final XMLCone[] cones = groups[i].getXMLCones();
      for (int j = 0; j < cones.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("cone (" + cones[j].getRadisu() + ", " + cones[j].getHeight() + ", " + cones[j].getDivision() + ", " + cones[j].getColor() + ")"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cones[j]);
      }

      final XMLTrianglePolygon[] trianglePolygons = groups[i].getXMLTrianglePolygons();
      for (int j = 0; j < trianglePolygons.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("trianglePolygon (" + trianglePolygons[j].getColor() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        boxChild.setData(trianglePolygons[j]);
      }

      final XMLQuadPolygon[] quadPolygons = groups[i].getXMLQuadPolygons();
      for (int j = 0; j < quadPolygons.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("quadPolygon (" + quadPolygons[j].getColor() + ")"); //$NON-NLS-1$//$NON-NLS-2$
        boxChild.setData(quadPolygons[j]);
      }

      if (item != null) {
        item.setExpanded(true);
      }
      final Group[] childGroups = groups[i].getGroups();
      addTreeItem(child, childGroups);
    }
    if (item != null) {
      item.setExpanded(true);
    }
  }

  /**
   * シーングラフツリーにプリミティブのデータを追加します。
   */
  public void fillTree() {
    clearTree();
    addTreeItem(null, this.model.getGroups());
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
   * XMLのモデルデータを返します。
   * 
   * @return XMLのモデルデータ
   */
  public Mikity3dModel getModel() {
    return this.model;
  }

  /**
   * ツリーを消去します。
   */
  public void clearTree() {
    if (this.xmlTree.getItemCount() == 0) {
      return;
    }

    final TreeItem rootNode = this.xmlTree.getItems()[0];

    final TreeItem[] items = rootNode.getItems();
    for (int i = 0; i < items.length; i++) {
      items[i].dispose();
    }
    rootNode.dispose();
  }

  /**
   * モデルを設定します。
   * 
   * @param model モデル
   */
  public void setModel(Mikity3dModel model) {
    this.model = model;
    fillTree();
  }

  /**
   * @return XML tree
   */
  public Tree getXMLTree() {
    return this.xmlTree;
  }

  /**
   * オブジェクトを修正中であるか設定します。
   * 
   * @param isModifyingObject オブジェクトを修正中ならばtrue
   */
  public static void setIsModifyingObject(boolean isModifyingObject) {
    SceneGraphTree.isModifyingObject = isModifyingObject;
  }
}