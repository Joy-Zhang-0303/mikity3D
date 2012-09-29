package org.mklab.mikity.java3d;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.gui.Modeler;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.xml.Jamast;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/12
 */
public class Java3dModeler extends Modeler {
  private Java3dModelCanvas canvas;
  /**
   * Initialize the generated object of {@link Java3dModeler}.
   * @param parent 親
   * @param style スタイル
   * @param root ルート
   * @param canceller 重複防止
   */
  public Java3dModeler(Composite parent, int style, Jamast root, CollisionCanceller canceller) {
    super(parent, style, root, canceller);
  }

  /**
   * GroupをsinsiCanvasに読み込ませ、Frameにaddする
   * 
   * @version $Revision: 1.22 $.2005/01/25
   */
  @Override
  public void createViewer() {
    org.mklab.mikity.xml.model.Group[] group = this.tree.getModel().loadGroup();
    this.canvas.setChild(group); 
  }

  /**
   * @see org.mklab.mikity.gui.Modeler#createModelCanvas(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createModelCanvas(Composite viewerComp) {
    // 何もないキャンバスを作る
    this.awtFrame = SWT_AWT.new_Frame(viewerComp);
    this.canvas = new Java3dModelCanvas(this.root);
    this.awtFrame.add(this.canvas);
  }
}
