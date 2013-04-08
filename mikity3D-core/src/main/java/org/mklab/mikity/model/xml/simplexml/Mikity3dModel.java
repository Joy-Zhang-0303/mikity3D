package org.mklab.mikity.model.xml.simplexml;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * モデルを表すクラスです。
 * 
 * @version $Revision: 1.15 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="model")
public class Mikity3dModel implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  /** groups */
  @ElementList(type=Group.class, inline=true)
  private List<Group> groups;

  /**
   * 新しく生成された<code>Mikity3dModel</code>オブジェクトを初期化します。
   */
  public Mikity3dModel() {
    this.groups = new ArrayList<Group>();
  }

  /**
   * グループを追加します。
   * 
   * @param argGroup グループ
   */
  public void addGroup(Group argGroup) {
    this.groups.add(argGroup);
  }

  /**
   * 指定されたグループを返します。
   * 
   * @param index グループのインデックス
   * @return (org.mklab.mikity.xml.Group) _groupList.get(index)
   */
  public Group getGroup(int index) {
    if ((index < 0) || (index > this.groups.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.groups.get(index);
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
    Mikity3dModel other = (Mikity3dModel)obj;
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
  public Group[] getGroups() {
    final int size = this.groups.size();
    final Group[] localGroups = new Group[size];
    for (int i = 0; i < size; i++) {
      localGroups[i] = this.groups.get(i);
    }
    return localGroups;
  } 
}
