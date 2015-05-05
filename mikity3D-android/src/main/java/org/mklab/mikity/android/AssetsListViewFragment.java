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
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * @author koga
 * @version $Revision$, 2015/03/26
 */
public class AssetsListViewFragment extends RoboFragment {

  private View view;
  ListView listView;
  String currentPath = "sample"; //$NON-NLS-1$
  CanvasActivity canvasActivity;
  AssetManager assetManager;
  boolean isModel;
  FragmentManager fragmentManager;

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    this.view = inflater.inflate(R.layout.list_assets, container, false);

    this.assetManager = getResources().getAssets();
    this.listView = (ListView)this.view.findViewById(R.id.assetsListView);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.canvasActivity, android.R.layout.simple_list_item_1, loadAssetsFolder(this.currentPath));
    this.listView.setAdapter(adapter);

    // リスト項目がクリックされた時の処理
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String item = (String)AssetsListViewFragment.this.listView.getItemAtPosition(position);
        final String nextFile = AssetsListViewFragment.this.currentPath + File.separator + item;
        String[] nextFiles = loadAssetsFolder(nextFile);
        List<String> nextFileLimits = new ArrayList<String>();

        if (AssetsListViewFragment.this.isModel) {
          nextFileLimits = getLimitList("mat", nextFiles); //$NON-NLS-1$
        } else {
          nextFileLimits = getLimitList("m3d", nextFiles); //$NON-NLS-1$
        }

        final String[] newNextFiles = nextFileLimits.toArray(new String[nextFileLimits.size()]);
        nextFiles = nextFileLimits.toArray(new String[nextFileLimits.size()]);

        if (nextFiles.length > 0) {
          AssetsListViewFragment.this.currentPath = nextFile;
          AssetsListViewFragment.this.listView.setAdapter(new ArrayAdapter<String>(AssetsListViewFragment.this.canvasActivity, android.R.layout.simple_list_item_1, nextFiles));
        } else {
          copyAssetsFiles(AssetsListViewFragment.this.currentPath, item, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator + "org.mklab.mikity")); //$NON-NLS-1$

          try {
            final InputStream input = AssetsListViewFragment.this.assetManager.open(nextFile);

            if (AssetsListViewFragment.this.isModel) {
              AssetsListViewFragment.this.canvasActivity.canvasFragment.loadModelFile(input);
              AssetsListViewFragment.this.canvasActivity.ndFragment.isSelectedModelFile = true;
              AssetsListViewFragment.this.canvasActivity.ndFragment.setButtonEnabled(true);
              AssetsListViewFragment.this.canvasActivity.ndFragment.assetsTimeButton.setEnabled(true);
            } else {
              AssetsListViewFragment.this.canvasActivity.canvasFragment.loadtimeSeriesData(input);
            }

            AssetsListViewFragment.this.fragmentManager.popBackStack();
            input.close();
          } catch (IOException e) {
            throw new RuntimeException(e);
          } catch (Mikity3dSerializeDeserializeException e) {
            throw new RuntimeException(e);
          }
        }
      }
    });
    return this.view;
  }

  /**
   * @param message
   */
  protected void setExceptionDailogFragment(String message) {
    final DialogFragment dialogFragment = new ExceptionDialogFragment();
    ((ExceptionDialogFragment)dialogFragment).setMessage(message);
    dialogFragment.show(getFragmentManager(), "exceptionDialogFragment"); //$NON-NLS-1$
  }

  /**
   * @param folderName
   * @return
   */
  String[] loadAssetsFolder(String folderName) {
    String[] files = null;
    try {
      files = this.assetManager.list(folderName);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return files;
  }

  /**
   * @param filePath
   * @return
   */
  @SuppressWarnings("resource")
  String loadAssetsFile(String filePath) {
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
   * @param parentPath
   * @param filename
   * @param folder
   */
  void copyAssetsFiles(final String parentPath, final String filename, File folder) {
    if (!folder.exists()) {
      folder.mkdirs();
    }

    final File file = new File(folder, filename);
    if (file.exists()) {
      file.delete();
    }

    try {
      final OutputStream output = new FileOutputStream(file);
      final byte[] buffer = new byte[1024];
      int length = 0;
      final InputStream input = this.canvasActivity.getAssets().open(parentPath + File.separator + filename);
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
   * @param activity アクティビティ
   */
  public void setActivity(CanvasActivity activity) {
    this.canvasActivity = activity;
  }

  /**
   * @param isModel モデルならばtrue
   */
  public void setModelTimeFrag(boolean isModel) {
    this.isModel = isModel;
  }

  /**
   * @param fileName ファイル名
   * @return 拡張子
   */
  public String getSuffix(String fileName) {
    if (fileName == null) {
      return null;
    }
    
    int point = fileName.lastIndexOf("."); //$NON-NLS-1$
   
    if (point != -1) {
      return fileName.substring(point + 1);
    }
    return fileName;
  }

  /**
   * @param extension 拡張子
   * @param nextFileList ファイルのリスト
   * @return 対象となるファイルのリスト
   */
  public List<String> getLimitList(String extension, String[] nextFileList) {
    final List<String> nextFileLimits = new ArrayList<String>();
    for (int i = 0; i < nextFileList.length; i++) {
      final String a = getSuffix(nextFileList[i]);
      if (getSuffix(nextFileList[i]).equals(extension)) {
        // nothing to do
      } else {
        nextFileLimits.add(nextFileList[i]);
      }
    }
    return nextFileLimits;
  }

  /**
   * 
   */
  public void fragmentTransaction() {
    final FragmentManager localFragmentManager = getFragmentManager();
    final FragmentTransaction fragmentTransaction = localFragmentManager.beginTransaction();
    final NavigationDrawerFragment fragment = new NavigationDrawerFragment();

    fragmentTransaction.replace(R.id.assets_list_layput, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  /**
   * @param fragmentManager フラグメントマネージャ
   */
  public void setFragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }
}
