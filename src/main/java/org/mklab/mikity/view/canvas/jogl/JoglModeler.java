package org.mklab.mikity.view.canvas.jogl;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

import org.mklab.mikity.model.xml.Jamast;
import org.mklab.mikity.view.gui.AbstractModeler;


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
   * {@inheritDoc}
   */
  @Override
  public void createViewer() {
    org.mklab.mikity.model.xml.model.Group[] groups = this.tree.getModel().loadGroups();
    this.canvas.setChildren(groups);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createModelCanvas(Composite viewerComposite) {
    this.awtFrame = SWT_AWT.new_Frame(viewerComposite);
    this.canvas = new JoglModelCanvas(this.root);
    this.awtFrame.add(this.canvas); 
  }

}
