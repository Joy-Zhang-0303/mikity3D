/*
 * Created on 2015/01/07
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class ColumnNumberExpandableListAdapter extends BaseExpandableListAdapter {

  
  private List<Map<String, String>> parentList;
  private List<List<Map<String, String>>> allChildList;
  private Context context = null;
  
  public ColumnNumberExpandableListAdapter(Context ctx, List<Map<String, String>> parentList,
                                    List<List<Map<String, String>>> allChildList) {
    this.context = ctx;
    this.parentList = parentList;
    this.allChildList = allChildList;
  }
  
  public View getGenericView() {
    View view = LayoutInflater.from(this.context).inflate(R.layout.line_item, null);
    return view;
  }
  
  public TextView getGroupGenericView() {
    AbsListView.LayoutParams param = new AbsListView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 64);
     
    TextView textView = new TextView(this.context);
    textView.setLayoutParams(param);
     
    textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
    textView.setPadding(64, 0, 0, 0);
     
    return textView;
}
  
  public int getGroupCount() {
    return allChildList.size();
  }

  public int getChildrenCount(int groupPosition) {
    return allChildList.get(groupPosition).size();
  }

  public Object getGroup(int groupPosition) {
    return parentList.get(groupPosition);
  }

  public Object getChild(int groupPosition, int childPosition) {
    return allChildList.get(groupPosition).get(childPosition);
  }

  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  public boolean hasStableIds() {
    return true;
  }

  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    TextView textView = getGroupGenericView();
    textView.setText(getGroup(groupPosition).toString());
    return textView;
  }

  public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    View childView = getGenericView();
    TextView textView = (TextView)childView.findViewById(R.id.member_list);
    textView.setText(allChildList.get(groupPosition).get(childPosition).toString());
    
    return childView;
  }

  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

}
