package org.mklab.mikity.model.graphic;

import org.mklab.mikity.model.xml.simplexml.model.AbstractPrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;

/**
 * BOXを表わすクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class BoxPrimitive extends AbstractGraphicPrimitive {
  /**
   * 新しく生成された<code>BoxObject</code>オブジェクトを初期化します。
   * @param box モデル
   */
  public BoxPrimitive(AbstractPrimitiveModel box) {
    super(box);
    updatePolygons();
  }
  
  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    final int polygonNumber = 6*3;
    initializeArrays(polygonNumber);
    
    //   v5 -- v4
    //  /      /
    // v1 -- v0
    //
    //   v6 -- v7
    //  /      /
    // v2 -- v3
    
    float width = ((BoxModel)this.primitive).getWidth();
    float height = ((BoxModel)this.primitive).getHeight();
    float depth = ((BoxModel)this.primitive).getDepth();
    
    float x0 = depth / 2;
    float y0 = width / 2;
    float z0 = height / 2;
    
    float x1 = depth / 2;
    float y1 = -width / 2;
    float z1 = height / 2;

    float x2 = depth / 2;
    float y2 = -width / 2;
    float z2 = -height / 2;
    
    float x3 = depth / 2;
    float y3 = width / 2;
    float z3 = -height / 2;
    
    float x4 = -depth / 2;
    float y4 = width / 2;
    float z4 = height / 2;
    
    float x5 = -depth / 2;
    float y5 = -width / 2;
    float z5 = height / 2;
    
    float x6 = -depth / 2;
    float y6 = -width / 2;
    float z6 = -height / 2;
    
    float x7 = -depth / 2;
    float y7 = width / 2;
    float z7 = -height / 2;  

    final float[][] vertices = new float[][]{
        {x0, y0, z0}, {x4, y4, z4}, {x1, y1, z1},
        {x1, y1, z1}, {x4, y4, z4}, {x5, y5, z5}, 
        {x1, y1, z1}, {x5, y5, z5}, {x2, y2, z2},
        {x5, y5, z5}, {x6, y6, z6}, {x2, y2, z2},
        {x2, y2, z2}, {x6, y6, z6}, {x3, y3, z3},
        {x6, y6, z6}, {x7, y7, z7}, {x3, y3, z3},
        {x3, y3, z3}, {x7, y7, z7}, {x0, y0, z0},
        {x7, y7, z7}, {x4, y4, z4}, {x0, y0, z0},
        {x4, y4, z4}, {x7, y7, z7}, {x5, y5, z5},
        {x7, y7, z7}, {x6, y6, z6}, {x5, y5, z5},
        {x0, y0, z0}, {x1, y1, z1}, {x3, y3, z3},
        {x1, y1, z1}, {x2, y2, z2}, {x3, y3, z3}};

    final float[][] normalVector = new float[][] 
        {{0, 0, 1},
          {0, 0, 1},
          {0, 0, 1},
          {0, 0, 1},
          {0, 0, 1},
          {0, 0, 1},
          {0, -1, 0},
          {0, -1, 0},
          {0, -1, 0},
          {0, -1, 0},
          {0, -1, 0},
          {0, -1, 0},
          {0, 0, -1},
          {0, 0, -1},
          {0, 0, -1},
          {0, 0, -1},
          {0, 0, -1},
          {0, 0, -1},
          {0, 1, 0},
          {0, 1, 0},
          {0, 1, 0},
          {0, 1, 0},
          {0, 1, 0},
          {0, 1, 0},
          {-1, 0, 0},
          {-1, 0, 0},
          {-1, 0, 0},
          {-1, 0, 0},
          {-1, 0, 0},
          {-1, 0, 0},
          {1, 0, 0},
          {1, 0, 0},
          {1, 0, 0},
          {1, 0, 0},
          {1, 0, 0},
          {1, 0, 0}};
    
    appendVertices(vertices);
    appendNormalVector(normalVector);
  }
}
