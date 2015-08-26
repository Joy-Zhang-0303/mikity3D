/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.widgets.Group;


/**
 * プリミティブを編集するダイアログを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public interface EditPrimitiveDialog extends EditModelDialog {
  /**
   * パラメータを設定するボックスを生成します。
   * 
   * @param group グループ
   */
  void createPrameterBoxes(Group group);
}
