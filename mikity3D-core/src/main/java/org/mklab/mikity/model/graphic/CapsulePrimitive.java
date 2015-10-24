package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.util.Vector3;


/**
 * 　カプセルプリミティブを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class CapsulePrimitive extends AbstractGraphicPrimitive {

  /**
   * 新しく生成された<code>CylinderObject</code>オブジェクトを初期化します。
   * 
   * @param capsule カプセル
   */
  public CapsulePrimitive(CapsuleModel capsule) {
    super(capsule);
    updatePolygons();
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    final float radius = ((CapsuleModel)this.primitive).getRadius();
    final float height = ((CapsuleModel)this.primitive).getHeight();
    final int division = ((CapsuleModel)this.primitive).getDivision();

    if (radius == 0 || height == 0 || division == 0) {
      return;
    }

    final float cylinderHeight = height - 2*radius;
    
    final float[][] upperPoints = new float[division + 1][3];
    for (int i = 0; i < division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      upperPoints[i][0] = (float)(radius * Math.cos(theta));
      upperPoints[i][1] = (float)(radius * Math.sin(theta));
      upperPoints[i][2] = cylinderHeight / 2.0f;
    }
    upperPoints[division][0] = (float)(radius * Math.cos(0));
    upperPoints[division][1] = (float)(radius * Math.sin(0));
    upperPoints[division][2] = cylinderHeight / 2.0f;

    final float[][] lowerPoints = new float[division + 1][3];
    for (int i = 0; i < division; i++) {
      final double theta = 2.0 * Math.PI / division * i;
      lowerPoints[i][0] = (float)(radius * Math.cos(theta));
      lowerPoints[i][1] = (float)(radius * Math.sin(theta));
      lowerPoints[i][2] = -cylinderHeight / 2.0f;
    }
    lowerPoints[division][0] = (float)(radius * Math.cos(0));
    lowerPoints[division][1] = (float)(radius * Math.sin(0));
    lowerPoints[division][2] = -cylinderHeight / 2.0f;

    final float[][][] upperSpherePoints = createSpherePoints(radius, division, cylinderHeight/2);
    final float[][][] lowerSpherePoints = createSpherePoints(radius, division, -cylinderHeight/2);

    final int sidePolygonNumber = (upperPoints.length-1) + (lowerPoints.length-1);
    final int upperSpherePolygonNumber = (upperSpherePoints.length-1)*(upperSpherePoints[0].length-1)*2;
    final int lowerSpherePolygonNumber = (lowerSpherePoints.length-1)*(lowerSpherePoints[0].length-1)*2;
    final int polygonNumber = sidePolygonNumber + upperSpherePolygonNumber + lowerSpherePolygonNumber;
    initializeArrays(polygonNumber);
    
    updateSidePolygons(upperPoints, lowerPoints, division);
    updateHalfSpherePolygons(upperSpherePoints, division);
    updateHalfSpherePolygons(lowerSpherePoints, division);
  }

  /**
   * 半球のポリゴンを生成します。
   * 
   * @param halfSpherePoints 半球上の点
   * @param division 分割数
   */
  private void updateHalfSpherePolygons(final float[][][] halfSpherePoints, final int division) {
    updateLowerRightPolygons(halfSpherePoints, division);
    updateUpperLeftPolygons(halfSpherePoints, division);
  }

  /**
   * 球上の点を生成します。
   * 
   * @param radius 半径
   * @param division 分割数
   * @param zOffset Z軸方向のオフセット
   * @return 球上の点
   */
  private float[][][] createSpherePoints(float radius, int division, float zOffset) {
    final float[][][] spherePoints = new float[division+1][division+1][3];

    final float incV = 2 * radius / division;
    final float incH = 360.f / division;

    for (int i = 0; i < division; ++i) {
      final float z = i * incV - radius;
      final float r = (float)Math.sqrt(radius * radius - z * z);
      for (int j = 0; j < division; ++j) {
        final float theta = (float)(j * incH * Math.PI / 180);
        spherePoints[i][j][0] = (float)(r * Math.cos(theta));
        spherePoints[i][j][1] = (float)(r * Math.sin(theta));
        spherePoints[i][j][2] = z + zOffset;
      }
      
      spherePoints[i][division][0] = (float)(r * Math.cos(0));
      spherePoints[i][division][1] = (float)(r * Math.sin(0));
      spherePoints[i][division][2] = z + zOffset;
    }
    
    for (int j = 0; j <= division; j++) {
      spherePoints[division][j][0] = 0;
      spherePoints[division][j][1] = 0;
      spherePoints[division][j][2] = radius + zOffset;
    }
    return spherePoints;
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

      vertices[2][0] = lowerPoints[i + 1][0];
      vertices[2][1] = lowerPoints[i + 1][1];
      vertices[2][2] = lowerPoints[i + 1][2];

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

      vertices[1][0] = lowerPoints[i + 1][0];
      vertices[1][1] = lowerPoints[i + 1][1];
      vertices[1][2] = lowerPoints[i + 1][2];

      vertices[2][0] = upperPoints[i + 1][0];
      vertices[2][1] = upperPoints[i + 1][1];
      vertices[2][2] = upperPoints[i + 1][2];

      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();

      appendVertices(vertices);
      appendNormalVectorsOfTriangle(normalVector);
    }
  }

  /**
   * 四角形セグメントの左上のポリゴンを更新します。
   * 
   * @param points 球面上の格子点
   */
  private void updateUpperLeftPolygons(final float[][][] points, int division) {
    for (int i = 0; i < division; ++i) {
      for (int j = 0; j < division; ++j) {
        final float[][] vertices = new float[3][3];
        vertices[0][0] = points[i][j][0];
        vertices[0][1] = points[i][j][1];
        vertices[0][2] = points[i][j][2];

        vertices[1][0] = points[i+1][j+1][0];
        vertices[1][1] = points[i+1][j+1][1];
        vertices[1][2] = points[i+1][j+1][2];

        vertices[2][0] = points[i+1][j][0];
        vertices[2][1] = points[i+1][j][1];
        vertices[2][2] = points[i+1][j][2];

        final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
        final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
        final float[] normalVector = v0.cross(v1).array();
        
        appendVertices(vertices);
        appendNormalVectorsOfTriangle(normalVector);
      }
    }
  }
  
  /**
   * 四角形セグメントの右下のポリゴンを更新します。
   * 
   * @param points 球面上の格子点
   */
  private void updateLowerRightPolygons(final float[][][] points, int division) {
    for (int i = 0; i < division; ++i) {
      for (int j = 0; j < division; ++j) {
        final float[][] vertices = new float[3][3];
        vertices[0][0] = points[i][j][0];
        vertices[0][1] = points[i][j][1];
        vertices[0][2] = points[i][j][2];

        vertices[1][0] = points[i][j+1][0];
        vertices[1][1] = points[i][j+1][1];
        vertices[1][2] = points[i][j+1][2];

        vertices[2][0] = points[i+1][j+1][0];
        vertices[2][1] = points[i+1][j+1][1];
        vertices[2][2] = points[i+1][j+1][2];

        final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
        final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
        final float[] normalVector = v0.cross(v1).array();
  
        appendVertices(vertices);
        appendNormalVectorsOfTriangle(normalVector);
      }
    }
  }
}
