package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.util.Vector3;


/**
 *　カプセルプリミティブを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class CapsulePrimitive extends AbstractGraphicPrimitive {
  /**
   * 新しく生成された<code>CylinderObject</code>オブジェクトを初期化します。
   * @param cylinder シリンダー
   */
  public CapsulePrimitive(CapsuleModel capsule) {
    super(capsule);
    updatePolygons();
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    float radius = ((CapsuleModel)this.primitive).getRadius();
    float height = ((CapsuleModel)this.primitive).getHeight();
    int division = ((CapsuleModel)this.primitive).getDivision();
    
    if (radius == 0 || height == 0 || division == 0) {
      return;
    }

    final int upperPolygonNumber = division;
    final int lowerPolygonNumber = division;
    final int sidePolygonNumber = division*2;
    final int spherePolygonNumber = division*division*2;
    final int polygonNumber = upperPolygonNumber + lowerPolygonNumber + sidePolygonNumber + spherePolygonNumber;
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
    
    //-------------------------------------------------------
    final float incV = 2 * radius / division;
    final float incH = 360.f / division;
    
    final float[][][] upperSpherePoints = new float[division/2 + 1][division + 1][3];
    
    for (int i = division/2 ; i >= 0; --i) {
      final float z = (i) * incV + (height/2);
      final float y = i * incV;
      final float r = (float)Math.sqrt(radius * radius - y * y);
      for (int j = division; j >= 0; --j) {
        final float theta = (float)(j * incH * Math.PI / 180);
        upperSpherePoints[i][j][0] = (float)(r * Math.cos(theta));
        upperSpherePoints[i][j][1] = (float)(r * Math.sin(theta));
        upperSpherePoints[i][j][2] = z;
      }
      
      upperSpherePoints[i][division-1][0] = (float)(r * Math.cos(0));
      upperSpherePoints[i][division-1][1] = (float)(r * Math.sin(0));
      upperSpherePoints[i][division-1][2] = z;
    }
    

    final float[][][] lowerSpherePoints = new float[division/2 + 1][division + 1][3];
    
    for (int i = 0; i < division/2; i++) {
      final float z1 = (i+1) * incV - radius - (height/2);
      final float y1 = i * incV - radius;
      final float r1 = (float)Math.sqrt(radius * radius - y1 * y1);
      for (int j = 0; j < division; ++j) {
        final float theta1 = (float)(j * incH * Math.PI / 180);
        lowerSpherePoints[i][j][0] = (float)(r1 * Math.cos(theta1));
        lowerSpherePoints[i][j][1] = (float)(r1 * Math.sin(theta1));
        lowerSpherePoints[i][j][2] = z1;
      }
      
      lowerSpherePoints[i][division-1][0] = (float)(r1 * Math.cos(0));
      lowerSpherePoints[i][division-1][1] = (float)(r1 * Math.sin(0));
      lowerSpherePoints[i][division-1][2] = z1;
    }
    

    updateUpperPolygons(upperCenterPoint, upperPoints, division);
    updateLowerPolygons(lowerCenterPoint, lowerPoints, division);
    updateSidePolygons(upperPoints, lowerPoints, division);
    updateLowerRightPolygons1(upperSpherePoints, division);
    updateUpperLeftPolygons1(upperSpherePoints, division); 
    updateLowerRightPolygons2(lowerSpherePoints, division);
    updateUpperLeftPolygons2(lowerSpherePoints, division);
  }
  
  //=======================================================================================================
  
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

  //-----------------------------------------------------------------------------
/**
 * 四角形セグメントの左上のポリゴンを更新します。
 * 
 * @param points 球面上の格子点
 */
private void updateUpperLeftPolygons1(final float[][][] upperSpherePoints, int division) {
  for (int i = 0; i < division/2 - 1; ++i) {
    for (int j = 0; j < division - 1; ++j) {
      final float[][] vertices = new float[3][3];
      vertices[0][0] = upperSpherePoints[i][j][0];
      vertices[0][1] = upperSpherePoints[i][j][1];
      vertices[0][2] = upperSpherePoints[i][j][2];

      vertices[1][0] = upperSpherePoints[i+1][j+1][0];
      vertices[1][1] = upperSpherePoints[i+1][j+1][1];
      vertices[1][2] = upperSpherePoints[i+1][j+1][2];

      vertices[2][0] = upperSpherePoints[i+1][j][0];
      vertices[2][1] = upperSpherePoints[i+1][j][1];
      vertices[2][2] = upperSpherePoints[i+1][j][2];

      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();
      
      appendVertices(vertices);
      appendNormalVectorsOfTriangle(normalVector);
    }
  }
}
private void updateUpperLeftPolygons2(final float[][][] lowerSpherePoints, int division) {
	  for (int i = 0; i < division/2 -1; ++i) {
	    for (int j = 0; j < division -1; ++j) {
	      final float[][] vertices = new float[3][3];
	      vertices[0][0] = lowerSpherePoints[i][j][0];
	      vertices[0][1] = lowerSpherePoints[i][j][1];
	      vertices[0][2] = lowerSpherePoints[i][j][2];

	      vertices[1][0] = lowerSpherePoints[i+1][j+1][0];
	      vertices[1][1] = lowerSpherePoints[i+1][j+1][1];
	      vertices[1][2] = lowerSpherePoints[i+1][j+1][2];

	      vertices[2][0] = lowerSpherePoints[i+1][j][0];
	      vertices[2][1] = lowerSpherePoints[i+1][j][1];
	      vertices[2][2] = lowerSpherePoints[i+1][j][2];

	      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
	      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
	      final float[] normalVector = v0.cross(v1).array();
	      
	      appendVertices(vertices);
	      appendNormalVectorsOfTriangle(normalVector);
	    }
	  }
	}

//-------------------------------------------------------------------------------------------
/**
 * 四角形セグメントの右下のポリゴンを更新します。
 * 
 * @param points 球面上の格子点
 */
private void updateLowerRightPolygons1(final float[][][] upperSpherePoints, int division) {
  for (int i = 0; i < division/2; ++i) {
    for (int j = 0; j < division -1; ++j) {
      final float[][] vertices = new float[3][3];
      vertices[0][0] = upperSpherePoints[i][j][0];
      vertices[0][1] = upperSpherePoints[i][j][1];
      vertices[0][2] = upperSpherePoints[i][j][2];

      vertices[1][0] = upperSpherePoints[i][j+1][0];
      vertices[1][1] = upperSpherePoints[i][j+1][1];
      vertices[1][2] = upperSpherePoints[i][j+1][2];

      vertices[2][0] = upperSpherePoints[i+1][j+1][0];
      vertices[2][1] = upperSpherePoints[i+1][j+1][1];
      vertices[2][2] = upperSpherePoints[i+1][j+1][2];

      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
      final float[] normalVector = v0.cross(v1).array();
      appendVertices(vertices);
      appendNormalVectorsOfTriangle(normalVector);
    }
  }
}

private void updateLowerRightPolygons2(final float[][][] lowerSpherePoints, int division) {
	  for (int i = 0; i < division/2 -1; ++i) {
	    for (int j = 0; j < division -1; ++j) {
	      final float[][] vertices = new float[3][3];
	      vertices[0][0] = lowerSpherePoints[i][j][0];
	      vertices[0][1] = lowerSpherePoints[i][j][1];
	      vertices[0][2] = lowerSpherePoints[i][j][2];

	      vertices[1][0] = lowerSpherePoints[i][j+1][0];
	      vertices[1][1] = lowerSpherePoints[i][j+1][1];
	      vertices[1][2] = lowerSpherePoints[i][j+1][2];

	      vertices[2][0] = lowerSpherePoints[i+1][j+1][0];
	      vertices[2][1] = lowerSpherePoints[i+1][j+1][1];
	      vertices[2][2] = lowerSpherePoints[i+1][j+1][2];

	      final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
	      final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
	      final float[] normalVector = v0.cross(v1).array();
	      appendVertices(vertices);
	      appendNormalVectorsOfTriangle(normalVector);
	    }
	  }
	}
}


