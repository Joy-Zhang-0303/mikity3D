/*
 * Created on 2015/01/23
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.searcher.GroupManager;
import org.mklab.mikity.model.searcher.GroupNameManager;

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
 * モデルのグループをListViewとして表示するためのFragmentです。
 * 
 * @author soda
 * @version $Revision$, 2015/02/03
 */
public class SourceNumberFragment extends RoboFragment {

  /** リストビュー */
  private ListView listView;

  private View view;

  /** グループリスト */
  List<GroupManager> groupManagers;

  /** GroupManagerの親を管理するためのグループマネージャー */
  GroupManager parentGroupManager;

  int groupPosition = 0;
  private Button backButton;
  private NavigationDrawerFragment fragment;
  List<Integer> targetNumbers = new ArrayList<Integer>();

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.view = inflater.inflate(R.layout.set_column_fragment, container, false);
    this.listView = (ListView)this.view.findViewById(R.id.list_view_column);
    configureListView();
    this.backButton = (Button)this.view.findViewById(R.id.rootButton);
    this.backButton.setOnClickListener(new OnClickListener() {

      /**
       * {@inheritDoc}
       */
      public void onClick(View v) {
        if (SourceNumberFragment.this.parentGroupManager.getParent() == null) {
          return;
        }

        SourceNumberFragment.this.parentGroupManager = SourceNumberFragment.this.parentGroupManager.getParent();
        SourceNumberFragment.this.groupManagers = SourceNumberFragment.this.parentGroupManager.getItems();
        final int size = SourceNumberFragment.this.targetNumbers.size() - 1;
        SourceNumberFragment.this.targetNumbers.remove(size);
        configureListView();
      }
    });
    return this.view;
  }

  /**
   * リストビューをアダプタに登録し、リストの処理します。
   */
  void configureListView() {
    final SourceNumberAdapter adapter = new SourceNumberAdapter(this.getActivity(), R.layout.list_groupname, this.groupManagers, this.fragment, this.targetNumbers);
    this.listView.setAdapter(adapter);
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      /**
       * {@inheritDoc}
       */
      @SuppressWarnings("boxing")
      public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
        final ListView listView1 = (ListView)parent;
        if (listView1.getItemAtPosition(position).getClass() != GroupNameManager.class) {
          return;
        }

        SourceNumberFragment.this.groupPosition = position;
        SourceNumberFragment.this.parentGroupManager = SourceNumberFragment.this.groupManagers.get(SourceNumberFragment.this.groupPosition);
        SourceNumberFragment.this.groupManagers = SourceNumberFragment.this.parentGroupManager.getItems();
        SourceNumberFragment.this.targetNumbers.add(position);
        configureListView();
      }
    });
  }

  /**
   * グループマネージャーとそのリストを初期化します。
   * 
   * @param groupManager GroupManager
   */
  public void setGroupManager(GroupManager groupManager) {
    this.parentGroupManager = groupManager;
    this.groupManagers = groupManager.getItems();
  }

  /**
   * NavigationDrawerFragmentを設定します。
   * 
   * @param fragment NavigationDrawerFragment
   */
  public void setNavigationDrawerFragment(NavigationDrawerFragment fragment) {
    this.fragment = fragment;
  }
}
