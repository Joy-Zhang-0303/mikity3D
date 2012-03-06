/*
 * $Id: PrimitiveQueue.java,v 1.2 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.collision;

import org.mklab.mikity.xml.model.Location;


/**
 * 過去に追加したプリミティブに関するデータをまとめたクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2008/02/29
 */
public class PrimitiveQueue {

  /**
   * これまで追加したプリミティブの存在範囲のキュー
   */
  private float rangeArray[] = new float[10];
  /**
   * これまで追加したプリミティブの位置座標のキュー
   */
  private Location locArray[] = new Location[10];
  /**
   * これまで追加したプリミティブの総数
   */
  private int count = 0;

  /**
   * 新しく生成された<code>PrimitiveQueue</code>オブジェクトを初期化します。
   */
  public PrimitiveQueue() {
    for (int i = 0; i < this.locArray.length; i++) {
      this.locArray[i] = new Location();
    }
  }

  /**
   * プリミティブの位置座標,存在範囲をキューに追加する。
   * 
   * @param loc 　プリミティブの位置座標
   * @param range プリミティブの存在範囲
   */
  public void enqueue(Location loc, float range) {
    while (this.locArray[this.count] != null && this.count < this.locArray.length + 1) {
      this.count++;
    }

    if (this.count == 0) {
      this.locArray[0] = loc;
      this.rangeArray[0] = range;
    } else {
      for (int i = 0; i < this.count + 1; i++) {
        this.locArray[i] = this.locArray[i + 1];
        this.locArray[this.count] = loc;

        this.rangeArray[i] = this.rangeArray[i + 1];
        this.rangeArray[this.count] = range;
      }
    }
  }

  /**
   * 現時点のプリミティブの存在範囲のリストを返す
   * 
   * @return　rangeArray　現時点のプリミティブ
   */
  public float[] getRangeArray() {
    return this.rangeArray;
  }

  /**
   * 現時点のプリミティブの位置座標のリストを返す
   * 
   * @return　locArray　現時点のプリミティブの位置座標
   */
  public Location[] getLocationArray() {
    return this.locArray;
  }

  /**
   * 現時点のプリミティブの総数を返す
   * 
   * @return　count　現時点のプリミティブの総数
   */
  public int getCount() {
    return this.count;
  }
}
