package org.mklab.mikity.model.graphic;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.util.Vector3;

/**
 * 三角形ポリゴンを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class TrianglePolygonObject extends GraphicPrimitive {
//  /** 頂点 (v0, v1, v2)(反時計回り) */
//  private float[][] vertices = new float[3][3];
//  
//  /** 法線ベクトル */
//  private float[] normalVector = new float[3];
  
  /**
   * 新しく生成された<code>TrianglePolygonObject</code>オブジェクトを初期化します。
   * @param triangle 三角形ポリゴン
   */
  public TrianglePolygonObject(TrianglePolygonModel triangle) {
    super(triangle);
    updatePolygons();
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    List<VertexModel> vertices = ((TrianglePolygonModel)this.primitive).getVerties();
    Vector3 normalVector = ((TrianglePolygonModel)this.primitive).getNormalVector();
    
    if (vertices == null || normalVector == null) {
      return;
    }

    final int polygonNumber = 1;
    initializeArrays(polygonNumber);
    
    float x0 = vertices.get(0).getX();
    float y0 = vertices.get(0).getY();
    float z0 = vertices.get(0).getZ();
    
    float x1 = vertices.get(1).getX();
    float y1 = vertices.get(1).getY();
    float z1 = vertices.get(1).getY();
    
    float x2 = vertices.get(2).getX();
    float y2 = vertices.get(2).getY();
    float z2 = vertices.get(2).getZ();
   
    final float[][] vertices2 = new float[][]
        {{x0, y0, z0}, {x1, y1, z1}, {x2, y2, z2}};

    final float nx = normalVector.getX();
    final float ny = normalVector.getY();
    final float nz = normalVector.getZ();
    
    final float[][] normalVector2 = new float[][]
        {{nx,ny,nz},{nx,ny,nz},{nx,ny,nz}};
   
    appendVertices(vertices2);
    appendNormalVector(normalVector2);
  }

//  /**
//   * 3個の頂点を設定します。
//   * @param vertices 3個の頂点
//   */
//  public void setVertices(float[][] vertices) {
//    this.vertices = vertices;
//    updatePolygons();
//  }
//  
//  /**
//   * 法線ベクトルを設定します。
//   * @param normalVector 法線ベクトル
//   */
//  public void setNormalVector(float[] normalVector) {
//    this.normalVector = normalVector;
//    updatePolygons();
//  }

}
