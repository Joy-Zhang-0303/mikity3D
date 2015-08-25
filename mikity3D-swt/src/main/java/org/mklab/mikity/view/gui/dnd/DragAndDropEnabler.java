/*
 * Created on 2004/12/15
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.view.gui.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.mklab.mikity.model.xml.simplexml.model.AbstractPrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;


/**
 * Dragを可能にするためのリスナークラスです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.6 $.2004/12/15
 */
public class DragAndDropEnabler {

  /** ライブラリツリー */
  Tree tree = null;
  /** タイプ */
  private static final Transfer[] TYPE = new Transfer[] {TextTransfer.getInstance()};
  /** 操作 */
  private static final int OPERATION = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

  /** */
  TreeItem treeItem;

  /**
   * コンストラクター ドラッグ元として {@link Tree}を設定する
   * 
   * @param tree ライブラリツリー
   */
  public DragAndDropEnabler(final Tree tree) {
    this.tree = tree;
    final DragSource source = new DragSource(tree, OPERATION);
    final DropTarget target = new DropTarget(tree, OPERATION);
    source.addDragListener(new MyDragSourceListener());
    source.setTransfer(TYPE);
    target.setTransfer(TYPE);
    target.addDropListener(new MyDropTargetListener());
  }

  /**
   * @author iwamoto
   * @version $Revision$, 2012/02/23
   */
  class MyDropTargetListener implements DropTargetListener {
    /**
     * {@inheritDoc}
     */
    public void dragEnter(DropTargetEvent e) {
    // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dragLeave(DropTargetEvent e) {
    // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dragOperationChanged(DropTargetEvent e) {
    // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dragOver(DropTargetEvent e) {
      final TreeItem item = DragAndDropEnabler.this.tree.getItem(DragAndDropEnabler.this.tree.toControl(e.x, e.y));
      if (item == null) {
        e.detail = DND.DROP_NONE;
      } else {
        e.detail = DND.DROP_MOVE;
      }
    }

    /**
     * {@inheritDoc}
     */
    public void drop(DropTargetEvent e) {
      // 今マウスがある場所のアイテムを取得
      final TreeItem item = DragAndDropEnabler.this.tree.getItem(DragAndDropEnabler.this.tree.toControl(e.x, e.y));
      if (item == null) {
        return;
      }

      // ここに来たらどろっぷ成功なはず。
      GroupModel group = null;
      TreeItem targetItem = null;

      if (item.getData() instanceof GroupModel) {
        group = (GroupModel)item.getData();
        targetItem = item;
      } else if (item.getParentItem() != null && item.getParentItem().getData() instanceof GroupModel) {
        group = (GroupModel)item.getParentItem().getData();
        targetItem = item.getParentItem();
      } else {
        return;
      }
      final Object obj = DragAndDropEnabler.this.treeItem.getData();
      final GroupModel sourceGroup = (GroupModel)DragAndDropEnabler.this.treeItem.getParentItem().getData();

      if (obj instanceof BoxModel) {
        sourceGroup.remove((BoxModel)obj);
        group.add((AbstractPrimitiveModel)obj);
      } else if (obj instanceof CylinderModel) {
        sourceGroup.remove((CylinderModel)obj);
        group.add((AbstractPrimitiveModel)obj);
      } else if (obj instanceof SphereModel) {
        sourceGroup.remove((SphereModel)obj);
        group.add((AbstractPrimitiveModel)obj);
      } else if (obj instanceof ConeModel) {
        sourceGroup.remove((ConeModel)obj);
        group.add((ConeModel)obj);
      } else if (obj instanceof TrianglePolygonModel) {
        sourceGroup.remove((TrianglePolygonModel)obj);
        group.add((TrianglePolygonModel)obj);
      } else if (obj instanceof QuadPolygonModel) {
        sourceGroup.remove((QuadPolygonModel)obj);
        group.add((QuadPolygonModel)obj);
      } else if (obj instanceof GroupModel) {
        sourceGroup.remove((GroupModel)obj);
        group.add((GroupModel)obj);
      } else {
        throw new RuntimeException(Messages.getString("DragAndDropEnabler.0")); //$NON-NLS-1$
      }
      final TreeItem newItem = new TreeItem(targetItem, SWT.NONE);
      newItem.setText(DragAndDropEnabler.this.treeItem.getText());
      newItem.setData(obj);

      DragAndDropEnabler.this.treeItem.dispose();

    }

    /**
     * {@inheritDoc}
     */
    public void dropAccept(DropTargetEvent e) {
    // nothing to do
    }

  }

  /**
   * @author iwamoto
   * @version $Revision$, 2012/02/23
   */
  class MyDragSourceListener implements DragSourceListener {
    /**
     * {@inheritDoc}
     */
    public void dragStart(DragSourceEvent e) {
      // 選択要素が1つでないときは、終了
      if (DragAndDropEnabler.this.tree.getSelectionCount() != 1 || DragAndDropEnabler.this.tree.getSelection()[0].getParentItem() == null) {
        e.doit = false;
        return;
      }
      DragAndDropEnabler.this.treeItem = DragAndDropEnabler.this.tree.getSelection()[0];
    }

    /**
     * {@inheritDoc}
     */
    public void dragSetData(DragSourceEvent e) {
    // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dragFinished(DragSourceEvent e) {
    // nothing to do
    }

  }

}