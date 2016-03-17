/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Polylist要素)です。
 * 
 * @author Iwamoto
 * @version $Revision: 1.2 $. 2011/10/12
 */
public class Polylist {

  @Element
  private String vcount;

  @Element
  private String p;

  private List<Integer> vcountValue = new ArrayList<>();
  
  /** 文字列の空白の位置を記したリスト */
  private List<Integer> spaces = new ArrayList<>();
  /** 使用する頂点の番号を記したリスト */
  private List<Integer> numbers = new ArrayList<>();
  /** 使用する頂点の組み合わせをまとめた配列リスト */
  private List<int[]> indices = new ArrayList<>();

  /**
   * 新しく生成された<code>Polylist</code>オブジェクトを初期化します。
   */
  public Polylist() {
    this.spaces = new ArrayList<>();
    this.numbers = new ArrayList<>();
    this.indices = new ArrayList<>();
    this.vcountValue = new ArrayList<>();
  }

  /**
   * 使用する頂点番号一覧を記述した文字列から使用しない値や空白を除外し、 前から3～4つずつ頂点の組み合わせをまとめる配列リストに加えます。
   */
  @SuppressWarnings("boxing")
  private void createPolylistIndex() {
    //要チェック
    getSpaceindex(this.p, this.spaces); //pからthis.spaceにスペースの位置を記録
    final List<Integer> vcountSpaceIndex = new ArrayList<>();
    getSpaceindex(this.vcount, vcountSpaceIndex);

    getOddIndexNumberFromP(this.numbers, this.spaces);
    getNumberFromVcount(this.vcountValue, vcountSpaceIndex);
    int i = 0;
    int j = 0;
    while (i < this.numbers.size()) {
      int n[] = new int[this.vcountValue.get(j)];
      for (int k = 0; k < n.length; k++) {
        n[k] = this.numbers.get(k + i);
      }
      i += n.length;
      j++;
      this.indices.add(n);
    }
  }

  private void getNumberFromVcount(List<Integer> values, List<Integer> countSpaceIndices) {
    for (int i = 0; i < countSpaceIndices.size() ; i++) {
      if (i == 0) {
        values.add(new Integer(Integer.parseInt(this.vcount.substring(0, countSpaceIndices.get(0).intValue()))));
      } /*else if (i == vCountSpaceIndex.size()) {
        vValue.add(new Integer(Integer.parseInt(this.vcount.substring(vCountSpaceIndex.get(vCountSpaceIndex.size() - 1).intValue() + 1))));
        //(translateSpace.get(translateSpace.size() - 1) + 1)
      } */else {
        values.add(new Integer(Integer.parseInt(this.vcount.substring(countSpaceIndices.get(i - 1).intValue() + 1, countSpaceIndices.get(i).intValue()))));
      }
    }
  }

  private void getOddIndexNumberFromP(List<Integer> numList, List<Integer> spaceList) {
    for (int i = 0; i < spaceList.size() + 1; i += 2) {
      if (i == 0) {
        numList.add(new Integer(Integer.parseInt(this.p.substring(0, spaceList.get(0).intValue()))));
      } else if (i == spaceList.size()) {
        numList.add(new Integer(Integer.parseInt(this.p.substring(spaceList.get(spaceList.size() - 1).intValue() + 1))));
      } else {
        numList.add(new Integer(Integer.parseInt(this.p.substring(spaceList.get(i - 1).intValue() + 1, spaceList.get(i).intValue()))));
      }
    }
  }

  @SuppressWarnings("static-method")
  private void getSpaceindex(String po, List<Integer> space2) {
    for (int j = 0; j < po.length(); j++) {
      if (po.charAt(j) == ' ') {
        space2.add(new Integer(j));
      }
    }
  }

  /**
   * 使用する頂点番号一覧を記述した文字列を返します。
   * 
   * @return　p　使用する頂点番号一覧を記述した文字列
   */
  public String getP() {
    return this.p;
  }
  
  /**
   * 使用する頂点の組み合わせをまとめた配列リストを返します。
   * 
   * @return　index　使用する頂点の組み合わせをまとめた配列リスト
   */
  public List<int[]> getPolylistIndices() {
    createPolylistIndex();
    return this.indices;
  }
}
