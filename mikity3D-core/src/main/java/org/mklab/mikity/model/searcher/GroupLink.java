/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;


/**
 * Mikity3Dモデルのリンクデータを管理するクラスです。
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupLink extends GroupManager {

  private int columnNumber;
  private String targetName;
  
  /**
   * 新しく生成された<code>GroupLink</code>オブジェクトを初期化します。
   * @param column
   * @param target
   * @param parent
   */
  /**
   * 新しく生成された<code>GroupLink</code>オブジェクトを初期化します。
   * @param column コラムナンバー
   * @param target ターゲット名
   * @param parent 親
   */
  public GroupLink(int column, String target, GroupManager parent) {
  	super(parent);
    this.columnNumber = column;
    this.targetName = target;
  }
  
  /**
   * コラムナンバーを取得するためのメソッドです。
   * @return columnNumber コラムナンバー
   */
  public int getColumn() {
    return this.columnNumber;
  }
  
  /**
   * ターゲット名を取得するためのメソッドです。
   * @return ターゲット名
   */
  public String getTarget() {
    return this.targetName;
  }
  
  /**
   * コラムナンバーを設定するためのメソッドです。
   * @param columnNumber コラムナンバー
   */
  public void setColumnNumber(int columnNumber) {
  	this.columnNumber = columnNumber;
  }
  
}
