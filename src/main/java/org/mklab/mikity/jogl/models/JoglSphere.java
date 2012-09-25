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
    GLUT glut = new GLUT();

    if (this._color != null) {
      if (this._color.equals("white")) { //$NON-NLS-1$
        gl.glColor4fv(this.white, 0);
      } else if (this._color.equals("lightGray")) { //$NON-NLS-1$
        gl.glColor4fv(this.lightGray, 0);
      } else if (this._color.equals("gray")) { //$NON-NLS-1$
        gl.glColor4fv(this.gray, 0);
      } else if (this._color.equals("darkGray")) { //$NON-NLS-1$
        gl.glColor4fv(this.darkGray, 0);
      } else if (this._color.equals("black")) { //$NON-NLS-1$
        gl.glColor4fv(this.black, 0);
      } else if (this._color.equals("red")) { //$NON-NLS-1$
        gl.glColor4fv(this.red, 0);
      } else if (this._color.equals("pink")) { //$NON-NLS-1$
        gl.glColor4fv(this.pink, 0);
      } else if (this._color.equals("orange")) { //$NON-NLS-1$
        gl.glColor4fv(this.orange, 0);
      } else if (this._color.equals("yellow")) { //$NON-NLS-1$
        gl.glColor4fv(this.yellow, 0);
      } else if (this._color.equals("green")) { //$NON-NLS-1$
        gl.glColor4fv(this.green, 0);
      } else if (this._color.equals("magenta")) { //$NON-NLS-1$
        gl.glColor4fv(this.magenta, 0);
      } else if (this._color.equals("cyan")) { //$NON-NLS-1$
        gl.glColor4fv(this.cyan, 0);
      } else if (this._color.equals("blue")) { //$NON-NLS-1$
        gl.glColor4fv(this.blue, 0);
      }
    }

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

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }
}
