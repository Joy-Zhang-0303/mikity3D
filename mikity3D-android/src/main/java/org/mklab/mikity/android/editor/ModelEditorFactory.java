/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
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
