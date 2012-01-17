package org.mklab.mikity.jogl;

import javax.media.opengl.GL;


public class TriJoglObject implements JoglObject {

  @Override
  public void display(GL gl) {

    
    gl.glClear(GL.GL_COLOR_BUFFER_BIT); // 画面をクリアします
    gl.glPushMatrix(); // 行列を退避します
    gl.glBegin(GL.GL_TRIANGLES); // 三角形を描画することを宣言します 
    gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f); // 頂点の色を決定します 
    gl.glVertex3f(-1.0f, 1.0f, 0); // 頂点を指定します 
    gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f); 
    gl.glVertex3f(1.0f, -1.0f, 0);
    gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f); 
    gl.glVertex3f(-1.0f, -1.0f, 0); // 3つ目の頂点を指定すると、三角形が描画されます 
    gl.glEnd(); // 描画処理が終了しました 
    gl.glPopMatrix(); // 行列を復帰します 
    
  }

}
