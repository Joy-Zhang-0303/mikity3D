/*
 * Created on 2016/01/31
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.AlertDialogFragment;
import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.android.apis.graphics.ColorPickerDialog;


/**
 * オブジェクトを編集するエディタを表すフラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/31
 */
public abstract class AbstractObjectEditor extends Fragment implements ObjectEditor, OnKeyListener, TextWatcher {

  ObjectModel object;
  OpenglesModeler modeler;
  SceneGraphTree tree;

  TextView objectType;

  int colorValue;
  private Button colorButton;
  ParameterInputBox colorAlpha;

  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;

  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;

  /** 値が変更されていればtrue。 */
  boolean isChanged = false;

  /** 保存ボタン。 */
  Button saveButton;

  /**
   * 新しく生成された<code>AbstractObjectEditorFragment</code>オブジェクトを初期化します。
   * 
   * @param object オブジェクト
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public AbstractObjectEditor(ObjectModel object, SceneGraphTree tree, OpenglesModeler modeler) {
    this.object = object;
    this.tree = tree;
    this.modeler = modeler;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, @SuppressWarnings("unused") Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_object_editor, container, false);

    this.objectType = (TextView)view.findViewById(R.id.objectType);

    final TableLayout parameters = ((TableLayout)view.findViewById(R.id.layout_parameters));
    if (parameters != null) {
      parameters.removeAllViews();
      parameters.setStretchAllColumns(true);
    }

    createColorBoxes(parameters);
    createParameterBoxes(parameters);
    createTranslationBoxes(parameters);
    createRotationBoxes(parameters);

    // Next line should be located after setting the parameters in the boxes
    createButtonComposite(view);

    return view;
  }

  private void createButtonComposite(final View view) {
    final Button backButton = (Button)view.findViewById(R.id.backButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(@SuppressWarnings("unused") View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });

    this.saveButton = (Button)view.findViewById(R.id.saveButton);
    this.saveButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(@SuppressWarnings("unused") View v) {
        saveParameters();
      }
    });

    this.saveButton.setEnabled(false);
  }

  private void createColorBoxes(TableLayout parameters) {
    final ColorModel color = this.object.getColor();
    this.colorValue = Color.argb(color.getAlpha(), color.getR(), color.getG(), color.getB());

    final TableRow colorParameter = new TableRow(getContext());
    parameters.addView(colorParameter);

    final TextView colorLabel = new TextView(getContext());
    colorLabel.setText(getString(R.string.color));
    colorLabel.setTextColor(Color.BLACK);
    colorParameter.addView(colorLabel);

    this.colorButton = new Button(getContext());
    updateColorButton();
    colorParameter.addView(this.colorButton);

    this.colorButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(@SuppressWarnings("unused") View v) {
        openColorPicker(AbstractObjectEditor.this.colorValue);
      }
    });

    this.colorAlpha = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.colorAlpha);
    this.colorAlpha.setName(R.string.color_alpha);
    this.colorAlpha.setValue("" + color.getAlpha()); //$NON-NLS-1$
    this.colorAlpha.setUnit(""); //$NON-NLS-1$
  }
  
  /**
   * 色選択ボタンを更新します。
   */
  void updateColorButton() {
    this.colorButton.setBackgroundColor(this.colorValue);
    final String rgba = "(" + Color.red(this.colorValue) + ", " + Color.green(this.colorValue) + ", " + Color.blue(this.colorValue) + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    this.colorButton.setText(rgba);
  }

  /**
   * 色選択ダイアログを開きます。 
   */
  void openColorPicker(final int initialColor) {
    final ColorPickerDialog dialog = new ColorPickerDialog(getContext(), new ColorPickerDialog.OnColorChangedListener() {

      /**
       * {@inheritDoc}
       */
      public void colorChanged(int color) {
        if (initialColor != color) {
          AbstractObjectEditor.this.colorValue = color;
          AbstractObjectEditor.this.colorAlpha.setValue("" + Color.alpha(color)); //$NON-NLS-1$
          AbstractObjectEditor.this.isChanged = true;          

          saveParameters();
          
          updateColorButton();
        }
      }
    }, initialColor);

    dialog.show();
  }

  private void createRotationBoxes(TableLayout parameters) {
    final RotationModel rotation = this.object.getRotation();

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

    this.rotationX = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationX);
    this.rotationX.setName(R.string.rotation_wrt_x_axis);
    this.rotationX.setValue(x);
    this.rotationX.setUnit("[rad]"); //$NON-NLS-1$

    this.rotationY = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationY);
    this.rotationY.setName(R.string.rotation_wrt_y_axis);
    this.rotationY.setValue(y);
    this.rotationY.setUnit("[rad]"); //$NON-NLS-1$

    this.rotationZ = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationZ);
    this.rotationZ.setName(R.string.rotation_wrt_z_axis);
    this.rotationZ.setValue(z);
    this.rotationZ.setUnit("[rad]"); //$NON-NLS-1$
  }

  private void createTranslationBoxes(TableLayout parameters) {
    final TranslationModel translation = this.object.getTranslation();

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

    this.translationX = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationX);
    this.translationX.setName(R.string.translation_to_x_axis);
    this.translationX.setValue(x);
    this.translationX.setUnit("[m]"); //$NON-NLS-1$

    this.translationY = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationY);
    this.translationY.setName(R.string.translation_to_y_axis);
    this.translationY.setValue(y);
    this.translationY.setUnit("[m]"); //$NON-NLS-1$

    this.translationZ = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationZ);
    this.translationZ.setName(R.string.translation_to_z_axis);
    this.translationZ.setValue(z);
    this.translationZ.setUnit("[m]"); //$NON-NLS-1$
  }

  /**
   * 数字のみが入力されているか判定します。
   * 
   * @return boolean 数字のみが入力されていればtrue、そうでなければfalse
   */
  boolean containsOnlyNumbers() {
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
   * {@inheritDoc}
   */
  public boolean isChanged() {
    return this.isChanged;
  }

  /**
   * パラメータを保存します。
   */
  void saveParameters() {
    if (containsOnlyNumbers() == false) {
      showAlertMessageInDialog(getActivity().getString(R.string.please_input_numerical_values));
      return;
    }

    updateObjectParameters();
    this.tree.updateTree();

    this.modeler.setIsChanged(this.modeler.isChanged() || isChanged());
    this.modeler.updateDisplay();

    this.saveButton.setEnabled(false);
  }

  /**
   * 警告メッセージを表示します。
   * 
   * @param message メッセージ
   */
  private void showAlertMessageInDialog(String message) {
    final AlertDialogFragment dialog = new AlertDialogFragment();
    final Bundle bundle = new Bundle();
    bundle.putString("message", message); //$NON-NLS-1$
    dialog.setArguments(bundle);
    dialog.show(getActivity().getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
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
   * オブジェクトのパラメータを更新します。
   */
  void updateObjectParameters() {
    final ColorModel color = new ColorModel(Color.red(this.colorValue), Color.green(this.colorValue), Color.blue(this.colorValue), Color.alpha(this.colorValue));
    color.setAlpha(this.colorAlpha.getIntValue());
    this.object.setColor(color);
    this.object.setTranslation(getTranslation());
    this.object.setRotation(getRotation());

    updateModelParameters();
  }

  /**
   * {@inheritDoc}
   */
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
      final InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
      saveParameters();
      return true;
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void afterTextChanged(@SuppressWarnings("unused") Editable s) {
    if (this.saveButton != null) {
      this.isChanged = true;
      this.saveButton.setEnabled(true);
    }
  }
}
