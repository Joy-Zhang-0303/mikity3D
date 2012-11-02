package org.mklab.mikity.action.toolbar;

import org.eclipse.jface.action.Action;
import org.mklab.mikity.gui.ModelingWindow;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLCylinder;


/**
 * マウス操作による円柱プリミティブ作成のためのツールバーを作成するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $.2005/11/21
 */
public class CylinderToolBarAction extends Action {
  /** プログラム実行画面クラスMainWindowのフィールド  */
  private ModelingWindow window;

  // private CollisionCanceller dc;

  /**
   * コンストラクター
   * 
   * @param window ウィンドウ
   * @param dc キャンセラー
   */
  public CylinderToolBarAction(final ModelingWindow window, CollisionCanceller dc) {
    super();
    this.window = window;
    // this.dc =dc;
    setText("Cylinder"); //$NON-NLS-1$
  }

  /**
   * 追加した円柱の情報をキャンバスとツリーに追加します。
   */
  private void updateCylinder() {
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

    XMLCylinder cyl = new XMLCylinder();
    cyl.setR(0.10f);
    cyl.setHeight(0.10f);
    cyl.setDiv(20);
    cyl.setColor("blue"); //$NON-NLS-1$
    // dc.checkCollision(cyl,cyl.loadLocation(),group);
    group.addXMLCylinder(cyl);
    updateCylinder();
  }
}