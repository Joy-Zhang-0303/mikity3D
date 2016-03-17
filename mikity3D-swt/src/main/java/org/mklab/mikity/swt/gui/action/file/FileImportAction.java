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
 * モデリングファイルデータを現在のツリーに追加するアクションです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/12/04
 */
public class FileImportAction extends Action {

  private ModelingWindow window;

  /**
   * 新しく生成された<code>FileImportAction</code>オブジェクトを初期化します。
   * 
   * @param window ウィンドウ
   */
  public FileImportAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("FileImportAction.0")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.IAction#run()
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
      this.window.importModelFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
