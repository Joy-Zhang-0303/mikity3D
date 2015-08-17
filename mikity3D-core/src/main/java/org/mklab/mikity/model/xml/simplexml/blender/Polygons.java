/*
 * $Id: Polygons.java,v 1.2 2007/11/30 10:56:15 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Polygon要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class Polygons {

  @ElementList
  private List<String> p;

  /** 使用する頂点の組み合わせをまとめた配列リスト */
  private List<int[]> vertices;
  /** 文字列の空白の位置を記したリスト */
  private List<int[]> spaceIndices;
  /** 使用する頂点の番号を記したリスト  */
  //private List<int[]> vertexNumbers;

  /**
   * 新しく生成された<code>Polygons</code>オブジェクトを初期化します。
   */
  public Polygons() {
    this.p = new ArrayList<>();
    this.vertices = new ArrayList<>();
    this.spaceIndices = new ArrayList<>();
    //this.vertexNumbers = new ArrayList<>();
  }

  /**
   * 使用する頂点の組み合わせをまとめた配列リストを返します。
   * 
   * @return　indexNumber　使用する頂点の組み合わせをまとめた配列リスト
   */
  public List<int[]> getIndexNumber() {
    setIndex();
    return this.vertices;
  }

  /**
   * 頂点座標一覧を記述した文字列から空白を除外し、 前から3つずつ頂点の組み合わせをまとめる配列リストに加えます。
   */
  @SuppressWarnings("boxing")
  private void setIndex() {
    for (int i = 0; i < this.p.size(); i++) {
      final List<Integer> spaces = new ArrayList<>();
      final String pi = this.p.get(i);
      
      for (int j = 0; j < pi.length(); j++) {
        if (pi.charAt(j) == ' ') {
          spaces.add(j);
        }
      }
      final int[] spaceNumbers = new int[spaces.size()];
      for (int j = 0; j < spaceNumbers.length; j++) {
        spaceNumbers[j] = spaces.get(j);
      }
      this.spaceIndices.add(spaceNumbers);

      final List<Integer> numbers = new ArrayList<>();
      int[] index = this.spaceIndices.get(i);
      
      for (int j = 0; j < index.length + 1; j += 2) {
        if (j == 0) {
          numbers.add(Integer.parseInt(pi.substring(0, index[0])));
        } else if (j == index.length) {
          numbers.add(Integer.parseInt(pi.substring(index[index.length - 1] + 1)));
        } else {
          numbers.add(Integer.parseInt(pi.substring(index[j - 1] + 1, index[j])));
        }
      }

      int[] n = new int[numbers.size()];
      for (int j = 0; j < numbers.size(); j++) {
        n[j] = numbers.get(j);
      }
      this.vertices.add(n);
    }
  }
}
