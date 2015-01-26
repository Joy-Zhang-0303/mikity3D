/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;


public class ColumnArrayAdapter extends ArrayAdapter <View>{
  private Context _context;
  private int _viewResourceId;
  private LayoutInflater layoutInflater_;
  private List<View> _items;
  private int count = 0;

  public ColumnArrayAdapter(Context context, int resourceId, List<View> items) {
    super(context, resourceId, items);
    _context = context;
    _viewResourceId = resourceId;
    _items = items;
    layoutInflater_ = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
//    ColumnData item = (ColumnData)getItem(position);
    // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
    View view;
    if (convertView != null) {
      view = convertView;
    } else {
//      view = layoutInflater_.inflate(_viewResourceId, null);
      if(count==0)
        view = layoutInflater_.inflate(R.layout.line_item, null);
      else
        view = layoutInflater_.inflate(_viewResourceId, null);
      count++;
    }
    View item = _items.get(position);
//    ListView listView = (ListView)convertView.findViewById(R.id.list_view_column);
//    listView.addView(_items.get(position));
//    listView.addView(item, position, new LayoutParams(WC,WC));
//    Button button = (Button)view.findViewById(R.id.variousView);
    return view;
  }

}
