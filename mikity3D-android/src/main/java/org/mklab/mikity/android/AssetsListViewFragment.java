/*
 * Created on 2015/02/19
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;

import roboguice.fragment.RoboFragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Assets中のファイルを操作するためのフラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2015/03/26
 */
public class AssetsListViewFragment extends RoboFragment {
  String currentPath = "sample"; //$NON-NLS-1$
  CanvasActivity canvasActivity;
  AssetManager assetManager;
  boolean isModel;
  FragmentManager fragmentManager;
  
  /** ソースID。 */
  String sourceId;
  
  /**
   * 新しく生成された<code>AssetsListViewFragment</code>オブジェクトを初期化します。
   * @param sourceID ソースID
   */
  public AssetsListViewFragment(String sourceID) {
    this.sourceId = sourceID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    final View view = inflater.inflate(R.layout.list_assets, container, false);

    this.assetManager = getResources().getAssets();
    
    final ListView listView = (ListView)view.findViewById(R.id.assetsListView);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.canvasActivity, android.R.layout.simple_list_item_1, getFilesInAssets(this.currentPath));
    listView.setAdapter(adapter);

    // リスト項目がクリックされた時の処理
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String item = (String)listView.getItemAtPosition(position);
        final String nextFile = AssetsListViewFragment.this.currentPath + File.separator + item;
        String[] nextFiles = getFilesInAssets(nextFile);
        List<String> nextFileLimits = new ArrayList<String>();

        if (AssetsListViewFragment.this.isModel) {
          nextFileLimits = getLimitList(nextFiles, new String[]{"m3d"}); //$NON-NLS-1$
        } else {
          nextFileLimits = getLimitList(nextFiles, new String[]{"mat", "csv", "txt"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        nextFiles = nextFileLimits.toArray(new String[nextFileLimits.size()]);

        if (nextFiles.length > 0) {
          AssetsListViewFragment.this.currentPath = nextFile;
          listView.setAdapter(new ArrayAdapter<String>(AssetsListViewFragment.this.canvasActivity, android.R.layout.simple_list_item_1, nextFiles));
        } else {
          //copyAssetsFiles(AssetsListViewFragment.this.currentPath, item, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator + "org.mklab.mikity")); //$NON-NLS-1$

          try {
            // input should not be closed since it is a virtual stream for asset
            final InputStream input = AssetsListViewFragment.this.assetManager.open(nextFile);

            if (AssetsListViewFragment.this.isModel) {
              //AssetsListViewFragment.this.canvasActivity.canvasFragment.loadModelData(input);
              AssetsListViewFragment.this.canvasActivity.ndFragment.loadSampleModelData(input, nextFile);
              
              if (AssetsListViewFragment.this.canvasActivity.canvasFragment.sourceData.size() != 0) {
                AssetsListViewFragment.this.canvasActivity.canvasFragment.sourceData.clear();
              }
              
              AssetsListViewFragment.this.canvasActivity.ndFragment.isSelectedModelFile = true;
              
              AssetsListViewFragment.this.canvasActivity.ndFragment.createSampleSourceComponent();
              
              AssetsListViewFragment.this.canvasActivity.ndFragment.setButtonEnabled(true);
            } else {
              //AssetsListViewFragment.this.canvasActivity.canvasFragment.loadSourceData(input, nextFile, AssetsListViewFragment.this.sourceId);
              AssetsListViewFragment.this.canvasActivity.ndFragment.loadSampleSourceData(input, nextFile, AssetsListViewFragment.this.sourceId);
            }

            AssetsListViewFragment.this.fragmentManager.popBackStack();

          } catch (IOException e) {
            throw new RuntimeException(e);
          } catch (Mikity3dSerializeDeserializeException e) {
            throw new RuntimeException(e);
          }
        }
      }
    });
    
    return view;
  }

  /**
   * ダイアログに警告メッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showAlertMessageInDailog(String message) {
    final AlertDialogFragment dialog = new AlertDialogFragment();
    dialog.setMessage(message);
    dialog.show(getFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }

  /**
   * Assets中のファイルを返します。
   * 
   * @param folderName フォルダーの名前
   * @return Assets中のファイル
   */
  String[] getFilesInAssets(String folderName) {
    String[] files = null;
    try {
      files = this.assetManager.list(folderName);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return files;
  }

  /**
   * Assets中のファイルを読み込みます。
   * 
   * @param filePath ファイルパス
   * 
   * @return 読み込んだファイルの内容
   */
  @SuppressWarnings("resource")
  String loadFileInAssets(String filePath) {
    String text = ""; //$NON-NLS-1$
    try {
      final InputStream input = this.assetManager.open(filePath);
      final BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
      String string;
      while ((string = buffer.readLine()) != null) {
        text += string + "\n"; //$NON-NLS-1$
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return text;
  }

  /**
   * Assets中のファイルをコピーします。
   * 
   * @param parentPath 親パス
   * @param filePath ファイルパス
   * @param folder フォルダー
   */
  void copyFilesInAssets(final String parentPath, final String filePath, File folder) {
    if (!folder.exists()) {
      folder.mkdirs();
    }

    final File file = new File(folder, filePath);
    if (file.exists()) {
      file.delete();
    }

    try {
      final OutputStream output = new FileOutputStream(file);
      final byte[] buffer = new byte[1024];
      int length = 0;
      final InputStream input = this.canvasActivity.getAssets().open(parentPath + File.separator + filePath);
      while ((length = input.read(buffer)) >= 0) {
        output.write(buffer, 0, length);
      }
      output.close();
      input.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * アクティビティを設定します。
   * 
   * @param activity アクティビティ
   */
  public void setActivity(CanvasActivity activity) {
    this.canvasActivity = activity;
  }

  /**
   * モデルデータであるか判定するための値を設定します。
   * 
   * @param isModelData モデルデータならばtrue
   */
  public void setIsModelData(boolean isModelData) {
    this.isModel = isModelData;
  }

  /**
   * 拡張子を返します。
   * 
   * 拡張子がない場合、そのままファイル名を返します。
   * 
   * @param fileName ファイル名
   * @return 拡張子
   */
  public String getSuffix(String fileName) {
    if (fileName == null) {
      return null;
    }
    
    final int point = fileName.lastIndexOf("."); //$NON-NLS-1$
   
    if (point != -1) {
      return fileName.substring(point + 1);
    }
    return fileName;
  }

  /**
   * 指定された拡張子をもつファイルのみのリストを返します。
   * 
   * @param files ファイルのリスト  
   * @param extensions 拡張子
   * @return 指定された拡張子をもつファイルのみのリスト
   */
  public List<String> getLimitList(String[] files, String[] extensions) {
    final List<String> nextFileLimits = new ArrayList<String>();
    
    for (String file : files) {
      for (String extension : extensions) {
        if (getSuffix(file).toLowerCase().equals(extension)) {
          nextFileLimits.add(file);
        }
      }
    }
    return nextFileLimits;
  }

  /**
   * 
   */
  public void commitFragmentTransaction() {
    final FragmentManager manager = getFragmentManager();
    final FragmentTransaction transaction = manager.beginTransaction();
    final NavigationDrawerFragment fragment = new NavigationDrawerFragment();

    transaction.replace(R.id.assets_list_layput, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  /**
   * フラグメントマネージャを設定します。
   * 
   * @param fragmentManager フラグメントマネージャ
   */
  public void setFragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }
}
