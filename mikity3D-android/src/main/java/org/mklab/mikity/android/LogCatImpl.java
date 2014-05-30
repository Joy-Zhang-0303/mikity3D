/*
 * Created on 2013/11/01
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import org.mklab.mikity.LogCatPrinter;

import android.util.Log;


/**
 * @author koga
 * @version $Revision$, 2014/05/30
 */
public class LogCatImpl implements LogCatPrinter {

  /**
   * {@inheritDoc}
   */
  public void printLog(String log) {
    Log.d("mikity3dAndroid", log); //$NON-NLS-1$
  }

}