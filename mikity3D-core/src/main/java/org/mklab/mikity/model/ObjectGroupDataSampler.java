/*
 * Created on 2015/07/14
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import org.mklab.mikity.model.sampler.DataSampler;

/**
 * {@link ObjectGroup}と{@link DataSampler}の組を表すクラスです。
 * @author koga
 * @version $Revision$, 2015/07/14
 */
class ObjectGroupDataSampler {
  /** オブジェクトグループ。 */
  public ObjectGroup group;
  /** データピッカー。 */
  public DataSampler sampler;

  /**
   * 新しく生成された<code>ObjectGroupDataSampler</code>オブジェクトを初期化します。
   * @param group オブジェクトグループ
   * @param sampler データ抽出器
   */
  public ObjectGroupDataSampler(ObjectGroup group, DataSampler sampler) {
    this.group = group;
    this.sampler = sampler;
  }

}
