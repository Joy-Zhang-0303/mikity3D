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
package org.mklab.mikity.android.openintents.intents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.widget.Toast;


/**
 * OIFileマネージャへのIntentを管理するクラスです。
 * 
 * @author koga
 * @version $Revision$, 2014/05/30
 */
public class OIFileManager {

  private int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 0;
  private Activity activity;

  /**
   * 新しく生成された<code>FileManager</code>オブジェクトを初期化します。 
   * 
   * OIファイルマネージャーが見つからないとトーストを表示するので表示するアクティビティのインスタンスを渡してください。
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
      Toast.makeText(this.activity, "IOファイルマネージャーをインストールしてください。", Toast.LENGTH_SHORT).show(); //$NON-NLS-1$
    }

  }

}