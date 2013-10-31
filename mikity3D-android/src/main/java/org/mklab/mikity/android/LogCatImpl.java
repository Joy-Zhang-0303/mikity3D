/*
 * Created on 2013/11/01
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android;

import org.mklab.mikity.LogCatPrinter;

import android.util.Log;


public class LogCatImpl implements LogCatPrinter {

  public void printLog(String log) {
    Log.d("mikity3dAndroid", log); //$NON-NLS-1$
  }

}