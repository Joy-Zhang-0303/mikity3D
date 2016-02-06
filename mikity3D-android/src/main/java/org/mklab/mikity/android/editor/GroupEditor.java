/*
 * Created on 2016/01/31
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.AlertDialogFragment;
import org.mklab.mikity.android.AnimationParameterInputBox;
import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
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
import android.widget.TableRow;
import android.widget.TextView;

/**
 * グループを編集するエディタを表すフラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/31
 */
public class GroupEditor extends Fragment implements ModelEditor, OnKeyListener, TextWatcher {
  GroupModel targetGroup;
  OpenglesModeler modeler;
  SceneGraphTree tree;

  private TextView groupName;
  
  private ParameterInputBox translationX;
  private ParameterInputBox translationY;
  private ParameterInputBox translationZ;
  private ParameterInputBox rotationX;
  private ParameterInputBox rotationY;
  private ParameterInputBox rotationZ;
  
  private AnimationParameterInputBox translationXsource;
  private AnimationParameterInputBox translationYsource;
  private AnimationParameterInputBox translationZsource;
  
  private AnimationParameterInputBox rotationXsource;
  private AnimationParameterInputBox rotationYsource;
  private AnimationParameterInputBox rotationZsource;
  
  /** 保存ボタン。 */
  private Button saveButton;
  /** 値が変更されていればtrue。 */
  boolean isChanged = false;
  
  /**
   * 新しく生成された<code>AbstractObjectEditorFragment</code>オブジェクトを初期化します。
   * @param targetGroup グループ
   * @param tree シーングラフツリー
   * @param modeler モデラー
   */
  public GroupEditor(GroupModel targetGroup, SceneGraphTree tree, OpenglesModeler modeler) {
    this.targetGroup = targetGroup;
    this.tree = tree;
    this.modeler = modeler;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, @SuppressWarnings("unused") Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_group_editor, container, false);

    this.groupName = (TextView)view.findViewById(R.id.groupName);
    this.groupName.setText("root"); //$NON-NLS-1$
    
    if (this.targetGroup.getName() != null) {
      this.groupName.setText(this.targetGroup.getName());
    }
    
    createCoordinateParameterBoxes(view);
    createAnimationParameterBoxes(view);
    
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

  private void createCoordinateParameterBoxes(final View view) {
    final TableLayout parameters = ((TableLayout)view.findViewById(R.id.coordinate_parameters));
    parameters.removeAllViews();
    parameters.setStretchAllColumns(true);
    
    final TableRow head = new TableRow(getContext());
    final TextView name = new TextView(getContext());
    name.setText(R.string.name);
    final TextView value = new TextView(getContext());
    value.setText(R.string.value);
    head.addView(name);
    head.addView(value);
    parameters.addView(head);

    createCoordinateTranslationBoxes(parameters);
    createCoordinateRotationBoxes(parameters);
  }
  
  private void createAnimationParameterBoxes(final View view) {
    final TableLayout parameters = ((TableLayout)view.findViewById(R.id.animation_parameters));
    parameters.removeAllViews();
    parameters.setStretchAllColumns(true);
    
    final TableRow head = new TableRow(getContext());
    parameters.addView(head);
    
    final TextView name = new TextView(getContext());
    name.setText(R.string.name);
    head.addView(name);
    
    final TextView id = new TextView(getContext());
    id.setText(R.string.id);
    head.addView(id);
    
    final TextView number = new TextView(getContext());
    number.setText(R.string.number);
    head.addView(number);

    createAnimationTranslationBoxes(parameters);
    createAnimationRotationBoxes(parameters);
    
    setAnimationInDialog();
  }

  private void createCoordinateTranslationBoxes(TableLayout parameters) {
    final TranslationModel translation = this.targetGroup.getTranslation();
    
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
    this.translationX.setUnit(""); //$NON-NLS-1$

    this.translationY = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationY);
    this.translationY.setName(R.string.translation_to_y_axis);
    this.translationY.setValue(y);
    this.translationY.setUnit(""); //$NON-NLS-1$
    
    this.translationZ = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationZ);
    this.translationZ.setName(R.string.translation_to_z_axis);
    this.translationZ.setValue(z);
    this.translationZ.setUnit(""); //$NON-NLS-1$
  }
  
  private void createCoordinateRotationBoxes(TableLayout parameters) {
    final RotationModel rotation = this.targetGroup.getRotation();
    
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
    this.rotationX.setUnit(""); //$NON-NLS-1$

    this.rotationY = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationY);
    this.rotationY.setName(R.string.rotation_wrt_y_axis);
    this.rotationY.setValue(y);
    this.rotationY.setUnit(""); //$NON-NLS-1$
    
    this.rotationZ = new ParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationZ);
    this.rotationZ.setName(R.string.rotation_wrt_z_axis);
    this.rotationZ.setValue(z);
    this.rotationZ.setUnit(""); //$NON-NLS-1$
  }
  
  private void createAnimationTranslationBoxes(TableLayout parameters) {
    this.translationXsource = new AnimationParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationXsource);
    this.translationXsource.setName(R.string.translation_to_x_axis);
    this.translationXsource.setValue1("0"); //$NON-NLS-1$
    this.translationXsource.setValue2("0"); //$NON-NLS-1$

    this.translationYsource = new AnimationParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationYsource);
    this.translationYsource.setName(R.string.translation_to_y_axis);
    this.translationYsource.setValue1("0"); //$NON-NLS-1$
    this.translationYsource.setValue2("0"); //$NON-NLS-1$
    
    this.translationZsource = new AnimationParameterInputBox(getContext(), this, this);
    parameters.addView(this.translationZsource);
    this.translationZsource.setName(R.string.translation_to_z_axis);
    this.translationZsource.setValue1("0"); //$NON-NLS-1$
    this.translationZsource.setValue2("0"); //$NON-NLS-1$
  }
  
  private void createAnimationRotationBoxes(TableLayout parameters) {
    this.rotationXsource = new AnimationParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationXsource);
    this.rotationXsource.setName(R.string.rotation_wrt_x_axis);
    this.rotationXsource.setValue1("0"); //$NON-NLS-1$
    this.rotationXsource.setValue2("0"); //$NON-NLS-1$

    this.rotationYsource = new AnimationParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationYsource);
    this.rotationYsource.setName(R.string.rotation_wrt_y_axis);
    this.rotationYsource.setValue1("0"); //$NON-NLS-1$
    this.rotationYsource.setValue2("0"); //$NON-NLS-1$
    
    this.rotationZsource = new AnimationParameterInputBox(getContext(), this, this);
    parameters.addView(this.rotationZsource);
    this.rotationZsource.setName(R.string.rotation_wrt_z_axis);
    this.rotationZsource.setValue1("0"); //$NON-NLS-1$
    this.rotationZsource.setValue2("0"); //$NON-NLS-1$
  }

  /**
   * アニメーションの 番号を表示させる
   */
  private void setAnimationInDialog() {
    final AnimationModel[] animations = this.targetGroup.getAnimations();

    for (final AnimationModel animation : animations) {
      final String target = animation.getTarget();
      final SourceModel source = animation.getSource();
      final String id = source.getId();
      final String number = "" + source.getNumber(); //$NON-NLS-1$

      if (target.equals("translationX")) { //$NON-NLS-1$
        this.translationXsource.setValue1(id);
        this.translationXsource.setValue2(number);
      } else if (target.equals("translationY")) { //$NON-NLS-1$
        this.translationYsource.setValue1(id);
        this.translationYsource.setValue2(number);
      } else if (target.equals("translationZ")) { //$NON-NLS-1$
        this.translationZsource.setValue1(id);
        this.translationZsource.setValue2(number);
      } else if (target.equals("rotationX")) { //$NON-NLS-1$
        this.rotationXsource.setValue1(id);
        this.rotationXsource.setValue2(number);
      } else if (target.equals("rotationY")) { //$NON-NLS-1$
        this.rotationYsource.setValue1(id);
        this.rotationYsource.setValue2(number);
      } else if (target.equals("rotationZ")) { //$NON-NLS-1$
        this.rotationZsource.setValue1(id);        
        this.rotationZsource.setValue2(number);
      }
    }
  }

  
  /**
   * 数字のみが入力されているか判定します。
   * 
   * @return boolean 数字のみが入力されていればtrue、そうでなければfalse
   */
  boolean containsOnlyNumbers() {
    if (this.translationX.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationY.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZ.containsOnlyNumbers() == false) {
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
    
    if (this.translationXsource.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationYsource.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.translationZsource.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationXsource.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationYsource.containsOnlyNumbers() == false) {
      return false;
    }
    if (this.rotationZsource.containsOnlyNumbers() == false) {
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
   * {@inheritDoc}
   */
  public void updateModelParameters() {
    this.targetGroup.setName(this.groupName.getText().toString());
    this.targetGroup.clearAnimations();
    addAnimation("translationX", this.translationXsource); //$NON-NLS-1$
    addAnimation("translationY", this.translationYsource); //$NON-NLS-1$
    addAnimation("translationZ", this.translationZsource); //$NON-NLS-1$
    addAnimation("rotationX", this.rotationXsource); //$NON-NLS-1$
    addAnimation("rotationY", this.rotationYsource); //$NON-NLS-1$
    addAnimation("rotationZ", this.rotationZsource); //$NON-NLS-1$
    
    final TranslationModel translation = new TranslationModel(this.translationX.getFloatValue(), this.translationY.getFloatValue(), this.translationZ.getFloatValue());
    final RotationModel rotation = new RotationModel(this.rotationX.getFloatValue(), this.rotationY.getFloatValue(), this.rotationZ.getFloatValue());
    
    this.targetGroup.setTranslation(translation);
    this.targetGroup.setRotation(rotation);
  }
  
  /**
   * Animationを追加します。
   * 
   * @param parameterName パラメータの名前
   * @param sourceNumber データの番号
   */
  void addAnimation(final String parameterName, final AnimationParameterInputBox sourceParameter) {
    if (sourceParameter.getIntValue2() != 0) {
      final SourceModel source = new SourceModel();
      source.setId(sourceParameter.getStringValue1());
      source.setNumber(sourceParameter.getIntValue2());
      final AnimationModel animation = new AnimationModel();
      animation.setTarget(parameterName);
      animation.setSource(source);
      this.targetGroup.add(animation);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    // nothing to do
  }

  /**
   * パラメータを保存します。 
   */
  void saveParameters() {
    if (containsOnlyNumbers() == false) {
      showAlertMessageInDialog(getActivity().getString(R.string.please_input_numerical_values));
      return;
    }
    
    updateModelParameters();
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
    dialog.setMessage(message);
    dialog.show(getActivity().getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
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
