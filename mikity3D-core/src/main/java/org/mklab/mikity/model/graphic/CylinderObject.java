package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.util.Vector3;


/**
 * 円柱(正多角柱)オブジェクトを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class CylinderObject extends AbstractGraphicObject {
  /**
   * 新しく生成された<code>CylinderObject</code>オブジェクトを初期化します。
   * @param cylinder シリンダー
   */
  public CylinderObject(CylinderModel cylinder) {
    super(cylinder);
    updatePolygons();
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    float radius = ((CylinderModel)this.primitive).getRadius();
    float height = ((CylinderModel)this.primitive).getHeight();
    int division = ((CylinderModel)this.primitive).getDivision();
    
    if (radius == 0 || height == 0 || division == 0) {
      return;
    }

    final int upperPolygonNumber = division;
    final int lowerPolygonNumber = division;
    final int sidePolygonNumber = division*2;
    final int polygonNumber = upperPolygonNumber + lowerPolygonNumber + sidePolygonNumber;
    initializeArrays(polygonNumber);

    final float[] upperCenterPoint = new float[3];
    upperCenterPoint[0] = 0;
    upperCenterPoint[1] = 0;
    upperCenterPoint[2] = height / 2;

    final float[] lowerCenterPoint = new float[3];
    lowerCenterPoint[0] = 0;
    lowerCenterPoint[1] = 0;
    lowerCenterPoint[2] = -height / 2;

    final float[][] upperPoints = new float[division+1][3];
    for (int i = 0; i < division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      upperPoints[i][0] = (float)(radius * Math.cos(theta));
      upperPoints[i][1] = (float)(radius * Math.sin(theta));
      upperPoints[i][2] = height / 2.0f;
    }
    upperPoints[division][0] = (float)(radius * Math.cos(0));
    upperPoints[division][1] = (float)(radius * Math.sin(0));
    upperPoints[division][2] = height / 2.0f;

    final float[][] lowerPoints = new float[division+1][3];
    for (int i = 0; i < division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      lowerPoints[i][0] = (float)(radius * Math.cos(theta));
      lowerPoints[i][1] = (float)(radius * Math.sin(theta));
      lowerPoints[i][2] = -height / 2.0f;
    }
    lowerPoints[division][0] = (float)(radius * Math.cos(0));
    lowerPoints[division][1] = (float)(radius * Math.sin(0));
    lowerPoints[division][2] = -height / 2.0f;
    
    updateUpperPolygons(upperCenterPoint, upperPoints, division);
    updateLowerPolygons(lowerCenterPoint, lowerPoints, division);
    updateSidePolygons(upperPoints, lowerPoints, division);
  }
  
  /**
   * 側面のポリゴンを生成します。
   * 
   * @param upperPoints 上面の周囲の頂点
   * @param lowerPoints 下面の周囲の頂点
   */
  private void updateSidePolygons(final float[][] upperPoints, final float[][] lowerPoints, int division) {
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
   * 下面のポリゴンを更新します。
   * 
   * @param lowerCenterPoint 下面の中心
   * @param lowerPoints 下面の周囲の頂点
   */
  private void updateLowerPolygons(final float[] lowerCenterPoint, final float[][] lowerPoints, int division) {
    for (int i = 0; i < division; i++) {
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
      appendNormalVectorsOfTriangle(normalVector);
    }
  }

  /**
   * 上面のポリゴンを更新します。
   * 
   * @param upperCenterPoint 上面の中心
   * @param upperPoints 上面の周囲の頂点
   */
  private void updateUpperPolygons(final float[] upperCenterPoint, final float[][] upperPoints, int division) {
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
}
