package org.mklab.mikity.view.renderer.jogl;

import java.util.List;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.util.Matrix4;
import org.mklab.mikity.view.renderer.jogl.primitive.JoglBox;
import org.mklab.mikity.view.renderer.jogl.primitive.JoglCone;
import org.mklab.mikity.view.renderer.jogl.primitive.JoglCylinder;
import org.mklab.mikity.view.renderer.jogl.primitive.JoglQuadPolygon;
import org.mklab.mikity.view.renderer.jogl.primitive.JoglSphere;
import org.mklab.mikity.view.renderer.jogl.primitive.JoglTrianglePolygon;


/**
 * JOGLのプリミティブを生成するファクトリークラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglPrimitiveFactory {

  /**
   * 可動グループを生成します。
   * 
   * @param group オブジェクトのグループ
   * @return 可動グループ
   */
  public static JoglTransformGroup create(Group group) {
    return new JoglTransformGroupFactory().create(group);
  }

  /**
   * 与えられたboxを含む可動グループを生成します。
   * 
   * @param box ボックス
   * @return 与えられたboxを含む可動グループ
   */
  public static JoglTransformGroup create(XMLBox box) {
    final float width = box.getWidth();
    final float height = box.getHeight();
    final float depth = box.getDepth();
    final String color = box.getColor();
    
    final JoglBox child = new JoglBox();
    child.setColor(color);
    child.setSize(width, height, depth);

    final JoglTransformGroup group = new JoglTransformGroup();

    final Translation boxTranslation = box.getTranslation();
    final Rotation boxRotation = box.getRotation();
    
    if (boxTranslation != null && boxRotation != null) {
      final float xLocation = boxTranslation.getX();
      final float yLocation = boxTranslation.getY();
      final float zLocation = boxTranslation.getZ();
      final float xRotation = boxRotation.getX();
      final float yRotation = boxRotation.getY();
      final float zRotation = boxRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      coordinate.setRotation(xRotation, yRotation, zRotation);
      group.setInitialCoordinate(coordinate);
    } else if (boxTranslation != null) {
      final float translationX = boxTranslation.getX();
      final float translationY = boxTranslation.getY();
      final float translationZ = boxTranslation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(translationX, translationY, translationZ);
      group.setInitialCoordinate(coordinate);
    } else if (boxRotation != null) {
      final float xRotation = boxRotation.getX();
      final float yRotation = boxRotation.getY();
      final float zRotation = boxRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(xRotation, yRotation, zRotation);
      group.setInitialCoordinate(coordinate);
    }
    
    group.addChild(child);
    
    return group;
  }

  /**
   * 与えられたcylinderを含む可動なグループを生成します。
   * 
   * @param cylinder シリンダー
   * @return 与えられたcylinderを含む可動グループ
   */
  public static JoglTransformGroup create(XMLCylinder cylinder) {
    final int div = cylinder.getDivision();
    final float radius = cylinder.getRadius();
    final float hight = cylinder.getHeight();
    final String color = cylinder.getColor();

    final JoglCylinder child = new JoglCylinder();
    child.setSize(radius, hight);
    child.setDivision(div);
    child.setColor(color);
    
    final JoglTransformGroup group = new JoglTransformGroup();
  
    final Translation cylinderLocation = cylinder.getTranslation();
    final Rotation cylinderRotation = cylinder.getRotation();
    
    if (cylinderLocation != null && cylinderRotation != null) {
      final float xLocation = cylinderLocation.getX();
      final float yLocation = cylinderLocation.getY();
      final float zLocation = cylinderLocation.getZ();
      final float xRotation = cylinderRotation.getX();
      final float yRotation = cylinderRotation.getY();
      final float zRotation = cylinderRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      coordinate.setRotation(xRotation, yRotation, zRotation);
      group.setInitialCoordinate(coordinate);
    } else if (cylinderLocation != null) {
      final float xLocation = cylinderLocation.getX();
      final float yLocation = cylinderLocation.getY();
      final float zLocation = cylinderLocation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      group.setInitialCoordinate(coordinate);
    } else if (cylinderRotation != null) {
      final float xRotation = cylinderRotation.getX();
      final float yRotation = cylinderRotation.getY();
      final float zRotation = cylinderRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(xRotation, yRotation, zRotation);
      group.setInitialCoordinate(coordinate);
    }  

    group.addChild(child);
    
    return group;
  }

  /**
   * 与えられたsphereを含む可動グループを生成します。
   * 
   * @param sphere 球体
   * @return 与えられたsphereを含む可動グループ
   */
  public static JoglTransformGroup create(XMLSphere sphere) {
    final int div = sphere.getDivision();
    final float radius = sphere.getRadius();
    final String color = sphere.getColor();
    
    final JoglSphere child = new JoglSphere();
    child.setSize(radius);
    child.setDivision(div);
    child.setColor(color);
    
    final JoglTransformGroup group = new JoglTransformGroup();
    
    final Translation sphereLocation = sphere.getTranslation();
    final Rotation sphereRotation = sphere.getRotation();
    
    if (sphereLocation != null && sphereRotation != null) {
      final float xLocation = sphereLocation.getX();
      final float yLocation = sphereLocation.getY();
      final float zLocatino = sphereLocation.getZ();
      final float xRotation = sphereRotation.getX();
      final float yRotation = sphereRotation.getY();
      final float zRotation = sphereRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocatino);
      coordinate.setRotation(xRotation, yRotation, zRotation);
      group.setInitialCoordinate(coordinate);
    } else if (sphereLocation != null) {
      final float xLocation = sphereLocation.getX();
      final float yLocation = sphereLocation.getY();
      final float zLocation = sphereLocation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      group.setInitialCoordinate(coordinate);
    } else if (sphereRotation != null) {
      final float xRotation = sphereRotation.getX();
      final float yRotation = sphereRotation.getY();
      final float zRotation = sphereRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(xRotation, yRotation, zRotation);
      group.setInitialCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * 与えられたconeを含む可動グループを生成します。
   * @param cone コーン
   * @return 与えられたconeを含む可動グループ
   */
  public static JoglTransformGroup create(XMLCone cone) {
    final float radius = cone.getRadisu();
    final float hight = cone.getHeight();
    final int div = cone.getDivision();
    final String color = cone.getColor();

    final JoglCone child = new JoglCone();
    child.setColor(color);
    child.setSize(radius, hight);
    child.setDivision(div);
    
    final JoglTransformGroup group = new JoglTransformGroup();

    final Translation coneTranslation = cone.getTranslation();
    final Rotation coneRotation = cone.getRotation();
    
    if (coneTranslation != null && coneRotation != null) {
      final float translationX = coneTranslation.getX();
      final float translationY = coneTranslation.getY();
      final float translationZ = coneTranslation.getZ();
      final float rotationX = coneRotation.getX();
      final float rotationY = coneRotation.getY();
      final float rotationZ = coneRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setInitialCoordinate(coordinate);
    } else if (coneTranslation != null) {
      final float locationX = coneTranslation.getX();
      final float locationY = coneTranslation.getY();
      final float locationZ = coneTranslation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(locationX, locationY, locationZ);
      group.setInitialCoordinate(coordinate);
    } else if (coneRotation != null) {
      final float xRotation = coneRotation.getX();
      final float yRotation = coneRotation.getY();
      final float zRotation = coneRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(xRotation, yRotation, zRotation);
      group.setInitialCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * 与えられた三角形ポリゴンを含む可動グループを生成します。
   * 
   * @param polygon 三角形のポリゴン
   * @param coordinateParameters リンクパラメータ
   * @param dhParameters DHパラメータ
   * @return 与えられた三角形ポリゴンを含む可動グループを生成します。
   */
  public static JoglTransformGroup create(XMLTrianglePolygon polygon, List<DHParameter> dhParameters, List<CoordinateParameter> coordinateParameters) {
    final float[][] points = new float[3][3];
    for (int i = 0; i < 3; i++) {
      points[i][0] = polygon.getPointLocationX(i);
      points[i][1] = polygon.getPointLocationY(i);
      points[i][2] = polygon.getPointLocationZ(i);
    }
    final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    
    final JoglTrianglePolygon child = new JoglTrianglePolygon();
    child.setColor(color);
    child.setPoints(points);
    
    final JoglTransformGroup group = new JoglTransformGroup();
    
    if (dhParameters == null && coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Translation polygonTranslation = polygon.getTranslation();
      final Rotation polygonRotation = polygon.getRotation();
      
      if (polygonTranslation != null && polygonRotation != null) {
        final float locationX = polygonTranslation.getX();
        final float locationY = polygonTranslation.getY();
        final float locationZ = polygonTranslation.getZ();
        final float xRotation = polygonRotation.getX();
        final float yRotation = polygonRotation.getY();
        final float zRotation = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setLocation(locationX, locationY, locationZ);
        coordinate.setRotation(xRotation, yRotation, zRotation);
        group.setInitialCoordinate(coordinate);
      } else if (polygonTranslation != null) {
        final float locationX = polygonTranslation.getX();
        final float locationY = polygonTranslation.getY();
        final float locationZ = polygonTranslation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setLocation(locationX, locationY, locationZ);
        group.setInitialCoordinate(coordinate);
      } else if (polygonRotation != null) {
        final float xRotation = polygonRotation.getX();
        final float yRotation = polygonRotation.getY();
        final float zRotation = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setRotation(xRotation, yRotation, zRotation);
        group.setInitialCoordinate(coordinate);
      }
    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
      group.setInitialCoordinate(coordinate);
    } else if (dhParameters != null) {
      final JoglCoordinate coordinate = createDhParameterCoordinate(dhParameters);
      group.setInitialCoordinate(coordinate);
    } else if (coordinateParameters != null) {
      final JoglCoordinate coordinate = createCoordinateParameterCoordinate(coordinateParameters);
      group.setInitialCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * 与えられた四角形ポリゴンを含む可動グループを生成します。
   * 
   * @param polygon 四角形のポリゴン
   * @param coordinateParameters リンクパラメータ
   * @param dhParameters DHパラメータ
   * @return　与えられた四角形ポリゴンを含む可動グループ
   */
  public static JoglTransformGroup create(XMLQuadPolygon polygon, List<DHParameter> dhParameters, List<CoordinateParameter> coordinateParameters) {
    final float[][] points = new float[4][3];
    for (int i = 0; i < 4; i++) {
      points[i][0] = polygon.getPointLocationX(i);
      points[i][1] = polygon.getPointLocationY(i);
      points[i][2] = polygon.getPointLocationZ(i);
    }
    final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    
    final JoglQuadPolygon child = new JoglQuadPolygon();
    child.setColor(color);
    child.setPoints(points);
        
    final JoglTransformGroup group = new JoglTransformGroup();
    
    if (dhParameters == null && coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Translation polygonTranslation = polygon.getTranslation();
      final Rotation polygonRotation = polygon.getRotation();
      if (polygonTranslation != null && polygonRotation != null) {
        final float xLocation = polygonTranslation.getX();
        final float yLocation = polygonTranslation.getY();
        final float zLocation = polygonTranslation.getZ();
        final float xRotation = polygonRotation.getX();
        final float yRotation = polygonRotation.getY();
        final float zRotation = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setLocation(xLocation, yLocation, zLocation);
        coordinate.setRotation(xRotation, yRotation, zRotation);
        group.setInitialCoordinate(coordinate);
      } else if (polygonTranslation != null) {
        final float xLocation = polygonTranslation.getX();
        final float yLocation = polygonTranslation.getY();
        final float zLocation = polygonTranslation.getZ();
        final JoglCoordinate coordinamte = new JoglCoordinate();
        coordinamte.setLocation(xLocation, yLocation, zLocation);
        group.setInitialCoordinate(coordinamte);
      } else if (polygonRotation != null) {
        final float xRotation = polygonRotation.getX();
        final float yRotation = polygonRotation.getY();
        final float zRotation = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setRotation(xRotation, yRotation, zRotation);
        group.setInitialCoordinate(coordinate);
      }
    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
      group.setInitialCoordinate(coordinate);
    } else if (dhParameters != null) {
      final JoglCoordinate coordinate = createDhParameterCoordinate(dhParameters);
      group.setInitialCoordinate(coordinate);
    } else if (coordinateParameters != null) {
      final JoglCoordinate coordinate = createCoordinateParameterCoordinate(coordinateParameters);
      group.setInitialCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * @param parameters リンクパラメータのリスト
   */
  private static JoglCoordinate createCoordinateParameterCoordinate(List<CoordinateParameter> parameters) {
    float x = 0; 
    float y = 0; 
    float z = 0; 
    float xRotation = 0; 
    float yRotation = 0; 
    float zRotation = 0;

    for (final CoordinateParameter parameter : parameters) {
      x = x + (float)parameter.getX();
      y = y + (float)parameter.getY();
      z = z + (float)parameter.getZ();
      xRotation = xRotation + (float)parameter.getAngleX();
      yRotation = yRotation + (float)parameter.getAngleY();
      zRotation = zRotation + (float)parameter.getAngleZ();
    }

    final JoglCoordinate coordinate = new JoglCoordinate();
    coordinate.setLocation(x, y, z);
    coordinate.setRotation(xRotation, yRotation, zRotation);
    return coordinate;
  }

  /**
   * @param parameters DHパラメータのリスト
   */
  private static JoglCoordinate createDhParameterCoordinate(List<DHParameter> parameters) {
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

    final JoglCoordinate coordinate = new JoglCoordinate();
    coordinate.setLocation(a, 0, d);
    coordinate.setRotation(alpha, 0, theta);
      
    return coordinate;
  }
}
