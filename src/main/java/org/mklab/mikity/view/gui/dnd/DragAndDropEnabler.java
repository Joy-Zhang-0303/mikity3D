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
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.XMLBox;
import org.mklab.mikity.model.xml.model.XMLCone;
import org.mklab.mikity.model.xml.model.XMLCylinder;
import org.mklab.mikity.model.xml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.model.XMLSphere;
import org.mklab.mikity.model.xml.model.XMLTrianglePolygon;


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
     * @see org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
    public void dragEnter(DropTargetEvent e) {
    // nothing to do
    }

    /**
     * @see org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
    public void dragLeave(DropTargetEvent e) {
    // nothing to do
    }

    /**
     * @see org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
    public void dragOperationChanged(DropTargetEvent e) {
    // nothing to do
    }

    /**
     * @see org.eclipse.swt.dnd.DropTargetListener#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
    public void dragOver(DropTargetEvent e) {
      final TreeItem item = DragAndDropEnabler.this.tree.getItem(DragAndDropEnabler.this.tree.toControl(e.x, e.y));
      if (item == null) {
        e.detail = DND.DROP_NONE;
      } else {
        e.detail = DND.DROP_MOVE;
      }
    }

    /**
     * @see org.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
    public void drop(DropTargetEvent e) {
      // 今マウスがある場所のアイテムを取得
      final TreeItem item = DragAndDropEnabler.this.tree.getItem(DragAndDropEnabler.this.tree.toControl(e.x, e.y));
      if (item == null) {
        return;
      }

      // ここに来たらどろっぷ成功なはず。
      Group group = null;
      TreeItem targetItem = null;

      if (item.getData() instanceof Group) {
        group = (Group)item.getData();
        targetItem = item;
      } else if (item.getParentItem() != null && item.getParentItem().getData() instanceof Group) {
        group = (Group)item.getParentItem().getData();
        targetItem = item.getParentItem();
      } else {
        return;
      }
      final Object obj = DragAndDropEnabler.this.treeItem.getData();
      final Group sourceGroup = (Group)DragAndDropEnabler.this.treeItem.getParentItem().getData();

      if (obj instanceof XMLBox) {
        sourceGroup.removeXMLBox((XMLBox)obj);
        group.addXMLBox((XMLBox)obj);
      } else if (obj instanceof XMLCylinder) {
        sourceGroup.removeXMLCylinder((XMLCylinder)obj);
        group.addXMLCylinder((XMLCylinder)obj);
      } else if (obj instanceof XMLSphere) {
        sourceGroup.removeXMLSphere((XMLSphere)obj);
        group.addXMLSphere((XMLSphere)obj);
      } else if (obj instanceof XMLCone) {
        sourceGroup.removeXMLCone((XMLCone)obj);
        group.addXMLCone((XMLCone)obj);
      } else if (obj instanceof XMLTrianglePolygon) {
        sourceGroup.removeXMLTrianglePolygon((XMLTrianglePolygon)obj);
        group.addXMLTrianglePolygon((XMLTrianglePolygon)obj);
      } else if (obj instanceof XMLQuadPolygon) {
        sourceGroup.removeXMLQuadPolygon((XMLQuadPolygon)obj);
        group.addXMLQuadPolygon((XMLQuadPolygon)obj);
      } else if (obj instanceof Group) {
        sourceGroup.removeGroup((Group)obj);
        group.addGroup((Group)obj);
      } else {
        throw new RuntimeException(Messages.getString("DragAndDropEnabler.0")); //$NON-NLS-1$
      }
      final TreeItem newItem = new TreeItem(targetItem, SWT.NONE);
      newItem.setText(DragAndDropEnabler.this.treeItem.getText());
      newItem.setData(obj);

      DragAndDropEnabler.this.treeItem.dispose();

    }

    /**
     * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
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
     * @see org.eclipse.swt.dnd.DragSourceListener#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
     */
    @Override
    public void dragStart(DragSourceEvent e) {
      // 選択要素が1つでないときは、終了
      if (DragAndDropEnabler.this.tree.getSelectionCount() != 1 || DragAndDropEnabler.this.tree.getSelection()[0].getParentItem() == null) {
        e.doit = false;
        return;
      }
      DragAndDropEnabler.this.treeItem = DragAndDropEnabler.this.tree.getSelection()[0];
    }

    /**
     * @see org.eclipse.swt.dnd.DragSourceListener#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
     */
    @Override
    public void dragSetData(DragSourceEvent e) {
    // nothing to do
    }

    /**
     * @see org.eclipse.swt.dnd.DragSourceListener#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
     */
    @Override
    public void dragFinished(DragSourceEvent e) {
    // nothing to do
    }

  }

}