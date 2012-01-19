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
   * 
   * @param parent ツリーの親
   * @param style スタイル
   * @param root ツリーのルート
   * @param dc 重複防止
   */
  public JoglModeler(Composite parent, int style, Jamast root, CollisionCanceller dc) {
    super(parent, style, root, dc);
  }

  /**
   * GroupをsinsiCanvasに読み込ませ、Frameにaddする
   * 
   * @version $Revision: 1.22 $.2005/01/25
   */
  @Override
  public void createViewer() {
    //org.mklab.mikity.xml.model.Group[] group = this.tree.getModel().loadGroup();
    //this.canvas.setObject(new SampleJoglObject(), new TeapotJoglObject());
    //this.canvas.setObject(new TeapotJoglObject(), new ColorfulCubeJoglObject());
    this.canvas.addCoordinate(new TeapotJoglCoordinate());
    this.canvas.addObject(new TeapotJoglObject());
    
    this.canvas.addCoordinate(new CubeJoglCoordinate());
    this.canvas.addObject(new CubeJoglObject());
    
    this.canvas.addCoordinate(new TeapotJoglCoordinate2());
    this.canvas.addObject(new TeapotJoglObject());
    
//    this.canvas.setObject(new TeapotJoglObject(), new CubeJoglObject());
//    this.canvas.setCoordinate(new CubeJoglCoordinate());
    //this.canvas.setObject(new TriJoglObject());
  }

  /**
   * @see org.mklab.mikity.gui.Modeler#createModelCanvas(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createModelCanvas(Composite viewerComp) {
    // 何もないキャンバスを作る
    this.awtFrame = SWT_AWT.new_Frame(viewerComp);
    this.canvas = new JoglModelCanvas();
    this.awtFrame.add(this.canvas);
  }

}
