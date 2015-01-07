/*
 * Created on 2014/12/11
 * Copyright (C) 2014 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;
import java.util.Map;

import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


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
	  List<List<Map<String, String>>> allChildList = this.ndFragment.columnNumberList;
	  List<List<Map<String, String>>> targetNameList = this.ndFragment.targetNameList;
	  	  
	  ColumnNumberExpandableListAdapter adapter = new ColumnNumberExpandableListAdapter(getActivity(), parentList, allChildList, targetNameList, this);
	  
	  ExpandableListView lv = (ExpandableListView)this.view.findViewById(R.id.expandableListView1);
	  lv.setAdapter(adapter);
	  
	  lv.setOnChildClickListener(new OnChildClickListener() {
		  
		  public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
	                int childPosition, long id) {
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  return false;
		  }
	  });
	  
	  lv.setOnGroupClickListener(new OnGroupClickListener() {
		  
		  public boolean onGroupClick(ExpandableListView parent, View view,
				  int groupPosition, long id) {
			  
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  return false;
		  }
	  });
  }
  
  public void changeModelColumnNumber(int groupPosition, int childPosition, int columnNumber) {
    Mikity3dModel model = this.canvasActivity.canvasFragment.root.getModel(0);
    Group[] groupArray = model.getGroups();
    Group group = groupArray[0];
    group = group.getGroup(0);
    for(int i=0; i<groupPosition; i++) {
      group = group.getGroup(0);
    }
    group.getLinkData(childPosition).setColumnNumber(columnNumber);
    this.canvasActivity.canvasFragment.setModel();
    this.canvasActivity.canvasFragment.setGroupManager();
  }
}
