/*
 * Created on 2013/10/30
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;


/**
 * @author Takuya
 * @version $Revision$, 2013/10/30
 * coreからAndroidのログキャットを利用するためのインターフェース
 */
public interface LogCatPrinter {
  /**
   * @param log LOGCATに出力したい文字列
   */
  void printLog(String log);
}
