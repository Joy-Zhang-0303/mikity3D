package org.mklab.mikity.java3d;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.gui.AbstractModeler;
import org.mklab.mikity.xml.Jamast;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/12
 */
public class Java3dModeler extends AbstractModeler {
  private Java3dModelCanvas canvas;
  /**
   * Initialize the generated object of {@link Java3dModeler}.
   * @param parent 親
   * @param style スタイル
   * @param root ルート
   */
  public Java3dModeler(Composite parent, int style, Jamast root) {
    super(parent, style, root);
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
   * {@inheritDoc}
   */
  @Override
  public void createModelCanvas(Composite viewerComp) {
    // 何もないキャンバスを作る
    this.awtFrame = SWT_AWT.new_Frame(viewerComp);
    this.canvas = new Java3dModelCanvas(this.root);
    this.awtFrame.add(this.canvas);
  }
}
