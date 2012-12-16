package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/14
 */
public class JoglDHTranslation implements JoglCoordinate {
  private double _a;
  private double _alpha;
  private double _d;
  private double _theta;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {   
    gl.glTranslated(this._a,0.0,0.0);
    gl.glRotated(this._alpha, 1.0, 0.0, 0.0);
    gl.glTranslated(0.0, 0.0, this._d);
    gl.glRotated(this._theta, 0.0, 0.0, 1.0);
  }
  
  /**
   * @param a a
   * @param alpha α
   * @param d d
   * @param theta θ
   */
  public void setDHtrans(double a,double alpha,double d,double theta){
    this._a = a;
    this._alpha = alpha;
    this._d = d;
    this._theta = theta;
  }
}
