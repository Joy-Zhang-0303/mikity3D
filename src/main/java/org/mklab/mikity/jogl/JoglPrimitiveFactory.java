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

    float xsize = box.loadXsize();
    float ysize = box.loadYsize();
    float zsize = box.loadZsize();
    String color = box.loadColor();
    JoglBox child = new JoglBox();
    child.setColor(color);
    child.setSize(xsize, ysize, zsize);

    JoglTransformGroup tg = new JoglTransformGroup();

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
      JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);

    } else if (box.loadLocation() != null && box.loadRotation() == null) {
      float xloc = box.loadLocation().loadX();
      float yloc = box.loadLocation().loadY();
      float zloc = box.loadLocation().loadZ();
      JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (box.loadLocation() == null && box.loadRotation() != null) {
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
      float xloc = cylinder.loadLocation().loadX();
      float yloc = cylinder.loadLocation().loadY();
      float zloc = cylinder.loadLocation().loadZ();
      float xrot = cylinder.loadRotation().loadXrotate();
      float yrot = cylinder.loadRotation().loadYrotate();
      float zrot = cylinder.loadRotation().loadZrotate();
      JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);

    } else if (cylinder.loadLocation() != null && cylinder.loadRotation() == null) {
      float xloc = cylinder.loadLocation().loadX();
      float yloc = cylinder.loadLocation().loadY();
      float zloc = cylinder.loadLocation().loadZ();
      JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (cylinder.loadLocation() == null && cylinder.loadRotation() != null) {
      float xrot = cylinder.loadRotation().loadXrotate();
      float yrot = cylinder.loadRotation().loadYrotate();
      float zrot = cylinder.loadRotation().loadZrotate();
      JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
    }

    int div = cylinder.loadDiv();
    float r = cylinder.loadR();
    float hight = cylinder.loadHeight();
    JoglCylinder child = new JoglCylinder();
    String color = cylinder.loadColor();
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
    JoglTransformGroup tg = new JoglTransformGroup();

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
      float xloc = sphere.loadLocation().loadX();
      float yloc = sphere.loadLocation().loadY();
      float zloc = sphere.loadLocation().loadZ();
      float xrot = sphere.loadRotation().loadXrotate();
      float yrot = sphere.loadRotation().loadYrotate();
      float zrot = sphere.loadRotation().loadZrotate();
      JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);

    } else if (sphere.loadLocation() != null && sphere.loadRotation() == null) {
      float xloc = sphere.loadLocation().loadX();
      float yloc = sphere.loadLocation().loadY();
      float zloc = sphere.loadLocation().loadZ();
      JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (sphere.loadLocation() == null && sphere.loadRotation() != null) {
      float xrot = sphere.loadRotation().loadXrotate();
      float yrot = sphere.loadRotation().loadYrotate();
      float zrot = sphere.loadRotation().loadZrotate();
      JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
    }

    int div = sphere.loadDiv();
    float r = sphere.loadR();
    JoglSphere child = new JoglSphere();
    String color = sphere.loadColor();
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
    JoglTransformGroup tg = new JoglTransformGroup();


    if (cone.loadLocation() != null && cone.loadRotation() != null) {
      float xloc = cone.loadLocation().loadX();
      float yloc = cone.loadLocation().loadY();
      float zloc = cone.loadLocation().loadZ();
      float xrot = cone.loadRotation().loadXrotate();
      float yrot = cone.loadRotation().loadYrotate();
      float zrot = cone.loadRotation().loadZrotate();
      JoglLocRot loc = new JoglLocRot();
      loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(loc);

    } else if (cone.loadLocation() != null && cone.loadRotation() == null) {
      float xloc = cone.loadLocation().loadX();
      float yloc = cone.loadLocation().loadY();
      float zloc = cone.loadLocation().loadZ();
      JoglLocation loc = new JoglLocation();
      loc.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(loc);
    } else if (cone.loadLocation() == null && cone.loadRotation() != null) {
      float xrot = cone.loadRotation().loadXrotate();
      float yrot = cone.loadRotation().loadYrotate();
      float zrot = cone.loadRotation().loadZrotate();
      JoglRotation rot = new JoglRotation();
      rot.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rot);
    }

    int div = cone.loadDiv();
    float r = cone.loadR();
    float hight = cone.loadHeight();
    String color = cone.loadColor();
    JoglCone child = new JoglCone();
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
    JoglTransformGroup tg = new JoglTransformGroup();

    if (parameters == null && links == null) {
      if (triangle.loadLocation() != null && triangle.loadRotation() != null) {
        float xloc = triangle.loadLocation().loadX();
        float yloc = triangle.loadLocation().loadY();
        float zloc = triangle.loadLocation().loadZ();
        float xrot = triangle.loadRotation().loadXrotate();
        float yrot = triangle.loadRotation().loadYrotate();
        float zrot = triangle.loadRotation().loadZrotate();
        JoglLocRot loc = new JoglLocRot();
        loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
        tg.setCoordinate(loc);

      } else if (triangle.loadLocation() != null && triangle.loadRotation() == null) {
        float xloc = triangle.loadLocation().loadX();
        float yloc = triangle.loadLocation().loadY();
        float zloc = triangle.loadLocation().loadZ();
        JoglLocation loc = new JoglLocation();
        loc.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(loc);
      } else if (triangle.loadLocation() == null && triangle.loadRotation() != null) {
        float xrot = triangle.loadRotation().loadXrotate();
        float yrot = triangle.loadRotation().loadYrotate();
        float zrot = triangle.loadRotation().loadZrotate();
        JoglRotation rot = new JoglRotation();
        rot.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(rot);
      }
    } else if (triangle.loadMatrix().getElement(0,3) != 0.0f || triangle.loadMatrix().getElement(1,3) != 0.0f || triangle.loadMatrix().getElement(2,3) != 0.0f) {
      JoglLocation loc = new JoglLocation();
      loc.setLocation(triangle.loadMatrix().getElement(0,3), triangle.loadMatrix().getElement(1,3), triangle.loadMatrix().getElement(2,3));
      tg.setCoordinate(loc);
    }else if (parameters == null) {
      setDHParameter(parameters, tg);
    } else if (links != null) {
      setLinkParameter(links, tg);
    }

    float[][] point = new float[3][3];
    float xpoint, ypoint, zpoint;
    String color = triangle.loadColor();
    for (int i = 0; i < 3; i++) {
      xpoint = triangle.loadPointLocationX(i);
      ypoint = triangle.loadPointLocationY(i);
      zpoint = triangle.loadPointLocationZ(i);
      point[i][0] = xpoint;
      point[i][1] = ypoint;
      point[i][2] = zpoint;
    }
    JoglTriangleObject child = new JoglTriangleObject();
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
    JoglTransformGroup tg = new JoglTransformGroup();

    if (parameters == null && links == null && quad.loadMatrix().getElement(0,3) == 0.0f && quad.loadMatrix().getElement(1,3) == 0.0f && quad.loadMatrix().getElement(2,3) == 0.0f) {
      if (quad.loadLocation() != null && quad.loadRotation() != null) {
        float xloc = quad.loadLocation().loadX();
        float yloc = quad.loadLocation().loadY();
        float zloc = quad.loadLocation().loadZ();
        float xrot = quad.loadRotation().loadXrotate();
        float yrot = quad.loadRotation().loadYrotate();
        float zrot = quad.loadRotation().loadZrotate();
        JoglLocRot loc = new JoglLocRot();
        loc.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
        tg.setCoordinate(loc);

      } else if (quad.loadLocation() != null && quad.loadRotation() == null) {
        float xloc = quad.loadLocation().loadX();
        float yloc = quad.loadLocation().loadY();
        float zloc = quad.loadLocation().loadZ();
        JoglLocation loc = new JoglLocation();
        loc.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(loc);
      } else if (quad.loadLocation() == null && quad.loadRotation() != null) {
        float xrot = quad.loadRotation().loadXrotate();
        float yrot = quad.loadRotation().loadYrotate();
        float zrot = quad.loadRotation().loadZrotate();
        JoglRotation rot = new JoglRotation();
        rot.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(rot);
      }

    } else if (quad.loadMatrix().getElement(0,3) != 0.0f || quad.loadMatrix().getElement(1,3) != 0.0f || quad.loadMatrix().getElement(2,3) != 0.0f) {
      JoglLocation loc = new JoglLocation();
      loc.setLocation(quad.loadMatrix().getElement(0,3), quad.loadMatrix().getElement(1,3), quad.loadMatrix().getElement(2,3));
      tg.setCoordinate(loc);
    } else if (parameters == null) {
      setDHParameter(parameters, tg);
    } else if (links != null) {
      setLinkParameter(links, tg);
    }

    float[][] point = new float[4][3];
    float xpoint, ypoint, zpoint;
    String color = quad.loadColor();
    for (int i = 0; i < 4; i++) {
      xpoint = quad.loadPointLocationX(i);
      ypoint = quad.loadPointLocationY(i);
      zpoint = quad.loadPointLocationZ(i);
      point[i][0] = xpoint;
      point[i][1] = ypoint;
      point[i][2] = zpoint;
    }
    JoglQuadObject child = new JoglQuadObject();
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
      locX = locX + (float)links.get(i).getLocX();
      locY = locY + (float)links.get(i).getLocY();
      locZ = locZ + (float)links.get(i).getLocZ();
      rotX = rotX + (float)links.get(i).getRotX();
      rotY = rotY + (float)links.get(i).getRotY();
      rotZ = rotZ + (float)links.get(i).getRotZ();
    }

    if (rotX == 0.0f && rotY == 0.0f && rotZ == 0.0f) {
      JoglLocation loc = new JoglLocation();
      loc.setLocation(locX, locY, locZ);
      tg.setCoordinate(loc);
    } else { //if(locX == 0.0f && locY == 0.0f && locZ == 0.0f){
      JoglLocRot locrot = new JoglLocRot();
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
        
        JoglDHTranslation DHtrans = new JoglDHTranslation();
        DHtrans.setDHtrans(a, alpha, d, theta);
        tg.setCoordinate(DHtrans);
  }
}
