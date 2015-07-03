package org.mklab.mikity.android.view.renderer;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.config.Eye;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPoint;
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
  @SuppressWarnings("unused")
  private static final long serialVersionUID = 5653656698891675370L;

  /** オブジェクトのグループ */
  private List<OpenglesTransformGroup> topGroups;

  /** 設定。 */
  private Mikity3dConfiguration configuration;

  /** アスペクト比 。 */
  private float aspect;
  
  /** Y軸周りの回転角度 */
  private float rotationY = 0.0f;
  /** Z軸周りの回転角度 */
  private float rotationZ = 0.0f;
  /** Y軸方向への移動距離 */
  private float translationY = 0.0f;
  /** Z軸方向への移動距離 */
  private float translationZ = 0.0f;

  private float scaleX = 1.0F;
  private float scaleY = 1.0F;
  private float scaleZ = 1.0F;

  GLSurfaceView glView;

  /** 平行光源1 */
  private float[] lightLocation0 = {1.0f, -1.0f, 0.5f, 1.0f};
  /** 平行光源2 */
  private float[] lightLocation1 = {-0.5f, 1.0f, -1.0f, 1.0f};
  /** 反射光の強さ */
  private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f};
  /** 拡散光の強さ */
  private float[] lightDiffuse = {0.3f, 0.3f, 0.3f, 1.0f};
  /** 環境光の強さ */
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f};

  /**
   * 新しく生成された<code>OpenglesModelRenderer</code>オブジェクトを初期化します。
   * 
   * @param glView GLサーフェースビュー
   */
  public OpenglesModelRenderer(GLSurfaceView glView) {
    this.glView = glView;
    this.configuration = new Mikity3dConfiguration();
    this.configuration.setEye(new Eye(-2.0f, 0.0f, 0.0f));
    this.configuration.setLookAtPoiint(new LookAtPoint(0.0f, 0.0f, 0.0f));
  }

  /**
   * {@inheritDoc}
   */
  public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
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
    // 画面のクリア　追加した
    gl10.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

    gl10.glMatrixMode(GL10.GL_PROJECTION);
    gl10.glLoadIdentity();
    GLU.gluPerspective(gl10, 45.0f, this.aspect, 0.01f, 100.0f);

    gl10.glEnable(GL10.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    // gl10.glEnable(GL10.GL_CULL_FACE); // 裏返ったポリゴンを描画しません 

    // 光源位置の指定
    gl10.glMatrixMode(GL10.GL_MODELVIEW);
    gl10.glLoadIdentity();
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[] {-10.0f, 10.0f, -10.0f, 1.0f}, 0);

    final Eye eye = this.configuration.getEye();
    final LookAtPoint lookAtPoint = this.configuration.getLookAtPoint();
    GLU.gluLookAt(gl10, eye.getX(), eye.getY(), eye.getZ(), lookAtPoint.getX(), lookAtPoint.getY(), lookAtPoint.getZ(), 0.0F, 0.0F, 1.0F);

    gl10.glTranslatef(0.0f, this.translationY, -this.translationZ);
    gl10.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    gl10.glRotatef(this.rotationZ, 0.0f, 0.0f, 1.0f);

    // ここで微調整してます
    gl10.glRotatef(-180f, 0.0f, 0.0f, 1.0f);

    gl10.glScalef(this.scaleX, this.scaleY, this.scaleZ);

    if (this.topGroups != null) {
      for (final OpenglesTransformGroup group : this.topGroups) {
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
    this.configuration = configuration;
    
    updateDisplay();
  }
  
  /**
   * {@inheritDoc}
   */
  public Mikity3dConfiguration getConfiguration() {
    return this.configuration;
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
   * @param rotationZ Z軸周りの回転角度
   * @param rotationY Y軸周りの回転角度
   */
  public void setRotation(float rotationZ, float rotationY) {
    this.rotationZ -= rotationZ / 5;
    this.rotationY -= rotationY / 5;
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
   * Y軸方向の移動量を設定します。
   * 
   * @param translationY Y軸方向の移動量
   */
  public void setTranslationY(float translationY) {
    this.translationY = (translationY + this.translationY);
  }

  /**
   * Z軸方向の移動量を設定します。
   * 
   * @param translationZ Z軸方向の移動量
   */
  public void setTranslationZ(float translationZ) {
    this.translationZ = (translationZ + this.translationZ);
  }
}
