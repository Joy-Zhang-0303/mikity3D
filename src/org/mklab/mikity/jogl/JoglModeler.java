package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

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
    JoglGroup[] groups = createModel();
    this.canvas.setChild(groups);
  }
  
  /**
   * オブジェクトのグループを生成します。
   * @return オブジェクトのグループ
   */
  public JoglGroup[] createModel() {
    List<JoglGroup> groups = new ArrayList<JoglGroup>();

    JoglGroup group = new JoglGroup();

    group.addCoordinate(new TeapotJoglCoordinate1());
    JoglObject object1 = createTeapotCubeTeapot();
    group.addObject(object1);
    
    group.addCoordinate(new TeapotJoglCoordinate3());
    JoglObject object2 = createTeapotCubeTeapot();
    group.addObject(object2);

    group.addCoordinate(new TeapotJoglCoordinate3());
    JoglObject object3 = createTeapotCubeTeapot();
    group.addObject(object3);
    
    group.addCoordinate(new TeapotJoglCoordinate3());
    JoglObject object4 = createTeapotCubeTeapot();
    group.addObject(object4);
    
    group.addCoordinate(new TeapotJoglCoordinate3());
    JoglObject object5 = createTeapotCubeTeapot();
    group.addObject(object5);
    
    group.addCoordinate(new TeapotJoglCoordinate3());
    JoglObject object6 = createTeapotCubeTeapot();
    group.addObject(object6);
    
    groups.add(group);
    
    return groups.toArray(new JoglGroup[groups.size()]);
  }

  /**
   * TeapotとCubeとTeapotが連結したオブジェクトを生成します。
   * @return TeapotとCubeとTeapotが連結したオブジェクト
   */
  public JoglGroup createTeapotCubeTeapot() {
    JoglGroup group1 = new JoglGroup();
    group1.addCoordinate(new TeapotJoglCoordinate1());
    group1.addObject(new TeapotJoglObject());
    
    group1.addCoordinate(new CubeJoglCoordinate());
    group1.addObject(new CubeJoglObject());
    
    group1.addCoordinate(new TeapotJoglCoordinate2());
    group1.addObject(new TeapotJoglObject());
    return group1;
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
