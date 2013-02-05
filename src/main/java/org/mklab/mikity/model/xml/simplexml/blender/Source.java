/*
 * $Id: Source.java,v 1.4 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;

import org.mklab.mikity.model.xml.simplexml.model.Location;
import org.simpleframework.xml.Element;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Source要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/11/30
 */
public class Source {

  @Element
  private String float_array;

  /**
   * 文字列の空白の位置を記したリスト
   */
  private ArrayList<Integer> space;
  /**
   * 座標の値を記したリスト
   */
  private ArrayList<Float> locationValue;
  /**
   * 頂点座標をまとめたリスト
   */
  private ArrayList<Location> vertexLocation;
  /**
   * 法線ベクトルをまとめたリスト
   */
  private ArrayList<Location> normalLocation;

  /**
   * コンストラクタ
   */
  public Source() {
    this.space = new ArrayList<Integer>();
    this.locationValue = new ArrayList<Float>();
    this.vertexLocation = new ArrayList<Location>();
    this.normalLocation = new ArrayList<Location>();
  }

  /**
   * 頂点座標をまとめたリストを返す
   * 
   * @return　vertexLocation 頂点座標をまとめたリスト
   */
  public ArrayList<Location> getVertexLocation() {
    setVertexLocation();
    return this.vertexLocation;
  }

  /**
   * 法線ベクトルをまとめたリストを返す
   * 
   * @return　vertexLocation 法線ベクトルをまとめたリスト
   */
  public ArrayList<Location> getNormalLocation() {
    setNormalLocation();
    return this.normalLocation;
  }

  /**
   * 頂点座標一覧を記述した文字列から空白を除外し、 前から3つずつ頂点座標からLocationオブジェクトを作成してリストに加えていく。
   */
  @SuppressWarnings("boxing")
  private void setVertexLocation() {
    divideString();

    for (int i = 0; i < this.locationValue.size(); i += 3) {
      this.vertexLocation.add(new Location(this.locationValue.get(i), this.locationValue.get(i + 1), this.locationValue.get(i + 2)));
    }

  }

  /**
   * 頂法線ベクトル一覧を記述した文字列から空白を除外し、 前から3つずつ法線ベクトルからLocationオブジェクトを作成してリストに加えていく。
   */
  @SuppressWarnings("boxing")
  private void setNormalLocation() {
    divideString();

    for (int i = 0; i < this.locationValue.size(); i += 3) {
      this.normalLocation.add(new Location(this.locationValue.get(i), this.locationValue.get(i + 1), this.locationValue.get(i + 2)));
    }

  }

  /**
   * 数値の文字列をスペースの場所で分割し、数値として格納する
   */
  @SuppressWarnings("boxing")
  private void divideString() {
    for (int j = 0; j < this.float_array.length(); j++) {
      if (this.float_array.charAt(j) == ' ') {
        this.space.add(j);
      }
    }

    for (int j = 0; j < this.space.size() + 1; j++) {
      if (j == 0) {
        this.locationValue.add(Float.parseFloat(this.float_array.substring(0, this.space.get(0))));
      } else if (j == this.space.size()) {
        this.locationValue.add(Float.parseFloat(this.float_array.substring(this.space.get(this.space.size() - 1) + 1)));
      } else {
        this.locationValue.add(Float.parseFloat(this.float_array.substring(this.space.get(j - 1) + 1, this.space.get(j))));
      }
    }
  }
}
