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

import org.mklab.mikity.model.xml.simplexml.model.AxisModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;


/**
 * {@link GraphicObject}のファクトリクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/17
 */
public class GraphicObjectFactory {
  /**
   * 与えられたモデルを含むをオブジェクトを生成します。
   * 
   * @param model モデル
   * @return 与えられたモデルを含むオブジェクト
   */
  public static GraphicObject create(ObjectModel model) {
    if (model instanceof BoxModel) {
      return new BoxObject((BoxModel)model);
    } 
    
    if (model instanceof CylinderModel) {
      return new CylinderObject((CylinderModel)model);
    } 
    
    if (model instanceof ConeModel) {
      return new ConeObject((ConeModel)model);
    } 
    
    if (model instanceof SphereModel) {
      return new SphereObject((SphereModel)model);
    }
    
    if (model instanceof CapsuleModel) {
      return new CapsuleObject((CapsuleModel)model);
    } 
    
    if (model instanceof TriangleModel) {
      return new TriangleObject((TriangleModel)model);
    } 

    if (model instanceof QuadrangleModel) {
      return new QuadrangleObject((QuadrangleModel)model);
    } 
    
    if (model instanceof CompositionModel) {
      return new CompositionObject((CompositionModel)model);
    } 

    if (model instanceof NullModel) {
      return new NullObject((NullModel)model);
    }
    
    if (model instanceof AxisModel) {
      return new AxisObject((AxisModel)model);
    }

    throw new IllegalArgumentException(model.getClass().toString());
  }
}
