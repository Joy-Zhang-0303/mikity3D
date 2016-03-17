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
package org.mklab.mikity.swt.gui.editor;

import org.eclipse.swt.widgets.Composite;
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
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.SceneGraphTree;


/**
 * {@link ModelEditor}のファクトリークラスです。
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public class ModelEditorFactory {
  /**
   * エディタを生成します。
   * 
   * @param model モデル
   * @param parent シェル
   * @param group グループ
   * @param tree ツリー
   * @param modeler モデラー
   * @return ダイアログ
   */
  public static ModelEditor create(Object model, Composite parent, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    if (model instanceof GroupModel) {
      final ModelEditor editor = new GroupEditor(parent, group, true, tree, modeler);
      return editor;
    } 
    if (model instanceof TriangleModel) {
      final ModelEditor editor = new TriangleEditor(parent, (ObjectModel)model, tree,  modeler);
      return editor;
    } 
    if (model instanceof QuadrangleModel) {
      final ModelEditor editor = new QuadrangleEditor(parent, (QuadrangleModel)model, tree,  modeler);
      return editor;
    }
    if (model instanceof BoxModel) {
      final ModelEditor editor = new BoxEditor(parent, (ObjectModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof CylinderModel) {
      final ModelEditor editor = new CylinderEditor(parent, (ObjectModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof SphereModel) {
      final ModelEditor editor = new SphereEditor(parent, (ObjectModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof ConeModel) {
      final ModelEditor editor = new ConeEditor(parent, (ObjectModel)model, tree, modeler);
      return editor;
    }
    if (model instanceof CapsuleModel) {
      final ModelEditor editor = new CapsuleEditor(parent, (ObjectModel)model, tree, modeler);
      return editor;
    }    
    if (model instanceof CompositionModel) {
      final ModelEditor editor = new CompositionEditor(parent, (ObjectModel)model, tree, modeler);
      return editor;
    }    

    throw new IllegalArgumentException(model.toString());
  }

}
