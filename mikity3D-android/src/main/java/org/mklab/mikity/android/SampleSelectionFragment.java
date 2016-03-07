/*
 * Created on 2015/02/05
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import android.os.Bundle;
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
public class SampleSelectionFragment extends AbstractSelectionFragment implements CanvasFragment.LoadSourceDataTaskCallback, CanvasFragment.LoadModelDataTaskCallback {
  AssetsListViewFragment modelViewFragment;

  AssetsListViewFragment sourceViewFragment;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View mainView = inflater.inflate(R.layout.fragment_sample_selection, container, false);
    
    final Button backButton = (Button)mainView.findViewById(R.id.settingsBackButton);
    backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        final FragmentManager manager = SampleSelectionFragment.this.mainActivity.getSupportFragmentManager();
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
   * モデルのコンポーネントを生成します。
   * 
   * @param mainView
   */
  private void createModelComponent(View mainView) {
    final Button modelButton = (Button)mainView.findViewById(R.id.sampleModelSelectButton);
    modelButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View view) {
        final FragmentManager manager = SampleSelectionFragment.this.mainActivity.getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);

        if (SampleSelectionFragment.this.modelViewFragment != null) {
          transaction.remove(SampleSelectionFragment.this.modelViewFragment);
          SampleSelectionFragment.this.modelViewFragment = null;
        }
        
        SampleSelectionFragment.this.modelViewFragment = new AssetsListViewFragment();
        final Bundle arguments = new Bundle();
        arguments.putBoolean("isModelData", true); //$NON-NLS-1$
        SampleSelectionFragment.this.modelViewFragment.setArguments(arguments);

        transaction.add(R.id.fragment_navigation_drawer, SampleSelectionFragment.this.modelViewFragment);
        transaction.commit();
      }
    });

    this.modelFileNameView = (TextView)mainView.findViewById(R.id.sampleModelFileNameView);
    this.modelFileNameView.setText(this.modelFileName);
    this.modelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  /**
   * サンプルのソースを読み込むコンポーネントを更新します。
   */
  public void updateSourceComponent() {
    createSourceComponent(getView());
  }

  /**
   * のソースを読み込むコンポーネントを生成します。
   */
  void createSourceComponent(View mainView) {
    final List<GroupModel> rootGroups = this.canvasFragment.root.getScene(0).getGroups();
    final Set<String> sourceIds = getAllSourceIds(rootGroups);

    final LinearLayout sources = (LinearLayout)mainView.findViewById(R.id.layout_sample_sources);
    sources.removeAllViews();

    this.sourceSelectButtons.clear();
    this.sourceFileNameViews.clear();

    for (final String id : sourceIds) {
      final LinearLayout source = (LinearLayout)SampleSelectionFragment.this.mainActivity.getLayoutInflater().inflate(R.layout.sample_source, null);
      sources.addView(source);

      final Button selectButton = (Button)source.findViewById(R.id.sampleSourceSelectButton);
      selectButton.setText(getString(R.string.source) + "(" + id + ")"); //$NON-NLS-1$ //$NON-NLS-2$
      this.sourceSelectButtons.add(selectButton);

      selectButton.setOnClickListener(new OnClickListener() {

        /**
         * {@inheritDoc}
         */
        public void onClick(View view) {
          final FragmentManager manager = SampleSelectionFragment.this.mainActivity.getSupportFragmentManager();
          final FragmentTransaction transaction = manager.beginTransaction();
          transaction.addToBackStack(null);

          if (SampleSelectionFragment.this.sourceViewFragment != null) {
            transaction.remove(SampleSelectionFragment.this.sourceViewFragment);
            SampleSelectionFragment.this.sourceViewFragment = null;
          }
          
          SampleSelectionFragment.this.sourceViewFragment = new AssetsListViewFragment();
          final Bundle arguments = new Bundle();
          arguments.putString("sourceId", id); //$NON-NLS-1$
          arguments.putBoolean("isModelData", false); //$NON-NLS-1$
          SampleSelectionFragment.this.sourceViewFragment.setArguments(arguments);

          transaction.add(R.id.fragment_navigation_drawer, SampleSelectionFragment.this.sourceViewFragment);
          transaction.commit();
        }
      });

      final TextView sourceFileNameView = (TextView)source.findViewById(R.id.sampleSourceFileNameView);
      if (this.sourceFileNames.containsKey(id)) {
        sourceFileNameView.setText(this.sourceFileNames.get(id));
      }
      this.sourceFileNameViews.put(id, sourceFileNameView);

    }
  }

  /**
   * ストリームからソースデータを読み込みます。
   * 
   * @param sourceInputStream ソースの入力ストリーム
   * @param sourceFilePath ソースのファイルパス
   * @param sourceId ソースID
   */
  public void loadSourceData(final InputStream sourceInputStream, final String sourceFilePath, String sourceId) {
    final String[] parts = sourceFilePath.split("/"); //$NON-NLS-1$
    final String sourceFileName = parts[parts.length - 1];
    
    this.canvasFragment.loadSourceDataInBackground(sourceInputStream, sourceFilePath, sourceFileName, sourceId, this);
  }
  

  /**
   * {@inheritDoc}
   */
  public void onSuccessLoadSourceData(String sourceId, String fileName) {
    this.sourceFileNameViews.get(sourceId).setText(fileName);
    this.sourceFileNames.put(sourceId, fileName);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  public void onFailedLoadSourceData(String sourceId, String fileName) {
    // nothing to do
  }

  /**
   * ストリームからモデルデータを読み込みます。
   * 
   * @param modelInputStream モデルの入力ストリーム
   * @param modelFilePath モデルのファイルパス
   */
  void loadModelData(InputStream modelInputStream, String modelFilePath)  {
    final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
    final String fileName = parts[parts.length - 1];
    
    this.canvasFragment.loadModelDataInBackground(modelInputStream, fileName, this);
  }

  /**
   * {@inheritDoc}
   */
  public void onSuccessLoadModelData(InputStream input, String fileName) {
    this.modelFileName = fileName;
    this.modelFileNameView.setText(this.modelFileName);

    final String sampleSourceFileName = "..."; //$NON-NLS-1$
    for (final TextView view : this.sourceFileNameViews.values()) {
      view.setText(sampleSourceFileName);
    }
    
    this.sourceFileNames.clear();
    
    if (this.canvasFragment.sourceData.size() != 0) {
      this.canvasFragment.sourceData.clear();
    }

    updateSourceComponent();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  public void onFailedLoadModelData(InputStream input, String fileName) {
    // nothing to do
  }
}
