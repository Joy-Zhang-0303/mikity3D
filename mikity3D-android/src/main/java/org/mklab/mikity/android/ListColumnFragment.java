/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.List;

import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupName;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import roboguice.fragment.RoboFragment;


public class ListColumnFragment extends RoboFragment {
  
  public ListView listView;
  private View view;
  private List<GroupManager> groupList;
  private GroupManager groupManager;
  private int groupPosition;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.set_column_fragment, container, false);
    this.listView = (ListView)view.findViewById(R.id.list_view_column);
    configureListView();
    return view;
  }
  
  
  public void configureListView() {
    ColumnArrayAdapter adapter = new ColumnArrayAdapter(this.getActivity(), R.layout.list_groupname, groupList);
    this.listView.setAdapter(adapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView)parent;
        if(listView.getItemAtPosition(position).getClass() == GroupName.class) {
          ListColumnFragment.this.groupPosition = position;
            ListColumnFragment.this.groupList = ListColumnFragment.this.groupList.get(ListColumnFragment.this.groupPosition).getItems();
          configureListView();
        }
      }
      
    });
  }
  
  public void setGroupManager(GroupManager list) {
    this.groupManager = list;
    this.groupList = list.getItems();
  }
}
