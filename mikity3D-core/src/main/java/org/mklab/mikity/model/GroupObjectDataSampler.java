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

import org.mklab.mikity.model.sampler.DataSampler;

/**
 * {@link GroupObject}と{@link DataSampler}の組を表すクラスです。
 * @author koga
 * @version $Revision$, 2015/07/14
 */
class GroupObjectDataSampler {
  /** グループオブジェクト。 */
  public GroupObject groupObject;
  /** データピッカー。 */
  public DataSampler sampler;

  /**
   * 新しく生成された<code>GroupObjectDataSampler</code>オブジェクトを初期化します。
   * @param groupObject オブジェクトグループ
   * @param sampler データ抽出器
   */
  public GroupObjectDataSampler(GroupObject groupObject, DataSampler sampler) {
    this.groupObject = groupObject;
    this.sampler = sampler;
  }

}
