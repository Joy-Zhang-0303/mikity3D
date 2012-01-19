package org.mklab.mikity.jogl;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/19
 */
public class CubeJoglObject implements JoglObject {

  /**
   * @see org.mklab.mikity.jogl.JoglObject#display(javax.media.opengl.GL)
   */
  @Override
  public void display(GL gl) {

    GLUT glut = new GLUT();

    gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
    glut.glutSolidCube(1.0f);
  }
}
