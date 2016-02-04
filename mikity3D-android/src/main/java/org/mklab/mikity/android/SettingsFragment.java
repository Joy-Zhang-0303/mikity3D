/*
 * Created on 2014/12/10
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.searcher.ExcecuteSearchGroup;
import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupNameManager;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * SettingsDrawerでメニューを表示するためのフラグメントです。
 * 
 * @author hirae
 * @version $Revision$, 2015/01/16
 */
public class SettingsFragment extends Fragment {

  static final String LOGTAG = null;

  /**
   * 新しく生成された<code>NavigationDrawerFragment</code>オブジェクトを初期化します。
   */
  public SettingsFragment() {
    // nothing to do
  }

  CanvasActivity canvasActivity;


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
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_settings, container, false);

    this.canvasActivity = (CanvasActivity)getActivity();
    
    final Button settingBackButton = (Button)view.findViewById(R.id.settingBackButton);
    settingBackButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });



    createSensorComponent(view);
    
    createConfigurationComponent(view);



    return view;
  }



  private void createSensorComponent(final View mainView) {
    this.rotationSensorButton = (CompoundButton)mainView.findViewById(R.id.rotationSensorButton);
    this.rotationSensorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (SettingsFragment.this.rotationSensorButton.isChecked()) {
          SettingsFragment.this.canvasActivity.sensorService.useRotationSensor = true;
        } else {
          SettingsFragment.this.canvasActivity.sensorService.useRotationSensor = false;
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
          SettingsFragment.this.canvasActivity.sensorService.useAccelerometer = true;
        } else {
          SettingsFragment.this.canvasActivity.sensorService.useAccelerometer = false;
        }
      }

    });

    this.rotationLockButton = (CompoundButton)mainView.findViewById(R.id.rotationLockButton);
    this.rotationLockButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsFragment.this.canvasActivity.controlRotation();
      }
    });
  }

  private void createConfigurationComponent(final View mainView) {
    this.gridShowingButton = (CompoundButton)mainView.findViewById(R.id.gridShowingButton);
    this.gridShowingButton.setEnabled(false);
    this.gridShowingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsFragment.this.canvasActivity.canvasFragment.setGridShowing(isChecked);
      }
    });

    this.axisShowingButton = (CompoundButton)mainView.findViewById(R.id.axisShowingButton);
    this.axisShowingButton.setEnabled(false);
    this.axisShowingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

      /**
       * {@inheritDoc}
       */
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsFragment.this.canvasActivity.canvasFragment.setAxisShowing(isChecked);
      }
    });
  }

  /**
   * Configurationを更新します。
   */
  private void updateConfiguration() {
    this.axisShowingButton.setChecked(this.canvasActivity.canvasFragment.isAxisShowing());
    this.gridShowingButton.setChecked(this.canvasActivity.canvasFragment.isGridShowing());
  }



  /**
   * @param enabled
   */
  void setButtonEnabled(boolean enabled) {

    this.gridShowingButton.setEnabled(enabled);
    this.axisShowingButton.setEnabled(enabled);

  }


  
}
