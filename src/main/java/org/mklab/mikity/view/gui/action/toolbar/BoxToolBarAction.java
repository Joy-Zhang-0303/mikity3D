package org.mklab.mikity.view.gui.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.Jamast;
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.XMLBox;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * マウス操作による直方体プリミティブ作成のためのツールバーを作成するクラスです，
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2005/11/21
 */
public class BoxToolBarAction extends Action {
  /**　プログラム実行画面クラスMainWindowのフィールド   */
  private ModelingWindow window;

  /**
   * 新しく生成された<code>BoxToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public BoxToolBarAction(final ModelingWindow window) {
    this.window = window;
    setText("Box"); //$NON-NLS-1$
  }

  /**
   * 追加した直方体の情報をキャンバスとツリーに追加します。
   */
  private void updateBox() {
    this.window.fillTree();
    this.window.createViewer();
  }

  /**
   * ツールバークリック時に実行する。
   * 
   * @see org.eclipse.jface.action.IAction#run()
   */
  @Override
  public void run() {
    final XMLBox box = new XMLBox();
    box.setXsize(0.10f);
    box.setYsize(0.10f);
    box.setZsize(0.10f);
    box.setColor("red"); //$NON-NLS-1$
    
    final Jamast root = this.window.getRoot();
    final Group rootGroup = root.getModel(0).getGroup(0);
    rootGroup.addXMLBox(box);
    updateBox();
  }
}