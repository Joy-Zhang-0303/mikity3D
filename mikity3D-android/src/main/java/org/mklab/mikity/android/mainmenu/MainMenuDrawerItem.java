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
package org.mklab.mikity.android.mainmenu;

/**
 * メインメニューの項目を表すクラスです。
 * 
 * @author hirae 
 * @version $Revision$, 2016/02/01
 */
public class MainMenuDrawerItem {
  /** タイトル。 */
  private String title;
  /** アイコン。 */
  private int navicon;
  /** 項目の数。 */
  private String count = "0"; //$NON-NLS-1$

  private boolean isCounterVisible = false;

  /**
   * 新しく生成された<code>NavDrawerItem</code>オブジェクトを初期化します。
   */
  public MainMenuDrawerItem() {
    // nothing to do
  }

  /**
   * 新しく生成された<code>NavDrawerItem</code>オブジェクトを初期化します。
   * @param title タイトル
   * @param icon アイコン
   */
  public MainMenuDrawerItem(String title, int icon) {
    this.title = title;
    this.navicon = icon;
  }

  /**
   * 新しく生成された<code>NavDrawerItem</code>オブジェクトを初期化します。
   * @param title タイトル
   * @param icon アイコン
   * @param isCounterVisible 項目の数が
   * @param count 項目の数
   */
  public MainMenuDrawerItem(String title, int icon, boolean isCounterVisible, String count) {
    this.title = title;
    this.navicon = icon;
    this.isCounterVisible = isCounterVisible;
    this.count = count;
  }

  /**
   * タイトルを返します。
   * 
   * @return タイトル
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * アイコンを返します。
   * 
   * @return アイコン
   */
  public int getIcon() {
    return this.navicon;
  }

  /**
   * 項目の数を返します。
   * 
   * @return 項目の数
   */
  public String getCount() {
    return this.count;
  }

  /**
   * @return
   */
  public boolean isCounterVisible() {
    return this.isCounterVisible;
  }

  /**
   * タイトルを設定します。
   * 
   * @param title タイトル
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * アイコンを設定します。
   * 
   * @param icon アイコン
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
  public void setCounterVisisble(boolean isCounterVisible) {
    this.isCounterVisible = isCounterVisible;
  }
}
