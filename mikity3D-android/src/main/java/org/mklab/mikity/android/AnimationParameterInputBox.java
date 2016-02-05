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
 * アニメーションパラメータを入力するためのボックスを現すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/25
 */
public class AnimationParameterInputBox extends TableRow {

  /** 名前用のラベル。 */
  private TextView nameLabel;
  /** 値1用のテキスト。 */
  private EditText valueText1;
  /** 値2用のテキスト。 */
  private EditText valueText2;

  /**
   * 新しく生成された<code>AnimationParameterInputBox</code>オブジェクトを初期化します。
   * @param context Context
   * @param attrs Attributes
   * @param onKeyListener Key listener 
   * @param textWatcher Text watcher
   */
  public AnimationParameterInputBox(Context context, AttributeSet attrs, OnKeyListener onKeyListener, TextWatcher textWatcher) {
    super(context, attrs);
    init(context, onKeyListener, textWatcher);
  }

  /**
   * 新しく生成された<code>AnimationParameterInputBox</code>オブジェクトを初期化します。
   * 
   * @param context Context
   * @param onKeyListener Key listener
   * @param textWatcher Text watcher
   */
  public AnimationParameterInputBox(Context context, OnKeyListener onKeyListener, TextWatcher textWatcher) {
    super(context);
    init(context, onKeyListener, textWatcher);
  }

  /**
   * @param context
   */
  private void init(Context context, OnKeyListener onKeyListener, TextWatcher textWatcher) {
    TableRow layout = (TableRow)LayoutInflater.from(context).inflate(R.layout.animation_parameter_input_box, this, true);
    this.nameLabel = (TextView)layout.findViewById(R.id.parameterName);
    
    this.valueText1  = (EditText)layout.findViewById(R.id.parameterValue1);
    this.valueText1.setOnKeyListener(onKeyListener);
    this.valueText1.addTextChangedListener(textWatcher);

    this.valueText2  = (EditText)layout.findViewById(R.id.parameterValue2);
    this.valueText2.setOnKeyListener(onKeyListener);
    this.valueText2.addTextChangedListener(textWatcher);
  }
  
  /**
   * パラメータ1をfloat型で返します。
   * 
   * @return パラメータ1
   */
  public float getFloatValue1() {
    return Float.parseFloat(this.valueText1.getText().toString());
  }
  
  /**
   * パラメータ1をdouble型で返す
   * 
   * @return パラメータ1
   */
  public double getDoubleValue1() {
    return Double.parseDouble(this.valueText1.getText().toString());
  }

  /**
   * パラメータ1をint型で返します。
   * 
   * @return パラメータ1
   */
  public int getIntValue1() {
    return Integer.parseInt(this.valueText1.getText().toString());
  }
  
  /**
   * パラメータ1をString型で返します。
   * 
   * @return パラメータ1
   */
  public String getStringValue1() {
    return this.valueText1.getText().toString();
  }
  
  /**
   * テキストボックス1の文字を設定します。
   * 
   * @param string 文字列
   */
  public void setValue1(String string) {
    if (string == null) {
      this.valueText1.setText("0.0"); //$NON-NLS-1$
    }
    this.valueText1.setText(string);
  }
  
  /**
   * テキストボックス1の幅を設定します。
   * 
   * @param width テキストボックス1の幅
   */
  public void setTextWidth1(int width) {
    this.valueText1.setWidth(width);
  }
  
  /**
   * パラメータ2をfloat型で返します。
   * 
   * @return パラメータ2
   */
  public float getFloatValue2() {
    return Float.parseFloat(this.valueText2.getText().toString());
  }
  
  /**
   * パラメータ2をdouble型で返す
   * 
   * @return パラメータ2
   */
  public double getDoubleValue2() {
    return Double.parseDouble(this.valueText2.getText().toString());
  }

  /**
   * パラメータ2をint型で返します。
   * 
   * @return テキストボックス2の文字の値
   */
  public int getIntValue2() {
    return Integer.parseInt(this.valueText2.getText().toString());
  }
  
  /**
   * パラメータ2をString型で返します。
   * 
   * @return テキストボックス2の文字
   */
  public String getStringValue2() {
    return this.valueText2.getText().toString();
  }
  
  /**
   * テキストボックス2の文字を設定します。
   * 
   * @param string 文字列
   */
  public void setValue2(String string) {
    if (string == null) {
      this.valueText2.setText("0.0"); //$NON-NLS-1$
    }
    this.valueText2.setText(string);
  }
  
  /**
   * テキストボックス2の幅を設定します。
   * 
   * @param width テキストボックス2の幅
   */
  public void setTextWidth2(int width) {
    this.valueText2.setWidth(width);
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
   * テキストボックスに 数字のみが入っているか判別します。
   * 
   * @return boolean テキストボックスに 数字のみが入っていればtrue、そうでなければfalse
   */
  public boolean containsOnlyNumbers() {
    try {
      Double.parseDouble(this.valueText2.getText().toString());
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
      this.valueText1.setInputType(InputType.TYPE_NULL);
      this.valueText2.setInputType(InputType.TYPE_NULL);
    } else {
      this.valueText1.setInputType(InputType.TYPE_CLASS_TEXT);
      this.valueText2.setInputType(InputType.TYPE_CLASS_TEXT);
    }
  }
}
