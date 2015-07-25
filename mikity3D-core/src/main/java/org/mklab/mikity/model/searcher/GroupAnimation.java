/*
 * Created on 2015/01/29
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.searcher;


/**
 * Mikity3Dモデルのアニメーションデータを管理するクラスです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class GroupAnimation extends GroupManager {
  /** 番号。 */
  private int number;
  /** ターゲット名。 */
  private String targetName;

  /**
   * 新しく生成された<code>GroupLink</code>オブジェクトを初期化します。
   * @param number 列番号
   * @param target ターゲット名
   * @param parent 親
   */
  public GroupAnimation(int number, String target, GroupManager parent) {
  	super(parent);
    this.number = number;
    this.targetName = target;
  }
  
  /**
   * 番号を返します。
   * 
   * @return 番号
   */
  public int getNumber() {
    return this.number;
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
   * 番号を設定すします。
   * 
   * @param number 番号
   */
  public void setNumber(int number) {
  	this.number = number;
  }
  
}
