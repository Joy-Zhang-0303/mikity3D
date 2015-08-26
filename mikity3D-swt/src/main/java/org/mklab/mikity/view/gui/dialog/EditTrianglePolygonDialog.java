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
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
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
  PrimitiveModel primitive;
  String groupName;
  
  JoglModeler modeler;
  SceneGraphTree tree;
  
  ColorSelectorButton colorSelector;
  ParameterInputBox alpha;
  
  Label primitiveType;

  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;

  private ParameterInputBox vertex1X;
  private ParameterInputBox vertex1Y;
  private ParameterInputBox vertex1Z;
  
  private ParameterInputBox vertex2X;
  private ParameterInputBox vertex2Y;
  private ParameterInputBox vertex2Z;
  
  private ParameterInputBox vertex3X;
  private ParameterInputBox vertex3Y;
  private ParameterInputBox vertex3Z;

  /**
   * 新しく生成された<code>EditTrianglePolygonDialog</code>オブジェクトを初期化します。
   * 
   * @param parentShell 親のシェル
   * @param primitive ポリゴン
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public EditTrianglePolygonDialog(Shell parentShell, TrianglePolygonModel primitive, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    this.parentShell = parentShell;
    this.primitive = primitive;
    this.groupName = group.getName();
    this.tree = tree;
    this.modeler = modeler;
    
    this.tree.setIsModifyingObject(true);
    
    createSShell();
  }

//  /**
//   * 新しく生成された<code>EditTrianglePolygonDialog</code>オブジェクトを初期化します。
//   */
//  public EditTrianglePolygonDialog() {
//  // nothing to do
//  }

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
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 780));
    this.sShell.setText(Messages.getString("EditTrianglePolygonDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

    addShellListener();
    
    createParameterBoxes();

    createButtonComposite();
    
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(400, 780));
  }
  
  private void createParameterBoxes() {
    this.primitiveType = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primitiveType, 2);
    
    final Group parameterGroup = new Group(this.sShell, SWT.NONE);
    parameterGroup.setText(Messages.getString("EditTrianglePolygonDialog.18")); //$NON-NLS-1$
    setGridLayout(parameterGroup, 1);
    
    final GridLayout parameterLayout = new GridLayout(3, false);
    parameterGroup.setLayout(parameterLayout);

    final Label colorLabel = new Label(parameterGroup, SWT.LEFT);
    colorLabel.setText(Messages.getString("EditTrianglePolygonDialog.20")); //$NON-NLS-1$
    final GridData gridData = new GridData();
    gridData.minimumWidth = 200;
    colorLabel.setLayoutData(gridData);
    setGridLayout(colorLabel, 1);
    
    this.colorSelector = new ColorSelectorButton(parameterGroup);
    this.colorSelector.setColor(this.primitive.getColor());
    
    this.alpha = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("AbstractEditPrimitiveDialog.1"), "" + this.primitive.getColor().getAlpha()); //$NON-NLS-1$ //$NON-NLS-2$
    
    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);
    
    createParameterBoxes(parameterGroup);

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    createTranslationBoxes(parameterGroup);

    setGridLayout(new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL), 3);

    createRotationBoxes(parameterGroup);
  }

  /**
   * @param parameterGroup パラメータグループ
   */
  @SuppressWarnings("unused")
  public void createParameterBoxes(final Group parameterGroup) {
    this.primitiveType.setText(Messages.getString("EditTrianglePolygonDialog.21")); //$NON-NLS-1$
    
    final TrianglePolygonModel polygon = (TrianglePolygonModel)this.primitive;
    
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
        if (containOnlyNumbers() == false) {
          final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditTrianglePolygonDialog.38")); //$NON-NLS-1$
          message.setText(Messages.getString("EditTrianglePolygonDialog.39")); //$NON-NLS-1$
          message.open();
          return;
        }

        updatePrimitiveParameters();
        EditTrianglePolygonDialog.this.tree.updateTree();
        EditTrianglePolygonDialog.this.modeler.updateDisplay();
        EditTrianglePolygonDialog.this.sShell.close();
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
        if (containOnlyNumbers() == false) {
          final MessageBox message = new MessageBox(EditTrianglePolygonDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditTrianglePolygonDialog.38")); //$NON-NLS-1$
          message.setText(Messages.getString("EditTrianglePolygonDialog.39")); //$NON-NLS-1$
          message.open();
          return;
        }

        updatePrimitiveParameters();
        EditTrianglePolygonDialog.this.tree.updateTree();
        EditTrianglePolygonDialog.this.modeler.updateDisplay();
      }
    });
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean
   */
  boolean containOnlyNumbers() {
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

    return true;
  }

  /**
   * オブジェクトのパラメータを更新します。
   */
  public void updatePrimitiveParameters() {
    final ColorModel color =this.colorSelector.getColor();
    color.setAlpha(this.alpha.getIntValue());
    this.primitive.setColor(color);
    this.primitive.setTranslation(getTranslation());
    this.primitive.setRotation(getRotation());

    updateModelParameters();
  }

  public void updateModelParameters() {
    final TrianglePolygonModel polygon = (TrianglePolygonModel)this.primitive;
    
    final VertexModel[] vertices = new VertexModel[3];

    vertices[0] = new VertexModel(this.vertex1X.getFloatValue(), this.vertex1Y.getFloatValue(), this.vertex1Z.getFloatValue());
    vertices[1] = new VertexModel(this.vertex2X.getFloatValue(), this.vertex2Y.getFloatValue(), this.vertex2Z.getFloatValue());
    vertices[2] = new VertexModel(this.vertex3X.getFloatValue(), this.vertex3Y.getFloatValue(), this.vertex3Z.getFloatValue());

    polygon.setVertices(Arrays.asList(vertices[0], vertices[1], vertices[2]));
  }
  
  /**
   * Rotationを設定 受け取ったRotationを変更に応じて設定
   * 
   * @return rotation
   */
  private RotationModel getRotation() {
    final RotationModel rotation = new RotationModel();
    rotation.setX(this.rotationX.getFloatValue());
    rotation.setY(this.rotationY.getFloatValue());
    rotation.setZ(this.rotationZ.getFloatValue());
    return rotation;
  }

  /**
   * Translationを返します。
   * 
   * @return translation
   */
  private TranslationModel getTranslation() {
    final TranslationModel translation = new TranslationModel();
    translation.setX(this.translationX.getFloatValue());
    translation.setY(this.translationY.getFloatValue());
    translation.setZ(this.translationZ.getFloatValue());
    return translation;
  }
  
  public boolean isChanged() {
    if (this.vertex1X.isChanged()) {
      return true;
    }
    if (this.vertex1Y.isChanged()) {
      return true;
    }
    if (this.vertex1Z.isChanged()) {
      return true;
    }
    
    if (this.vertex2X.isChanged()) {
      return true;
    }
    if (this.vertex2Y.isChanged()) {
      return true;
    }
    if (this.vertex2Z.isChanged()) {
      return true;
    }
    
    if (this.vertex3X.isChanged()) {
      return true;
    }
    if (this.vertex3Y.isChanged()) {
      return true;
    }
    if (this.vertex3Z.isChanged()) {
      return true;
    }
    
    return false;
  }
}
