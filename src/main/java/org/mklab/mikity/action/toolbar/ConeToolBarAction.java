package org.mklab.mikity.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLCone;


/**
 * マウス操作による円錐プリミティブ作成のためのツールバーを作成する。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class ConeToolBarAction extends Action {

  /**
   * プログラム実行画面クラスMainWindowのフィールド
   */
  private ModelingWindow window;

  // private CollisionCanceller dc;

  /**
   * 新しく生成された<code>ConeToolBarAction</code>オブジェクトを初期化します。
   * @param window ウィンドウ
   * @param dc キャンセラー
   */
  public ConeToolBarAction(final ModelingWindow window, CollisionCanceller dc) {
    super();
    this.window = window;
    // this.dc = dc;
    setText("Cone"); //$NON-NLS-1$
  }

  /**
   * 追加した円錐の情報をキャンバスとツリーに追加させる。
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

    Jamast root = ModelingWindow.getRoot();
    Group group = root.loadModel(0).loadGroup(0);

    XMLCone cone = new XMLCone();
    cone.setR(0.10f);
    cone.setHeight(0.10f);
    cone.setDiv(20);
    cone.setColor("green"); //$NON-NLS-1$
    // dc.checkCollision(cone,cone.loadLocation(),group);
    group.addXMLCone(cone);
    updateCone();
  }
}