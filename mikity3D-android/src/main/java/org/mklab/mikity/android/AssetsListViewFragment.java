/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class AssetsListViewFragment extends Fragment {
  static String currentPath = "sample"; //$NON-NLS-1$
  MainActivity mainActivity;
  AssetManager assetManager;
  boolean isModelData;
  String sourceId;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    
    this.mainActivity = (MainActivity)getActivity();
    
    final Bundle arguments = getArguments();
    if (arguments != null && arguments.containsKey("sourceId")) { //$NON-NLS-1$
      this.sourceId = arguments.getString("sourceId"); //$NON-NLS-1$
    }
    
    if (arguments != null && arguments.containsKey("isModelData")) { //$NON-NLS-1$
      this.isModelData = arguments.getBoolean("isModelData"); //$NON-NLS-1$
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    final View view = inflater.inflate(R.layout.fragment_assets_list_view, container, false);

    this.assetManager = getResources().getAssets();
    
    final ListView listView = (ListView)view.findViewById(R.id.assetsListView);
    
    final String selectedItem = AssetsListViewFragment.currentPath;
    final String[] selectedFiles = getSelectedFiles(selectedItem);
    
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.mainActivity, R.layout.source_item, selectedFiles);
    listView.setAdapter(adapter);

    // リスト項目がクリックされた時の処理
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String)listView.getItemAtPosition(position);
        if (item.endsWith(File.separator)) {
          item = item.substring(0,item.length()-1);
        }
        
        final String selectedItem;
        if (item.equals("..")) { //$NON-NLS-1$
          selectedItem = getParentFolder(AssetsListViewFragment.currentPath);
        } else { 
          selectedItem = AssetsListViewFragment.currentPath + File.separator + item;
        }

        final String[] selectedFiles = getSelectedFiles(selectedItem);

        if (selectedFiles.length > 1) {
          AssetsListViewFragment.currentPath = selectedItem;
          ArrayAdapter<String> adapter = new ArrayAdapter<String>(AssetsListViewFragment.this.mainActivity, R.layout.source_item, selectedFiles);
          listView.setAdapter(adapter);
          return;
        }

        final String selectedFile = selectedItem;
          
        try {
          // input should not be closed since it is a virtual stream for asset
          final InputStream input = AssetsListViewFragment.this.assetManager.open(selectedFile);
          
          if (AssetsListViewFragment.this.isModelData) {
            AssetsListViewFragment.this.mainActivity.sampleSelectionFragment.loadModelData(input, selectedFile);
          } else {
            AssetsListViewFragment.this.mainActivity.sampleSelectionFragment.loadSourceData(input, selectedFile, AssetsListViewFragment.this.sourceId);
          }
          
          getActivity().getSupportFragmentManager().popBackStack();

        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
    
    return view;
  }

  String[] getSelectedFiles(String selectedItem) {
    String[] filesInFolder = getFilesInPath(selectedItem);
    final List<String> selectedFileList  = new ArrayList<String>();
    selectedFileList.add(".."); //$NON-NLS-1$

    if (AssetsListViewFragment.this.isModelData) {
      selectedFileList.addAll(selectFilesWithExtensionsOrFolders(selectedItem, filesInFolder, new String[]{"m3d"})); //$NON-NLS-1$
    } else {
      selectedFileList.addAll(selectFilesWithExtensionsOrFolders(selectedItem, filesInFolder, new String[]{"mat", "csv", "txt"})); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    final String[] selectedFiles = selectedFileList.toArray(new String[selectedFileList.size()]);
    return selectedFiles;
  }

  /**
   * ダイアログにメッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showMessageInDailog(String message) {
    final MessageDialogFragment dialog = new MessageDialogFragment();
    final Bundle arguments = new Bundle();
    arguments.putString("message", message); //$NON-NLS-1$
    dialog.setArguments(arguments);
    dialog.show(this.mainActivity.getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }

  /**
   * 指定したパスの中のファイル一覧を返します。
   * 
   * @param path パス
   * 
   * @return 指定したパスの中のファイル一覧
   */
  String[] getFilesInPath(String path) {
    try {
      final String[] files = this.assetManager.list(path);
      return files;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
    try {
      final InputStream input = this.assetManager.open(filePath);
      final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      
      String text = ""; //$NON-NLS-1$
      String string;
      while ((string = reader.readLine()) != null) {
        text += string + "\n"; //$NON-NLS-1$
      }
      
      return text;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
      final InputStream input = this.mainActivity.getAssets().open(parentPath + File.separator + filePath);
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
   * 拡張子を返します。
   * 
   * 拡張子がない場合、そのままファイル名を返します。
   * 
   * @param fileName ファイル名
   * @return 拡張子
   */
  private String getExtension(String fileName) {
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
   * 親フォルダーを返します。
   * 
   * @param path パス
   * @return 親フォルダー
   */
  String getParentFolder(String path) {
    final int point = path.lastIndexOf(File.separator);
   
    if (point != -1) {
      return path.substring(0, point);
    }

    return path;
  }

  /**
   * 指定された拡張子をもつファイルとフォルダーのリストを返します。
   * 
   * @param folderName フォルダー名 
   * @param files ファイルのリスト  
   * @param extensions 拡張子
   * 
   * @return 指定された拡張子をもつファイルとフォルダーのリスト
   */
  private List<String> selectFilesWithExtensionsOrFolders(String folderName, String[] files, String[] extensions) {
    final List<String> selectedFiles = new ArrayList<String>();

    for (final String file : files) {
      final String path = folderName + File.separator + file;
      if (isFolder(path)) {
        selectedFiles.add(file + File.separator);
      }
      
      for (final String extension : extensions) {
        if (getExtension(file).toLowerCase().equals(extension)) {
          selectedFiles.add(file);
        }         
      }
    }

    return selectedFiles;
  }
  
  /**
   * 指定したパスがフォルダーであるか判別します。
   * 
   * @param path パス
   * @return 指定したパスがフォルダーならばtrue
   */
  boolean isFolder(final String path) {
    final String[] filesInFolder = getFilesInPath(path);

    if (filesInFolder.length > 0) {
      return true;
    }
    
    return false;
  }

}
