package org.mklab.mikity.android.mainmenu;

/**
 * メインメニューの項目を表すクラスです。
 * 
 * @author hirae 
 * @version $Revision$, 2016/02/01
 */
public class MainMenuDrawerItem {
  private String title;
  private int navicon;
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
   * @param title
   * @param icon
   * @param isCounterVisible
   * @param count
   */
  public MainMenuDrawerItem(String title, int icon, boolean isCounterVisible, String count) {
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
