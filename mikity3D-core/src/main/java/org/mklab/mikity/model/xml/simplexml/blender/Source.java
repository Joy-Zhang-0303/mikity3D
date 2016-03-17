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

import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Source要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class Source {

  @Element
  private String floatArray;

  /** 文字列の空白の位置を記したリスト  */
  private List<Integer> spaces;
  /** 座標の値を記したリスト */
  private List<Float> locations;
  /** 頂点座標をまとめたリスト  */
  private List<VertexModel> vertexLocations;
  /** 法線ベクトルをまとめたリスト  */
  private List<VertexModel> normalVectors;

  /**
   * コンストラクタ
   */
  public Source() {
    this.spaces = new ArrayList<>();
    this.locations = new ArrayList<>();
    this.vertexLocations = new ArrayList<>();
    this.normalVectors = new ArrayList<>();
  }

  /**
   * 頂点座標をまとめたリストを返します。
   * 
   * @return　頂点座標をまとめたリスト
   */
  public List<VertexModel> getVertexLocation() {
    setVertexLocations();
    return this.vertexLocations;
  }

  /**
   * 法線ベクトルをまとめたリストを返します。
   * 
   * @return　法線ベクトルをまとめたリスト
   */
  public List<VertexModel> getNormalLocation() {
    setNormalLocations();
    return this.normalVectors;
  }

  /**
   * 頂点座標一覧を記述した文字列から空白を除外し、 前から3つずつ頂点座標からLocationオブジェクトを作成してリストに加えます。
   */
  @SuppressWarnings("boxing")
  private void setVertexLocations() {
    divideString();

    for (int i = 0; i < this.locations.size(); i += 3) {
      this.vertexLocations.add(new VertexModel(this.locations.get(i), this.locations.get(i + 1), this.locations.get(i + 2)));
    }

  }

  /**
   * 頂法線ベクトル一覧を記述した文字列から空白を除外し、 前から3つずつ法線ベクトルからLocationオブジェクトを作成してリストに加えます。
   */
  @SuppressWarnings("boxing")
  private void setNormalLocations() {
    divideString();

    for (int i = 0; i < this.locations.size(); i += 3) {
      this.normalVectors.add(new VertexModel(this.locations.get(i), this.locations.get(i + 1), this.locations.get(i + 2)));
    }
  }

  /**
   * 数値の文字列をスペースの場所で分割し、数値として格納します。
   */
  @SuppressWarnings("boxing")
  private void divideString() {
    for (int i = 0; i < this.floatArray.length(); i++) {
      if (this.floatArray.charAt(i) == ' ') {
        this.spaces.add(i);
      }
    }

    for (int i = 0; i < this.spaces.size() + 1; i++) {
      if (i == 0) {
        this.locations.add(Float.parseFloat(this.floatArray.substring(0, this.spaces.get(0))));
      } else if (i == this.spaces.size()) {
        this.locations.add(Float.parseFloat(this.floatArray.substring(this.spaces.get(this.spaces.size() - 1) + 1)));
      } else {
        this.locations.add(Float.parseFloat(this.floatArray.substring(this.spaces.get(i - 1) + 1, this.spaces.get(i))));
      }
    }
  }
}
