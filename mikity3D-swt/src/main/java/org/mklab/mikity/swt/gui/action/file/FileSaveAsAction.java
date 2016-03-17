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

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.swt.gui.MessagegUtil;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * モデリングデータを別名保存するクラスです。
 * 
 * @author Yusuke Tsutsui
 * 
 */
public class FileSaveAsAction extends Action {

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   * 
   */
  public FileSaveAsAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileSaveAsAction.0")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final FileDialog dialog = new FileDialog(this.window.getShell(), SWT.SAVE);
    dialog.setText(Messages.getString("FileSaveAsAction.1")); //$NON-NLS-1$
    dialog.setFilterExtensions(new String[] {"*.m3d", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$

    String filePath = dialog.open();
    if (filePath == null) {
      return;
    }
    
    if (filePath.endsWith(".m3d") == false) { //$NON-NLS-1$
      filePath = filePath + ".m3d"; //$NON-NLS-1$
    }
    
    final File file = new File(filePath);
    if (file.equals(this.window.getModelFile())) {
      new FileSaveAction(this.window).run();
      return;
    }
    
    if (file.exists()) {
      int yesNo = MessagegUtil.showYesNo(this.window.getShell(), Messages.getString("FileSaveAsAction.2")); //$NON-NLS-1$
      if (yesNo != SWT.YES) {
        return;
      }
    }

    this.window.setModelFile(file);
    
    try {
      this.window.saveModelFile();
    } catch (Mikity3dSerializeDeserializeException e) {
      throw new RuntimeException(e);
    }
  }
}