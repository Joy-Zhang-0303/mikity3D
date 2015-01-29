/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import org.mklab.mikity.model.searcher.GroupManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;


public class ColumnArrayAdapter extends ArrayAdapter <View>{
  private Context _context;
  private int _viewResourceId;
  private LayoutInflater layoutInflater_;
  private List<View> _items;
  private int count = 0;
  private List<GroupManager> lists;

  public ColumnArrayAdapter(Context context, int resourceId, List<View> items, List<GroupManager> lists) {
    super(context, resourceId, items);
    _context = context;
    _viewResourceId = resourceId;
    _items = items;
    layoutInflater_ = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.lists = lists;
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
//        view = layoutInflater_.inflate(R.layout.line_item, null);
        view = layoutInflater_.inflate(_viewResourceId, null);
      else
        view = layoutInflater_.inflate(_viewResourceId, null);
      count++;
    }
    final Button button = (Button)view.findViewById(R.id.variousView);
    button.setOnTouchListener(new OnTouchListener() {

      public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        button.setText("pussssssssssssed!!!!");
        return false;
      }
      
    });
//    View item = _items.get(position);
////    _items.get(1).findViewById(R.id.variousView).setFocusableInTouchMode(false);
////    _items.get(1).findViewById(R.id.variousView).setFocusable(false);
//    _items.get(1).setOnTouchListener(new OnTouchListener() {
//
//      public boolean onTouch(View v, MotionEvent event) {
//        ((Button)_items.get(1)).setText("pushed!!!!");
//        return false;
//      }
//    });
//    ListView listView = (ListView)convertView.findViewById(R.id.list_view_column);
//    listView.addView(_items.get(position));
//    listView.addView(item, position, new LayoutParams(WC,WC));
//    Button button = (Button)view.findViewById(R.id.variousView);
    return view;
  }

}
