/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;


/**
 * プリミティブを編集するエディタを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public interface PrimitiveEditor extends ModelEditor {
  /**
   * パラメータを設定するボックスを生成します。
   * 
   * @param group グループ
   */
  void createParameterBoxes(Group group);
  
//  /**
//   * サイズを設定します。 
//   * 
//   * @param composite コンポジット
//   */
//  void setCompositeSize(Composite composite);
}
