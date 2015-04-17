/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;


/**
 * Mikity3Dモデルのリンクデータを管理するクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupLink extends GroupManager {
  /** 列番号。 */
  private int columnNumber;
  /** ターゲット名。 */
  private String targetName;

  /**
   * 新しく生成された<code>GroupLink</code>オブジェクトを初期化します。
   * @param columnNumber 列番号
   * @param target ターゲット名
   * @param parent 親
   */
  public GroupLink(int columnNumber, String target, GroupManager parent) {
  	super(parent);
    this.columnNumber = columnNumber;
    this.targetName = target;
  }
  
  /**
   * 列番号を返します。
   * 
   * @return columnNumber 列番号
   */
  public int getColumn() {
    return this.columnNumber;
  }
  
  /**
   * ターゲット名を返します。
   * 
   * @return ターゲット名
   */
  public String getTarget() {
    return this.targetName;
  }
  
  /**
   * 列番号を設定すします。
   * 
   * @param columnNumber コラムナンバー
   */
  public void setColumnNumber(int columnNumber) {
  	this.columnNumber = columnNumber;
  }
  
}
