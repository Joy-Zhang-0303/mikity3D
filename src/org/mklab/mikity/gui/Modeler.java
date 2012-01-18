/*
 * Created on 2004/12/03
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.gui.dialog.AddGroupDialog;
import org.mklab.mikity.gui.dialog.AddPrimitiveDialog;
import org.mklab.mikity.gui.dialog.EditPrimitiveDialog;
import org.mklab.mikity.gui.dialog.GroupConfigDialogDH;
import org.mklab.mikity.gui.dialog.GroupConfigDialogLink;
import org.mklab.mikity.util.MsgUtil;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.config.DataUnit;
import org.mklab.mikity.xml.config.ModelUnit;


/**
 * Primitiveを作成して、Linkを作成するクラス Treeからグループを読み込む
 * 
 * @author miki
 * @version $Revision: 1.22 $.2004/12/03
 */
public abstract class Modeler extends Composite {

  /** */
  protected SceneGraphTree tree;

  /** */
  protected Jamast root;
  
  /** */
  protected Frame awtFrame;
  private Group treeViewerGroup;
  /** */
  private String modelAngleUnit;
  /** */
  private String modelLengthUnit;
  /** */
  private String dataAngleUnit;
  /** */
  private String dataLengthUnit;

  /** */
  CollisionCanceller dc;

  /**
   * コンストラクター
   * 
   * @param parent
   * @param style
   * @param root
   * @param dc
   */
  public Modeler(Composite parent, int style, final Jamast root, CollisionCanceller dc) {
    super(parent, style);
    this.root = root;
    this.setLayout(new GridLayout());
    this.setLayoutData(new GridData(GridData.FILL_BOTH));
    this.dc = dc;
    // SashForm 画面を垂直に広げることができる
    SashForm sash = new SashForm(this, SWT.NONE);
    sash.setLayoutData(new GridData(GridData.FILL_BOTH));
    sash.setLayout(new GridLayout());

    createViewerComp(sash);
    createTreeComp(sash);

  }


  /**
   * 3Dグラフィックスを表示するcompositeの作成
   * 
   * @param composite
   */
  private void createViewerComp(Composite composite) {
    GridData gridData = new GridData(GridData.FILL_BOTH);
    Composite viewerComp = new Composite(composite, SWT.EMBEDDED);
    viewerComp.setLayout(new GridLayout());
    viewerComp.setLayoutData(gridData);
    createModelCanvas(viewerComp);
  }

  /**
   * Treeを表示するcompositeの作成
   * 
   * @param composite
   */
  private void createTreeComp(Composite composite) {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    this.treeViewerGroup = new Group(composite, SWT.NONE);
    this.treeViewerGroup.setLayout(layout);

    GridData data = new GridData(GridData.FILL_BOTH);
    data.widthHint = 10;
    this.treeViewerGroup.setLayoutData(data);
    this.treeViewerGroup.setText("XMLデータ");

    this.tree = new SceneGraphTree(this.treeViewerGroup, this, this.root.loadModel(0), this.dc);
    createViewer();
  }

  /**
   * 編集機能を持ったコンポジットを作成してみる。
   * 
   * @param composite コンポジット
   */
  public void createEditComp(Composite composite) {
    GridData gridData = new GridData(GridData.FILL_BOTH);
    Composite editComp = new Composite(composite, SWT.EMBEDDED);
    editComp.setLayoutData(gridData);
  }

  /**
   * 編集機能を持ったコンポジットを作成してみる。
   * 
   * @param composite コンポジット
   */
  public void createEditGroupComp(Composite composite) {
    GridData gridData = new GridData(GridData.FILL_BOTH);
    Composite editGroupComp = new Composite(composite, SWT.EMBEDDED);
    editGroupComp.setLayout(new GridLayout());
    editGroupComp.setLayoutData(gridData);
  }

  /**
   * 状態の表示、編集を行うアプリケーションを表示するcompositeの作成
   * 
   * @param comp コンポジット
   */
  public void createParmComp(Composite comp) {
    Composite composite = new Composite(comp, SWT.NONE);
    GridLayout layout = new GridLayout(1, true);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    composite.setLayout(layout);
    composite.setLayoutData(data);

    // Groupを追加するボタン
    Button addGroupButton = new Button(composite, SWT.NONE);
    addGroupButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    addGroupButton.setText("Groupを追加");

    // //いつか実装できたらすてきだね
    // //undoボタン
    // Button undoButton = new Button(composite, SWT.NONE);
    // undoButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    // undoButton.setText("元に戻す");

    Button xzButton = new Button(composite, SWT.NONE);
    xzButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    xzButton.setText("xz平面を描画");

    // DHパラメータを設定する
    Button dhButton = new Button(composite, SWT.NONE);
    dhButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    dhButton.setText("DHパラメータの設定");

    Button primAddButton = new Button(composite, SWT.NONE);
    primAddButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    primAddButton.setText("プリミティブの追加");

    Button primEditButton = new Button(composite, SWT.NONE);
    primEditButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    primEditButton.setText("プリミティブの編集");

    // 保存して終了ボタン
    Button closeButton = new Button(composite, SWT.NONE);
    closeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    closeButton.setText(" 保存して終了 ");

    // キャンセルボタン
    Button cancelButton = new Button(composite, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText("キャンセル");

    xzButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
      // nothing to do
      }
    });

    addGroupButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        org.mklab.mikity.xml.model.Group group = Modeler.this.tree.getSelectionGroup();
        if (group == null) {
          MsgUtil.showMsg(getShell(), "追加したいグループを選択してください。");
          return;
        }

        AddGroupDialog addGroup = new AddGroupDialog(getShell(), group);
        addGroup.open();

        Modeler.this.tree.fillTree();
        createViewer();
      }
    });

    dhButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        org.mklab.mikity.xml.model.Group group = Modeler.this.tree.getSelectionGroup();
        if (group == null) {
          MsgUtil.showMsg(getShell(), "グループを選択してください。");
          return;
        }
        org.mklab.mikity.xml.model.Linkdata[] linkdata = group.loadLinkdata();
        if (linkdata.length == 0) {
          MessageBox mesBox = new MessageBox(getShell(), SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
          mesBox.setMessage("DHパラメータを使用しますか？");
          mesBox.setText("確認");
          int result = mesBox.open();
          if (result == SWT.YES) {
            GroupConfigDialogDH groupConf = new GroupConfigDialogDH(getShell(), group, Modeler.this.tree.getGroupEditable());
            groupConf.open();
          } else if (result == SWT.NO) {
            GroupConfigDialogLink groupConf = new GroupConfigDialogLink(getShell(), group, Modeler.this.tree.getGroupEditable());
            groupConf.open();
          }
        }

        for (int i = 0; i < linkdata.length; i++) {
          if (linkdata[i].hasDH()) {
            GroupConfigDialogDH groupConf = new GroupConfigDialogDH(getShell(), group, Modeler.this.tree.getGroupEditable());
            groupConf.open();
            break;
          } else if (linkdata[i].hasLink()) {
            GroupConfigDialogLink groupConf = new GroupConfigDialogLink(getShell(), group, Modeler.this.tree.getGroupEditable());
            groupConf.open();
            break;
          }
        }

        Modeler.this.tree.fillTree();
        createViewer();
      }
    });

    primAddButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        org.mklab.mikity.xml.model.Group group = Modeler.this.tree.getSelectionGroup();
        if (group == null) {
          MessageBox box = new MessageBox(getShell(), SWT.ICON_WARNING);
          box.setText("グループが選択されていません");
          box.setMessage("グループを選択してください。");
          box.open();
          return;
        }

        AddPrimitiveDialog addPrim = new AddPrimitiveDialog(getShell(), group, Modeler.this.dc);
        addPrim.open();

        Modeler.this.tree.fillTree();
        createViewer();
      }
    });

    primEditButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        Object prim = Modeler.this.tree.getSelectionData();
        org.mklab.mikity.xml.model.Group group = Modeler.this.tree.getSelectionGroup();

        if (prim == null) {
          MessageBox box = new MessageBox(getShell(), SWT.ICON_WARNING);
          box.setText("プリミティブが選択されていません");
          box.setMessage("プリミティブを選択してください。");
          box.open();
          return;
        }

        EditPrimitiveDialog editPrim = new EditPrimitiveDialog(getShell(), prim, group);
        editPrim.open();

        Modeler.this.tree.fillTree();
        createViewer();

      }
    });
  }

  /**
   * @param root ツリーのルート
   */
  public void setModel(Jamast root) {
    this.root = root;
    setUnit();
    this.tree.setModel(root.loadModel(0));
    createViewer();
  }

  /**
   * 
   */
  public void setUnit() {
    this.modelAngleUnit = "radian"; //$NON-NLS-1$
    this.modelLengthUnit = "m"; //$NON-NLS-1$
    this.dataAngleUnit = "radian"; //$NON-NLS-1$
    this.dataLengthUnit = "m"; //$NON-NLS-1$

    if (this.root.loadConfig(0).loadModelUnit() != null) {
      ModelUnit modelUnit = this.root.loadConfig(0).loadModelUnit();
      if (modelUnit.loadAngle() != null) {
        this.modelAngleUnit = modelUnit.loadAngle();
      }
      if (modelUnit.loadLength() != null) {
        this.modelLengthUnit = modelUnit.loadLength();
      }
    }
    if (this.root.loadConfig(0).loadDataUnit() != null) {
      DataUnit dataUnit = this.root.loadConfig(0).loadDataUnit();
      if (dataUnit.loadAngle() != null) {
        this.dataAngleUnit = dataUnit.loadAngle();
      }
      if (dataUnit.loadLength() != null) {
        this.dataLengthUnit = dataUnit.loadLength();
      }
    }
  }

  /**
   * シーングラフツリーにプリミティブのデータを追加させる。
   */
  public void fillTree() {
    this.tree.fillTree();
  }
  
  /**
   * GroupをsinsiCanvasに読み込ませ、Frameにaddする
   * 
   * @version $Revision: 1.22 $.2005/01/25
   */
  public abstract void createViewer();

  /**
   * @param viewerComp ビュワーコンポジット
   */
  public abstract void createModelCanvas(Composite viewerComp);
}