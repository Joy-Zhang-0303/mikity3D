package org.openintents.intents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.widget.Toast;


/**
 * @author koga
 * @version $Revision$, 2014/05/30
 */
public class OIFileManager {

  private int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  private Activity activity;

  /**
   * 新しく生成された<code>FileManager</code>オブジェクトを初期化します。 OIファイルマネージャーが見つからないとトーストを表示するので表示するアクティビティのインスタンスを渡してください。
   * 
   * @param activity アクティビティのインスタンス。
   */
  public OIFileManager(Activity activity) {
    this.activity = activity;
  }

  /**
   * Opens the file manager to pick a file.
   */
  public void getFilePath() {
    Intent intent = new Intent(OIFileManagerIntents.ACTION_PICK_FILE);
    try {
      this.activity.startActivityForResult(intent, this.REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
    } catch (ActivityNotFoundException e) {
      // No compatible file manager was found.
      Toast.makeText(this.activity, "IOファイルマネージャーをインストールしてください。", Toast.LENGTH_SHORT).show();
    }

  }

}