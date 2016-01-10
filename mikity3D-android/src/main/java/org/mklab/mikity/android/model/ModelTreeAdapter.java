/*
 * Created on 2016/01/08
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.model;

import org.mklab.mikity.android.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * モデルツリーのアダプタを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/08
 */
public class ModelTreeAdapter extends BaseAdapter {

  private LayoutInflater inflater;
  TreeItem rootItem = null;

  /**
   * 新しく生成された<code>ModelTreeAdapter</code>オブジェクトを初期化します。
   * 
   * @param context Context
   */
  public ModelTreeAdapter(Context context) {
    this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * {@inheritDoc}
   */
  public View getView(int position, @SuppressWarnings("unused") View convertView, @SuppressWarnings("unused") ViewGroup parent) {
    final TextView view = (TextView)this.inflater.inflate(R.layout.model_tree_item, null);
    TreeItem treeItem = (TreeItem)getItem(position);
    view.setText(treeItem.getText());
    view.setPadding(treeItem.getDepth() * 20, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());

    return view;
  }

  /**
   * データを追加します。
   * 
   * @param item データ
   * @return　ツリーの要素
   */
  public TreeItem add(Object item) {
    if (this.rootItem == null) {
      this.rootItem = new TreeItem(this);
    }
    //return this.rootItem.add(item);
    
    return new TreeItem(this.rootItem, item);
  }

  /**
   * {@inheritDoc}
   */
  public int getCount() {
    return this.rootItem == null ? 0 : this.rootItem.getCount();
  }

  /**
   * {@inheritDoc}
   */
  public Object getItem(int position) {
    return this.rootItem == null ? null : this.rootItem.getItem(position + 1);
  }

  /**
   * {@inheritDoc}
   */
  public long getItemId(int position) {
    return position;
  }

  /**
   * ツリーから全てのItemを消去します。
   */
  public void clearTree() {
    if (this.rootItem == null) {
      return;
    }

    this.rootItem.clearTree();
  }
}
