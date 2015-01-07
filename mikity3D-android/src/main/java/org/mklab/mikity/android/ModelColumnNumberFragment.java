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
import org.mklab.mikity.model.xml.simplexml.model.LinkData;

import roboguice.fragment.RoboFragment;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
  public Button minusButton;
  public Button plusButton;
  public EditText columnEditText; 
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.model_column_number_fragment, container, false);
    this.canvasActivity = (CanvasActivity)getActivity();
    this.ndFragment = this.canvasActivity.ndFragment;
    setExpandableListView();
//    this.minusButton = (Button)view.findViewById(R.id.minusButton);
//    this.plusButton = (Button)view.findViewById(R.id.plusButton);
    return this.view;
  }
  
  protected void setExpandableListView() {
	  List<Map<String, String>> parentList = this.ndFragment.groupNameList;
	  List<List<Map<String, String>>> allChildList = this.ndFragment.columnNumberList;
	  List<List<Map<String, String>>> targetNameList = this.ndFragment.targetNameList;
	  	  
//	  SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
//			  this.getActivity(), parentList,
//			  android.R.layout.simple_expandable_list_item_1,
//			  new String[] { "groupName" }, new int[] { android.R.id.text1 },
//			  allChildList, android.R.layout.simple_expandable_list_item_2,
//			  new String[] { "columnNumber" }, new int[] {
//					  android.R.id.text1 });
	  ColumnNumberExpandableListAdapter adapter = new ColumnNumberExpandableListAdapter(getActivity(), parentList, allChildList, targetNameList, this);
	  
	  ExpandableListView lv = (ExpandableListView)this.view.findViewById(R.id.expandableListView1);
	  lv.setAdapter(adapter);
	  
	  lv.setOnChildClickListener(new OnChildClickListener() {
		  
		  public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
	                int childPosition, long id) {
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  
			  DialogFragment newFragment = new ColumnNumberSelectionDialogFragment(ModelColumnNumberFragment.this.canvasActivity, groupPosition, childPosition);
		        newFragment.show(ModelColumnNumberFragment.this.canvasActivity.getFragmentManager(), "contact_us");
			  
			  
//			  Map<String, String> item = (Map<String, String>) adapter.getChild(groupPosition, childPosition);
//			  
//			  Toast.makeText(getActivity().getApplicationContext(), "child clicked" + item.get("columnNumber"), Toast.LENGTH_LONG).show();
			  return false;
		  }
	  });
	  
	  lv.setOnGroupClickListener(new OnGroupClickListener() {
		  
		  public boolean onGroupClick(ExpandableListView parent, View view,
				  int groupPosition, long id) {
			  
			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  
//			  Map<String, String> item = (Map<String, String>) adapter.getGroup(groupPosition).;
//			  Toast.makeText(getActivity().getApplicationContext(), "parent clicked" + item.get("groupName"),
//					  Toast.LENGTH_LONG).show();
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
//    group.setLinkData(columnNumber, group.getLinkData(childPosition));
  }
}
