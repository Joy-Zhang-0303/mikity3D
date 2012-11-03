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

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   * @param dc キャンセラー
   */
  public SphereToolBarAction(final ModelingWindow window, CollisionCanceller dc) {
    this.window = window;
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
    final XMLSphere sphere = new XMLSphere();
    sphere.setR(0.10f);
    sphere.setDiv(20);
    sphere.setColor("yellow"); //$NON-NLS-1$
    
    final Jamast root = ModelingWindow.getRoot();
    final Group group = root.loadModel(0).loadGroup(0);
    group.addXMLSphere(sphere);
    
    updateSphere();
  }
}