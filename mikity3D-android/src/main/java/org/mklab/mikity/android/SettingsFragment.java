/*
 * Created on 2014/12/10
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import org.mklab.mikity.android.control.AnimationTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * NavigationDrawerでメニューを表示するためのフラグメントです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class SettingsFragment extends Fragment {
  MainActivity mainActivity;
  
  CanvasFragment canvasFragment;

  /** アニメーション用タスク。 */
  AnimationTask animationTask;

  /** アニメーションスピード。 */
  EditText animationSpeedTextEdit;

  /** アニメーションスピードを早くするためのボタン。 */
  ImageButton quickButton;
  /** アニメーションスピードを遅くするためのボタン。 */
  ImageButton slowButton;

  /** 端末の角度を3Dオブジェクトに反映させるならばtrue。 */
  CompoundButton rotationSensorButton;
  /** 加速度を3Dオブジェクトに反映させるならばtrue。 */
  CompoundButton accelerometerButton;
  /** 端末の回転を許可するならばtrue。 */
  CompoundButton rotationLockButton;
  /** グリッドを表示するならばtrue。 */
  CompoundButton gridShowingButton;
  /** 座標軸を表示するならばtrue。 */
  CompoundButton axisShowingButton;

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    
    this.mainActivity = (MainActivity)getActivity();
    this.canvasFragment = this.mainActivity.canvasFragment;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_settings, container, false);

    final Button backButton = (Button)view.findViewById(R.id.settingsBackButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });

    createAnimationSpeedComponent(view);

    createSensorComponent(view);

    createConfigurationComponent(view);
    
    updateConfiguration();

    return view;
  }

  private void createAnimationSpeedComponent(final View mainView) {
    this.slowButton = (ImageButton)mainView.findViewById(R.id.slowButton);
    this.slowButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        int animationSpeedRate = SettingsFragment.this.canvasFragment.animationSpeedRate;
        
        final int step = (int)Math.floor(Math.log10(animationSpeedRate - 1));
        animationSpeedRate -= (int)Math.pow(10, step);
        if (animationSpeedRate < 0) {
          animationSpeedRate = 1;
        }
        SettingsFragment.this.animationSpeedTextEdit.setText(String.format("%g", Double.valueOf(animationSpeedRate / 1000.0))); //$NON-NLS-1$
        if (SettingsFragment.this.animationTask != null) {
          SettingsFragment.this.animationTask.setSpeedScale(animationSpeedRate / 1000.0);
        }
        
        SettingsFragment.this.canvasFragment.animationSpeedRate = animationSpeedRate;
      }
    });

    this.animationSpeedTextEdit = (EditText)mainView.findViewById(R.id.animationSpeedEditText);
    this.animationSpeedTextEdit.clearFocus();
    this.animationSpeedTextEdit.setText(String.format("%g", Double.valueOf(SettingsFragment.this.canvasFragment.animationSpeedRate / 1000.0))); //$NON-NLS-1$    
    this.animationSpeedTextEdit.clearFocus();

    this.animationSpeedTextEdit.addTextChangedListener(new TextWatcher() {

      /**
       * {@inheritDoc}
       */
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        // nothing to do
      }

      /**
       * {@inheritDoc}
       */
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //nothin to do
      }

      /**
       * {@inheritDoc}
       */
      public void afterTextChanged(Editable s) {
        final double value = Double.parseDouble(SettingsFragment.this.animationSpeedTextEdit.getText().toString());
        SettingsFragment.this.canvasFragment.animationSpeedRate = (int)Math.round(value * 1000);
      }
    });

    this.quickButton = (ImageButton)mainView.findViewById(R.id.quickButton);
    this.quickButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        int animationSpeedRate = SettingsFragment.this.canvasFragment.animationSpeedRate;
        
        final int step = (int)Math.floor(Math.log10(animationSpeedRate));
        animationSpeedRate += (int)Math.pow(10, step);
        if (animationSpeedRate > 1000000) {
          animationSpeedRate = 1000000;
        }
        SettingsFragment.this.animationSpeedTextEdit.setText(String.format("%g", Double.valueOf(animationSpeedRate / 1000.0))); //$NON-NLS-1$
        if (SettingsFragment.this.animationTask != null) {
          SettingsFragment.this.animationTask.setSpeedScale(animationSpeedRate / 1000.0);
        }
        
        SettingsFragment.this.canvasFragment.animationSpeedRate = animationSpeedRate;
      }
    });
  }

  private void createSensorComponent(final View mainView) {
    this.rotationSensorButton = (CompoundButton)mainView.findViewById(R.id.rotationSensorButton);
    this.rotationSensorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (SettingsFragment.this.rotationSensorButton.isChecked()) {
          SettingsFragment.this.mainActivity.sensorService.useRotationSensor = true;
        } else {
          SettingsFragment.this.mainActivity.sensorService.useRotationSensor = false;
        }

      }
    });

    this.accelerometerButton = (CompoundButton)mainView.findViewById(R.id.accelerometerButton);
    this.accelerometerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (SettingsFragment.this.accelerometerButton.isChecked()) {
          SettingsFragment.this.mainActivity.sensorService.useAccelerometer = true;
        } else {
          SettingsFragment.this.mainActivity.sensorService.useAccelerometer = false;
        }
      }

    });

    this.rotationLockButton = (CompoundButton)mainView.findViewById(R.id.rotationLockButton);
    this.rotationLockButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsFragment.this.mainActivity.controlRotation();
      }
    });
  }

  private void createConfigurationComponent(final View mainView) {
    this.gridShowingButton = (CompoundButton)mainView.findViewById(R.id.gridShowingButton);
    this.gridShowingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsFragment.this.canvasFragment.setGridShowing(isChecked);
      }
    });

    this.axisShowingButton = (CompoundButton)mainView.findViewById(R.id.axisShowingButton);
    this.axisShowingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsFragment.this.canvasFragment.setAxisShowing(isChecked);
      }
    });
  }

  /**
   * Configurationを更新します。
   */
  private void updateConfiguration() {
    this.axisShowingButton.setChecked(this.canvasFragment.isAxisShowing());
    this.gridShowingButton.setChecked(this.canvasFragment.isGridShowing());
  }
}
