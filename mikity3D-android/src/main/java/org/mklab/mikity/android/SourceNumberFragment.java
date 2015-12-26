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
  /** グループリスト */
  List<GroupManager> groupManagers;

  /** GroupManagerの親を管理するためのグループマネージャー */
  GroupManager parentGroupManager;

  /** NavigationDrawerFragment */
  private NavigationDrawerFragment parentFragment;
  List<Integer> targetNumbers = new ArrayList<Integer>();

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.set_source_number_fragment, container, false);
    this.listView = (ListView)view.findViewById(R.id.list_view_source_number);
    configureListView();
    
    final Button backButton = (Button)view.findViewById(R.id.backButton);
    backButton.setOnClickListener(new OnClickListener() {

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
    
    return view;
  }

  /**
   * リストビューをアダプタに登録し、リストの処理します。
   */
  void configureListView() {
    final SourceNumberAdapter adapter = new SourceNumberAdapter(this.getActivity(), R.layout.list_group_name, this.groupManagers, this.parentFragment, this.targetNumbers);
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

        final int groupPosition = position;
        SourceNumberFragment.this.parentGroupManager = SourceNumberFragment.this.groupManagers.get(groupPosition);
        SourceNumberFragment.this.groupManagers = SourceNumberFragment.this.parentGroupManager.getItems();
        SourceNumberFragment.this.targetNumbers.add(position);
        configureListView();
      }
    });
  }

  /**
   * グループマネージャーとそのリストを初期化します。
   * 
   * @param parentGroupManager GroupManager
   */
  public void setParentGroupManager(GroupManager parentGroupManager) {
    this.parentGroupManager = parentGroupManager;
    this.groupManagers = parentGroupManager.getItems();
  }

  /**
   * NavigationDrawerFragmentを設定します。
   * 
   * @param parentFragment NavigationDrawerFragment
   */
  public void setParentFragment(NavigationDrawerFragment parentFragment) {
    this.parentFragment = parentFragment;
  }
}
