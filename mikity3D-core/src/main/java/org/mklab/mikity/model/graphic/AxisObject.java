/*
 * Created on 2015/10/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.AxisModel;
import org.mklab.mikity.util.Vector3;

/**
 * 軸オブジェクトを表すクラスです。
 * 
 * @author eguchi
 * @version $Revision$, 2015/10/26
 */
public class AxisObject extends AbstractGraphicObject {
  /**
   * 新しく生成された<code>AxisPrimitive</code>オブジェクトを初期化します。
   * @param axis 軸モデル
   */
  public AxisObject(AxisModel axis) {
    super(axis);
    updatePolygons();
  }
  
  private void updatePolygons() {
    float radius = ((AxisModel)this.primitive).getRadius();
    float height = ((AxisModel)this.primitive).getHeight();
    int division = ((AxisModel)this.primitive).getDivision();
    
    if (radius == 0 || height == 0 || division == 0) {
      return;
    }
    
    final int upperCylinderPolygonNumber = division;
    final int lowerCylinderPolygonNumber = division;
    final int sideCylinderPolygonNumber = division*2;
    final int cylinderPolygonNumber = upperCylinderPolygonNumber + lowerCylinderPolygonNumber + sideCylinderPolygonNumber;
    
    final int lowerConePolygonNumber = division;
    final int sideConePolygonNumber = division;
    final int conePolygonNumber = lowerConePolygonNumber + sideConePolygonNumber;
    
    final int polygonNumber = cylinderPolygonNumber + conePolygonNumber;
    initializeArrays(polygonNumber);
    
    final float[] coneTopPoints = new float[3];
    coneTopPoints[0] = 0;
    coneTopPoints[1] = 0;
    coneTopPoints[2] = height;
    
    final float[] coneCenterPoints = new float[3];
    coneCenterPoints[0] = 0;
    coneCenterPoints[1] = 0;
    coneCenterPoints[2] = height - height / 6.0f;
    
    final float[][] coneLowPoints = new float[division+1][3];
    
    for (int i = 0; i <= division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      coneLowPoints[i][0] = radius * (float)Math.cos(theta) * 3;
      coneLowPoints[i][1] = radius * (float)Math.sin(theta) * 3; 
      coneLowPoints[i][2] =  height - height / 6.0f;
    }
    coneLowPoints[division][0] = radius * (float)Math.cos(0) * 3;
    coneLowPoints[division][1] = radius * (float)Math.sin(0) * 3; 
    coneLowPoints[division][2] =  height - height / 6.0f;
    
    updateLowerPolygons(coneCenterPoints, coneLowPoints, division);
    updateConeSidePolygons(coneTopPoints, coneLowPoints, division);
    
    final float[] cylinderUpperCenterPoint = new float[3];
    cylinderUpperCenterPoint[0] = 0;
    cylinderUpperCenterPoint[1] = 0;
    cylinderUpperCenterPoint[2] =  height - height / 6.0f;
    
    final float[] cylinderLowerCenterPoint = new float[3];
    cylinderLowerCenterPoint[0] = 0;
    cylinderLowerCenterPoint[1] = 0;
    cylinderLowerCenterPoint[2] = 0;

    final float[][] cylinderUpperPoints = new float[division+1][3];
    for (int i = 0; i < division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      cylinderUpperPoints[i][0] = (float)(radius * Math.cos(theta));
      cylinderUpperPoints[i][1] = (float)(radius * Math.sin(theta));
      cylinderUpperPoints[i][2] =  height - height / 6.0f;
    }
    cylinderUpperPoints[division][0] = (float)(radius * Math.cos(0));
    cylinderUpperPoints[division][1] = (float)(radius * Math.sin(0));
    cylinderUpperPoints[division][2] =  height - height / 6.0f;

    final float[][] cylinderLowerPoints = new float[division+1][3];
    for (int i = 0; i < division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      cylinderLowerPoints[i][0] = (float)(radius * Math.cos(theta));
      cylinderLowerPoints[i][1] = (float)(radius * Math.sin(theta));
      cylinderLowerPoints[i][2] = 0;
    }
    cylinderLowerPoints[division][0] = (float)(radius * Math.cos(0));
    cylinderLowerPoints[division][1] = (float)(radius * Math.sin(0));
    cylinderLowerPoints[division][2] = 0;
    
    updateLowerPolygons(cylinderLowerCenterPoint, cylinderLowerPoints, division);
    updateCylinderUpperPolygons(cylinderUpperCenterPoint, cylinderUpperPoints, division);
    updateCylinderSidePolygons(cylinderUpperPoints, cylinderLowerPoints, division);
  }
  
  /**
   * 上面のポリゴンを更新します。
   * 
   * @param upperCenterPoint 上面の中心
   * @param upperPoints 上面の周囲の頂点
   */
  private void updateCylinderUpperPolygons(final float[] upperCenterPoint, final float[][] upperPoints, int division) {
    for (int i = 0; i < division; i++) {
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
      appendNormalVectorsOfTriangle(normalVector);
    }
  }
  
  /**
   * 側面のポリゴンを生成します。
   * 
   * @param upperPoints 上面の周囲の頂点
   * @param lowerPoints 下面の周囲の頂点
   */
  private void updateCylinderSidePolygons(final float[][] upperPoints, final float[][] lowerPoints, int division) {
    for (int i = 0; i < division; i++) {
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
      appendNormalVectorsOfTriangle(normalVector);
    }
    
    for (int i = 0; i < division; i++) {
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
      appendNormalVectorsOfTriangle(normalVector);
    }
  }
  
  /**
   * 側面のポリゴンを更新します。
   * @param topPoint 上の点
   * @param lowerPoints 底面の周囲の頂点
   */
  private void updateConeSidePolygons(final float[] topPoint, final float[][] lowerPoints, int division) {
    for (int i = 0; i < division; i++) {
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
  private void updateLowerPolygons(final float[] centerPoint, final float[][] lowerPoints, int division) {
    for (int i = 0; i < division; i++) {
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
}
