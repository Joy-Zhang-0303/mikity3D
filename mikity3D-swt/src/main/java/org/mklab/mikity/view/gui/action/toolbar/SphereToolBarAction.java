package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * マウス操作による球プリミティブ作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class SphereToolBarAction extends AbstractToolBarAction {
  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   */
  public SphereToolBarAction(final ModelingWindow window) {
    super(window, "Sphere"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final GroupModel group = this.modeler.getTargetGroup();
    final PrimitiveModel primitive = SphereModel.createDefault();
    group.add(primitive);
    
    update();
    
    this.modeler.setIsChanged(true);
  }
}