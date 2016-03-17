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
package org.mklab.mikity.renderer;

import java.util.List;

import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

/**
 * オブジェクトを描画するレンダラーを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2012/02/19
 */
public interface ObjectRenderer {
  /**
   * 描画対象となるルートグループ群を設定します。
   * 
   * @param rootGroups ルートグループ群 
   * @param manager オブジェクトグループマネージャ
   */
  void setRootGroups(List<GroupModel> rootGroups, GroupObjectManager manager);
  
  /**
   * 環境データを設定します。
   * 
   * @param configuration 環境データ
   */
  void setConfiguration(ConfigurationModel configuration);
  
  /**
   * 設定を返します。
   * @return 設定
   */
  ConfigurationModel getConfiguration();
  
  /**
   * 画面の更新のためにdisplayメソッドの呼び出しを必要とするか判定します。
   * @return 画面の更新のためにdisplayメソッドの呼び出しを必要と場合，true
   */
  boolean isRequiredToCallDisplay();
  
  /**
   * 画面を更新します。
   */
  void updateDisplay();
}
