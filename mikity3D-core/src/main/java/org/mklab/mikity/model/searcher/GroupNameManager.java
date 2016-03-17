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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Mikity3Dモデルのグループ名を管理するためのクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupNameManager extends GroupManager {
  /** 要素。 */
  private List<GroupManager> items = new ArrayList<>();
  /** グループ名。 */
  private String groupName;
  
  /**
   * 新しく生成された<code>GroupName</code>オブジェクトを初期化します。
   * @param name グループ名
   * @param parent 親
   */
  public GroupNameManager(String name, GroupManager parent) {
  	super(parent);
    this.groupName = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addItems(GroupManager item) {
    this.items.add(item);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeNullElements() {
    this.items.removeAll(Collections.singleton(null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trimToSizeArray() {
    ((ArrayList<GroupManager>)this.items).trimToSize();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<GroupManager> getItems() {
    return this.items;
  }
  
  /**
   * グループ名を返します。
   * 
   * @return groupName グループ名
   */
  public String getGroupName() {
    return this.groupName;
  }
}
