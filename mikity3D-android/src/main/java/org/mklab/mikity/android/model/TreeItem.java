/*
 * Created on 2016/01/10
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.model;

import java.util.ArrayList;
import java.util.List;


/**
 * ツリーの要素を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/08
 */
public class TreeItem {
  private TreeItem parent;
  private List<TreeItem> treeItems = new ArrayList<TreeItem>();
  private int depth = -1;

  private boolean isExpanded = false;
  private Object data = null;
  private String text = null;

  /**
   * 新しく生成された<code>TreeItem</code>オブジェクトを初期化します。
   * 
   * ルートオブジェクト専用
   */
  TreeItem() {
    this.isExpanded = true;
  }

  /**
   * 新しく生成された<code>TreeItem</code>オブジェクトを初期化します。
   * 
   * @param parent 親
   * @param value データ
   */
  public TreeItem(TreeItem parent, Object value) {
    this.parent = parent;
    this.depth = parent.depth + 1;
    this.data = value;
    this.text = value.toString();

    parent.treeItems.add(this);
  }

  /**
   * テキストを設定します。
   * 
   * @param text テキスト
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * テキストを返します。
   * 
   * @return テキスト
   */
  public String getText() {
    return this.text;
  }

  /**
   * 要素を数を返します。
   * 
   * @return 要素を数
   */
  int getCount() {
    if (this.treeItems.size() == 0 || this.isExpanded == false) {
      return 0;
    }

    int count = this.treeItems.size();
    for (TreeItem item : this.treeItems) {
      count += item.getCount();
    }
    return count;
  }
  
  /**
   * 親を返します。
   * 
   * @return 親
   */
  TreeItem getParentItem() {
    return this.parent;
  }

  /**
   * 要素を返します。
   * 
   * @param itemPosition 要素の位置
   * @return 要素
   */
  TreeItem getItem(int itemPosition) {
    if (itemPosition == 0) {
      return this;
    }

    if (this.treeItems.size() == 0 || !this.isExpanded) {
      return null;
    }

    int position = itemPosition;

    for (int i = 0; position >= 0 && i < this.treeItems.size(); i++) {
      TreeItem item = this.treeItems.get(i);
      --position;

      int count = item.getCount();
      if (count >= position) {
        return item.getItem(position);
      }
      position -= count;
    }

    return null;
  }

  /**
   * 展開します。
   */
  public void expand() {
    if (hasChild() == false) {
      return;
    }
    if (this.isExpanded) {
      return;
    }
    this.isExpanded = true;
  }

  /**
   * つぶします。
   */
  public void collapse() {
    if (!this.isExpanded) {
      return;
    }

    this.isExpanded = false;
  }

  /**
   * 展開されているか判定します。
   * 
   * @return 展開されていればtrue
   */
  public boolean isExpanded() {
    return this.isExpanded;
  }

  /**
   * 深さを返します。
   * 
   * @return 深さ
   */
  public int getDepth() {
    return this.depth;
  }

  /**
   * データを返します。
   * 
   * @return データ
   */
  public Object getData() {
    return this.data;
  }

  /**
   * 子要素を持つか判定します。
   * 
   * @return 子要素を持てばtrue
   */
  public boolean hasChild() {
    return this.treeItems.isEmpty() == false;
  }

  /**
   * ツリーから全てのItemを消去します。
   */
  public void clearTree() {
    if (getCount() == 0) {
      return;
    }

    for (final TreeItem item : this.treeItems) {
      removeItemsFromTree(item);
      item.dispose();
    }
  }

  /**
   * データを削除します。
   */
  private void dispose() {
    this.treeItems.clear();
    this.data = null;
    this.text = null;
  }

  /**
   * ツリーからItemを削除します。
   * 
   * @param items Item郡
   */
  private void removeItemsFromTree(TreeItem items) {
    for (final TreeItem item : items.treeItems) {
      if (item.getCount() != 0) {
        removeItemsFromTree(item);
      }
      item.dispose();
    }
  }
}
