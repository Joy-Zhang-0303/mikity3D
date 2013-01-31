package org.mklab.mikity.view.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.model.xml.Jamast;
import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.XMLSphere;
import org.mklab.mikity.view.gui.ModelingWindow;


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
   */
  public SphereToolBarAction(final ModelingWindow window) {
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
    
    final Jamast root = this.window.getRoot();
    final Group rootGroup = root.loadModel(0).loadGroup(0);
    rootGroup.addXMLSphere(sphere);
    
    updateSphere();
  }
}