package org.mklab.mikity.view.renderer.jogl.primitive;

import javax.media.opengl.GL2;

import org.mklab.mikity.util.Vector3;
import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * シリンダーをGLで表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class JoglCylinder extends AbstractJoglObject {
  /** 底面の半径 */
  private float radius = 0;

  /** 高さ */
  private float height = 0;

  /** 分割数 */
  private int division = 0;

//  /** 上面ポリゴン。 */
//  private JoglTrianglePolygon[] upperPolygons;
//
//  /** 下面ポリゴン。 */
//  private JoglTrianglePolygon[] lowerPolygons;
//
//  /** 側面ポリゴン1。 */
//  private JoglTrianglePolygon[] sidePolygons1;
//
//  /** 側面ポリゴン2。 */
//  private JoglTrianglePolygon[] sidePolygons2;

  /** 頂点配列の参照位置。 */
  private int vertexPosition = 0;
  
  /** 法線ベクトル配列の参照位置。 */
  private int normalVectorPosition = 0;
  
  /** 頂点配列。 */
  private float vertexArray[];
  
  /** 法線ベクトル配列。 */
  private float normalVectorArray[];
  
  /**
   * {@inheritDoc}
   */
  public void display(GL2 gl) {
    applyColor(gl);
    applyTransparency(gl);
    
    drawTrianglePolygons(gl, this.vertexArray, this.normalVectorArray);
    
//    for (int i = 0; i < this.division; i++) {
//      this.upperPolygons[i].display(gl);
//      this.lowerPolygons[i].display(gl);
//      this.sidePolygons1[i].display(gl);
//      this.sidePolygons2[i].display(gl);
//    }
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    if (this.radius == 0 || this.height == 0 || this.division == 0) {
      return;
    }
    
    final float[] upperCenterPoint = new float[3];
    upperCenterPoint[0] = 0;
    upperCenterPoint[1] = 0;
    upperCenterPoint[2] = this.height / 2;

    final float[] lowerCenterPoint = new float[3];
    lowerCenterPoint[0] = 0;
    lowerCenterPoint[1] = 0;
    lowerCenterPoint[2] = -this.height / 2;

    final float[][] upperPoints = new float[this.division+1][3];
    for (int i = 0; i < this.division; i++) {
      final double theta = 2.0 * Math.PI / this.division * i;
      upperPoints[i][0] = (float)(this.radius * Math.cos(theta));
      upperPoints[i][1] = (float)(this.radius * Math.sin(theta));
      upperPoints[i][2] = this.height / 2.0f;
    }
    upperPoints[this.division][0] = (float)(this.radius * Math.cos(0));
    upperPoints[this.division][1] = (float)(this.radius * Math.sin(0));
    upperPoints[this.division][2] = this.height / 2.0f;

    final float[][] lowerPoints = new float[this.division+1][3];
    for (int i = 0; i < this.division; i++) {
      final double theta = 2.0 * Math.PI / this.division * i;
      lowerPoints[i][0] = (float)(this.radius * Math.cos(theta));
      lowerPoints[i][1] = (float)(this.radius * Math.sin(theta));
      lowerPoints[i][2] = -this.height / 2.0f;
    }
    lowerPoints[this.division][0] = (float)(this.radius * Math.cos(0));
    lowerPoints[this.division][1] = (float)(this.radius * Math.sin(0));
    lowerPoints[this.division][2] = -this.height / 2.0f;

    final int upperPolygonNumber = this.division;
    final int lowerPolygonNumber = this.division;
    final int sidePolygonNumber = this.division*2;
    final int polygonNumber = upperPolygonNumber + lowerPolygonNumber + sidePolygonNumber;

    this.vertexPosition = 0;
    this.normalVectorPosition = 0;
    this.vertexArray = new float[polygonNumber*3*3];
    this.normalVectorArray = new float[polygonNumber*3*3];
    
    updateUpperPolygons(upperCenterPoint, upperPoints);
    updateLowerPolygons(lowerCenterPoint, lowerPoints);
    updateSidePolygons(upperPoints, lowerPoints);
  }
  
  /**
   * 頂点を頂点配列に追加します。
   * @param vertices 頂点
   */
  private void appendVertices(float[][] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      for (int j = 0; j < 3; j++) {
        this.vertexArray[this.vertexPosition++] = vertices[i][j];
      }
    }
  }

  /**
   * 法線ベクトルを法線ベクトル配列に3個追加します。
   * @param normalVector 法線ベクトル
   */
  private void appendNormalVectors(float[] normalVector) {
    for (int i = 0; i < 3; i++) {
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[0];
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[1];
      this.normalVectorArray[this.normalVectorPosition++] = normalVector[2];
    }
  }

  
  /**
   * 側面のポリゴンを生成します。
   * 
   * @param upperPoints 上面の周囲の頂点
   * @param lowerPoints 下面の周囲の頂点
   */
  private void updateSidePolygons(final float[][] upperPoints, final float[][] lowerPoints) {
//    this.sidePolygons1 = new JoglTrianglePolygon[this.division];
    for (int i = 0; i < this.division; i++) {
//      this.sidePolygons1[i] = new JoglTrianglePolygon();
      final float[][] vertices = new float[3][3];
      vertices[0][0] = upperPoints[i][0];
      vertices[0][1] = upperPoints[i][1];
      vertices[0][2] = upperPoints[i][2];
      
      vertices[1][0] = lowerPoints[i][0];
      vertices[1][1] = lowerPoints[i][1];
      vertices[1][2] = lowerPoints[i][2];

      vertices[2][0] = lowerPoints[i+1][0];
      vertices[2][1] = lowerPoints[i+1][1];
      vertices[2][2] = lowerPoints[i+1][2];
      
      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();

      appendVertices(vertices);
      appendNormalVectors(normalVector);
      
//      this.sidePolygons1[i].setVertices(vertices);
//      this.sidePolygons1[i].setNormalVector(normalVector);
    }
    
    //this.sidePolygons2 = new JoglTrianglePolygon[this.division];
    for (int i = 0; i < this.division; i++) {
      //this.sidePolygons2[i] = new JoglTrianglePolygon();
      final float[][] vertices = new float[3][3];

      vertices[0][0] = upperPoints[i][0];
      vertices[0][1] = upperPoints[i][1];
      vertices[0][2] = upperPoints[i][2];
      
      vertices[1][0] = lowerPoints[i+1][0];
      vertices[1][1] = lowerPoints[i+1][1];
      vertices[1][2] = lowerPoints[i+1][2];

      vertices[2][0] = upperPoints[i+1][0];
      vertices[2][1] = upperPoints[i+1][1];
      vertices[2][2] = upperPoints[i+1][2];
      
      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();

      appendVertices(vertices);
      appendNormalVectors(normalVector);
      
//      this.sidePolygons2[i].setVertices(vertices);
//      this.sidePolygons2[i].setNormalVector(normalVector);
    }
  }

  /**
   * 下面のポリゴンを更新します。
   * 
   * @param lowerCenterPoint 下面の中心
   * @param lowerPoints 下面の周囲の頂点
   */
  private void updateLowerPolygons(final float[] lowerCenterPoint, final float[][] lowerPoints) {
//    this.lowerPolygons = new JoglTrianglePolygon[this.division];
    for (int i = 0; i < this.division; i++) {
//      this.lowerPolygons[i] = new JoglTrianglePolygon();
      final float[][] vertices = new float[3][3];
      vertices[0][0] = lowerCenterPoint[0];
      vertices[0][1] = lowerCenterPoint[1];
      vertices[0][2] = lowerCenterPoint[2];
      
      vertices[1][0] = lowerPoints[i+1][0];
      vertices[1][1] = lowerPoints[i+1][1];
      vertices[1][2] = lowerPoints[i+1][2];

      vertices[2][0] = lowerPoints[i][0];
      vertices[2][1] = lowerPoints[i][1];
      vertices[2][2] = lowerPoints[i][2];

      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();

      appendVertices(vertices);
      appendNormalVectors(normalVector);
      
//      this.lowerPolygons[i].setVertices(vertices);
//      this.lowerPolygons[i].setNormalVector(normalVector);
    }
  }

  /**
   * 上面のポリゴンを更新します。
   * 
   * @param upperCenterPoint 上面の中心
   * @param upperPoints 上面の周囲の頂点
   */
  private void updateUpperPolygons(final float[] upperCenterPoint, final float[][] upperPoints) {
//    this.upperPolygons = new JoglTrianglePolygon[this.division];
    for (int i = 0; i < this.division; i++) {
//      this.upperPolygons[i] = new JoglTrianglePolygon();
      final float[][] vertices = new float[3][3];
      vertices[0][0] = upperCenterPoint[0];
      vertices[0][1] = upperCenterPoint[1];
      vertices[0][2] = upperCenterPoint[2];
      
      vertices[1][0] = upperPoints[i][0];
      vertices[1][1] = upperPoints[i][1];
      vertices[1][2] = upperPoints[i][2];

      vertices[2][0] = upperPoints[i+1][0];
      vertices[2][1] = upperPoints[i+1][1];
      vertices[2][2] = upperPoints[i+1][2];

      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();

      appendVertices(vertices);
      appendNormalVectors(normalVector);
      
//      this.upperPolygons[i].setVertices(vertices);
//      this.upperPolygons[i].setNormalVector(normalVector);
    }
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param height 高さ
   */
  public void setSize(float radius, float height) {
    this.radius = radius;
    this.height = height;
    updatePolygons();
  }

  /**
   * 分割数を設定します。
   * 
   * @param division 分割数
   */
  public void setDivision(int division) {
    this.division = division;
    updatePolygons();
  }

}
