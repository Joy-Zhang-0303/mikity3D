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
package org.mklab.mikity.android.settings.adapter;

import java.util.ArrayList;

import org.mklab.mikity.android.R;
import org.mklab.mikity.android.settings.SettingsDrawerItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 
 * @version $Revision$, 2016/02/01
 */
public class SettingsDrawerListAdapter extends BaseAdapter {

  private Context context;
  private ArrayList<SettingsDrawerItem> items;

  /**
   * 新しく生成された<code>NavDrawerListAdapter</code>オブジェクトを初期化します。
   * 
   * @param context コンテキスト
   * @param items 項目
   */
  public SettingsDrawerListAdapter(Context context, ArrayList<SettingsDrawerItem> items) {
    this.context = context;
    this.items = items;
  }

  /**
   * {@inheritDoc}
   */
  public int getCount() {
    return this.items.size();
  }

  /**
   * {@inheritDoc}
   */
  public Object getItem(int position) {
    return this.items.get(position);
  }

  /**
   * {@inheritDoc}
   */
  public long getItemId(int position) {
    return position;
  }

  /**
   * {@inheritDoc}
   */
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater mInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = mInflater.inflate(R.layout.drawer_list_item, null);
    }

    ImageView icon = (ImageView)convertView.findViewById(R.id.navicon);
    TextView title = (TextView)convertView.findViewById(R.id.title);
    TextView counter = (TextView)convertView.findViewById(R.id.counter);

    icon.setImageResource(this.items.get(position).getIcon());
    title.setText(this.items.get(position).getTitle());

    if (this.items.get(position).getCounterVisiblity()) {
      counter.setText(this.items.get(position).getCount());
    } else {
      counter.setVisibility(View.GONE);
    }

    return convertView;

  }
}
