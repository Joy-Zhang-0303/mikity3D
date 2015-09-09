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
      final ModelEditor dialog = new GroupEditor(shell, group, true, tree, modeler);
      return dialog;
    } 
    if (model instanceof TrianglePolygonModel) {
      final ModelEditor dialog = new TrianglePolygonEditor(shell, (PrimitiveModel)model, group, tree,  modeler);
      return dialog;
    } 
    if (model instanceof QuadPolygonModel) {
      final ModelEditor dialog = new QuadPolygonEditor(shell, (QuadPolygonModel)model, group, tree,  modeler);
      return dialog;
    }
    if (model instanceof BoxModel) {
      final ModelEditor dialog = new BoxEditor(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    } 
    if (model instanceof CylinderModel) {
      final ModelEditor dialog = new CylinderEditor(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    } 
    if (model instanceof SphereModel) {
      final ModelEditor dialog = new SphereEditor(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    } 
    if (model instanceof ConeModel) {
      final ModelEditor dialog = new ConeEditor(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    }

    throw new IllegalArgumentException(model.toString());
  }
}
