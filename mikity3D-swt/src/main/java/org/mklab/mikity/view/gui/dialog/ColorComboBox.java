/*
 * $Id: ColorComboBox.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;


/**
 * カラーコンボボックス作成クラス
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class ColorComboBox {

  /** カラーコンボボックス  */
  private Combo colorCombo;
  /** 変化後のプリミティブ */
  private Group afterGroup;
  /** 色リスト  */
  private String[] colors;

  /**
   * コンストラクタ
   * 
   * @param afterGroup 　変化後のプリミティブ
   * @param colors 　色リスト
   */
  public ColorComboBox(Group afterGroup, String[] colors) {
    this.afterGroup = afterGroup;
    this.colors = colors;
  }

  /**
   * カラーコンボボックスを生成する
   */
  public void createColorCombo() {
    this.colorCombo = new Combo(this.afterGroup, SWT.READ_ONLY);
    final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
    this.colorCombo.setLayoutData(gridData);
    this.colorCombo.setItems(this.colors);
  }

  /**
   * カラーコンボボックスを返す
   * 
   * @return　colorCombo　カラーコンボボックス
   */
  public Combo getColorComboBox() {
    return this.colorCombo;
  }
}
