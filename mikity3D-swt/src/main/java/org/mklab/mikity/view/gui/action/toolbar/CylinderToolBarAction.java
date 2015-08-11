package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
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
    final CylinderModel cylinder = new CylinderModel(0.1f, 0.1f, 36);    
    final ColorModel color = new ColorModel("blue"); //$NON-NLS-1$
    cylinder.setColor(color);
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.add(cylinder);
    
    update();
    
    this.modeler.setChanged(true);
  }
}