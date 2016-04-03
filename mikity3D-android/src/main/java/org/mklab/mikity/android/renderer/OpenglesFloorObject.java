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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.mklab.mikity.model.graphic.FloorObject;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;


/**
 * OpenGLESの床を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/12/12
 */
public class OpenglesFloorObject {
  /** 環境。 */
  private ConfigurationModel configuration;
  
  /** 床オブジェクト。 */
  private FloorObject object;

  /**
   * 新しく生成された<code>JoglGridObject</code>オブジェクトを初期化します。
   * @param configuration 環境
   */
  public OpenglesFloorObject(ConfigurationModel configuration) {
    this.configuration = configuration;
    this.object = new FloorObject(configuration.getBaseCoordinate());
  }
  
  /**
   * 環境データを設定します。
   * 
   * @param configuration 環境データ
   */
  void setConfiguration(ConfigurationModel configuration) {
    this.configuration = configuration;
  }
  
  /**
   * オブジェクトを表示します。
   * 
   * @param gl GL
   */
  public void display(GL10 gl) {
    if (this.configuration.getBaseCoordinate().isFloorDrawing() == false) {
      return;
    }
    
    applyColorBright(gl);
    drawObjectBright(gl);
    
    applyColorDark(gl);
    drawObjectDark(gl);
  }
  
  /**
   * 色(薄い色)を適用します。
   * 
   * @param gl GL　
   */
  private void applyColorBright(GL10 gl) {
    final float specular[] = {0.1f, 0.1f, 0.1f, 1.0f}; // 鏡面光
    final float ambient[] = {0.6f, 0.6f, 0.6f, 1.0f}; // 環境光
    final float diffuse[] = {0.9f, 0.9f, 0.7f, 1.0f}; // 拡散光

    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, specular, 0);
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT, ambient, 0);
    gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 120.0f);
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE, diffuse, 0);
  }
  
  /**
   * 色(濃い色)を適用します。
   * 
   * @param gl GL　
   */
  private void applyColorDark(GL10 gl) {
    final float specular[] = {0.1f, 0.1f, 0.1f, 1.0f}; // 鏡面光
    final float ambient[] = {0.6f, 0.6f, 0.6f, 1.0f}; // 環境光
    final float diffuse[] = {0.7f, 0.7f, 0.9f, 1.0f}; // 拡散光

    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, specular, 0);
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT, ambient, 0);
    gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 120.0f);
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE, diffuse, 0);
  }
  
  /**
   * float配列をFloatBufferに変換します。
   * 
   * @param array 変換元
   * @return 変換結果
   */
  private FloatBuffer makeFloatBuffer(float[] array) {
    final FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    buffer.put(array).position(0);
    return buffer;
  }

  /**
   * オブジェクト(薄い)を描画します。
   * 
   * @param gl GL
   */
  private void drawObjectBright(GL10 gl) {
    final float[] vertexArray = this.object.getVertexArrayBright();
    final float[] normalVectorArray = this.object.getNormalVectorArrayBright();
  
    drawTrianglePolygons(gl, vertexArray, normalVectorArray);
  }
  
  /**
   * オブジェクト(濃い)を描画します。
   * 
   * @param gl GL
   */
  private void drawObjectDark(GL10 gl) {
    final float[] vertexArray = this.object.getVertexArrayDark();
    final float[] normalVectorArray = this.object.getNormalVectorArrayDark();
  
    drawTrianglePolygons(gl, vertexArray, normalVectorArray);
  }
  
  /**
   * 三角形ポリゴンを描画します。
   * 
   * @param gl GL
   * @param vertexArray 頂点の成分配列
   * @param normalVectorArray 法線ベクトルの成分配列
   */
  private void drawTrianglePolygons(GL10 gl, final float[] vertexArray, final float[] normalVectorArray) {
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
    final FloatBuffer normalBuffer = makeFloatBuffer(normalVectorArray);
  
    // 法線配列の有効化
    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    // 法線バッファの指定
    gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
  
    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
  
    final int number = vertexArray.length / 3;
    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, number);
  }
}
