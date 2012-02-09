package org.mklab.mikity.jogl;

import org.mklab.mikity.jogl.models.JoglBox;
import org.mklab.mikity.jogl.models.JoglCone;
import org.mklab.mikity.jogl.models.JoglCylinder;
import org.mklab.mikity.jogl.models.JoglLocation;
import org.mklab.mikity.jogl.models.JoglPolygonLocation;
import org.mklab.mikity.jogl.models.JoglQuadObject;
import org.mklab.mikity.jogl.models.JoglRotation;
import org.mklab.mikity.jogl.models.JoglSphere;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


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

    float xsize = box.loadXsize() * 10f;
    float ysize = box.loadYsize() * 10f;
    float zsize = box.loadZsize() * 10f;
    JoglBox child = new JoglBox();
    child.setSize(xsize, ysize, zsize);

    JoglTransformGroup tg = new JoglTransformGroup();

    if (box.loadLocation() != null) {
      float x = box.loadLocation().loadX() * 10f;
      float y = box.loadLocation().loadY() * 10f;
      float z = box.loadLocation().loadZ() * 10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }

    if (box.loadRotation() != null) {
      float xrot = box.loadRotation().loadXrotate();
      float yrot = box.loadRotation().loadYrotate();
      float zrot = box.loadRotation().loadZrotate();
      JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
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

    if (cylinder.loadLocation() != null) {
      float xloc = cylinder.loadLocation().loadX() * 10f;
      float yloc = cylinder.loadLocation().loadY() * 10f;
      float zloc = cylinder.loadLocation().loadZ() * 10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    }

    if (cylinder.loadRotation() != null) {
      float xrot = cylinder.loadRotation().loadXrotate();
      float yrot = cylinder.loadRotation().loadYrotate();
      float zrot = cylinder.loadRotation().loadZrotate();
      JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
    }

    int div = cylinder.loadDiv();
    float r = cylinder.loadR() * 10.0f;
    float hight = cylinder.loadHeight() * 10.0f;
    JoglCylinder child = new JoglCylinder();
    child.setSize(div, r, hight);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param sphere 球体
   * @return 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLSphere sphere) {
    JoglTransformGroup tg = new JoglTransformGroup();

    if (sphere.loadLocation() != null) {
      float x = sphere.loadLocation().loadX() * 10f;
      float y = sphere.loadLocation().loadY() * 10f;
      float z = sphere.loadLocation().loadZ() * 10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }

    int div = sphere.loadDiv();
    float r = sphere.loadR() * 10.0f;
    JoglSphere child = new JoglSphere();
    child.setSize(div, r);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param cone コーン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLCone cone) {
    JoglTransformGroup tg = new JoglTransformGroup();

    if (cone.loadLocation() != null) {
      float x = cone.loadLocation().loadX() * 10f;
      float y = cone.loadLocation().loadY() * 10f;
      float z = cone.loadLocation().loadZ() * 10f;
      JoglLocation loc = new JoglLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }

    int div = cone.loadDiv();
    float r = cone.loadR() * 10.0f;
    float hight = cone.loadHeight() * 10.0f;
    JoglCone child = new JoglCone();
    child.setSize(div, r, hight);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param triangle 三角形のポリゴン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLTrianglePolygon triangle) {
    JoglTransformGroup tg = new JoglTransformGroup();

    if (triangle.loadLocation() != null) {
      float x = triangle.loadLocation().loadX() * 10f;
      float y = triangle.loadLocation().loadY() * 10f;
      float z = triangle.loadLocation().loadZ() * 10f;
      JoglLocation loc = new JoglLocation();
//      JoglPolygonLocation loc = new JoglPolygonLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }

    float[][] point = new float[3][3];
    float xpoint, ypoint, zpoint;
    for (int i = 0; i < 3; i++) {
      xpoint = triangle.loadPointLocationX(i) * 10f;
      ypoint = triangle.loadPointLocationY(i) * 10f;
      zpoint = triangle.loadPointLocationZ(i) * 10f;
      point[i][0] = xpoint;
      point[i][1] = ypoint;
      point[i][2] = zpoint;
    }
    JoglTriangleObject child = new JoglTriangleObject();
    child.setSize(point);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param quad 四角形のポリゴン
   * @return　tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLQuadPolygon quad) {
    JoglTransformGroup tg = new JoglTransformGroup();

    if (quad.loadLocation() != null) {
      float x = quad.loadLocation().loadX() * 10f;
      float y = quad.loadLocation().loadY() * 10f;
      float z = quad.loadLocation().loadZ() * 10f;
      JoglLocation loc = new JoglLocation();
//      JoglPolygonLocation loc = new JoglPolygonLocation();
      loc.setLocation(x, y, z);
      tg.setCoordinate(loc);
    }

    float[][] point = new float[4][3];
    float xpoint, ypoint, zpoint;
    for (int i = 0; i < 4; i++) {
      xpoint = quad.loadPointLocationX(i) * 10f;
      ypoint = quad.loadPointLocationY(i) * 10f;
      zpoint = quad.loadPointLocationZ(i) * 10f;
      point[i][0] = xpoint;
      point[i][1] = ypoint;
      point[i][2] = zpoint;
    }
    JoglQuadObject child = new JoglQuadObject();
    child.setSize(point);
    tg.addChild(child);

    return tg;

  }
}
