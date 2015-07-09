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
  public static JoglObjectGroup create(Group group) {
    return new JoglObjectGroupFactory().create(group);
  }

  /**
   * 与えられたboxを含む可動グループを生成します。
   * 
   * @param box ボックス
   * @return 与えられたboxを含む可動グループ
   */
  public static JoglObjectGroup create(XMLBox box) {
    final float width = box.getWidth();
    final float height = box.getHeight();
    final float depth = box.getDepth();
    final String color = box.getColor();
    final boolean transparent = box.getTransparent();
    
    final JoglBox child = new JoglBox();
    child.setColor(color);
    child.setSize(width, height, depth);
    child.setTransparent(transparent);

    final JoglObjectGroup group = new JoglObjectGroup();

    final Translation boxTranslation = box.getTranslation();
    final Rotation boxRotation = box.getRotation();
    
    if (boxTranslation != null && boxRotation != null) {
      final float translationx = boxTranslation.getX();
      final float translationY = boxTranslation.getY();
      final float translationZ = boxTranslation.getZ();
      final float rotationX = boxRotation.getX();
      final float rotationY = boxRotation.getY();
      final float rotationZ = boxRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationx, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (boxTranslation != null) {
      final float translationX = boxTranslation.getX();
      final float translationY = boxTranslation.getY();
      final float translationZ = boxTranslation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (boxRotation != null) {
      final float rotationX = boxRotation.getX();
      final float rotationY = boxRotation.getY();
      final float rotationZ = boxRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
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
  public static JoglObjectGroup create(XMLCylinder cylinder) {
    final int division = cylinder.getDivision();
    final float radius = cylinder.getRadius();
    final float hight = cylinder.getHeight();
    final String color = cylinder.getColor();
    final boolean transparent = cylinder.getTransparent();
        
    final JoglCylinder child = new JoglCylinder();
    child.setSize(radius, hight);
    child.setDivision(division);
    child.setColor(color);
    child.setTransparent(transparent);
    
    final JoglObjectGroup group = new JoglObjectGroup();
  
    final Translation cylinderLocation = cylinder.getTranslation();
    final Rotation cylinderRotation = cylinder.getRotation();
    
    if (cylinderLocation != null && cylinderRotation != null) {
      final float translationX = cylinderLocation.getX();
      final float translationY = cylinderLocation.getY();
      final float translationZ = cylinderLocation.getZ();
      final float rotationX = cylinderRotation.getX();
      final float rotationY = cylinderRotation.getY();
      final float rotationZ = cylinderRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (cylinderLocation != null) {
      final float translationX = cylinderLocation.getX();
      final float translationY = cylinderLocation.getY();
      final float translationZ = cylinderLocation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (cylinderRotation != null) {
      final float rotationX = cylinderRotation.getX();
      final float rotationY = cylinderRotation.getY();
      final float rotationZ = cylinderRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
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
  public static JoglObjectGroup create(XMLSphere sphere) {
    final int division = sphere.getDivision();
    final float radius = sphere.getRadius();
    final String color = sphere.getColor();
    final boolean transparent = sphere.getTransparent();
    
    final JoglSphere child = new JoglSphere();
    child.setSize(radius);
    child.setDivision(division);
    child.setColor(color);
    child.setTransparent(transparent);
    
    final JoglObjectGroup group = new JoglObjectGroup();
    
    final Translation sphereLocation = sphere.getTranslation();
    final Rotation sphereRotation = sphere.getRotation();
    
    if (sphereLocation != null && sphereRotation != null) {
      final float translationX = sphereLocation.getX();
      final float translationY = sphereLocation.getY();
      final float translationZ = sphereLocation.getZ();
      final float rotationX = sphereRotation.getX();
      final float rotationY = sphereRotation.getY();
      final float rotationZ = sphereRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (sphereLocation != null) {
      final float translationX = sphereLocation.getX();
      final float translationY = sphereLocation.getY();
      final float translationZ = sphereLocation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (sphereRotation != null) {
      final float rotationX = sphereRotation.getX();
      final float rotationY = sphereRotation.getY();
      final float rotationZ = sphereRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * 与えられたconeを含む可動グループを生成します。
   * @param cone コーン
   * @return 与えられたconeを含む可動グループ
   */
  public static JoglObjectGroup create(XMLCone cone) {
    final float radius = cone.getRadisu();
    final float hight = cone.getHeight();
    final int division = cone.getDivision();
    final String color = cone.getColor();
    final boolean transparent = cone.getTransparent();

    final JoglCone child = new JoglCone();
    child.setColor(color);
    child.setSize(radius, hight);
    child.setDivision(division);
    child.setTransparent(transparent);
    
    final JoglObjectGroup group = new JoglObjectGroup();

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
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (coneTranslation != null) {
      final float translationX = coneTranslation.getX();
      final float translationY = coneTranslation.getY();
      final float translationZ = coneTranslation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (coneRotation != null) {
      final float rotationX = coneRotation.getX();
      final float rotationY = coneRotation.getY();
      final float rotationZ = coneRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
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
  public static JoglObjectGroup create(XMLTrianglePolygon polygon, List<DHParameter> dhParameters, List<CoordinateParameter> coordinateParameters) {
    final float[][] points = new float[3][3];
    for (int i = 0; i < 3; i++) {
      points[i][0] = polygon.getVertexX(i);
      points[i][1] = polygon.getVertexY(i);
      points[i][2] = polygon.getVertexZ(i);
    }
    final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
    
    final JoglTrianglePolygon child = new JoglTrianglePolygon();
    child.setColor(color);
    child.setPoints(points);
    child.setTransparent(transparent);
    
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (dhParameters == null && coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Translation polygonTranslation = polygon.getTranslation();
      final Rotation polygonRotation = polygon.getRotation();
      
      if (polygonTranslation != null && polygonRotation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (polygonTranslation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinate);
      } else if (polygonRotation != null) {
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
      group.setBaseCoordinate(coordinate);
    } else if (dhParameters != null) {
      final JoglCoordinate coordinate = createDhParameterCoordinate(dhParameters);
      group.setBaseCoordinate(coordinate);
    } else if (coordinateParameters != null) {
      final JoglCoordinate coordinate = createCoordinateParameterCoordinate(coordinateParameters);
      group.setBaseCoordinate(coordinate);
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
  public static JoglObjectGroup create(XMLQuadPolygon polygon, List<DHParameter> dhParameters, List<CoordinateParameter> coordinateParameters) {
    final float[][] points = new float[4][3];
    for (int i = 0; i < 4; i++) {
      points[i][0] = polygon.getVertexX(i);
      points[i][1] = polygon.getVertexY(i);
      points[i][2] = polygon.getVertexZ(i);
    }
    final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
    
    final JoglQuadPolygon child = new JoglQuadPolygon();
    child.setColor(color);
    child.setPoints(points);
    child.setTransparent(transparent);
        
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (dhParameters == null && coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Translation polygonTranslation = polygon.getTranslation();
      final Rotation polygonRotation = polygon.getRotation();
      if (polygonTranslation != null && polygonRotation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (polygonTranslation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final JoglCoordinate coordinamte = new JoglCoordinate();
        coordinamte.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinamte);
      } else if (polygonRotation != null) {
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
      group.setBaseCoordinate(coordinate);
    } else if (dhParameters != null) {
      final JoglCoordinate coordinate = createDhParameterCoordinate(dhParameters);
      group.setBaseCoordinate(coordinate);
    } else if (coordinateParameters != null) {
      final JoglCoordinate coordinate = createCoordinateParameterCoordinate(coordinateParameters);
      group.setBaseCoordinate(coordinate);
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
    float rotationX = 0; 
    float rotationY = 0; 
    float rotationZ = 0;

    for (final CoordinateParameter parameter : parameters) {
      x = x + (float)parameter.getTranslationX();
      y = y + (float)parameter.getTranslationY();
      z = z + (float)parameter.getTranslationZ();
      rotationX = rotationX + (float)parameter.getRotationX();
      rotationY = rotationY + (float)parameter.getRotationY();
      rotationZ = rotationZ + (float)parameter.getRotationZ();
    }

    final JoglCoordinate coordinate = new JoglCoordinate();
    coordinate.setTranslation(x, y, z);
    coordinate.setRotation(rotationX, rotationY, rotationZ);
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
    coordinate.setTranslation(a, 0, d);
    coordinate.setRotation(alpha, 0, theta);
      
    return coordinate;
  }
}
