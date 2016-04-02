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
package org.mklab.mikity.swt.gui.dnd;

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
import org.mklab.mikity.model.xml.simplexml.model.AbstractObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadrangleModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TriangleModel;


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
    public void dragEnter(@SuppressWarnings("unused") DropTargetEvent e) {
      // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dragLeave(@SuppressWarnings("unused") DropTargetEvent e) {
      // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dragOperationChanged(@SuppressWarnings("unused") DropTargetEvent e) {
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
        group.add((AbstractObjectModel)obj);
      } else if (obj instanceof CylinderModel) {
        sourceGroup.remove((CylinderModel)obj);
        group.add((AbstractObjectModel)obj);
      } else if (obj instanceof SphereModel) {
        sourceGroup.remove((SphereModel)obj);
        group.add((AbstractObjectModel)obj);
      } else if (obj instanceof ConeModel) {
        sourceGroup.remove((ConeModel)obj);
        group.add((ConeModel)obj);
      } else if (obj instanceof CapsuleModel) {
        sourceGroup.remove((CapsuleModel)obj);
        group.add((AbstractObjectModel)obj);
      } else if (obj instanceof TriangleModel) {
        sourceGroup.remove((TriangleModel)obj);
        group.add((TriangleModel)obj);
      } else if (obj instanceof QuadrangleModel) {
        sourceGroup.remove((QuadrangleModel)obj);
        group.add((QuadrangleModel)obj);
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
    public void dropAccept(@SuppressWarnings("unused") DropTargetEvent e) {
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
    public void dragSetData(@SuppressWarnings("unused") DragSourceEvent e) {
      // nothing to do
    }

    /**
     * {@inheritDoc}
     */
    public void dragFinished(@SuppressWarnings("unused") DragSourceEvent e) {
      // nothing to do
    }

  }

}