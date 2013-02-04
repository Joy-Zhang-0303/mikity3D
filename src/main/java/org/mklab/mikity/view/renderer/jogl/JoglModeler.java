package org.mklab.mikity.view.renderer.jogl;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

import org.mklab.mikity.model.xml.Jamast;
import org.mklab.mikity.view.gui.AbstractModeler;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/12
 */
public class JoglModeler extends AbstractModeler {
  private JoglModelRenderer renderer;
  
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
    org.mklab.mikity.model.xml.model.Group[] children = this.tree.getModel().getGroups();
    this.renderer.setChildren(children);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createModelCanvas(Composite viewerComposite) {
    this.awtFrame = SWT_AWT.new_Frame(viewerComposite);
    this.renderer = new JoglModelRenderer();
    this.awtFrame.add(this.renderer); 
  }

}
