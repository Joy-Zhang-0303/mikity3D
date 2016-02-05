/*
 * Created on 2016/01/25
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * パラメータを入力するためのボックスを現すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/25
 */
public class ParameterInputBox extends TableRow {

  /** 名前用のラベル。 */
  private TextView nameLabel;
  /** 値用のテキスト。 */
  private EditText valueText;
  /** 単位用のラベル。 */
  private TextView unitLabel;

  /**
   * 新しく生成された<code>ParameterInputBox</code>オブジェクトを初期化します。
   * 
   * @param context Context
   * @param attrs Attributes
   * @param onKeyListener キーリスナー
   * @param textWatcher Text watcher
   */
  public ParameterInputBox(Context context, AttributeSet attrs, OnKeyListener onKeyListener, TextWatcher textWatcher) {
    super(context, attrs);
    init(context, onKeyListener, textWatcher);
  }

  /**
   * 新しく生成された<code>ParameterInputBox</code>オブジェクトを初期化します。
   * 
   * @param context Context
   * @param onKeyListener キーリスナー
   * @param textWatcher Text watcher
   */
  public ParameterInputBox(Context context, OnKeyListener onKeyListener, TextWatcher textWatcher) {
    super(context);
    init(context, onKeyListener, textWatcher);
  }

  /**
   * @param context
   */
  private void init(Context context, OnKeyListener onKeyListener, TextWatcher textWatcher) {
    TableRow layout = (TableRow)LayoutInflater.from(context).inflate(R.layout.parameter_input_box, this, true);
    this.nameLabel = (TextView)layout.findViewById(R.id.parameterName);

    this.valueText  = (EditText)layout.findViewById(R.id.parameterValue);
    this.valueText.setOnKeyListener(onKeyListener);
    this.valueText.addTextChangedListener(textWatcher);
    
    this.unitLabel = (TextView)layout.findViewById(R.id.parameterUnit);
  }
  
  /**
   * パラメータをfloat型で返します。
   * 
   * @return テキストボックスの文字の値
   */
  public float getFloatValue() {
    return Float.parseFloat(this.valueText.getText().toString());
  }
  
  /**
   * パラメータをdouble型で返す
   * 
   * @return Double.parseDouble(text.getText())
   */
  public double getDoubleValue() {
    return Double.parseDouble(this.valueText.getText().toString());
  }

  /**
   * パラメータをint型で返します。
   * 
   * @return テキストボックスの文字の値
   */
  public int getIntValue() {
    return Integer.parseInt(this.valueText.getText().toString());
  }
  
  /**
   * パラメータをString型で返します。
   * 
   * @return テキストボックスの文字
   */
  public String getStringValue() {
    return this.valueText.getText().toString();
  }
  
  /**
   * テキストボックスの文字を設定します。
R   * 
   * @param string 文字列
   */
  public void setValue(String string) {
    if (string == null) {
      this.valueText.setText("0.0"); //$NON-NLS-1$
    }
    this.valueText.setText(string);
  }
  
  /**
   * テキストボックスの幅を設定します。
   * @param width テキストボックスの幅
   */
  public void setTextWidth(int width) {
    this.valueText.setWidth(width);
  }

  /**
   * 名前の文字を設定します。
   * 
   * @param name 名前
   */
  public void setName(String name) {
    this.nameLabel.setText(name);
  }
  
  /**
   * 名前の文字を設定します。
   * 
   * @param id 名前のID
   */
  public void setName(int id) {
    this.nameLabel.setText(id);
  }
  
  /**
   * 単位の文字を設定します。
   * 
   * @param unit 単位
   */
  public void setUnit(String unit) {
    this.unitLabel.setText(unit);
  }
  
  
  /**
   * 単位の文字を設定します。
   * 
   * @param id 単位のID
   */
  public void setUnit(int id) {
    this.unitLabel.setText(id);
  }
  
  /**
   * テキストボックスに 数字のみが入っているか判別します。
   * 
   * @return boolean テキストボックスに 数字のみが入っていればtrue、そうでなければfalse
   */
  public boolean containsOnlyNumbers() {
    try {
      Double.parseDouble(this.valueText.getText().toString());
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
  
  /**
   * 値を編集可能か設定します。
   * 
   * @param editable 編集可能ならばtrue
   */
  public void setEditable(boolean editable) {
    if (editable == false) {
      this.valueText.setInputType(InputType.TYPE_NULL);
    } else {
      this.valueText.setInputType(InputType.TYPE_CLASS_TEXT);
    }
  }
}
