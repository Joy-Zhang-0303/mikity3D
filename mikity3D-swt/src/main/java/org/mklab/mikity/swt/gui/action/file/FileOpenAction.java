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
import org.eclipse.swt.widgets.FileDialog;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * モデリングデータを読み込むアクションです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/02/14
 */
public class FileOpenAction extends Action {

  private ModelingWindow window;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public FileOpenAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("FileOpenAction.1")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final FileDialog dialog = new FileDialog(this.window.getShell());
    dialog.setFilterExtensions(new String[] {"*.m3d", "*.stl", "*.*"}); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    
    final String filePath = dialog.open();
    if (filePath == null) {
      return;
    }
    
    try {
      final File file = new File(filePath);
      this.window.setModelFile(file);
      this.window.loadModelFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}