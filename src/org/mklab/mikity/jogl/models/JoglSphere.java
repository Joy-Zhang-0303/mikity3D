package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;

import com.sun.opengl.util.GLUT;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglSphere implements JoglObject {

  /**
   * Field _r
   */
  @XmlAttribute
  private float _r;

  /**
   * Field _div
   */
  @XmlAttribute
  private int _div;

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    GLUT glut = new GLUT();
    float[] yellow = new float[] {1.0f, 1.0f, 0.0f, 1.0f};
    gl.glColor4fv(yellow, 0);
    glut.glutSolidSphere(this._r, this._div, this._div);

    gl.glPopMatrix();
    gl.glPopMatrix();

  }

  /**
   * @param div 分割数
   * @param r 半径
   */
  public void setSize(int div, float r) {

    this._div = div;
    this._r = r;

  }

}
