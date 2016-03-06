/*
 * Created on 2016/01/08
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.model;

import java.util.List;

import org.mklab.mikity.android.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * グラフツリーを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2016/01/08
 */
public class GraphTree extends BaseAdapter {

  private LayoutInflater inflater;
  private TreeItem rootItem = null;

  /**
   * 新しく生成された<code>ModelTreeAdapter</code>オブジェクトを初期化します。
   * 
   * @param context Context
   */
  public GraphTree(Context context) {
    this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * {@inheritDoc}
   */
  public View getView(int position, @SuppressWarnings("unused") View convertView, ViewGroup parent) {
    final TextView view = (TextView)this.inflater.inflate(R.layout.scene_graph_tree_item, null);
    final TreeItem treeItem = (TreeItem)getItem(position);

    String itemText;
    if (treeItem.hasItems()) {
      if (treeItem.isExpanded()) {
        itemText = "- " + treeItem.getText(); //$NON-NLS-1$
      } else {
        itemText = "+ " + treeItem.getText(); //$NON-NLS-1$
      }
    } else {
      itemText = treeItem.getText();
    }

    final ListView listView = (ListView)parent;
    
    if (listView.getCheckedItemPosition() == position) {
      final SpannableString spannable = new SpannableString(Html.fromHtml(itemText));
      final BackgroundColorSpan bgcolor = new BackgroundColorSpan(Color.parseColor("#CCCCFF")); //$NON-NLS-1$
      if (treeItem.hasItems()) {
        spannable.setSpan(bgcolor, 2, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      } else {
        spannable.setSpan(bgcolor, 0, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      }
      view.setText(spannable);
    } else {
      view.setText(itemText);  
    }

    view.setPadding(treeItem.getDepth() * 20, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    
    return view;
  }
  
  /**
   * ルート要素を設定します。
   * 
   * @param rootItem ルート要素
   */
  public void setRootItem(TreeItem rootItem) {
    this.rootItem = rootItem;
  }

  /**
   * {@inheritDoc}
   */
  public int getCount() {
    return this.rootItem == null ? 0 : this.rootItem.getItemsCount();
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
   * 要素のリストを返します。
   * 
   * @return 要素のリスト
   */
  public List<TreeItem> getItems() {
    return this.rootItem.getItems();
  }

  /**
   * ツリーから全てのItemを消去します。
   */
  public void clearTree() {
    this.rootItem.clearTree();
  }
}
