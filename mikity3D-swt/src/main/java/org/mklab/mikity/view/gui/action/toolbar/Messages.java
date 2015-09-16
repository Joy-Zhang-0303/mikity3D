/*
 * Created on 2015/09/14
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.action.toolbar;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Messages {

  private static final String BUNDLE_NAME = "org.mklab.mikity.gui.action.toolbar.messages"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private Messages() {}

  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}
