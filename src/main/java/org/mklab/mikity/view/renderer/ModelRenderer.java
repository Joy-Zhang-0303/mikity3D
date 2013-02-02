/*
 * Created on 2012/02/19
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.renderer;

import org.mklab.mikity.model.xml.JamastConfig;
import org.mklab.mikity.model.xml.model.Group;


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
  void setConfiguration(JamastConfig configuration);
}
