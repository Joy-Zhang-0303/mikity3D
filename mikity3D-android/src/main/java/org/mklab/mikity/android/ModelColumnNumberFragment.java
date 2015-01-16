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
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


/**
 * モデルのターゲットに対する番号を設定するためのフラグメントです。
 * @author soda
 * @version $Revision$, 2015/01/16
 */
public class ModelColumnNumberFragment extends RoboFragment {
  
  View view;
  CanvasActivity canvasActivity;
  NavigationDrawerFragment ndFragment;
  String[] groupNameArray;
  int[] columnNumberArray; 
  
  /**
   * @param savedInstanceState Bundle
   * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
   */
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
		  
		  /**
       * @param parent ExpandableListView
		   * @param v  view
		   * @param groupPosition 親のリスト
		   * @param childPosition 子供のリスト
		   * @param id id
       */
		  public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
	                int childPosition, long id) {
//			  ExpandableListAdapter adapter1 = parent.getExpandableListAdapter();
			  return false;
		  }
	  });
	  
	  lv.setOnGroupClickListener(new OnGroupClickListener() {
		  
		  /**
       * @param parent ExpandableListView 
		   * @param view1 view
		   * @param groupPosition 親のリスト
		   * @param id id
       */
		  public boolean onGroupClick(ExpandableListView parent, View view1,
				  int groupPosition, long id) {
			  
//			  ExpandableListAdapter adapter = parent.getExpandableListAdapter();
			  return false;
		  }
	  });
  }
  
  /**
   * @param groupPosition 親の場所
   * @param childPosition 子供の場所
   * @param columnNumber カラムナンバー
   */
  public void changeModelColumnNumber(int groupPosition, int childPosition, int columnNumber) {
    Mikity3dModel model = this.canvasActivity.canvasFragment.root.getModel(0);
    Group[] groupArray = model.getGroups();
    Group group = groupArray[0];
    group = group.getGroup(0);
    for(int i=0; i<groupPosition; i++) {
      group = group.getGroup(0);
    }
    group.getLinkData(childPosition).setColumnNumber(columnNumber);
    this.canvasActivity.canvasFragment.configurateModel();
    this.canvasActivity.canvasFragment.setGroupManager();
  }
  
  /**
   * @param message エラーメッセージ
   */
  public void setExceptionDailogFragment(String message) {
    DialogFragment dialogFragment = new ExceptionDialogFragment();
    ((ExceptionDialogFragment)dialogFragment).setMessage(message);
    dialogFragment.show(getFragmentManager(), "exceptionDialogFragment"); //$NON-NLS-1$
  }
}
