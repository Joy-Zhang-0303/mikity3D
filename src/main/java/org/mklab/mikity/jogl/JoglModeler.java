package org.mklab.mikity.jogl;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.gui.AbstractModeler;
import org.mklab.mikity.xml.Jamast;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/12
 */
public class JoglModeler extends AbstractModeler {
  private JoglModelCanvas canvas;
  
  /**
   * Initialize the generated object of {@link JoglModeler}.
   * 
   * @param parent ツリーの親
   * @param style スタイル
   * @param root ツリーのルート
   */
  public JoglModeler(Composite parent, int style, Jamast root) {
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
   * @see org.mklab.mikity.gui.AbstractModeler#createModelCanvas(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createModelCanvas(Composite viewerComp) {
    // 何もないキャンバスを作る
    this.awtFrame = SWT_AWT.new_Frame(viewerComp);
    this.canvas = new JoglModelCanvas(this.root);
    this.awtFrame.add(this.canvas); 
  }

}
