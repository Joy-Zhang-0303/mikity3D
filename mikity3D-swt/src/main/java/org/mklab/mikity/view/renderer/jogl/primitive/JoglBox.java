package org.mklab.mikity.view.renderer.jogl.primitive;

import org.mklab.mikity.view.renderer.jogl.AbstractJoglObject;


/**
 * BOXをJOGLで表したクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class JoglBox extends AbstractJoglObject {

  /** 幅 */
  private float width;

  /** 高さ */
  private float height;

  /** 奥行 */
  private float depth;

//  /**
//   * {@inheritDoc}
//   */
//  public void display(GL2 gl) {
//    applyColor(gl);
//    applyTransparency(gl);
//    
//    drawTrianglePolygons(gl);
//  }

  private void updatePolygons() {
    //   v5 -- v4
    //  /      /
    // v1 -- v0
    //
    //   v6 -- v7
    //  /      /
    // v2 -- v3
    
    float x0 = this.depth / 2;
    float y0 = this.width / 2;
    float z0 = this.height / 2;
    
    float x1 = this.depth / 2;
    float y1 = -this.width / 2;
    float z1 = this.height / 2;

    float x2 = this.depth / 2;
    float y2 = -this.width / 2;
    float z2 = -this.height / 2;
    
    float x3 = this.depth / 2;
    float y3 = this.width / 2;
    float z3 = -this.height / 2;
    
    float x4 = -this.depth / 2;
    float y4 = this.width / 2;
    float z4 = this.height / 2;
    
    float x5 = -this.depth / 2;
    float y5 = -this.width / 2;
    float z5 = this.height / 2;
    
    float x6 = -this.depth / 2;
    float y6 = -this.width / 2;
    float z6 = -this.height / 2;
    
    float x7 = -this.depth / 2;
    float y7 = this.width / 2;
    float z7 = -this.height / 2;

    this.vertexArray = new float[]{
        x0, y0, z0, x4, y4, z4, x1, y1, z1,
        x1, y1, z1, x4, y4, z4, x5, y5, z5, 
        x1, y1, z1, x5, y5, z5, x2, y2, z2,
        x5, y5, z5, x6, y6, z6, x2, y2, z2,
        x2, y2, z2, x6, y6, z6, x3, y3, z3,
        x6, y6, z6, x7, y7, z7, x3, y3, z3,
        x3, y3, z3, x7, y7, z7, x0, y0, z0,
        x7, y7, z7, x4, y4, z4, x0, y0, z0,
        x4, y4, z4, x7, y7, z7, x5, y5, z5,
        x7, y7, z7, x6, y6, z6, x5, y5, z5,
        x0, y0, z0, x1, y1, z1, x3, y3, z3,
        x1, y1, z1, x2, y2, z2, x3, y3, z3};

    this.normalVectorArray = new float[] 
      {0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, 0, 1,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, -1, 0,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 0, -1,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        0, 1, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        -1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0,
        1, 0, 0};
  }

  /**
   * 大きさを設定します。
   * 
   * @param width 幅
   * @param height 高さ
   * @param depth 奥行
   */
  public void setSize(float width, float height, float depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
    
    updatePolygons();
  }

}
