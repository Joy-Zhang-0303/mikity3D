package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class JoglTriangleObject implements JoglObject {

  //  @XmlElement
  //  private Location[] _point = new Location[3];

  @XmlAttribute
  private String _color;

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

  private float[][] _point = new float[3][3];

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    float x, y, z;

    if (this._color != null) {
      if (this._color == "white") {
        gl.glColor4fv(this.white, 0);
      } else if (this._color == "lightGray") {
        gl.glColor4fv(this.lightGray, 0);
      } else if (this._color == "gray") {
        gl.glColor4fv(this.gray, 0);
      } else if (this._color == "darkGray") {
        gl.glColor4fv(this.darkGray, 0);
      } else if (this._color == "black") {
        gl.glColor4fv(this.black, 0);
      } else if (this._color == "red") {
        gl.glColor4fv(this.red, 0);
      } else if (this._color == "pink") {
        gl.glColor4fv(this.pink, 0);
      } else if (this._color == "orange") {
        gl.glColor4fv(this.orange, 0);
      } else if (this._color == "yellow") {
        gl.glColor4fv(this.yellow, 0);
      } else if (this._color == "green") {
        gl.glColor4fv(this.green, 0);
      } else if (this._color == "magenta") {
        gl.glColor4fv(this.magenta, 0);
      } else if (this._color == "cyan") {
        gl.glColor4fv(this.cyan, 0);
      } else if (this._color == "blue") {
        gl.glColor4fv(this.blue, 0);
      }
    }

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
   * @param point 座標
   */
  public void setSize(float[][] point) {

    this._point = point;

  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

}
