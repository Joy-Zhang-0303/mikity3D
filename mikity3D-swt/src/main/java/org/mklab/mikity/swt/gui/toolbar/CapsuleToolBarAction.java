package org.mklab.mikity.swt.gui.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.CapsuleModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.swt.gui.ModelingWindow;


/**
 * マウス操作によるカプセルプリミティブ作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class CapsuleToolBarAction extends AbstractToolBarAction {
  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public CapsuleToolBarAction(final ModelingWindow window) {
    super(window, Messages.getString("CapsuleToolBarAction.0")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final GroupModel group = this.modeler.getSelectedGroup();
    final ObjectModel object = CapsuleModel.createDefault();    
    group.add(object);
    
    this.modeler.setSelectedObject(object);
    
    update();
    
    this.modeler.setIsChanged(true);
  }
}