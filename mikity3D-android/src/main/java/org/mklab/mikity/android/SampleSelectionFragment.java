/*
 * Created on 2015/02/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * サンプルのモデルデータとソースデータを選択するためのフラグメントです。
 * 
 * @author hirae
 * @version $Revision$, 2015/02/15
 */
public class SampleSelectionFragment extends Fragment {
  MainActivity mainActivity;
  
  CanvasFragment canvasFragment;
  
  /** サンプルモデルファイル名。 */
  String sampleModelFileName = "..."; //$NON-NLS-1$
  /** サンプルのモデルファイルのパス。 */
  TextView sampleModelFileNameView;

  /** 3Dモデルが選ばれて表示されたならばtrue。 */
  boolean isSelectedModelFile;

  /** サンプルのソースファイルのパス。 */
  Map<String, TextView> sampleSourceFileNameViews = new HashMap<String, TextView>();
  /** サンプルソースファイル名。 */
  Map<String, String> sampleSourceFileNames = new HashMap<String, String>();
  
  /** サンプルのソースファイルを選択するためのボタン */
  List<Button> sampleSourceSelectButtons = new ArrayList<Button>();

  AssetsListViewFragment sampleModelViewFragment;

  AssetsListViewFragment sampleSourceViewFragment;

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
    View mainView = inflater.inflate(R.layout.fragment_sample_selection, container, false);
    
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

    createSampleModelComponent(mainView);
    if (this.sampleModelFileName.equals("...") == false) { //$NON-NLS-1$
      createSampleSourceComponent(mainView);
    }

    return mainView;
  }

  private void createSampleModelComponent(View view) {
    final Button modelButton = (Button)view.findViewById(R.id.sampleModelSelectButton);
    modelButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);

        if (SampleSelectionFragment.this.sampleModelViewFragment != null) {
          transaction.remove(SampleSelectionFragment.this.sampleModelViewFragment);
          SampleSelectionFragment.this.sampleModelViewFragment = null;
        }
        
        SampleSelectionFragment.this.sampleModelViewFragment = new AssetsListViewFragment();
        final Bundle arguments = new Bundle();
        arguments.putBoolean("isModelData", true); //$NON-NLS-1$
        SampleSelectionFragment.this.sampleModelViewFragment.setArguments(arguments);

        transaction.add(R.id.fragment_navigation_drawer, SampleSelectionFragment.this.sampleModelViewFragment);
        transaction.commit();
      }
    });

    this.sampleModelFileNameView = (TextView)view.findViewById(R.id.sampleModelFileNameView);
    this.sampleModelFileNameView.setText(this.sampleModelFileName);
    this.sampleModelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
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
   * サンプルのソースを読み込むボタンを更新します。
   */
  public void updateSampleSourceComponent() {
    createSampleSourceComponent(getView());
  }

  /**
   * サンプルのソースを読み込むボタンを生成します。
   */
  void createSampleSourceComponent(View view) {
    final List<GroupModel> rootGroups = this.canvasFragment.root.getScene(0).getGroups();
    final Set<String> sourceIds = getAllSourceIds(rootGroups);

    final LinearLayout sources = (LinearLayout)view.findViewById(R.id.layout_sample_sources);
    sources.removeAllViews();

    this.sampleSourceSelectButtons.clear();
    this.sampleSourceFileNameViews.clear();

    for (final String id : sourceIds) {
      final LinearLayout source = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.sample_source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sampleSourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sampleSourceSelectButtons.add(selectButton);

      selectButton.setOnClickListener(new OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          final FragmentManager manager = getActivity().getSupportFragmentManager();
          final FragmentTransaction transaction = manager.beginTransaction();
          transaction.addToBackStack(null);

          if (SampleSelectionFragment.this.sampleSourceViewFragment != null) {
            transaction.remove(SampleSelectionFragment.this.sampleSourceViewFragment);
            SampleSelectionFragment.this.sampleSourceViewFragment = null;
          }
          
          SampleSelectionFragment.this.sampleSourceViewFragment = new AssetsListViewFragment();
          final Bundle arguments = new Bundle();
          arguments.putString("sourceId", id); //$NON-NLS-1$
          arguments.putBoolean("isModelData", false); //$NON-NLS-1$
          SampleSelectionFragment.this.sampleSourceViewFragment.setArguments(arguments);

          transaction.add(R.id.fragment_navigation_drawer, SampleSelectionFragment.this.sampleSourceViewFragment);
          transaction.commit();
        }
      });

      final TextView sourceFileNameView = (TextView)source.findViewById(R.id.sampleSourceFileNameView);
      if (this.sampleSourceFileNames.containsKey(id)) {
        sourceFileNameView.setText(this.sampleSourceFileNames.get(id));
      }
      this.sampleSourceFileNameViews.put(id, sourceFileNameView);

    }
  }

  /**
   * ストリームからサンプルソースデータを取り出します。
   * 
   * @param input ソースの入力ストリーム
   * @param filePath ソースのファイルパス
   * @param sourceId ソースID
   */
  public void loadSampleSourceData(final InputStream input, final String filePath, String sourceId) {
    this.canvasFragment.loadSourceData(input, filePath, sourceId);

    final String[] parts = filePath.split("/"); //$NON-NLS-1$
    final String sourceFileName = parts[parts.length - 1];
    this.sampleSourceFileNameViews.get(sourceId).setText(sourceFileName);
    this.sampleSourceFileNames.put(sourceId, sourceFileName);
  }

  void loadSampleModelData(InputStream modelStream, String modelFilePath) throws Mikity3dSerializeDeserializeException {
    this.canvasFragment.loadModelData(modelStream);

    final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
    this.sampleModelFileName = parts[parts.length - 1];
    this.sampleModelFileNameView.setText(this.sampleModelFileName);

    final String sampleSourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sampleSourceFileNameViews.values()) {
      view.setText(sampleSourceFileName);
    }
    
    this.sampleSourceFileNames.clear();
    
    if (this.canvasFragment.sourceData.size() != 0) {
      this.canvasFragment.sourceData.clear();
    }
    this.isSelectedModelFile = true;
    updateSampleSourceComponent();
    setButtonEnabled(true);
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

    for (Button button : this.sampleSourceSelectButtons) {
      button.setEnabled(enabled);
    }
  }
}
