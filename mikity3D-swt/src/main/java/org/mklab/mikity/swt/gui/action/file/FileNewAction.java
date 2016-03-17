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

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * モデリングデータを新規作成するアクションです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/02/10
 */
public class FileNewAction extends Action {

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public FileNewAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileNewAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileNewAction.1")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final FileDialog dialog = new FileDialog(this.window.getShell());
    dialog.setText(Messages.getString("FileNewAction.2")); //$NON-NLS-1$
    dialog.setFilterExtensions(new String[] {"*.m3d", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$
    
    final String filePath = dialog.open();
    if (filePath == null) {
      return;
    }
    
    try {
      final File file = new File(filePath);
      if (file.createNewFile()) {
        this.window.setModelFile(file);
        this.window.loadModelFile();
      } else {
        // 新規作成したいが、もともとその名前のファイルが存在するとき
        final MessageBox message = new MessageBox(this.window.getShell(), SWT.YES | SWT.NO);
        message.setText(Messages.getString("FileNewAction.3")); //$NON-NLS-1$
        message.setMessage(Messages.getString("FileNewAction.4")); //$NON-NLS-1$
        final int yesNo = message.open();

        if (yesNo == SWT.YES) {
          this.window.setModelFile(file);
          this.window.loadModelFile();
        } else if (yesNo == SWT.NO) {
          return;
        }
        return;
      }
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
  }
}