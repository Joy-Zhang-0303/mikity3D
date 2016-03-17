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
package org.mklab.mikity.swt.gui.action;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.Mikity3dFactory;
import org.mklab.mikity.swt.gui.AnimationWindow;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * アニメーション実行画面を展開するクラスです。
 * 
 * @author Yusuke Tsutsui
 * @version $Revision: 1.5 $.2005/02/18
 */
public class AnimationWindowOpenAction extends Action {
  /** モデリングウィンドウ。 */
  private ModelingWindow modelingWindow;

  /**
   * 新しく生成された<code>AnimationWindowOpenAction</code>オブジェクトを初期化します。
   * @param modelingWindow ウィンドウ
   */
  public AnimationWindowOpenAction(ModelingWindow modelingWindow) {
    setText(Messages.getString("AnimationWindowOpenAction.0")); //$NON-NLS-1$
    setToolTipText(Messages.getString("AnimationWindowOpenAction.1")); //$NON-NLS-1$
    this.modelingWindow = modelingWindow;
  }

  /**
   * アニメーション画面を展開します。
   * 
   * {@inheritDoc}
   */
  @Override
  public void run() {
    if (this.modelingWindow.getRoot() == Mikity3dFactory.getEmptyModel()) {
      return;
    }
    
    final AnimationWindow animationWindow = new AnimationWindow(this.modelingWindow.getShell(), this.modelingWindow.getRoot(), this.modelingWindow.getModelFile());
    this.modelingWindow.setAnimationWindow(animationWindow);
    animationWindow.open();
  }
}
