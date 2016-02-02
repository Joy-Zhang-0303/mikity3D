package org.mklab.mikity.android.settings;

/**
 * @author 
 * @version $Revision$, 2016/02/01
 */
public class SettingsDrawerItem {

  private String title;
  private int navicon;
  private String count = "0";

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
