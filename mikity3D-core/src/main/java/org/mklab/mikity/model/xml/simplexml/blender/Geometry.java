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
package org.mklab.mikity.model.xml.simplexml.blender;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Geometry要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class Geometry {

  @Attribute
  private String name;
  @Attribute
  private String id;
  @Element
  private Mesh mesh;

  /**
   * 新しく生成された<code>Geometry</code>オブジェクトを初期化します。
   */
  public Geometry() {
    this.mesh = new Mesh();
  }

  /**
   * Blenderデータから作成したポリゴンをまとめたグループを返します。
   * 
   * @return　Blenderデータからのポリゴンをまとめたグループ
   */
  public GroupModel getBlenderPolygonGroup() {
    final GroupModel group = this.mesh.getBlenderPolygonGroup();
    if (this.name.indexOf("-G") != -1) { //$NON-NLS-1$
      this.name = this.name.substring(0, this.name.indexOf("-G")); //$NON-NLS-1$
    }
    
    if (this.name.indexOf("_001") != -1) { //$NON-NLS-1$
      this.name = this.name.substring(0, this.name.indexOf("_001")); //$NON-NLS-1$
    }
    group.setName(this.name);
    return group;
  }

  /**
   * @param libraryVisualScenes ノード
   */
  public void setLibraryVisualScenes(LibraryVisualScenes libraryVisualScenes) {
    if(this.name == null){
      this.name = this.id;
    }
    if (this.name.indexOf("-G") != -1) { //$NON-NLS-1$
      this.name = this.name.substring(0, this.name.indexOf("-G")); //$NON-NLS-1$
    }
    if (this.name.indexOf("-m") != -1){ //$NON-NLS-1$
      this.name = this.name.substring(0, this.name.indexOf("-m")); //$NON-NLS-1$
    }
    
    if (this.name.indexOf("_00") != -1) { //$NON-NLS-1$
      this.name = this.name.substring(0, this.name.indexOf("_00")); //$NON-NLS-1$
    }
    this.mesh.setLibraryVisualScenes(libraryVisualScenes, this.name);
  }

}
