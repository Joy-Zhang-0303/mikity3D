package org.mklab.mikity.view.renderer.jogl;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.view.gui.AbstractModeler;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/12
 */
public class JoglModeler extends AbstractModeler {

  private JoglModelRenderer renderer;

  /**
   * 新しく生成された<code>JoglModeler</code>オブジェクトを初期化します。
   * @param parent ツリーの親
   * @param style スタイル
   * @param root ツリーのルート
   */
  public JoglModeler(Composite parent, int style, Mikity3d root) {
    super(parent, style, root);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createViewer() {
    org.mklab.mikity.model.xml.simplexml.model.Group[] children = this.tree.getModel().getGroups();
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
