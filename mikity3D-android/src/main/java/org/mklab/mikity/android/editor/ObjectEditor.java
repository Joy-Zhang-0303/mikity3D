/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.editor;

import android.widget.LinearLayout;


/**
 * オブジェクトを編集するエディタを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public interface ObjectEditor extends ModelEditor {
  /**
   * パラメータを設定するボックスを生成します。
   * 
   * @param parameters パラメータ群
   */
  void createParameterBoxes(LinearLayout parameters);
}
