package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.JoglObject;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglQuadObject implements JoglObject {

  private float[][] _point = new float[4][3];
  
  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    float x ,y,z;
    
    float[] blue = new float[] {0.0f, 0.0f, 1.0f, 1.0f};

    gl.glColor4fv(blue, 0);
    gl.glBegin(GL.GL_QUADS);
    for (int i = 0; i < 4; i++) {
      x = this._point[i][0];
      y = this._point[i][1];
      z = this._point[i][2];
      gl.glVertex3f(x, y, z);
    }
    gl.glEnd();
  }
  
  /**
   * @param point
   */
  public void setSize(float[][] point) {
    
    this._point = point;

  }

}
