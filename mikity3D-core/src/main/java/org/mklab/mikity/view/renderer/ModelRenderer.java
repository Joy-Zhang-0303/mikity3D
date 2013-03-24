/*
 * Created on 2012/02/19
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer;

import org.mklab.mikity.model.xml.simplexml.Mikity3dConfig;
import org.mklab.mikity.model.xml.simplexml.model.Group;

/**
 * モデルを描画するレンダラーを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2012/02/19
 */
public interface ModelRenderer {
  /**
   * 描画対象となるグループ群を設定します。
   * 
   * @param children グループ群 
   */
  void setChildren(Group[] children);
  
  /**
   * 環境データを設定します。
   * 
   * @param configuration 環境データ
   */
  void setConfiguration(Mikity3dConfig configuration);
  
  /**
   * 画面の更新のためにdisplayメソッドの呼び出しを必要とするか判定します。
   * @return 画面の更新のためにdisplayメソッドの呼び出しを必要と場合，true
   */
  boolean isRequiredToCallDisplay();
  
  /**
   * 画面を更新します。
   */
  void updateDisplay();
}
