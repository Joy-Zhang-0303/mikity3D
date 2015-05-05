package org.mklab.mikity.view.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
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
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
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
import org.mklab.mikity.view.gui.dialog.EditPrimitiveDialog;
import org.mklab.mikity.view.gui.dialog.EditQuadPolygonDialog;
import org.mklab.mikity.view.gui.dialog.EditTrianglePolygonDialog;
import org.mklab.mikity.view.gui.dialog.GroupConfigWithCoordinateParameterDialog;
import org.mklab.mikity.view.gui.dialog.GroupConfigWithDHParameterDialog;
import org.mklab.mikity.view.gui.dnd.DragAndDropEnabler;


/**
 * シーングラフを表すクラスです。
 * 
 * ツリーの読み込み、書き込みを行います。
 * 
 * Displays a single-selection tree, a multi-selection tree, and a checkbox tree
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
  /** */
  Object targetObj = null;
  /** */
  Group targetGroup = null;
  /** */
  Group targetParentGroup = null;
  /** */
  Group root = null;
  /** */
  boolean editable = true;
  /** */
  AbstractModeler modeler;
  /** */
  boolean usedDHParameter = false;
  /** */
  boolean usedCoordinateParameter = false;

  /**
   * コンストラクター
   */
  public SceneGraphTree() {
    // nothing to do
  }

  /**
   * 新しく生成された<code>SceneGraphTree</code>オブジェクトを初期化します。
   * @param composite コンポジット
   * @param modeler モデラー
   * @param model モデル
   */
  public SceneGraphTree(final Composite composite, final AbstractModeler modeler, final Mikity3dModel model) {
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
    final GridData data1 = new GridData(GridData.FILL_BOTH);
    this.xmlTree = new Tree(composite, SWT.SINGLE | SWT.BORDER);

    final GridData data2 = new GridData(GridData.FILL_BOTH);
    this.xmlTree.setLayoutData(data2);

    fillTree();

    new DragAndDropEnabler(this.xmlTree);

    this.xmlTree.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseDoubleClick(MouseEvent arg0) {
        // ダブルクリック
        SceneGraphTree.this.editable = true;

        final Object doubleClickObj = SceneGraphTree.this.xmlTree.getSelection()[0].getData();
        if (doubleClickObj == null) {
          return;
        } else if (doubleClickObj instanceof Group) {
          if (SceneGraphTree.this.xmlTree.getSelection()[0].getText().startsWith("root")) { //$NON-NLS-1$
            // 選択されたものがrootであるとき
            SceneGraphTree.this.editable = false;
          }
          checkUsedLinkType(SceneGraphTree.this.model.getGroups());

          if (SceneGraphTree.this.usedDHParameter) {
            final GroupConfigWithDHParameterDialog groupConf = new GroupConfigWithDHParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else if (SceneGraphTree.this.usedCoordinateParameter) {
            final GroupConfigWithCoordinateParameterDialog groupConf = new GroupConfigWithCoordinateParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else {
            final MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
            mesBox.setMessage(Messages.getString("SceneGraphTree.1")); //$NON-NLS-1$
            mesBox.setText(Messages.getString("SceneGraphTree.2")); //$NON-NLS-1$
            int result = mesBox.open();
            if (result == SWT.YES) {
              GroupConfigWithDHParameterDialog groupConf = new GroupConfigWithDHParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            } else if (result == SWT.NO) {
              final GroupConfigWithCoordinateParameterDialog groupConf = new GroupConfigWithCoordinateParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            }
          }
          setTree();
        } else if (doubleClickObj instanceof XMLTrianglePolygon) {
          final EditTrianglePolygonDialog editPoly = new EditTrianglePolygonDialog(composite.getShell(), (XMLTrianglePolygon)doubleClickObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else if (doubleClickObj instanceof XMLQuadPolygon) {
          final EditQuadPolygonDialog editPoly = new EditQuadPolygonDialog(composite.getShell(), (XMLQuadPolygon)doubleClickObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else {
          final EditPrimitiveDialog editPrim = new EditPrimitiveDialog(composite.getShell(), doubleClickObj, SceneGraphTree.this.targetGroup);
          editPrim.open();
          setTree();
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

    final Menu popup = new Menu(composite.getShell(), SWT.POP_UP);
    this.xmlTree.setMenu(popup);

    final MenuItem addModel = new MenuItem(popup, SWT.CASCADE);
    addModel.setText(Messages.getString("SceneGraphTree.3")); //$NON-NLS-1$

    final Menu modelingSub = new Menu(popup);
    addModel.setMenu(modelingSub);

    final MenuItem addPrimitive = new MenuItem(modelingSub, SWT.POP_UP);
    addPrimitive.setText(Messages.getString("SceneGraphTree.4")); //$NON-NLS-1$

    final MenuItem addTrianglePolygon = new MenuItem(modelingSub, SWT.POP_UP);
    addTrianglePolygon.setText(Messages.getString("SceneGraphTree.5")); //$NON-NLS-1$

    final MenuItem addQuadPolygon = new MenuItem(modelingSub, SWT.POP_UP);
    addQuadPolygon.setText(Messages.getString("SceneGraphTree.6")); //$NON-NLS-1$

    final MenuItem addGroup = new MenuItem(popup, SWT.POP_UP);
    addGroup.setText(Messages.getString("SceneGraphTree.7")); //$NON-NLS-1$

    final MenuItem edit = new MenuItem(popup, SWT.POP_UP);
    edit.setText(Messages.getString("SceneGraphTree.12")); //$NON-NLS-1$

    final MenuItem delete = new MenuItem(popup, SWT.POP_UP);
    delete.setText(Messages.getString("SceneGraphTree.13")); //$NON-NLS-1$

    addPrimitive.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddPrimitiveDialog dialog = new AddPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        dialog.open();
        setTree();
      }
    });

    addTrianglePolygon.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddTrianglePolygonDialog dialog = new AddTrianglePolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        dialog.open();
        setTree();
      }
    });

    addQuadPolygon.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddQuadPolygonDialog dialog = new AddQuadPolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        dialog.open();
        setTree();
      }
    });

    addGroup.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddGroupDialog dialog = new AddGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        dialog.open();
        setTree();
      }
    });

    edit.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        // if (targetObj == null) {
        if (SceneGraphTree.this.targetObj instanceof Group) {
          checkUsedLinkType(SceneGraphTree.this.model.getGroups());

          if (SceneGraphTree.this.usedDHParameter) {
            final GroupConfigWithDHParameterDialog dialog = new GroupConfigWithDHParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            dialog.open();
          } else if (SceneGraphTree.this.usedCoordinateParameter) {
            final GroupConfigWithCoordinateParameterDialog dialog = new GroupConfigWithCoordinateParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            dialog.open();
          } else {
            final MessageBox message = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
            message.setMessage(Messages.getString("SceneGraphTree.14")); //$NON-NLS-1$
            message.setText(Messages.getString("SceneGraphTree.15")); //$NON-NLS-1$
            int result = message.open();
            if (result == SWT.YES) {
              final GroupConfigWithDHParameterDialog dialog = new GroupConfigWithDHParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              dialog.open();
              setTree();
            } else if (result == SWT.NO) {
              final GroupConfigWithCoordinateParameterDialog dialog = new GroupConfigWithCoordinateParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              dialog.open();
              setTree();
            }
          }
        } else if (SceneGraphTree.this.targetObj instanceof XMLTrianglePolygon) {
          final EditTrianglePolygonDialog dialog = new EditTrianglePolygonDialog(composite.getShell(), (XMLTrianglePolygon)SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          dialog.open();
          setTree();
        } else if (SceneGraphTree.this.targetObj instanceof XMLQuadPolygon) {
          final EditQuadPolygonDialog dialog = new EditQuadPolygonDialog(composite.getShell(), (XMLQuadPolygon)SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          dialog.open();
          setTree();
        } else {
          final EditPrimitiveDialog dialog = new EditPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          dialog.open();
          setTree();
        }
      }
    });

    delete.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObj instanceof Group) {
          if (SceneGraphTree.this.targetParentGroup == null) {
            final MessageBox message = new MessageBox(composite.getShell(), SWT.ICON_INFORMATION);
            message.setText(Messages.getString("SceneGraphTree.16")); //$NON-NLS-1$
            message.setMessage(Messages.getString("SceneGraphTree.17")); //$NON-NLS-1$
            message.open();
          } else {
            removeObject(SceneGraphTree.this.targetParentGroup, SceneGraphTree.this.targetGroup);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        } else if (SceneGraphTree.this.targetObj instanceof XMLTrianglePolygon || SceneGraphTree.this.targetObj instanceof XMLQuadPolygon) {
          final MessageBox message = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("SceneGraphTree.18")); //$NON-NLS-1$
          message.setText(Messages.getString("SceneGraphTree.19")); //$NON-NLS-1$
          int result = message.open();
          if (result == SWT.YES) {
            removeObject(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObj);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        } else {

          final MessageBox message = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          message.setMessage(Messages.getString("SceneGraphTree.20")); //$NON-NLS-1$
          message.setText(Messages.getString("SceneGraphTree.21")); //$NON-NLS-1$
          int result = message.open();
          if (result == SWT.YES) {
            removeObject(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObj);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        }
      }
    });
  }

  /**
   * 現在選択されているObjectを設定する。
   */
  void setSelectedObjectAsTarget() {
    this.targetObj = null;
    this.targetGroup = null;
    
    this.targetParentGroup = null;
    if (this.xmlTree.getSelectionCount() == 0) {
      // 何も選択されていないとき
      setAllTransparent(this.model.getGroup(1), true);
      return;
    }
    
    this.targetObj = this.xmlTree.getSelection()[0].getData();
    if (this.targetObj instanceof Group) {
      this.targetGroup = (Group)this.targetObj;
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
      setTarget(this.targetObj);
    }
    this.modeler.createViewer();
    // setTree();
  }

  /**
   * ターゲットを設定します。
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
   * 抽出したグループを返します。
   * 
   * @return targetGroup
   */
  public Group getSelectionGroup() {
    return this.targetGroup;
  }

  /**
   * グループが編集可能か判定する。
   * 
   * @return editable 編集可能ならばtrue
   */
  public boolean getGroupEditable() {
    return this.editable;
  }

  /**
   * ツリーとビューアを再描画します。
   */
  void setTree() {
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
    // Groupの長さだけ繰り返す
    for (int i = 0; i < groups.length; i++) {
      // 子となるTreeItem、childに名前をつけて接続
      TreeItem child = null;
      if (item == null) {
        child = new TreeItem(this.xmlTree, SWT.NONE);
        child.setText("rootGroup : " + groups[i].getName()); //$NON-NLS-1$
      } else {
        child = new TreeItem(item, SWT.NONE);
        child.setText("Group : " + groups[i].getName()); //$NON-NLS-1$
      }
      child.setData(groups[i]);

      final XMLBox[] boxes = groups[i].getXMLBoxes();
      for (int j = 0; j < boxes.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("box ( " + boxes[j].getXsize() + ", " + boxes[j].getYsize() + ", " + boxes[j].getZsize() + ", " + boxes[j].getColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(boxes[j]);
      }

      final XMLCylinder[] cylinders = groups[i].getXMLCylinders();
      for (int j = 0; j < cylinders.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Cylinder ( " + cylinders[j].getRadius() + ", " + cylinders[j].getHeight() + ", " + cylinders[j].getDiv() + ", " + cylinders[j].getColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cylinders[j]);
      }

      final XMLSphere[] spheres = groups[i].getXMLSpheres();
      for (int j = 0; j < spheres.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Sphere ( " + spheres[j].getRadius() + ", " + spheres[j].getDiv() + ", " + spheres[j].getColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        boxChild.setData(spheres[j]);
      }

      final XMLCone[] cones = groups[i].getXMLCones();
      for (int j = 0; j < cones.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Cone ( " + cones[j].getRadisu() + ", " + cones[j].getHeight() + ", " + cones[j].getDiv() + ", " + cones[j].getColor() + " )"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cones[j]);
      }
      
      final XMLTrianglePolygon[] trianglePolygons = groups[i].getXMLTrianglePolygons();
      for (int j = 0; j < trianglePolygons.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Triangle ( " + trianglePolygons[j].getColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$
        boxChild.setData(trianglePolygons[j]);
      }

      final XMLQuadPolygon[] quadPolygons = groups[i].getXMLQuadPolygons();
      for (int j = 0; j < quadPolygons.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Quad ( " + quadPolygons[j].getColor() + " )"); //$NON-NLS-1$//$NON-NLS-2$
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
   * Helper method to fill a tree with data シーングラフツリーにプリミティブのデータを追加させる。
   */
  public void fillTree() {
    clearTree();
    // addTreeItemメソッドに引数rootItemとgroupを渡す
    addTreeItem(null, this.model.getGroups());
  }

  /**
   * 抽出したデータを返します。
   * 
   * @return xmlTree.getSelection()[0].getData()
   */
  public Object getSelectionData() {
    if (this.xmlTree.getSelectionCount() == 0) {
      return null;
    } else if (this.xmlTree.getSelection()[0].getData() instanceof Group) {
      return null;
    }
    return this.xmlTree.getSelection()[0].getData();
  }

  /**
   * XMLのモデルデータを返します。
   * 
   * @return XMLのモデルデータ
   */
  public Mikity3dModel getModel() {
    return this.model;
  }

//  /**
//   * グループを消去する。
//   */
//  public void clearGroups() {
//    this.model.clearGroups();
//    Group group = new Group();
//    this.model.addGroup(group);
//    fillTree();
//  }

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
   * 現在ツリーのリンク関係にDHパラメータが使われているか判定します。
   * 
   * @param groups グループリスト
   */
  void checkUsedLinkType(Group[] groups) {
    if (groups.length == 0) {
      return;
    }
    
    if (this.usedDHParameter && this.usedCoordinateParameter) {
      return;
    }
      
    for (int i = 0; i < groups.length; i++) {
      final LinkData[] links = groups[i].getLinkData();
      for (int j = 0; j < links.length; j++) {
        if (links[j].hasDHParameter()) {
          this.usedDHParameter = true;
          break;
        } else if (links[j].hasCoordinateParameter()) {
          this.usedCoordinateParameter = true;
          break;
        }
      }
      checkUsedLinkType(groups[i].getGroups());
    }
  }
}