/*
 * Created on 2016/01/31
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;

import android.content.Context;
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
import android.widget.TextView;


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

  private ParameterInputBox colorR;
  private ParameterInputBox colorG;
  private ParameterInputBox colorB;
  private ParameterInputBox colorAlpha;

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
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_object_editor, container, false);

    final Button backButton = (Button)view.findViewById(R.id.backButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });
    
    this.saveButton = (Button)view.findViewById(R.id.saveButton);
    this.saveButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        saveParameters();
      }
    });

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
    this.saveButton.setEnabled(false);
    
    return view;
  }

  private void createColorBoxes(TableLayout parameters) {
    final ColorModel color = this.object.getColor();

    this.colorR = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.colorR);
    this.colorR.setName(R.string.color_r);
    this.colorR.setValue("" + color.getR()); //$NON-NLS-1$
    this.colorR.setUnit(""); //$NON-NLS-1$

    this.colorG = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.colorG);
    this.colorG.setName(R.string.color_g);
    this.colorG.setValue("" + color.getG()); //$NON-NLS-1$
    this.colorG.setUnit(""); //$NON-NLS-1$

    this.colorB = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.colorB);
    this.colorB.setName(R.string.color_b);
    this.colorB.setValue("" + color.getB()); //$NON-NLS-1$
    this.colorB.setUnit(""); //$NON-NLS-1$

    this.colorAlpha = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.colorAlpha);
    this.colorAlpha.setName(R.string.color_alpha);
    this.colorAlpha.setValue("" + color.getAlpha()); //$NON-NLS-1$
    this.colorAlpha.setUnit(""); //$NON-NLS-1$
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
//    if (containsOnlyNumbers() == false) {
//      final MessageBox message = new MessageBox(this.modeler.getShell(), SWT.ICON_WARNING);
//      message.setMessage(Messages.getString("EditPrimitiveDialog.23")); //$NON-NLS-1$
//      message.setText(Messages.getString("EditPrimitiveDialog.24")); //$NON-NLS-1$
//      message.open();
//      return;
//    }
//    
//    updateObjectParameters();
//    this.tree.updateTree();
//    
//    this.modeler.setIsChanged(this.modeler.isChanged() || isChanged());
//    this.modeler.updateDisplay();
//    
    this.saveButton.setEnabled(false);
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
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    // nothing to do
    }

  /**
   * {@inheritDoc}
   */
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void afterTextChanged(Editable s) {
    if (this.saveButton != null) {
      this.isChanged = true;
      this.saveButton.setEnabled(true);
    }
  }
}
