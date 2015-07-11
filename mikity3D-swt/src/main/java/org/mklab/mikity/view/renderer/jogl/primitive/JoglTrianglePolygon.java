package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * 三角形ポリゴンをJOGLで表したクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTrianglePolygon extends AbstractJoglObject {
  /** 頂点 (v0, v1, v2)(反時計回り) */
  private float[][] vertices = new float[3][3];
  
  /** 法線ベクトル */
  private float[] normalVector = new float[3];

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    if (this.vertices == null || this.normalVector == null) {
      return;
    }

    final int polygonNumber = 1;
    initializeArrays(polygonNumber);
    
    float x0 = this.vertices[0][0];
    float y0 = this.vertices[0][1];
    float z0 = this.vertices[0][2];
    
    float x1 = this.vertices[1][0];
    float y1 = this.vertices[1][1];
    float z1 = this.vertices[1][2];
    
    float x2 = this.vertices[2][0];
    float y2 = this.vertices[2][1];
    float z2 = this.vertices[2][2];

    final float nx = this.normalVector[0];
    final float ny = this.normalVector[1];
    final float nz = this.normalVector[2];
    
    final float[][] vertices2 = new float[][]
        {{x0, y0, z0}, {x1, y1, z1}, {x2, y2, z2}};
    
    final float[][] normalVector2 = new float[][]
        {{nx,ny,nz},{nx,ny,nz},{nx,ny,nz}};
   
    appendVertices(vertices2);
    appendNormalVector(normalVector2);
  }

  /**
   * 3個の頂点を設定します。
   * @param vertices 3個の頂点
   */
  public void setVertices(float[][] vertices) {
    this.vertices = vertices;
    updatePolygons();
  }
  
  /**
   * 法線ベクトルを設定します。
   * @param normalVector 法線ベクトル
   */
  public void setNormalVector(float[] normalVector) {
    this.normalVector = normalVector;
    updatePolygons();
  }

}
