package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.util.Vector3;

/**
 * 球を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class SphereObject extends GraphicPrimitive {
  /**
   * 新しく生成された<code>SphereObject</code>オブジェクトを初期化します。
   * @param sphere 球
   */
  public SphereObject(SphereModel sphere) {
    super(sphere);
    updatePolygons();
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    float radius = ((SphereModel)this.primitive).getRadius();
    int division = ((SphereModel)this.primitive).getDivision();
    
    if (radius == 0 || division == 0) {
      return;
    }
    
    final int polygonNumber = division*division*2;
    initializeArrays(polygonNumber);
    
    final float incV = 2 * radius / division;
    final float incH = 360.f / division;
    
    final float[][][] points = new float[division+1][division+1][3];
    
    for (int i = 0; i < division; ++i) {
      final float z = i * incV - radius;
      final float r = (float)Math.sqrt(radius * radius - z * z);
      for (int j = 0; j < division; ++j) {
        final float theta = (float)(j * incH * Math.PI / 180);
        points[i][j][0] = (float)(r * Math.cos(theta));
        points[i][j][1] = (float)(r * Math.sin(theta));
        points[i][j][2] = z;
      }
      
      points[i][division][0] = (float)(r * Math.cos(0));
      points[i][division][1] = (float)(r * Math.sin(0));
      points[i][division][2] = z;
    }
    
    for (int j = 0; j <= division; j++) {
      points[division][j][0] = 0;
      points[division][j][1] = 0;
      points[division][j][2] = radius;
    }
       
    updateLowerRightPolygons(points, division);
    updateUpperLeftPolygons(points, division);
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
