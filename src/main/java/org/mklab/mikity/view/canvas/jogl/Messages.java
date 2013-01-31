/*
 * Created on 2013/01/19
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.view.canvas.jogl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Messages {

  private static final String BUNDLE_NAME = "org.mklab.mikity.jogl.messages"; //$NON-NLS-1$

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
