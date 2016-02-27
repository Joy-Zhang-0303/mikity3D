/*
 * Created on 2015/02/05
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
    View mainView = inflater.inflate(R.layout.fragment_file_selection, container, false);
    
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

  private void createModelComponent(View view) {
    final Button modelButton = (Button)view.findViewById(R.id.modelSelectButton);
    modelButton.setOnClickListener(new View.OnClickListener() {

      final int REQUEST_CODE = MainActivity.REQUEST_CODE_PICK_MODEL_DATA_FILE;

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        FileSelectionFragment.this.mainActivity.sendFileChooseIntentForModel(this.REQUEST_CODE);
      }
    });

    this.modelFileNameView = (TextView)view.findViewById(R.id.modelFileNameView);
    this.modelFileNameView.setText(this.modelFileName);
    this.modelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  private void createSourceComponent(View view) {
    final List<GroupModel> rootGroups = this.canvasFragment.root.getScene(0).getGroups();
    final Set<String> sourceIds = getAllSourceIds(rootGroups);

    final LinearLayout sources =  (LinearLayout)view.findViewById(R.id.layout_sources);
    sources.removeAllViews();
    this.sourceSelectButtons.clear();
    this.sourceReloadButtons.clear();
    this.sourceFileNameViews.clear();

    for (final String id : sourceIds) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sourceSelectButtons.add(selectButton);

      selectButton.setOnClickListener(new View.OnClickListener() {

        final int REQUEST_CODE = MainActivity.REQUEST_CODE_PICK_SOURCE_DATA_FILE;

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          FileSelectionFragment.this.mainActivity.sendFileChooseIntentForSource(this.REQUEST_CODE, id);
        }
      });

      final TextView fileNameView = (TextView)source.findViewById(R.id.sourceFileNameView);
      if (this.sourceFileNames.containsKey(id)) {
        fileNameView.setText(this.sourceFileNames.get(id));
      }
      this.sourceFileNameViews.put(id, fileNameView);

      final Button reloadButton = (Button)source.findViewById(R.id.sourceReloadButton);
      this.sourceReloadButtons.add(reloadButton);

      reloadButton.setOnClickListener(new View.OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          if (FileSelectionFragment.this.canvasFragment.sourceData.containsKey(id)) {
            FileSelectionFragment.this.canvasFragment.addSource(id);
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
   * 時間データをURIから読み込みます。
   * 
   * @param uri 時間データURI
   * @param sourceId ソースID
   */
  public void loadSourceData(Uri uri, String sourceId) {
    if (uri == null) {
      return;
    }

    final String sourceFileName;
    final InputStream sourceStream;

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        sourceStream = this.mainActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor2 = this.mainActivity.getContentResolver().query(uri, null, null, null, null);
      cursor2.moveToFirst();
      sourceFileName = cursor2.getString(cursor2.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor2.close();
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String sourceDataFilePath = uri.getPath();
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

    this.canvasFragment.loadSourceData(sourceStream, uri.getPath(), sourceId);
    // sourceStream has been already closed in the loadSourceData method. 
  }

  /**
   * モデルをURIから読み込みます。
   * 
   * @param uri モデルURI
   */
  void loadModelData(Uri uri) {
    if (uri == null) {
      return;
    }

    final InputStream modelStream;

    if ("content".equals(uri.getScheme())) { //$NON-NLS-1$
      // ストリームを直接URIから取り出します。
      try {
        modelStream = this.mainActivity.getContentResolver().openInputStream(uri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor = this.mainActivity.getContentResolver().query(uri, null, null, null, null);
      cursor.moveToFirst();
      this.modelFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor.close();
      // URIをファイルパスに変換し、その後ストリームを取り出します。
    } else {
      final String modelFilePath = uri.getPath();
      try {
        modelStream = new FileInputStream(modelFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
      this.modelFileName = parts[parts.length - 1];
    }

    this.modelFileNameView.setText(this.modelFileName);
    final String sourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sourceFileNameViews.values()) {
      view.setText(sourceFileName);
    }
    this.sourceFileNames.clear();

    try {
      this.canvasFragment.loadModelData(modelStream);
      modelStream.close();

      if (this.canvasFragment.sourceData.size() != 0) {
        this.canvasFragment.sourceData.clear();
      }

      createSourceComponent(getView());
      
      setButtonEnabled(true);

    } catch (Mikity3dSerializeDeserializeException e) {
      showAlertMessageInDialog("please select model file."); //$NON-NLS-1$
      setButtonEnabled(false);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 警告メッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showAlertMessageInDialog(String message) {
    final AlertDialogFragment dialog = new AlertDialogFragment();
    final Bundle bundle = new Bundle();
    bundle.putString("message", message); //$NON-NLS-1$
    dialog.setArguments(bundle);
    dialog.show(this.mainActivity.getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }

  /**
   * @param enabled
   */
  void setButtonEnabled(boolean enabled) {
    this.isSelectedModelFile = enabled;

    for (Button button : this.sourceSelectButtons) {
      button.setEnabled(enabled);
    }

    for (Button button : this.sourceReloadButtons) {
      button.setEnabled(enabled);
    }
  }
}
