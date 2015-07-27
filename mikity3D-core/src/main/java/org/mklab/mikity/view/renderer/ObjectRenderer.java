/*
 * Created on 2012/02/19
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer;

import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

/**
 * オブジェクトを描画するレンダラーを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2012/02/19
 */
public interface ObjectRenderer {
  /**
   * 描画対象となるルートグループ群を設定します。
   * 
   * @param rootGroups ルートグループ群 
   */
  void setRootGroups(GroupModel[] rootGroups);
  
  /**
   * 環境データを設定します。
   * 
   * @param configuration 環境データ
   */
  void setConfiguration(ConfigurationModel configuration);
  
  /**
   * 設定を返します。
   * @return 設定
   */
  ConfigurationModel getConfiguration();
  
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
