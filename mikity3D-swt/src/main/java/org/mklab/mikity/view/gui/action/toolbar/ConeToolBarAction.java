package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
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
    final GroupModel group = this.modeler.getTargetGroup();
    final PrimitiveModel primitive = ConeModel.createDefault();
    group.add(primitive);
    update();
    
    this.modeler.setIsChanged(true);
  }
}
