package org.mklab.mikity.model.graphic;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.util.Vector3;

/**
 * 三角形ポリゴンプリミティブを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class TrianglePolygonPrimitive extends AbstractGraphicPrimitive {
  /**
   * 新しく生成された<code>TrianglePolygonObject</code>オブジェクトを初期化します。
   * @param polygon 三角形ポリゴン
   */
  public TrianglePolygonPrimitive(TrianglePolygonModel polygon) {
    super(polygon);
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
    float z1 = vertices.get(1).getZ();
    
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
}
