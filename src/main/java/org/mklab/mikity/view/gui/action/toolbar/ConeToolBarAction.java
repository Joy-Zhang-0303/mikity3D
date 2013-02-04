package org.mklab.mikity.view.gui.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.Jamast;
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.XMLCone;
import org.mklab.mikity.view.gui.ModelingWindow;


/**
 * マウス操作による円錐プリミティブ作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class ConeToolBarAction extends Action {

  /** プログラム実行画面クラスMainWindowのフィールド  */
  private ModelingWindow window;

  /**
   * 新しく生成された<code>ConeToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   */
  public ConeToolBarAction(final ModelingWindow window) {
    this.window = window;
    setText("Cone"); //$NON-NLS-1$
  }

  /**
   * 追加した円錐の情報をキャンバスとツリーに追加します。
   */
  private void updateCone() {
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
    final XMLCone cone = new XMLCone();
    cone.setRadius(0.10f);
    cone.setHeight(0.10f);
    cone.setDiv(20);
    cone.setColor("green"); //$NON-NLS-1$
    
    final Jamast root = this.window.getRoot();
    final Group rootGroup = root.getModel(0).getGroup(0);
    rootGroup.addXMLCone(cone);
    updateCone();
  }
}