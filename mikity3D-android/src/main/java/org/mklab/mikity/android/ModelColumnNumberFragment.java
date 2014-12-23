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

import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;


public class ModelColumnNumberFragment extends ListFragment{
  
  View view;
  CanvasActivity canvasActivity;
  NavigationDrawerFragment ndFragment;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.model_column_number_fragment, container, false);
    this.canvasActivity = (CanvasActivity)getActivity();
    this.ndFragment = this.canvasActivity.ndFragment;
    sampleExpandableListView();
    return this.view;
  }
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(
          getActivity(),android.R.layout.simple_list_item_1,
          this.ndFragment.groupNameArray);
    
    setListAdapter(adapter);
  }
  
  protected void sampleExpandableListView() {
	  List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();
	  List<List<Map<String, String>>> allChildList = new ArrayList<List<Map<String, String>>>();
	  
	  for (int i=0; i<this.ndFragment.groupNameArray.length; i++) {
		  Map<String, String> parentData = new HashMap<String, String>();
		  parentData.put("title", "title" + Integer.toString(i));
		  parentList.add(parentData);
	  }
	  
	  for (int i=0; i<this.ndFragment.columnNumberArray.length; i++) {
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
}
