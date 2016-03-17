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

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * ソースデータを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/29
 */
@Root(name="source")
public class SourceDataModel implements Serializable, Cloneable {
  /** */
  private static final long serialVersionUID = 1L;

  /** ID。 */
  @Attribute(name="id", required=true)
  private String id;
  
  /** データのファイルパス。 */
  @Attribute(name="filepath", required=true)
  private String filePath;
  
  /**
   * 新しく生成された<code>SourceDataModel</code>オブジェクトを初期化します。
   */
  public SourceDataModel() {
    this.id = "1"; //$NON-NLS-1$
    this.filePath = null;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public SourceDataModel clone() {
    try {
      return (SourceDataModel)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.filePath == null) ? 0 : this.filePath.hashCode());
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SourceDataModel other = (SourceDataModel)obj;
    if (this.filePath == null) {
      if (other.filePath != null) return false;
    } else if (!this.filePath.equals(other.filePath)) return false;
    if (this.id == null) {
      if (other.id != null) return false;
    } else if (!this.id.equals(other.id)) return false;
    return true;
  }
  
  /**
   * IDを返します。
   * 
   * @return ID
   */
  public String getId() {
    return this.id;
  }
  
  /**
   * IDを設定します。
   * 
   * @param id ID
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * データファイルのパスを返します。
   * 
   * @return データファイルのパス
   */
  public String getFilePath() {
    return this.filePath;
  }
  
  /**
   * データファイルのパスを設定します。
   * 
   * @param filePath データファイルのパス
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

}
