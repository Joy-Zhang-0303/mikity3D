/*
 * Created on 2015/07/14
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import org.mklab.mikity.model.picker.DataPicker;


/**
 * {@link ObjectGroup}と{@link DataPicker}の組を表すクラスです。
 * @author koga
 * @version $Revision$, 2015/07/14
 */
class ObjectGroupDataPicker {
  /** オブジェクトグループ。 */
  public ObjectGroup objectGroup;
  /** データピッカー。 */
  public DataPicker dataPicker;

  /**
   * 新しく生成された<code>ObjectGroupDataPicker</code>オブジェクトを初期化します。
   */
  /**
   * 新しく生成された<code>ObjectGroupDataPicker</code>オブジェクトを初期化します。
   * @param objectGroup オブジェクトグループ
   * @param dataPicker データピッカー
   */
  public ObjectGroupDataPicker(ObjectGroup objectGroup, DataPicker dataPicker) {
    this.objectGroup = objectGroup;
    this.dataPicker = dataPicker;
  }

}
