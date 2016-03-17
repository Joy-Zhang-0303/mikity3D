/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.swt.gui;

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
    if (message != null) {
      final MessageBox box = new MessageBox(shell);
      box.setMessage(message);
      box.setText("Mikity3D"); //$NON-NLS-1$
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
    final MessageBox box = new MessageBox(shell, SWT.YES | SWT.NO);
    box.setMessage(message);
    box.setText("Mikity3D"); //$NON-NLS-1$
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
    final MessageBox box = new MessageBox(shell, SWT.YES | SWT.NO | SWT.CANCEL);
    box.setMessage(message);
    box.setText("Mikity3D"); //$NON-NLS-1$
    return box.open();
  }
}
