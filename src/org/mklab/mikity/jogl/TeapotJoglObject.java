package org.mklab.mikity.jogl;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/13
 */
public class TeapotJoglObject implements JoglObject {

  /**
   * @see org.mklab.mikity.jogl.JoglObject#display(javax.media.opengl.GL)
   */
  public void display(GL gl) {
    GLUT glut = new GLUT();
    int counter=0; // 表示回数のカウンターです 
    final float RADIUS=1.5f;
    float[] red=new float[]{0.0f,0.0f,0.0f,1.0f};
    
    //gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    float x=(float)(RADIUS*Math.cos((float)counter/30)); 
    float y=(float)(RADIUS*Math.sin((float)counter/30)); 
    
    //gl.glPushMatrix(); // 行列を退避します 
    //gl.glTranslatef(x, y, 0.0f); // 移動させます 
    //gl.glPushMatrix(); // 行列を退避します 
    //gl.glRotatef(counter*2, 0.0f, 1.0f, 0.0f); // 回転させます
    //gl.glColor4fv(red,0);
    //glut.glutSolidCube(1.0f);
    //gl.glTranslatef(0.0f, 1.25f, 0.0f);
    gl.glColor4fv(red,0);
    glut.glutSolidTeapot(1.0f);
    //gl.glPopMatrix(); // 行列を復帰します 
    //gl.glPopMatrix(); 
  }

}
