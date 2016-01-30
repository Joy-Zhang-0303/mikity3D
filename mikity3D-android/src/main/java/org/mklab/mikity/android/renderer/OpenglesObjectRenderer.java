package org.mklab.mikity.android.renderer;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.renderer.ObjectRenderer;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;


/**
 * OpenGL用のキャンバスを表すクラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/06
 */
public class OpenglesObjectRenderer implements ObjectRenderer, Renderer {
  @SuppressWarnings("unused")
  private static final long serialVersionUID = 5653656698891675370L;

  /** ルートオブジェクト。 */
  private List<OpenglesGroupObject> rootObjects;

  /** 設定。 */
  private ConfigurationModel configuration;
  
  /** グリッド。 */
  private OpenglesGridObject grid; 

  /** アスペクト比 。 */
  private float aspect;
  
  /** Y軸周りの回転角度[rad] */
  private float rotationY = 0.0f;
  /** Z軸周りの回転角度[rad] */
  private float rotationZ = 0.0f;
  /** Y軸方向への移動距離[m] */
  private float translationY = 0.0f;
  /** Z軸方向への移動距離[m] */
  private float translationZ = 0.0f;
  /** 拡大縮小比。 */
  private float scale = 1.0F;

  /** 反射光の強さ */
  private float[] lightSpecular = {0.9f, 0.9f, 0.9f, 1.0f};
  /** 拡散光の強さ */
  private float[] lightDiffuse = {0.5f, 0.5f, 0.5f, 1.0f};
  /** 環境光の強さ */
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f};
  
  GLSurfaceView glView;

  /**
   * 新しく生成された<code>OpenglesModelRenderer</code>オブジェクトを初期化します。
   * 
   * @param glView GLサーフェースビュー
   * @param configuration 環境 
   */
  public OpenglesObjectRenderer(GLSurfaceView glView, ConfigurationModel configuration) {
    this.glView = glView;
    this.configuration = configuration;
    
    this.grid = new OpenglesGridObject(configuration);
  }

  /**
   * {@inheritDoc}
   */
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    gl.glEnable(GL10.GL_LIGHTING); //光源を有効にします 
    gl.glEnable(GL10.GL_COLOR_MATERIAL); //カラーマテリアルを有効にします
    gl.glEnable(GL10.GL_NORMALIZE);
    gl.glEnable(GL10.GL_LIGHT0); //0番のライトを有効にします
    gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 100.0f);
        
    final LightModel light = this.configuration.getLight();
    final float[] lightLocation = new float[]{light.getX(), light.getY(), light.getZ(), 1.0f};
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightLocation, 0); // 平行光源を設定します 
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します
  }

  /**
   * {@inheritDoc}
   */
  public void onDrawFrame(GL10 gl) {
    final ColorModel background = this.configuration.getBackground().getColor();
    gl.glClearColor(background.getRf(), background.getGf(), background.getBf(), background.getAlphaf());  

    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

    gl.glMatrixMode(GL10.GL_PROJECTION);
    gl.glLoadIdentity();
    GLU.gluPerspective(gl, 10.0f, this.aspect, 0.1f, 1000.0f);

    gl.glEnable(GL10.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    // gl10.glEnable(GL10.GL_CULL_FACE); // 裏返ったポリゴンを描画しません 

    // 光源位置の指定
    gl.glMatrixMode(GL10.GL_MODELVIEW);
    gl.glLoadIdentity();
    final LightModel light = this.configuration.getLight();
    final float[] lightLocation = new float[]{light.getX(), light.getY(), light.getZ(), 1.0f};
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightLocation, 0); // 平行光源を設定します 

    final EyeModel eye = this.configuration.getEye();
    final LookAtPointModel lookAtPoint = this.configuration.getLookAtPoint();
    GLU.gluLookAt(gl, eye.getX(), eye.getY(), eye.getZ(), lookAtPoint.getX(), lookAtPoint.getY(), lookAtPoint.getZ(), 0.0F, 0.0F, 1.0F);

    gl.glTranslatef(0.0f, this.translationY, -this.translationZ);
    gl.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(this.rotationZ, 0.0f, 0.0f, 1.0f);

    gl.glScalef(this.scale, this.scale, this.scale);

    final boolean isAxisShowing = this.configuration.getBaseCoordinate().isAxisShowing();
    
    if (this.rootObjects != null) {
      for (final OpenglesGroupObject topObject : this.rootObjects) {
        topObject.setShowingAxis(isAxisShowing);
        topObject.display(gl);
      }
    }
    
    this.grid.display(gl);
  }

  /**
   * {@inheritDoc}
   */
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    //ビューポート変換
    gl.glViewport(0, 0, width, height);
    this.aspect = (float)width / (float)height;
  }

  /**
   * {@inheritDoc}
   */
  public void setRootGroups(List<GroupModel> rootGroups, GroupObjectManager manager) {
    this.rootObjects = createGroupObjects(rootGroups, manager);
  }

  /**
   * グループオブジェクトを生成します。
   * 
   * @param groupModels グループモデル
   * @param manager オブジェクトグループマネージャ
   * @return グループオブジェクト
   */
  private List<OpenglesGroupObject> createGroupObjects(List<GroupModel> groupModels, GroupObjectManager manager) {
    final OpenglesObjectFactory factory = new OpenglesObjectFactory(manager);
    
    final List<OpenglesGroupObject> groupObjects = new ArrayList<OpenglesGroupObject>();
    for (final GroupModel groupModel : groupModels) {
      final OpenglesGroupObject objectGroup = factory.create(groupModel);
      groupObjects.add(objectGroup);
    }
    return groupObjects;
  }
  
  /**
   * {@inheritDoc}
   */
  public void setConfiguration(ConfigurationModel configuration) {
    if (configuration == null) {
      return;
    }
    
    this.configuration = configuration;
    this.grid.setConfiguration(configuration);    
    
    updateDisplay();
  }
  
  /**
   * {@inheritDoc}
   */
  public ConfigurationModel getConfiguration() {
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
   * Y軸周りの回転角度[rad]を返します。
   * 
   * @return Y軸周りの回転角度[rad]
   */
  public float getRotationY() {
    return this.rotationY;
  }

  /**
   * Z軸周りの回転角度[rad]を返します。
   * 
   * @return Z軸周りの回転角度[rad]
   */
  public float getRotationZ() {
    return this.rotationZ;
  }

  /**
   * Y軸周りの回転角度[rad]を設定します。
   * 
   * @param rotation Y軸周りの回転角度[rad]
   */
  public void rotateY(float rotation) {
    this.rotationY = this.rotationY + rotation;
  }
  
  /**
   * Z軸周りの回転角度[rad]を設定します。
   * 
   * @param rotation Z軸周りの回転角度[rad]
   */
  public void rotateZ(float rotation) {
    this.rotationZ = this.rotationZ + rotation;
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
   * @param translation Y軸方向の移動量
   */
  public void translateY(float translation) {
    this.translationY = this.translationY + translation;
  }

  /**
   * Z軸方向の移動量を設定します。
   * 
   * @param translation Z軸方向の移動量
   */
  public void translateZ(float translation) {
    this.translationZ = this.translationZ + translation;
  }
  
  /**
   * 移動・回転・拡大・縮小等の操作をリセットし、初期状態に戻します。
   */
  public void resetToInitialState() {
    this.translationY = 0;
    this.translationZ = 0;
    this.rotationY = 0;
    this.rotationZ = 0;
    this.scale = 1;
  }
}