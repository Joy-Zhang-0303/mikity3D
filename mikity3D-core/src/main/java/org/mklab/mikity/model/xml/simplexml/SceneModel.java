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
package org.mklab.mikity.model.xml.simplexml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * シーンを表すクラスです。
 * 
 * @version $Revision: 1.15 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="scene")
public class SceneModel implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;
  /** groups */
  @ElementList(type=GroupModel.class, inline=true)
  private List<GroupModel> groups;

  /**
   * 新しく生成された<code>SceneModel</code>オブジェクトを初期化します。
   */
  public SceneModel() {
    this.groups = new ArrayList<>();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public SceneModel clone() {
    try {
      final SceneModel ans = (SceneModel)super.clone();
      ans.groups = new ArrayList<>();
      for (final GroupModel group : this.groups) {
        ans.groups.add(group.clone());
      }
      return ans;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * グループを追加します。
   * 
   * @param argGroup グループ
   */
  public void addGroup(GroupModel argGroup) {
    this.groups.add(argGroup);
  }

  /**
   * 指定されたグループを返します。
   * 
   * @param index グループのインデックス
   * @return (org.mklab.mikity.xml.Group) _groupList.get(index)
   */
  public GroupModel getGroup(int index) {
    if ((index < 0) || (index > this.groups.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.groups.get(index);
  }
  
  /**
   * グループを削除します。
   * 
   * @param argGroup グループ
   */
  public void removeGroup(GroupModel argGroup) {
    this.groups.remove(argGroup);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.groups == null) ? 0 : this.groups.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SceneModel other = (SceneModel)obj;
    if (this.groups == null) {
      if (other.groups != null) {
        return false;
      }
    } else if (!this.groups.equals(other.groups)) {
      return false;
    }
    return true;
  }

  /**
   * Method getGroup
   * 
   * @return mArray
   */
  public List<GroupModel> getGroups() {
    return this.groups;
  } 
}
