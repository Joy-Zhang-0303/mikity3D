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
   * {@inheritDoc}
   */
  @Override
  public void createViewer() {
    final org.mklab.mikity.xml.model.Group[] groups = this.tree.getModel().loadGroup();
    this.canvas.setChild(groups); 
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
