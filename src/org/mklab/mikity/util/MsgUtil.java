/*
 * Created on 2003/11/13
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


/**
 * @author Yusuke Tsutsui
 * 
 *         メッセージ表示に関するクラス
 * 
 */
public final class MsgUtil {

  /**
   * コンストラクター
   */
  private MsgUtil() {
    // nothing to do
  }

  /**
   * メッセージ表示
   * 
   * @param shell
   *        シェル
   * @param msg
   *        メッセージ
   */
  public static void showMsg(final Shell shell, final String msg) {
    MessageBox mesBox = new MessageBox(shell);
    if (msg != null) {
      mesBox.setMessage(msg);
      mesBox.setText("JAMAST"); //$NON-NLS-1$
      mesBox.open();
    }
  }

  /**
   * メッセージ表示(YES/NO)
   * 
   * @param shell
   *        シェル
   * @param msg
   *        メッセージ
   * @return SWT.YES,SWT.NO, SWT.CANCEL
   */
  public static int showYesNoMsg(final Shell shell, final String msg) {
    MessageBox mesBox = new MessageBox(shell, SWT.YES | SWT.NO);
    mesBox.setMessage(msg);
    mesBox.setText("JAMAST"); //$NON-NLS-1$
    return mesBox.open();
  }

  /**
   * メッセージ表示(YES/NO/CANCEL)
   * 
   * @param shell
   *        シェル
   * @param msg
   *        メッセージ
   * @return SWT.YES,SWT.NO, SWT.CANCEL
   */
  public static int showYesNoCancelMsg(final Shell shell, final String msg) {
    MessageBox mesBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.CANCEL);
    mesBox.setMessage(msg);
    mesBox.setText("JAMAST"); //$NON-NLS-1$
    return mesBox.open();
  }
}