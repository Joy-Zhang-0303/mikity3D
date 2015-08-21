/*
 * $Id: EditQuadPolygonDialog.java,v 1.7 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * 四角形ポリゴンの編集を行うクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.7 $. 2007/11/15
 */
public class EditQuadPolygonDialog {

  private Shell parentShell;
  Shell sShell;
  private QuadPolygonModel polygon;

  private String groupName;
  private Group afterGroup;

  private ColorSelectorButton colorSelector;

  private ParameterInputBox vertex1X;
  private ParameterInputBox vertex1Y;
  private ParameterInputBox vertex1Z;
  
  private ParameterInputBox vertexX2;
  private ParameterInputBox vertex2Y;
  private ParameterInputBox vertex2Z;
  
  private ParameterInputBox vertex3X;
  private ParameterInputBox vertex3Y;
  private ParameterInputBox vertex3Z;
  
  private ParameterInputBox vertex4X;
  private ParameterInputBox vertex4Y;
  private ParameterInputBox vertex4Z;
  
  private ParameterInputBox leftVertexX;
  private ParameterInputBox leftVertexY;
  private ParameterInputBox leftVertexZ;
  
  private ParameterInputBox rightVertexX;
  private ParameterInputBox rightVertexY;
  private ParameterInputBox rightVertexZ;

  SceneGraphTree tree;
  JoglModeler modeler;
  
  /**
   * コンストラクター
   * 
   * @param parentShell 親のシェル
   * @param quad ポリゴン
   * @param group グループ
   * @param tree シーングラフツリー 
   * @param modeler モデラー
   */
  public EditQuadPolygonDialog(Shell parentShell, QuadPolygonModel quad, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    this.parentShell = parentShell;
    this.polygon = quad;
    this.groupName = group.getName();
    this.tree = tree;
    this.modeler = modeler;
    
    this.tree.setIsModifyingObject(true);
    
    createSShell();
    detectPrim();
  }

  /**
   * コンストラクター
   */
  public void EditTrianglePolygonDialog() {
  // nothing to do
  }

  /**
   * シェルを開く
   */
  public void open() {
    this.sShell.open();
//    Display display = this.sShell.getDisplay();
//    while (!this.sShell.isDisposed()) {
//      if (!display.readAndDispatch()) {
//        display.sleep();
//      }
//    }
  }

  /**
   * シェルを作成
   */
  private void createSShell() {
    //this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 850));
    this.sShell.setText(Messages.getString("EditQuadPolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("EditQuadPolygonDialog.1") + this.groupName); //$NON-NLS-1$
    setGridLayout(groupLabel, 1);
    
    addShellListener();

    createNewGroup();

    createButtonComp();
  }
  
  /**
   * Shellのリスナーを追加します。 
   */
  private void addShellListener() {
    this.sShell.addShellListener(new ShellListener() {
      public void shellIconified(ShellEvent arg0) {
        // nothing to do
      }
      
      public void shellDeiconified(ShellEvent arg0) {
        // nothing to do
      }
      
      public void shellDeactivated(ShellEvent arg0) {
        // nothing to do
      }
      
      public void shellClosed(ShellEvent arg0) {
        EditQuadPolygonDialog.this.tree.setIsModifyingObject(false);
      }
      
      public void shellActivated(ShellEvent arg0) {
        // nothing to do
      }
    });
  }

  private void createNewGroup() {
    this.afterGroup = new Group(this.sShell, SWT.NONE);
    this.afterGroup.setText(Messages.getString("EditQuadPolygonDialog.20")); //$NON-NLS-1$
    setGridLayout(this.afterGroup, 1);
    final GridLayout afterLayout = new GridLayout(3, false);
    this.afterGroup.setLayout(afterLayout);

    final Label colorLabel4 = new Label(this.afterGroup, SWT.LEFT);
    colorLabel4.setText(Messages.getString("EditQuadPolygonDialog.22")); //$NON-NLS-1$
    setGridLayout(colorLabel4, 1);
    
    this.colorSelector = new ColorSelectorButton(this.afterGroup);
    
    Label label9 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label9, 3);
    
    this.vertex1X = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.3"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.4"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label5 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.vertexX2 = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.6"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.7"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label6 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    this.vertex3X = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.21"), "0.0");    //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.9"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.10"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label7 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label7, 3);

    this.vertex4X = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.11"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex4Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.12"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex4Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.13"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    Label label8 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label8, 3);

    this.leftVertexX = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.leftVertexY = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.18"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.leftVertexZ = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.19"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
   
    final Label labelR2 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR2, 3);

    this.rightVertexX = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.rightVertexY = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.rightVertexZ = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.16"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
  }

  /**
   * レイアウトマネージャGridLayoutを設定
   * 
   * @param control
   * @param hSpan
   */
  private void setGridLayout(Control control, int hSpan) {
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    gridData.horizontalSpan = hSpan;
    control.setLayoutData(gridData);
  }

  /**
   * 変更を決定するButtonを作成
   */
  private void createButtonComp() {
    final Composite composite = new Composite(this.sShell, SWT.NONE);
    setGridLayout(composite, 3);

    final GridLayout compLayout = new GridLayout(3, true);
    composite.setLayout(compLayout);

    final Button okButton = new Button(composite, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("EditQuadPolygonDialog.40")); //$NON-NLS-1$
    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containOnlyNumbers()) {
          updateObjectParameters();
          EditQuadPolygonDialog.this.tree.updateTree();
          EditQuadPolygonDialog.this.modeler.updateDisplay();
          EditQuadPolygonDialog.this.sShell.close();
        } else {
          final MessageBox message = new MessageBox(EditQuadPolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditQuadPolygonDialog.43")); //$NON-NLS-1$
          message.setText(Messages.getString("EditQuadPolygonDialog.44")); //$NON-NLS-1$
          message.open();
        }
      }
    });

    final Button cancelButton = new Button(composite, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("EditQuadPolygonDialog.45")); //$NON-NLS-1$
    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        final MessageBox message = new MessageBox(EditQuadPolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditQuadPolygonDialog.46")); //$NON-NLS-1$
        message.setText(Messages.getString("EditQuadPolygonDialog.47")); //$NON-NLS-1$
        int yesNo = message.open();
        if (yesNo == SWT.YES) {
          EditQuadPolygonDialog.this.sShell.close();
        }
      }
    });
    
    final Button applyButton = new Button(composite, SWT.NONE);
    applyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    applyButton.setText(Messages.getString("EditTrianglePolygonDialog.19")); //$NON-NLS-1$
    applyButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containOnlyNumbers()) {
          updateObjectParameters();
          EditQuadPolygonDialog.this.tree.updateTree();
          EditQuadPolygonDialog.this.modeler.updateDisplay();
        } else {
          final MessageBox message = new MessageBox(EditQuadPolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditQuadPolygonDialog.43")); //$NON-NLS-1$
          message.setText(Messages.getString("EditQuadPolygonDialog.44")); //$NON-NLS-1$
          message.open();
        }
      }
    });
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean containOnlyNumbers() {
    if (this.vertex1X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex1Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertexX2.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex2Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex3Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.vertex4Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rightVertexX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rightVertexY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rightVertexZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.leftVertexX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.leftVertexY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.leftVertexZ.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * パラメータを変更する
   */
  void updateObjectParameters() {
    final VertexModel[] vertices = new VertexModel[4];

    vertices[0] = new VertexModel(this.vertex1X.getFloatValue(), this.vertex1Y.getFloatValue(), this.vertex1Z.getFloatValue());
    vertices[1] = new VertexModel(this.vertexX2.getFloatValue(), this.vertex2Y.getFloatValue(), this.vertex2Z.getFloatValue());
    vertices[2] = new VertexModel(this.vertex3X.getFloatValue(), this.vertex3Y.getFloatValue(), this.vertex3Z.getFloatValue());
    vertices[3] = new VertexModel(this.vertex4X.getFloatValue(), this.vertex4Y.getFloatValue(), this.vertex4Z.getFloatValue());

    final ColorModel color = this.colorSelector.getColor();

    this.polygon.setVertices(Arrays.asList(vertices[0], vertices[1], vertices[2], vertices[3]));
    this.polygon.setColor(color);
    this.polygon.setRotation(new RotationModel(this.rightVertexX.getFloatValue(), this.rightVertexY.getFloatValue(), this.rightVertexZ.getFloatValue()));
    this.polygon.setTranslation(new TranslationModel(this.leftVertexX.getFloatValue(), this.leftVertexY.getFloatValue(), this.leftVertexZ.getFloatValue()));
  }

  /**
   * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
   */
  private void detectPrim() {
    final VertexModel vertex1 = this.polygon.getVertex(0);
    this.vertex1X.setText("" + vertex1.getX()); //$NON-NLS-1$
    this.vertex1Y.setText("" + vertex1.getY()); //$NON-NLS-1$
    this.vertex1Z.setText("" + vertex1.getZ()); //$NON-NLS-1$

    final VertexModel vertex2 = this.polygon.getVertex(1);
    this.vertexX2.setText("" + vertex2.getX()); //$NON-NLS-1$
    this.vertex2Y.setText("" + vertex2.getY()); //$NON-NLS-1$
    this.vertex2Z.setText("" + vertex2.getZ()); //$NON-NLS-1$

    final VertexModel vertex3 = this.polygon.getVertex(2);
    this.vertex3X.setText("" + vertex3.getX()); //$NON-NLS-1$
    this.vertex3Y.setText("" + vertex3.getY()); //$NON-NLS-1$
    this.vertex3Z.setText("" + vertex3.getZ()); //$NON-NLS-1$

    final VertexModel vertex4 = this.polygon.getVertex(3);
    this.vertex4X.setText("" + vertex4.getX()); //$NON-NLS-1$
    this.vertex4Y.setText("" + vertex4.getY()); //$NON-NLS-1$
    this.vertex4Z.setText("" + vertex4.getZ()); //$NON-NLS-1$
    this.colorSelector.setColor(this.polygon.getColor());

    if (this.polygon.getRotation() != null) {
      this.rightVertexX.setText("" + this.polygon.getRotation().getX()); //$NON-NLS-1$
      this.rightVertexY.setText("" + this.polygon.getRotation().getY()); //$NON-NLS-1$
      this.rightVertexZ.setText("" + this.polygon.getRotation().getZ()); //$NON-NLS-1$
    } else {
      this.rightVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.rightVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.rightVertexZ.setText("" + 0.0); //$NON-NLS-1$
    }

    if (this.polygon.getTranslation() != null) {
      this.leftVertexX.setText("" + this.polygon.getTranslation().getX()); //$NON-NLS-1$
      this.leftVertexY.setText("" + this.polygon.getTranslation().getY()); //$NON-NLS-1$
      this.leftVertexZ.setText("" + this.polygon.getTranslation().getZ()); //$NON-NLS-1$
    } else {
      this.leftVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.leftVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.leftVertexZ.setText("" + 0.0); //$NON-NLS-1$
    }

  }
}
