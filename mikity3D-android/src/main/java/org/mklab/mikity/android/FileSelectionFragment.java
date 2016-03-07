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
import java.util.List;
import java.util.Set;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * モデルデータとソースデータを選択するためのフラグメントです。
 * 
 * @author hirae
 * @version $Revision$, 2015/02/15
 */
public class FileSelectionFragment extends AbstractSelectionFragment implements CanvasFragment.LoadSourceDataTaskCallback, CanvasFragment.LoadModelDataTaskCallback, CanvasFragment.SaveModelDataTaskCallback {
  /** ソースファイルを再読み込みするためのボタン。 */
  List<Button> sourceReloadButtons = new ArrayList<Button>();
  
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
        final FragmentManager manager = FileSelectionFragment.this.mainActivity.getSupportFragmentManager();
        manager.popBackStack();
      }
    });

    createModelComponent(mainView);
    createSourceComponent(mainView);

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
        if (FileSelectionFragment.this.canvasFragment.isModelChanged() == false) {
          FileSelectionFragment.this.mainActivity.sendFileChooseIntentForLoadingModel();
          return;
        }
        
        final DialogFragment fragment = new DialogFragment() {
          /**
           * {@inheritDoc}
           */
          @Override
          public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.modelIsChanged) + " " + getString(R.string.willYouSave)) //$NON-NLS-1$
            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                if (FileSelectionFragment.this.mainActivity.modelFileUri == null) {
                  FileSelectionFragment.this.mainActivity.sendFileChooseIntentForSavingModel();
                } else {
                  FileSelectionFragment.this.mainActivity.saveModelData();
                }
                FileSelectionFragment.this.mainActivity.sendFileChooseIntentForLoadingModel();
              }
            })
            .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                FileSelectionFragment.this.mainActivity.sendFileChooseIntentForLoadingModel();
              }
            });
            return builder.create();
          }
        };

        fragment.show(getFragmentManager(), "confirmDialog"); //$NON-NLS-1$
      }
    });

    this.modelFileNameView = (TextView)mainView.findViewById(R.id.modelFileNameView);
    this.modelFileNameView.setText(this.modelFileName);
    this.modelFileNameView.setMovementMethod(ScrollingMovementMethod.getInstance());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() {
    super.reset();

    this.sourceReloadButtons.clear();
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
      final LinearLayout source = (LinearLayout)FileSelectionFragment.this.mainActivity.getLayoutInflater().inflate(R.layout.source, null);
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
        sourceStream = this.mainActivity.getContentResolver().openInputStream(sourceFileUri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      final Cursor cursor = this.mainActivity.getContentResolver().query(sourceFileUri, null, null, null, null);
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

    this.canvasFragment.loadSourceDataInBackground(sourceStream, sourceFileUri.getPath(), sourceFileName, sourceId, this);
    // sourceStream has been already closed in the loadSourceData method. 
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
   * モデルをURIから読み込みます。
   * 
   * @param modelFileUri モデルURI
   */
  public void loadModelData(Uri modelFileUri) {
    if (modelFileUri == null) {
      return;
    }

    final InputStream modelInputStream;
    String fileName;

    if ("content".equals(modelFileUri.getScheme())) { //$NON-NLS-1$
      try {
        modelInputStream = this.mainActivity.getContentResolver().openInputStream(modelFileUri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      
      final Cursor cursor = this.mainActivity.getContentResolver().query(modelFileUri, null, null, null, null);
      cursor.moveToFirst();
      fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor.close();
    } else {
      final String modelFilePath = modelFileUri.getPath();
      try {
        modelInputStream = new FileInputStream(modelFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      
      final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
      try {
        fileName = URLDecoder.decode(parts[parts.length - 1], "utf-8"); //$NON-NLS-1$
      } catch (UnsupportedEncodingException e) {
        fileName = parts[parts.length - 1];
      }
    }

    this.canvasFragment.loadModelDataInBackground(modelInputStream, fileName, this);
  }
  

  /**
   * {@inheritDoc}
   */
  public void onSuccessLoadModelData(InputStream input, String fileName) {
    try {
      input.close();

      this.modelFileName = fileName;
      this.modelFileNameView.setText(this.modelFileName);
      final String sourceFileName = "..."; //$NON-NLS-1$
      for (final TextView view : this.sourceFileNameViews.values()) {
        view.setText(sourceFileName);
      }
      this.sourceFileNames.clear();
      
      if (this.canvasFragment.sourceData.size() != 0) {
        this.canvasFragment.sourceData.clear();
      }

      createSourceComponent(getView());
    } catch (IOException e) {
      showMessageInDialog(e.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  public void onFailedLoadModelData(InputStream input, String fileName) {
    // nothing to do
  }
  
  /**
   * モデルをURIへ保存します。
   * 
   * @param modelFileUri モデルURI
   */
  public void saveModelData(Uri modelFileUri) {
    final OutputStream modelOutputStream;

    String fileName;
    if ("content".equals(modelFileUri.getScheme())) { //$NON-NLS-1$
      try {        
        modelOutputStream = this.mainActivity.getContentResolver().openOutputStream(modelFileUri);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      
      final Cursor cursor = this.mainActivity.getContentResolver().query(modelFileUri, null, null, null, null);
      cursor.moveToFirst();
      fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
      cursor.close();
    } else {
      final String modelFilePath = modelFileUri.getPath();
      try {
        modelOutputStream = new FileOutputStream(modelFilePath);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      
      final String[] parts = modelFilePath.split("/"); //$NON-NLS-1$
      try {
        fileName = URLDecoder.decode(parts[parts.length - 1], "utf-8"); //$NON-NLS-1$
      } catch (UnsupportedEncodingException e) {
        fileName = parts[parts.length - 1];
      }
    }

    this.canvasFragment.saveModelDataInBackground(modelOutputStream, fileName, this);
  }

  /**
   * {@inheritDoc}
   */
  public void onSuccessSaveModelData(OutputStream output, String fileName) {
    try {
      output.close();
    } catch (IOException e) {
      showMessageInDialog(e.getLocalizedMessage());
      return;
    }
    
    this.modelFileName = fileName;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  public void onFailedSaveModelData(OutputStream output, String fileName) {
    // nothing to do
  }
}
