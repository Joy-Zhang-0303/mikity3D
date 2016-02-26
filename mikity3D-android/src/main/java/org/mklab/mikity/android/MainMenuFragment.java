/*
 * Created on 2015/02/19
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.android.mainmenu.MainMenuDrawerItem;
import org.mklab.mikity.android.mainmenu.adapter.MainMenuDrawerListAdapter;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
  private MainActivity mainActivity;

  /**
   * アクティビティを設定します。
   * 
   * @param activity アクティビティ
   */
  public void setActivity(MainActivity activity) {
    this.mainActivity = activity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

    final String[] mainMenuTitles = getResources().getStringArray(R.array.main_menu_drawer_items);

    final TypedArray mainMenuIcons = getResources().obtainTypedArray(R.array.main_menu_drawer_icons);

    final ListView mainMenuList = (ListView)view.findViewById(R.id.list_main_menu);

    final List<MainMenuDrawerItem> mainMenuItems = new ArrayList<MainMenuDrawerItem>();
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[0], mainMenuIcons.getResourceId(0, -1)));
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[1], mainMenuIcons.getResourceId(1, -1)));
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[2], mainMenuIcons.getResourceId(2, -1)));
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[3], mainMenuIcons.getResourceId(3, -1)));
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[4], mainMenuIcons.getResourceId(4, -1)));
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[5], mainMenuIcons.getResourceId(5, -1)));

    mainMenuIcons.recycle();

    final MainMenuDrawerListAdapter adapter = new MainMenuDrawerListAdapter(getActivity().getApplicationContext(), mainMenuItems);
    mainMenuList.setAdapter(adapter);
    mainMenuList.setOnItemClickListener(new SlideMenuClickListener());

    return view;
  }

  /**
   * Slide menu item click listener
   */
  class SlideMenuClickListener implements ListView.OnItemClickListener {
    /**
     * {@inheritDoc}
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      displayView(position);
    }
  }

  /**
   * Displaying fragment view for selected nav drawer list item
   */
  void displayView(int position) {
    switch (position) {
      case 1:
        this.mainActivity.createFileSelectionFragment();
        break;
      case 2:
        this.mainActivity.createSceneGraphTreeFragment();
        break;
      case 3:
        this.mainActivity.createSettingsFragment();
        break;
      default:
        break;
    }
  }
}
