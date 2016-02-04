/*
 * Created on 2016/01/31
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.ParameterInputBox;
import org.mklab.mikity.android.ParameterInputBox2;
import org.mklab.mikity.android.R;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
public class GroupEditor extends Fragment implements ModelEditor {
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
  
  private ParameterInputBox2 translationXsource;
  private ParameterInputBox2 translationYsource;
  private ParameterInputBox2 translationZsource;
  
  private ParameterInputBox2 rotationXsource;
  private ParameterInputBox2 rotationYsource;
  private ParameterInputBox2 rotationZsource;
  
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_group_editor, container, false);
       
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
    
    this.groupName = (TextView)view.findViewById(R.id.groupName);
    this.groupName.setText("root"); //$NON-NLS-1$
    
    if (this.targetGroup.getName() != null) {
      this.groupName.setText(this.targetGroup.getName());
    }
    
    createCoordinateParameterBoxes(view);
    createAnimationParameterBoxes(view);
    
    return view;
  }

  void createCoordinateParameterBoxes(final View view) {
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
  
  void createAnimationParameterBoxes(final View view) {
    final TableLayout parameters = ((TableLayout)view.findViewById(R.id.animation_parameters));
    parameters.removeAllViews();
    parameters.setStretchAllColumns(true);
    
    final TableRow head = new TableRow(getContext());
    final TextView name = new TextView(getContext());
    name.setText(R.string.name);
    final TextView id = new TextView(getContext());
    id.setText(R.string.id);
    final TextView number = new TextView(getContext());
    number.setText(R.string.number);

    head.addView(name);
    head.addView(id);
    head.addView(number);
    parameters.addView(head);

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

    this.translationX = new ParameterInputBox(getContext());
    parameters.addView(this.translationX);
    this.translationX.setName(R.string.translation_to_x_axis);
    this.translationX.setValue(x);
    this.translationX.setUnit(""); //$NON-NLS-1$

    this.translationY = new ParameterInputBox(getContext());
    parameters.addView(this.translationY);
    this.translationY.setName(R.string.translation_to_y_axis);
    this.translationY.setValue(y);
    this.translationY.setUnit(""); //$NON-NLS-1$
    
    this.translationZ = new ParameterInputBox(getContext());
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
    
    this.rotationX = new ParameterInputBox(getContext());
    parameters.addView(this.rotationX);
    this.rotationX.setName(R.string.rotation_wrt_x_axis);
    this.rotationX.setValue(x);
    this.rotationX.setUnit(""); //$NON-NLS-1$

    this.rotationY = new ParameterInputBox(getContext());
    parameters.addView(this.rotationY);
    this.rotationY.setName(R.string.rotation_wrt_y_axis);
    this.rotationY.setValue(y);
    this.rotationY.setUnit(""); //$NON-NLS-1$
    
    this.rotationZ = new ParameterInputBox(getContext());
    parameters.addView(this.rotationZ);
    this.rotationZ.setName(R.string.rotation_wrt_z_axis);
    this.rotationZ.setValue(z);
    this.rotationZ.setUnit(""); //$NON-NLS-1$
  }
  
  private void createAnimationTranslationBoxes(TableLayout parameters) {
    this.translationXsource = new ParameterInputBox2(getContext());
    parameters.addView(this.translationXsource);
    this.translationXsource.setName(R.string.translation_to_x_axis);
    this.translationXsource.setValue1("0"); //$NON-NLS-1$
    this.translationXsource.setValue2("0"); //$NON-NLS-1$

    this.translationYsource = new ParameterInputBox2(getContext());
    parameters.addView(this.translationYsource);
    this.translationYsource.setName(R.string.translation_to_y_axis);
    this.translationYsource.setValue1("0"); //$NON-NLS-1$
    this.translationYsource.setValue2("0"); //$NON-NLS-1$
    
    this.translationZsource = new ParameterInputBox2(getContext());
    parameters.addView(this.translationZsource);
    this.translationZsource.setName(R.string.translation_to_z_axis);
    this.translationZsource.setValue1("0"); //$NON-NLS-1$
    this.translationZsource.setValue2("0"); //$NON-NLS-1$
  }
  
  private void createAnimationRotationBoxes(TableLayout parameters) {
    this.rotationXsource = new ParameterInputBox2(getContext());
    parameters.addView(this.rotationXsource);
    this.rotationXsource.setName(R.string.rotation_wrt_x_axis);
    this.rotationXsource.setValue1("0"); //$NON-NLS-1$
    this.rotationXsource.setValue2("0"); //$NON-NLS-1$

    this.rotationYsource = new ParameterInputBox2(getContext());
    parameters.addView(this.rotationYsource);
    this.rotationYsource.setName(R.string.rotation_wrt_y_axis);
    this.rotationYsource.setValue1("0"); //$NON-NLS-1$
    this.rotationYsource.setValue2("0"); //$NON-NLS-1$
    
    this.rotationZsource = new ParameterInputBox2(getContext());
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
//    this.targetGroup.setName(this.groupName.getStringValue());
//    this.targetGroup.clearAnimations();
//    addAnimation("translationX", this.translationXsourceId, this.translationXsourceNumber); //$NON-NLS-1$
//    addAnimation("translationY", this.translationYsourceId, this.translationYsourceNumber); //$NON-NLS-1$
//    addAnimation("translationZ", this.translationZsourceId, this.translationZsourceNumber); //$NON-NLS-1$
//    addAnimation("rotationX", this.rotationXsourceId, this.rotationXsourceNumber); //$NON-NLS-1$
//    addAnimation("rotationY", this.rotationYsourceId, this.rotationYsourceNumber); //$NON-NLS-1$
//    addAnimation("rotationZ", this.rotationZsourceId, this.rotationZsourceNumber); //$NON-NLS-1$
//    
//    final TranslationModel translation = new TranslationModel(this.translationX.getFloatValue(), this.translationY.getFloatValue(), this.translationZ.getFloatValue());
//    final RotationModel rotation = new RotationModel(this.rotationX.getFloatValue(), this.rotationY.getFloatValue(), this.rotationZ.getFloatValue());
//    
//    this.targetGroup.setTranslation(translation);
//    this.targetGroup.setRotation(rotation);
  }
  
  /**
   * {@inheritDoc}
   */
  public void updateEditor() {
    // nothing to do
  }
}
