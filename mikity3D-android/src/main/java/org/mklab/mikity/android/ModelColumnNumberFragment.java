/*
 * Created on 2014/12/11
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;
import java.util.Map;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;


public class ModelColumnNumberFragment extends RoboFragment {
  
  View view;
  CanvasActivity canvasActivity;
  NavigationDrawerFragment ndFragment;
  String[] groupNameArray;
  int[] columnNumberArray;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.model_column_number_fragment, container, false);
    this.canvasActivity = (CanvasActivity)getActivity();
    this.ndFragment = this.canvasActivity.ndFragment;
    setExpandableListView();
    return this.view;
  }
  
  protected void setExpandableListView() {
	  List<Map<String, String>> parentList = this.ndFragment.groupNameList;
	  List<List<Map<String, String>>> allChildList = this.ndFragment.columnNUmberList;
	  	  
	  SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
			  this.getActivity(), parentList,
			  android.R.layout.simple_expandable_list_item_1,
			  new String[] { "groupName" }, new int[] { android.R.id.text1 },
			  allChildList, android.R.layout.simple_expandable_list_item_2,
			  new String[] { "columnNumber" }, new int[] {
					  android.R.id.text1 });
	  
	  ExpandableListView lv = (ExpandableListView)this.view.findViewById(R.id.expandableListView1);
	  lv.setAdapter(adapter);
	  
	  lv.setOnChildClickListener(new OnChildClickListener() {
		  
		  public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
	                int childPosition, long id) {
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  
			  Map<String, String> item = (Map<String, String>) adapter.getChild(groupPosition, childPosition);
			  
			  Toast.makeText(getActivity().getApplicationContext(), "child clicked" + item.get("columnNumber"), Toast.LENGTH_LONG).show();
			  return false;
		  }
	  });
	  
	  lv.setOnGroupClickListener(new OnGroupClickListener() {
		  
		  public boolean onGroupClick(ExpandableListView parent, View view,
				  int groupPosition, long id) {
			  
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  
			  Map<String, String> item = (Map<String, String>) adapter.getGroup(groupPosition);
			  Toast.makeText(getActivity().getApplicationContext(), "parent clicked" + item.get("groupName"),
					  Toast.LENGTH_LONG).show();
			  return false;
		  }
	  });
  }
}
