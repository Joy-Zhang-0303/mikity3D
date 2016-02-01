/*
 * Created on 2015/02/19
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;

import org.mklab.mikity.android.slidingmenu.NavDrawerItem;
import org.mklab.mikity.android.slidingmenu.adapter.NavDrawerListAdapter;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * メインメニューを表すフラグメントです。
 * 
 * @author koga
 * @version $Revision$, 2015/03/26
 */
public class MainMenuFragment extends Fragment {

  private DrawerLayout mDrawerLayout;
  private ListView mainMenuList;

  /** nav drawer title */
  //private CharSequence mDrawerTitle;

  /** used to store app title */
  //private CharSequence mTitle;

  /** slide menu items */
  private String[] mainMenuTitles;

  private ArrayList<NavDrawerItem> mainMenuItems;
  private NavDrawerListAdapter adapter;

  CanvasActivity canvasActivity;
  
  /**
   * アクティビティを設定します。
   * 
   * @param activity アクティビティ
   */
  public void setActivity(CanvasActivity activity) {
    this.canvasActivity = activity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

    this.mainMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

    final TypedArray mainMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

    //this.mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
    this.mainMenuList = (ListView)view.findViewById(R.id.list_slidermenu);

    this.mainMenuItems = new ArrayList<NavDrawerItem>();
    this.mainMenuItems.add(new NavDrawerItem(this.mainMenuTitles[0], mainMenuIcons.getResourceId(0, -1)));
    this.mainMenuItems.add(new NavDrawerItem(this.mainMenuTitles[1], mainMenuIcons.getResourceId(1, -1)));
    this.mainMenuItems.add(new NavDrawerItem(this.mainMenuTitles[2], mainMenuIcons.getResourceId(2, -1)));
    this.mainMenuItems.add(new NavDrawerItem(this.mainMenuTitles[3], mainMenuIcons.getResourceId(3, -1)));
    this.mainMenuItems.add(new NavDrawerItem(this.mainMenuTitles[4], mainMenuIcons.getResourceId(4, -1)));
    this.mainMenuItems.add(new NavDrawerItem(this.mainMenuTitles[5], mainMenuIcons.getResourceId(5, -1)));
    this.mainMenuItems.add(new NavDrawerItem(this.mainMenuTitles[6], mainMenuIcons.getResourceId(6, -1)));

    mainMenuIcons.recycle();

    this.adapter = new NavDrawerListAdapter(getActivity().getApplicationContext(), this.mainMenuItems);
    this.mainMenuList.setAdapter(this.adapter);
    this.mainMenuList.setOnItemClickListener(new SlideMenuClickListener());

    return view;
  }

  /**
   * Slide menu item click listener
   */
  class SlideMenuClickListener implements ListView.OnItemClickListener {

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      displayView(position);
    }
  }

  /**
   * Displaying fragment view for selected nav drawer list item
   */
  void displayView(int position) {
    switch (position) {
      case 6:
        this.canvasActivity.createNavigationDrawerFragment();
        break;
      default:
        break;
    }
  }
}
