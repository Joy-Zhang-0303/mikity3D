/*
 * Created on 2015/08/26
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.dialog;

import org.eclipse.swt.widgets.Shell;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
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
   * @param shell シェル
   * @param group グループ
   * @param tree ツリー
   * @param modeler モデラー
   * @return ダイアログ
   */
  public static ModelEditor create(Object model, Shell shell, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    if (model instanceof GroupModel) {
      final ModelEditor editor = new GroupEditor(shell, group, true, tree, modeler);
      return editor;
    } 
    if (model instanceof TrianglePolygonModel) {
      final ModelEditor editor = new TrianglePolygonEditor(shell, (PrimitiveModel)model, tree,  modeler);
      return editor;
    } 
    if (model instanceof QuadPolygonModel) {
      final ModelEditor editor = new QuadPolygonEditor(shell, (QuadPolygonModel)model, tree,  modeler);
      return editor;
    }
    if (model instanceof BoxModel) {
      final ModelEditor editor = new BoxEditor(shell, (PrimitiveModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof CylinderModel) {
      final ModelEditor editor = new CylinderEditor(shell, (PrimitiveModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof SphereModel) {
      final ModelEditor editor = new SphereEditor(shell, (PrimitiveModel)model, tree, modeler);
      return editor;
    } 
    if (model instanceof ConeModel) {
      final ModelEditor editor = new ConeEditor(shell, (PrimitiveModel)model, tree, modeler);
      return editor;
    }

    throw new IllegalArgumentException(model.toString());
  }
}
