/*
 * $Id: EditTrianglePolygonDialog.java,v 1.9 2008/02/29 13:51:56 morimune Exp $
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
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;

/**
 * 三角形ポリゴンを編集するダイアログを表すクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.9 $. 2008/02/29
 */
public class EditTrianglePolygonDialog {
  Shell parentShell;
  Shell sShell;
  TrianglePolygonModel primitive;
  String groupName;
  
  JoglModeler modeler;
  SceneGraphTree tree;
  
  ColorSelectorButton colorSelector;

  private ParameterInputBox vertex1X;
  private ParameterInputBox vertex1Y;
  private ParameterInputBox vertex1Z;
  
  private ParameterInputBox vertex2X;
  private ParameterInputBox vertex2Y;
  private ParameterInputBox vertex2Z;
  
  private ParameterInputBox vertex3X;
  private ParameterInputBox vertex3Y;
  private ParameterInputBox vertex3Z;
  
  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;

  /**
   * 新しく生成された<code>EditTrianglePolygonDialog</code>オブジェクトを初期化します。
   * 
   * @param parentShell 親のシェル
   * @param triangle ポリゴン
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public EditTrianglePolygonDialog(Shell parentShell, TrianglePolygonModel triangle, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    this.parentShell = parentShell;
    this.primitive = triangle;
    this.groupName = group.getName();
    this.tree = tree;
    this.modeler = modeler;
    
    this.tree.setIsModifyingObject(true);
    
    createSShell();
    
//    setParametersInDialog();
  }

  /**
   * 新しく生成された<code>EditTrianglePolygonDialog</code>オブジェクトを初期化します。
   */
  public EditTrianglePolygonDialog() {
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
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 740));
    this.sShell.setText(Messages.getString("EditTrianglePolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("EditTrianglePolygonDialog.1") + this.groupName); //$NON-NLS-1$
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
        EditTrianglePolygonDialog.this.tree.setIsModifyingObject(false);
      }
      
      public void shellActivated(ShellEvent arg0) {
        // nothing to do
      }
    });
  }

  @SuppressWarnings("unused")
  private void createParameterBoxes() {
    final Group parameterGroup = new Group(this.sShell, SWT.NONE);
    parameterGroup.setText(Messages.getString("EditTrianglePolygonDialog.18")); //$NON-NLS-1$
    setGridLayout(parameterGroup, 1);
    final GridLayout layout = new GridLayout(3, false);
    parameterGroup.setLayout(layout);

    final Label colorLabel = new Label(parameterGroup, SWT.LEFT);
    colorLabel.setText(Messages.getString("EditTrianglePolygonDialog.20")); //$NON-NLS-1$
    setGridLayout(colorLabel, 1);
    
    this.colorSelector = new ColorSelectorButton(parameterGroup);
    this.colorSelector.setColor(this.primitive.getColor());
    
    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);
    
    final TrianglePolygonModel polygon = this.primitive;
    
    final VertexModel vertex1 = polygon.getVertex(0);
    this.vertex1X = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.3"), "" + vertex1.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Y = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.4"), "" + vertex1.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex1Z = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.5"), "" + vertex1.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex2 = polygon.getVertex(1);
    this.vertex2X = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.6"), "" + vertex2.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Y = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.7"), "" + vertex2.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex2Z = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.8"), "" + vertex2.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    final VertexModel vertex3 = polygon.getVertex(2);    
    this.vertex3X = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.9"), "" + vertex3.getX());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Y = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.10"), "" + vertex3.getY());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.vertex3Z = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.11"), "" + vertex3.getZ());  //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    createTranslationBoxes(parameterGroup);
    
//    this.translationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.15"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
//    this.translationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.16"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
//    this.translationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.17"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    createRotationBoxes(parameterGroup);
    
//    this.rotationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.12"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
//    this.rotationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.13"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
//    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
//    this.rotationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditTrianglePolygonDialog.14"), "0.0");  //$NON-NLS-1$//$NON-NLS-2$
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
    okButton.setText(Messages.getString("EditTrianglePolygonDialog.35")); //$NON-NLS-1$
    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containOnlyNumbers()) {
          updateModdelParameters();
          EditTrianglePolygonDialog.this.tree.updateTree();
          EditTrianglePolygonDialog.this.modeler.updateDisplay();
          EditTrianglePolygonDialog.this.sShell.close();
        } else {
          final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditTrianglePolygonDialog.38")); //$NON-NLS-1$
          message.setText(Messages.getString("EditTrianglePolygonDialog.39")); //$NON-NLS-1$
          message.open();
        }
      }
    });

    final Button cancelButton = new Button(composite, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("EditTrianglePolygonDialog.40")); //$NON-NLS-1$
    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditTrianglePolygonDialog.41")); //$NON-NLS-1$
        message.setText(Messages.getString("EditTrianglePolygonDialog.42")); //$NON-NLS-1$
        int yesNo = message.open();
        if (yesNo == SWT.YES) {
          EditTrianglePolygonDialog.this.sShell.close();
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
          updateModdelParameters();
          EditTrianglePolygonDialog.this.tree.updateTree();
          EditTrianglePolygonDialog.this.modeler.updateDisplay();
        } else {
          final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditTrianglePolygonDialog.38")); //$NON-NLS-1$
          message.setText(Messages.getString("EditTrianglePolygonDialog.39")); //$NON-NLS-1$
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
    if (this.vertex2X.containsOnlyNumbers() == false) {
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
   * オブジェクトのパラメータを更新します。
   */
  public void updateModdelParameters() {
    final VertexModel[] vertices = new VertexModel[3];

    vertices[0] = new VertexModel(this.vertex1X.getFloatValue(), this.vertex1Y.getFloatValue(), this.vertex1Z.getFloatValue());
    vertices[1] = new VertexModel(this.vertex2X.getFloatValue(), this.vertex2Y.getFloatValue(), this.vertex2Z.getFloatValue());
    vertices[2] = new VertexModel(this.vertex3X.getFloatValue(), this.vertex3Y.getFloatValue(), this.vertex3Z.getFloatValue());

    final ColorModel color =this.colorSelector.getColor();

    this.primitive.setVertices(Arrays.asList(vertices[0], vertices[1], vertices[2]));
    this.primitive.setColor(color);
    this.primitive.setRotation(new RotationModel(this.rotationX.getFloatValue(), this.rotationY.getFloatValue(), this.rotationZ.getFloatValue()));
    this.primitive.setTranslation(new TranslationModel(this.translationX.getFloatValue(), this.translationY.getFloatValue(), this.translationZ.getFloatValue()));
  }

//  /**
//   * 各頂点の座標値を色を入れる　変更後の欄にはデフォルトで変更前の値を入力
//   */
//  private void setParametersInDialog() {
//    final VertexModel vertex1 = this.primitive.getVertex(0);
//    this.vertex1X.setStringValue("" + vertex1.getX()); //$NON-NLS-1$
//    this.vertex1Y.setStringValue("" + vertex1.getY()); //$NON-NLS-1$
//    this.vertex1Z.setStringValue("" + vertex1.getZ()); //$NON-NLS-1$
//
//    final VertexModel vertex2 = this.primitive.getVertex(1);
//    this.vertex2X.setStringValue("" + vertex2.getX()); //$NON-NLS-1$
//    this.vertex2Y.setStringValue("" + vertex2.getY()); //$NON-NLS-1$
//    this.vertex2Z.setStringValue("" + vertex2.getZ()); //$NON-NLS-1$
//
//    final VertexModel vertex3 = this.primitive.getVertex(2);
//    this.vertex3X.setStringValue("" + vertex3.getX()); //$NON-NLS-1$
//    this.vertex3Y.setStringValue("" + vertex3.getY()); //$NON-NLS-1$
//    this.vertex3Z.setStringValue("" + vertex3.getZ()); //$NON-NLS-1$
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
