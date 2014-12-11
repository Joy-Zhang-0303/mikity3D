/*
 * Created on 2014/12/10
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.nfc.matrix.Matrix;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectFragment;


public class NavigationDrawerFragment extends RoboFragment {
  
  protected static final String LOGTAG = null;
  /** アニメーション用タスク */
  AnimationTask animationTask;

  /** */
  public static boolean playable = true;

  /** 等間隔の時間を保存しとく配列 */
  double[] timeTable;

  Timer timer = new Timer();

  /** */
  MovableGroupManager manager;

  // DEBUG textview
  TextView testTextView;
  TextView filePathView;
  TextView modelFilePathView;

  /** 前回のタッチのｘ座標 */
  float prevX = 0;
  /** 前回のタッチのｙ座標 */
  float prevY = 0;
  /** アニメーションの再生速度 丸め誤差を防ぐために１０で割る必要があります。 */
  int animationSpeed = 10;

  private final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  private final int REQUEST_CODE_PICK_TIME_DATA_FILE = 1;
  /** アニメーションスピードを表示するエディットテキスト */
  public EditText animationSpeedTextEdit;
  /** 3Dモデルのインプットストリーム */
  private InputStream inputModelFile;
  /** 3Dモデルが選ばれて表示されたかどうかのフラグ */
  protected boolean isSelectedModelFile;
  /** モデルを参照するときに押されるボタン */
  Button selectButton;
  /** アニメーションスピードを早くするときに押されるボタン */
  Button quickButton;
  /** アニメーションスピードを遅くするときに押されるボタン */
  Button slowButton;
  /** アニメーションを停止するときに押されるボタン */
  Button stopButton;
  /** モデルファイル読み込むときに押されるボタン */
  Button loadModelButton;
  /** アニメーションを再生するボタン */
  Button playButton;
  /** 端末の角度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton gyroToggleButton;
  /** 加速度を3Dオブジェクトに反映させるかどうかのトグル */
  ToggleButton accelerToggleButton;
  ToggleButton rotateTogguleButton;
  InputStream inputDataFile;
  private String timeDataName;
  private String modelFileName;
  protected Button setModelColumnNumberButton;
  CanvasActivity canvasActivity;
  public int animationTextSpeed;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
    
    this.canvasActivity = (CanvasActivity)getActivity();
    
    //モデルデータ選択ボタンの表示
    this.loadModelButton = (Button)view.findViewById(R.id.modelSelectButton);
    //時系列選択ボタンの配置
    this.selectButton = (Button)view.findViewById(R.id.timeSelectButton);
    this.quickButton = (Button)view.findViewById(R.id.quickButton);
    this.slowButton = (Button)view.findViewById(R.id.slowButton);
    this.playButton = (Button)view.findViewById(R.id.button1);
    this.stopButton = (Button)view.findViewById(R.id.button2);

    this.selectButton.setEnabled(false);
    this.quickButton.setEnabled(false);
    this.slowButton.setEnabled(false);
    this.playButton.setEnabled(false);
    this.stopButton.setEnabled(false);

    this.loadModelButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendIntent(REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
      }
    });

    this.animationSpeedTextEdit = (EditText)view.findViewById(R.id.animationSpeedEditText);
    this.animationSpeedTextEdit.clearFocus();
    this.animationSpeedTextEdit.setText(Double.toString(this.animationSpeed / 10));
    this.animationSpeedTextEdit.clearFocus();

    //ファイルパスビューの配置
    this.filePathView = (TextView)view.findViewById(R.id.filePathView);
    this.modelFilePathView = (TextView)view.findViewById(R.id.modelPathView);

    this.testTextView = new TextView(this.canvasActivity);
    this.testTextView = (TextView)view.findViewById(R.id.textView1);

    //再生速度の設定
    this.quickButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.animationSpeed += 1;
        NavigationDrawerFragment.this.animationSpeedTextEdit.setText("" + (double)NavigationDrawerFragment.this.animationSpeed / 10); //$NON-NLS-1$
        if (NavigationDrawerFragment.this.animationTask != null) NavigationDrawerFragment.this.animationTask.setSpeedScale(NavigationDrawerFragment.this.canvasActivity.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.setAnimationSpeed();
      }
    });

    this.selectButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendIntent(REQUEST_CODE_PICK_TIME_DATA_FILE);
      }
    });

    // イベントリスナー
    this.playButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.runAnimation();
      }
    });

    this.slowButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.canvasActivity.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.canvasActivity.animationSpeed -= 1;
        if (NavigationDrawerFragment.this.canvasActivity.animationSpeed < 0) NavigationDrawerFragment.this.canvasActivity.animationSpeed = 0;
        NavigationDrawerFragment.this.canvasActivity.animationSpeedTextEdit.setText(Double.toString((double)NavigationDrawerFragment.this.canvasActivity.animationSpeed / 10));
        if (NavigationDrawerFragment.this.canvasActivity.animationTask != null) NavigationDrawerFragment.this.canvasActivity.animationTask.setSpeedScale(NavigationDrawerFragment.this.canvasActivity.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.setAnimationSpeed();
      }
    });

    this.stopButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.timer.cancel();
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.playable = false;
      }
    });

    this.gyroToggleButton = (ToggleButton)view.findViewById(R.id.gyroToggleButton);
    this.gyroToggleButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (NavigationDrawerFragment.this.gyroToggleButton.isChecked()) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useOrientationSensor = true;
        } else NavigationDrawerFragment.this.canvasActivity.canvasFragment.useOrientationSensor = false;

      }
    });
    this.accelerToggleButton = (ToggleButton)view.findViewById(R.id.accelerToggleButton);
    this.accelerToggleButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (NavigationDrawerFragment.this.accelerToggleButton.isChecked()) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useAccelerSensor = true;

        } else {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.useAccelerSensor = false;
        }
      }

    });
    this.rotateTogguleButton = (ToggleButton)view.findViewById(R.id.rotateLayoutButton);
    this.rotateTogguleButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.controlRotate();
      }
    });
    // fragmentの値をactivityで保持。
    //TODO setRetainInstance()を使っても、activityでこのfragmentを保持しているため、処理が被っている。要修正
    this.canvasActivity.ndFragment = this;
    return view;
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }
  
  public void setAnimationSpeed() {
    this.canvasActivity.animationSpeed = this.animationSpeed;
  }
  
  protected void loadDataUri(Uri uri) {
    String timeDataPath;
    if (uri != null && "content".equals(uri.getScheme())) {
      // ストリームを直接URIから取り出します。
      try {
        this.inputDataFile = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      Cursor cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      cursor.moveToFirst();
      this.timeDataName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      //Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
      Log.d("URI", uri.toString());
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      timeDataPath = uri.getPath();
      this.canvasActivity.canvasFragment.setTimeDataPath(timeDataPath);
      //      this.filePathView.setText(new File(timeDataPath).getName());
      try {
        this.inputDataFile = new FileInputStream(timeDataPath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      String[] parts = timeDataPath.split("/");
      this.timeDataName = parts[parts.length - 1];
    }
    this.filePathView.setText(this.timeDataName);
  }
  
  protected void loadModelUri(Uri uri) {
    String modelFilePath;
    if (uri != null && "content".equals(uri.getScheme())) {
      // ストリームを直接URIから取り出します。
      try {
        this.inputModelFile = this.canvasActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      Cursor cursor = this.canvasActivity.getContentResolver().query(uri, null, null, null, null);
      cursor.moveToFirst();
      this.modelFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      //      Toast.makeText(getBaseContext(), cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)), Toast.LENGTH_SHORT).show();
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      modelFilePath = uri.getPath();
      this.canvasActivity.canvasFragment.setModelFilePath(modelFilePath);
      try {
        this.inputModelFile = new FileInputStream(modelFilePath);
        //        this.modelFilePathView.setText(new File(modelFilePath).getName());
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      String[] parts = modelFilePath.split("/");
      this.modelFileName = parts[parts.length - 1];
      Toast.makeText(this.canvasActivity.getBaseContext(), modelFileName, Toast.LENGTH_SHORT).show();
    }
    Log.d("URI", uri.toString());
    this.modelFilePathView.setText(this.modelFileName);
    this.timeDataName = "...";
    this.filePathView.setText(this.timeDataName);
    try {
      this.canvasActivity.canvasFragment.loadModelFile(this.inputModelFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
    this.isSelectedModelFile = true;
    this.selectButton.setEnabled(true);
    this.quickButton.setEnabled(true);
    this.slowButton.setEnabled(true);
    this.playButton.setEnabled(true);
    this.stopButton.setEnabled(true);
  }
}
