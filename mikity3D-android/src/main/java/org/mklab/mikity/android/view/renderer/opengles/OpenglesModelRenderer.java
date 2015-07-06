package org.mklab.mikity.android.view.renderer.opengles;

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

  /** オブジェクトのグループ。 */
  private List<OpenglesObjectGroup> topGroups;

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
  /** 拡大縮小比。 */
  private float scale = 1.0F;

  /** 平行光源1 */
  private float[] lightLocation0 = {-10.0f, 10.0f, -10.0f, 1.0f};
  /** 平行光源2 */
  //private float[] lightLocation1 = {-0.5f, 1.0f, -1.0f, 1.0f};
  /** 反射光の強さ */
  private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f};
  /** 拡散光の強さ */
  private float[] lightDiffuse = {0.3f, 0.3f, 0.3f, 1.0f};
  /** 環境光の強さ */
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f};
  
  GLSurfaceView glView;

  /**
   * 新しく生成された<code>OpenglesModelRenderer</code>オブジェクトを初期化します。
   * 
   * @param glView GLサーフェースビュー
   */
  public OpenglesModelRenderer(GLSurfaceView glView) {
    this.glView = glView;
    this.configuration = new Mikity3dConfiguration();
    this.configuration.setEye(new Eye(5.0f, 0.0f, 0.0f));
    this.configuration.setLookAtPoiint(new LookAtPoint(0.0f, 0.0f, 0.0f));
  }

  /**
   * {@inheritDoc}
   */
  public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
    gl10.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    gl10.glEnable(GL10.GL_LIGHTING); //光源を有効にします 
    gl10.glEnable(GL10.GL_COLOR_MATERIAL); //カラーマテリアルを有効にします 
    gl10.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 90.0f);

    gl10.glEnable(GL10.GL_NORMALIZE);
    
    gl10.glEnable(GL10.GL_LIGHT0); //0番のライトを有効にします
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, this.lightLocation0, 0); // 平行光源を設定します 
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します
    
   // gl10.glEnable(GL10.GL_LIGHT1);
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
    GLU.gluPerspective(gl10, 10.0f, this.aspect, 0.01f, 100.0f);

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

    gl10.glScalef(this.scale, this.scale, this.scale);

    if (this.topGroups != null) {
      for (final OpenglesObjectGroup group : this.topGroups) {
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
   * @param rotationY Y軸周りの回転角度
   */
  public void setRotationY(float rotationY) {
    this.rotationY -= rotationY / 5;
  }
  
  /**
   * 回転角度を設定します。
   * 
   * @param rotationZ Z軸周りの回転角度
   */
  public void setRotationZ(float rotationZ) {
    this.rotationZ -= rotationZ / 5;
  }

  /**
   * 拡大縮小倍率のを設定します。　
   * 
   * @param scale スケール
   */
  public void setScale(float scale) {
    this.scale = scale;
  }

  /**
   * スケールを返します。
   * 
   * @return　スケール
   */
  public float getScale() {
    return this.scale;
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
