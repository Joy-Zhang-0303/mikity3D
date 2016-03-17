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
package org.mklab.mikity.model;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;


/**
 * グループオブジェクトを表すインターフェイスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/14
 */
public interface GroupObject {

  /**
   * 名前を設定します。
   * 
   * @param name 名前
   */
  void setName(String name);

  /**
   * グループの座標を設定します。
   * 
   * @param coordinate 座標
   */
  void setCoordinate(Coordinate coordinate);

  /**
   * 関連するオブジェクトのグループを返します。
   * 
   * @return 関連するオブジェクトのグループ
   */
  GroupModel getGroup();
}
