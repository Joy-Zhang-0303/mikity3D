/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesObject;
import org.mklab.mikity.util.Vector3;


/**
 * 三角錐を表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesCone extends AbstractOpenglesObject {
  /** 底面の半径。 */
  private float radius = 0;
  /** 高さ。 */
  private float height = 0;
  /** 分割数。 */
  private int division = 0;
  
  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    if (this.radius == 0 || this.height == 0 || this.division == 0) {
      return;
    }
    
    final float[] topPoint = new float[3];
    topPoint[0] = 0;
    topPoint[1] = 0;
    topPoint[2] = this.height / 2;
    
    final float[] centerPoint = new float[3];
    centerPoint[0] = 0;
    centerPoint[1] = 0;
    centerPoint[2]= -this.height / 2;
    
    final float[][] lowerPoints = new float[this.division+1][3];
    for (int i = 0; i <= this.division; i++) {
      final double theta = 2.0 * Math.PI / this.division * i;
      lowerPoints[i][0] = this.radius * (float)Math.cos(theta);
      lowerPoints[i][1] = this.radius * (float)Math.sin(theta); 
      lowerPoints[i][2] = -this.height / 2; 
    }
    lowerPoints[this.division][0] = this.radius * (float)Math.cos(0);
    lowerPoints[this.division][1] = this.radius * (float)Math.sin(0); 
    lowerPoints[this.division][2] = -this.height / 2; 

    final int lowerPolygonNumber = this.division;
    final int sidePolygonNumber = this.division;
    final int polygonNumber = lowerPolygonNumber + sidePolygonNumber;

    prepareArrays(polygonNumber);
    
    updateLowerPolygons(centerPoint, lowerPoints);
    updateSidePolygons(topPoint, lowerPoints);
  }
  
  /**
   * 側面のポリゴンを更新します。
   * @param topPoint 上の点
   * @param lowerPoints 底面の周囲の頂点
   */
  private void updateSidePolygons(final float[] topPoint, final float[][] lowerPoints) {
    for (int i = 0; i < this.division; i++) {
      final float[][] vertices = new float[3][3];
      vertices[0][0] = topPoint[0];
      vertices[0][1] = topPoint[1];
      vertices[0][2] = topPoint[2];
      
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
      appendNormalVectorsOfTriangle(normalVector);
    }
  }

  /**
   * 底面のポリゴンを更新します。
   * 
   * @param centerPoint 底面の中心
   * @param lowerPoints 底面の周囲の頂点
   */
  private void updateLowerPolygons(final float[] centerPoint, final float[][] lowerPoints) {
    for (int i = 0; i < this.division; i++) {
      final float[][] vertices = new float[3][3];
      vertices[0][0] = centerPoint[0];
      vertices[0][1] = centerPoint[1];
      vertices[0][2] = centerPoint[2];
      
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
      appendNormalVectorsOfTriangle(normalVector);
    }
  }

  /**
   * 大きさを設定します。
   * 
   * @param radius 底面の半径
   * @param hight 高さ
   */
  public void setSize(float radius, float hight) {
    this.radius = radius;
    this.height = hight;
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
