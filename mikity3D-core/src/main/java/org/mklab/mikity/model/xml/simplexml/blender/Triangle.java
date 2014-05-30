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

  /** 文字列の空白の位置を記したリスト */
  private List<Integer> spaces = new ArrayList<>();
  /** 使用する頂点の番号を記したリスト */
  private List<Integer> numbers = new ArrayList<>();
  /** 使用する頂点の組み合わせをまとめた配列リスト */
  private List<int[]> indices = new ArrayList<>();

  /**
   * コンストラクタ
   */
  public Triangle() {
    this.spaces = new ArrayList<>();
    this.numbers = new ArrayList<>();
    this.indices = new ArrayList<>();
  }

  /**
   * 使用する頂点番号一覧を記述した文字列から使用しない値や空白を除外し、 前から3つずつ頂点の組み合わせをまとめる配列リストに加えます。
   */
  @SuppressWarnings("boxing")
  private void createTriangleIndex() {
    getSpaceindexFromPToSpace(); //pからthis.spaceにスペースの位置を記録
    for (int i = 0; i < this.spaces.size() + 1; i += 2) {
      getNumberFromPToNum(i);
    }
    for (int i = 0; i < this.numbers.size(); i += 3) {
      int[] n = new int[3];
      n[0] = this.numbers.get(i);
      n[1] = this.numbers.get(i + 1);
      n[2] = this.numbers.get(i + 2);
      this.indices.add(n);
    }
  }

  @SuppressWarnings("boxing")
  private void getNumberFromPToNum(int i) {
    if (i == 0) {
      this.numbers.add(Integer.parseInt(this.p.substring(0, this.spaces.get(0))));
    } else if (i == this.spaces.size()) {
      this.numbers.add(Integer.parseInt(this.p.substring(this.spaces.get(this.spaces.size() - 1) + 1)));
    } else {
      this.numbers.add(Integer.parseInt(this.p.substring(this.spaces.get(i - 1) + 1, this.spaces.get(i))));
    }
  }

  @SuppressWarnings("boxing")
  private void getSpaceindexFromPToSpace() {
    for (int i = 0; i < this.p.length(); i++) {
      if (this.p.charAt(i) == ' ') {
        this.spaces.add(i);
      }
    }
  }

  /**
   * 使用する頂点番号一覧を記述した文字列を返します。
   * 
   * @return　使用する頂点番号一覧を記述した文字列
   */
  public String getP() {
    return this.p;
  }

  /**
   * 使用する頂点の組み合わせをまとめた配列リストを返します。
   * 
   * @return　使用する頂点の組み合わせをまとめた配列リスト
   */
  public List<int[]> getTriangleIndices() {
    createTriangleIndex();
    return this.indices;
  }
}
