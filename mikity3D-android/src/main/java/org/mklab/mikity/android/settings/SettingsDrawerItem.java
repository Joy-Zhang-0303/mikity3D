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
package org.mklab.mikity.android.settings;

/**
 * @author 
 * @version $Revision$, 2016/02/01
 */
public class SettingsDrawerItem {

  private String title;
  private int navicon;
  private String count = "0"; //$NON-NLS-1$

  private boolean isCounterVisible = false;

  /**
   * 新しく生成された<code>NavDrawerItem</code>オブジェクトを初期化します。
   */
  public SettingsDrawerItem() {
    // nothing to do
  }

  /**
   * 新しく生成された<code>NavDrawerItem</code>オブジェクトを初期化します。
   * @param title
   * @param icon
   */
  public SettingsDrawerItem(String title, int icon) {
    this.title = title;
    this.navicon = icon;
  }

  /**
   * 新しく生成された<code>NavDrawerItem</code>オブジェクトを初期化します。
   * @param title
   * @param icon
   * @param isCounterVisible
   * @param count
   */
  public SettingsDrawerItem(String title, int icon, boolean isCounterVisible, String count) {
    this.title = title;
    this.navicon = icon;
    this.isCounterVisible = isCounterVisible;
    this.count = count;
  }

  /**
   * @return
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * @return
   */
  public int getIcon() {
    return this.navicon;
  }

  /**
   * @return
   */
  public String getCount() {
    return this.count;
  }

  /**
   * @return
   */
  public boolean getCounterVisiblity() {
    return this.isCounterVisible;
  }

  /**
   * @param title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param icon
   */
  public void setIcon(int icon) {
    this.navicon = icon;
  }

  /**
   * @param count
   */
  public void setCount(String count) {
    this.count = count;
  }

  /**
   * @param isCounterVisible
   */
  public void setCounterVisisblity(boolean isCounterVisible) {
    this.isCounterVisible = isCounterVisible;
  }
}
