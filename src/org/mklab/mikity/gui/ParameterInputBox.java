/*
 * Created on 2004/12/15
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui;

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
 * 数値の入力を行うボックスに関するクラス
 * 
 * @author miki
 * @version $Revision: 1.11 $.2004/12/15
 */
public class ParameterInputBox extends Composite {

  private Label label;
  private Label label1;
  private Text text;
  boolean changed = false;

  /**
   * ラベルとテキストを作る コンストラクター
   * 
   * @param composite
   * @param style
   *        keyをラベルに、valueをテキストボックスに
   * @param key
   * @param value
   */
  public ParameterInputBox(Composite composite, int style, String key, String value) {
    super(composite, style);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    this.label = new Label(this, SWT.NONE);
    this.label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.label.setAlignment(SWT.RIGHT);
    this.label.setText(key);
    this.text = new Text(this, SWT.BORDER | SWT.RIGHT | style);
    this.text.setText(value);
    this.text.addModifyListener(new ModifyListener() {

      public void modifyText(ModifyEvent arg0) {
        ParameterInputBox.this.changed = true;
      }
    });
    GridData data = new GridData();
    data.widthHint = 65;
    this.text.setLayoutData(data);
    data = new GridData(GridData.FILL_HORIZONTAL);
    data.horizontalSpan = 2;
    this.setLayoutData(data);
  }

  /**
   * ラベルとボタンを作る コンストラクター
   * 
   * @param c
   * @param lab
   * @param but
   *        labをラベルに、butをボタンのテキストに
   */
  public ParameterInputBox(Composite c, String lab, String but) {
    super(c, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    this.label1 = new Label(this, SWT.NONE);
    this.label1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    this.label1.setAlignment(SWT.RIGHT);
    this.label1.setText(lab);
    Button button = new Button(this, SWT.NONE);
    button.setText(but);
  }

  /**
   * コンストラクター
   * 
   * @param c
   * @param style
   * @param value
   */
  public ParameterInputBox(Composite c, int style, int value) {
    super(c, SWT.RIGHT);
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    this.text = new Text(this, SWT.BORDER | SWT.RIGHT | style);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 65;
    this.text.setLayoutData(data);
    this.text.setText("" + value); //$NON-NLS-1$
  }

  /**
   * テキストボックスの文字をfloat型で返す
   * 
   * @return Float.parseFloat(text.getText())
   */
  public float getFloatValue() {
    return Float.parseFloat(this.text.getText());
  }

  /**
   * テキストボックスの中の数字をセット
   * 
   * @param value
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
   * テキストボックスの文字をInt型で返す
   * 
   * @return (int)getDoubleValue()
   */
  public int getIntValue() {
    return (int)getDoubleValue();
  }

  /**
   * テキストボックスの文字をセット
   * 
   * @param string
   */
  public void setText(String string) {
    if (string == null) {
      this.text.setText("0.0です");
    }
    this.text.setText(string);
  }

  /**
   * テキストボックスの文字を返す
   * 
   * @return text.getText()
   */
  public String getText() {
    return this.text.getText();
  }

  /**
   * ラベルの文字をセットする
   * 
   * @param string
   */
  public void setLabelText(String string) {
    this.label.setText(string);
  }

  /**
   * ラベルの文字を返す
   * 
   * @return label.getText()
   */
  public String getLabelText() {
    return this.label.getText();
  }

  /**
   * テキストボックスに入っている値が 数字ではないときにfalseを返す
   * 
   * @return boolean
   */
  public boolean checkParam() {
    try {
      Double.parseDouble(this.text.getText());
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}