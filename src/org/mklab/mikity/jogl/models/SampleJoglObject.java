package org.mklab.mikity.jogl.models;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.JoglObject;


/**
 * JOGLオブジェクトのサンプルです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/13
 */
public class SampleJoglObject implements JoglObject {

  // 立方体の反射率です 
  private float[] cubeSpecular = {0.5f, 0.5f, 0.5f, 1.0f};
  private float[] cubeDiffuse = {0.5f, 0.5f, 0.5f, 1.0f};
  private float[] cubeAmbient = {0.2f, 0.2f, 0.2f, 1.0f};
  private float cubeShiness = 5.0f; // ハイライトの強さです

  /**
   * @see org.mklab.mikity.jogl.JoglObject#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {

    float[][] vertices = new float[][] { {1, 1, 1}, {-1, 1, 1}, {-1, -1, 1}, {1, -1, 1}, {1, 1, -1}, {-1, 1, -1}, {-1, -1, -1}, {1, -1, -1}};

    float[] red = new float[] {1.0f, 0.0f, 0.0f, 1.0f};
    float[] green = new float[] {0.0f, 1.0f, 0.0f, 1.0f};
    float[] blue = new float[] {0.0f, 0.0f, 1.0f, 1.0f};
    float[] yellow = new float[] {1.0f, 1.0f, 0.0f, 1.0f};
    float[] cyan = new float[] {0.0f, 1.0f, 1.0f, 1.0f};
    float[] magenta = new float[] {1.0f, 0.0f, 1.0f, 1.0f};

    int counter = 0; // 表示回数のカウンターです
    final float RADIUS = 1.5f;

    float x = (float)(RADIUS * Math.cos((float)counter / 30));
    float y = (float)(RADIUS * Math.sin((float)counter / 30));

    gl.glPushMatrix(); // 行列を退避します 
    gl.glTranslatef(x, y, 0.0f); // 移動させます
    gl.glPushMatrix(); // 行列を退避します 
    gl.glRotatef(counter * 2, 0.0f, 1.0f, 0.0f); // 回転させます 
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, -1.0f, 0.0f);

    // 物体の材質の、光源に対する反射率を決めます
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, this.cubeSpecular, 0); //ポリゴンの前面にどのくらいの反射か 
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, this.cubeDiffuse, 0); //ポリゴンの前面にどのくらいの拡散か 
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, this.cubeAmbient, 0); //ポリゴンの前面にどのくらいの環境光か
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, this.cubeShiness); //ポリゴンの前面にどのくらいのハイライトか

    gl.glBegin(GL.GL_QUADS); // 四角形を描画することを宣言します

    gl.glColor4fv(red, 0); // 頂点の色を決定します 
    gl.glVertex3fv(vertices[0], 0);
    gl.glVertex3fv(vertices[1], 0);
    gl.glVertex3fv(vertices[2], 0);
    gl.glVertex3fv(vertices[3], 0); // 4つ目の頂点を指定するとポリゴンが描画されます

    gl.glColor4fv(green, 0);
    gl.glVertex3fv(vertices[7], 0);
    gl.glVertex3fv(vertices[6], 0);
    gl.glVertex3fv(vertices[5], 0);
    gl.glVertex3fv(vertices[4], 0);

    gl.glColor4fv(blue, 0);
    gl.glVertex3fv(vertices[0], 0);
    gl.glVertex3fv(vertices[3], 0);
    gl.glVertex3fv(vertices[7], 0);
    gl.glVertex3fv(vertices[4], 0);

    gl.glColor4fv(cyan, 0); // 頂点の色を決定します
    gl.glVertex3fv(vertices[5], 0);
    gl.glVertex3fv(vertices[6], 0);
    gl.glVertex3fv(vertices[2], 0);
    gl.glVertex3fv(vertices[1], 0); // 4つ目の頂点を指定するとポリゴンが描画されます 

    gl.glColor4fv(magenta, 0);
    gl.glVertex3fv(vertices[4], 0);
    gl.glVertex3fv(vertices[5], 0);
    gl.glVertex3fv(vertices[1], 0);
    gl.glVertex3fv(vertices[0], 0);

    gl.glColor4fv(yellow, 0);
    gl.glVertex3fv(vertices[6], 0);
    gl.glVertex3fv(vertices[7], 0);
    gl.glVertex3fv(vertices[3], 0);
    gl.glVertex3fv(vertices[2], 0);

    gl.glEnd(); // 描画処理が終了しました 

    gl.glRotatef(180, 0.0f, 1.0f, 0.0f);
    gl.glTranslatef(0.0f, 2.0f, 0.0f);

    gl.glBegin(GL.GL_QUADS); // 四角形を描画することを宣言します

    gl.glColor4fv(red, 0); // 頂点の色を決定します 
    gl.glVertex3fv(vertices[0], 0);
    gl.glVertex3fv(vertices[1], 0);
    gl.glVertex3fv(vertices[2], 0);
    gl.glVertex3fv(vertices[3], 0); // 4つ目の頂点を指定するとポリゴンが描画されます

    gl.glColor4fv(green, 0);
    gl.glVertex3fv(vertices[7], 0);
    gl.glVertex3fv(vertices[6], 0);
    gl.glVertex3fv(vertices[5], 0);
    gl.glVertex3fv(vertices[4], 0);

    gl.glColor4fv(blue, 0);
    gl.glVertex3fv(vertices[0], 0);
    gl.glVertex3fv(vertices[3], 0);
    gl.glVertex3fv(vertices[7], 0);
    gl.glVertex3fv(vertices[4], 0);

    gl.glColor4fv(cyan, 0); // 頂点の色を決定します
    gl.glVertex3fv(vertices[5], 0);
    gl.glVertex3fv(vertices[6], 0);
    gl.glVertex3fv(vertices[2], 0);
    gl.glVertex3fv(vertices[1], 0); // 4つ目の頂点を指定するとポリゴンが描画されます 

    gl.glColor4fv(magenta, 0);
    gl.glVertex3fv(vertices[4], 0);
    gl.glVertex3fv(vertices[5], 0);
    gl.glVertex3fv(vertices[1], 0);
    gl.glVertex3fv(vertices[0], 0);

    gl.glColor4fv(yellow, 0);
    gl.glVertex3fv(vertices[6], 0);
    gl.glVertex3fv(vertices[7], 0);
    gl.glVertex3fv(vertices[3], 0);
    gl.glVertex3fv(vertices[2], 0);

    gl.glEnd(); // 描画処理が終了しました */
    gl.glPopMatrix();
    gl.glPopMatrix(); // 行列を復帰します 
    gl.glPopMatrix();
  }

}
