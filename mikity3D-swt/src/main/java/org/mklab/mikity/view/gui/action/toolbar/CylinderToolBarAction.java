package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.view.gui.ModelingWindow;


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
    super(window, "Cylinder"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final XMLCylinder cylinder = new XMLCylinder();    
    cylinder.setRadius(0.10f);
    cylinder.setHeight(0.10f);
    cylinder.setDivision(20);
    cylinder.setColor("blue"); //$NON-NLS-1$
    
    final Group rootGroup = this.modeler.getTargetGroup();
    rootGroup.addXMLCylinder(cylinder);
    
    update();
    
    this.window.setChanged(true);
  }
}