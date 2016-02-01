package org.mklab.mikity.android.slidingmenu.adapter;

import java.util.ArrayList;

import org.mklab.mikity.android.R;
import org.mklab.mikity.android.slidingmenu.NavDrawerItem;

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
public class NavDrawerListAdapter extends BaseAdapter {

  private Context context;
  private ArrayList<NavDrawerItem> items;

  /**
   * 新しく生成された<code>NavDrawerListAdapter</code>オブジェクトを初期化します。
   * 
   * @param context コンテキスト
   * @param items 項目
   */
  public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> items) {
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
