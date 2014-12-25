/*
 * Created on 2014/12/11
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
//    this.groupNameArray = this.ndFragment.groupNameArray;
//    this.columnNumberArray = this.ndFragment.columnNumberArray;
    sampleExpandableListView3();
    return this.view;
  }
//  @Override
//  public void onActivityCreated(Bundle savedInstanceState) {
//    super.onActivityCreated(savedInstanceState);
//    
//    ArrayAdapter<String> adapter =
//        new ArrayAdapter<String>(
//          getActivity(),android.R.layout.simple_list_item_1,
//          this.ndFragment.groupNameArray);
//    
//    setListAdapter(adapter);
//  }
  
  protected void sampleExpandableListView() {
	  List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();
	  List<List<Map<String, String>>> allChildList = new ArrayList<List<Map<String, String>>>();
	  
	  for (int i=0; i<this.ndFragment.groupNameArray.length; i++) {
		  Map<String, String> parentData = new HashMap<String, String>();
		  parentData.put("title", "title" + Integer.toString(i));
		  parentList.add(parentData);
	  }
	  
	  for (int i=0; i<this.ndFragment.groupNameArray.length; i++) {
		  List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
		  
		  for (int j=0; j<this.ndFragment.columnNumberArray.length; j++) {
			  Map<String, String> childData = new HashMap<String, String>();
			  childData.put("TITLE", "child" + Integer.toString(j));
			  childData.put("SUMMARY", "summary" + Integer.toString(j));
			  childList.add(childData);
		  }
		  
		  allChildList.add(childList);
	  }
	  
	  SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
			  this.getActivity(), parentList,
			  android.R.layout.simple_expandable_list_item_1,
			  new String[] { "title" }, new int[] { android.R.id.text1 },
			  allChildList, android.R.layout.simple_expandable_list_item_2,
			  new String[] { "TITLE", "SUMMARY" }, new int[] {
					  android.R.id.text1, android.R.id.text2 });
	  
	  ExpandableListView lv = (ExpandableListView)this.view.findViewById(R.id.expandableListView1);
	  lv.setAdapter(adapter);
	  
	  lv.setOnChildClickListener(new OnChildClickListener() {
		  
		  public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
	                int childPosition, long id) {
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  
			  Map<String, String> item = (Map<String, String>) adapter.getChild(groupPosition, childPosition);
			  
			  Toast.makeText(getActivity().getApplicationContext(), "child clicked" + item.get("TITLE") + " "
			  		+ item.get("SUMMARY"), Toast.LENGTH_LONG).show();
			  return false;
		  }
	  });
	  
	  lv.setOnGroupClickListener(new OnGroupClickListener() {
		  
		  public boolean onGroupClick(ExpandableListView parent, View view,
				  int groupPosition, long id) {
			  
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  
			  Map<String, String> item = (Map<String, String>) adapter.getGroup(groupPosition);
			  Toast.makeText(getActivity().getApplicationContext(), "parent clicked" + item.get("title"),
					  Toast.LENGTH_LONG).show();
			  return false;
		  }
	  });
  }
  
  protected void sampleExpandableListView2() {
	  List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();
	  List<List<Map<String, String>>> allChildList = new ArrayList<List<Map<String, String>>>();
	  
	  for (int i=0; i<this.groupNameArray.length; i++) {
		  Map<String, String> parentData = new HashMap<String, String>();
		  parentData.put("groupName", this.groupNameArray[i]);
		  parentList.add(parentData);
	  }
	  
	  for (int i=0; i<this.groupNameArray.length; i++) {
		  List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
		  
		  for (int j=0; j<this.columnNumberArray.length; j++) {
			  Map<String, String> childData = new HashMap<String, String>();
			  childData.put("columnNumber", String.valueOf(this.columnNumberArray[j]));
//			  childData.put("SUMMARY", "summary" + Integer.toString(j));
			  childList.add(childData);
		  }
		  
		  allChildList.add(childList);
	  }
	  
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
//			  Toast.makeText(getActivity().getApplicationContext(), "parent clicked" + item.get("groupName"),
//					  Toast.LENGTH_LONG).show();
			  return false;
		  }
	  });
  }
  
  protected void sampleExpandableListView3() {
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
