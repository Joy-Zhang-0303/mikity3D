/*
 * Created on 2003/11/13
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 */
package org.mklab.mikity.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * メッセージ表示に関するクラスです。
 * 
 * @author Yusuke Tsutsui
 */
public final class MessagegUtil {

  /**
   * コンストラクター
   */
  private MessagegUtil() {
  // nothing to do
  }

  /**
   * メッセージを表示します。
   * 
   * @param shell シェル
   * @param message メッセージ
   */
  public static void show(final Shell shell, final String message) {
    MessageBox box = new MessageBox(shell);
    if (message != null) {
      box.setMessage(message);
      box.setText("JAMAST"); //$NON-NLS-1$
      box.open();
    }
  }

  /**
   * メッセージ(YES/NO)を表示します。
   * 
   * @param shell シェル
   * @param message メッセージ
   * @return SWT.YES,SWT.NO, SWT.CANCEL
   */
  public static int showYesNo(final Shell shell, final String message) {
    MessageBox box = new MessageBox(shell, SWT.YES | SWT.NO);
    box.setMessage(message);
    box.setText("JAMAST"); //$NON-NLS-1$
    return box.open();
  }

  /**
   * メッセージ(YES/NO/CANCEL)を表示します。
   * 
   * @param shell シェル
   * @param message メッセージ
   * @return SWT.YES,SWT.NO, SWT.CANCEL
   */
  public static int showYesNoCancel(final Shell shell, final String message) {
    MessageBox box = new MessageBox(shell, SWT.YES | SWT.NO | SWT.CANCEL);
    box.setMessage(message);
    box.setText("JAMAST"); //$NON-NLS-1$
    return box.open();
  }
}
