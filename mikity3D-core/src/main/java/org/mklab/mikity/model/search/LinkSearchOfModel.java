/*
 * Created on 2015/01/28
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.search;


/**
 * リンクデータ検索のためのクラスです。
 * @author soda
 * @version $Revision$, 2015/01/28
 */
@SuppressWarnings("serial")
public class LinkSearchOfModel extends SearchOfModel {
  
  private String targetName;
  private int columnNumber;
  
  /**
   * コラムナンバーを返します。
   * @return columnNumber
   */
  public int getColumnNumber() {
    return this.columnNumber;
  }
  
  /**
   * ターゲット名を返します。
   * @return targetName
   */
  public String getTartetName() {
    return this.targetName;
  }
  
  /**
   * コラムナンバーを設定します。
   * @param column コラムナンバー
   */
  public void setColumnNumber(int column) {
    this.columnNumber = column;
  }
  
  /**
   * ターゲット名を設定します。
   * @param target ターゲット名
   */
  public void setTargetName(String target) {
    this.targetName = target;
  }
}
