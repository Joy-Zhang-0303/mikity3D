/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

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
import org.mklab.mikity.view.gui.JoglModeler;
import org.mklab.mikity.view.gui.SceneGraphTree;


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
