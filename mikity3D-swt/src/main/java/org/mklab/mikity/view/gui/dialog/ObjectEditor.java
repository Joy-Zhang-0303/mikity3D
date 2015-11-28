/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.widgets.Group;


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
   * @param group グループ
   */
  void createParameterBoxes(Group group);
}
