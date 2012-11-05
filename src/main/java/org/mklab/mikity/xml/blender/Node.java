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
import org.mklab.mikity.xml.model.LinkData;



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
  @XmlAttribute
  private String id;
  @XmlElement
  private String matrix;
  @XmlElement
  private Instance_Geometry instance_geometry;
  @XmlElement
  private ArrayList<Node> node;
  @XmlElement
  private String translate;
  @XmlElement
  private ArrayList<String> rotate;

  private ArrayList<Matrix4f> matrix_node;

  private String url;

  private Group g;

  /**
   * タブ・改行が含まれる位置を記録するリスト
   */
//  private ArrayList<Integer> tab;
  /**
   * 列ベクトルリスト
   */
  private ArrayList<String> vector;
  /**
   * 変換行列
   */
  private Matrix4f matrix4f;

  /**
   * rotateの空白記録用
   */
  //private ArrayList<int[]> spaceNum;
  /**
   * translateの番号記録用
   */
  //private ArrayList<float[]> translateNumber;
  private float rotateNum[][] = {{(float)0.0,(float)0.0,(float)0.0,(float)0.0},{(float)0.0,(float)0.0,(float)0.0,(float)0.0},{(float)0.0,(float)0.0,(float)0.0,(float)0.0}};
  //private ArrayList<Float> rotateNum;
  /**
   * rotateの番号記録用
   */
  //private ArrayList<float[]> rotateNumber;
  /**
   * 移動用行列
   */
  private double translater[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};

  /**
   * x回転行列
   */
  private double rotateX[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};
  /**
   * x回転行列
   */
  private double rotateY[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};
  /**
   * x回転行列
   */
  private double rotateZ[][] = {{1.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0},{0.0,0.0,1.0,0.0},{0.0,0.0,0.0,1.0}};

  /**
   * 変換行列記録用
   */
  private double matrixXY[][] = {{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0}};
  private double matrixXYZ[][] = {{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0}};
  private double matrixAll[][] = {{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0},{0.0,0.0,0.0,0.0}};

  
  /**
   * コンストラクタ
   */
  public Node() {
    this.instance_geometry = new Instance_Geometry();
    this.node = new ArrayList<Node>();
//    this.tab = new ArrayList<Integer>();
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
   * @return geometry URL
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
          this.vector.add(this.matrix.substring(tab.get(i) + 6, tab.get(tab.size() - 1) - 1));
        } else {
          this.vector.add(this.matrix.substring(tab.get(i) + 6, tab.get(i + 1) - 1));
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
      //ここからver1．4.1用
    }else /*if(this.version == "1.4.1")*/{ 
      if(this.name == null){
        this.name = this.id;
      }
      /**
       * translate
       */
      //ArrayList<Integer> translateSpace = new ArrayList<Integer>();
      String[] trans =  this.translate.split(" "); //$NON-NLS-1$
      float[] n = new float[trans.length];
      for(int l = 0; l < trans.length; l++){
        n[l] = Float.parseFloat(trans[l]);
      }
      //this.translateNumber.add(n);
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
        ArrayList<Integer> space = new ArrayList<Integer>();
        for (int j = 0; j < this.rotate.get(i).length(); j++) {
          if (this.rotate.get(i).charAt(j) == ' ') {
            space.add(j);
          }
        }
        int[] spaceNum = new int[space.size()];
        for (int k = 0; k < spaceNum.length; k++) {
          spaceNum[k] = space.get(k);
        }
        //this.rotateSpaceList.add(spaceNum);
        //float[][] rotateNum = new float[4][4];
        //ArrayList<Float> rotateNum = new ArrayList<Float>();
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
      //this.rotateNumber.add(rotateNum);
      //rotateの変換行列の作成
      for(int i = 0; i < 3; i++){
        setRotateMatrix(this.rotateNum,i);//this.rotateNumber.get(i),i);
      }
      //変換行列の作成
      makeMatrix4f();
      
      
      if (this.node.size() != 0) {
        for (int i = 0; i < this.node.size(); i++) {
          this.node.get(i).createMatrix();
          this.matrix_node.add(this.node.get(i).loadMatrix());
        }
      }
      setURL();
    }

  }

  private void makeMatrix4f() {
    
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
  public String loadType() {
    return this.type;
  }

  private void setURL() {
    this.url = this.instance_geometry.loadURL();
    if (this.url != null) {
      if (this.url.indexOf("-G") != -1) { //$NON-NLS-1$
        this.url = this.url.substring(1, this.url.indexOf("-G")); //$NON-NLS-1$
      } else if (this.url.indexOf("-m") != -1){ //$NON-NLS-1$
        this.url = this.url.substring(1, this.url.indexOf("-m")); //$NON-NLS-1$
      }else{
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
        this.g.setLinkData(linkdata);
      }
    }
  }

  /**
   * @return group
   */
  public Group getGroup() {
    return this.g;
  }
}