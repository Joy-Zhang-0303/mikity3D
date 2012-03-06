package org.mklab.mikity.gui.collision;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author koga
 * @version $Revision$, 2009/02/11
 */
public class Messages {

  private static final String BUNDLE_NAME = "org.mklab.mikity.gui.collision.messages"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private Messages() {
  // nothing
  }

  /**
   * キーに対応する文字列を返します。
   * @param key キー
   * @return キーに対応する文字列
   */
  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}
