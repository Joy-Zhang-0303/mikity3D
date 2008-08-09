/*
 * Created on 2004/12/03
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui;

import java.awt.Frame;
//import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
//import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.gui.dialog.AddGroupDialog;
import org.mklab.mikity.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.gui.dialog.EditPrimitiveDialog;
import org.mklab.mikity.gui.dialog.GroupConfigDialogLink;
import org.mklab.mikity.gui.dialog.GroupConfigDialogDH;
import org.mklab.mikity.util.MsgUtil;
import org.mklab.mikity.xml.config.DataUnit;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.config.ModelUnit;


/**
 * Primitiveを作成して、Linkを作成するクラス
 * Treeからグループを読み込む
 * @author miki+Shogo Morimune
 * @version $Revision: 1.22 $.2004/12/03 
 */
public class Modeler extends Composite {

  private SceneGraphTree tree;

  private Jamast root;
  //private File tempFile;
  private ModelCanvas canvas;
  private Frame awtFrame;
  private Group treeViewerGroup;
  /** */
  public String modelAngleUnit;
  /** */
  public String modelLengthUnit;
  /** */
  public String dataAngleUnit;
  /** */
  public String dataLengthUnit;
  
  private CollisionCanceller dc;
  /**
   * コンストラクター
   * @param parent
   * @param style
   * @param root
   */
  public Modeler(Composite parent,int style, final Jamast root,CollisionCanceller dc) {
    super(parent,style);
    this.root = root;
    this.setLayout(new GridLayout());
    this.setLayoutData(new GridData(GridData.FILL_BOTH));
    this.dc =dc;
    // SashForm 画面を垂直に広げることができる
    SashForm sash = new SashForm(this, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());
    
    createViewerComp(sash);
//    createParmComp(sash);
    createTreeComp(sash);
    
    //******コンポジットを作成してみる*******************
   // createEditComp(sash);
    //createEditGroupComp(sash);
  }

//  public Modeler(Shell parentShell, Mikity root) {
//    this.parentShell = parentShell;
//    this.root = root;
//
//    /*
//     * そのまま閉じると、変更した点が更新されてしまうので、 最初の状態をtempFileに保存しておく
//     */
//    try {
//      tempFile = File.createTempFile("model", ".xml");
//      root.getModel(0).marshal(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));
//    } catch (IOException e) {
//      // 
//      e.printStackTrace();
//    } catch (MarshalException e) {
//      // 
//      e.printStackTrace();
//    } catch (ValidationException e) {
//      // 
//      e.printStackTrace();
//    }
//
//    createSShell();
//  }
//
//  /**
//   * MainWindowのシェルを親シェルとする
//   * 
//   * @param shell
//   */
//  public Modeler(Shell parentShell) {
//    this.parentShell = parentShell;
//    createSShell();
//  }
//
//  public void open() {
//    sShell.open();
//  }

//  /**
//   * シェルを作成する
//   */
//  private void createShell() {
//    //SWT.APPLICATION_MODAL→このシェルを閉じないと、親シェルを編集できなくする
//    sShell = new Shell(parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
//    GridLayout layout = new GridLayout();
//    layout.numColumns = 1;
//
//    sShell.addDisposeListener(new DisposeListener() {
//
//      //ウインドウを閉じたときにTempFileを消すようにする。
//      public void widgetDisposed(DisposeEvent arg0) {
//        if (tempFile.exists()) {
//          tempFile.delete();
//        }
//      }
//    });

//    // SashForm 画面を垂直に広げることができる
//    SashForm sash = new SashForm(this, SWT.NONE);
//    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
//    sash.setLayout(new GridLayout());
//
//    createViewerComp(sash);
//    createParmComp(sash);
//    createTreeComp(sash);

//    sShell.setSize(new org.eclipse.swt.graphics.Point(820, 510));
//    sShell.setText("Modeler");
//    sShell.setLayout(layout);
  //}

/**
   * 3Dグラフィックスを表示するcompositeの作成
   * @param composite 
   */
  private void createViewerComp(Composite composite) {
    GridData gridData = new GridData(GridData.FILL_BOTH);
    Composite viewerComp = new Composite(composite, SWT.EMBEDDED);
    viewerComp.setLayout(new GridLayout());
    viewerComp.setLayoutData(gridData);

    //何もないキャンバスを作る
    awtFrame = SWT_AWT.new_Frame(viewerComp);
    canvas = new ModelCanvas(root);
    awtFrame.add(canvas);
  }

  /**
   * Treeを表示するcompositeの作成
   * @param composite 
   */
  private void createTreeComp(Composite composite) {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    treeViewerGroup = new Group(composite, SWT.NONE);
    treeViewerGroup.setLayout(layout);

    GridData data = new GridData(GridData.FILL_BOTH);
    data.widthHint = 10;
    treeViewerGroup.setLayoutData(data);
    treeViewerGroup.setText("XMLデータ");

    tree = new SceneGraphTree(treeViewerGroup, this, root.loadModel(0),dc);
    createViewer();
  }

  /**
   * 編集機能を持ったコンポジットを作成してみる。
   * @param composite 
   */
  public void createEditComp(Composite composite) {
    //GridLayout layout = new GridLayout();
    //layout.numColumns = 3;
    GridData gridData = new GridData(GridData.FILL_BOTH);
    Composite editComp = new Composite(composite, SWT.EMBEDDED);
    //editComp.setLayout(new GridLayout());
    editComp.setLayoutData(gridData);
  }
  
  /**
   * 編集機能を持ったコンポジットを作成してみる。
   * @param composite 
   */
  public void createEditGroupComp(Composite composite) {
    //GridLayout layout = new GridLayout();
    //layout.numColumns = 4;
    GridData gridData = new GridData(GridData.FILL_BOTH);
    Composite editGroupComp = new Composite(composite, SWT.EMBEDDED);
    editGroupComp.setLayout(new GridLayout());
    editGroupComp.setLayoutData(gridData);
  }
 
  /**
   * 状態の表示、編集を行うアプリケーションを表示するcompositeの作成
   * @param comp 
   */
  public void createParmComp(Composite comp) {
    Composite composite = new Composite(comp, SWT.NONE);
    GridLayout layout = new GridLayout(1, true);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    composite.setLayout(layout);
    composite.setLayoutData(data);

    //Groupを追加するボタン
    Button addGroupButton = new Button(composite, SWT.NONE);
    addGroupButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addGroupButton.setText("Groupを追加");

    //    //いつか実装できたらすてきだね
    //    //undoボタン
    //    Button undoButton = new Button(composite, SWT.NONE);
    //    undoButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    //    undoButton.setText("元に戻す");

    Button xzButton = new Button(composite, SWT.NONE);
    xzButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    xzButton.setText("xz平面を描画");

    //DHパラメータを設定する
    Button dhButton = new Button(composite, SWT.NONE);
    dhButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    dhButton.setText("DHパラメータの設定");

    Button primAddButton = new Button(composite, SWT.NONE);
    primAddButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    primAddButton.setText("プリミティブの追加");

    Button primEditButton = new Button(composite, SWT.NONE);
    primEditButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    primEditButton.setText("プリミティブの編集");

    //保存して終了ボタン
    Button closeButton = new Button(composite, SWT.NONE);
    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    closeButton.setText(" 保存して終了 ");

    //キャンセルボタン
    Button cancelButton = new Button(composite, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText("キャンセル");

    xzButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

      }
    });

    addGroupButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        org.mklab.mikity.xml.model.Group group = tree.getSelectionGroup();
        if (group == null) {
          MsgUtil.showMsg(getShell(),"追加したいグループを選択してください。");
          return;
        }

        AddGroupDialog addGroup = new AddGroupDialog(getShell(), group);
        addGroup.open();

        tree.fillTree();
        createViewer();
      }
    });

    dhButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        org.mklab.mikity.xml.model.Group group = tree.getSelectionGroup();
        if (group == null) {
          MsgUtil.showMsg(getShell(),"グループを選択してください。");
          return;
        }
        org.mklab.mikity.xml.model.Linkdata[] linkdata = group.loadLinkdata();
        if(linkdata.length == 0){
			MessageBox mesBox = new MessageBox(getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
			mesBox.setMessage("DHパラメータを使用しますか？");
			mesBox.setText("確認");
			int result = mesBox.open();
			if (result == SWT.YES) {
				GroupConfigDialogDH groupConf = new GroupConfigDialogDH(getShell(), group, tree.getGroupEditable());
				groupConf.open();
			}else if(result == SWT.NO){
				GroupConfigDialogLink groupConf = new GroupConfigDialogLink(getShell(), group, tree.getGroupEditable());
				groupConf.open();
			}
		}
		
		for(int i=0; i<linkdata.length; i++){
			if(linkdata[i].hasDH()){
				GroupConfigDialogDH groupConf = new GroupConfigDialogDH(getShell(), group, tree.getGroupEditable());
				groupConf.open();
				break;
			}else if(linkdata[i].hasLink()){
				GroupConfigDialogLink groupConf = new GroupConfigDialogLink(getShell(), group, tree.getGroupEditable());
				groupConf.open();
				break;
			}
		}
        
        tree.fillTree();
        createViewer();
      }
    });

    primAddButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        org.mklab.mikity.xml.model.Group group = tree.getSelectionGroup();
        if (group == null) {
          MessageBox box = new MessageBox(getShell(), SWT.ICON_WARNING);
          box.setText("グループが選択されていません");
          box.setMessage("グループを選択してください。");
          box.open();
          return;
        }

        AddPrimitiveDialog addPrim = new AddPrimitiveDialog(getShell(), group,dc);
        addPrim.open();

        tree.fillTree();
        createViewer();
      }
    });

    primEditButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        Object prim = tree.getSelectionData();
        org.mklab.mikity.xml.model.Group group = tree.getSelectionGroup();

        if (prim == null) {
        	MessageBox box = new MessageBox(getShell(), SWT.ICON_WARNING);
        	box.setText("プリミティブが選択されていません");
        	box.setMessage("プリミティブを選択してください。");
        	box.open();
        	return;
        }

        EditPrimitiveDialog editPrim = new EditPrimitiveDialog(getShell(), prim, group);
        editPrim.open();

        tree.fillTree();
        createViewer();

      }
    });

//    //編集後、保存して終了
//    closeButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//
//      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//        tree.setAllTransparent(root.getModel(0).getGroup(0), false);
//        getShell().close();
//      }
//    });
//
//    //編集したが、変更を保存せず、キャンセルして終了
//    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//
//      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
//
//        //***********2/2 作ってみたけど、変更されてしまう*****************
//        try {
//          Model tmpModel = (Model)Model.unmarshal(new InputStreamReader(new FileInputStream(tempFile), "UTF-8"));
//          root.setModel(0, tmpModel);
//        } catch (MarshalException e1) {
//          // 
//          e1.printStackTrace();
//        } catch (ValidationException e1) {
//          // 
//          e1.printStackTrace();
//        } catch (UnsupportedEncodingException e1) {
//          // 
//          e1.printStackTrace();
//        } catch (FileNotFoundException e1) {
//          // 
//          e1.printStackTrace();
//        }
//        getShell().close();
//      }
//
//    });
  }

  /**
   * GroupをsinsiCanvasに読み込ませ、Frameにaddする
   * 
   * @version $Revision: 1.22 $.2005/01/25
   */
  public void createViewer() {
    org.mklab.mikity.xml.model.Group[] group = tree.getModel().loadGroup();
    canvas.setChild(group);
  }
  
  /**
   * @param root
   */
  public void setModel(Jamast root){
    this.root = root;
    setUnit();
    tree.setModel(root.loadModel(0));
    createViewer();
  }

  /**
   * 
   */
  public void setUnit() {
    modelAngleUnit = "radian";
    modelLengthUnit = "m";
    dataAngleUnit = "radian";
    dataLengthUnit = "m";

    if (root.loadConfig(0).loadModelUnit() != null) {
      ModelUnit modelUnit = root.loadConfig(0).loadModelUnit();
      if (modelUnit.loadAngle() != null) {
        modelAngleUnit = modelUnit.loadAngle();
      }
      if (modelUnit.loadLength() != null) {
        modelLengthUnit = modelUnit.loadLength();
      }
    }
    if (root.loadConfig(0).loadDataUnit() != null) {
      DataUnit dataUnit = root.loadConfig(0).loadDataUnit();
      if (dataUnit.loadAngle() != null) {
        dataAngleUnit = dataUnit.loadAngle();
      }
      if (dataUnit.loadLength() != null) {
        dataLengthUnit = dataUnit.loadLength();
      }
    }
  }
  
  /**
   * シーングラフツリーにプリミティブのデータを追加させる。
   */
  public void fillTree() {
    tree.fillTree();
  }

  /**
   * マウス操作の状態を指定する。
   * @param i マウス操作の状態の表す数値
   */
  public void setMouseOperation(int i) {
    canvas.mouseOperationType = i; 
  }
  
  /**
   * マウス操作の状態の表す数値を返す
   * @return マウス操作の状態の表す数値
   */
  public int getMouseOperation(){
    return canvas.mouseOperationType;
  }
}