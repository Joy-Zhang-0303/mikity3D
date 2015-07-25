package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
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
    final SphereModel sphere = new SphereModel();
    sphere.setRadius(0.10f);
    sphere.setDivision(20);
    sphere.setColor("yellow"); //$NON-NLS-1$
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.addSphere(sphere);
    
    update();
    
    this.modeler.setChanged(true);
  }
}