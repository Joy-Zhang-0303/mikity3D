package org.mklab.mikity.android.mainmenu.adapter;

import java.util.List;

import org.mklab.mikity.android.R;
import org.mklab.mikity.android.mainmenu.MainMenuDrawerItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author hirae 
 * @version $Revision$, 2016/02/01
 */
public class MainMenuDrawerListAdapter extends BaseAdapter {

  private Context context;
  private List<MainMenuDrawerItem> items;

  /**
   * 新しく生成された<code>NavDrawerListAdapter</code>オブジェクトを初期化します。
   * 
   * @param context コンテキスト
   * @param items 項目
   */
  public MainMenuDrawerListAdapter(Context context, List<MainMenuDrawerItem> items) {
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

    final ImageView icon = (ImageView)convertView.findViewById(R.id.navicon);
    final TextView title = (TextView)convertView.findViewById(R.id.title);
    final TextView counter = (TextView)convertView.findViewById(R.id.counter);

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
