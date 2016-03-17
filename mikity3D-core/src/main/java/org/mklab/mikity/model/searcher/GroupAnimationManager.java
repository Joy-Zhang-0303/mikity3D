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


/**
 * Mikity3Dモデルのアニメーションデータを管理するクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupAnimationManager extends GroupManager {
  /** 番号。 */
  private int number;
  /** ターゲット名。 */
  private String targetName;

  /**
   * 新しく生成された<code>GroupLink</code>オブジェクトを初期化します。
   * @param number 番号
   * @param target ターゲット名
   * @param parent 親
   */
  public GroupAnimationManager(int number, String target, GroupManager parent) {
  	super(parent);
    this.number = number;
    this.targetName = target;
  }
  
  /**
   * 番号を返します。
   * 
   * @return 番号
   */
  public int getNumber() {
    return this.number;
  }
  
  /**
   * ターゲット名を返します。
   * 
   * @return ターゲット名
   */
  public String getTarget() {
    return this.targetName;
  }
  
  /**
   * 番号を設定すします。
   * 
   * @param number 番号
   */
  public void setNumber(int number) {
  	this.number = number;
  }
  
}
