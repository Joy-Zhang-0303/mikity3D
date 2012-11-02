package org.mklab.mikity.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * マウス操作による球プリミティブ作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class SphereToolBarAction extends Action {

  /** プログラム実行画面クラスMainWindowのフィールド */
  private ModelingWindow window;

  // private CollisionCanceller dc;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   * @param dc キャンセラー
   */
  public SphereToolBarAction(final ModelingWindow window, CollisionCanceller dc) {
    super();
    this.window = window;
    // this.dc = dc;
    setText("Sphere"); //$NON-NLS-1$
  }

  /**
   * 追加した球体の情報をキャンバスとツリーに追加します。
   */
  private void updateSphere() {
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

    XMLSphere sph = new XMLSphere();
    sph.setR(0.10f);
    sph.setDiv(20);
    sph.setColor("yellow"); //$NON-NLS-1$
    // dc.checkCollision(sph,sph.loadLocation(),group);
    group.addXMLSphere(sph);
    updateSphere();
  }
}