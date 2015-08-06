package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * マウス操作による直方体プリミティブ作成のためのツールバーを作成するクラスです，
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2005/11/21
 */
public class BoxToolBarAction extends AbstractToolBarAction {
  /**
   * 新しく生成された<code>BoxToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public BoxToolBarAction(final ModelingWindow window) {
    super(window, "Box"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final BoxModel box = new BoxModel();
    box.setWidth(0.10f);
    box.setHeight(0.10f);
    box.setDepth(0.10f);
    box.setColorName("red"); //$NON-NLS-1$
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.add(box);
    update();
    
    this.modeler.setChanged(true);
  }
}