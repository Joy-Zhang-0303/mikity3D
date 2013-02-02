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
import org.mklab.mikity.model.xml.JamastModel;
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.LinkData;
import org.mklab.mikity.model.xml.model.XMLBox;
import org.mklab.mikity.model.xml.model.XMLCone;
import org.mklab.mikity.model.xml.model.XMLCylinder;
import org.mklab.mikity.model.xml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.model.XMLSphere;
import org.mklab.mikity.model.xml.model.XMLTrianglePolygon;
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
  JamastModel model;
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
   * コンストラクター
   * 
   * @param composite コンポジット
   * @param modeler モデラー
   * @param model モデル
   */
  public SceneGraphTree(final Composite composite, final AbstractModeler modeler, final JamastModel model) {
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
          checkUsedLinkType(SceneGraphTree.this.model.loadGroup());

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
        setObj();
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
          final Object prim = SceneGraphTree.this.selectItem.getData();
          final Group g = (Group)groupNode.getData();
          if (removeObj(g, prim)) {
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

    final MenuItem addPrim = new MenuItem(modelingSub, SWT.POP_UP);
    addPrim.setText(Messages.getString("SceneGraphTree.4")); //$NON-NLS-1$

    final MenuItem addTriPoly = new MenuItem(modelingSub, SWT.POP_UP);
    addTriPoly.setText(Messages.getString("SceneGraphTree.5")); //$NON-NLS-1$

    final MenuItem addQuadPoly = new MenuItem(modelingSub, SWT.POP_UP);
    addQuadPoly.setText(Messages.getString("SceneGraphTree.6")); //$NON-NLS-1$

    final MenuItem addGroup = new MenuItem(popup, SWT.POP_UP);
    addGroup.setText(Messages.getString("SceneGraphTree.7")); //$NON-NLS-1$

    final MenuItem edit = new MenuItem(popup, SWT.POP_UP);
    edit.setText(Messages.getString("SceneGraphTree.12")); //$NON-NLS-1$

    final MenuItem delete = new MenuItem(popup, SWT.POP_UP);
    delete.setText(Messages.getString("SceneGraphTree.13")); //$NON-NLS-1$

    addPrim.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddPrimitiveDialog localAddPrim = new AddPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        localAddPrim.open();
        setTree();
      }
    });

    addTriPoly.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddTrianglePolygonDialog localAddTriPoly = new AddTrianglePolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        localAddTriPoly.open();
        setTree();
      }
    });

    addQuadPoly.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddQuadPolygonDialog localAddQuadPoly = new AddQuadPolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        localAddQuadPoly.open();
        setTree();
      }
    });

    addGroup.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        final AddGroupDialog localAddGroup = new AddGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        localAddGroup.open();
        setTree();
      }
    });

    edit.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        // if (targetObj == null) {
        if (SceneGraphTree.this.targetObj instanceof Group) {
          checkUsedLinkType(SceneGraphTree.this.model.loadGroup());

          if (SceneGraphTree.this.usedDHParameter) {
            final GroupConfigWithDHParameterDialog groupConf = new GroupConfigWithDHParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else if (SceneGraphTree.this.usedCoordinateParameter) {
            final GroupConfigWithCoordinateParameterDialog groupConf = new GroupConfigWithCoordinateParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else {
            final MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
            mesBox.setMessage(Messages.getString("SceneGraphTree.14")); //$NON-NLS-1$
            mesBox.setText(Messages.getString("SceneGraphTree.15")); //$NON-NLS-1$
            int result = mesBox.open();
            if (result == SWT.YES) {
              final GroupConfigWithDHParameterDialog groupConf = new GroupConfigWithDHParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            } else if (result == SWT.NO) {
              final GroupConfigWithCoordinateParameterDialog groupConf = new GroupConfigWithCoordinateParameterDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            }
          }
        } else if (SceneGraphTree.this.targetObj instanceof XMLTrianglePolygon) {
          final EditTrianglePolygonDialog editPoly = new EditTrianglePolygonDialog(composite.getShell(), (XMLTrianglePolygon)SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else if (SceneGraphTree.this.targetObj instanceof XMLQuadPolygon) {
          final EditQuadPolygonDialog editPoly = new EditQuadPolygonDialog(composite.getShell(), (XMLQuadPolygon)SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else {
          final EditPrimitiveDialog editPrim = new EditPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          editPrim.open();
          setTree();
        }
      }
    });

    delete.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObj instanceof Group) {
          if (SceneGraphTree.this.targetParentGroup == null) {
            final MessageBox msg = new MessageBox(composite.getShell(), SWT.ICON_INFORMATION);
            msg.setText(Messages.getString("SceneGraphTree.16")); //$NON-NLS-1$
            msg.setMessage(Messages.getString("SceneGraphTree.17")); //$NON-NLS-1$
            msg.open();
          } else {
            removeObj(SceneGraphTree.this.targetParentGroup, SceneGraphTree.this.targetGroup);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        } else if (SceneGraphTree.this.targetObj instanceof XMLTrianglePolygon || SceneGraphTree.this.targetObj instanceof XMLQuadPolygon) {
          final MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage(Messages.getString("SceneGraphTree.18")); //$NON-NLS-1$
          mesBox.setText(Messages.getString("SceneGraphTree.19")); //$NON-NLS-1$
          int result = mesBox.open();
          if (result == SWT.YES) {
            removeObj(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObj);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        } else {

          final MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage(Messages.getString("SceneGraphTree.20")); //$NON-NLS-1$
          mesBox.setText(Messages.getString("SceneGraphTree.21")); //$NON-NLS-1$
          int result = mesBox.open();
          if (result == SWT.YES) {
            removeObj(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObj);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        }
      }
    });
  }

  /**
   * 現在選択されているObjを設定する。
   */
  void setObj() {
    this.targetObj = null;
    this.targetGroup = null;
    
    this.targetParentGroup = null;
    if (this.xmlTree.getSelectionCount() == 0) {
      // 何も選択されていないとき
      setAllTransparent(this.model.loadGroup(1), true);
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
   * ターゲットを指定
   * 
   * @param obj
   */
  private void setTarget(Object obj) {
    setAllTransparent(this.model.loadGroup(0), true);

    if (obj instanceof XMLBox) {
      ((XMLBox)obj).setTransparent(false);
    } else if (obj instanceof XMLCone) {
      ((XMLCone)obj).setTransparent(false);
    } else if (obj instanceof XMLCylinder) {
      ((XMLCylinder)obj).setTransparent(false);
    } else if (obj instanceof XMLSphere) {
      ((XMLSphere)obj).setTransparent(false);
    } else if (obj instanceof XMLTrianglePolygon) {
      ((XMLTrianglePolygon)obj).setTransparent(false);
    } else if (obj instanceof XMLQuadPolygon) {
      ((XMLQuadPolygon)obj).setTransparent(false);
    } else if (obj instanceof Group) {
      Group group = (Group)obj;
      final XMLBox[] xmlBox = group.getXMLBox();
      for (int i = 0; i < xmlBox.length; i++) {
        xmlBox[i].setTransparent(false);
      }
      final XMLCylinder[] xmlCylinder = group.getXMLCylinder();
      for (int i = 0; i < xmlCylinder.length; i++) {
        xmlCylinder[i].setTransparent(false);
      }
      final XMLSphere[] xmlSphere = group.getXMLSphere();
      for (int i = 0; i < xmlSphere.length; i++) {
        xmlSphere[i].setTransparent(false);
      }
      final XMLCone[] xmlCone = group.getXMLCone();
      for (int i = 0; i < xmlCone.length; i++) {
        xmlCone[i].setTransparent(false);
      }
      final XMLTrianglePolygon[] xmlTriangle = group.getXMLTrianglePolygon();
      for (int i = 0; i < xmlTriangle.length; i++) {
        xmlTriangle[i].setTransparent(false);
      }
      final XMLQuadPolygon[] xmlQuad = group.getXMLQuadPolygon();
      for (int i = 0; i < xmlQuad.length; i++) {
        xmlQuad[i].setTransparent(false);
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
    final XMLBox[] xmlBox = group.getXMLBox();
    final XMLCylinder[] xmlCylinder = group.getXMLCylinder();
    final XMLSphere[] xmlSphere = group.getXMLSphere();
    final XMLCone[] xmlCone = group.getXMLCone();
    final XMLTrianglePolygon[] xmlTriangle = group.getXMLTrianglePolygon();
    final XMLQuadPolygon[] xmlQuad = group.getXMLQuadPolygon();

    for (int i = 0; i < xmlBox.length; i++) {
      if (transparent) {
        xmlBox[i].setTransparent(transparent);
      } else {
        xmlBox[i].deleteTransparent();
      }
    }
    for (int i = 0; i < xmlCylinder.length; i++) {
      if (transparent) {
        xmlCylinder[i].setTransparent(transparent);
      } else {
        xmlCylinder[i].deleteTransparent();
      }
    }
    for (int i = 0; i < xmlSphere.length; i++) {
      if (transparent) {
        xmlSphere[i].setTransparent(transparent);
      } else {
        xmlSphere[i].deleteTransparent();
      }
    }
    for (int i = 0; i < xmlCone.length; i++) {
      if (transparent) {
        xmlCone[i].setTransparent(transparent);
      } else {
        xmlCone[i].deleteTransparent();
      }
    }
    for (int i = 0; i < xmlTriangle.length; i++) {
      if (transparent) {
        xmlTriangle[i].setTransparent(transparent);
      } else {
        xmlTriangle[i].deleteTransparent();
      }
    }
    for (int i = 0; i < xmlQuad.length; i++) {
      if (transparent) {
        xmlQuad[i].setTransparent(transparent);
      } else {
        xmlQuad[i].deleteTransparent();
      }
    }

    Group[] groups = group.getGroups();

    for (int i = 0; i < groups.length; i++) {
      setAllTransparent(groups[i], transparent);
    }
  }

  /**
   * 抽出したグループを取得する。
   * 
   * @return targetGroup
   */
  public Group getSelectionGroup() {
    return this.targetGroup;
  }

  /**
   * グループが編集可能か判定する
   * 
   * @return editable
   */
  public boolean getGroupEditable() {
    return this.editable;
  }

  /**
   * ツリー、ビューアの再描画
   */
  void setTree() {
    fillTree();
    this.modeler.createViewer();
  }

  /**
   * グループgからPrimitive primを消す。
   * 
   * @param g グループ
   * @param prim プリミティブ
   * 
   * @return ノードを削除したかどうか。（削除したとき:true,削除されなかったとき:false）
   */
  protected boolean removeObj(Group g, Object prim) {
    if (prim instanceof XMLBox) {
      g.removeXMLBox((XMLBox)prim);
    } else if (prim instanceof XMLCone) {
      g.removeXMLCone((XMLCone)prim);
    } else if (prim instanceof XMLCylinder) {
      g.removeXMLCylinder((XMLCylinder)prim);
    } else if (prim instanceof XMLSphere) {
      g.removeXMLSphere((XMLSphere)prim);
    } else if (prim instanceof XMLTrianglePolygon) {
      g.removeXMLTrianglePolygon((XMLTrianglePolygon)prim);
    } else if (prim instanceof XMLQuadPolygon) {
      g.removeXMLQuadPolygon((XMLQuadPolygon)prim);
    } else if (prim instanceof Group) {
      MessageBox box = new MessageBox(this.comp.getShell(), SWT.YES | SWT.NO | SWT.ICON_QUESTION);
      box.setMessage(Messages.getString("SceneGraphTree.29")); //$NON-NLS-1$
      box.setText(Messages.getString("SceneGraphTree.30")); //$NON-NLS-1$
      int result = box.open();
      if (result == SWT.NO) {
        return false;
      }
      g.removeGroup((Group)prim);
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
        child.setText("rootGroup : " + groups[i].loadName()); //$NON-NLS-1$
      } else {
        child = new TreeItem(item, SWT.NONE);
        child.setText("Group : " + groups[i].loadName()); //$NON-NLS-1$
      }
      child.setData(groups[i]);

      final XMLBox[] boxes = groups[i].getXMLBox();
      for (int j = 0; j < boxes.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("box ( " + boxes[j].loadXsize() + ", " + boxes[j].loadYsize() + ", " + boxes[j].loadZsize() + ", " + boxes[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(boxes[j]);
      }

      final XMLCylinder[] cylinders = groups[i].getXMLCylinder();
      for (int j = 0; j < cylinders.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Cylinder ( " + cylinders[j].loadR() + ", " + cylinders[j].loadHeight() + ", " + cylinders[j].loadDiv() + ", " + cylinders[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cylinders[j]);
      }

      final XMLSphere[] spheres = groups[i].getXMLSphere();
      for (int j = 0; j < spheres.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Sphere ( " + spheres[j].loadR() + ", " + spheres[j].loadDiv() + ", " + spheres[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        boxChild.setData(spheres[j]);
      }

      final XMLCone[] cones = groups[i].getXMLCone();
      for (int j = 0; j < cones.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Cone ( " + cones[j].loadR() + ", " + cones[j].loadHeight() + ", " + cones[j].loadDiv() + ", " + cones[j].loadColor() + " )"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cones[j]);
      }
      
      final XMLTrianglePolygon[] trianglePolygons = groups[i].getXMLTrianglePolygon();
      for (int j = 0; j < trianglePolygons.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Triangle ( " + trianglePolygons[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$
        boxChild.setData(trianglePolygons[j]);
      }

      final XMLQuadPolygon[] quadPolygons = groups[i].getXMLQuadPolygon();
      for (int j = 0; j < quadPolygons.length; j++) {
        final TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Quad ( " + quadPolygons[j].loadColor() + " )"); //$NON-NLS-1$//$NON-NLS-2$
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
    addTreeItem(null, this.model.loadGroup());
  }

  /**
   * 抽出したデータを取得する。
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
  public JamastModel getModel() {
    return this.model;
  }

  /**
   * グループを消去する。
   */
  public void clearGroups() {
    this.model.clearGroup();
    Group group = new Group();
    this.model.addGroup(group);
    fillTree();
  }

  /**
   * ツリーを消去する。
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
   * モデルを設定する。
   * 
   * @param model モデル
   */
  public void setModel(JamastModel model) {
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
   * 現在ツリーのリンク関係にDHパラメータが使われているか調べる
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