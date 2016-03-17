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
package org.mklab.mikity.swt.gui.action.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.MessagegUtil;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * 終了処理を行うアクションです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.3 $.
 */
public class FileExitAction extends Action {
  /** ウィンドウ */
  private ModelingWindow window;
  /** モデラー。 */
  private JoglModeler modeler;

  /**
   * 新しく生成された<code>FileExitAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public FileExitAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileExitAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileExitAction.1")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    if (this.modeler.isChanged()) {
      final int yesNo = MessagegUtil.showYesNoCancel(this.window.getShell(), Messages.getString("FileExitAction.2")); //$NON-NLS-1$
      switch (yesNo) {
        case SWT.YES:
            new FileSaveAction(this.window).run();
          break;
        case SWT.NO:
          break;
        case SWT.CANCEL:
          return;
        default:
          throw new IllegalArgumentException();
      }
    }
    this.window.close();
  }
  
  /**
   * モデラーを設定します。
   * @param modeler モデラー
   */
  public void setModeler(JoglModeler modeler) {
    this.modeler = modeler;
  }
}