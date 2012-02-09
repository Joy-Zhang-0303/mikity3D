package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.JoglObject;

import com.sun.opengl.util.GLUT;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/19
 */
public class CubeJoglObject implements JoglObject {

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    GLUT glut = new GLUT();

    gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
    glut.glutSolidCube(1.0f);
  }


}
