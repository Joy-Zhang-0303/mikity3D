/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
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

  /** 環境。 */
  private ConfigurationModel configuration;
  
  /** グリッド。 */
  private OpenglesGridObject grid; 
  
  /** 床。 */
  private OpenglesFloorObject floor;
  
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

  /** 拡散光の強さ */
  private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
  /** 反射光の強さ */
  private float[] lightSpecular = {1.0f, 1.0f, 1.0f, 1.0f};
  /** 環境光の強さ */
  private float[] lightAmbient = {0.4f, 0.4f, 0.4f, 1.0f};
  
  /** GLサーフェイスビュー。 */
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
    this.floor = new OpenglesFloorObject(configuration);
  }

  /**
   * {@inheritDoc}
   */
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    gl.glEnable(GL10.GL_CULL_FACE);  // 裏面削除を有効にします 
    gl.glCullFace(GL10.GL_BACK); // 裏面を削除
    
    gl.glEnable(GL10.GL_LIGHTING); //光源を有効にします
    gl.glEnable(GL10.GL_LIGHT0); //0番のライトを有効にします
    
    gl.glClear(GL10.GL_DEPTH_BITS); //奥行きバッファをクリア
    gl.glEnable(GL10.GL_DEPTH_TEST); // 奥行きバッファ(判定)を有効にします
    
    gl.glShadeModel(GL10.GL_SMOOTH);
    gl.glEnable(GL10.GL_NORMALIZE); //法線ベクトルの自動正規化を有効にします
    
    gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 120.0f);
  }

  /**
   * {@inheritDoc}
   */
  public void onDrawFrame(GL10 gl) {
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    gl.glClear(GL10.GL_DEPTH_BUFFER_BIT);
    
    final ColorModel background = this.configuration.getBackground().getColor();
    gl.glClearColor(background.getRf(), background.getGf(), background.getBf(), background.getAlphaf());  

    gl.glLoadIdentity();
    
    final LightModel light = this.configuration.getLight();
    final float[] lightLocation = new float[]{light.getX(), light.getY(), light.getZ(), 1.0f}; // 点光源を設定します 
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightLocation, 0); // 光源を設定します 
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します

    gl.glLoadIdentity();
    
    final EyeModel eye = this.configuration.getEye();
    final LookAtPointModel lookAtPoint = this.configuration.getLookAtPoint();
    GLU.gluLookAt(gl, eye.getX(), eye.getY(), eye.getZ(), lookAtPoint.getX(), lookAtPoint.getY(), lookAtPoint.getZ(), 0.0F, 0.0F, 1.0F);
    
    gl.glTranslatef(0.0f, this.translationY, -this.translationZ);
    gl.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(this.rotationZ, 0.0f, 0.0f, 1.0f);

    gl.glScalef(this.scale, this.scale, this.scale);

    this.floor.display(gl);
    this.grid.display(gl);
    
    if (this.rootObjects != null) {
      final boolean isAxisShowing = this.configuration.getBaseCoordinate().isAxisShowing();
      
      for (final OpenglesGroupObject topObject : this.rootObjects) {
        topObject.setDrawingAxis(isAxisShowing);
        topObject.display(gl);
      }
    }
    
    final boolean isShadowDrawing = this.configuration.getBaseCoordinate().isShadowDrawing();
    
    if (isShadowDrawing) { 
      drawShadow(gl);
    }
  }
  
  /**
   * オブジェクト毎に光源の方向を変え、疑似的に点光源に対する影(平行光線に対する影)を作成します。
   */
  private void drawShadow(GL10 gl) {
    gl.glEnable(GL10.GL_BLEND);
    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    gl.glDepthMask(false);

    if (this.rootObjects != null) {
      for (final OpenglesGroupObject topGroup : this.rootObjects) {
        final float[] matrix = getShadowMatrix(topGroup.getGroup().getTranslation());
        gl.glPushMatrix();
        gl.glMultMatrixf(matrix, 0);
        topGroup.setDrawingShadow(true);
        topGroup.display(gl);
        topGroup.setDrawingShadow(false);
        gl.glPopMatrix();
      }
    }

    gl.glDepthMask(true);
    gl.glDisable(GL10.GL_BLEND);
  }

  /**
   * 射影行列を返します。
   * 
   * @param orgin 物体座標系の原点
   * @return 射影行列
   */
  private float[] getShadowMatrix(TranslationModel orgin) {
    final LightModel light = this.configuration.getLight();

    final float x = light.getX() - orgin.getX();
    final float y = light.getY() - orgin.getY();
    final float z = light.getZ() - orgin.getZ();

    // 各物体の原点から光源までの距離
    final float s = (float)Math.sqrt(x*x + y*y + z*z);

    // 光源の方向ベクトル
    final float cx = x/s;
    final float cy = y/s;
    final float cz = z/s;

    // 床の方向ベクトル
    final float fx = 0;
    final float fy = 0;
    final float fz = 1;
    final float fa = -0.002f; //0.2f; //床と影の干渉を防止

    // 射影行列
    final float[] matrix = new float[16];
    matrix[0] = fy*cy + fz*cz;
    matrix[1] = -fx*cy;
    matrix[2] = -fx*cz;
    matrix[3] = 0;
    matrix[4] = -fy*cx;
    matrix[5] = fx*cx + fz*cz;
    matrix[6] = -fy*cz;
    matrix[7] = 0;
    matrix[8] = -fz*cx;
    matrix[9] = -fz*cy;
    matrix[10] = fx*cx + fy*cy;
    matrix[11] = 0;
    matrix[12] = -fa*cx;
    matrix[13] = -fa*cy;
    matrix[14] = -fa*cz;
    matrix[15] = fx*cx + fy*cy + fz*cz;

    return matrix;
  }


  /**
   * {@inheritDoc}
   */
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    gl.glViewport(0, 0, width, height);
    
    gl.glMatrixMode(GL10.GL_PROJECTION);
    gl.glLoadIdentity();
    final float aspect = (float)width / (float)height;
    GLU.gluPerspective(gl, 10.0f, aspect, 0.1f, 1000.0f);
    gl.glMatrixMode(GL10.GL_MODELVIEW);
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
    this.floor.setConfiguration(configuration);    
    
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
