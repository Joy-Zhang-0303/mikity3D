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
package org.mklab.mikity.android.editor;


/**
 * モデルを編集するエディタを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public interface ModelEditor {
  /**
   * パラメータを更新します。
   */
  void updateModelParameters();
  
  /**
   * エディタを更新し(オブジェクトの変化をエディタに反映させ)ます。
   */
  void updateEditor();
  
  /**
   * パラメータが変更されているか判定します。
   * 
   * @return パラメータが変更されていればtrue
   */
  boolean isChanged();
}
