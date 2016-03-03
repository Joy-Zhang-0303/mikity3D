/*
 * Created on 2015/02/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * モデルデータとソースデータを選択するためのフラグメントです。
 * 
 * @author hirae
 * @version $Revision$, 2015/02/15
 */
public class FileSelectionFragment extends Fragment {
  MainActivity mainActivity;
  
  CanvasFragment canvasFragment;

  /** モデルファイル名。 */
  String modelFileName = "..."; //$NON-NLS-1$
  /** モデルファイルのパス。 */
  TextView modelFileNameView;
  
  /** 3Dモデルが選ばれて表示されたならばtrue。 */
  boolean isSelectedModelFile;

  /** ソースファイルのパス。 */
  Map<String, TextView> sourceFileNameViews = new HashMap<String, TextView>();
  /** ソースファイル名。 */
  Map<String, String> sourceFileNames = new HashMap<String,String>();
  /** ソースファイルを選択するためのボタン。 */
  List<Button> sourceSelectButtons = new ArrayList<Button>();
  /** ソースファイルを再読み込みするためのボタン。 */
  List<Button> sourceReloadButtons = new ArrayList<Button>();

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
    final View mainView = inflater.inflate(R.layout.fragment_file_selection, container, false);
    
    final Button backButton = (Button)mainView.findViewById(R.id.settingsBackButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
      }
    });

    createModelComponent(mainView);
    if (this.modelFileName.equals("...") == false) { //$NON-NLS-1$
      createSourceComponent(mainView);
    }

    return mainView;
  }

  /**
   * モデルデータを読み込むコンポーネントを生成します。
   * 
   * @param mainView
   */
  private void createModelComponent(View mainView) {
    final Button modelButton = (Button)mainView.findViewById(R.id.modelSelectButton);
    modelButton.setOnClickListener(new View.OnClickListener() {
      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        FileSelectionFragment.this.mainActivity.sendFileChooseIntentForLoadingModel();
      }
    });

    this.modelFileNameView = (TextView)mainView.findViewById(R.id.modelFileNameView);
    this.modelFileNameView.setText(this.modelFileName);
    this.modelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  /**
   * ソースデータを読み込むコンポーネントを生成します。
   * 
   * @param mainView
   */
  private void createSourceComponent(View mainView) {
    final List<GroupModel> rootGroups = this.canvasFragment.root.getScene(0).getGroups();
    final Set<String> sourceIds = getAllSourceIds(rootGroups);

    final LinearLayout sources =  (LinearLayout)mainView.findViewById(R.id.layout_sources);
    sources.removeAllViews();
    this.sourceSelectButtons.clear();
    this.sourceReloadButtons.clear();
    this.sourceFileNameViews.clear();

    for (final String sourceId : sourceIds) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + sourceId + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sourceSelectButtons.add(selectButton);

      selectButton.setOnClickListener(new View.OnClickListener() {
        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          FileSelectionFragment.this.mainActivity.sendFileChooseIntentForLoadingSource(sourceId);
        }
      });

      final TextView fileNameView = (TextView)source.findViewById(R.id.sourceFileNameView);
      if (this.sourceFileNames.containsKey(sourceId)) {
        fileNameView.setText(this.sourceFileNames.get(sourceId));
      }
      this.sourceFileNameViews.put(sourceId, fileNameView);

      final Button reloadButton = (Button)source.findViewById(R.id.sourceReloadButton);
      this.sourceReloadButtons.add(reloadButton);

      reloadButton.setOnClickListener(new View.OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          if (FileSelectionFragment.this.canvasFragment.sourceData.containsKey(sourceId)) {
            FileSelectionFragment.this.canvasFragment.addSource(sourceId);
          }
        }
      });
    }

  }

  /**
   * 全ての含まれるアニメーソンを返します。
   * 
   * @param groups グループ
   * 
   * @return 全ての含まれるアニメーソン
   */
  private List<AnimationModel> getAllAnimation(List<GroupModel> groups) {
    final List<AnimationModel> allAnimations = new ArrayList<AnimationModel>();

    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          allAnimations.add(animation);
        }
      }

      allAnimations.addAll(getAllAnimation(group.getGroups()));
    }

    return allAnimations;
  }

  /**
   * グループ以下に含まれるアニメーションのソースIDを返します。
   * 
   * @param ｇroups グループ
   * @return グループ以下に含まれるアニメーションのソースIDを返します。
   */
  private Set<String> getAllSourceIds(final List<GroupModel> ｇroups) {
    final List<AnimationModel> allAnimations = getAllAnimation(ｇroups);

    final Set<String> ids = new TreeSet<String>();
    for (final AnimationModel animation : allAnimations) {
      ids.add(animation.getSource().getId());
    }
    return ids;
  }

  /**
   * ソースデータをURIから読み込みます。
   * 
   * @param sourceFileUri ソースデータURI
   * @param sourceId ソースID
   */
  public void loadSourceData(Uri sourceFileUri, String sourceId) {
    if (sourceFileUri == null) {
      return;
    }

    final String sourceFileName;
    final InputStream sourceStream;

    if ("content".equals(sourceFileUri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        sourceStream = getActivity().getContentResolver().openInputStream(sourceFileUri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor = getActivity().getContentResolver().query(sourceFileUri, null, null, null, null);
      cursor.moveToFirst();
      sourceFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor.close();
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String sourceDataFilePath = sourceFileUri.getPath();
      try {
        sourceStream = new FileInputStream(sourceDataFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = sourceDataFilePath.split("/"); //$NON-NLS-1$
      sourceFileName = parts[parts.length - 1];
    }

    this.sourceFileNameViews.get(sourceId).setText(sourceFileName);
    this.sourceFileNames.put(sourceId, sourceFileName);

    this.canvasFragment.loadSourceDataInBackground(sourceStream, sourceFileUri.getPath(), sourceId);
    // sourceStream has been already closed in the loadSourceData method. 
  }

  /**
   * モデルをURIから読み込みます。
   * 
   * @param modelFileUri モデルURI
   */
  public void loadModelData(Uri modelFileUri) {
    if (modelFileUri == null) {
      return;
    }

    final InputStream modelInputStream;

    if ("content".equals(modelFileUri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        modelInputStream = this.mainActivity.getContentResolver().openInputStream(modelFileUri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor = this.mainActivity.getContentResolver().query(modelFileUri, null, null, null, null);
      cursor.moveToFirst();
      this.modelFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor.close();
    } else {
      // URIをファイルパスに変換し、その後ストリームを取り出します。
      final String modelFilePath = modelFileUri.getPath();
      try {
        modelInputStream = new FileInputStream(modelFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      
      try {
        final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
        this.modelFileName =  URLDecoder.decode(parts[parts.length - 1], "utf-8"); //$NON-NLS-1$
      } catch (UnsupportedEncodingException e) {
        try {
          modelInputStream.close();
        } catch (IOException e1) {
          throw new RuntimeException(e1);
        }
        throw new RuntimeException(e);
      }
    }

    this.modelFileNameView.setText(this.modelFileName);
    final String sourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sourceFileNameViews.values()) {
      view.setText(sourceFileName);
    }
    this.sourceFileNames.clear();

    try {
      this.canvasFragment.loadModelData(modelInputStream);
      modelInputStream.close();

      if (this.canvasFragment.sourceData.size() != 0) {
        this.canvasFragment.sourceData.clear();
      }

      createSourceComponent(getView());
      
      setButtonEnabled(true);

    } catch (Mikity3dSerializeDeserializeException e) {
      showMessageInDialog("please select model file."); //$NON-NLS-1$
      setButtonEnabled(false);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * モデルをURIへ保存します。
   * 
   * @param modelFileUri モデルURI
   * @return 正常に保存された場合はtrue
   */
  public boolean saveModelData(Uri modelFileUri) {
    final OutputStream modelOutputStream;

    if ("content".equals(modelFileUri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {        
        modelOutputStream = this.mainActivity.getContentResolver().openOutputStream(modelFileUri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else {
      // URIをファイルパスに変換し、その後ストリームを取り出します。
      final String modelFilePath = modelFileUri.getPath();
      try {
        modelOutputStream = new FileOutputStream(modelFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    try {
      this.canvasFragment.saveModelData(modelOutputStream);
      modelOutputStream.close();
    } catch (Mikity3dSerializeDeserializeException e) {
      try {
        modelOutputStream.close();
      } catch (IOException e1) {
        throw new RuntimeException(e1);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
        
    return true;
  }


  /**
   * メッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showMessageInDialog(String message) {
    final MessageDialogFragment fragment = new MessageDialogFragment();
    final Bundle arguments = new Bundle();
    arguments.putString("message", message); //$NON-NLS-1$
    fragment.setArguments(arguments);
    fragment.show(this.mainActivity.getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }

  /**
   * ボタンのアクティブ・非アクティブを設定します。
   * 
   * @param enabled trueならばアクティブ
   */
  void setButtonEnabled(boolean enabled) {
    this.isSelectedModelFile = enabled;

    for (final Button button : this.sourceSelectButtons) {
      button.setEnabled(enabled);
    }

    for (final Button button : this.sourceReloadButtons) {
      button.setEnabled(enabled);
    }
  }
}
