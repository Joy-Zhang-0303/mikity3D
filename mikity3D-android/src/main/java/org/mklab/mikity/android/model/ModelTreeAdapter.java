/*
 * Created on 2016/01/08
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.model;

import org.mklab.mikity.android.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
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
    final TextView view = (TextView)this.inflater.inflate(R.layout.scene_graph_item, null);
    final TreeItem treeItem = (TreeItem)getItem(position);

    String itemText;
    if (treeItem.hasChild()) {
      if (treeItem.isExpanded()) {
        itemText = "- " + treeItem.getText(); //$NON-NLS-1$
      } else {
        itemText = "+ " + treeItem.getText(); //$NON-NLS-1$
      }
    } else {
      itemText = treeItem.getText();
    }


    final ListView listView = (ListView)parent;
    
    int p = listView.getCheckedItemPosition();
    
    if (listView.getCheckedItemPosition() == position) {
      CharSequence str = Html.fromHtml(itemText); //$NON-NLS-1$ //$NON-NLS-2$
      SpannableString spannable = new SpannableString(str);
      BackgroundColorSpan bgcolor = new BackgroundColorSpan(Color.parseColor("#CCCCFF")); //$NON-NLS-1$
      spannable.setSpan(bgcolor, 2, spannable.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
      view.setText(spannable);
    } else {
      view.setText(itemText);  
    }

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
      this.rootItem = new TreeItem();
    }

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
