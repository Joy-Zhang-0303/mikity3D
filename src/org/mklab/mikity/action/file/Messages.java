package org.mklab.mikity.action.file;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * 
 * @author ｋａｗａｍｕｒａ
 *
 */
public class Messages {

  private static final String BUNDLE_NAME = "org.mklab.mikity.action.file.messages"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private Messages() {}

  /**
   * @param key
   * @return key メッセージ
   */
  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}
