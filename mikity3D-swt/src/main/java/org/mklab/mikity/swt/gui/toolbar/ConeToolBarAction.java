package org.mklab.mikity.swt.gui.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * マウス操作による円錐プリミティブ作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class ConeToolBarAction extends AbstractToolBarAction {
  /**
   * 新しく生成された<code>ConeToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public ConeToolBarAction(final ModelingWindow window) {
    super(window, Messages.getString("ConeToolBarAction.0")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final GroupModel group = this.modeler.getSelectedGroup();
    final ObjectModel object = ConeModel.createDefault();
    group.add(object);
    
    this.modeler.setSelectedObject(object);
    
    update();
    
    this.modeler.setIsChanged(true);
  }
}
