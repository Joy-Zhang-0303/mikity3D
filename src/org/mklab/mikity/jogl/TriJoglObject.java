package org.mklab.mikity.jogl;

import javax.media.opengl.GL;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/18
 */
public class TriJoglObject implements JoglObject {

  /**
   * @see org.mklab.mikity.jogl.JoglObject#display(javax.media.opengl.GL)
   */
  @Override
  public void display(GL gl) {

    gl.glBegin(GL.GL_TRIANGLES); // 三角形を描画することを宣言します 
    gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); // 頂点の色を決定します 
    gl.glVertex3f(-1.0f, 1.0f, 0); // 頂点を指定します
    gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
    gl.glVertex3f(-1.0f, -1.0f, 0);
    gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
    gl.glVertex3f(1.0f, -1.0f, 0);
    gl.glEnd(); // 描画処理が終了しました 
    //gl.glTranslatef(2.0f, 0.0f, 0.0f);
  }

}
