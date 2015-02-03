/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupName;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import roboguice.fragment.RoboFragment;


public class ListColumnFragment extends RoboFragment {
  
  public ListView listView;
  private View view;
  private List<GroupManager> groupList;
  private GroupManager groupManager;
  private int groupPosition = 0;
  private Button backButton;
  private NavigationDrawerFragment fragment;
  private int groupDepth = 0;
  private List<Integer> targetColumn = new ArrayList<Integer>();
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.set_column_fragment, container, false);
    this.listView = (ListView)this.view.findViewById(R.id.list_view_column);
//    targetColumn.add(0);
    configureListView();
    this.backButton = (Button)this.view.findViewById(R.id.rootButton);
    this.backButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        if(ListColumnFragment.this.groupManager.getParent() != null) {
          ListColumnFragment.this.groupManager = ListColumnFragment.this.groupManager.getParent();
          ListColumnFragment.this.groupList = ListColumnFragment.this.groupManager.getItems();
          groupDepth--;
          int size = targetColumn.size()-1;
          targetColumn.remove(size);
          configureListView();
        }
      }
    });
    return view;
  }
  
  
  public void configureListView() {
//    targetColumn.add(groupDepth);
    ColumnArrayAdapter adapter = new ColumnArrayAdapter(this.getActivity(), R.layout.list_groupname, groupList, fragment, targetColumn);
    this.listView.setAdapter(adapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView)parent;
        if(listView.getItemAtPosition(position).getClass() == GroupName.class) {
          ListColumnFragment.this.groupPosition = position;
          ListColumnFragment.this.groupManager = ListColumnFragment.this.groupList.get(ListColumnFragment.this.groupPosition);
          ListColumnFragment.this.groupList = groupManager.getItems();
          groupDepth++;
          targetColumn.add(position);
          configureListView();
        }
      }  
    });
  }
  
  public void setGroupManager(GroupManager list) {
    this.groupManager = list;
    this.groupList = list.getItems();
  }


  public void setNavigationDrawerFragment(NavigationDrawerFragment fragment) {
    this.fragment = fragment;
  }
}
