/*
 * Created on 2005/02/09
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
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
import org.mklab.mikity.view.gui.ParameterInputBox;
import org.mklab.mikity.view.gui.UnitLabel;


/**
 * プリミティブの追加を行うダイアログを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.3 $.2005/02/09
 */
public class AddPrimitiveDialog {
  private Shell parentShell;
  Shell sShell;

  private ParameterInputBox parameter1;
  private ParameterInputBox parameter2;
  private ParameterInputBox parameter3;
  private Label unitLabel1;
  private Label unitLabel2;
  private Label unitLabel3;
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;
  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  private Combo colorCombo;
  private GroupModel targetGroup;
  private String angleUnit;
  private String lengthUnit;

  int selectedIndex = BOX;
  /** BOX */
  public static final int BOX = 0;
  /** CYLINDER */
  public static final int CYLINDER = 1;
  /** SPHERE */
  public static final int SPHERE = 2;
  /** CONE */
  public static final int CONE = 3;

  /**
   * 新しく生成された<code>AddPrimitiveDialog</code>オブジェクトを初期化します。
   * @param parentShell 親シェル
   * @param targetGroup グループ
   * @param selectedIndex プリミティブの選択
   */
  public AddPrimitiveDialog(Shell parentShell, GroupModel targetGroup, int selectedIndex) {
    this.parentShell = parentShell;
    this.targetGroup = targetGroup;
    this.selectedIndex = selectedIndex;
    this.angleUnit = UnitLabel.getUnit("modelAngle"); //$NON-NLS-1$
    this.lengthUnit = UnitLabel.getUnit("modelLength"); //$NON-NLS-1$
    createSShell();
    
    if (selectedIndex == BOX) {
      prepareBoxLabel();
    } else if (selectedIndex == CYLINDER) {
      prepareCylinderLabel();
    } else if (selectedIndex == SPHERE) {
      prepareSphereLabel();
    } else if (selectedIndex == CONE) {
      prepareConeLabel();
    }
  }

  /**
   * シェルを生成します。
   */
  private void createSShell() {
    this.sShell = new Shell(this.parentShell, SWT.RESIZE | SWT.APPLICATION_MODAL | SWT.NORMAL | SWT.BORDER | SWT.MAX | SWT.MIN | SWT.CLOSE);
    final GridLayout layout1 = new GridLayout();
    layout1.numColumns = 3;
    this.sShell.setSize(new org.eclipse.swt.graphics.Point(350, 550));
    this.sShell.setText(Messages.getString("AddPrimitiveDialog.0")); //$NON-NLS-1$
    this.sShell.setLayout(layout1);

    final Label groupLabel = new Label(this.sShell, SWT.LEFT);
    groupLabel.setText(Messages.getString("AddPrimitiveDialog.1") + this.targetGroup.getName()); //$NON-NLS-1$
    final GridData gLabelData = new GridData(GridData.FILL_HORIZONTAL);
    gLabelData.horizontalSpan = 3;
    groupLabel.setLayoutData(gLabelData);
    
    final GridData labelData2 = new GridData(GridData.FILL_HORIZONTAL);
    final Label colorLabel = new Label(this.sShell, SWT.LEFT);
    colorLabel.setText(Messages.getString("AddPrimitiveDialog.34")); //$NON-NLS-1$
    colorLabel.setLayoutData(labelData2);
    createColorCombo();

    this.parameter1 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.2"), "0.2"); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel1 = new Label(this.sShell, SWT.NONE);
    this.unitLabel1.setText(this.lengthUnit);

    this.parameter2 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.3"), "0.2"); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel2 = new Label(this.sShell, SWT.NONE);
    this.unitLabel2.setText(this.lengthUnit);

    this.parameter3 = new ParameterInputBox(this.sShell, SWT.NONE, Messages.getString("AddPrimitiveDialog.4"), "0.2"); //$NON-NLS-1$//$NON-NLS-2$
    this.unitLabel3 = new Label(this.sShell, SWT.NONE);
    this.unitLabel3.setText(this.lengthUnit);

    GridData translationData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group translationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    translationGroup.setText(Messages.getString("AddPrimitiveDialog.12")); //$NON-NLS-1$
    
    final GridLayout layout3 = new GridLayout();
    layout3.numColumns = 3;
    translationGroup.setLayout(layout3);
    translationData = new GridData(GridData.FILL_HORIZONTAL);
    translationData.horizontalSpan = 3;
    translationGroup.setLayoutData(translationData);

    this.translationX = new ParameterInputBox(translationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.13"), "0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLX = new Label(translationGroup, SWT.NONE);
    unitLabelLX.setText(this.lengthUnit + " "); //$NON-NLS-1$
    
    this.translationY = new ParameterInputBox(translationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.14"), "0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLY = new Label(translationGroup, SWT.NONE);
    unitLabelLY.setText(this.lengthUnit + " "); //$NON-NLS-1$
    
    this.translationZ = new ParameterInputBox(translationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.15"), "0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelLZ = new Label(translationGroup, SWT.NONE);
    unitLabelLZ.setText(this.lengthUnit + " "); //$NON-NLS-1$

    GridData rotationData = new GridData(GridData.FILL_HORIZONTAL);
    org.eclipse.swt.widgets.Group rotationGroup = new org.eclipse.swt.widgets.Group(this.sShell, SWT.NONE);
    rotationGroup.setText(Messages.getString("AddPrimitiveDialog.5")); //$NON-NLS-1$
    
    final GridLayout layout2 = new GridLayout();
    layout2.numColumns = 3;
    rotationGroup.setLayout(layout2);
    rotationData = new GridData(GridData.FILL_HORIZONTAL);
    rotationData.horizontalSpan = 3;
    rotationGroup.setLayoutData(rotationData);

    this.rotationX = new ParameterInputBox(rotationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.6"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRX = new Label(rotationGroup, SWT.NONE);
    unitLabelRX.setText(this.angleUnit + " "); //$NON-NLS-1$
    
    this.rotationY = new ParameterInputBox(rotationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.8"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRY = new Label(rotationGroup, SWT.NONE);
    unitLabelRY.setText(this.angleUnit + " "); //$NON-NLS-1$
    
    this.rotationZ = new ParameterInputBox(rotationGroup, SWT.NONE, Messages.getString("AddPrimitiveDialog.10"), "0.0"); //$NON-NLS-1$//$NON-NLS-2$
    final Label unitLabelRZ = new Label(rotationGroup, SWT.NONE);
    unitLabelRZ.setText(this.angleUnit + " "); //$NON-NLS-1$

    final Label spaceLabel = new Label(this.sShell, SWT.NONE);
    spaceLabel.setText(" "); //$NON-NLS-1$

    createButtons();
  }

  /**
   * ボタンを生成します。
   */
  private void createButtons() {
    final Button okButton = new Button(this.sShell, SWT.NONE);
    okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    okButton.setText(Messages.getString("AddPrimitiveDialog.16")); //$NON-NLS-1$
    okButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      /**
       * {@inheritDoc}
       */
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

        if (checkParamters()) {
          addPrimitive();
          AddPrimitiveDialog.this.sShell.close();
        } else {
          MessageBox messageBox = new MessageBox(AddPrimitiveDialog.this.sShell, SWT.ICON_WARNING);
          messageBox.setMessage(Messages.getString("AddPrimitiveDialog.17")); //$NON-NLS-1$
          messageBox.setText(Messages.getString("AddPrimitiveDialog.18")); //$NON-NLS-1$
        }
      }
    });

    final Button cancelButton = new Button(this.sShell, SWT.NONE);
    cancelButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    cancelButton.setText(Messages.getString("AddPrimitiveDialog.19")); //$NON-NLS-1$
    cancelButton.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        final MessageBox messageBox = new MessageBox(AddPrimitiveDialog.this.sShell, SWT.YES | SWT.NO | SWT.ICON_INFORMATION);
        messageBox.setMessage(Messages.getString("AddPrimitiveDialog.20")); //$NON-NLS-1$
        messageBox.setText(Messages.getString("AddPrimitiveDialog.21")); //$NON-NLS-1$
        int result = messageBox.open();
        if (result == SWT.YES) {
          AddPrimitiveDialog.this.sShell.close();
        }
      }
    });
  }

  /**
   * 数字以外が入力されていたらfalseを返す
   * 
   * @return boolean 数字以外が入力されていればfalse
   */
  boolean checkParamters() {
    if (this.parameter1.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.parameter2.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.parameter3.containsOnlyNumbers() == false) {
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
   * プリミティブを追加します。
   */
  void addPrimitive() {
    final RotationModel rotation;
    final TranslationModel translation;
    final String colorName;
    final ColorModel color;
    
    switch (this.selectedIndex) {
      case BOX:
        final BoxModel box = new BoxModel();
        box.setWidth(this.parameter1.getFloatValue());
        box.setHeight(this.parameter2.getFloatValue());
        box.setDepth(this.parameter3.getFloatValue());
        rotation = getRotation(); 
        if (rotation != null) {
          box.setRotation(getRotation());
        }
        translation = getTranslation();
        if (translation != null) {
          box.setTranslation(translation);
        }
        colorName = this.colorCombo.getText();
        color = new ColorModel(colorName);
        box.setColor(color);
        this.targetGroup.add(box);
        break;
      case CYLINDER:
        final CylinderModel cylinder = new CylinderModel();
        cylinder.setRadius(this.parameter1.getFloatValue());
        cylinder.setHeight(this.parameter2.getFloatValue());
        cylinder.setDivision(getDivision(this.parameter3));
        rotation = getRotation(); 
        if (rotation != null) {
          cylinder.setRotation(rotation);
        }
        translation = getTranslation();
        if (translation != null) {
          cylinder.setTranslation(translation);
        }
        colorName = this.colorCombo.getText();
        color = new ColorModel(colorName);
        cylinder.setColor(color);
        this.targetGroup.add(cylinder);
        break;
      case SPHERE:
        final SphereModel sphere = new SphereModel();
        sphere.setRadius(this.parameter1.getFloatValue());
        sphere.setDivision(getDivision(this.parameter2));
        rotation = getRotation(); 
        if (rotation != null) {
          sphere.setRotation(rotation);
        }
        translation = getTranslation();
        if (translation != null) {
          sphere.setTranslation(translation);
        }
        colorName = this.colorCombo.getText();
        color = new ColorModel(colorName);        
        sphere.setColor(color);
        this.targetGroup.add(sphere);
        break;
      case CONE:
        final ConeModel cone = new ConeModel();
        cone.setRadius(this.parameter1.getFloatValue());
        cone.setHeight(this.parameter2.getFloatValue());
        cone.setDivision(getDivision(this.parameter3));
        rotation = getRotation(); 
        if (rotation != null) {
          cone.setRotation(rotation);
        }
        translation = getTranslation();
        if (translation != null) {
          cone.setTranslation(translation);
        }
        colorName = this.colorCombo.getText();
        color = new ColorModel(colorName);        
        cone.setColor(color);
        this.targetGroup.add(cone);
        break;
      default:
        throw new IllegalArgumentException();
    }

  }

  /**
   * Rotationを設定します。
   * 
   * @param rotation
   * @return rot
   */
  private RotationModel getRotation() {
    final RotationModel rotation = new RotationModel();
    
    if (this.rotationX.getFloatValue() == 0 && this.rotationY.getFloatValue() == 0 && this.rotationZ.getFloatValue() == 0) {
      return null;
    }
    rotation.setX(this.rotationX.getFloatValue());
    rotation.setY(this.rotationY.getFloatValue());
    rotation.setZ(this.rotationZ.getFloatValue());
    return rotation;
  }

  /**
   * Locationを返します。
   * 
   * @param translation
   * @return Location
   */
  private TranslationModel getTranslation() {
    final TranslationModel translation = new TranslationModel();
    
    if (this.translationX.getFloatValue() == 0 && this.translationY.getFloatValue() == 0 && this.translationZ.getFloatValue() == 0) {
      return null;
    }
    translation.setX(this.translationX.getFloatValue());
    translation.setY(this.translationY.getFloatValue());
    translation.setZ(this.translationZ.getFloatValue());
    return translation;
  }

  /**
   * 分割数が3より小さいときに変わりに30にする
   * 
   * @param parameter
   * @return division
   */
  private int getDivision(ParameterInputBox parameter) {
    int division = (int)parameter.getDoubleValue();
    if (division < 3) {
      division = 3;
    }
    return division;
  }

  /**
   * shellを開く
   */
  public void open() {
    this.sShell.open();
    final Display display = this.sShell.getDisplay();
    while (!this.sShell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  class PComboCheck extends SelectionAdapter {
    /**
     * 選択されたプリミティブによってLabel,TextBoxを変更するための flagとなるselectedIndex
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
      final Combo combo = (Combo)e.widget;
      AddPrimitiveDialog.this.selectedIndex = combo.getSelectionIndex();

      if (AddPrimitiveDialog.this.selectedIndex == BOX) {
        prepareBoxLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == CYLINDER) {
        prepareCylinderLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == SPHERE) {
        prepareSphereLabel();
      } else if (AddPrimitiveDialog.this.selectedIndex == CONE) {
        prepareConeLabel();
      }
    }
  }

  /**
   * 選ばれているprimitiveがBoxのとき
   */
  void prepareBoxLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.22")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.23")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("AddPrimitiveDialog.24")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setVisible(true);
  }

  /**
   * Cylinderのとき
   */
  void prepareCylinderLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.25")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.26")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("AddPrimitiveDialog.27")); //$NON-NLS-1$
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    this.parameter3.setVisible(true);
    if (this.parameter3.getIntValue() < 3) {
      this.parameter3.setText("36"); //$NON-NLS-1$
    } else {
      this.parameter3.setText("" + this.parameter3.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * Sphereのとき
   */
  void prepareSphereLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.28")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.29")); //$NON-NLS-1$
    this.unitLabel2.setText(" "); //$NON-NLS-1$
    this.parameter3.setVisible(false);
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    if (this.parameter2.getIntValue() < 3) {
      this.parameter2.setText("36"); //$NON-NLS-1$
    } else {
      this.parameter2.setText("" + this.parameter2.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * Coneのとき
   */
  void prepareConeLabel() {
    this.parameter1.setLabelText(Messages.getString("AddPrimitiveDialog.31")); //$NON-NLS-1$
    this.parameter2.setLabelText(Messages.getString("AddPrimitiveDialog.32")); //$NON-NLS-1$
    this.unitLabel2.setText(this.lengthUnit + " "); //$NON-NLS-1$
    this.parameter3.setLabelText(Messages.getString("AddPrimitiveDialog.33")); //$NON-NLS-1$
    this.unitLabel3.setText(" "); //$NON-NLS-1$
    this.parameter3.setVisible(true);
    if (this.parameter3.getIntValue() < 3) {
      this.parameter3.setText("36"); //$NON-NLS-1$
    } else {
      this.parameter3.setText("" + this.parameter3.getIntValue()); //$NON-NLS-1$
    }
  }

  /**
   * コンボボックス colorCombo プリミティブの色を選択
   */
  private void createColorCombo() {
    this.colorCombo = ColorComboBoxFactory.create(this.sShell);
    this.colorCombo.setText("red"); //$NON-NLS-1$
    
//    this.colorCombo = new Combo(this.sShell, SWT.READ_ONLY);
//    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
//    gridData.horizontalSpan = 2;
//    this.colorCombo.setLayoutData(gridData);
//    final String[] colorNames = {"white", "black", "red", "lightGray", "darkGray", "pink", "orange", "yellow", "green", "magenta", "cyan", "blue"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
//    this.colorCombo.setItems(colorNames);
//    this.colorCombo.setText("red"); //$NON-NLS-1$
  }
}