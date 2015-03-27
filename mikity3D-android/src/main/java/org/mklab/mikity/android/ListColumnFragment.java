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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import roboguice.fragment.RoboFragment;


/**
 * モデルのグループデータをListViewとして表示するためのFragmentです。
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class ListColumnFragment extends RoboFragment {
  
  /** リストビュー */
  public ListView listView;
  private View view;
  /** グループリスト */
  public List<GroupManager> groupList;
  /** GroupNamerの親の管理のためのグループマネージャー */
  public GroupManager groupManager;
  int groupPosition = 0;
  private Button backButton;
  private NavigationDrawerFragment fragment;
  List<Integer> targetColumn = new ArrayList<Integer>();
  
  /**
   * @param savedInstanceState Bundle
   * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.set_column_fragment, container, false);
    this.listView = (ListView)this.view.findViewById(R.id.list_view_column);
    configureListView();
    this.backButton = (Button)this.view.findViewById(R.id.rootButton);
    this.backButton.setOnClickListener(new OnClickListener() {

      /**
       * @param v View
       */
      public void onClick(View v) {
        if(ListColumnFragment.this.groupManager.getParent() != null) {
          ListColumnFragment.this.groupManager = ListColumnFragment.this.groupManager.getParent();
          ListColumnFragment.this.groupList = ListColumnFragment.this.groupManager.getItems();
          int size = ListColumnFragment.this.targetColumn.size()-1;
          ListColumnFragment.this.targetColumn.remove(size);
          configureListView();
        }
      }
    });
    return this.view;
  }
  
  /**
   * リストビューをアダプタに登録し、リストの処理します。
   */
  public void configureListView() {
    ColumnArrayAdapter adapter = new ColumnArrayAdapter(this.getActivity(), R.layout.list_groupname, this.groupList, this.fragment, this.targetColumn);
    this.listView.setAdapter(adapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      /**
       * @param view1 View
       * @param id id
       */
      @SuppressWarnings("boxing")
      public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
        ListView listView1 = (ListView)parent;
        if(listView1.getItemAtPosition(position).getClass() == GroupName.class) {
          ListColumnFragment.this.groupPosition = position;
          ListColumnFragment.this.groupManager = ListColumnFragment.this.groupList.get(ListColumnFragment.this.groupPosition);
          ListColumnFragment.this.groupList = ListColumnFragment.this.groupManager.getItems();
          ListColumnFragment.this.targetColumn.add(position);
          configureListView();
        }
      }  
    });
  }
  
  /**
   * グループマネージャーとそのリストを初期化します。
   * @param list GroupManager
   */
  public void setGroupManager(GroupManager list) {
    this.groupManager = list;
    this.groupList = list.getItems();
  }

  /**
   * NavigationDrawerFragmentを設定します。
   * @param fragment NavigationDrawerFragment
   */
  public void setNavigationDrawerFragment(NavigationDrawerFragment fragment) {
    this.fragment = fragment;
  }
}
