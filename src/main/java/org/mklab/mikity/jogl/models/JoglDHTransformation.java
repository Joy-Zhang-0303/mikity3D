package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/14
 */
public class JoglDHTransformation implements JoglCoordinate {
  /** a */
  private double a;
  /** alpha */
  private double alpha;
  /** d */
  private double d;
  /** theta */
  private double theta;

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    gl.glTranslated(this.a, 0.0, 0.0);
    gl.glRotated(this.alpha, 1.0, 0.0, 0.0);
    gl.glTranslated(0.0, 0.0, this.d);
    gl.glRotated(this.theta, 0.0, 0.0, 1.0);
  }

  /**
   * @param a a
   * @param alpha α
   * @param d d
   * @param theta θ
   */
  public void setDHParameters(double a, double alpha, double d, double theta) {
    this.a = a;
    this.alpha = alpha;
    this.d = d;
    this.theta = theta;
  }
}
