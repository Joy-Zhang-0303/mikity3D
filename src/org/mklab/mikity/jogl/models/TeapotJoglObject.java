package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.JoglObject;

import com.sun.opengl.util.GLUT;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/13
 */
public class TeapotJoglObject implements JoglObject {

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    GLUT glut = new GLUT();
    float[] red = new float[] {1.0f, 0.0f, 0.0f, 1.0f};

    //    gl.glTranslatef(0.0f, 0.5f, 0.0f);
    gl.glColor4fv(red, 0);
    glut.glutSolidTeapot(1.0f);
    //    gl.glTranslatef(0.0f, -0.875f, 0.0f);
    //    glut.glutSolidCube(1.0f);
  }

}
