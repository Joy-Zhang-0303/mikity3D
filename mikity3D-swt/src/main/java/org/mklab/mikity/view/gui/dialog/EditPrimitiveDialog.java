/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.view.gui.ParameterInputBox;
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
  private String[] colors = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
  private ColorComboBox colorCombo;
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

  /**
   * コンストラクター
   * 
   * @param parent 親のシェル
   * @param primitive プリミティブ
   * @param group グループ
   */
  public EditPrimitiveDialog(Shell parent, Object primitive, org.mklab.mikity.model.xml.simplexml.model.Group group) {
    this.parentShell = parent;
    this.primitive = primitive;
    this.groupName = group.getName();
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
   * シェルを開く
   */
  public void open() {
    this.sShell.open();
    Display display = this.sShell.getDisplay();
    while (!this.sShell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * シェルを作成
   */
  @SuppressWarnings({"unused"})
  private void createSShell() {
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 550));
    this.sShell.setText(Messages.getString("EditPrimitiveDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout);

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
    colorLabel.setText("color"); //$NON-NLS-1$
    final GridData gridData = new GridData();
    gridData.minimumWidth = 200;
    colorLabel.setLayoutData(gridData);
    setGridLayout(colorLabel, 1);

    this.colorCombo = new ColorComboBox(parameterGroup, this.colors);
    this.colorCombo.createColorCombo();
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
    setGridLayout(comp, 2);

    final GridLayout compLayout = new GridLayout(2, true);
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
        message.setText(Messages.getString("EditPrimitiveDialog.27")); //$NON-NLS-1$
        int yesNo = message.open();
        if (yesNo == SWT.YES) {
          EditPrimitiveDialog.this.sShell.close();
        }
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
    if (this.primitive instanceof XMLBox) {
      final XMLBox box = (XMLBox)this.primitive;

      box.setColor(this.colorCombo.getColorComboBox().getText());
      box.setWidth(this.parameter1.getFloatValue());
      box.setHeight(this.parameter2.getFloatValue());
      box.setDepth(this.parameter3.getFloatValue());
      
      final Translation translation = getTranslation();
      if (translation.isZero() == false) {
        box.setTranslation(getTranslation());
      }
      final Rotation rotation = getRotation();
      if (rotation.isZero() == false) {
        box.setRotation(getRotation());
      }
    } else if (this.primitive instanceof XMLCylinder) {
      final XMLCylinder cylinder = (XMLCylinder)this.primitive;
      
      cylinder.setColor(this.colorCombo.getColorComboBox().getText());
      cylinder.setRadius(this.parameter1.getFloatValue());
      cylinder.setHeight(this.parameter2.getFloatValue());
      cylinder.setDivision(this.parameter3.getIntValue());

      final Translation translation = getTranslation();
      if (translation.isZero() == false) {
        cylinder.setTranslation(getTranslation());
      }
      final Rotation rotation = getRotation();
      if (rotation.isZero() == false) {
        cylinder.setRotation(getRotation());
      }
    } else if (this.primitive instanceof XMLSphere) {
      final XMLSphere sphere = (XMLSphere)this.primitive;

      sphere.setColor(this.colorCombo.getColorComboBox().getText());
      sphere.setRadius(this.parameter1.getFloatValue());
      sphere.setDivision(this.parameter2.getIntValue());

      final Translation translation = getTranslation();
      if (translation.isZero() == false) {
        sphere.setTranslation(getTranslation());
      }
      final Rotation rotation = getRotation();
      if (rotation.isZero() == false) {
        sphere.setRotation(getRotation());
      }
    } else if (this.primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)this.primitive;
      cone.setColor(this.colorCombo.getColorComboBox().getText());
      cone.setRadius(this.parameter1.getFloatValue());
      cone.setHeight(this.parameter2.getFloatValue());
      cone.setDivision(this.parameter3.getIntValue());

      final Translation translation = getTranslation();
      if (translation.isZero() == false) {
        cone.setTranslation(getTranslation());
      }
      final Rotation rotation = getRotation();
      if (rotation.isZero() == false) {
        cone.setRotation(getRotation());
      }
    }
  }

  /**
   * Rotationを設定 受け取ったRotationを変更に応じて設定
   * 
   * @return rotation
   */
  private Rotation getRotation() {
    final Rotation rotation = new Rotation();
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
  private Translation getTranslation() {
    final Translation translation = new Translation();
    translation.setX(this.translationX.getFloatValue());
    translation.setY(this.translationY.getFloatValue());
    translation.setZ(this.translationZ.getFloatValue());
    return translation;
  }

  /**
   * primitiveの型を判断し、ダイアログにパラメータを設定します。
   */
  private void setParametersInDialog() {
    if (this.primitive instanceof XMLBox) {
      final XMLBox box = (XMLBox)this.primitive;
      this.parameter1.setText("" + box.getWidth()); //$NON-NLS-1$
      this.parameter2.setText("" + box.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + box.getDepth()); //$NON-NLS-1$
      final Rotation rotation = box.getRotation();
      final Translation translation = box.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setBoxLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.28")); //$NON-NLS-1$
      this.colorCombo.getColorComboBox().setText(box.getColor());
    } else if (this.primitive instanceof XMLCylinder) {
      final XMLCylinder cylinder = (XMLCylinder)this.primitive;
      this.parameter1.setText("" + cylinder.getRadius()); //$NON-NLS-1$
      this.parameter2.setText("" + cylinder.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + cylinder.getDivision()); //$NON-NLS-1$
      final Rotation rotation = cylinder.getRotation();
      final Translation translation = cylinder.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setCylinderLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.29")); //$NON-NLS-1$
      this.colorCombo.getColorComboBox().setText(cylinder.getColor());
    } else if (this.primitive instanceof XMLSphere) {
      final XMLSphere sphere = (XMLSphere)this.primitive;
      this.parameter1.setText("" + sphere.getRadius()); //$NON-NLS-1$
      this.parameter2.setText("" + sphere.getDivision()); //$NON-NLS-1$
      final Rotation rotation = sphere.getRotation();
      final Translation translation = sphere.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setSphereLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.30")); //$NON-NLS-1$
      this.colorCombo.getColorComboBox().setText(sphere.getColor());
    } else if (this.primitive instanceof XMLCone) {
      final XMLCone cone = (XMLCone)this.primitive;
      this.parameter1.setText("" + cone.getRadisu()); //$NON-NLS-1$
      this.parameter2.setText("" + cone.getHeight()); //$NON-NLS-1$
      this.parameter3.setText("" + cone.getDivision()); //$NON-NLS-1$
      final Rotation rotation = cone.getRotation();
      final Translation translation = cone.getTranslation();
      if (rotation != null) {
        setRotationInDialog(rotation);
      }
      if (translation != null) {
        setTranslationInDialog(translation);
      }
      setConeLabel();
      this.primitiveLabel.setText(Messages.getString("EditPrimitiveDialog.31")); //$NON-NLS-1$
      this.colorCombo.getColorComboBox().setText(cone.getColor());
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

    int division = 0;
    if (this.parameter3.getIntValue() < 3) {
      division = 30;
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

    int division = 0;
    if (this.parameter2.getIntValue() < 3) {
      division = 30;
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
      division = 30;
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
  private void setRotationInDialog(Rotation rotation) {
    this.rotationX.setText("" + rotation.getX()); //$NON-NLS-1$
    this.rotationY.setText("" + rotation.getY()); //$NON-NLS-1$
    this.rotationZ.setText("" + rotation.getZ()); //$NON-NLS-1$
  }

  /**
   * 並進量をダイアログに設定します。
   * 
   * @param translation 並進量
   */
  private void setTranslationInDialog(Translation translation) {
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