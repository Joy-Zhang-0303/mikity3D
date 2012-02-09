package org.mklab.mikity.jogl;

import org.mklab.mikity.jogl.models.JoglBox;
import org.mklab.mikity.jogl.models.JoglCone;
import org.mklab.mikity.jogl.models.JoglCylinder;
import org.mklab.mikity.jogl.models.JoglLocation;
import org.mklab.mikity.jogl.models.JoglSphere;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglPrimitiveFactory {
  
  /**
   * グループを作る。
   * 
   * @param group グループ
   * @return tg トランスフォームグループ
   */
  public static JoglTransformGroup create(Group group) {
    
    return JoglTransformGroupFactory.create(group);
  }

  /**
   * 与えられたboxインスタンスのプリミティブを作成する。
   * 
   * @param box ボックス
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLBox box) {

//    float xrot = box.loadRotation().loadXrotate();
//    float yrot = box.loadRotation().loadYrotate();
//    float zrot = box.loadRotation().loadZrotate();
//    JoglRotation rot = new JoglRotation();
//    rot.setLocation(xrot, yrot, zrot);
//    tg.setCoordinate(rot);
    
    float xsize = box.loadXsize()*10f;
    float ysize = box.loadYsize()*10f;
    float zsize = box.loadZsize()*10f;
    JoglBox child = new JoglBox();
    child.setSize(xsize,ysize,zsize);
    
    JoglTransformGroup tg = new JoglTransformGroup();

    if(box.loadLocation() != null){
      float x = box.loadLocation().loadX()*10f;
      float y = box.loadLocation().loadY()*10f;
      float z = box.loadLocation().loadZ()*10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }
    
    tg.addChild(child);

    return tg;
  }

  /**
   * @param cylinder シリンダー
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLCylinder cylinder) {
    JoglTransformGroup tg = new JoglTransformGroup();
    
    if(cylinder.loadLocation() != null){
      float x = cylinder.loadLocation().loadX()*10f;
      float y = cylinder.loadLocation().loadY()*10f;
      float z = cylinder.loadLocation().loadZ()*10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }
    
//    float xrot = cylinder.loadRotation().loadXrotate();
//    float yrot = cylinder.loadRotation().loadYrotate();
//    float zrot = cylinder.loadRotation().loadZrotate();
//    JoglRotation rot = new JoglRotation();
//    rot.setLocation(xrot, yrot, zrot);
//    tg.setCoordinate(rot);
    
    int div = cylinder.loadDiv();
    float r = cylinder.loadR()*10.0f;
    float hight = cylinder.loadHeight()*10.0f;
    JoglCylinder child = new JoglCylinder();
    child.setSize(div,r,hight);
    tg.addChild(child);
    
    return tg;
  }

  /**
   * @param sphere 球体
   * @return 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLSphere sphere) {
    JoglTransformGroup tg = new JoglTransformGroup();
    
    if(sphere.loadLocation() != null){
      float x = sphere.loadLocation().loadX()*10f;
      float y = sphere.loadLocation().loadY()*10f;
      float z = sphere.loadLocation().loadZ()*10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }
    
    int div = sphere.loadDiv();
    float r = sphere.loadR()*10.0f;
    JoglSphere child = new JoglSphere();
    child.setSize(div,r);
    tg.addChild(child);
    
    return tg;
  }
  
  /**
   * @param cone コーン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLCone cone) {
    JoglTransformGroup tg = new JoglTransformGroup();
    
    if(cone.loadLocation() != null){
      float x = cone.loadLocation().loadX()*10f;
      float y = cone.loadLocation().loadY()*10f;
      float z = cone.loadLocation().loadZ()*10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }
    
    int div = cone.loadDiv();
    float r = cone.loadR()*10.0f;
    float hight = cone.loadHeight()*10.0f;
    JoglCone child = new JoglCone();
    child.setSize(div,r,hight);
    tg.addChild(child);
    
    return tg;
  }
  
//  public static JoglTransformGroup create(XMLTrianglePolygon triangle) {
//    JoglTransformGroup tg = new JoglTransformGroup();
//
//    if(triangle.loadLocation() != null){
//      float x = triangle.loadLocation().loadX()*10f;
//      float y = triangle.loadLocation().loadY()*10f;
//      float z = triangle.loadLocation().loadZ()*10f;
//      JoglLocation loc = new JoglLocation();
//      loc.setLocation(x, y, z);
//      tg.setCoordinate(loc);
//    }
//    
//    float xsize = triangle.loadXsize()*10f;
//    float ysize = triangle.loadYsize()*10f;
//    float zsize = triangle.loadZsize()*10f;
//    JoglBox child = new JoglBox();
//    child.setSize(xsize,ysize,zsize);
//    tg.addChild(child);
//
//    return tg;
//  }
}
