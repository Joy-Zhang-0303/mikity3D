package org.mklab.mikity.view.gui.action.toolbar;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.gui.ModelingWindow;

/**
 * マウス操作によるグループ作成のためのツールバーを作成するクラスです，
 * 
 * @author koga
 * @version $Revision$, 2015/07/25
 */
public class GroupToolBarAction extends AbstractToolBarAction {
  /**
   * 新しく生成された<code>GroupToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ 
   */
  public GroupToolBarAction(final ModelingWindow window) {
    super(window, Messages.getString("GroupToolBarAction.0")); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    final GroupModel group = new GroupModel();
    group.setName("group"); //$NON-NLS-1$
    
    final GroupModel targetGroup = this.modeler.getTargetGroup();
    targetGroup.add(group);
    update();
    
    this.modeler.setIsChanged(true);
  }
}