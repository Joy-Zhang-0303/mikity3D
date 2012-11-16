/*
 * $Id: Node.java,v 1.6 2008/02/03 04:25:00 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.LinkData;

/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Node要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class Node {
  @XmlAttribute
  private String name;
  @XmlAttribute
  private String type;
  @XmlAttribute
  private String id;
  @XmlElement
  private String matrix;
  @XmlElement
  private Instance_Geometry instance_geometry;
  @XmlElement
  private List<Node> nodes;
  @XmlElement
  private String translate;
  @XmlElement
  private List<String> rotate;

  private List<Matrix4f> matrixNodes;

  private String geometryURL;

  private Group group;

  /** 列ベクトルリスト */
  private List<String> vectors;
  /** 変換行列 */
  private Matrix4f matrix4f;

  /** translateの番号記録用 */
  private float rotateNum[][] = {{(float)0.0,(float)0.0,(float)0.0,(float)0.0},{(float)0.0,(float)0.0,(float)0.0,(float)0.0},{(float)0.0,(float)0.0,(float)0.0,(float)0.0}};
  /** 移動用行列 */
  private double translater[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};
  /** x回転行列  */
  private double rotateX[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};
  /** y回転行列 */
  private double rotateY[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};
  /** z回転行列 */
  private double rotateZ[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};

  /** 変換行列記録用 */
  private double matrixXY[][] = {{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0}};
  private double matrixXYZ[][] = {{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0}};
  private double matrixAll[][] = {{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0}};

  /**
   * 新しく生成された<code>Node</code>オブジェクトを初期化します。
   */
  public Node() {
    this.instance_geometry = new Instance_Geometry();
    this.nodes = new ArrayList<Node>();
    this.vectors = new ArrayList<String>();
    this.matrix4f = new Matrix4f();
    this.matrixNodes = new ArrayList<Matrix4f>();
    this.group = new Group();
  }

  /**
   * ノードの名前を返します。
   * 
   * @return　ノードの名前
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return geometry URL
   */
  public String getGeometryURL() {
    return this.geometryURL;
  }

  /**
   * 変換行列を返します。
   * 
   * @return 変換行列
   */
  public Matrix4f getTransformMatrix() {
    return this.matrix4f;
  }

  /**
   * 変換行列の要素をまとめた文字列から余分なものを省いて，各要素の値を抽出して変換行列を生成します。
   */
  @SuppressWarnings("boxing")
  public void createTransformMatrix() {
    if(this.translate == null){
      if (this.name.indexOf("-N") != -1) { //$NON-NLS-1$
        this.name = this.name.substring(0, this.name.indexOf("-N")); //$NON-NLS-1$
      }
      if (this.name.indexOf("-J") != -1) { //$NON-NLS-1$
        this.name = this.name.substring(0, this.name.indexOf("-J")); //$NON-NLS-1$
      }
      ArrayList<Integer> tab = new ArrayList<Integer>();
      for (int i = 0; i < this.matrix.length(); i++) {
        if (this.matrix.indexOf("\n\t\t\t\t", i) == i) { //$NON-NLS-1$
          tab.add(i);
        }
      }
      for (int i = 0; i < tab.size() - 1; i++) {
        if (i == tab.size() - 2) {
          this.vectors.add(this.matrix.substring(tab.get(i) + 6, tab.get(tab.size() - 1) - 1));
        } else {
          this.vectors.add(this.matrix.substring(tab.get(i) + 6, tab.get(i + 1) - 1));
        }
      }
      for (int i = 0; i < this.vectors.size(); i++) {
        ArrayList<Integer> space = new ArrayList<Integer>();
        for (int j = 0; j < this.vectors.get(i).length(); j++) {
          if (this.vectors.get(i).indexOf(" ", j) == j) { //$NON-NLS-1$
            space.add(j);
          }
        }
        float[] elements = new float[4];
        elements[0] = Float.parseFloat(this.vectors.get(i).substring(0, space.get(0)));
        elements[1] = Float.parseFloat(this.vectors.get(i).substring(space.get(0) + 1, space.get(1)));
        elements[2] = Float.parseFloat(this.vectors.get(i).substring(space.get(1) + 1, space.get(2)));
        elements[3] = Float.parseFloat(this.vectors.get(i).substring(space.get(2) + 1));
        this.matrix4f.setRow(i, elements);
      }
      if (this.nodes.size() != 0) {
        for (int i = 0; i < this.nodes.size(); i++) {
          this.nodes.get(i).createTransformMatrix();
          this.matrixNodes.add(this.nodes.get(i).getTransformMatrix());
        }
      }
      setURL();
      //ここからver1．4.1用
    }else /*if(this.version == "1.4.1")*/{ 
      if(this.name == null){
        this.name = this.id;
      }

      /**
       * translate
       */
      String[] trans =  this.translate.split(" "); //$NON-NLS-1$
      float[] n = new float[trans.length];
      for(int l = 0; l < trans.length; l++){
        n[l] = Float.parseFloat(trans[l]);
      }

      for (int i = 0; i < 4;i++){
        for(int j = 0; j < 4; j++){
          if(i == j){
            this.translater[i][j] = (float)1.0;
          }else if(i != 3 && j == 3){
            this.translater[i][j] = n[i];//num.get(i);
          }else{
            this.translater[i][j] = (float)0.0;
          }

        }
      }
      
      /**
       * rotate
       */
      for (int i = 0; i < this.rotate.size(); i++) {
        List<Integer> space = new ArrayList<Integer>();
        for (int j = 0; j < this.rotate.get(i).length(); j++) {
          if (this.rotate.get(i).charAt(j) == ' ') {
            space.add(j);
          }
        }
        int[] spaceNum = new int[space.size()];
        for (int k = 0; k < spaceNum.length; k++) {
          spaceNum[k] = space.get(k);
        }

        for (int j = 0; j < spaceNum.length + 1; j ++) {
          if (j == 0) {
            this.rotateNum[i][j] = Float.parseFloat(this.rotate.get(i).substring(0, spaceNum[0]));
          }else if (j == spaceNum.length){
            this.rotateNum[i][j] = Float.parseFloat(this.rotate.get(i).substring(spaceNum[spaceNum.length - 1] + 1));
          }else{
            this.rotateNum[i][j] = Float.parseFloat(this.rotate.get(i).substring(spaceNum[j - 1] + 1, spaceNum[j]));
          }
        }
      }

      //rotateの変換行列の作成
      for(int i = 0; i < 3; i++){
        setRotateMatrix(this.rotateNum,i);//this.rotateNumber.get(i),i);
      }
      //変換行列の作成
      createMatrix4f();   
      
      if (this.nodes.size() != 0) {
        for (int i = 0; i < this.nodes.size(); i++) {
          this.nodes.get(i).createTransformMatrix();
          this.matrixNodes.add(this.nodes.get(i).getTransformMatrix());
        }
      }
      setURL();
    }

  }

  private void createMatrix4f() {
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 4; j++){
        for (int k = 0; k < 4; k++){
          this.matrixXY[i][j] += this.rotateX[i][k] * this.rotateY[k][j];
        }
      }
    }
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 4; j++){
        for (int k = 0; k < 4; k++){
          this.matrixXYZ[i][j] += this.matrixXY[i][k] * this.rotateZ[k][j];
        }
      }
    }
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 4; j++){
        for (int k = 0; k < 4; k++){
          this.matrixAll[i][j] += this.matrixXYZ[i][k] * this.translater[k][j];
        }
      }
    }
    setMatrix4f();
  }

  private void setMatrix4f() {
    float[] elements = new float[4];
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 4; j++){
        elements[j] = (float)this.matrixAll[i][j];
      }
      this.matrix4f.setRow(i, elements);
    }
  }

  private  void setRotateMatrix(float[][] rotate,int k) {
    if(k == 0){
      for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
          if(i == j && i < 2){
            this.rotateZ[i][j] = Math.cos(rotate[k][3]);
          }else if(i == 0 && j == 1){
            this.rotateZ[i][j] = -1.0 * Math.sin(rotate[k][3]);
          }else if(i == 1 && j == 0){
            this.rotateZ[i][j] = Math.sin(rotate[k][3]);
          }else if(i == j && i > 1){
            this.rotateZ[i][j] = 1.0;
          }else{
            this.rotateZ[i][j] = 0.0;
          }
        }
      }
    }else if(k == 1){
     for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
          if(i == j && i == 0){
            this.rotateY[i][j] = Math.cos(rotate[k][3]);
          }else if(i == j && i == 2){
            this.rotateY[i][j] = Math.cos(rotate[k][3]);
          }else if(i == 0 && j == 1){
            this.rotateY[i][j] = -1.0 * Math.sin(rotate[k][3]);
          }else if(i == 1 && j == 0){
            this.rotateY[i][j] = Math.sin(rotate[k][3]);
          }else if(i == j && ((i == 1) || i == 3)){
            this.rotateY[i][j] = 1.0;
          }else{
            this.rotateY[i][j] = 0.0;
          }
        }
      }
    }else if(k == 2){
      for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
          if(i == j && i > 0 && i < 3){
            this.rotateX[i][j] = Math.cos(rotate[k][3]);
          }else if(i == 1 && j == 2){
            this.rotateX[i][j] = -1.0 *Math.sin(rotate[k][3]);
          }else if(i == 2 && j == 1){
            this.rotateX[i][j] = Math.sin(rotate[k][3]);
          }else if(i == j && (i > 1 || i == 0)){
            this.rotateX[i][j] = 1.0;
          }else{
            this.rotateX[i][j] = (float)0.0;
          }
        }
      }
    }
  }

  
  /**
   * @return type
   */
  public String getType() {
    return this.type;
  }

  private void setURL() {
    this.geometryURL = this.instance_geometry.getGeometryURL();
    if (this.geometryURL != null) {
      if (this.geometryURL.indexOf("-G") != -1) { //$NON-NLS-1$
        this.geometryURL = this.geometryURL.substring(1, this.geometryURL.indexOf("-G")); //$NON-NLS-1$
      } else if (this.geometryURL.indexOf("-m") != -1){ //$NON-NLS-1$
        this.geometryURL = this.geometryURL.substring(1, this.geometryURL.indexOf("-m")); //$NON-NLS-1$
      }else{
        this.geometryURL = this.geometryURL.substring(1);
      }
    }
  }

  /**
   * 
   */
  public void createScene() {
    for (final Node node : this.nodes) {
      node.createGroup();
      node.createScene();
    }
    
    for (final Node node : this.nodes) {
      if (node != null) {
        this.group.addGroup(node.getGroup());
      }
    }
    
  }

  /**
   * 
   */
  public void createGroup() {
    if (this.type != null) {
      if (this.type.equals("JOINT")) { //$NON-NLS-1$
        this.group.setName(this.name);
        LinkData[] linkdata = new LinkData[3];
        linkdata[0] = new LinkData();
        linkdata[0].setTargetName("locationX"); //$NON-NLS-1$
        linkdata[0].setInitialValue(this.matrix4f.m03);
        linkdata[1] = new LinkData();
        linkdata[1].setTargetName("locationY"); //$NON-NLS-1$
        linkdata[1].setInitialValue(this.matrix4f.m13);
        linkdata[2] = new LinkData();
        linkdata[2].setTargetName("locationZ"); //$NON-NLS-1$
        linkdata[2].setInitialValue(this.matrix4f.m23);
        this.group.setLinkData(linkdata);
      }
    }
  }

  /**
   * @return group
   */
  public Group getGroup() {
    return this.group;
  }
}