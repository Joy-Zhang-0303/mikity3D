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
package org.mklab.mikity.android.editor;

import org.mklab.mikity.android.OpenglesModeler;
import org.mklab.mikity.android.model.SceneGraphTree;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;


/**
 * {@link AbstractObjectEditor}のファクトリークラスです。
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public class ModelEditorFactory {
  /**
   * エディタを生成します。
   * 
   * @param model モデル
   * @param group グループ
   * @param tree ツリー
   * @param modeler モデラー
   * @return ダイアログ
   */
  public static ModelEditor create(Object model, GroupModel group, SceneGraphTree tree, OpenglesModeler modeler) {
    if (model instanceof GroupModel) {
      final ModelEditor editor = new GroupEditor(group, tree, modeler);
      return editor;
    } 
    if (model instanceof TriangleModel) {
      final ModelEditor editor = new TriangleEditor((ObjectModel)model, tree,  modeler);
      return editor;
    } 
    if (model instanceof QuadrangleModel) {
      final ModelEditor editor = new QuadrangleEditor((ObjectModel)model, tree,  modeler);
      return editor;
    }
    if (model instanceof BoxModel) {
      final ModelEditor editor = new BoxEditor((ObjectModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof CylinderModel) {
      final ModelEditor editor = new CylinderEditor((ObjectModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof SphereModel) {
      final ModelEditor editor = new SphereEditor((ObjectModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof ConeModel) {
      final ModelEditor editor = new ConeEditor((ObjectModel)model, tree, modeler);
      return editor;
    }
    if (model instanceof CapsuleModel) {
      final ModelEditor editor = new CapsuleEditor((ObjectModel)model, tree, modeler);
      return editor;
    }    
    if (model instanceof CompositionModel) {
      final ModelEditor editor = new CompositionEditor((ObjectModel)model, tree, modeler);
      return editor;
    }    

    throw new IllegalArgumentException(model.toString());
  }

}
