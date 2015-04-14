package org.mklab.mikity.android.view.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.view.renderer.ModelRenderer;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;


/**
 * OpenGL用のキャンバスを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesModelRenderer implements ModelRenderer, Renderer {
  /** アスペクト比 。*/
  private float aspect;
  /** 回転角度。 */
  private int angle;

  private static final long serialVersionUID = 5653656698891675370L;

  private GLU glu;

  /** オブジェクトのグループ */
  private OpenglesBranchGroup[] topGroups;

  /** 視点。 */
  private double[] eye = {0.0, 0.0, 5.0};

  /** X軸周りの回転角度 */
  private float rotationX = 0.0f;
  /** Y軸周りの回転角度 */
  private float rotationY = 0.0f;
  /** X軸方向への移動距離 */
  private float translationX = 0.0f;
  /** Y軸方向への移動距離 */
  private float translationY = 0.0f;
  /** 拡大縮小率 */
  private float scale = 1.0f;

  private float scaleX = 1.0F;
  private float scaleY = 1.0F;
  private float scaleZ = 1.0F;
  /** マウスボタンを押した点 */
  //private Point startPoint;
  /** マウスボタンを離した点 */
  //private Point endPoint;

  /** X軸に関する開始回転角度 */
  //private float startRotationX;
  /** Y軸に関する終了回転角度 */
  //private float startRotationY;
  /** X軸方向への移動開始距離 */
  //private float startTranslationX;
  /** Y軸方向への移動開始距離 */
  //private float startTranslationY;
  /** 開始拡大縮小率 */
  //private float startScale;

  GLSurfaceView glView;

  /** 平行光源1  */
  private float[] lightLocation0 = {0.5f, 1.0f, -1.0f, 1.0f};
  /**  平行光源2 */
  private float[] lightLocation1 = {-0.5f, 1.0f, -1.0f, 1.0f};
  /** 反射光の強さ  */
  private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f};
  /** 拡散光の強さ  */
  private float[] lightDiffuse = {0.3f, 0.3f, 0.3f, 1.0f};
  /** 環境光の強さ  */
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f}; 

  /** カメラの位置 */
  private float eyeZ = -2.0f;
  private float eyeY = 0.0f;
  private float eyeX = 0.0f;

  /**
   * 新しく生成された<code>OpenglesModelRenderer</code>オブジェクトを初期化します。
   * 
   * @param glView GLサーフェースビュー
   */
  public OpenglesModelRenderer(GLSurfaceView glView) {
    this.glView = glView;
  }

  /**
   * {@inheritDoc}
   */
  public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
    //final GL10 gl10 = drawable.getGL();

    this.glu = new GLU();

    gl10.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    gl10.glEnable(GL10.GL_LIGHTING); //光源を有効にします 
    gl10.glEnable(GL10.GL_COLOR_MATERIAL); //カラーマテリアルを有効にします 
    gl10.glEnable(GL10.GL_LIGHT0); //0番のライトを有効にします
    gl10.glEnable(GL10.GL_LIGHT1);
    gl10.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 90.0f);

    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, this.lightLocation0, 0); // 平行光源を設定します 
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します
  }

  /**
   * {@inheritDoc}
   */
  public void onDrawFrame(GL10 gl10) {

    //画面のクリア　追加した
    gl10.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

    gl10.glMatrixMode(GL10.GL_PROJECTION);
    gl10.glLoadIdentity();
    GLU.gluPerspective(gl10, 45.0f, //Y方向の画角
        this.aspect, //アスペクト比
        0.01f, //ニアクリップ
        100.0f);//ファークリップ

    gl10.glEnable(GL10.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    //gl10.glEnable(GL10.GL_CULL_FACE); // 裏返ったポリゴンを描画しません 

    //光源位置の指定
    gl10.glMatrixMode(GL10.GL_MODELVIEW);
    gl10.glLoadIdentity();
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[] {-10.0f, 10.0f, -10.0f, 1.0f}, 0);
    GLU.gluLookAt(gl10, this.eyeX, this.eyeY, this.eyeZ, //カメラの視点
        0.0F, 0.0F, -1.0F, //カメラの焦点
        0.0F, 1.0F, 1.0F);//カメラの上方向

    gl10.glTranslatef(this.translationY, -this.translationX, 0.0f);
    gl10.glRotatef(this.rotationX, 1.0f, 0.0f, 0.0f);
    gl10.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);

    //ここで微調整してます
    gl10.glRotatef(-180f, 0f, 1f, 0f);

    gl10.glScalef(this.scaleX, this.scaleY, this.scaleZ);

    if (this.topGroups != null) { 
      for (final OpenglesBranchGroup group : this.topGroups) {
        group.display(gl10);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void onSurfaceChanged(GL10 gl10, int w, int h) {
    //ビューポート変換
    gl10.glViewport(0, 0, w, h);
    //アスペクト比の設定
    this.aspect = (float)w / (float)h;
  }

  /**
   * {@inheritDoc}
   */
  public void setChildren(Group[] children) {
    this.topGroups = new OpenglesModelCreater().create(children);
  }

  /**
   * {@inheritDoc}
   */
  public void setConfiguration(Mikity3dConfiguration configuration) {
    if (configuration == null) {
      return;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isRequiredToCallDisplay() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void updateDisplay() {
    this.glView.requestRender();//再描画

  }

  /**
   * 回転角度を設定します。
   * 
   * @param rotationX X軸周りの回転角度
   * @param rotationY y軸周りの回転角度
   */
  public void setRotation(float rotationX, float rotationY) {
    this.rotationY -= rotationX / 5;
    this.rotationX -= rotationY / 5;
  }

  /**
   * 拡大縮小倍率のを設定します。　
   * 
   * x方向、ｙ方向、ｚ方向全て同じ倍率を設定します。
   * 
   * @param scale スケール
   */
  public void setScale(float scale) {
    this.scaleX = scale;
    this.scaleY = scale;
    this.scaleZ = scale;
  }

  /**
   * スケールを設定します。
   * 
   * @param scaleY y方向のスケール
   * @param scaleX x方向のスケール
   * @param scaleZ ｚ方向のスケール
   */
  public void setScale(float scaleX, float scaleY, float scaleZ) {
    this.scaleX = scaleX;
    this.scaleY = scaleY;
    this.scaleZ = scaleZ;
  }

  /**
   * スケールを返します。
   * 
   * @return　スケール
   */
  public float getscale() {
    return this.scaleX;
  }

  /**
   * X軸方向の移動量を設定します。
   * 
   * @param translateX X軸方向の移動量
   */
  public void setTranslationX(float translateX) {
    this.translationX = (translateX + this.translationX);
  }

  /**
   * Y軸方向の移動量を設定します。
   * 
   * @param translateY Y軸方向の移動量
   */
  public void setTranslationY(float translateY) {
    this.translationY = (translateY + this.translationY);
  }

  /**
   * @param widthX
   * @param widthZ
   * @param nx
   * @param nz
   * @param gl10
   */
  public void drawFloor(double widthX, double widthZ, int nx, int nz, GL10 gl10) {
    //    int i, j;
    //    //Floor‚P–‡“–‚½‚è‚Ì•
    //    double wX = widthX / (double)nx;
    //    double wZ = widthZ / (double)nz;
    //
    //    float diffuse[][] = {
    //      { 0.9f, 0.6f, 0.3f, 1.0f}, { 0.3f, 0.5f, 0.8f, 1.0f} };
    //    float ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f};
    //    float specular[]= { 0.5f, 0.5f, 0.5f, 1.0f};;
    //    gl10.glMaterialfv(GL10.GL_FRONT,GL10.GL_AMBIENT,ambient, 0);
    //    gl10.glMaterialfv(GL10.GL_FRONT,GL10.GL_SPECULAR,specular, 0);
    //    gl10.glMaterialf(GL10.GL_FRONT,GL10.GL_SHININESS,10);
    //
    //    gl10.glNormal3f(0.0f, 1.0f, 0.0f);
    //    gl10.glPushMatrix();
    //    gl10.glBegin(GL10.GL_TRIANGLE_FAN);
    //    for (j = 0; j < nz; j++) {
    //      double z1 = -widthZ / 2.0 + wZ * j; double z2 = z1 + wZ;
    //      for (i = 0; i < nx; i++) {
    //        double x1 = -widthX / 2.0 + wX * i; double x2 = x1 + wX;
    //
    //        glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuse[(i + j) & 1]);
    //        glVertex3d(x1, 0.0, z1);
    //        glVertex3d(x1, 0.0, z2);
    //        glVertex3d(x2, 0.0, z2);
    //        glVertex3d(x2, 0.0, z1);
    //      }
    //    }
    //    glEnd();
    //    glPopMatrix();
  }

}
