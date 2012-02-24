package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglCone implements JoglObject {

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

  /** */
  protected String _color;

  /** */
  float[] white = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
  /** */
  float[] lightGray = new float[] {0.75f, 0.75f, 0.75f, 1.0f};
  /** */
  float[] gray = new float[] {0.5f, 0.5f, 0.5f, 1.0f};
  /** */
  float[] darkGray = new float[] {0.25f, 0.25f, 0.25f, 1.0f};
  /** */
  float[] black = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
  /** */
  float[] red = new float[] {1.0f, 0.0f, 0.0f, 1.0f};
  /** */
  float[] pink = new float[] {1.0f, 0.69f, 0.69f, 1.0f};
  /** */
  float[] orange = new float[] {1.0f, 0.78f, 0.0f, 1.0f};
  /** */
  float[] yellow = new float[] {1.0f, 1.0f, 0.0f, 1.0f};
  /** */
  float[] green = new float[] {0.0f, 1.0f, 0.0f, 1.0f};
  /** */
  float[] magenta = new float[] {1.0f, 0.0f, 1.0f, 1.0f};
  /** */
  float[] cyan = new float[] {0.0f, 1.0f, 1.0f, 1.0f};
  /** */
  float[] blue = new float[] {0.0f, 0.0f, 1.0f, 1.0f};

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    int i;
    double ang;
    double PAI = 3.1415;

    if (this._color != null) {
      if (this._color.equals("white")) {
        gl.glColor4fv(this.white, 0);
      } else if (this._color.equals("lightGray")) {
        gl.glColor4fv(this.lightGray, 0);
      } else if (this._color.equals("gray")) {
        gl.glColor4fv(this.gray, 0);
      } else if (this._color.equals("darkGray")) {
        gl.glColor4fv(this.darkGray, 0);
      } else if (this._color.equals("black")) {
        gl.glColor4fv(this.black, 0);
      } else if (this._color.equals("red")) {
        gl.glColor4fv(this.red, 0);
      } else if (this._color.equals("pink")) {
        gl.glColor4fv(this.pink, 0);
      } else if (this._color.equals("orange")) {
        gl.glColor4fv(this.orange, 0);
      } else if (this._color.equals("yellow")) {
        gl.glColor4fv(this.yellow, 0);
      } else if (this._color.equals("green")) {
        gl.glColor4fv(this.green, 0);
      } else if (this._color.equals("magenta")) {
        gl.glColor4fv(this.magenta, 0);
      } else if (this._color.equals("cyan")) {
        gl.glColor4fv(this.cyan, 0);
      } else if (this._color.equals("blue")) {
        gl.glColor4fv(this.blue, 0);
      }
    }

    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, 1.0f, 0.0f);
    gl.glVertex3f(0.0f, this._height / 2.0f, 0.0f);
    for (i = this._div; i >= 0; i--) {
      ang = 2.0 * PAI / this._div * i;
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      gl.glVertex3f(this._r * (float)Math.cos(ang), -this._height / 2.0f, this._r * (float)Math.sin(ang));
    }
    gl.glEnd();

    gl.glBegin(GL.GL_TRIANGLE_FAN);
    gl.glNormal3f(0.0f, -1.0f, 0.0f);
    gl.glVertex3f(0.0f, -this._height / 2.0f, 0.0f);
    for (i = 0; i <= this._div; i++) {
      ang = 2.0 * PAI / this._div * i;
      gl.glNormal3f(0.0f, -1.0f, 0.0f);
      gl.glVertex3f(this._r * (float)Math.cos(ang), -this._height / 2.0f, this._r * (float)Math.sin(ang));
    }
    gl.glEnd();

    gl.glPopMatrix();
  }

  /**
   * @param div 分割数
   * @param r 半径
   * @param hight 高さ
   * @param color 色
   */
  public void setSize(int div, float r, float hight) {
    this._div = div;
    this._r = r;
    this._height = hight;

  }

  /**
   * @param color
   */
  public void setColor(String color) {
    this._color = color;
  }

}
