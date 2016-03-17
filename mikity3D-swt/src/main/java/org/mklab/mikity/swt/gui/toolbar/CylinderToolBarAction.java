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
package org.mklab.mikity.swt.gui.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * マウス操作による円柱プリミティブ作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class CylinderToolBarAction extends AbstractToolBarAction {
  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public CylinderToolBarAction(final ModelingWindow window) {
    super(window, Messages.getString("CylinderToolBarAction.0")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final GroupModel group = this.modeler.getSelectedGroup();
    final ObjectModel object = CylinderModel.createDefault();
    
    this.modeler.setSelectedObject(object);
    
    group.add(object);
    
    update();
    
    this.modeler.setIsChanged(true);
  }
}