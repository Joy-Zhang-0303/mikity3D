/*
 * $Id: AdjustLocation.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.collision;

import org.mklab.mikity.xml.model.Location;


/**
 * プリミティブが重複すると判断された場合、追加しようとしているプリミティブの位置座標を変更するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2006/07/26
 */
public class AdjustLocation {

  private CollisionCanceller cc;

  private Location newLoc;

  private float locX;
  private float locY;
  private float locZ;

  /**
   * コンストラクタ
   * 
   * @param cc
   */
  public AdjustLocation(CollisionCanceller cc) {
    this.cc = cc;
    this.newLoc = new Location();
  }

  /**
   * 二つのプリミティブが重ならないように位置座標を調整する
   * 
   * @param range 　範囲
   * @param primLoc 　プリミティブの座標
   * @param listRange 　リスト内の範囲
   * @param listLoc 　リスト内の座標
   * @param distance 　二つのプリミティブの距離
   */
  public void adjustLoc(float range, Location primLoc, float listRange, Location listLoc, float distance) {
    calculateLoc(range, primLoc, listRange, listLoc, distance);
    this.newLoc.setX(this.locX);
    this.newLoc.setY(this.locY);
    this.newLoc.setZ(this.locZ);
  }

  /**
   * 新規位置座標を返す
   * 
   * @return　newLoc　新規位置座標
   */
  public Location getNewLocation() {
    return this.newLoc;
  }

  /**
   * 二つのプリミティブが重ならないように位置座標を計算する
   * 
   * @param range 　範囲
   * @param primLoc 　プリミティブの座標
   * @param listRange 　リスト内の範囲
   * @param listLoc 　リスト内の座標
   * @param distance 　二つのプリミティブの距離
   */
  private void calculateLoc(float range, Location primLoc, float listRange, Location listLoc, float distance) {

    float rate = (range + listRange) * 1.25f / distance;

    float x1 = primLoc.loadX();
    float y1 = primLoc.loadY();
    float z1 = primLoc.loadZ();

    float x2 = listLoc.loadX();
    float y2 = listLoc.loadY();
    float z2 = listLoc.loadZ();

    // locX = (1.0f+rate)*x1-x2;
    // locY = (1.0f+rate)*y1-y2;
    // locZ = (1.0f+rate)*z1-z2;
    this.locX = (range + listRange) * 1.25f + x2;
    this.locY = (range + listRange) * 1.25f + y2;
    this.locZ = (range + listRange) * 1.25f + z2;
  }
}
