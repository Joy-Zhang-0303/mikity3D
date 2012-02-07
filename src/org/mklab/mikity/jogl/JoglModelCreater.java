package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.xml.model.Group;


/**
 * treeに登録されているシーングラフのモデルからJoglのモデルを作る。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/25
 */
public class JoglModelCreater {

  /**
   * オブジェクトのグループを生成します。
   * 
   * @param group グループ
   * 
   * @return オブジェクトのグループ
   */
  public JoglBranchGroup[] setModels(Group[] group) {
    JoglBranchGroup bg = new JoglBranchGroup();

    JoglTransformGroup tg = new JoglTransformGroup();
    for (int i = 0; i < group.length; i++) {
      bg.addChild(tg);
      JoglTransformGroup child = JoglPrimitiveFactory.create(group[i]);
      tg.addChild(child);
    }

//    tg.setCoordinate(new TeapotJoglCoordinate1());
//    tg.addChild(createTeapotCubeTeapotBranchGroup());
//    bg.addChild(tg);
//
//    for (int i = 0; i < 5; i++) {
//      JoglTransformGroup child = createTeapotCubeTeapot();
//      tg.addChild(child);
//      tg = child;
//    }

    List<JoglBranchGroup> groups = new ArrayList<JoglBranchGroup>();
    groups.add(bg);

    return groups.toArray(new JoglBranchGroup[groups.size()]);
  }

  /**
   * TeapotとCubeとTeapotが連結したオブジェクトを生成します。
   * 
   * @return TeapotとCubeとTeapotが連結したオブジェクト
   */
  public JoglTransformGroup createTeapotCubeTeapot() {
    JoglTransformGroup tg = new JoglTransformGroup();
    tg.setCoordinate(new TeapotJoglCoordinate3());

    tg.addChild(createTeapotCubeTeapotBranchGroup());
    return tg;
  }

  /**
   * TeapotとCubeとTeapotが連結したオブジェクトを生成します。
   * 
   * @return TeapotとCubeとTeapotが連結したオブジェクト
   */
  public JoglBranchGroup createTeapotCubeTeapotBranchGroup() {
    JoglBranchGroup bg0 = new JoglBranchGroup();

    JoglTransformGroup tg1 = new JoglTransformGroup();
    tg1.setCoordinate(new TeapotJoglCoordinate1());
    tg1.addChild(new TeapotJoglObject());
    bg0.addChild(tg1);

    JoglTransformGroup tg2 = new JoglTransformGroup();
    tg2.setCoordinate(new CubeJoglCoordinate());
    tg2.addChild(new CubeJoglObject());
    tg1.addChild(tg2);

    JoglTransformGroup tg3 = new JoglTransformGroup();
    tg3.addChild(new TeapotJoglObject());
    tg3.setCoordinate(new TeapotJoglCoordinate2());
    tg2.addChild(tg3);

    return bg0;
  }

}