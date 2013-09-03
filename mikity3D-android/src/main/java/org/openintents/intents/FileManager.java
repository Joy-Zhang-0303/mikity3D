package org.openintents.intents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.mklab.mikity.android.MainActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;

public class FileManager{

  private int  REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  private Activity activity;

  /**
   * 新しく生成された<code>FileManager</code>オブジェクトを初期化します。
   * OIファイルマネージャーが見つからないとトーストを表示するので表示するアクティビティのインスタンスを渡してください。
   * @param activity アクティビティのインスタンス。
   */
  public FileManager(Activity activity) {
    this.activity = activity;
  }

  /**
   * Opens the file manager to pick a file.
   */
  public void getFilePath() {

    Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

    try {
      activity.startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY );
    } catch (ActivityNotFoundException e) {
      // No compatible file manager was found.
      Toast.makeText(this.activity, "IOファイルマネージャーをインストールしてください。", Toast.LENGTH_SHORT).show();
    }

  }
  
  
	
}