package org.mklab.mikity.jogl;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.gui.Modeler;
import org.mklab.mikity.gui.collision.CollisionCanceller;
import org.mklab.mikity.xml.Jamast;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/12
 */
public class JoglModeler extends Modeler {
  private JoglModelCanvas canvas;

  /**
   * Initialize the generated object of {@link JoglModeler}.
   * @param parent
   * @param style
   * @param root
   * @param dc
   */
  public JoglModeler(Composite parent, int style, Jamast root, CollisionCanceller dc) {
    super(parent, style, root, dc);
  }
  
  /**
   * GroupをsinsiCanvasに読み込ませ、Frameにaddする
   * 
   * @version $Revision: 1.22 $.2005/01/25
   */
  public void createViewer() {
    org.mklab.mikity.xml.model.Group[] group = this.tree.getModel().loadGroup();
    this.canvas.setObject(new SampleJoglObject());
    this.canvas.setObject(new TeapotJoglObject());
    //this.canvas.setObject(new TriJoglObject());
  }

  public void createModelCanvas(Composite viewerComp) {
    // 何もないキャンバスを作る
    this.awtFrame = SWT_AWT.new_Frame(viewerComp);
    this.canvas = new JoglModelCanvas();
    this.awtFrame.add(this.canvas);
  }

}
