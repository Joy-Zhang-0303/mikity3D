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
package org.mklab.mikity.swt.gui.action.display;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.swt.gui.JoglModeler;
import org.mklab.mikity.swt.gui.ModelingWindow;
import org.mklab.mikity.swt.gui.action.Messages;
import org.mklab.mikity.swt.gui.editor.ConfigurationEditor;


/**
 * 各種設定画面を展開するクラスです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.4 $.2005/02/18
 */
public class ConfigurationDialogOpenAction extends Action {
  /** モデリングウィンドウ。 */
  private ModelingWindow window;
  
  /** モデラー。 */
  private JoglModeler modeler;

  /**
   * 新しく生成された<code>ConfigDialogOpenAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public ConfigurationDialogOpenAction(final ModelingWindow window) {
    this.window = window;
    setText(Messages.getString("ConfigurationDialogOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("ConfigurationDialogOpenAction.1")); //$NON-NLS-1$
  }

  /**
   * 各種設定画面を展開します。
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final ConfigurationEditor dialog = new ConfigurationEditor(this.window.getShell(), this.modeler.getRoot().getConfiguration(0), this.modeler);
    dialog.open();
  }
  
  /**
   * モデラーを設定します。
   * @param modeler モデラー
   */
  public void setModeler(JoglModeler modeler) {
    this.modeler = modeler;
  }
}