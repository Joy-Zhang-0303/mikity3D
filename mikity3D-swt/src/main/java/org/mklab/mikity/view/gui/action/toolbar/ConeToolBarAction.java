package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.view.gui.ModelingWindow;


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
    super(window, "Cone"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final ConeModel cone = new ConeModel();
    cone.setRadius(0.10f);
    cone.setHeight(0.10f);
    cone.setDivision(20);
    cone.setColor("green"); //$NON-NLS-1$
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.addCone(cone);
    update();
    
    this.modeler.setChanged(true);
  }
}