package org.mklab.mikity.model.graphic;

import org.mklab.mikity.util.Vector3;

/**
 * 球をJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class SphereObject extends GraphicObject {
  /** 半径。 */
  private float radius = 0;

  /** 分割数。 */
  private int division = 0;

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    if (this.radius == 0 || this.division == 0) {
      return;
    }
    
    final int polygonNumber = this.division*this.division*2;
    initializeArrays(polygonNumber);
    
    final float incV = 2 * this.radius / this.division;
    final float incH = 360.f / this.division;
    
    final float[][][] points = new float[this.division+1][this.division+1][3];
    
    for (int i = 0; i < this.division; ++i) {
      final float z = i * incV - this.radius;
      final float r = (float)Math.sqrt(this.radius * this.radius - z * z);
      for (int j = 0; j < this.division; ++j) {
        final float theta = (float)(j * incH * Math.PI / 180);
        points[i][j][0] = (float)(r * Math.cos(theta));
        points[i][j][1] = (float)(r * Math.sin(theta));
        points[i][j][2] = z;
      }
      
      points[i][this.division][0] = (float)(r * Math.cos(0));
      points[i][this.division][1] = (float)(r * Math.sin(0));
      points[i][this.division][2] = z;
    }
    
    for (int j = 0; j <= this.division; j++) {
      points[this.division][j][0] = 0;
      points[this.division][j][1] = 0;
      points[this.division][j][2] = this.radius;
    }
       
    updateLowerRightPolygons(points);
    updateUpperLeftPolygons(points);
  }

  /**
   * 四角形セグメントの左上のポリゴンを更新します。
   * 
   * @param points 球面上の格子点
   */
  private void updateUpperLeftPolygons(final float[][][] points) {
    for (int i = 0; i < this.division; ++i) {
      for (int j = 0; j < this.division; ++j) {
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
  private void updateLowerRightPolygons(final float[][][] points) {
    for (int i = 0; i < this.division; ++i) {
      for (int j = 0; j < this.division; ++j) {
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

  /**
   * 大きさを設定します。
   * 
   * @param radius 半径
   */
  public void setSize(float radius) {
    this.radius = radius;
    updatePolygons();
  }
  
  /**
   * 分割数を設定します。
   * @param division 分割数
   */
  public void setDivision(int division) {
    this.division = division;
    updatePolygons();
  }

}
