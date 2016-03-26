/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.android;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * NavigationDrawerでメニューを表示するためのフラグメントです。
 * 
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class SettingsFragment extends Fragment implements OnKeyListener, TextWatcher {

  MainActivity mainActivity;

  CanvasFragment canvasFragment;

  /** アニメーション用タスク。 */
  AnimationTask animationTask;

  /** アニメーションスピード。 */
  //EditText animationSpeedTextEdit;

  /** アニメーションスピードを早くするためのボタン。 */
  //ImageButton quickButton;
  /** アニメーションスピードを遅くするためのボタン。 */
  //ImageButton slowButton;

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

  EditText eyePositionX;
  EditText eyePositionY;
  EditText eyePositionZ;
  
  EditText lookAtPointX;
  EditText lookAtPointY;
  EditText lookAtPointZ;
  
  EditText lightPositionX;
  EditText lightPositionY;
  EditText lightPositionZ;
  
  private boolean isChanged = false;

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

    createEnvironmentComponent(view);

    createSensorComponent(view);

    createGridAxisComponent(view);

    return view;
  }

  private void createAnimationSpeedComponent(final View mainView) {
    final EditText animationSpeedTextEdit = (EditText)mainView.findViewById(R.id.animationSpeedEditText);
    animationSpeedTextEdit.clearFocus();
    animationSpeedTextEdit.setText(String.format("%g", Double.valueOf(SettingsFragment.this.canvasFragment.animationSpeedRate / 1000.0))); //$NON-NLS-1$    
    animationSpeedTextEdit.clearFocus();

    animationSpeedTextEdit.addTextChangedListener(new TextWatcher() {

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
        final double value = Double.parseDouble(animationSpeedTextEdit.getText().toString());
        SettingsFragment.this.canvasFragment.animationSpeedRate = (int)Math.round(value * 1000);
      }
    });

    final ImageButton slowButton = (ImageButton)mainView.findViewById(R.id.slowButton);
    slowButton.setOnClickListener(new View.OnClickListener() {

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
        animationSpeedTextEdit.setText(String.format("%g", Double.valueOf(animationSpeedRate / 1000.0))); //$NON-NLS-1$
        if (SettingsFragment.this.animationTask != null) {
          SettingsFragment.this.animationTask.setSpeedScale(animationSpeedRate / 1000.0);
        }

        SettingsFragment.this.canvasFragment.animationSpeedRate = animationSpeedRate;
      }
    });

    final ImageButton quickButton = (ImageButton)mainView.findViewById(R.id.quickButton);
    quickButton.setOnClickListener(new View.OnClickListener() {

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
        animationSpeedTextEdit.setText(String.format("%g", Double.valueOf(animationSpeedRate / 1000.0))); //$NON-NLS-1$
        if (SettingsFragment.this.animationTask != null) {
          SettingsFragment.this.animationTask.setSpeedScale(animationSpeedRate / 1000.0);
        }

        SettingsFragment.this.canvasFragment.animationSpeedRate = animationSpeedRate;
      }
    });
  }

  private void createEnvironmentComponent(final View mainView) {
    final ConfigurationModel configuration = this.canvasFragment.root.getConfiguration(0);

    createEye(mainView, configuration);
    createLookAtPoint(mainView, configuration);
    createLightPosition(mainView, configuration);
  }

  void createEye(final View mainView, final ConfigurationModel configuration) {
    final EyeModel eye = configuration.getEye();

    this.eyePositionX = (EditText)mainView.findViewById(R.id.eyePositionX);
    this.eyePositionX.setText(String.format("%g", Double.valueOf(eye.getX()))); //$NON-NLS-1$
    this.eyePositionX.addTextChangedListener(this);
    this.eyePositionX.setOnKeyListener(this);

    this.eyePositionY = (EditText)mainView.findViewById(R.id.eyePositionY);
    this.eyePositionY.setText(String.format("%g", Double.valueOf(eye.getY()))); //$NON-NLS-1$
    this.eyePositionY.addTextChangedListener(this);
    this.eyePositionY.setOnKeyListener(this);

    this.eyePositionZ = (EditText)mainView.findViewById(R.id.eyePositionZ);
    this.eyePositionZ.setText(String.format("%g", Double.valueOf(eye.getZ()))); //$NON-NLS-1$
    this.eyePositionZ.addTextChangedListener(this);
    this.eyePositionZ.setOnKeyListener(this);
  }

  void createLookAtPoint(final View mainView, final ConfigurationModel configuration) {
    final LookAtPointModel lookAtPoint = configuration.getLookAtPoint();

    this.lookAtPointX = (EditText)mainView.findViewById(R.id.lookAtPointX);
    this.lookAtPointX.setText(String.format("%g", Double.valueOf(lookAtPoint.getX()))); //$NON-NLS-1$    
    this.lookAtPointX.addTextChangedListener(this);
    this.lookAtPointX.setOnKeyListener(this);

    this.lookAtPointY = (EditText)mainView.findViewById(R.id.lookAtPointY);
    this.lookAtPointY.setText(String.format("%g", Double.valueOf(lookAtPoint.getY()))); //$NON-NLS-1$    
    this.lookAtPointY.addTextChangedListener(this);
    this.lookAtPointY.setOnKeyListener(this);

    this.lookAtPointZ = (EditText)mainView.findViewById(R.id.lookAtPointZ);
    this.lookAtPointZ.setText(String.format("%g", Double.valueOf(lookAtPoint.getZ()))); //$NON-NLS-1$    
    this.lookAtPointZ.addTextChangedListener(this);
    this.lookAtPointZ.setOnKeyListener(this);
  }

  void createLightPosition(final View mainView, final ConfigurationModel configuration) {
    final LightModel light = configuration.getLight();

    this.lightPositionX = (EditText)mainView.findViewById(R.id.lightPositionX);
    this.lightPositionX.setText(String.format("%g", Double.valueOf(light.getX()))); //$NON-NLS-1$    
    this.lightPositionX.addTextChangedListener(this);
    this.lightPositionX.setOnKeyListener(this);

    this.lightPositionY = (EditText)mainView.findViewById(R.id.lightPositionY);
    this.lightPositionY.setText(String.format("%g", Double.valueOf(light.getY()))); //$NON-NLS-1$    
    this.lightPositionY.addTextChangedListener(this);
    this.lightPositionY.setOnKeyListener(this);

    this.lightPositionZ = (EditText)mainView.findViewById(R.id.lightPositionZ);
    this.lightPositionZ.setText(String.format("%g", Double.valueOf(light.getZ()))); //$NON-NLS-1$    
    this.lightPositionZ.addTextChangedListener(this);
    this.lightPositionZ.setOnKeyListener(this);
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

  private void createGridAxisComponent(final View mainView) {
    this.gridShowingButton = (CompoundButton)mainView.findViewById(R.id.gridShowingButton);
    this.gridShowingButton.setChecked(this.canvasFragment.isGridShowing());
    this.gridShowingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsFragment.this.canvasFragment.setGridShowing(isChecked);
      }
    });

    this.axisShowingButton = (CompoundButton)mainView.findViewById(R.id.axisShowingButton);
    this.axisShowingButton.setChecked(this.canvasFragment.isAxisShowing());
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
//    if (this.saveButton != null) {
      this.isChanged = true;
//      this.saveButton.setEnabled(true);
//    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
      final InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
      saveEnvironment();
      return true;
    }

    return false;
  }
  
  private void saveEnvironment() {
    final ConfigurationModel configuration = this.canvasFragment.root.getConfiguration(0);

    final EyeModel eye = configuration.getEye();
    eye.setX(Float.parseFloat(this.eyePositionX.getText().toString()));
    eye.setY(Float.parseFloat(this.eyePositionY.getText().toString()));
    eye.setZ(Float.parseFloat(this.eyePositionZ.getText().toString()));
    configuration.setEye(eye);

    final LookAtPointModel lookAtPoint = configuration.getLookAtPoint();
    lookAtPoint.setX(Float.parseFloat(this.lookAtPointX.getText().toString()));
    lookAtPoint.setY(Float.parseFloat(this.lookAtPointY.getText().toString()));
    lookAtPoint.setZ(Float.parseFloat(this.lookAtPointZ.getText().toString()));
    configuration.setLookAtPoiint(lookAtPoint);
    
    final LightModel light = configuration.getLight();
    light.setX(Float.parseFloat(this.lightPositionX.getText().toString()));
    light.setY(Float.parseFloat(this.lightPositionY.getText().toString()));
    light.setZ(Float.parseFloat(this.lightPositionZ.getText().toString()));
    configuration.setLight(light);
    
    SettingsFragment.this.canvasFragment.setConfiguration(configuration);
  }
}
