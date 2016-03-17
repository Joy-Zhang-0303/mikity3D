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
import org.mklab.mikity.swt.gui.action.Messages;


/**
 * モデルへの操作をリセットし初期状態へ戻すアクションを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/18
 */
public class ResetToInitialStateAction extends Action {
  /** モデラー。 */
  JoglModeler modeler;

  /**
   * 新しく生成された<code>ResetOperationAction</code>オブジェクトを初期化します。
   */
  public ResetToInitialStateAction() {
    setText(Messages.getString("ResetToInitialStateAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("ResetToInitialStateAction.1")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    this.modeler.resetToInitialState();
  }
  
  /**
   * モデラーを設定します。
   * @param modeler モデラー
   */
  public void setModeler(JoglModeler modeler) {
    this.modeler = modeler;
  }
}