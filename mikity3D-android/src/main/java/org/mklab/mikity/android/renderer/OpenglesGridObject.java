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

import org.mklab.mikity.model.graphic.GridObject;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.config.BaseCoordinateModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;

/**
 * Openglesのグリッドを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/12/12
 */
public class OpenglesGridObject {
  /** 環境。 */
  private ConfigurationModel configuration;
  
  /** グリッドオブジェクト。 */
  private GridObject grid;

  /**
   * 新しく生成された<code>JoglGridObject</code>オブジェクトを初期化します。
   * @param configuration 環境
   */
  public OpenglesGridObject(ConfigurationModel configuration) {
    this.configuration = configuration;
    this.grid = new GridObject(configuration.getBaseCoordinate());
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
    final BaseCoordinateModel baseCoordinate = this.configuration.getBaseCoordinate();
    
    if (baseCoordinate.isAxisShowing()) {
      drawBaseAxis(gl);
    }
    
    if (baseCoordinate.isGridShowing()) {
      drawGrid(gl);
    }
  }

  /**
   * 絶対座標の座標軸を描画します。
   * 
   * @param gl GL
   */
  private void drawBaseAxis(GL10 gl) {
    float floorWidth = this.configuration.getBaseCoordinate().getFloorWidth();
    final float axisMin = -floorWidth/2;
    final float axisMax = floorWidth/2;
    
    // x軸の描画
    applyColor(gl, new ColorModel("red")); //$NON-NLS-1$
    float[] xAxis = {axisMin, 0, 0, axisMax, 0, 0};
    drawLines(gl, xAxis);

    // y軸の描画
    applyColor(gl, new ColorModel("blue")); //$NON-NLS-1$
    float[] yAxis = {0, axisMin, 0, 0, axisMax, 0};
    drawLines(gl, yAxis);

    // z軸の描画
    applyColor(gl, new ColorModel("green")); //$NON-NLS-1$
    float[] zAxis = {0, 0, axisMin, 0, 0, axisMax}; 
    drawLines(gl, zAxis);
  }

  /**
   * グリッドを描画します。
   * 
   * @param gl GL
   */
  private void drawGrid(GL10 gl) {
    applyColor(gl, this.configuration.getBaseCoordinate().getGridColor());
    drawLines(gl, this.grid.getVertexArray());
  }
  
  /**
   *  線分を描画します。
   *  
   * @param gl GL
   * @param vertexArray 頂点の成分配列
   */
  private void drawLines(GL10 gl, final float[] vertexArray) {
    final FloatBuffer vertexBuffer = makeFloatBuffer(vertexArray);
    
    // 頂点配列の有効化
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    // 頂点バッファの指定
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

    final int number = vertexArray.length/3;
    gl.glDrawArrays(GL10.GL_LINES, 0, number);
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
   * 色を適用します。
   * 
   * @param gl GL　
   */
  private void applyColor(GL10 gl, ColorModel color) {
    final float ambient0 = 0.6f;
    final float specular0 = 0.1f;
    final float highlight = 120.0f;

    final float[] ambient = {ambient0, ambient0, ambient0, 1};
    final float[] specular = {specular0, specular0, specular0, 1};
    final float[] diffuse = {color.getRf(), color.getGf(), color.getBf(), color.getAlphaf()};

    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE, diffuse, 0);
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT, ambient, 0);
    gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, specular, 0);
    gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, highlight);
    
    //gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());
  }
}
