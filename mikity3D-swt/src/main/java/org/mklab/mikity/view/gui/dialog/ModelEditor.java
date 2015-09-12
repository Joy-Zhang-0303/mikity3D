/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;


/**
 * モデルを編集するエディタを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public interface ModelEditor {
  /**
   * パラメータを更新します。
   */
  void updateModelParameters();
  
  /**
   * パラメータが変更されているか判定します。
   * 
   * @return パラメータが変更されていればtrue
   */
  boolean isChanged();
}
