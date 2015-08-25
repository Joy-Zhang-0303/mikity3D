package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.util.Vector3;

/**
 * 円錐(正多角錘)プリミティブを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class ConePrimitive extends AbstractGraphicPrimitive {
  /**
   * 新しく生成された<code>ConeObject</code>オブジェクトを初期化します。
   * @param cone コーン
   */
  public ConePrimitive(ConeModel cone) {
    super(cone);
    updatePolygons();
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    float radius = ((ConeModel)this.primitive).getRadisu();
    float height = ((ConeModel)this.primitive).getHeight();
    int division = ((ConeModel)this.primitive).getDivision();
    
    if (radius == 0 || height == 0 || division == 0) {
      return;
    }

    final int lowerPolygonNumber = division;
    final int sidePolygonNumber = division;
    final int polygonNumber = lowerPolygonNumber + sidePolygonNumber;
    initializeArrays(polygonNumber);

    final float[] topPoint = new float[3];
    topPoint[0] = 0;
    topPoint[1] = 0;
    topPoint[2] = height / 2;
    
    final float[] centerPoint = new float[3];
    centerPoint[0] = 0;
    centerPoint[1] = 0;
    centerPoint[2]= -height / 2;
    
    final float[][] lowerPoints = new float[division+1][3];
    for (int i = 0; i <= division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      lowerPoints[i][0] = radius * (float)Math.cos(theta);
      lowerPoints[i][1] = radius * (float)Math.sin(theta); 
      lowerPoints[i][2] = -height / 2; 
    }
    lowerPoints[division][0] = radius * (float)Math.cos(0);
    lowerPoints[division][1] = radius * (float)Math.sin(0); 
    lowerPoints[division][2] = -height / 2; 
    
    updateLowerPolygons(centerPoint, lowerPoints, division);
    updateSidePolygons(topPoint, lowerPoints, division);
  }

  /**
   * 側面のポリゴンを更新します。
   * @param topPoint 上の点
   * @param lowerPoints 底面の周囲の頂点
   */
  private void updateSidePolygons(final float[] topPoint, final float[][] lowerPoints, int division) {
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
