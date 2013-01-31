/*
 * $Id: Polygons.java,v 1.2 2007/11/30 10:56:15 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.blender;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Polygon要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class Polygons {

  @XmlElement
  private ArrayList<String> p;

  /**
   * 使用する頂点の組み合わせをまとめた配列リスト
   */
  private ArrayList<int[]> indexNumber;
  /**
   * 文字列の空白の位置を記したリスト
   */
  ArrayList<int[]> spaceList;
  /**
   * 使用する頂点の番号を記したリスト
   */
  ArrayList<int[]> numList;

  /**
   * コンストラクタ
   */
  public Polygons() {
    this.p = new ArrayList<String>();
    this.indexNumber = new ArrayList<int[]>();
    this.spaceList = new ArrayList<int[]>();
    this.numList = new ArrayList<int[]>();
  }

  /**
   * 使用する頂点の組み合わせをまとめた配列リストを返す
   * 
   * @return　indexNumber　使用する頂点の組み合わせをまとめた配列リスト
   */
  public ArrayList<int[]> getIndexNumber() {
    setIndex();
    return this.indexNumber;
  }

  /**
   * 頂点座標一覧を記述した文字列から空白を除外し、 前から3つずつ頂点の組み合わせをまとめる配列リストに加えていく。
   */
  @SuppressWarnings("boxing")
  private void setIndex() {
    for (int i = 0; i < this.p.size(); i++) {
      ArrayList<Integer> space = new ArrayList<Integer>();
      for (int j = 0; j < this.p.get(i).length(); j++) {
        if (this.p.get(i).charAt(j) == ' ') {
          space.add(j);
        }
      }
      int[] spaceNum = new int[space.size()];
      for (int k = 0; k < spaceNum.length; k++) {
        spaceNum[k] = space.get(k);
      }
      this.spaceList.add(spaceNum);

      ArrayList<Integer> num = new ArrayList<Integer>();
      for (int j = 0; j < this.spaceList.get(i).length + 1; j += 2) {
        if (j == 0) {
          num.add(Integer.parseInt(this.p.get(i).substring(0, this.spaceList.get(i)[0])));
        } else if (j == this.spaceList.get(i).length) {
          num.add(Integer.parseInt(this.p.get(i).substring(this.spaceList.get(i)[this.spaceList.get(i).length - 1] + 1)));
        } else {
          num.add(Integer.parseInt(this.p.get(i).substring(this.spaceList.get(i)[j - 1] + 1, this.spaceList.get(i)[j])));
        }
      }

      int[] n = new int[num.size()];
      for (int l = 0; l < num.size(); l++) {
        n[l] = num.get(l);
      }
      this.indexNumber.add(n);
    }
  }
}
