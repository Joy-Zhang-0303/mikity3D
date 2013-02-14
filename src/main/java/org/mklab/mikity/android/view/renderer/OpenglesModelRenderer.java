package org.mklab.mikity.android.view.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.android.MainActivity;
import org.mklab.mikity.model.xml.simplexml.JamastConfig;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.view.renderer.ModelRenderer;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;


/**
 * OpenGL用のキャンバスを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesModelRenderer implements ModelRenderer, Renderer {

  private static final long serialVersionUID = 5653656698891675370L;

  private GLU glu;

  /** オブジェクトのグループ */
  private OpenglesBranchGroup[] topGroups;

  private double[] eye = {0.0, 0.0, 5.0};

  /** X軸に関する回転角度 */
  private float rotationX = 0.0f;
  /** Y軸に関する回転角度 */
  private float rotationY = 0.0f;
  /** X軸方向への移動距離 */
  private float translationX = 0.0f;
  /** Y軸方向への移動距離 */
  private float translationY = 0.0f;
  /** 拡大縮小率 */
  private float scale = 0.0f;

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

  //光源の設定です 
  private float[] lightLocation0 = {0.5f, 1.0f, -1.0f, 1.0f}; // 平行光源1です 
  private float[] lightLocation1 = {-0.5f, 1.0f, -1.0f, 1.0f}; // 平行光源2です 
  private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f}; // 反射光の強さです 
  private float[] lightDiffuse = {0.3f, 0.3f, 0.3f, 1.0f}; // 拡散光の強さです 
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f}; // 環境光の強さです 

  
  public OpenglesModelRenderer(GLSurfaceView glView){
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

    ////final GL10 gl10 = drawable.getGL;

    gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

    gl10.glEnable(GL10.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    //gl10.glEnable(GL10.GL_CULL_FACE); // 裏返ったポリゴンを描画しません 
    gl10.glLoadIdentity();

    ////this.glu.gluLookAt(this.eye[0], this.eye[1], this.eye[2], 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

    gl10.glTranslatef(this.translationY, -this.translationX, 0.0f);
    gl10.glTranslatef(0.0f, 0.0f, -this.scale);
    gl10.glRotatef(this.rotationX, 1.0f, 0.0f, 0.0f);
    gl10.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    
    //ここで微調整してます
  
    gl10.glRotatef(-180f, 0f, 1f, 0f);                                               
    gl10.glScalef(2.3f, 2.3f, 2.3f);
    
    
    for (final OpenglesBranchGroup group : this.topGroups) {
      group.display(gl10);
    }
  }

  /**
   * 画面サイズ変更時に呼ばれる
   */
  public void onSurfaceChanged(GL10 gl10, int w, int h) {
    //ビューポート変換
    gl10.glViewport(0, 0, w, h);

  }

  public void setChildren(Group[] children) {

    this.topGroups = new OpenglesModelCreater().create(children);
  }

  public void setConfiguration(JamastConfig configuration) {
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

  public void updateDisplay() {
 this.glView.requestRender();//再描画

  }

}