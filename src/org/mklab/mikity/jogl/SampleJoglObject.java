package org.mklab.mikity.jogl;

import javax.media.opengl.GL;


/**
 * JOGLオブジェクトのサンプルです。
 * @author iwamoto
 * @version $Revision$, 2012/01/13
 */
public class SampleJoglObject implements JoglObject {

  /**
   * @see org.mklab.mikity.jogl.JoglObject#display(javax.media.opengl.GL)
   */
  public void display(GL gl) {
    float[][] vertices=new float[][]{{1,1,1},{-1,1,1},{-1,-1,1},{1,-1,1},
        {1,1,-1},{-1,1,-1},{-1,-1,-1},{1,-1,-1}}; 
    
    float[] red=new float[]{1.0f,0.0f,0.0f,1.0f};
    float[] green=new float[]{0.0f,1.0f,0.0f,1.0f};
    float[] blue=new float[]{0.0f,0.0f,1.0f,1.0f};
    float[] yellow=new float[]{1.0f,1.0f,0.0f,1.0f};
    float[] cyan=new float[]{0.0f,1.0f,1.0f,1.0f};
    float[] magenta=new float[]{1.0f,0.0f,1.0f,1.0f};
    
    int counter=0; // 表示回数のカウンターです
    final float RADIUS=1.5f;
    
    float x=(float)(RADIUS*Math.cos((float)counter/30)); 
    float y=(float)(RADIUS*Math.sin((float)counter/30));
    
    gl.glPushMatrix(); // 行列を退避します 
    gl.glTranslatef(x, y, 0.0f); // 移動させます
    gl.glPushMatrix(); // 行列を退避します 
    gl.glRotatef(counter*2, 0.0f, 1.0f, 0.0f); // 回転させます 
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, -1.0f, 0.0f);


    gl.glBegin(GL.GL_QUADS); // 四角形を描画することを宣言します
    
    gl.glColor4fv(red,0); // 頂点の色を決定します 
    gl.glVertex3fv(vertices[0],0);
    gl.glVertex3fv(vertices[1],0);
    gl.glVertex3fv(vertices[2],0);
    gl.glVertex3fv(vertices[3],0); // 4つ目の頂点を指定するとポリゴンが描画されます
 
    gl.glColor4fv(green,0); 
    gl.glVertex3fv(vertices[7],0);
    gl.glVertex3fv(vertices[6],0);
    gl.glVertex3fv(vertices[5],0);
    gl.glVertex3fv(vertices[4],0);
    
    gl.glColor4fv(blue,0);
    gl.glVertex3fv(vertices[0],0);
    gl.glVertex3fv(vertices[3],0);
    gl.glVertex3fv(vertices[7],0);
    gl.glVertex3fv(vertices[4],0);
    
    gl.glColor4fv(cyan,0); // 頂点の色を決定します
    gl.glVertex3fv(vertices[5],0);
    gl.glVertex3fv(vertices[6],0);
    gl.glVertex3fv(vertices[2],0); 
    gl.glVertex3fv(vertices[1],0); // 4つ目の頂点を指定するとポリゴンが描画されます 
    
    gl.glColor4fv(magenta,0); 
    gl.glVertex3fv(vertices[4],0); 
    gl.glVertex3fv(vertices[5],0); 
    gl.glVertex3fv(vertices[1],0); 
    gl.glVertex3fv(vertices[0],0); 
    
    gl.glColor4fv(yellow,0); 
    gl.glVertex3fv(vertices[6],0); 
    gl.glVertex3fv(vertices[7],0); 
    gl.glVertex3fv(vertices[3],0); 
    gl.glVertex3fv(vertices[2],0); 
    
    gl.glEnd(); // 描画処理が終了しました 
    
    gl.glRotatef(180, 0.0f, 1.0f, 0.0f);
    gl.glTranslatef(0.0f, 2.0f , 0.0f);

    gl.glBegin(GL.GL_QUADS); // 四角形を描画することを宣言します
    
    gl.glColor4fv(red,0); // 頂点の色を決定します 
    gl.glVertex3fv(vertices[0],0);
    gl.glVertex3fv(vertices[1],0);
    gl.glVertex3fv(vertices[2],0);
    gl.glVertex3fv(vertices[3],0); // 4つ目の頂点を指定するとポリゴンが描画されます
 
    gl.glColor4fv(green,0); 
    gl.glVertex3fv(vertices[7],0);
    gl.glVertex3fv(vertices[6],0);
    gl.glVertex3fv(vertices[5],0);
    gl.glVertex3fv(vertices[4],0);
    
    gl.glColor4fv(blue,0);
    gl.glVertex3fv(vertices[0],0);
    gl.glVertex3fv(vertices[3],0);
    gl.glVertex3fv(vertices[7],0);
    gl.glVertex3fv(vertices[4],0);
    
    gl.glColor4fv(cyan,0); // 頂点の色を決定します
    gl.glVertex3fv(vertices[5],0);
    gl.glVertex3fv(vertices[6],0);
    gl.glVertex3fv(vertices[2],0); 
    gl.glVertex3fv(vertices[1],0); // 4つ目の頂点を指定するとポリゴンが描画されます 
    
    gl.glColor4fv(magenta,0); 
    gl.glVertex3fv(vertices[4],0); 
    gl.glVertex3fv(vertices[5],0); 
    gl.glVertex3fv(vertices[1],0); 
    gl.glVertex3fv(vertices[0],0); 
    
    gl.glColor4fv(yellow,0); 
    gl.glVertex3fv(vertices[6],0); 
    gl.glVertex3fv(vertices[7],0); 
    gl.glVertex3fv(vertices[3],0); 
    gl.glVertex3fv(vertices[2],0); 
    
    gl.glEnd(); // 描画処理が終了しました */
    gl.glPopMatrix();
    gl.glPopMatrix(); // 行列を復帰します 
    gl.glPopMatrix();
  }

}
