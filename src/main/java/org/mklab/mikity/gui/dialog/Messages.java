package org.mklab.mikity.gui.dialog;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author koga
 * @version $Revision$, 2009/02/11
 */
public class Messages {

  private static final String BUNDLE_NAME = "org.mklab.mikity.gui.dialog.messages"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private Messages() {
  // nothing
  }

  /**
   * @param key
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
