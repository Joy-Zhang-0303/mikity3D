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
 * {@link EditModelDialog}のファクトリークラスです。
 * @author koga
 * @version $Revision$, 2015/08/26
 */
public class EditModelDialogFactory {
  /**
   * ダイアログを生成します。
   * 
   * @param model モデル
   * @param shell シェル
   * @param group グループ
   * @param tree ツリー
   * @param modeler モデラー
   * @return ダイアログ
   */
  public static EditModelDialog create(Object model, Shell shell, GroupModel group, SceneGraphTree tree, JoglModeler modeler) {
    if (model instanceof GroupModel) {
      final EditModelDialog dialog = new EditGroupDialog(shell, group, true, tree, modeler);
      return dialog;
    } 
    if (model instanceof TrianglePolygonModel) {
      final EditModelDialog dialog = new EditTrianglePolygonDialog(shell, (PrimitiveModel)model, group, tree,  modeler);
      return dialog;
    } 
    if (model instanceof QuadPolygonModel) {
      final EditModelDialog dialog = new EditQuadPolygonDialog(shell, (QuadPolygonModel)model, group, tree,  modeler);
      return dialog;
    }
    if (model instanceof BoxModel) {
      final EditModelDialog dialog = new EditBoxDialog(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    } 
    if (model instanceof CylinderModel) {
      final EditModelDialog dialog = new EditCylinderDialog(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    } 
    if (model instanceof SphereModel) {
      final EditModelDialog dialog = new EditSphereDialog(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    } 
    if (model instanceof ConeModel) {
      final EditModelDialog dialog = new EditConeDialog(shell, (PrimitiveModel)model, group, tree, modeler);
      return dialog;
    }

    throw new IllegalArgumentException(model.toString());
  }
}
