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
    label = new Label(this, SWT.NONE);
    label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    label.setAlignment(SWT.RIGHT);
    label.setText(key);
    text = new Text(this, SWT.BORDER | SWT.RIGHT | style);
    text.setText(value);
    text.addModifyListener(new ModifyListener() {

      public void modifyText(ModifyEvent arg0) {
        changed = true;
      }
    });
    GridData data = new GridData();
    data.widthHint = 65;
    text.setLayoutData(data);
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
    label1 = new Label(this, SWT.NONE);
    label1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    label1.setAlignment(SWT.RIGHT);
    label1.setText(lab);
    Button button = new Button(this, SWT.NONE);
    button.setText(but);
  }

  /**
   * コンストラクター
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
    text = new Text(this, SWT.BORDER | SWT.RIGHT | style);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.widthHint = 65;
    text.setLayoutData(data);
    text.setText("" + value);
  }

  /**
   * テキストボックスの文字をfloat型で返す
   * @return Float.parseFloat(text.getText())
   */
  public float getFloatValue() {
    return Float.parseFloat(text.getText());
  }

  /**
   * テキストボックスの中の数字をセット
   * @param value 
   */
  public void setDoubleValue(double value) {
    text.setText("" + value);
  }

  /**
   * テキストボックスの文字をdouble型で返す
   * @return Double.parseDouble(text.getText())
   */
  public double getDoubleValue() {
    return Double.parseDouble(text.getText());
  }

  /**
   * テキストボックスの文字をInt型で返す
   * @return (int)getDoubleValue()
   */
  public int getIntValue() {
    return (int)getDoubleValue();
  }

  /**
   * テキストボックスの文字をセット
   * @param string 
   */
  public void setText(String string) {
    if(string == null){
      text.setText("0.0です");
    }
    text.setText(string);
  }

  /**
   * テキストボックスの文字を返す
   * @return text.getText()
   */
  public String getText() {
    return text.getText();
  }
  
  /**
   * ラベルの文字をセットする
   * @param string 
   */
  public void setLabelText(String string) {
    label.setText(string);
  }

  /**
   * ラベルの文字を返す
   * @return label.getText()
   */
  public String getLabelText() {
    return label.getText();
  }

  /**
   * テキストボックスに入っている値が
   * 数字ではないときにfalseを返す
   * @return boolean
   */
  public boolean checkParam() {
    try {
      Double.parseDouble(text.getText());
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}