package org.mklab.mikity.view.renderer.jogl;

import java.util.List;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.Vertex;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.util.Vector3;
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
   * グループを生成します。
   * 
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public static JoglObjectGroup create(Group group) {
    return new JoglObjectGroupFactory().create(group);
  }

  /**
   * 与えられたboxを含むグループを生成します。
   * 
   * @param box ボックス
   * @return 与えられたboxを含むグループ
   */
  public static JoglObject create(XMLBox box) {
    final float width = box.getWidth();
    final float height = box.getHeight();
    final float depth = box.getDepth();
    final String color = box.getColor();
    final boolean transparent = box.getTransparent();
    
    final JoglBox child = new JoglBox();
    child.setColor(color);
    child.setSize(width, height, depth);
    child.setTransparent(transparent);

    final Translation translation = box.getTranslation();
    final Rotation rotation = box.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (translation != null && rotation != null) {
      final float translationx = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationx, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    }
    
    group.addChild(child);
    
    return group;
  }

  /**
   * 与えられたcylinderを含むなグループを生成します。
   * 
   * @param cylinder シリンダー
   * @return 与えられたcylinderを含むグループ
   */
  public static JoglObject create(XMLCylinder cylinder) {
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
  
    final Translation translation = cylinder.getTranslation();
    final Rotation rotation = cylinder.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (translation != null && rotation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    }  

    group.addChild(child);
    
    return group;
  }

  /**
   * 与えられたsphereを含むグループを生成します。
   * 
   * @param sphere 球体
   * @return 与えられたsphereを含むグループ
   */
  public static JoglObject create(XMLSphere sphere) {
    final int division = sphere.getDivision();
    final float radius = sphere.getRadius();
    final String color = sphere.getColor();
    final boolean transparent = sphere.getTransparent();
    
    final JoglSphere child = new JoglSphere();
    child.setSize(radius);
    child.setDivision(division);
    child.setColor(color);
    child.setTransparent(transparent);
    
    final Translation translation = sphere.getTranslation();
    final Rotation rotation = sphere.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (translation != null && rotation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * 与えられたconeを含むグループを生成します。
   * @param cone コーン
   * @return 与えられたconeを含むグループ
   */
  public static JoglObject create(XMLCone cone) {
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
        
    final Translation translation = cone.getTranslation();
    final Rotation rotation = cone.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (translation != null && rotation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * 与えられた三角形ポリゴンを含むグループを生成します。
   * 
   * @param polygon 三角形のポリゴン
   * @param coordinateParameters リンクパラメータ
   * @return 与えられた三角形ポリゴンを含むグループを生成します。
   */
  public static JoglObject create(XMLTrianglePolygon polygon, List<CoordinateParameter> coordinateParameters) {
    final float[][] vertices = new float[3][3];
    for (int i = 0; i < 3; i++) {
      final Vertex vertex = polygon.getVertex(i);
      vertices[i][0] = vertex.getX();
      vertices[i][1] = vertex.getY();
      vertices[i][2] = vertex.getZ();
    }
    
    final float[] normalVector = new float[3];
    final Vector3 vector = polygon.getNormalVector();
    normalVector[0] = vector.getX();
    normalVector[1] = vector.getY();
    normalVector[2] = vector.getZ();
    
    //final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
        
    final JoglTrianglePolygon child = new JoglTrianglePolygon();
    child.setColor(color);
    child.setVertices(vertices);
    child.setNormalVector(normalVector);
    child.setTransparent(transparent);
    
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (coordinateParameters == null) {
      final Translation translation = polygon.getTranslation();
      final Rotation rotation = polygon.getRotation();
      
      if (translation != null && rotation != null) {
        final float translationX = translation.getX();
        final float translationY = translation.getY();
        final float translationZ = translation.getZ();
        final float rotationX = rotation.getX();
        final float rotationY = rotation.getY();
        final float rotationZ = rotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (translation != null) {
        final float translationX = translation.getX();
        final float translationY = translation.getY();
        final float translationZ = translation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinate);
      } else if (rotation != null) {
        final float rotationX = rotation.getX();
        final float rotationY = rotation.getY();
        final float rotationZ = rotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
//    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
//      final JoglCoordinate coordinate = new JoglCoordinate();
//      coordinate.setTranslation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
//      group.setBaseCoordinate(coordinate);
    } else {
      final JoglCoordinate coordinate = createCoordinate(coordinateParameters);
      group.setBaseCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * 与えられた四角形ポリゴンを含むグループを生成します。
   * 
   * @param polygon 四角形のポリゴン
   * @param coordinateParameters リンクパラメータ
   * @return　与えられた四角形ポリゴンを含むグループ
   */
  public static JoglObject create(XMLQuadPolygon polygon, List<CoordinateParameter> coordinateParameters) {
    final float[][] vertices = new float[4][3];
    for (int i = 0; i < 4; i++) {
      final Vertex vertex = polygon.getVertex(i);
      vertices[i][0] = vertex.getX();
      vertices[i][1] = vertex.getY();
      vertices[i][2] = vertex.getZ();
    }
    
    final float[] normalVector = new float[3];
    final Vector3 vector = polygon.getNormalVector();
    normalVector[0] = vector.getX();
    normalVector[1] = vector.getY();
    normalVector[2] = vector.getZ();
    
    //final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
    
    final JoglQuadPolygon child = new JoglQuadPolygon();
    child.setColor(color);
    child.setVertices(vertices);
    child.setNormalVector(normalVector);
    child.setTransparent(transparent);
        
    final JoglObjectGroup group = new JoglObjectGroup();
    
    if (coordinateParameters == null) {
      final Translation translation = polygon.getTranslation();
      final Rotation rotation = polygon.getRotation();
      
      if (translation != null && rotation != null) {
        final float translationX = translation.getX();
        final float translationY = translation.getY();
        final float translationZ = translation.getZ();
        final float rotationX = rotation.getX();
        final float rotationY = rotation.getY();
        final float rotationZ = rotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (translation != null) {
        final float translationX = translation.getX();
        final float translationY = translation.getY();
        final float translationZ = translation.getZ();
        final JoglCoordinate coordinamte = new JoglCoordinate();
        coordinamte.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinamte);
      } else if (rotation != null) {
        final float rotationX = rotation.getX();
        final float rotationY = rotation.getY();
        final float rotationZ = rotation.getZ();
        final JoglCoordinate coordinate = new JoglCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
//    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
//      final JoglCoordinate coordinate = new JoglCoordinate();
//      coordinate.setTranslation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
//      group.setBaseCoordinate(coordinate);
    } else {
      final JoglCoordinate coordinate = createCoordinate(coordinateParameters);
      group.setBaseCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * @param parameters リンクパラメータのリスト
   */
  private static JoglCoordinate createCoordinate(List<CoordinateParameter> parameters) {
    float translationX = 0; 
    float translationY = 0; 
    float translationZ = 0; 
    float rotationX = 0; 
    float rotationY = 0; 
    float rotationZ = 0;

    for (final CoordinateParameter parameter : parameters) {
      translationX = translationX + (float)parameter.getTranslationX();
      translationY = translationY + (float)parameter.getTranslationY();
      translationZ = translationZ + (float)parameter.getTranslationZ();
      rotationX = rotationX + (float)parameter.getRotationX();
      rotationY = rotationY + (float)parameter.getRotationY();
      rotationZ = rotationZ + (float)parameter.getRotationZ();
    }

    final JoglCoordinate coordinate = new JoglCoordinate();
    coordinate.setTranslation(translationX, translationY, translationZ);
    coordinate.setRotation(rotationX, rotationY, rotationZ);
    return coordinate;
  }
}
