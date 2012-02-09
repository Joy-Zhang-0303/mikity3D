package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.jogl.JoglObject;
import org.mklab.mikity.xml.model.Location;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTriangleObject implements JoglObject {

//  @XmlElement
//  private Location[] _point = new Location[3];

  private float[][] _point = new float[3][3];

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    float x ,y,z;
    
    float[] red = new float[] {1.0f, 0.0f, 0.0f, 1.0f};

    gl.glColor4fv(red, 0);
    gl.glBegin(GL.GL_TRIANGLES);
    for (int i = 0; i < 3; i++) {
      x = this._point[i][0];
      y = this._point[i][1];
      z = this._point[i][2];
      gl.glVertex3f(x, y, z);
    }
    gl.glEnd();
    gl.glPopMatrix();
  }

  /**
   * @param point
   */
  public void setSize(float[][] point) {
    
    this._point = point;

  }

}
