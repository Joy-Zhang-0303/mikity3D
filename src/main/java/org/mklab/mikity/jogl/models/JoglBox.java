package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;

import org.mklab.mikity.jogl.JoglObject;


/**
 * XMLBOXをJOGLで表したクラスです
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/26
 */
public class JoglBox implements JoglObject {

  //--------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _xsize
   */
  @XmlAttribute
  private float _xsize;

  /**
   * Field _ysize
   */
  @XmlAttribute
  private float _ysize;

  /**
   * Field _zsize
   */
  @XmlAttribute
  private float _zsize;

  @XmlAttribute
  private String _color;

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    float[][] vertice = new float[][] { {this._xsize / 2, this._ysize / 2, this._zsize / 2}, {-this._xsize / 2, this._ysize / 2, this._zsize / 2},
        {-this._xsize / 2, -this._ysize / 2, this._zsize / 2}, {this._xsize / 2, -this._ysize / 2, this._zsize / 2}, {this._xsize / 2, this._ysize / 2, -this._zsize / 2},
        {-this._xsize / 2, this._ysize / 2, -this._zsize / 2}, {-this._xsize / 2, -this._ysize / 2, -this._zsize / 2}, {this._xsize / 2, -this._ysize / 2, -this._zsize / 2}};

    if (this._color != null) {
      if (this._color.equals("white")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
      } else if (this._color.equals("lightGray")) { //$NON-NLS-1$
        gl.glColor4f(0.75f, 0.75f, 0.75f, 1.0f);
      } else if (this._color.equals("gray")) { //$NON-NLS-1$
        gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
      } else if (this._color.equals("darkGray")) { //$NON-NLS-1$
        gl.glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
      } else if (this._color.equals("black")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
      } else if (this._color.equals("red")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
      } else if (this._color.equals("pink")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.69f, 0.69f, 1.0f);
      } else if (this._color.equals("orange")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.78f, 0.0f, 1.0f);
      } else if (this._color.equals("yellow")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
      } else if (this._color.equals("green")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
      } else if (this._color.equals("magenta")) { //$NON-NLS-1$
        gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f);
      } else if (this._color.equals("cyan")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
      } else if (this._color.equals("blue")) { //$NON-NLS-1$
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
      }
    }

    gl.glBegin(GL.GL_QUADS); // 四角形を描画することを宣言します 
    gl.glVertex3fv(vertice[0], 0);
    gl.glVertex3fv(vertice[1], 0);
    gl.glVertex3fv(vertice[2], 0);
    gl.glVertex3fv(vertice[3], 0); // 4つ目の頂点を指定するとポリゴンが描画されます

    gl.glVertex3fv(vertice[7], 0);
    gl.glVertex3fv(vertice[6], 0);
    gl.glVertex3fv(vertice[5], 0);
    gl.glVertex3fv(vertice[4], 0);

    gl.glVertex3fv(vertice[0], 0);
    gl.glVertex3fv(vertice[3], 0);
    gl.glVertex3fv(vertice[7], 0);
    gl.glVertex3fv(vertice[4], 0);

    gl.glVertex3fv(vertice[5], 0);
    gl.glVertex3fv(vertice[6], 0);
    gl.glVertex3fv(vertice[2], 0);
    gl.glVertex3fv(vertice[1], 0); // 4つ目の頂点を指定するとポリゴンが描画されます 

    gl.glVertex3fv(vertice[4], 0);
    gl.glVertex3fv(vertice[5], 0);
    gl.glVertex3fv(vertice[1], 0);
    gl.glVertex3fv(vertice[0], 0);

    gl.glVertex3fv(vertice[6], 0);
    gl.glVertex3fv(vertice[7], 0);
    gl.glVertex3fv(vertice[3], 0);
    gl.glVertex3fv(vertice[2], 0);

    gl.glEnd(); // 描画処理が終了しました

    gl.glPopMatrix();
  }

  /**
   * 大きさの設定
   * 
   * @param x xの長さ
   * @param y yの長さ
   * @param z zの長さ
   */
  public void setSize(float x, float y, float z) {
    this._xsize = x;
    this._ysize = y;
    this._zsize = z;
  }

  /**
   * @param color 色
   */
  public void setColor(String color) {
    this._color = color;
  }

}
