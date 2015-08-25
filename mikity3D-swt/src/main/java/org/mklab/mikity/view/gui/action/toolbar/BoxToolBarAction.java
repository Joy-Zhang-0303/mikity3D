package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.AbstractPrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
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
    final AbstractPrimitiveModel box = new BoxModel(0.1f, 0.1f, 0.1f);
    final ColorModel color = new ColorModel("red"); //$NON-NLS-1$
    box.setColor(color);
    
    final GroupModel group = this.modeler.getTargetGroup();
    group.add(box);
    update();
    
    this.modeler.setChanged(true);
  }
}