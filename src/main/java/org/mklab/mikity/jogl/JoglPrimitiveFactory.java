package org.mklab.mikity.jogl;

import java.util.List;

import org.mklab.mikity.jogl.models.JoglBox;
import org.mklab.mikity.jogl.models.JoglCone;
import org.mklab.mikity.jogl.models.JoglCylinder;
import org.mklab.mikity.jogl.models.JoglDHTranslation;
import org.mklab.mikity.jogl.models.JoglLocRot;
import org.mklab.mikity.jogl.models.JoglLocation;
import org.mklab.mikity.jogl.models.JoglQuadObject;
import org.mklab.mikity.jogl.models.JoglRotation;
import org.mklab.mikity.jogl.models.JoglSphere;
import org.mklab.mikity.jogl.models.JoglTriangleObject;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * JOGLのプリミティブを生成するファクトリークラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglPrimitiveFactory {

  /**
   * グループを生成します。
   * 
   * @param group グループ
   * @return tg トランスフォームグループ
   */
  public static JoglTransformGroup create(Group group) {
    return new JoglTransformGroupFactory().create(group);
  }

  /**
   * 与えられたboxインスタンスのプリミティブを生成します。
   * 
   * @param box ボックス
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLBox box) {

    float xsize = box.loadXsize();
    float ysize = box.loadYsize();
    float zsize = box.loadZsize();
    final String color = box.loadColor();
    final JoglBox child = new JoglBox();
    child.setColor(color);
    child.setSize(xsize, ysize, zsize);

    final JoglTransformGroup tg = new JoglTransformGroup();

    //    if (parameters == null && links == null) {
    //      if (box.loadLocation() != null && box.loadRotation() != null) {
    //        float xloc = box.loadLocation().loadX();
    //        float yloc = box.loadLocation().loadY();
    //        float zloc = box.loadLocation().loadZ();
    //        float xrot = box.loadRotation().loadXrotate();
    //        float yrot = box.loadRotation().loadYrotate();
    //        float zrot = box.loadRotation().loadZrotate();
    //        JoglLocRot loc = new JoglLocRot();
    //        loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
    //        tg.setCoordinate(loc);
    //
    //      } else if (box.loadLocation() != null && box.loadRotation() == null) {
    //        float xloc = box.loadLocation().loadX();
    //        float yloc = box.loadLocation().loadY();
    //        float zloc = box.loadLocation().loadZ();
    //        JoglLocation loc = new JoglLocation();
    //        loc.setLocation(xloc, yloc, zloc);
    //        tg.setCoordinate(loc);
    //      } else if (box.loadLocation() == null && box.loadRotation() != null) {
    //        float xrot = box.loadRotation().loadXrotate();
    //        float yrot = box.loadRotation().loadYrotate();
    //        float zrot = box.loadRotation().loadZrotate();
    //        JoglRotation rot = new JoglRotation();
    //        rot.setRotation(xrot, yrot, zrot);
    //        tg.setCoordinate(rot);
    //      }
    //    }else if (parameters == null) {
    //      setDHParameter(parameters, tg);
    //    } else if (links != null) {
    //      setLinkParameter(links, tg);
    //    }

    if (box.loadLocation() != null && box.loadRotation() != null) {
      float xloc = box.loadLocation().loadX() * 10f;
      float yloc = box.loadLocation().loadY() * 10f;
      float zloc = box.loadLocation().loadZ() * 10f;
      float xrot = box.loadRotation().loadXrotate();
      float yrot = box.loadRotation().loadYrotate();
      float zrot = box.loadRotation().loadZrotate();
      final JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);

    } else if (box.loadLocation() != null && box.loadRotation() == null) {
      float xloc = box.loadLocation().loadX();
      float yloc = box.loadLocation().loadY();
      float zloc = box.loadLocation().loadZ();
      final JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (box.loadLocation() == null && box.loadRotation() != null) {
      float xrot = box.loadRotation().loadXrotate();
      float yrot = box.loadRotation().loadYrotate();
      float zrot = box.loadRotation().loadZrotate();
      final JoglRotation rot = new JoglRotation();
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
    final JoglTransformGroup tg = new JoglTransformGroup();

    //    if (parameters == null && links == null) {
    //      if (cylinder.loadLocation() != null && cylinder.loadRotation() != null) {
    //        float xloc = cylinder.loadLocation().loadX();
    //        float yloc = cylinder.loadLocation().loadY();
    //        float zloc = cylinder.loadLocation().loadZ();
    //        float xrot = cylinder.loadRotation().loadXrotate();
    //        float yrot = cylinder.loadRotation().loadYrotate();
    //        float zrot = cylinder.loadRotation().loadZrotate();
    //        JoglLocRot loc = new JoglLocRot();
    //        loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
    //        tg.setCoordinate(loc);
    //
    //      } else if (cylinder.loadLocation() != null && cylinder.loadRotation() == null) {
    //        float xloc = cylinder.loadLocation().loadX();
    //        float yloc = cylinder.loadLocation().loadY();
    //        float zloc = cylinder.loadLocation().loadZ();
    //        JoglLocation loc = new JoglLocation();
    //        loc.setLocation(xloc, yloc, zloc);
    //        tg.setCoordinate(loc);
    //      } else if (cylinder.loadLocation() == null && cylinder.loadRotation() != null) {
    //        float xrot = cylinder.loadRotation().loadXrotate();
    //        float yrot = cylinder.loadRotation().loadYrotate();
    //        float zrot = cylinder.loadRotation().loadZrotate();
    //        JoglRotation rot = new JoglRotation();
    //        rot.setRotation(xrot, yrot, zrot);
    //        tg.setCoordinate(rot);
    //      }
    //    }else if (parameters == null) {
    //      setDHParameter(parameters, tg);
    //    } else if (links != null) {
    //      setLinkParameter(links, tg);
    //    }

    if (cylinder.loadLocation() != null && cylinder.loadRotation() != null) {
      final float xloc = cylinder.loadLocation().loadX();
      final float yloc = cylinder.loadLocation().loadY();
      final float zloc = cylinder.loadLocation().loadZ();
      final float xrot = cylinder.loadRotation().loadXrotate();
      final float yrot = cylinder.loadRotation().loadYrotate();
      final float zrot = cylinder.loadRotation().loadZrotate();
      final JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);

    } else if (cylinder.loadLocation() != null && cylinder.loadRotation() == null) {
      final float xloc = cylinder.loadLocation().loadX();
      final float yloc = cylinder.loadLocation().loadY();
      final float zloc = cylinder.loadLocation().loadZ();
      final JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (cylinder.loadLocation() == null && cylinder.loadRotation() != null) {
      final float xrot = cylinder.loadRotation().loadXrotate();
      final float yrot = cylinder.loadRotation().loadYrotate();
      final float zrot = cylinder.loadRotation().loadZrotate();
      final JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
    }

    final int div = cylinder.loadDiv();
    final float r = cylinder.loadR();
    final float hight = cylinder.loadHeight();
    final JoglCylinder child = new JoglCylinder();
    final String color = cylinder.loadColor();
    child.setColor(color);
    child.setSize(div, r, hight);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param sphere 球体
   * @return 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLSphere sphere) {
    final JoglTransformGroup tg = new JoglTransformGroup();

    //    if (parameters == null && links == null) {
    //      if (sphere.loadLocation() != null && sphere.loadRotation() != null) {
    //        float xloc = sphere.loadLocation().loadX();
    //        float yloc = sphere.loadLocation().loadY();
    //        float zloc = sphere.loadLocation().loadZ();
    //        float xrot = sphere.loadRotation().loadXrotate();
    //        float yrot = sphere.loadRotation().loadYrotate();
    //        float zrot = sphere.loadRotation().loadZrotate();
    //        JoglLocRot loc = new JoglLocRot();
    //        loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
    //        tg.setCoordinate(loc);
    //
    //      } else if (sphere.loadLocation() != null && sphere.loadRotation() == null) {
    //        float xloc = sphere.loadLocation().loadX();
    //        float yloc = sphere.loadLocation().loadY();
    //        float zloc = sphere.loadLocation().loadZ();
    //        JoglLocation loc = new JoglLocation();
    //        loc.setLocation(xloc, yloc, zloc);
    //        tg.setCoordinate(loc);
    //      } else if (sphere.loadLocation() == null && sphere.loadRotation() != null) {
    //        float xrot = sphere.loadRotation().loadXrotate();
    //        float yrot = sphere.loadRotation().loadYrotate();
    //        float zrot = sphere.loadRotation().loadZrotate();
    //        JoglRotation rot = new JoglRotation();
    //        rot.setRotation(xrot, yrot, zrot);
    //        tg.setCoordinate(rot);
    //      }
    //    }else if (parameters == null) {
    //      setDHParameter(parameters, tg);
    //    } else if (links != null) {
    //      setLinkParameter(links, tg);
    //    }
    if (sphere.loadLocation() != null && sphere.loadRotation() != null) {
      final float xloc = sphere.loadLocation().loadX();
      final float yloc = sphere.loadLocation().loadY();
      final float zloc = sphere.loadLocation().loadZ();
      final float xrot = sphere.loadRotation().loadXrotate();
      final float yrot = sphere.loadRotation().loadYrotate();
      final float zrot = sphere.loadRotation().loadZrotate();
      final JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);
    } else if (sphere.loadLocation() != null && sphere.loadRotation() == null) {
      final float xloc = sphere.loadLocation().loadX();
      final float yloc = sphere.loadLocation().loadY();
      final float zloc = sphere.loadLocation().loadZ();
      final JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (sphere.loadLocation() == null && sphere.loadRotation() != null) {
      final float xrot = sphere.loadRotation().loadXrotate();
      final float yrot = sphere.loadRotation().loadYrotate();
      final float zrot = sphere.loadRotation().loadZrotate();
      final JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
    }

    final int div = sphere.loadDiv();
    final float r = sphere.loadR();
    final JoglSphere child = new JoglSphere();
    final String color = sphere.loadColor();
    child.setColor(color);
    child.setSize(div, r);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param cone コーン
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLCone cone) {
    final JoglTransformGroup tg = new JoglTransformGroup();

    if (cone.loadLocation() != null && cone.loadRotation() != null) {
      final float xloc = cone.loadLocation().loadX();
      final float yloc = cone.loadLocation().loadY();
      final float zloc = cone.loadLocation().loadZ();
      final float xrot = cone.loadRotation().loadXrotate();
      final float yrot = cone.loadRotation().loadYrotate();
      final float zrot = cone.loadRotation().loadZrotate();
      final JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);
    } else if (cone.loadLocation() != null && cone.loadRotation() == null) {
      final float xloc = cone.loadLocation().loadX();
      final float yloc = cone.loadLocation().loadY();
      final float zloc = cone.loadLocation().loadZ();
      final JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (cone.loadLocation() == null && cone.loadRotation() != null) {
      final float xrot = cone.loadRotation().loadXrotate();
      final float yrot = cone.loadRotation().loadYrotate();
      final float zrot = cone.loadRotation().loadZrotate();
      final JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
    }

    final int div = cone.loadDiv();
    final float r = cone.loadR();
    final float hight = cone.loadHeight();
    final String color = cone.loadColor();
    final JoglCone child = new JoglCone();
    child.setColor(color);
    child.setSize(div, r, hight);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param triangle 三角形のポリゴン
   * @param links リンクパラメータ
   * @param parameters DHパラメータ
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLTrianglePolygon triangle, List<DHParameter> parameters, List<CoordinateParameter> links) {
    final JoglTransformGroup tg = new JoglTransformGroup();

    if (parameters == null && links == null) {
      if (triangle.loadLocation() != null && triangle.loadRotation() != null) {
        final float xloc = triangle.loadLocation().loadX();
        final float yloc = triangle.loadLocation().loadY();
        final float zloc = triangle.loadLocation().loadZ();
        final float xrot = triangle.loadRotation().loadXrotate();
        final float yrot = triangle.loadRotation().loadYrotate();
        final float zrot = triangle.loadRotation().loadZrotate();
        final JoglLocRot loc = new JoglLocRot();
        loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
        tg.setCoordinate(loc);
      } else if (triangle.loadLocation() != null && triangle.loadRotation() == null) {
        final float xloc = triangle.loadLocation().loadX();
        final float yloc = triangle.loadLocation().loadY();
        final float zloc = triangle.loadLocation().loadZ();
        final JoglLocation loc = new JoglLocation();
        loc.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(loc);
      } else if (triangle.loadLocation() == null && triangle.loadRotation() != null) {
        final float xrot = triangle.loadRotation().loadXrotate();
        final float yrot = triangle.loadRotation().loadYrotate();
        final float zrot = triangle.loadRotation().loadZrotate();
        final JoglRotation rot = new JoglRotation();
        rot.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(rot);
      }
    } else if (triangle.loadMatrix().getElement(0, 3) != 0.0f || triangle.loadMatrix().getElement(1, 3) != 0.0f || triangle.loadMatrix().getElement(2, 3) != 0.0f) {
      final JoglLocation loc = new JoglLocation();
      loc.setLocation(triangle.loadMatrix().getElement(0, 3), triangle.loadMatrix().getElement(1, 3), triangle.loadMatrix().getElement(2, 3));
      tg.setCoordinate(loc);
    } else if (parameters == null) {
      setDHParameter(parameters, tg);
    } else if (links != null) {
      setLinkParameter(links, tg);
    }

    final float[][] point = new float[3][3];
    float xpoint, ypoint, zpoint;
    final String color = triangle.loadColor();
    for (int i = 0; i < 3; i++) {
      xpoint = triangle.loadPointLocationX(i);
      ypoint = triangle.loadPointLocationY(i);
      zpoint = triangle.loadPointLocationZ(i);
      point[i][0] = xpoint;
      point[i][1] = ypoint;
      point[i][2] = zpoint;
    }
    final JoglTriangleObject child = new JoglTriangleObject();
    child.setColor(color);
    child.setSize(point);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param quad 四角形のポリゴン
   * @param links リンクパラメータ
   * @param parameters DHパラメータ
   * @return　tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLQuadPolygon quad, List<DHParameter> parameters, List<CoordinateParameter> links) {
    final JoglTransformGroup tg = new JoglTransformGroup();

    if (parameters == null && links == null && quad.loadMatrix().getElement(0, 3) == 0.0f && quad.loadMatrix().getElement(1, 3) == 0.0f && quad.loadMatrix().getElement(2, 3) == 0.0f) {
      if (quad.loadLocation() != null && quad.loadRotation() != null) {
        final float xloc = quad.loadLocation().loadX();
        final float yloc = quad.loadLocation().loadY();
        final float zloc = quad.loadLocation().loadZ();
        final float xrot = quad.loadRotation().loadXrotate();
        final float yrot = quad.loadRotation().loadYrotate();
        final float zrot = quad.loadRotation().loadZrotate();
        final JoglLocRot loc = new JoglLocRot();
        loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
        tg.setCoordinate(loc);
      } else if (quad.loadLocation() != null && quad.loadRotation() == null) {
        final float xloc = quad.loadLocation().loadX();
        final float yloc = quad.loadLocation().loadY();
        final float zloc = quad.loadLocation().loadZ();
        final JoglLocation loc = new JoglLocation();
        loc.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(loc);
      } else if (quad.loadLocation() == null && quad.loadRotation() != null) {
        final float xrot = quad.loadRotation().loadXrotate();
        final float yrot = quad.loadRotation().loadYrotate();
        final float zrot = quad.loadRotation().loadZrotate();
        final JoglRotation rot = new JoglRotation();
        rot.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(rot);
      }
    } else if (quad.loadMatrix().getElement(0, 3) != 0.0f || quad.loadMatrix().getElement(1, 3) != 0.0f || quad.loadMatrix().getElement(2, 3) != 0.0f) {
      final JoglLocation loc = new JoglLocation();
      loc.setLocation(quad.loadMatrix().getElement(0, 3), quad.loadMatrix().getElement(1, 3), quad.loadMatrix().getElement(2, 3));
      tg.setCoordinate(loc);
    } else if (parameters == null) {
      setDHParameter(parameters, tg);
    } else if (links != null) {
      setLinkParameter(links, tg);
    }

    final float[][] point = new float[4][3];
    float xpoint, ypoint, zpoint;
    final String color = quad.loadColor();
    for (int i = 0; i < 4; i++) {
      xpoint = quad.loadPointLocationX(i);
      ypoint = quad.loadPointLocationY(i);
      zpoint = quad.loadPointLocationZ(i);
      point[i][0] = xpoint;
      point[i][1] = ypoint;
      point[i][2] = zpoint;
    }

    final JoglQuadObject child = new JoglQuadObject();
    child.setColor(color);
    child.setSize(point);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param links リンクパラメータのリスト
   * @param tg JOGLトランスフォームグループ
   */
  public static void setLinkParameter(List<CoordinateParameter> links, JoglTransformGroup tg) {
    float locX = 0, locY = 0, locZ = 0, rotX = 0, rotY = 0, rotZ = 0;

    for (int i = 0; i < links.size(); i++) {
      locX = locX + (float)links.get(i).getX();
      locY = locY + (float)links.get(i).getY();
      locZ = locZ + (float)links.get(i).getZ();
      rotX = rotX + (float)links.get(i).getRotX();
      rotY = rotY + (float)links.get(i).getThY();
      rotZ = rotZ + (float)links.get(i).getThZ();
    }

    if (rotX == 0.0f && rotY == 0.0f && rotZ == 0.0f) {
      final JoglLocation loc = new JoglLocation();
      loc.setLocation(locX, locY, locZ);
      tg.setCoordinate(loc);
    } else { //if(locX == 0.0f && locY == 0.0f && locZ == 0.0f){
      final JoglLocRot locrot = new JoglLocRot();
      locrot.setLocRot(locX, locY, locZ, rotX, rotY, rotZ);
      tg.setCoordinate(locrot);

    }
  }

  /**
   * @param param DHパラメータのリスト
   * @param tg JOGLトランスフォームグループ
   */
  public static void setDHParameter(List<DHParameter> param, JoglTransformGroup tg) {
    float a = 0, alpha = 0, d = 0, theta = 0;
    for (int i = 0; i < param.size(); i++) {
      a += param.get(i).getA();
      alpha += param.get(i).getAlpha();
      d += param.get(i).getD();
      theta += param.get(i).getTheta();

    }

    final JoglDHTranslation DHtrans = new JoglDHTranslation();
    DHtrans.setDHtrans(a, alpha, d, theta);
    tg.setCoordinate(DHtrans);
  }
}
