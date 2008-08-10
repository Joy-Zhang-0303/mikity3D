package org.mklab.mikity.gui;

//import javax.naming.directory.SearchControls;
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
import org.mklab.mikity.gui.dialog.AddGroupDialog;
import org.mklab.mikity.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.gui.dialog.AddQuadPolygonDialog;
import org.mklab.mikity.gui.dialog.AddTrianglePolygonDialog;
import org.mklab.mikity.gui.dialog.EditPrimitiveDialog;
import org.mklab.mikity.gui.dialog.EditQuadPolygonDialog;
import org.mklab.mikity.gui.dialog.EditTrianglePolygonDialog;
import org.mklab.mikity.gui.dialog.GroupConfigDialogLink;
import org.mklab.mikity.gui.dialog.GroupConfigDialogDH;
import org.mklab.mikity.gui.dnd.DragAndDropEnabler;
import org.mklab.mikity.xml.Model;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;

import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.gui.connector.Connect;
import org.mklab.mikity.gui.connector.PrimitiveConnector;
import org.mklab.mikity.gui.connector.ConnectorSelect;


/**
 * Displays a single-selection tree, a multi-selection tree, and a checkbox tree
 * ツリーの読み込み、書き込みを行う
 */
public class SceneGraphTree {

  Tree xmlTree;
  Model model;
  Composite comp;
  TreeItem selectItem = null;
  Object targetObj = null;
  Group targetGroup = null;
  Group targetParentGroup = null;
  Group root = null;
  boolean editable = true;

  Modeler modeler;

  ConnectorSelect cs;
  Connect connect;

  CollisionCanceller dc;

  boolean usedDHParam = false;
  boolean usedLink = false;

  /**
   * コンストラクター
   */
  public SceneGraphTree() {
    // nothing to do
  }

  /**
   * コンストラクター
   * 
   * @param composite
   * @param modeler
   * @param model
   * @param dc 
   */
  public SceneGraphTree(final Composite composite, final Modeler modeler, final Model model, CollisionCanceller dc) {
    this.model = model;
    this.modeler = modeler;
    // ファイルの読み込みを行う
    createTree(composite);
    this.comp = composite;
    this.dc = dc;
    this.dc.setComposite(this.comp);
    this.cs = new ConnectorSelect(composite, this, modeler);
    this.connect = new Connect();
  }

  /**
   * ツリーを生成する
   * 
   * @param composite
   */
  private void createTree(final Composite composite) {
    composite.setLayout(new GridLayout(1, true));
    GridData data = new GridData(GridData.FILL_BOTH);
    this.xmlTree = new Tree(composite, SWT.SINGLE | SWT.BORDER);

    data = new GridData(GridData.FILL_BOTH);
    this.xmlTree.setLayoutData(data);

    fillTree();

    new DragAndDropEnabler(this.xmlTree);

    this.xmlTree.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseDoubleClick(MouseEvent arg0) {
        // ダブルクリック
        SceneGraphTree.this.editable = true;

        Object doubleClickObj = SceneGraphTree.this.xmlTree.getSelection()[0].getData();
        if (doubleClickObj == null) {
          return;
        } else if (doubleClickObj instanceof Group) {
          if (SceneGraphTree.this.xmlTree.getSelection()[0].getText().startsWith("root")) {
            // 選択されたものがrootであるとき
            SceneGraphTree.this.editable = false;
          }
          checkUsedLinkType(SceneGraphTree.this.model.loadGroup());

          if (SceneGraphTree.this.usedDHParam) {
            GroupConfigDialogDH groupConf = new GroupConfigDialogDH(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else if (SceneGraphTree.this.usedLink) {
            GroupConfigDialogLink groupConf = new GroupConfigDialogLink(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else {
            MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
            mesBox.setMessage("DHパラメータを使用しますか？");
            mesBox.setText("確認");
            int result = mesBox.open();
            if (result == SWT.YES) {
              GroupConfigDialogDH groupConf = new GroupConfigDialogDH(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            } else if (result == SWT.NO) {
              GroupConfigDialogLink groupConf = new GroupConfigDialogLink(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            }
          }
          setTree();
        } else if (doubleClickObj instanceof XMLTrianglePolygon) {
          EditTrianglePolygonDialog editPoly = new EditTrianglePolygonDialog(composite.getShell(), (XMLTrianglePolygon)doubleClickObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else if (doubleClickObj instanceof XMLQuadPolygon) {
          EditQuadPolygonDialog editPoly = new EditQuadPolygonDialog(composite.getShell(), (XMLQuadPolygon)doubleClickObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else if (doubleClickObj instanceof XMLConnector) {
          SceneGraphTree.this.cs.select(SceneGraphTree.this.targetObj, SceneGraphTree.this.root, SceneGraphTree.this.xmlTree, SceneGraphTree.this.targetGroup);
        } else {
          EditPrimitiveDialog editPrim = new EditPrimitiveDialog(composite.getShell(), doubleClickObj, SceneGraphTree.this.targetGroup);
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
          TreeItem groupNode = SceneGraphTree.this.selectItem.getParentItem();
          // groupNodeがnullならrootノードなので削除不可能
          if (groupNode == null) {
            return;
          }
          Object prim = SceneGraphTree.this.selectItem.getData();
          Group g = (Group)groupNode.getData();
          if (removeObj(g, prim)) {
            // 中身が消されれば、表示も削除
            SceneGraphTree.this.selectItem.dispose();
          }
          SceneGraphTree.this.modeler.createViewer();
        }
      }
    });

    Menu popup = new Menu(composite.getShell(), SWT.POP_UP);
    this.xmlTree.setMenu(popup);

    MenuItem addModel = new MenuItem(popup, SWT.CASCADE);
    addModel.setText("3Dモデルを追加(&M)");

    Menu modelingSub = new Menu(popup);
    addModel.setMenu(modelingSub);

    MenuItem addPrim = new MenuItem(modelingSub, SWT.POP_UP);
    addPrim.setText("プリミティブを追加(&P)");

    MenuItem addTriPoly = new MenuItem(modelingSub, SWT.POP_UP);
    addTriPoly.setText("三角形ポリゴンを追加(&T)");

    MenuItem addQuadPoly = new MenuItem(modelingSub, SWT.POP_UP);
    addQuadPoly.setText("四角形ポリゴンを追加(&Q)");

    MenuItem addGroup = new MenuItem(popup, SWT.POP_UP);
    addGroup.setText("グループを追加(&G)");

    MenuItem connector = new MenuItem(popup, SWT.CASCADE);
    connector.setText("コネクタ(&C)");

    Menu connectorSub = new Menu(popup);
    connector.setMenu(connectorSub);

    MenuItem connectorAuto = new MenuItem(connectorSub, SWT.POP_UP);
    connectorAuto.setText("一括表示(&D)");

    MenuItem connectorSelect = new MenuItem(connectorSub, SWT.POP_UP);
    connectorSelect.setText("選択(&S)");

    MenuItem connectAction = new MenuItem(connectorSub, SWT.POP_UP);
    connectAction.setText("接続(&C)");

    MenuItem edit = new MenuItem(popup, SWT.POP_UP);
    edit.setText("編集(&E)");

    MenuItem delete = new MenuItem(popup, SWT.POP_UP);
    delete.setText("削除(&D)");

    addPrim.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        AddPrimitiveDialog addPrim = new AddPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.dc);
        addPrim.open();
        setTree();
      }
    });

    addTriPoly.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        AddTrianglePolygonDialog addTriPoly = new AddTrianglePolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        addTriPoly.open();
        setTree();
      }
    });

    addQuadPoly.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        AddQuadPolygonDialog addQuadPoly = new AddQuadPolygonDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        addQuadPoly.open();
        setTree();
      }
    });

    addGroup.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        AddGroupDialog addGroup = new AddGroupDialog(composite.getShell(), SceneGraphTree.this.targetGroup);
        addGroup.open();
        setTree();
      }
    });

    edit.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        // if (targetObj == null) {
        if (SceneGraphTree.this.targetObj instanceof Group) {
          checkUsedLinkType(SceneGraphTree.this.model.loadGroup());

          if (SceneGraphTree.this.usedDHParam) {
            GroupConfigDialogDH groupConf = new GroupConfigDialogDH(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else if (SceneGraphTree.this.usedLink) {
            GroupConfigDialogLink groupConf = new GroupConfigDialogLink(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
            groupConf.open();
          } else {
            MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
            mesBox.setMessage("DHパラメータを使用しますか？");
            mesBox.setText("確認");
            int result = mesBox.open();
            if (result == SWT.YES) {
              GroupConfigDialogDH groupConf = new GroupConfigDialogDH(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            } else if (result == SWT.NO) {
              GroupConfigDialogLink groupConf = new GroupConfigDialogLink(composite.getShell(), SceneGraphTree.this.targetGroup, SceneGraphTree.this.editable);
              groupConf.open();
              setTree();
            }
          }
        } else if (SceneGraphTree.this.targetObj instanceof XMLTrianglePolygon) {
          EditTrianglePolygonDialog editPoly = new EditTrianglePolygonDialog(composite.getShell(), (XMLTrianglePolygon)SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else if (SceneGraphTree.this.targetObj instanceof XMLQuadPolygon) {
          EditQuadPolygonDialog editPoly = new EditQuadPolygonDialog(composite.getShell(), (XMLQuadPolygon)SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
          editPoly.open();
          setTree();
        } else {
          EditPrimitiveDialog editPrim = new EditPrimitiveDialog(composite.getShell(), SceneGraphTree.this.targetObj, SceneGraphTree.this.targetGroup);
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
            MessageBox msg = new MessageBox(composite.getShell(), SWT.ICON_INFORMATION);
            msg.setText("削除できません");
            msg.setMessage("選択されているグループはルートグループです　\n 削除できません");
            msg.open();
          } else {
            removeObj(SceneGraphTree.this.targetParentGroup, SceneGraphTree.this.targetGroup);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        } else if (SceneGraphTree.this.targetObj instanceof XMLTrianglePolygon || SceneGraphTree.this.targetObj instanceof XMLQuadPolygon) {
          MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage("ポリゴンを削除します");
          mesBox.setText("確認");
          int result = mesBox.open();
          if (result == SWT.YES) {
            removeObj(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObj);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        } else {

          MessageBox mesBox = new MessageBox(composite.getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage("プリミティブを削除します");
          mesBox.setText("確認");
          int result = mesBox.open();
          if (result == SWT.YES) {
            removeObj(SceneGraphTree.this.targetGroup, SceneGraphTree.this.targetObj);
            SceneGraphTree.this.xmlTree.getSelection()[0].dispose();
            setTree();
          }
        }
      }
    });

    connectorAuto.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        if (SceneGraphTree.this.targetObj instanceof Group) {
          MessageBox mesBox = new MessageBox(composite.getShell(), SWT.OK | SWT.ICON_INFORMATION);
          mesBox.setMessage("グループにはコネクタを追加できません");
          mesBox.setText("確認");
          mesBox.open();
        } else if (SceneGraphTree.this.targetObj instanceof XMLConnector) {
          MessageBox mesBox = new MessageBox(composite.getShell(), SWT.OK | SWT.ICON_INFORMATION);
          mesBox.setMessage("コネクタにはコネクタを追加できません");
          mesBox.setText("確認");
          mesBox.open();
        } else {
          SceneGraphTree.this.root = SceneGraphTree.this.targetGroup;
          PrimitiveConnector pConnector = new PrimitiveConnector();
          SceneGraphTree.this.connect.setPrimitiveNS(SceneGraphTree.this.targetObj, SceneGraphTree.this.cs.getTreeConnectorFlag());
          pConnector.createPrimitiveConnector(SceneGraphTree.this.targetObj);
          setTree();
        }
      }
    });

    connectorSelect.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        SceneGraphTree.this.cs.select(SceneGraphTree.this.targetObj, SceneGraphTree.this.root, SceneGraphTree.this.xmlTree, SceneGraphTree.this.targetGroup);
      }
    });

    connectAction.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        // コネクタN,Sの位置および回転情報を取得
        SceneGraphTree.this.connect.connectorNS(SceneGraphTree.this.cs.getConnectorN());
        SceneGraphTree.this.connect.connectorNS(SceneGraphTree.this.cs.getConnectorS());

        // 移動させるコネクタの新しい座標と回転を設定。
        SceneGraphTree.this.connect.setNewLoc();
        SceneGraphTree.this.connect.setNewRot();
        // 接続実行。
        MessageBox mesBox = new MessageBox(composite.getShell(), SWT.OK | SWT.ICON_INFORMATION);
        mesBox.setMessage("コネクタでプリミティブ同士を接続しました。");
        mesBox.setText("接続");
        mesBox.open();
        // 接続
        SceneGraphTree.this.connect.connect();
        // 必要のなくなったコネクタはグループごと消去。コネクタフラグもリセット
        SceneGraphTree.this.cs.reset(SceneGraphTree.this.root);
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
      if (this.xmlTree.getSelection()[0].getText().startsWith("root")) {
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
    } else if (obj instanceof XMLConnector) {
      ((XMLConnector)obj).setTransparent(false);
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
      XMLBox[] xmlBox = group.loadXMLBox();
      for (int i = 0; i < xmlBox.length; i++) {
        xmlBox[i].setTransparent(false);
      }
      XMLCylinder[] xmlCylinder = group.loadXMLCylinder();
      for (int i = 0; i < xmlCylinder.length; i++) {
        xmlCylinder[i].setTransparent(false);
      }
      XMLSphere[] xmlSphere = group.loadXMLSphere();
      for (int i = 0; i < xmlSphere.length; i++) {
        xmlSphere[i].setTransparent(false);
      }
      XMLCone[] xmlCone = group.loadXMLCone();
      for (int i = 0; i < xmlCone.length; i++) {
        xmlCone[i].setTransparent(false);
      }
      XMLConnector[] xmlConnector = group.loadXMLConnector();
      for (int i = 0; i < xmlConnector.length; i++) {
        xmlConnector[i].setTransparent(false);
      }
      XMLTrianglePolygon[] xmlTriangle = group.loadXMLTrianglePolygon();
      for (int i = 0; i < xmlTriangle.length; i++) {
        xmlTriangle[i].setTransparent(false);
      }
      XMLQuadPolygon[] xmlQuad = group.loadXMLQuadPolygon();
      for (int i = 0; i < xmlQuad.length; i++) {
        xmlQuad[i].setTransparent(false);
      }
    }
  }

  /**
   * 全透過性を設定する。
   * 
   * @param group
   * @param transparent
   */
  public void setAllTransparent(final Group group, boolean transparent) {
    XMLBox[] xmlBox = group.loadXMLBox();
    XMLCylinder[] xmlCylinder = group.loadXMLCylinder();
    XMLSphere[] xmlSphere = group.loadXMLSphere();
    XMLCone[] xmlCone = group.loadXMLCone();
    XMLConnector[] xmlConnector = group.loadXMLConnector();
    XMLTrianglePolygon[] xmlTriangle = group.loadXMLTrianglePolygon();
    XMLQuadPolygon[] xmlQuad = group.loadXMLQuadPolygon();

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
    for (int i = 0; i < xmlConnector.length; i++) {
      if (transparent) {
        xmlConnector[i].setTransparent(transparent);
      } else {
        xmlConnector[i].deleteTransparent();
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

    Group[] groups = group.loadGroup();

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
   * @param g
   * @param prim
   * 
   * @return ノードを削除したかどうか。（削除したとき:true,削除されなかったとき:false）
   */
  protected boolean removeObj(Group g, Object prim) {
    if (prim instanceof XMLBox) {
      g.removeXMLBox((XMLBox)prim);
    } else if (prim instanceof XMLCone) {
      g.removeXMLCone((XMLCone)prim);
    } else if (prim instanceof XMLConnector) {
      g.removeXMLConnector((XMLConnector)prim);
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
      box.setMessage("グループ全体を削除します");
      box.setText("確認");
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
   * @param group
   */
  private void addTreeItem(TreeItem item, Group[] group) {
    // Groupの長さだけ繰り返す
    for (int i = 0; i < group.length; i++) {
      // 子となるTreeItem、childに名前をつけて接続
      TreeItem child = null;
      if (item == null) {
        child = new TreeItem(this.xmlTree, SWT.NONE);
        child.setText("rootGroup : " + group[i].loadName()); //$NON-NLS-1$
      } else {
        child = new TreeItem(item, SWT.NONE);
        child.setText("Group : " + group[i].loadName()); //$NON-NLS-1$
      }
      child.setData(group[i]);

      XMLBox[] box = group[i].loadXMLBox();
      for (int j = 0; j < box.length; j++) {
        TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("box ( " + box[j].loadXsize() + ", " + box[j].loadYsize() + ", " + box[j].loadZsize() + ", " + box[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(box[j]);
      }

      XMLCylinder[] cyl = group[i].loadXMLCylinder();
      for (int j = 0; j < cyl.length; j++) {
        TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Cylinder ( " + cyl[j].loadR() + ", " + cyl[j].loadHeight() + ", " + cyl[j].loadDiv() + ", " + cyl[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cyl[j]);
      }

      XMLSphere[] sph = group[i].loadXMLSphere();
      for (int j = 0; j < sph.length; j++) {
        TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Sphere ( " + sph[j].loadR() + ", " + sph[j].loadDiv() + ", " + sph[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        boxChild.setData(sph[j]);
      }

      XMLCone[] cone = group[i].loadXMLCone();
      for (int j = 0; j < cone.length; j++) {
        TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Cone ( " + cone[j].loadR() + ", " + cone[j].loadHeight() + ", " + cone[j].loadDiv() + ", " + cone[j].loadColor() + " )");  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        boxChild.setData(cone[j]);
      }
      XMLConnector[] connector = group[i].loadXMLConnector();
      for (int j = 0; j < connector.length; j++) {
        TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Connector ( No." + connector[j].loadNum() + ", " + connector[j].loadFlag() + " )");  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        boxChild.setData(connector[j]);

        // if(connector[j].getFlag()=="N"){
        // cs.setConnectorN(connector[j]);
        // }else if(connector[j].getFlag()=="S"){
        // cs.setConnectorS(connector[j]);
        // }
      }

      XMLTrianglePolygon[] triangle = group[i].loadXMLTrianglePolygon();
      for (int j = 0; j < triangle.length; j++) {
        TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Triangle ( " + triangle[j].loadColor() + " )"); //$NON-NLS-1$ //$NON-NLS-2$
        boxChild.setData(triangle[j]);
      }

      XMLQuadPolygon[] quad = group[i].loadXMLQuadPolygon();
      for (int j = 0; j < quad.length; j++) {
        TreeItem boxChild = new TreeItem(child, SWT.NONE);
        boxChild.setText("Quad ( " + quad[j].loadColor() + " )");  //$NON-NLS-1$//$NON-NLS-2$
        boxChild.setData(quad[j]);
      }

      if (item != null) {
        item.setExpanded(true);
      }
      Group[] childGroup = group[i].loadGroup();
      addTreeItem(child, childGroup);
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
  public Model getModel() {
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

    TreeItem rootNode = this.xmlTree.getItems()[0];

    TreeItem[] items = rootNode.getItems();
    for (int i = 0; i < items.length; i++) {
      items[i].dispose();
    }
    rootNode.dispose();
  }

  /**
   * モデルを設定する。
   * 
   * @param model
   */
  public void setModel(Model model) {
    this.model = model;
    fillTree();
  }

  /**
   * @return
   */
  public Tree getXMLTree() {
    return this.xmlTree;
  }

  /**
   * 現在ツリーのリンク関係にDHパラメータが使われているか調べる
   * 
   * @param g
   *        グループリスト
   */
  void checkUsedLinkType(Group[] g) {
    if (g.length != 0 || this.usedDHParam == false || this.usedLink == false) {
      for (int i = 0; i < g.length; i++) {
        Linkdata[] link = g[i].loadLinkdata();
        for (int j = 0; j < link.length; j++) {
          if (link[j].hasDH()) {
            this.usedDHParam = true;
            break;
          } else if (link[j].hasLink()) {
            this.usedLink = true;
            break;
          }
        }
        checkUsedLinkType(g[i].loadGroup());
      }
    }
  }
  // /**
  // * @return Group
  // */
  // public Group getSelectionGroup() {
  // //何も選んでない状態ならは、nullを返す
  // if (xmlTree.getSelectionCount() == 0) {
  // return null;
  // }
  //
  // selectItem = xmlTree.getSelection()[0];
  //
  // Object itemData = selectItem.getData();
  // if (itemData instanceof Group) {
  // return (Group)itemData;
  // } else {
  // selectItem = selectItem.getParentItem();
  // if (selectItem == null) {
  // return null;
  // }
  // itemData = selectItem.getData();
  // if (itemData instanceof Group) {
  // return (Group)itemData;
  // }
  // }
  // return null;
  // }
}