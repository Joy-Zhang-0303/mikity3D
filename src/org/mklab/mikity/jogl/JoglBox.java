package org.mklab.mikity.jogl;

import javax.media.opengl.GL;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


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

  /**
   * Field _rotation
   */
  @XmlElement
  private org.mklab.mikity.xml.model.Rotation _rotation;

  /**
   * Field _location
   */
  @XmlElement
  private org.mklab.mikity.xml.model.Location _location;

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    float[][] vertice = new float[][] { {this._xsize / 2, this._ysize / 2, this._zsize / 2}, {-this._xsize / 2, this._ysize / 2, this._zsize / 2},
        {-this._xsize / 2, -this._ysize / 2, this._zsize / 2}, {this._xsize / 2, -this._ysize / 2, this._zsize / 2}, {this._xsize / 2, this._ysize / 2, -this._zsize / 2},
        {-this._xsize / 2, this._ysize / 2, -this._zsize / 2}, {-this._xsize / 2, -this._ysize / 2, -this._zsize / 2}, {this._xsize / 2, -this._ysize / 2, -this._zsize / 2}};

    //    float[][] vertice = new float[][] { {1, 1, 1}, {-1, 1, 1}, {-1, -1, 1}, {1, -1, 1}, {1, 1, -1}, {-1, 1, -1}, {-1, -1, -1}, {1, -1, -1}};

    float[] red = new float[] {1.0f, 0.0f, 0.0f, 1.0f};

    gl.glColor4fv(red, 0);
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
  }

  /**
   * @param x
   * @param y
   * @param z
   */
  public void setSize(float x, float y, float z) {
    this._xsize = x;
    this._ysize = y;
    this._zsize = z;
  }

  /**
   * Returns the value of field 'location'.
   * 
   * @return the value of field 'location'.
   */
  public org.mklab.mikity.xml.model.Location loadLocation() {
    return this._location;
  }

  /**
   * Returns the value of field 'rotation'.
   * 
   * @return the value of field 'rotation'.
   */
  public org.mklab.mikity.xml.model.Rotation loadRotation() {
    return this._rotation;
  }

}
