package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/31
 */
public class JoglCylinder implements JoglObject {
  
  /**
   * Field _r
   */
  @XmlAttribute
  protected float _r;
  
  /**
   * Field _height
   */
  @XmlAttribute
  protected float _height;

  /**
   * Field _div
   */
  @XmlAttribute
  protected int _div;
  

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    

    int i;
    double ang;
    double PAI = 3.1415;
    
    float[] blue = new float[] {0.0f, 0.0f, 1.0f, 1.0f};

    gl.glColor4fv(blue, 0);
    
    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f,1.0f,0.0f);
    gl.glVertex3f(0.0f,this._height/2.0f,0.0f);
    for( i=this._div ;i>=0;i--){
      ang = 2.0*PAI/this._div * i;
      gl.glNormal3f(0.0f,1.0f,0.0f);
      gl.glVertex3f(this._r*(float)Math.cos(ang),this._height/2.0f,this._r*(float)Math.sin(ang)); 
    } 
    gl.glEnd();
  //側面を作成
    gl.glBegin(GL.GL_QUAD_STRIP);
    for( i=this._div ;i>=0;i--){
      ang = 2.0*PAI/this._div * i;
      gl.glNormal3f((float)Math.cos(ang),0.0f,(float)Math.sin(ang));
      gl.glVertex3f(this._r*(float)Math.cos(ang),this._height/2.0f,this._r*(float)Math.sin(ang));
      gl.glVertex3f(this._r*(float)Math.cos(ang),-this._height/2.0f,this._r*(float)Math.sin(ang));
    } 
    gl.glEnd();
    
    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f,-1.0f,0.0f);
    gl.glVertex3f(0.0f,-this._height/2.0f,0.0f);
    for( i=0 ;i<=this._div;i++){
      ang = 2.0*PAI/this._div * i;
      gl.glNormal3f(0.0f,-1.0f,0.0f);
      gl.glVertex3f(this._r*(float)Math.cos(ang),-this._height/2.0f,this._r*(float)Math.sin(ang)); 
    } 
    gl.glEnd();
    
    gl.glPopMatrix();
  }


  /**
   * @param div 分割数
   * @param r 半径
   * @param hight 高さ
   */
  public void setSize(int div, float r, float hight) {
    this._div = div;
    this._r = r;
    this._height = hight;
    
  }

}
