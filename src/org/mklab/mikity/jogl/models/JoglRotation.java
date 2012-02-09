package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/08
 */
public class JoglRotation implements JoglCoordinate {

  /**
   * Field _xrotate
   */
  @XmlAttribute
  private float _xrotate;

  /**
   * Field _yrotate
   */
  @XmlAttribute
  private float _yrotate;

  /**
   * Field _zrotate
   */
  @XmlAttribute
  private float _zrotate;
  
  /**
   * @see org.mklab.mikity.jogl.models.JoglCoordinate#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    gl.glPushMatrix();
    gl.glRotatef(this._xrotate, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(this._yrotate, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(this._zrotate, 0.0f, 0.0f, 1.0f);

  }
  
  /**
   * @param x
   * @param y
   * @param z
   */
  public void setLocation(float x,float y,float z){
    
    this._xrotate = x;
    this._yrotate = y;
    this._zrotate = z;
    
  }

}
