/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    
    this.mainActivity = (MainActivity)getActivity();
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
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[6], mainMenuIcons.getResourceId(6, -1)));
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[7], mainMenuIcons.getResourceId(7, -1)));
    mainMenuItems.add(new MainMenuDrawerItem(mainMenuTitles[8], mainMenuIcons.getResourceId(8, -1)));

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
      case 0:
        this.mainActivity.createNewModelData();
        break;
      case 1:
        this.mainActivity.showFileSelectionFragment();
        break;
      case 2:
        this.mainActivity.saveModelData();
        break;
      case 3:
        this.mainActivity.sendFileChooseIntentForSavingModel();
        break;
      case 4:
        this.mainActivity.showSceneGraphTreeFragment();
        break;
      case 5:
        this.mainActivity.showSettingsFragment();
        break;
      case 6:
        this.mainActivity.showSampleSelectionFragment();
        break;
      case 7:
        showAboutApp();
        break;
      default:
        break;
    }
  }
  
  /**
   * アプリについて表示します。 
   */
  private void showAboutApp() {
    String message =    "Mikity3D for Android" + System.getProperty("line.separator"); //$NON-NLS-1$ //$NON-NLS-2$
    message = message + "version 0.7.0" + System.getProperty("line.separator");  //$NON-NLS-1$//$NON-NLS-2$
    message = message + "Copyright (C) 2015-2016 MKLab.org (Koga Laboratory)" + System.getProperty("line.separator"); //$NON-NLS-1$ //$NON-NLS-2$
    message = message + System.getProperty("line.separator"); //$NON-NLS-1$
    message = message + "Send bugs and comments to org.mklab.android@gmail.com"; //$NON-NLS-1$
    showMessageInDialog(message);
  }
  
  /**
   * ダイアログに警告メッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showMessageInDialog(String message) {
    final MessageDialogFragment dialog = new MessageDialogFragment();
    final Bundle arguments = new Bundle();
    arguments.putString("message", message); //$NON-NLS-1$
    dialog.setArguments(arguments);
    dialog.show(getActivity().getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }
}
