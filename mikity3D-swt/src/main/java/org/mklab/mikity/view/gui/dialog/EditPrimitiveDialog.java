/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.SceneGraphTree;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * プリミティブの編集を行うクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/09
 */
public class EditPrimitiveDialog {

  private Shell parentShell;
  /** */
  Shell sShell;
  private Object primitive;
  private String groupName;

  private ColorSelectorButton colorSelector;
  private Label primitiveLabel;
  private Label unitLabel1;
  private Label unitLabel2;
  private Label unitLabel3;

  private ParameterInputBox parameter1;
  private ParameterInputBox parameter2;
  private ParameterInputBox parameter3;

  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;

  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  
  /** 変更されていればtrue */
  private boolean isChanged = false;
  
  JoglModeler modeler;
  
  SceneGraphTree tree;

  /**
   * コンストラクター
   * 
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public EditPrimitiveDialog(Shell parent, Object primitive, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    this.parentShell = parent;
    this.primitive = primitive;
    this.groupName = group.getName();
    this.tree = tree;
    this.modeler = modeler;
    
    this.tree.setIsModifyingObject(true);
    
    createSShell();
    setParametersInDialog();
  }

  /**
   * コンストラクター
   */
  public EditPrimitiveDialog() {
    // nothing to do
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
        EditPrimitiveDialog.this.tree.setIsModifyingObject(false);
      }
      
      public void shellActivated(ShellEvent arg0) {
        // nothing to do
      }
    });
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
  @SuppressWarnings({"unused"})
  private void createSShell() {
    //this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 550));
    this.sShell.setText(Messages.getString("EditPrimitiveDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);
    
    addShellListener();

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("EditPrimitiveDialog.1") + this.groupName); //$NON-NLS-1$
    setGridLayout(groupLabel, 2);

    this.primitiveLabel = new Label(this.sShell, SWT.NONE);
    setGridLayout(this.primitiveLabel, 2);

    Group parameterGroup = new Group(this.sShell, SWT.NONE);
    parameterGroup.setText(Messages.getString("EditPrimitiveDialog.9")); //$NON-NLS-1$
    setGridLayout(parameterGroup, 1);

    final GridLayout newValueLayout = new GridLayout(3, false);
    parameterGroup.setLayout(newValueLayout);
    
    final Label colorLabel = new Label(parameterGroup, SWT.LEFT);
    colorLabel.setText(Messages.getString("EditPrimitiveDialog.10")); //$NON-NLS-1$
    final GridData gridData = new GridData();
    gridData.minimumWidth = 200;
    colorLabel.setLayoutData(gridData);
    setGridLayout(colorLabel, 1);

    this.colorSelector = new ColorSelectorButton(parameterGroup);
    
    final Label spaceLabel = new Label(parameterGroup, SWT.NONE);
    spaceLabel.setText(" "); //$NON-NLS-1$
    setGridLayout(spaceLabel, 1);
    
    this.parameter1 = new ParameterInputBox(parameterGroup, SWT.NONE, "→", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel1 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel1.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel1, 1);

    this.parameter2 = new ParameterInputBox(parameterGroup, SWT.NONE, "→", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel2 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel2.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel2, 1);

    this.parameter3 = new ParameterInputBox(parameterGroup, SWT.NONE, "→", ""); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel3 = new Label(parameterGroup, SWT.NONE);
    this.unitLabel3.setText(UnitLabel.getUnit("modelLength")); //$NON-NLS-1$
    setGridLayout(this.unitLabel3, 1);
    final Label label5 = new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label5, 3);

    this.translationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.6"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.7"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    this.translationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.8"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelLength"); //$NON-NLS-1$
    final Label label6 = new Label(parameterGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
    setGridLayout(label6, 3);

    this.rotationX = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.3"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationY = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.4"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$
    this.rotationZ = new ParameterInputBox(parameterGroup, SWT.NONE, Messages.getString("EditPrimitiveDialog.5"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    new UnitLabel(parameterGroup, "modelAngle"); //$NON-NLS-1$

    createButtonComposite();
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
   * 変更を決定するButtonを作成します。
   */
  private void createButtonComposite() {
    final Composite comp = new Composite(this.sShell, SWT.NONE);
    setGridLayout(comp, 3);

    final GridLayout compLayout = new GridLayout(3, true);
    comp.setLayout(compLayout);
    
    final Button okButton = new Button(comp, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("EditPrimitiveDialog.20")); //$NON-NLS-1$
    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        if (containsOnlyNumbers() == false) {
          final MessageBox message = new MessageBox(EditPrimitiveDialog.this.sShell, SWT.ICON_WARNING);
          message.setMessage(Messages.getString("EditPrimitiveDialog.23")); //$NON-NLS-1$
          message.setText(Messages.getString("EditPrimitiveDialog.24")); //$NON-NLS-1$
          message.open();
          return;
        }

        updatePrimitiveParameters();
        EditPrimitiveDialog.this.tree.updateTree();
        EditPrimitiveDialog.this.modeler.updateDisplay();
        EditPrimitiveDialog.this.sShell.close();
      }
    });

    final Button cancelButton = new Button(comp, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("EditPrimitiveDialog.25")); //$NON-NLS-1$
    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        final MessageBox message = new MessageBox(EditPrimitiveDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        message.setMessage(Messages.getString("EditPrimitiveDialog.26")); //$NON-NLS-1$
        //message.setText(Messages.getString("EditPrimitiveDialog.27")); //$NON-NLS-1$
        int yesNo = message.open();
        if (yesNo == SWT.YES) {
          EditPrimitiveDialog.this.sShell.close();
        }
      }
    });
    
    final Button applyButton = new Button(comp, SWT.NONE);
    applyButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    applyButton.setText(Messages.getString("EditPrimitiveDialog.11")); //$NON-NLS-1$
    applyButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        updatePrimitiveParameters();
        //setParametersInDialog();
        EditPrimitiveDialog.this.tree.updateTree();
        EditPrimitiveDialog.this.modeler.updateDisplay();
      }

    });
  }

  /**
   * 数字のみが入力されているか判定します。
   * 
   * @return boolean 数字のみが入力されていればtrue、そうでなければfalse
   */
  boolean containsOnlyNumbers() {
    if (this.parameter1.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.parameter2.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.parameter3.isVisible() && this.parameter3.containsOnlyNumbers() == false) {
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
   * プリミティブのパラメータを更新します。
   */
  void updatePrimitiveParameters() {
    if (this.primitive instanceof BoxModel) {
      final BoxModel box = (BoxModel)this.primitive;
      
      final ColorModel color = this.colorSelector.getColor();
      box.setColor(color);
      box.setWidth(this.parameter1.getFloatValue());
      box.setHeight(this.parameter2.getFloatValue());
      box.setDepth(this.parameter3.getFloatValue());
      box.setTranslation(getTranslation());
      box.setRotation(getRotation());
    } else if (this.primitive instanceof CylinderModel) {
      final CylinderModel cylinder = (CylinderModel)this.primitive;
      
      final ColorModel color = this.colorSelector.getColor();
      cylinder.setColor(color);
      cylinder.setRadius(this.parameter1.getFloatValue());
      cylinder.setHeight(this.parameter2.getFloatValue());
      cylinder.setDivision(this.parameter3.getIntValue());
      cylinder.setTranslation(getTranslation());
      cylinder.setRotation(getRotation());
    } else if (this.primitive instanceof SphereModel) {
      final SphereModel sphere = (SphereModel)this.primitive;

      final ColorModel color = this.colorSelector.getColor();
      sphere.setColor(color);
      sphere.setRadius(this.parameter1.getFloatValue());
      sphere.setDivision(this.parameter2.getIntValue());
      sphere.setTranslation(getTranslation());
      sphere.setRotation(getRotation());
    } else if (this.primitive instanceof ConeModel) {
      final ConeModel cone = (ConeModel)this.primitive;

      final ColorModel color = this.colorSelector.getColor();
      cone.setColor(color);
      cone.setRadius(this.parameter1.getFloatValue());
      cone.setHeight(this.parameter2.getFloatValue());
      cone.setDivision(this.parameter3.getIntValue());
      cone.setTranslation(getTranslation());
      cone.setRotation(getRotation());
    }
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

  /**
   * primitiveの型を判断し、ダイアログにパラメータを設定します。
   */
  void setParametersInDialog() {
    if (this.primitive instanceof BoxModel) {
      final BoxModel box = (BoxModel)this.primitive;
      this.parameter1.setText("" + box.getWidth()); //$NON-NLS-1$
      this.parameter2.setText("" + box.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + box.getDepth()); //$NON-NLS-1$
      final RotationModel rotation = box.getRotation();
      final TranslationModel translation = box.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setBoxLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.28")); //$NON-NLS-1$
      this.colorSelector.setColor(box.getColor());
    } else if (this.primitive instanceof CylinderModel) {
      final CylinderModel cylinder = (CylinderModel)this.primitive;
      this.parameter1.setText("" + cylinder.getRadius()); //$NON-NLS-1$
      this.parameter2.setText("" + cylinder.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + cylinder.getDivision()); //$NON-NLS-1$
      final RotationModel rotation = cylinder.getRotation();
      final TranslationModel translation = cylinder.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setCylinderLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.29")); //$NON-NLS-1$
      this.colorSelector.setColor(cylinder.getColor());
    } else if (this.primitive instanceof SphereModel) {
      final SphereModel sphere = (SphereModel)this.primitive;
      this.parameter1.setText("" + sphere.getRadius()); //$NON-NLS-1$
      this.parameter2.setText("" + sphere.getDivision()); //$NON-NLS-1$
      final RotationModel rotation = sphere.getRotation();
      final TranslationModel translation = sphere.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setSphereLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.30")); //$NON-NLS-1$
      this.colorSelector.setColor(sphere.getColor());
    } else if (this.primitive instanceof ConeModel) {
      final ConeModel cone = (ConeModel)this.primitive;
      this.parameter1.setText("" + cone.getRadisu()); //$NON-NLS-1$
      this.parameter2.setText("" + cone.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + cone.getDivision()); //$NON-NLS-1$
      final RotationModel rotation = cone.getRotation();
      final TranslationModel translation = cone.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setConeLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
      this.colorSelector.setColor(cone.getColor());
    }
  }

  /**
   * primitiveがBoxのとき
   */
  public void setBoxLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.32")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.33")); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("EditPrimitiveDialog.34")); //$NON-NLS-1$
    
    this.parameter3.setVisible(true);
    this.unitLabel2.setVisible(true);
    this.unitLabel3.setVisible(true);
  }

  /**
   * primitiveがCylinderのとき
   */
  public void setCylinderLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.35")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.36")); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("EditPrimitiveDialog.37")); //$NON-NLS-1$

    int division;
    if (this.parameter3.getIntValue() < 3) {
      division = 3;
    } else {
      division = this.parameter3.getIntValue();
    }
    this.parameter3.setText("" + division); //$NON-NLS-1$

    this.parameter3.setVisible(true);
    this.unitLabel2.setVisible(true);
    this.unitLabel3.setVisible(false);
  }

  /**
   * primitiveがSphereのとき
   */
  public void setSphereLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.38")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.39")); //$NON-NLS-1$

    int division;
    if (this.parameter2.getIntValue() < 3) {
      division = 3;
    } else {
      division = this.parameter2.getIntValue();
    }
    this.parameter2.setText("" + division); //$NON-NLS-1$

    this.parameter3.setVisible(false);
    this.unitLabel2.setVisible(false);
    this.unitLabel3.setVisible(false);
  }

  /**
   * primitiveがConeのとき
   */
  public void setConeLabel() {
    this.parameter1.setLabelText(Messages.getString("EditPrimitiveDialog.40")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("EditPrimitiveDialog.41")); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("EditPrimitiveDialog.42")); //$NON-NLS-1$

    int division = 0;
    if (this.parameter3.getIntValue() < 3) {
      division = 3;
    } else {
      division = this.parameter3.getIntValue();
    }
    this.parameter3.setText("" + division); //$NON-NLS-1$

    this.parameter3.setVisible(true);
    this.unitLabel2.setVisible(true);
    this.unitLabel3.setVisible(false);
  }

  /**
   * 角度をダイアログに設定します。
   * 
   * @param rotation 角度
   */
  private void setRotationInDialog(RotationModel rotation) {
    this.rotationX.setText("" + rotation.getX()); //$NON-NLS-1$
    this.rotationY.setText("" + rotation.getY()); //$NON-NLS-1$
    this.rotationZ.setText("" + rotation.getZ()); //$NON-NLS-1$
  }

  /**
   * 並進量をダイアログに設定します。
   * 
   * @param translation 並進量
   */
  private void setTranslationInDialog(TranslationModel translation) {
    this.translationX.setText("" + translation.getX()); //$NON-NLS-1$
    this.translationY.setText("" + translation.getY()); //$NON-NLS-1$
    this.translationZ.setText("" + translation.getZ()); //$NON-NLS-1$
  }
  
  /**
   * 変更されているか判定します。
   * @return 変更されている場合true
   */
  public boolean isChanged() {
    return this.isChanged;
  }

  /**
   * 変更されているか設定します。
   * @param isChanged 変更されている場合true
   */
  public void setChanged(final boolean isChanged) {
    this.isChanged = isChanged;
  }
}