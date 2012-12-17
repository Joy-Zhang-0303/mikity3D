package org.mklab.mikity.jogl;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.mklab.mikity.jogl.models.JoglBox;
import org.mklab.mikity.jogl.models.JoglCone;
import org.mklab.mikity.jogl.models.JoglCylinder;
import org.mklab.mikity.jogl.models.JoglDHTranslation;
import org.mklab.mikity.jogl.models.JoglLocationRotation;
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
    final float xsize = box.loadXsize();
    final float ysize = box.loadYsize();
    final float zsize = box.loadZsize();
    final String color = box.loadColor();
    final JoglBox child = new JoglBox();
    child.setColor(color);
    child.setSize(xsize, ysize, zsize);

    final JoglTransformGroup tg = new JoglTransformGroup();

    final Location boxLocation = box.loadLocation();
    final Rotation boxRotation = box.loadRotation();
    
    if (boxLocation != null && boxRotation != null) {
      final float xloc = boxLocation.loadX() * 10f;
      final float yloc = boxLocation.loadY() * 10f;
      final float zloc = boxLocation.loadZ() * 10f;
      final float xrot = boxRotation.loadXrotate();
      final float yrot = boxRotation.loadYrotate();
      final float zrot = boxRotation.loadZrotate();
      final JoglLocationRotation locationRotation = new JoglLocationRotation();
      locationRotation.setLocation(xloc, yloc, zloc);
      locationRotation.setRotation(xrot, yrot, zrot);
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

    final Location cylinderLocation = cylinder.loadLocation();
    final Rotation cylinderRotation = cylinder.loadRotation();
    
    if (cylinderLocation != null && cylinderRotation != null) {
      final float xloc = cylinderLocation.loadX();
      final float yloc = cylinderLocation.loadY();
      final float zloc = cylinderLocation.loadZ();
      final float xrot = cylinderRotation.loadXrotate();
      final float yrot = cylinderRotation.loadYrotate();
      final float zrot = cylinderRotation.loadZrotate();
      final JoglLocationRotation locationRotation = new JoglLocationRotation();
      locationRotation.setLocation(xloc, yloc, zloc);
      locationRotation.setRotation(xrot, yrot, zrot);
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
    final float radius = cylinder.loadR();
    final float hight = cylinder.loadHeight();
    final JoglCylinder child = new JoglCylinder();
    final String color = cylinder.loadColor();
    child.setSize(radius, hight);
    child.setDiv(div);
    child.setColor(color);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param sphere 球体
   * @return 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLSphere sphere) {
    final JoglTransformGroup tg = new JoglTransformGroup();
    
    final Location sphereLocation = sphere.loadLocation();
    final Rotation sphereRotation = sphere.loadRotation();
    
    if (sphereLocation != null && sphereRotation != null) {
      final float xloc = sphereLocation.loadX();
      final float yloc = sphereLocation.loadY();
      final float zloc = sphereLocation.loadZ();
      final float xrot = sphereRotation.loadXrotate();
      final float yrot = sphereRotation.loadYrotate();
      final float zrot = sphereRotation.loadZrotate();
      final JoglLocationRotation locationRotation = new JoglLocationRotation();
      locationRotation.setLocation(xloc, yloc, zloc);
      locationRotation.setRotation(xrot, yrot, zrot);
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
    final float radius = sphere.loadR();
    final JoglSphere child = new JoglSphere();
    final String color = sphere.loadColor();
    child.setSize(radius);
    child.setDiv(div);
    child.setColor(color);
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
      final JoglLocationRotation locationRotation = new JoglLocationRotation();
      locationRotation.setLocation(xloc, yloc, zloc);
      locationRotation.setRotation(xrot, yrot, zrot);
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

    final float radius = cone.loadR();
    final float hight = cone.loadHeight();
    final int div = cone.loadDiv();
    final String color = cone.loadColor();
    final JoglCone child = new JoglCone();
    child.setColor(color);
    child.setSize(radius, hight);
    child.setDiv(div);
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

    final Matrix4f matrix = triangle.loadMatrix();
    
    if (dhParameters == null && coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Location polygonLocation = triangle.loadLocation();
      final Rotation polygonRotation = triangle.loadRotation();
      
      if (polygonLocation != null && polygonRotation != null) {
        final float xloc = polygonLocation.loadX();
        final float yloc = polygonLocation.loadY();
        final float zloc = polygonLocation.loadZ();
        final float xrot = polygonRotation.loadXrotate();
        final float yrot = polygonRotation.loadYrotate();
        final float zrot = polygonRotation.loadZrotate();
        final JoglLocationRotation locationRotation = new JoglLocationRotation();
        locationRotation.setLocation(xloc, yloc, zloc);
        locationRotation.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(locationRotation);
      } else if (polygonLocation != null) {
        final float xloc = polygonLocation.loadX();
        final float yloc = polygonLocation.loadY();
        final float zloc = polygonLocation.loadZ();
        final JoglLocation location = new JoglLocation();
        location.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(location);
      } else if (polygonRotation != null) {
        final float xrot = polygonRotation.loadXrotate();
        final float yrot = polygonRotation.loadYrotate();
        final float zrot = polygonRotation.loadZrotate();
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

    final String color = triangle.loadColor();
    
    final float[][] point = new float[3][3];
    for (int i = 0; i < 3; i++) {
      point[i][0] = triangle.loadPointLocationX(i);
      point[i][1] = triangle.loadPointLocationY(i);
      point[i][2] = triangle.loadPointLocationZ(i);
    }
    final JoglTriangleObject child = new JoglTriangleObject();
    child.setColor(color);
    child.setVertices(point);
    tg.addChild(child);

    return tg;
  }

  /**
   * @param quadPolygon 四角形のポリゴン
   * @param coordinateParameters リンクパラメータ
   * @param dhParameters DHパラメータ
   * @return　tg 与えられたファイルで出来るプリミティブ (XMLBox クラス名 org.mklab.mikity.xml 変数名)
   */
  public static JoglTransformGroup create(XMLQuadPolygon quadPolygon, List<DHParameter> dhParameters, List<CoordinateParameter> coordinateParameters) {
    final JoglTransformGroup tg = new JoglTransformGroup();
    final Matrix4f matrix = quadPolygon.loadMatrix();
    
    if (dhParameters == null && coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Location polygonLocation = quadPolygon.loadLocation();
      final Rotation polygonRotation = quadPolygon.loadRotation();
      if (polygonLocation != null && polygonRotation != null) {
        final float xloc = polygonLocation.loadX();
        final float yloc = polygonLocation.loadY();
        final float zloc = polygonLocation.loadZ();
        final float xrot = polygonRotation.loadXrotate();
        final float yrot = polygonRotation.loadYrotate();
        final float zrot = polygonRotation.loadZrotate();
        final JoglLocationRotation locationRotation = new JoglLocationRotation();
        locationRotation.setLocation(xloc, yloc, zloc);
        locationRotation.setRotation(xrot, yrot, zrot);
        tg.setCoordinate(locationRotation);
      } else if (polygonLocation != null) {
        final float xloc = polygonLocation.loadX();
        final float yloc = polygonLocation.loadY();
        final float zloc = polygonLocation.loadZ();
        final JoglLocation location = new JoglLocation();
        location.setLocation(xloc, yloc, zloc);
        tg.setCoordinate(location);
      } else if (polygonRotation != null) {
        final float xrot = polygonRotation.loadXrotate();
        final float yrot = polygonRotation.loadYrotate();
        final float zrot = polygonRotation.loadZrotate();
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

    final String color = quadPolygon.loadColor();
    
    final float[][] point = new float[4][3];
    for (int i = 0; i < 4; i++) {
      point[i][0] = quadPolygon.loadPointLocationX(i);
      point[i][1] = quadPolygon.loadPointLocationY(i);
      point[i][2] = quadPolygon.loadPointLocationZ(i);
    }

    final JoglQuadObject child = new JoglQuadObject();
    child.setColor(color);
    child.setVertices(point);
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
      final JoglLocationRotation locationRotation = new JoglLocationRotation();
      locationRotation.setLocation(locX, locY, locZ);
      locationRotation.setRotation(rotX, rotY, rotZ);
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
