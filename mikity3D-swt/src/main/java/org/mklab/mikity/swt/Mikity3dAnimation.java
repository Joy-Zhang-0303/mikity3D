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
package org.mklab.mikity.swt;

import org.eclipse.swt.widgets.Display;
import org.mklab.mikity.swt.gui.AnimationWindow;

/**
 * アニメーションツールのメインクラスです。
 * 
 * @author Miki Koga(miki@mk.ces.kyutech.ac.jp)
 * @version $Revision: 1.2 $.2005/02/09
 */
public class Mikity3dAnimation {

  /**
   * メインメソッドです。
   * @param args コマンドライン引数
   */
  public static void main(String[] args) {
    final AnimationWindow main = new AnimationWindow(null);
    main.setBlockOnOpen(true);
    main.open();
    Display.getCurrent().dispose();
  }
}
