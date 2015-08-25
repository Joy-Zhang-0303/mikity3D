package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.AbstractPrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
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
    final AbstractPrimitiveModel sphere = new SphereModel(0.1f, 36);
    final ColorModel color = new ColorModel("yellow"); //$NON-NLS-1$
    sphere.setColor(color);
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.add(sphere);
    
    update();
    
    this.modeler.setChanged(true);
  }
}