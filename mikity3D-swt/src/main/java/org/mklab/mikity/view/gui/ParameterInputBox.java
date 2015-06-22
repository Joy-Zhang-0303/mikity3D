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
import org.eclipse.swt.widgets.Button;
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
  private Label label;
  private Label label1;
  private Text text;
  /** */
  boolean changed = false;

  /**
   * ラベルとテキストを作る コンストラクター
   * 
   * @param composite コンポジット
   * @param style スタイル
   * @param key キー
   * @param value 値
   */
  public ParameterInputBox(Composite composite, int style, String key, String value) {
    super(composite, style);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    this.label = new Label(this, SWT.NONE);
    this.label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.label.setAlignment(SWT.LEFT);
    this.label.setText(key);
    this.text = new Text(this, SWT.BORDER | SWT.RIGHT | style);
    this.text.setText(value);
    this.text.addModifyListener(new ModifyListener() {

      /**
       * {@inheritDoc}
       */
      public void modifyText(ModifyEvent arg0) {
        ParameterInputBox.this.changed = true;
      }
    });
    
    final GridData data1 = new GridData();
    data1.widthHint = 65;
    this.text.setLayoutData(data1);
    
    final GridData data2 = new GridData(GridData.FILL_HORIZONTAL);
    data2.horizontalSpan = 2;
    this.setLayoutData(data2);
  }

  /**
   * ラベルとボタンを作る コンストラクター
   * 
   * @param c コンポジット
   * @param lab ラベル
   * @param but ボタンのラベル
   */
  public ParameterInputBox(Composite c, String lab, String but) {
    super(c, SWT.NONE);
    final GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    this.label1 = new Label(this, SWT.NONE);
    this.label1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.label1.setAlignment(SWT.RIGHT);
    this.label1.setText(lab);
    final Button button = new Button(this, SWT.NONE);
    button.setText(but);
  }

  /**
   * コンストラクター
   * 
   * @param c コンポジット
   * @param style スタイル
   * @param value 値 
   */
  public ParameterInputBox(Composite c, int style, int value) {
    super(c, SWT.RIGHT);
    final GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    this.text = new Text(this, SWT.BORDER | SWT.RIGHT | style);
    final GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 65;
    this.text.setLayoutData(data);
    this.text.setText("" + value); //$NON-NLS-1$
  }

  /**
   * テキストボックスの文字をfloat型で返します。
   * 
   * @return テキストボックスの文字の値
   */
  public float getFloatValue() {
    return Float.parseFloat(this.text.getText());
  }

  /**
   * テキストボックスの中の数字を設定します。
   * 
   * @param value 値
   */
  public void setDoubleValue(double value) {
    this.text.setText("" + value); //$NON-NLS-1$
  }

  /**
   * テキストボックスの文字をdouble型で返す
   * 
   * @return Double.parseDouble(text.getText())
   */
  public double getDoubleValue() {
    return Double.parseDouble(this.text.getText());
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
  public void setText(String string) {
    if (string == null) {
      this.text.setText(Messages.getString("ParameterInputBox.0")); //$NON-NLS-1$
    }
    this.text.setText(string);
  }

  /**
   * テキストボックスの文字を返します。
   * 
   * @return テキストボックスの文字
   */
  public String getText() {
    return this.text.getText();
  }

  /**
   * ラベルの文字を設定します。
   * 
   * @param string 文字列
   */
  public void setLabelText(String string) {
    this.label.setText(string);
  }

  /**
   * ラベルの文字を返します。
   * 
   * @return ラベルの文字
   */
  public String getLabelText() {
    return this.label.getText();
  }

  /**
   * テキストボックスに 数字のみが入っているか判別します。
   * 
   * @return boolean テキストボックスに 数字のみが入っていればtrue、そうでなければfalse
   */
  public boolean containsOnlyNumbers() {
    try {
      Double.parseDouble(this.text.getText());
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}