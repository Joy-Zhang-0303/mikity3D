package org.mklab.mikity.jogl;

import java.util.List;

import javax.vecmath.Matrix4f;

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
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
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

    final Location boxLocation = box.loadLocation();
    final Rotation boxRotation = box.loadRotation();
    
    if (boxLocation != null && boxRotation != null) {
      final float xloc = boxLocation.loadX() * 10f;
      final float yloc = boxLocation.loadY() * 10f;
      final float zloc = boxLocation.loadZ() * 10f;
      final float xrot = boxRotation.loadXrotate();
      final float yrot = boxRotation.loadYrotate();
      final float zrot = boxRotation.loadZrotate();
      final JoglLocRot locationRotation = new JoglLocRot();
      locationRotation.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(locationRotation);
    } else if (boxLocation != null) {
      final float xloc = boxLocation.loadX();
      final float yloc = boxLocation.loadY();
      final float zloc = boxLocation.loadZ();
      final JoglLocation locationRotation = new JoglLocation();
      locationRotation.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(locationRotation);
    } else if (boxRotation != null) {
      final float xrot = boxRotation.loadXrotate();
      final float yrot = boxRotation.loadYrotate();
      final float zrot = boxRotation.loadZrotate();
      final JoglRotation rotation = new JoglRotation();
      rotation.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rotation);
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

    final Location cylinderLocation = cylinder.loadLocation();
    final Rotation cylinderRotation = cylinder.loadRotation();
    
    if (cylinderLocation != null && cylinderRotation != null) {
      final float xloc = cylinderLocation.loadX();
      final float yloc = cylinderLocation.loadY();
      final float zloc = cylinderLocation.loadZ();
      final float xrot = cylinderRotation.loadXrotate();
      final float yrot = cylinderRotation.loadYrotate();
      final float zrot = cylinderRotation.loadZrotate();
      final JoglLocRot locationRotation = new JoglLocRot();
      locationRotation.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(locationRotation);
    } else if (cylinderLocation != null) {
      final float xloc = cylinderLocation.loadX();
      final float yloc = cylinderLocation.loadY();
      final float zloc = cylinderLocation.loadZ();
      final JoglLocation location = new JoglLocation();
      location.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(location);
    } else if (cylinderRotation != null) {
      final float xrot = cylinderRotation.loadXrotate();
      final float yrot = cylinderRotation.loadYrotate();
      final float zrot = cylinderRotation.loadZrotate();
      final JoglRotation rotation = new JoglRotation();
      rotation.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rotation);
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
    
    final Location sphereLocation = sphere.loadLocation();
    final Rotation sphereRotation = sphere.loadRotation();
    
    if (sphereLocation != null && sphereRotation != null) {
      final float xloc = sphereLocation.loadX();
      final float yloc = sphereLocation.loadY();
      final float zloc = sphereLocation.loadZ();
      final float xrot = sphereRotation.loadXrotate();
      final float yrot = sphereRotation.loadYrotate();
      final float zrot = sphereRotation.loadZrotate();
      final JoglLocRot locationRotation = new JoglLocRot();
      locationRotation.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(locationRotation);
    } else if (sphereLocation != null) {
      final float xloc = sphereLocation.loadX();
      final float yloc = sphereLocation.loadY();
      final float zloc = sphereLocation.loadZ();
      final JoglLocation location = new JoglLocation();
      location.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(location);
    } else if (sphereRotation != null) {
      final float xrot = sphereRotation.loadXrotate();
      final float yrot = sphereRotation.loadYrotate();
      final float zrot = sphereRotation.loadZrotate();
      final JoglRotation rotation = new JoglRotation();
      rotation.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rotation);
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

    final Location coneLocation = cone.loadLocation();
    final Rotation coneRotation = cone.loadRotation();
    
    if (coneLocation != null && coneRotation != null) {
      final float xloc = coneLocation.loadX();
      final float yloc = coneLocation.loadY();
      final float zloc = coneLocation.loadZ();
      final float xrot = coneRotation.loadXrotate();
      final float yrot = coneRotation.loadYrotate();
      final float zrot = coneRotation.loadZrotate();
      final JoglLocRot locationRotation = new JoglLocRot();
      locationRotation.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
      tg.setCoordinate(locationRotation);
    } else if (coneLocation != null) {
      final float xloc = coneLocation.loadX();
      final float yloc = coneLocation.loadY();
      final float zloc = coneLocation.loadZ();
      final JoglLocation location = new JoglLocation();
      location.setLocation(xloc, yloc, zloc);
      tg.setCoordinate(location);
    } else if (coneRotation != null) {
      final float xrot = coneRotation.loadXrotate();
      final float yrot = coneRotation.loadYrotate();
      final float zrot = coneRotation.loadZrotate();
      final JoglRotation rotation = new JoglRotation();
      rotation.setRotation(xrot, yrot, zrot);
      tg.setCoordinate(rotation);
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
   * @param coordinateParameters リンクパラメータ
   * @param dhParameters DHパラメータ
   * @return tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLTrianglePolygon triangle, List<DHParameter> dhParameters, List<CoordinateParameter> coordinateParameters) {
    final JoglTransformGroup tg = new JoglTransformGroup();

    final Matrix4f triangleMatrix = triangle.loadMatrix();
    
    if (dhParameters == null && coordinateParameters == null && triangleMatrix.getElement(0, 3) == 0.0f && triangleMatrix.getElement(1, 3) == 0.0f && triangleMatrix.getElement(2, 3) == 0.0f) {
      final Location triangleLocation = triangle.loadLocation();
      final Rotation triangleRotation = triangle.loadRotation();
      
      if (triangleLocation != null && triangleRotation != null) {
        final float xloc = triangleLocation.loadX();
        final float yloc = triangleLocation.loadY();
        final float zloc = triangleLocation.loadZ();
        final float xrot = triangleRotation.loadXrotate();
        final float yrot = triangleRotation.loadYrotate();
        final float zrot = triangleRotation.loadZrotate();
        final JoglLocRot locationRotation = new JoglLocRot();
        locationRotation.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
        tg.setCoordinate(locationRotation);
      } else if (triangleLocation != null) {
        final float xloc = triangleLocation.loadX();
        final float yloc = triangleLocation.loadY();
        final float zloc = triangleLocation.loadZ();
        final JoglLocation location = new JoglLocation();
        location.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(location);
      } else if (triangleRotation != null) {
        final float xrot = triangleRotation.loadXrotate();
        final float yrot = triangleRotation.loadYrotate();
        final float zrot = triangleRotation.loadZrotate();
        final JoglRotation rotation = new JoglRotation();
        rotation.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(rotation);
      }
    } else if (triangleMatrix.getElement(0, 3) != 0.0f || triangleMatrix.getElement(1, 3) != 0.0f || triangleMatrix.getElement(2, 3) != 0.0f) {
      final JoglLocation location = new JoglLocation();
      location.setLocation(triangleMatrix.getElement(0, 3), triangleMatrix.getElement(1, 3), triangleMatrix.getElement(2, 3));
      tg.setCoordinate(location);
    } else if (dhParameters != null) {
      setDHParameter(dhParameters, tg);
    } else if (coordinateParameters != null) {
      setLinkParameter(coordinateParameters, tg);
    }

    final String color = triangle.loadColor();
    
    final float[][] point = new float[3][3];
    for (int i = 0; i < 3; i++) {
      point[i][0] = triangle.loadPointLocationX(i);
      point[i][1] = triangle.loadPointLocationY(i);
      point[i][2] = triangle.loadPointLocationZ(i);
    }
    final JoglTriangleObject child = new JoglTriangleObject();
    child.setColor(color);
    child.setSize(point);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param quad 四角形のポリゴン
   * @param coordinateParameters リンクパラメータ
   * @param dhParameters DHパラメータ
   * @return　tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLQuadPolygon quad, List<DHParameter> dhParameters, List<CoordinateParameter> coordinateParameters) {
    final JoglTransformGroup tg = new JoglTransformGroup();
    final Matrix4f matrix = quad.loadMatrix();
    
    if (dhParameters == null && coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Location quadLocation = quad.loadLocation();
      final Rotation quadRotation = quad.loadRotation();
      if (quadLocation != null && quadRotation != null) {
        final float xloc = quadLocation.loadX();
        final float yloc = quadLocation.loadY();
        final float zloc = quadLocation.loadZ();
        final float xrot = quadRotation.loadXrotate();
        final float yrot = quadRotation.loadYrotate();
        final float zrot = quadRotation.loadZrotate();
        final JoglLocRot locationRotation = new JoglLocRot();
        locationRotation.setLocRot(xloc, yloc, zloc, xrot, yrot, zrot);
        tg.setCoordinate(locationRotation);
      } else if (quadLocation != null) {
        final float xloc = quadLocation.loadX();
        final float yloc = quadLocation.loadY();
        final float zloc = quadLocation.loadZ();
        final JoglLocation location = new JoglLocation();
        location.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(location);
      } else if (quadRotation != null) {
        final float xrot = quadRotation.loadXrotate();
        final float yrot = quadRotation.loadYrotate();
        final float zrot = quadRotation.loadZrotate();
        final JoglRotation rotation = new JoglRotation();
        rotation.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(rotation);
      }
    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
      final JoglLocation location = new JoglLocation();
      location.setLocation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
      tg.setCoordinate(location);
    } else if (dhParameters != null) {
      setDHParameter(dhParameters, tg);
    } else if (coordinateParameters != null) {
      setLinkParameter(coordinateParameters, tg);
    }

    final String color = quad.loadColor();
    
    final float[][] point = new float[4][3];
    for (int i = 0; i < 4; i++) {
      point[i][0] = quad.loadPointLocationX(i);
      point[i][1] = quad.loadPointLocationY(i);
      point[i][2] = quad.loadPointLocationZ(i);
    }

    final JoglQuadObject child = new JoglQuadObject();
    child.setColor(color);
    child.setSize(point);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param parameters リンクパラメータのリスト
   * @param group JOGLトランスフォームグループ
   */
  public static void setLinkParameter(List<CoordinateParameter> parameters, JoglTransformGroup group) {
    float locX = 0; 
    float locY = 0; 
    float locZ = 0; 
    float rotX = 0; 
    float rotY = 0; 
    float rotZ = 0;

    for (final CoordinateParameter parameter : parameters) {
      locX = locX + (float)parameter.getX();
      locY = locY + (float)parameter.getY();
      locZ = locZ + (float)parameter.getZ();
      rotX = rotX + (float)parameter.getRotX();
      rotY = rotY + (float)parameter.getThY();
      rotZ = rotZ + (float)parameter.getThZ();
    }

    if (rotX == 0.0f && rotY == 0.0f && rotZ == 0.0f) {
      final JoglLocation location = new JoglLocation();
      location.setLocation(locX, locY, locZ);
      group.setCoordinate(location);
    } else { //if(locX == 0.0f && locY == 0.0f && locZ == 0.0f){
      final JoglLocRot locationRotation = new JoglLocRot();
      locationRotation.setLocRot(locX, locY, locZ, rotX, rotY, rotZ);
      group.setCoordinate(locationRotation);
    }
  }

  /**
   * @param parameters DHパラメータのリスト
   * @param group JOGLトランスフォームグループ
   */
  public static void setDHParameter(List<DHParameter> parameters, JoglTransformGroup group) {
    float a = 0; 
    float alpha = 0; 
    float d = 0; 
    float theta = 0;
    
    for (final DHParameter parameter : parameters) {
      a += parameter.getA();
      alpha += parameter.getAlpha();
      d += parameter.getD();
      theta += parameter.getTheta();
    }

    final JoglDHTranslation translation = new JoglDHTranslation();
    translation.setDHtrans(a, alpha, d, theta);
    group.setCoordinate(translation);
  }
}
