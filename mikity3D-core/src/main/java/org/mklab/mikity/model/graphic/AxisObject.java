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
  
  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    final float radius = ((AxisModel)this.object).getRadius();
    final float height = ((AxisModel)this.object).getHeight();
    final int division = ((AxisModel)this.object).getDivision();
    
    if (radius == 0 || height == 0 || division == 0) {
      return;
    }
    
    final int bodyUpperPolygonNumber = division;
    final int bodyLowerPolygonNumber = division;
    final int bodySidePolygonNumber = division*2;
    final int bodyPolygonNumber = bodyUpperPolygonNumber + bodyLowerPolygonNumber + bodySidePolygonNumber;
    
    final int headerLowerPolygonNumber = division;
    final int headerSidePolygonNumber = division;
    final int headerPolygonNumber = headerLowerPolygonNumber + headerSidePolygonNumber;
    
    final int polygonNumber = bodyPolygonNumber + headerPolygonNumber;
    initializeArrays(polygonNumber);
    
    updateHeaderPolygons(radius, height, division);
    updateBodyPolygons(radius, height, division);
  }

  /**
   * 軸本体のポリゴンを更新します。
   * 
   * @param radius 半径
   * @param height 高さ
   * @param division 分割数
   */
  private void updateBodyPolygons(final float radius, final float height, final int division) {
    final float headerHeight = 6 * radius;
    
    final float[] cylinderUpperCenterPoint = new float[3];
    cylinderUpperCenterPoint[0] = 0;
    cylinderUpperCenterPoint[1] = 0;
    cylinderUpperCenterPoint[2] =  height - headerHeight;
    
    final float[] cylinderLowerCenterPoint = new float[3];
    cylinderLowerCenterPoint[0] = 0;
    cylinderLowerCenterPoint[1] = 0;
    cylinderLowerCenterPoint[2] = 0;

    final float[][] cylinderUpperPoints = new float[division+1][3];
    for (int i = 0; i < division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      cylinderUpperPoints[i][0] = (float)(radius * Math.cos(theta));
      cylinderUpperPoints[i][1] = (float)(radius * Math.sin(theta));
      cylinderUpperPoints[i][2] =  height - headerHeight;
    }
    cylinderUpperPoints[division][0] = (float)(radius * Math.cos(0));
    cylinderUpperPoints[division][1] = (float)(radius * Math.sin(0));
    cylinderUpperPoints[division][2] =  height - headerHeight;

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
    
    updateLowerDiskPolygons(cylinderLowerCenterPoint, cylinderLowerPoints, division);
    updateUpperDiskPolygons(cylinderUpperCenterPoint, cylinderUpperPoints, division);
    updateBodySidePolygons(cylinderUpperPoints, cylinderLowerPoints, division);
  }

  /**
   * 軸先端のポリゴンを更新します。
   * 
   * @param radius 半径
   * @param height 高さ
   * @param division 分割数
   */
  private void updateHeaderPolygons(final float radius, final float height, final int division) {
    final float headerRadius = 3 * radius;    
    final float headerHeight = 6 * radius;
    
    final float[] coneTopPoints = new float[3];
    coneTopPoints[0] = 0;
    coneTopPoints[1] = 0;
    coneTopPoints[2] = height;
    
    final float[] coneCenterPoints = new float[3];
    coneCenterPoints[0] = 0;
    coneCenterPoints[1] = 0;
    coneCenterPoints[2] = height - headerHeight;
    
    final float[][] coneLowerPoints = new float[division+1][3];
    
    for (int i = 0; i <= division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      coneLowerPoints[i][0] = headerRadius * (float)Math.cos(theta);
      coneLowerPoints[i][1] = headerRadius * (float)Math.sin(theta); 
      coneLowerPoints[i][2] = height - headerHeight;
    }
    coneLowerPoints[division][0] = headerRadius * (float)Math.cos(0);
    coneLowerPoints[division][1] = headerRadius * (float)Math.sin(0); 
    coneLowerPoints[division][2] = height - headerHeight;
    
    updateLowerDiskPolygons(coneCenterPoints, coneLowerPoints, division);
    updateHeaderSidePolygons(coneTopPoints, coneLowerPoints, division);
  }
  
  /**
   * 円盤の上面のポリゴンを更新します。
   * 
   * @param centerPoint 円盤の中心
   * @param surroundingPoints 周囲の頂点
   */
  private void updateUpperDiskPolygons(final float[] centerPoint, final float[][] surroundingPoints, int division) {
    for (int i = 0; i < division; i++) {
      final float[][] vertices = new float[3][3];
      vertices[0][0] = centerPoint[0];
      vertices[0][1] = centerPoint[1];
      vertices[0][2] = centerPoint[2];
      
      vertices[1][0] = surroundingPoints[i][0];
      vertices[1][1] = surroundingPoints[i][1];
      vertices[1][2] = surroundingPoints[i][2];

      vertices[2][0] = surroundingPoints[i+1][0];
      vertices[2][1] = surroundingPoints[i+1][1];
      vertices[2][2] = surroundingPoints[i+1][2];

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
  private void updateBodySidePolygons(final float[][] upperPoints, final float[][] lowerPoints, int division) {
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
   * 
   * @param topPoint 上の点
   * @param lowerPoints 底面の周囲の頂点
   */
  private void updateHeaderSidePolygons(final float[] topPoint, final float[][] lowerPoints, int division) {
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
   * 円盤の下面のポリゴンを更新します。
   * 
   * @param centerPoint 円盤の中心
   * @param surroundingPoints 底面の周囲の頂点
   */
  private void updateLowerDiskPolygons(final float[] centerPoint, final float[][] surroundingPoints, int division) {
    for (int i = 0; i < division; i++) {
      final float[][] vertices = new float[3][3];
      vertices[0][0] = centerPoint[0];
      vertices[0][1] = centerPoint[1];
      vertices[0][2] = centerPoint[2];
      
      vertices[1][0] = surroundingPoints[i+1][0];
      vertices[1][1] = surroundingPoints[i+1][1];
      vertices[1][2] = surroundingPoints[i+1][2];

      vertices[2][0] = surroundingPoints[i][0];
      vertices[2][1] = surroundingPoints[i][1];
      vertices[2][2] = surroundingPoints[i][2];

      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();

      appendVertices(vertices);
      appendNormalVectorsOfTriangle(normalVector);
    }
  }
}
