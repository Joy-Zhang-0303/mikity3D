package org.mklab.mikity.view.renderer.java3d;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.view.gui.AbstractModeler;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/12
 */
public class Java3dModeler extends AbstractModeler {
  /** キャンバス */
  private Java3dModelRenderer renderer;
  
  /**
   * Initialize the generated object of {@link Java3dModeler}.
   * @param parent 親
   * @param style スタイル
   * @param root ルート
   */
  public Java3dModeler(Composite parent, int style, Mikity3d root) {
    super(parent, style, root);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createViewer() {
    final org.mklab.mikity.model.xml.simplexml.model.Group[] children = this.tree.getModel().getGroups();
    this.renderer.setChildren(children); 
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createModelCanvas(Composite viewerComp) {
    this.awtFrame = SWT_AWT.new_Frame(viewerComp);
    this.renderer = new Java3dModelRenderer();
    
    final Mikity3dConfiguration configuration = this.root.getConfiguration(0);
    this.renderer.setConfiguration(configuration);
    
    this.awtFrame.add(this.renderer);
  }
}
