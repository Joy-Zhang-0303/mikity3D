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
package org.mklab.mikity.model.searcher;

import java.util.List;


/**
 * Mikity3Dモデルのグループとアニメーションを管理するためのクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupManager {
  /** 親。 */
  private GroupManager parent;

  /**
   * 新しく生成された<code>GroupManager</code>オブジェクトを初期化します。
   * 
   * @param parent 親GroupManager(GroupName)
   */
  public GroupManager(GroupManager parent) {
    this.parent = parent;
  }

  /**
   * 親を登録します。
   */
  public void registerParent() {
    throw new UnsupportedOperationException();
  }

  /**
   * 要素を追加します。
   * 
   * @param item 要素
   */
  public void addItems(GroupManager item) {
    throw new UnsupportedOperationException(item.toString());
  }

  /**
   * Null要素を削除します。
   */
  public void removeNullElements() {
    throw new UnsupportedOperationException();
  }

  /**
   * 
   */
  public void trimToSizeArray() {
    throw new UnsupportedOperationException();
  }

  /**
   * 要素を返します。
   * 
   * @return 要素
   */
  public List<GroupManager> getItems() {
    throw new UnsupportedOperationException();
  }

  /**
   * 親を返します。
   * 
   * @return parent 親
   */
  public GroupManager getParent() {
    return this.parent;
  }

}