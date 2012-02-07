package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.mklab.mikity.gui.SceneGraphTree;
import org.mklab.mikity.java3d.MyTransformGroup;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


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
   * @return オブジェクトのグループ
   */
  public JoglBranchGroup[] setModels() {

    List<JoglBranchGroup> groups = new ArrayList<JoglBranchGroup>();

    JoglBranchGroup bg0 = new JoglBranchGroup();
    
    JoglTransformGroup tg1 = new JoglTransformGroup();
    tg1.addCoordinate(new TeapotJoglCoordinate1());
    JoglBranchGroup bg1 = new JoglBranchGroup();
    bg1.addObject(createTeapotCubeTeapot());
       
    JoglTransformGroup tg2 = new JoglTransformGroup();
    tg2.addCoordinate(new TeapotJoglCoordinate3());
    JoglBranchGroup bg2 = new JoglBranchGroup();
    bg2.addObject(createTeapotCubeTeapot());

    JoglTransformGroup tg3 = new JoglTransformGroup();
    tg3.addCoordinate(new TeapotJoglCoordinate3());
    JoglBranchGroup bg3 = new JoglBranchGroup();
    bg3.addObject(createTeapotCubeTeapot());

    JoglTransformGroup tg4 = new JoglTransformGroup();
    tg4.addCoordinate(new TeapotJoglCoordinate3());
    JoglBranchGroup bg4 = new JoglBranchGroup();
    bg4.addObject(createTeapotCubeTeapot());
    
    JoglTransformGroup tg5 = new JoglTransformGroup();
    tg5.addCoordinate(new TeapotJoglCoordinate3());
    JoglBranchGroup bg5 = new JoglBranchGroup();
    bg5.addObject(createTeapotCubeTeapot());
    
    JoglTransformGroup tg6 = new JoglTransformGroup();
    tg6.addCoordinate(new TeapotJoglCoordinate3());
    JoglBranchGroup bg6 = new JoglBranchGroup();
    bg6.addObject(createTeapotCubeTeapot());

    bg0.setChild(tg1);
    tg1.setChild(bg1);
    bg1.setChild(tg2);
    tg2.setChild(bg2);
    bg2.setChild(tg3);
    tg3.setChild(bg3);
    bg3.setChild(tg4);
    tg4.setChild(bg4);
    bg4.setChild(tg5);
    tg5.setChild(bg5);
    bg5.setChild(tg6);
    tg6.setChild(bg6);
    
    groups.add(bg0);

    return groups.toArray(new JoglBranchGroup[groups.size()]);
  }

  /**
   * TeapotとCubeとTeapotが連結したオブジェクトを生成します。
   * 
   * @return TeapotとCubeとTeapotが連結したオブジェクト
   */
  public JoglBranchGroup createTeapotCubeTeapot() {
    JoglBranchGroup bg0 = new JoglBranchGroup();
    
    JoglBranchGroup bg1 = new JoglBranchGroup();
    JoglTransformGroup tg1 = new JoglTransformGroup();
    tg1.addCoordinate(new TeapotJoglCoordinate1());
    bg1.addObject(new TeapotJoglObject());

    JoglBranchGroup bg2 = new JoglBranchGroup();
    JoglTransformGroup tg2 = new JoglTransformGroup();
    tg2.addCoordinate(new CubeJoglCoordinate());
    bg2.addObject(new CubeJoglObject());
    
    JoglBranchGroup bg3 = new JoglBranchGroup();
    JoglTransformGroup tg3 = new JoglTransformGroup();
    bg3.addObject(new TeapotJoglObject());
    tg3.addCoordinate(new TeapotJoglCoordinate2());

    bg0.setChild(tg1);
    tg1.setChild(bg1);
    bg1.setChild(tg2);
    tg2.setChild(bg2);
    bg2.setChild(tg3);
    tg3.setChild(bg3);
    
    return bg0;
  }

}
