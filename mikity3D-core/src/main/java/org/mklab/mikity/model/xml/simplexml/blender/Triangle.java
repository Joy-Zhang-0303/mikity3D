/*
 * $Id: Triangle.java,v 1.2 2007/11/30 10:56:15 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Triangle要素) 頂点座標のうち、使用する頂点の番号を取得するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class Triangle {

  @Element
  private String p;

  /**
   * 文字列の空白の位置を記したリスト
   */
  private List<Integer> space = new ArrayList<Integer>();
  /**
   * 使用する頂点の番号を記したリスト
   */
  private List<Integer> num = new ArrayList<Integer>();
  /**
   * 使用する頂点の組み合わせをまとめた配列リスト
   */
  private List<int[]> index = new ArrayList<int[]>();

  /**
   * コンストラクタ
   */
  public Triangle() {
    this.space = new ArrayList<Integer>();
    this.num = new ArrayList<Integer>();
    this.index = new ArrayList<int[]>();
  }

  /**
   * 使用する頂点番号一覧を記述した文字列から使用しない値や空白を除外し、 前から3つずつ頂点の組み合わせをまとめる配列リストに加えていく。
   */
  @SuppressWarnings("boxing")
  private void createTriangleIndex() {
    getSpaceindexFromPToSpace(); //pからthis.spaceにスペースの位置を記録
    for (int i = 0; i < this.space.size() + 1; i += 2) {
      getNumberFromPToNum(i);
    }
    for (int i = 0; i < this.num.size(); i += 3) {
      int[] n = new int[3];
      n[0] = this.num.get(i);
      n[1] = this.num.get(i + 1);
      n[2] = this.num.get(i + 2);
      this.index.add(n);
    }
  }

  @SuppressWarnings("boxing")
  private void getNumberFromPToNum(int i) {
    if (i == 0) {
      this.num.add(Integer.parseInt(this.p.substring(0, this.space.get(0))));
    } else if (i == this.space.size()) {
      this.num.add(Integer.parseInt(this.p.substring(this.space.get(this.space.size() - 1) + 1)));
    } else {
      this.num.add(Integer.parseInt(this.p.substring(this.space.get(i - 1) + 1, this.space.get(i))));
    }
  }

  @SuppressWarnings("boxing")
  private void getSpaceindexFromPToSpace() {
    for (int j = 0; j < this.p.length(); j++) {
      if (this.p.charAt(j) == ' ') {
        this.space.add(j);
      }
    }
  }

  /**
   * 使用する頂点番号一覧を記述した文字列を返す
   * 
   * @return　p　使用する頂点番号一覧を記述した文字列
   */
  public String loadP() {
    return this.p;
  }

  /**
   * 使用する頂点の組み合わせをまとめた配列リストを返す
   * 
   * @return　index　使用する頂点の組み合わせをまとめた配列リスト
   */
  public List<int[]> getTriangleIndex() {
    createTriangleIndex();
    return this.index;
  }
}
