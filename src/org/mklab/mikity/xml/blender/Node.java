/*
 * $Id: Node.java,v 1.6 2008/02/03 04:25:00 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import javax.vecmath.Matrix4f;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Node要素)
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class Node {

  @XmlAttribute
  private String name;
  @XmlAttribute
  private String type;
  @XmlElement
  private String matrix;
  @XmlElement
  private Instance_Geometry instance_geometry;
  @XmlElement
  private ArrayList<Node> node;

  private ArrayList<Matrix4f> matrix_node;

  private String url;

  private Group g;

  /**
   * タブ・改行が含まれる位置を記録するリスト
   */
  private ArrayList<Integer> tab;
  /**
   * 列ベクトルリスト
   */
  private ArrayList<String> vector;
  /**
   * 変換行列
   */
  private Matrix4f matrix4f;

  /**
   * コンストラクタ
   */
  public Node() {
    this.instance_geometry = new Instance_Geometry();
    this.node = new ArrayList<Node>();
    this.tab = new ArrayList<Integer>();
    this.vector = new ArrayList<String>();
    this.matrix4f = new Matrix4f();
    this.matrix_node = new ArrayList<Matrix4f>();
    this.g = new Group();
  }

  /**
   * ノードの名前を返す
   * 
   * @return name　ノードの名前
   */
  public String loadName() {
    return this.name;
  }

  /**
   * @return
   */
  public String loadGeometryURL() {
    return this.url;
  }

  /**
   * 変換行列を返す
   * 
   * @return matrix4f　変換行列
   */
  public Matrix4f loadMatrix() {
    return this.matrix4f;
  }

  /**
   * 変換行列の要素をまとめた文字列から余分なものを省いて各要素の値を抽出して変換行列を生成する
   */
  @SuppressWarnings("boxing")
  public void createMatrix() {
    if (this.name.indexOf("-N") != -1) { //$NON-NLS-1$
      this.name = this.name.substring(0, this.name.indexOf("-N")); //$NON-NLS-1$
    }
    if (this.name.indexOf("-J") != -1) { //$NON-NLS-1$
      this.name = this.name.substring(0, this.name.indexOf("-J")); //$NON-NLS-1$
    }
    for (int i = 0; i < this.matrix.length(); i++) {
      if (this.matrix.indexOf("\n\t\t\t\t", i) == i) { //$NON-NLS-1$
        this.tab.add(i);
      }
    }
    for (int i = 0; i < this.tab.size() - 1; i++) {
      if (i == this.tab.size() - 2) {
        this.vector.add(this.matrix.substring(this.tab.get(i) + 6, this.tab.get(this.tab.size() - 1) - 1));
      } else {
        this.vector.add(this.matrix.substring(this.tab.get(i) + 6, this.tab.get(i + 1) - 1));
      }
    }
    for (int i = 0; i < this.vector.size(); i++) {
      ArrayList<Integer> space = new ArrayList<Integer>();
      for (int j = 0; j < this.vector.get(i).length(); j++) {
        if (this.vector.get(i).indexOf(" ", j) == j) { //$NON-NLS-1$
          space.add(j);
        }
      }
      float[] elements = new float[4];
      elements[0] = Float.parseFloat(this.vector.get(i).substring(0, space.get(0)));
      elements[1] = Float.parseFloat(this.vector.get(i).substring(space.get(0) + 1, space.get(1)));
      elements[2] = Float.parseFloat(this.vector.get(i).substring(space.get(1) + 1, space.get(2)));
      elements[3] = Float.parseFloat(this.vector.get(i).substring(space.get(2) + 1));
      this.matrix4f.setRow(i, elements);
    }
    if (this.node.size() != 0) {
      for (int i = 0; i < this.node.size(); i++) {
        this.node.get(i).createMatrix();
        this.matrix_node.add(this.node.get(i).loadMatrix());
      }
    }
    setURL();
  }

  /**
   * @return
   */
  public String loadType() {
    return this.type;
  }

  private void setURL() {
    this.url = this.instance_geometry.loadURL();
    if (this.url != null) {
      if (this.url.indexOf("-G") != -1) { //$NON-NLS-1$
        this.url = this.url.substring(1, this.url.indexOf("-G")); //$NON-NLS-1$
      } else {
        this.url = this.url.substring(1);
      }
    }
  }

  /**
   * 
   */
  public void createScene() {
    for (int i = 0; i < this.node.size(); i++) {
      this.node.get(i).createGroup();
      this.node.get(i).createScene();
    }
    for (int i = 0; i < this.node.size(); i++) {
      if (this.node.get(i) != null) {
        this.g.addGroup(this.node.get(i).getGroup());
      }
    }
  }

  /**
   * 
   */
  public void createGroup() {
    if (this.type != null) {
      if (this.type.equals("JOINT")) { //$NON-NLS-1$
        this.g.setName(this.name);
        Linkdata[] linkdata = new Linkdata[3];
        linkdata[0] = new Linkdata();
        linkdata[0].setTarget("locationX"); //$NON-NLS-1$
        linkdata[0].setConst(this.matrix4f.m03);
        linkdata[1] = new Linkdata();
        linkdata[1].setTarget("locationY"); //$NON-NLS-1$
        linkdata[1].setConst(this.matrix4f.m13);
        linkdata[2] = new Linkdata();
        linkdata[2].setTarget("locationZ"); //$NON-NLS-1$
        linkdata[2].setConst(this.matrix4f.m23);
        this.g.setLinkdata(linkdata);
      }
    }
  }

  /**
   * @return
   */
  public Group getGroup() {
    return this.g;
  }
}