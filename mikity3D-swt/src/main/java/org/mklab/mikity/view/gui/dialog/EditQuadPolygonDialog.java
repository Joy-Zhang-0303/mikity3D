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
 * 四角形ポリゴンを編集するダイアログを表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.7 $. 2007/11/15
 */
public class EditQuadPolygonDialog {
  Shell parentShell;
  Shell sShell;
  QuadPolygonModel primitive;
  String groupName;

  JoglModeler modeler;
  SceneGraphTree tree;

  ColorSelectorButton colorSelector;

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
  
  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;

  /**
   * 新しく生成された<code>EditQuadPolygonDialog</code>オブジェクトを初期化します。
   * @param parentShell 親のシェル
   * @param quad ポリゴン
   * @param group グループ
   * @param tree シーングラフツリー 
   * @param modeler モデラー
   */
  public EditQuadPolygonDialog(Shell parentShell, QuadPolygonModel quad, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    this.parentShell = parentShell;
    this.primitive = quad;
    this.groupName = group.getName();
    this.tree = tree;
    this.modeler = modeler;
    
    this.tree.setIsModifyingObject(true);
    
    createSShell();
    
    //setParametersinDialog();
  }

  /**
   * 
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

    createParameterBoxes();

    createButtonComposite();
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

  @SuppressWarnings("unused")
  private void createParameterBoxes() {
    final Group parameterGroup = new Group(this.sShell, SWT.NONE);
    parameterGroup.setText(Messages.getString("EditQuadPolygonDialog.20")); //$NON-NLS-1$
    setGridLayout(parameterGroup, 1);
    final GridLayout afterLayout = new GridLayout(3, false);
    parameterGroup.setLayout(afterLayout);

    final Label colorLabel = new Label(parameterGroup, SWT.LEFT);
    colorLabel.setText(Messages.getString("EditQuadPolygonDialog.22")); //$NON-NLS-1$
    setGridLayout(colorLabel, 1);
    
    this.colorSelector = new ColorSelectorButton(parameterGroup);
    this.colorSelector.setColor(this.primitive.getColor());
    
    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final QuadPolygonModel polygon = this.primitive;
    
    final VertexModel vertex1 = polygon.getVertex(0);
    this.vertex1X = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.3"), "" + vertex1.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Y = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.4"), "" + vertex1.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Z = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.5"), "" + vertex1.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex2 = polygon.getVertex(1);
    this.vertexX2 = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.6"), "" +vertex2.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Y = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.7"),  "" + vertex2.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Z = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.8"), "" + vertex2.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex3 = polygon.getVertex(2);
    this.vertex3X = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.21"), "" + vertex3.getX());    //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Y = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.9"), "" + vertex3.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Z = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.10"), "" + vertex3.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex4 = polygon.getVertex(3);
    this.vertex4X = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.11"), "" + vertex4.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex4Y = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.12"), "" + vertex4.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex4Z = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.13"), "" + vertex4.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    createTranslationBoxes(parameterGroup);
    
//    this.translationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
//    this.translationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.18"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
//    this.translationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.19"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
   
    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    createRotationBoxes(parameterGroup);
    
//    this.rotationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
//    this.rotationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
//    this.rotationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditQuadPolygonDialog.16"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
  }
  
  @SuppressWarnings("unused")
  private void createTranslationBoxes(Group parameterGroup) {
    final TranslationModel translation = this.primitive.getTranslation();
    
    final String x;
    final String y;
    final String z;
    if (translation != null) {
      x = "" + translation.getX(); //$NON-NLS-1$
      y = "" + translation.getY(); //$NON-NLS-1$
      z = "" + translation.getZ(); //$NON-NLS-1$
    } else {
      x = "0"; //$NON-NLS-1$
      y = "0"; //$NON-NLS-1$
      z = "0"; //$NON-NLS-1$
    }

    this.translationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.6"), x); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.7"), y); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.8"), z); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
  }

  
  @SuppressWarnings("unused")
  private void createRotationBoxes(Group parameterGroup) {
    final RotationModel rotation = this.primitive.getRotation();
    
    final String x;
    final String y;
    final String z;
    
    if (rotation != null) {
      x = "" + rotation.getX(); //$NON-NLS-1$
      y = "" + rotation.getY(); //$NON-NLS-1$
      z = "" + rotation.getZ(); //$NON-NLS-1$
    } else {
      x = "0"; //$NON-NLS-1$
      y = "0"; //$NON-NLS-1$
      z = "0"; //$NON-NLS-1$
    }
    
    this.rotationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.3"), x); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.4"), y); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.5"), z); //$NON-NLS-1$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
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
  private void createButtonComposite() {
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
          updateModelParameters();
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
          updateModelParameters();
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
    if (this.rotationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZ.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZ.containsOnlyNumbers() == false) {
      return false;
    }
    return true;
  }

  /**
   * パラメータを変更する
   */
  void updateModelParameters() {
    final VertexModel[] vertices = new VertexModel[4];

    vertices[0] = new VertexModel(this.vertex1X.getFloatValue(), this.vertex1Y.getFloatValue(), this.vertex1Z.getFloatValue());
    vertices[1] = new VertexModel(this.vertexX2.getFloatValue(), this.vertex2Y.getFloatValue(), this.vertex2Z.getFloatValue());
    vertices[2] = new VertexModel(this.vertex3X.getFloatValue(), this.vertex3Y.getFloatValue(), this.vertex3Z.getFloatValue());
    vertices[3] = new VertexModel(this.vertex4X.getFloatValue(), this.vertex4Y.getFloatValue(), this.vertex4Z.getFloatValue());

    this.primitive.setVertices(Arrays.asList(vertices[0], vertices[1], vertices[2], vertices[3]));
    
    final ColorModel color = this.colorSelector.getColor();
    this.primitive.setColor(color);
    this.primitive.setRotation(new RotationModel(this.rotationX.getFloatValue(), this.rotationY.getFloatValue(), this.rotationZ.getFloatValue()));
    this.primitive.setTranslation(new TranslationModel(this.translationX.getFloatValue(), this.translationY.getFloatValue(), this.translationZ.getFloatValue()));
  }

//  /**
//   * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
//   */
//  private void setParametersinDialog() {
//    final VertexModel vertex1 = this.primitive.getVertex(0);
//    this.vertex1X.setStringValue("" + vertex1.getX()); //$NON-NLS-1$
//    this.vertex1Y.setStringValue("" + vertex1.getY()); //$NON-NLS-1$
//    this.vertex1Z.setStringValue("" + vertex1.getZ()); //$NON-NLS-1$
//
//    final VertexModel vertex2 = this.primitive.getVertex(1);
//    this.vertexX2.setStringValue("" + vertex2.getX()); //$NON-NLS-1$
//    this.vertex2Y.setStringValue("" + vertex2.getY()); //$NON-NLS-1$
//    this.vertex2Z.setStringValue("" + vertex2.getZ()); //$NON-NLS-1$
//
//    final VertexModel vertex3 = this.primitive.getVertex(2);
//    this.vertex3X.setStringValue("" + vertex3.getX()); //$NON-NLS-1$
//    this.vertex3Y.setStringValue("" + vertex3.getY()); //$NON-NLS-1$
//    this.vertex3Z.setStringValue("" + vertex3.getZ()); //$NON-NLS-1$
//
//    final VertexModel vertex4 = this.primitive.getVertex(3);
//    this.vertex4X.setStringValue("" + vertex4.getX()); //$NON-NLS-1$
//    this.vertex4Y.setStringValue("" + vertex4.getY()); //$NON-NLS-1$
//    this.vertex4Z.setStringValue("" + vertex4.getZ()); //$NON-NLS-1$
//    
//    this.colorSelector.setColor(this.primitive.getColor());
//
//    if (this.primitive.getRotation() != null) {
//      this.rotationX.setStringValue("" + this.primitive.getRotation().getX()); //$NON-NLS-1$
//      this.rotationY.setStringValue("" + this.primitive.getRotation().getY()); //$NON-NLS-1$
//      this.rotationZ.setStringValue("" + this.primitive.getRotation().getZ()); //$NON-NLS-1$
//    } else {
//      this.rotationX.setStringValue("" + 0.0); //$NON-NLS-1$
//      this.rotationY.setStringValue("" + 0.0); //$NON-NLS-1$
//      this.rotationZ.setStringValue("" + 0.0); //$NON-NLS-1$
//    }
//
//    if (this.primitive.getTranslation() != null) {
//      this.translationX.setStringValue("" + this.primitive.getTranslation().getX()); //$NON-NLS-1$
//      this.translationY.setStringValue("" + this.primitive.getTranslation().getY()); //$NON-NLS-1$
//      this.translationZ.setStringValue("" + this.primitive.getTranslation().getZ()); //$NON-NLS-1$
//    } else {
//      this.translationX.setStringValue("" + 0.0); //$NON-NLS-1$
//      this.translationY.setStringValue("" + 0.0); //$NON-NLS-1$
//      this.translationZ.setStringValue("" + 0.0); //$NON-NLS-1$
//    }
//  }
}
