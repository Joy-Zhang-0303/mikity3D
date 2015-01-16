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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.mklab.mikity.android.control.AnimationTask;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
import org.mklab.mikity.model.xml.simplexml.model.Group;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import roboguice.fragment.RoboFragment;


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
  protected String timeDataName;
  private String modelFileName;
  protected Button setModelColumnNumberButton;
  CanvasActivity canvasActivity;
  public int animationTextSpeed;
  protected String[] groupNameArray;
  protected int[] columnNumberArray;
  protected List groupNameList;
  protected List columnNumberList;
  protected List targetNameList;
  public Button reloadButton;
  public Button timeDataDeleteButton;

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
    this.reloadButton = (Button)view.findViewById(R.id.reloadButton);
    this.timeDataDeleteButton = (Button)view.findViewById(R.id.timeDataDeleteButton);

    this.selectButton.setEnabled(false);
    this.quickButton.setEnabled(false);
    this.slowButton.setEnabled(false);
    this.playButton.setEnabled(false);
    this.stopButton.setEnabled(false);
    this.reloadButton.setEnabled(false);
    this.timeDataDeleteButton.setEnabled(false);

    this.loadModelButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
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
        if (NavigationDrawerFragment.this.animationTask != null) NavigationDrawerFragment.this.animationTask.setSpeedScale(NavigationDrawerFragment.this.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
//        NavigationDrawerFragment.this.setAnimationSpeed();
      }
    });

    this.selectButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        NavigationDrawerFragment.this.canvasActivity.sendFileChooseIntent(REQUEST_CODE_PICK_TIME_DATA_FILE);
      }
    });

    // イベントリスナー
    this.playButton.setOnClickListener(new View.OnClickListener() {

      // コールバックメソッド
      public void onClick(View view) {
        NavigationDrawerFragment.this.canvasActivity.canvasFragment.runAnimation();
      }
    });

    this.reloadButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        if(NavigationDrawerFragment.this.canvasActivity.canvasFragment.data != null) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.setTimeData();
        }
      }
    });
    
    this.timeDataDeleteButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        if(NavigationDrawerFragment.this.canvasActivity.canvasFragment.data != null) {
          NavigationDrawerFragment.this.canvasActivity.canvasFragment.data = null;
          NavigationDrawerFragment.this.timeDataName = "...";
          NavigationDrawerFragment.this.filePathView.setText(NavigationDrawerFragment.this.timeDataName);
        }
      }
    });

    this.slowButton.setOnClickListener(new View.OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
        NavigationDrawerFragment.this.animationSpeed -= 1;
        if (NavigationDrawerFragment.this.animationSpeed < 0) NavigationDrawerFragment.this.animationSpeed = 0;
        NavigationDrawerFragment.this.animationSpeedTextEdit.setText(Double.toString((double)NavigationDrawerFragment.this.animationSpeed / 10));
        if (NavigationDrawerFragment.this.animationTask != null) NavigationDrawerFragment.this.animationTask.setSpeedScale(NavigationDrawerFragment.this.animationSpeed / 10);
        NavigationDrawerFragment.this.animationSpeed = (int)(Double.parseDouble(NavigationDrawerFragment.this.animationSpeedTextEdit.getText().toString()) * 10);
//        NavigationDrawerFragment.this.setAnimationSpeed();
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
        NavigationDrawerFragment.this.canvasActivity.controlRotation();
      }
    });
    this.setModelColumnNumberButton = (Button)view.findViewById(R.id.setModelColumnNumberlButton);
    this.setModelColumnNumberButton.setEnabled(false);
    this.setModelColumnNumberButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ModelColumnNumberFragment fragment =  new ModelColumnNumberFragment();
        fragmentTransaction.replace(R.id.fragment_navigation_drawer, fragment);
        fragmentTransaction.addToBackStack(null);
        NavigationDrawerFragment.this.canvasActivity.setMCNFragmnet(fragment);
        fragmentTransaction.commit();
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
//  
//  public void setAnimationSpeed() {
//    this.canvasActivity.animationSpeed = this.animationSpeed;
//  }
  
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
      setButtonEnabled(true);
      if(this.canvasActivity.canvasFragment.data != null) {
        this.canvasActivity.canvasFragment.data = null;
      }
    } catch (Mikity3dSerializeDeserializeException e) {
      setExceptionDailogFragment("please select model file.");
      setButtonEnabled(false);
    }
  }
  
  protected void setExceptionDailogFragment(String message) {
    DialogFragment dialogFragment = new ExceptionDialogFragment();
    ((ExceptionDialogFragment)dialogFragment).setMessage("Please select data file.");
    dialogFragment.show(getFragmentManager(), "exceptionDialogFragment");
//    this.modelFileName = "";
//    this.modelFilePathView.setText(this.modelFileName);
  }
  
  protected void setButtonEnabled(boolean flag) {
    this.isSelectedModelFile = flag;
    this.selectButton.setEnabled(flag);
    this.quickButton.setEnabled(flag);
    this.slowButton.setEnabled(flag);
    this.playButton.setEnabled(flag);
    this.stopButton.setEnabled(flag);
    this.setModelColumnNumberButton.setEnabled(flag);
    this.reloadButton.setEnabled(flag);
    this.timeDataDeleteButton.setEnabled(flag);
  }
  
  protected void setGroupNameList(Mikity3d root) {
	Mikity3dModel model = root.getModel(0);
	Group[] groupArray = model.getGroups();
	Group group = groupArray[0];
	group = group.getGroup(0);
	List<Map<String, String>> groupNameList = new ArrayList<Map<String, String>>();
	int count = 0;
	while(true) {
	  try {
 		if (count != 0) {
 		  group = group.getGroup(0);
	   	}
	  } catch(IndexOutOfBoundsException e) {
	    break;
	  }
	  Map<String, String> groupNameData = new HashMap<String, String>();
	  groupNameData.put("groupName", group.getName());
	  groupNameList.add(groupNameData);
	  count++;
    }
	group = groupArray[0];
	List<List<Map<String, String>>> allColumnNumberList = new ArrayList<List<Map<String, String>>>();
	List<List<Map<String, String>>> allTargetList = new ArrayList<List<Map<String, String>>>();
	for(int i=0; i<groupNameList.size(); i++) {
		List<Map<String, String>> columnNumberList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> targetList = new ArrayList<Map<String, String>>();
		int j = 0;
		group = group.getGroup(0);
	  while(true) {
		Map<String, String> columnNumberData = new HashMap<String, String>();
		Map<String, String> targetData = new HashMap<String, String>();
		try {
			columnNumberData.put("columnNumber", String.valueOf(group.getLinkData(j).getColumnNumber()));
			columnNumberList.add(columnNumberData);
			targetData.put("target", group.getLinkData(j).getTargetName());
			targetList.add(targetData);
			j++;
		} catch(IndexOutOfBoundsException e) {
			break;
		}
      }
	  allColumnNumberList.add(columnNumberList);
	  allTargetList.add(targetList);
	}
	setLists(groupNameList, allColumnNumberList, allTargetList);
  }
  protected void setLists(List<Map<String, String>> groupNameList, List<List<Map<String, String>>> allColumnNumberList,
      List<List<Map<String, String>>> allTargetList) {
	  this.groupNameList = groupNameList;
	  this.columnNumberList = allColumnNumberList;
	  this.targetNameList = allTargetList;
  }
}
