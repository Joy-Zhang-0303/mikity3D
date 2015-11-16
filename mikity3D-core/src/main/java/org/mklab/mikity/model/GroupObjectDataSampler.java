/*
 * Created on 2015/07/14
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
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
