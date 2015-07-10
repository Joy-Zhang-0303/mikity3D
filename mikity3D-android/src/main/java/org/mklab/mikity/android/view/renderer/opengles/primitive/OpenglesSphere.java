/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles.primitive;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.android.view.renderer.opengles.AbstractOpenglesObject;
import org.mklab.mikity.util.Vector3;

/**
 * 球を表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class OpenglesSphere extends AbstractOpenglesObject {
  /** 半径。 */
  private float radius = 0;
  /** 分割数。 */
  private int division = 0;
  
  /** ポリゴン。 */
  private OpenglesQuadPolygon[][] polygons;

  /**
   * {@inheritDoc}
   */
  public void display(GL10 gl) {
    applyColor(gl);
    applyTransparency(gl);
    
    for (int i = 0; i < this.division; ++i) {
      for (int j = 0; j < this.division; ++j) {
        this.polygons[i][j].display(gl);
      }
    }
  }
  
  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    if (this.radius == 0 || this.division == 0) {
      return;
    }
    
    final int grid = this.division;
    final float incV = 2 * this.radius / grid;
    final float incH = 360.f / grid;
    
    final float[][][] points = new float[this.division+1][this.division+1][3];
    
    for (int i = 0; i < grid; ++i) {
      final float z = i * incV - this.radius;
      final float r = (float)Math.sqrt(this.radius * this.radius - z * z);
      for (int j = 0; j < grid; ++j) {
        final float theta = (float)(j * incH * Math.PI / 180);
        points[i][j][0] = (float)(r * Math.cos(theta));
        points[i][j][1] = (float)(r * Math.sin(theta));
        points[i][j][2] = z;
      }
      
      points[i][grid][0] = (float)(r * Math.cos(0));
      points[i][grid][1] = (float)(r * Math.sin(0));
      points[i][grid][2] = z;
    }
    
    for (int j = 0; j <= grid; j++) {
      points[grid][j][0] = 0;
      points[grid][j][1] = 0;
      points[grid][j][2] = this.radius;
    }
    
    this.polygons = new OpenglesQuadPolygon[grid][grid];
    
    for (int i = 0; i < grid; ++i) {
      for (int j = 0; j < grid; ++j) {
        this.polygons[i][j] = new OpenglesQuadPolygon();
        final float[][] vertices = new float[4][3];
        vertices[0][0] = points[i][j][0];
        vertices[0][1] = points[i][j][1];
        vertices[0][2] = points[i][j][2];

        vertices[1][0] = points[i][j+1][0];
        vertices[1][1] = points[i][j+1][1];
        vertices[1][2] = points[i][j+1][2];

        vertices[2][0] = points[i+1][j+1][0];
        vertices[2][1] = points[i+1][j+1][1];
        vertices[2][2] = points[i+1][j+1][2];

        vertices[3][0] = points[i+1][j][0];
        vertices[3][1] = points[i+1][j][1];
        vertices[3][2] = points[i+1][j][2];

        final Vector3 v0 = new Vector3(vertices[1][0] - vertices[0][0], vertices[1][1] - vertices[0][1], vertices[1][2] - vertices[0][2]);
        final Vector3 v1 = new Vector3(vertices[2][0] - vertices[0][0], vertices[2][1] - vertices[0][1], vertices[2][2] - vertices[0][2]);
        final Vector3 normalVector = v0.cross(v1);
        
        this.polygons[i][j].setVertices(vertices);
        this.polygons[i][j].setNormalVector(new float[]{normalVector.getX(), normalVector.getY(), normalVector.getZ()});
      }
    }
  }
  /**
   * 半径を設定します。
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