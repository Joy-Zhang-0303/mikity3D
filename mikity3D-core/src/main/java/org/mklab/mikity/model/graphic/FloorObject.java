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
package org.mklab.mikity.model.graphic;

import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.util.Vector3;

/**
 * 四角形オブジェクトを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/09
 */
public class FloorObject extends AbstractGraphicPrimitive {
  /**
   * 新しく生成された<code>QuadranglePrimitive</code>オブジェクトを初期化します。
   * 
   * @param polygon 四角形ポリゴン
   */
  public FloorObject(QuadrangleModel polygon) {
    super(polygon);
    updatePolygons();
  }

  /**
   * ポリゴンを更新します。
   */
  private void updatePolygons() {
    List<VertexModel> vertices = ((QuadrangleModel)this.object).getVertices();
    Vector3 normalVector = ((QuadrangleModel)this.object).getNormalVector();
    
    if (vertices == null || normalVector == null) {
      return;
    }
    
    final int polygonNumber = 2;
    initializeArrays(polygonNumber);
    
    float x0 = vertices.get(0).getX();
    float y0 = vertices.get(0).getY();
    float z0 = vertices.get(0).getZ();
    
    float x1 = vertices.get(1).getX();
    float y1 = vertices.get(1).getY();
    float z1 = vertices.get(1).getZ();
    
    float x2 = vertices.get(2).getX();
    float y2 = vertices.get(2).getY();
    float z2 = vertices.get(2).getZ();
    
    float x3 = vertices.get(3).getX();
    float y3 = vertices.get(3).getY();
    float z3 = vertices.get(3).getZ();

    final float nx = normalVector.getX();
    final float ny = normalVector.getY();
    final float nz = normalVector.getZ();
    
    final float[][] vertices2 = new float[][]{
        {x0, y0, z0}, {x1, y1, z1}, {x2, y2, z2},
        {x0, y0, z0}, {x2, y2, z2}, {x3, y3, z3}};
    
    final float[][] normalVector2 = new float[][]
        {{nx,ny,nz},{nx,ny,nz},{nx,ny,nz},{nx,ny,nz},{nx,ny,nz},{nx,ny,nz}};
    
    appendVertices(vertices2);
    appendNormalVector(normalVector2);
  }
}
