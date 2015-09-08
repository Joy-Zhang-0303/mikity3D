/*
 * Created on 2004/12/15
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


/**
 * 数値の入力を行うボックスに関するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.11 $.2004/12/15
 */
public class ParameterInputBox extends Composite {
  /** 名前用のラベル。 */
  private Label nameLabel;
  //private Label label1;
  /** 値用のテキスト。 */
  private Text valueText;
  /** 値が変呼されていればtrue。 */
  boolean isChanged = false;

  /**
   * 新しく生成された<code>ParameterInputBox</code>オブジェクトを初期化します。
   * @param composite コンポジット
   * @param style スタイル
   * @param name 名前
   * @param value 値
   */
  public ParameterInputBox(Composite composite, int style, String name, String value) {
    super(composite, style);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    
    this.nameLabel = new Label(this, SWT.NONE);
    this.nameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.nameLabel.setAlignment(SWT.LEFT);
    this.nameLabel.setText(name);
    
    this.valueText = new Text(this, SWT.BORDER | SWT.RIGHT | style);
    this.valueText.setText(value);
    this.valueText.setFocus();
    this.valueText.addModifyListener(new ModifyListener() {

      /**
       * {@inheritDoc}
       */
      public void modifyText(ModifyEvent arg0) {
        ParameterInputBox.this.isChanged = true;
      }
    });
    
    final GridData data1 = new GridData();
    data1.widthHint = 65;
    this.valueText.setLayoutData(data1);
    
    final GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
    data2.horizontalSpan = 2;
    setLayoutData(data2);
  }

  /**
   * コンストラクター
   * 
   * @param composite コンポジット
   * @param style スタイル
   * @param value 値 
   */
  public ParameterInputBox(Composite composite, int style, int value) {
    super(composite, SWT.RIGHT);
    final GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    this.valueText = new Text(this, SWT.BORDER | SWT.RIGHT | style);

    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 65;
    this.valueText.setLayoutData(data);
    this.valueText.setText("" + value); //$NON-NLS-1$

    this.valueText.addModifyListener(new ModifyListener() {

      /**
       * {@inheritDoc}
       */
      public void modifyText(ModifyEvent arg0) {
        ParameterInputBox.this.isChanged = true;
      }
    });
  }

  /**
   * テキストボックスの文字をfloat型で返します。
   * 
   * @return テキストボックスの文字の値
   */
  public float getFloatValue() {
    return Float.parseFloat(this.valueText.getText());
  }

//  /**
//   * テキストボックスの中の数字を設定します。
//   * 
//   * @param value 値
//   */
//  public void setDoubleValue(double value) {
//    this.valueText.setText("" + value); //$NON-NLS-1$
//  }

  /**
   * テキストボックスの文字をdouble型で返す
   * 
   * @return Double.parseDouble(text.getText())
   */
  public double getDoubleValue() {
    return Double.parseDouble(this.valueText.getText());
  }

  /**
   * テキストボックスの文字をInt型で返します。
   * 
   * @return テキストボックスの文字の値
   */
  public int getIntValue() {
    return (int)getDoubleValue();
  }

  /**
   * テキストボックスの文字を設定します。
   * 
   * @param string 文字列
   */
  public void setStringValue(String string) {
    if (string == null) {
      this.valueText.setText(Messages.getString("ParameterInputBox.0")); //$NON-NLS-1$
    }
    this.valueText.setText(string);
  }
  
  /**
   * テキストボックスの幅を設定します。
   * @param width テキストボックスの幅
   */
  public void setTextWidth(int width) {
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = width;
    this.valueText.setLayoutData(data);
  }

  /**
   * テキストボックスの文字を返します。
   * 
   * @return テキストボックスの文字
   */
  public String getStringValue() {
    return this.valueText.getText();
  }

  /**
   * ラベルの文字を設定します。
   * 
   * @param string 文字列
   */
  public void setName(String string) {
    this.nameLabel.setText(string);
  }

//  /**
//   * ラベルの文字を返します。
//   * 
//   * @return ラベルの文字
//   */
//  public String getLabelText() {
//    return this.name.getText();
//  }

  /**
   * テキストボックスに 数字のみが入っているか判別します。
   * 
   * @return boolean テキストボックスに 数字のみが入っていればtrue、そうでなければfalse
   */
  public boolean containsOnlyNumbers() {
    try {
      Double.parseDouble(this.valueText.getText());
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
  
  /**
   * 値が変更されたか判定します。
   * 
   * @return 値が変更されていればtrue
   */
  public boolean isChanged() {
    return this.isChanged;
  }
  
  /**
   * 値が変更されたか設定します。
   * 
   * @param isChanged 値が変更されていればtrue
   */
  public void setIsChanged(boolean isChanged) {
    this.isChanged = isChanged;
  }
}