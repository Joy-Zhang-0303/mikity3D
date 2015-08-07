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
  private QuadPolygonModel quad;
  private String[] colors = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$

  private String groupName;
  private Group afterGroup;

  private ColorComboBox colorCombo;

  private ParameterInputBox newVertex1X;
  private ParameterInputBox newVertex1Y;
  private ParameterInputBox newVertex1Z;
  
  private ParameterInputBox newVertexX2;
  private ParameterInputBox newVertex2Y;
  private ParameterInputBox newVertex2Z;
  
  private ParameterInputBox newVertex3X;
  private ParameterInputBox newVertex3Y;
  private ParameterInputBox newVertex3Z;
  
  private ParameterInputBox newVertex4X;
  private ParameterInputBox newVertex4Y;
  private ParameterInputBox newVertex4Z;
  
  private ParameterInputBox newLeftVertexX;
  private ParameterInputBox newLeftVertexY;
  private ParameterInputBox newLeftVertexZ;
  
  private ParameterInputBox newRightVertexX;
  private ParameterInputBox newRightVertexY;
  private ParameterInputBox newRightVertexZ;

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
    this.quad = quad;
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
    
    this.colorCombo = new ColorComboBox(this.afterGroup, this.colors);
    this.colorCombo.createColorCombo();
    
    Label label9 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label9, 3);
    
    this.newVertex1X = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.3"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex1Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.4"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex1Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.5"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label5 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.newVertexX2 = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.6"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex2Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.7"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex2Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.8"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label6 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    this.newVertex3X = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.21"), "0.0");    //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex3Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.9"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex3Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.10"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    final Label label7 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label7, 3);

    this.newVertex4X = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.11"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex4Y = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.12"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newVertex4Z = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.13"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$

    Label label8 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label8, 3);

    this.newLeftVertexX = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLeftVertexY = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.18"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
    this.newLeftVertexZ = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.19"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelLength"); //$NON-NLS-1$
   
    final Label labelR2 = new Label(this.afterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(labelR2, 3);

    this.newRightVertexX = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRightVertexY = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(this.afterGroup, "modelAngle"); //$NON-NLS-1$
    this.newRightVertexZ = new ParameterInputBox(this.afterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.16"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
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
    if (this.newVertex1X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex1Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex1Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertexX2.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex2Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex2Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex3X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex3Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex3Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex4X.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex4Y.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newVertex4Z.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newRightVertexX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newRightVertexY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newRightVertexZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newLeftVertexX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newLeftVertexY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.newLeftVertexZ.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * パラメータを変更する
   */
  void updateObjectParameters() {
    final VertexModel[] newVertices = new VertexModel[4];

    newVertices[0] = new VertexModel(this.newVertex1X.getFloatValue(), this.newVertex1Y.getFloatValue(), this.newVertex1Z.getFloatValue());
    newVertices[1] = new VertexModel(this.newVertexX2.getFloatValue(), this.newVertex2Y.getFloatValue(), this.newVertex2Z.getFloatValue());
    newVertices[2] = new VertexModel(this.newVertex3X.getFloatValue(), this.newVertex3Y.getFloatValue(), this.newVertex3Z.getFloatValue());
    newVertices[3] = new VertexModel(this.newVertex4X.getFloatValue(), this.newVertex4Y.getFloatValue(), this.newVertex4Z.getFloatValue());

    final String colorName = this.colorCombo.getColorComboBox().getText();
    final ColorModel color = new ColorModel(colorName);

    this.quad.setVertices(Arrays.asList(newVertices[0], newVertices[1], newVertices[2], newVertices[3]));
    this.quad.setColor(color);
    this.quad.setRotation(new RotationModel(this.newRightVertexX.getFloatValue(), this.newRightVertexY.getFloatValue(), this.newRightVertexZ.getFloatValue()));
    this.quad.setTranslation(new TranslationModel(this.newLeftVertexX.getFloatValue(), this.newLeftVertexY.getFloatValue(), this.newLeftVertexZ.getFloatValue()));
  }

  /**
   * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
   */
  private void detectPrim() {
    final VertexModel vertex0 = this.quad.getVertex(0);
    this.newVertex1X.setText("" + vertex0.getX()); //$NON-NLS-1$
    this.newVertex1Y.setText("" + vertex0.getY()); //$NON-NLS-1$
    this.newVertex1Z.setText("" + vertex0.getZ()); //$NON-NLS-1$

    final VertexModel vertex1 = this.quad.getVertex(1);
    this.newVertexX2.setText("" + vertex1.getX()); //$NON-NLS-1$
    this.newVertex2Y.setText("" + vertex1.getY()); //$NON-NLS-1$
    this.newVertex2Z.setText("" + vertex1.getZ()); //$NON-NLS-1$

    final VertexModel vertex2 = this.quad.getVertex(2);
    this.newVertex3X.setText("" + vertex2.getX()); //$NON-NLS-1$
    this.newVertex3Y.setText("" + vertex2.getY()); //$NON-NLS-1$
    this.newVertex3Z.setText("" + vertex2.getZ()); //$NON-NLS-1$

    final VertexModel vertex3 = this.quad.getVertex(3);
    this.newVertex4X.setText("" + vertex3.getX()); //$NON-NLS-1$
    this.newVertex4Y.setText("" + vertex3.getY()); //$NON-NLS-1$
    this.newVertex4Z.setText("" + vertex3.getZ()); //$NON-NLS-1$
    this.colorCombo.getColorComboBox().setText(this.quad.getColor().getName());

    if (this.quad.getRotation() != null) {
      this.newRightVertexX.setText("" + this.quad.getRotation().getX()); //$NON-NLS-1$
      this.newRightVertexY.setText("" + this.quad.getRotation().getY()); //$NON-NLS-1$
      this.newRightVertexZ.setText("" + this.quad.getRotation().getZ()); //$NON-NLS-1$
    } else {
      this.newRightVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.newRightVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.newRightVertexZ.setText("" + 0.0); //$NON-NLS-1$
    }

    if (this.quad.getTranslation() != null) {
      this.newLeftVertexX.setText("" + this.quad.getTranslation().getX()); //$NON-NLS-1$
      this.newLeftVertexY.setText("" + this.quad.getTranslation().getY()); //$NON-NLS-1$
      this.newLeftVertexZ.setText("" + this.quad.getTranslation().getZ()); //$NON-NLS-1$
    } else {
      this.newLeftVertexX.setText("" + 0.0); //$NON-NLS-1$
      this.newLeftVertexY.setText("" + 0.0); //$NON-NLS-1$
      this.newLeftVertexZ.setText("" + 0.0); //$NON-NLS-1$
    }

  }
}
