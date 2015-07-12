/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import java.util.List;

import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesBox;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesCone;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesCylinder;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesQuadPolygon;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesSphere;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesTrianglePolygon;
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
import org.mklab.mikity.util.Matrix4;
import org.mklab.mikity.util.Vector3;



/**
 * OpenGLESのプリミティブを生成するファクトリークラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesPrimitiveFactory {

  /**
   * グループを生成します。
   * 
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public static OpenglesObjectGroup create(Group group) {
    return new OpenglesObjectGroupFactory().create(group);
  }

  /**
   * 与えられたboxを含むグループを生成します。
   * 
   * @param box ボックス
   * @return 与えられたboxを含むグループ
   */
  public static OpenglesObjectGroup create(XMLBox box) {
    final float width = box.getWidth();
    final float height = box.getHeight();
    final float depth = box.getDepth();
    final String color = box.getColor();
    final boolean transparent = box.getTransparent();
    
    final OpenglesBox child = new OpenglesBox();
    child.setColor(color);
    child.setSize(width, height, depth);
    child.setTransparent(transparent);

    final OpenglesObjectGroup group = new OpenglesObjectGroup();

    final Translation boxTranslation = box.getTranslation();
    final Rotation boxRotation = box.getRotation();
    
    if (boxTranslation != null && boxRotation != null) {
      final float translationX = boxTranslation.getX();
      final float translationY = boxTranslation.getY();
      final float translationZ = boxTranslation.getZ();
      final float rotationX = boxRotation.getX();
      final float rotationY = boxRotation.getY();
      final float rotationZ = boxRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (boxTranslation != null) {
      final float translationX = boxTranslation.getX();
      final float translationY = boxTranslation.getY();
      final float translationZ = boxTranslation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (boxRotation != null) {
      final float rotationX = boxRotation.getX();
      final float rotationY = boxRotation.getY();
      final float rotationZ = boxRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    }
    
    group.addChild(child);
    
    return group;
  }

  /**
   * 与えられたcylinderを含むグループを生成します。
   * 
   * @param cylinder シリンダー
   * @return 与えられたcylinderを含むグループ
   */
  public static OpenglesObjectGroup create(XMLCylinder cylinder) {
    final int division = cylinder.getDivision();
    final float radius = cylinder.getRadius();
    final float hight = cylinder.getHeight();
    final String color = cylinder.getColor();
    final boolean transparent = cylinder.getTransparent();

    final OpenglesCylinder child = new OpenglesCylinder();
    child.setSize(radius, hight);
    child.setDivision(division);
    child.setColor(color);
    child.setTransparent(transparent);
    
    final OpenglesObjectGroup group = new OpenglesObjectGroup();
  
    final Translation cylinderTranslation = cylinder.getTranslation();
    final Rotation cylinderRotation = cylinder.getRotation();
    
    if (cylinderTranslation != null && cylinderRotation != null) {
      final float translationX = cylinderTranslation.getX();
      final float translationY = cylinderTranslation.getY();
      final float translationZ = cylinderTranslation.getZ();
      final float rotationX = cylinderRotation.getX();
      final float rotationY = cylinderRotation.getY();
      final float rotationZ = cylinderRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (cylinderTranslation != null) {
      final float translationX = cylinderTranslation.getX();
      final float translationY = cylinderTranslation.getY();
      final float translationZ = cylinderTranslation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (cylinderRotation != null) {
      final float rotationX = cylinderRotation.getX();
      final float rotationY = cylinderRotation.getY();
      final float rotationZ = cylinderRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
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
  public static OpenglesObjectGroup create(XMLSphere sphere) {
    final int division = sphere.getDivision();
    final float radius = sphere.getRadius();
    final String color = sphere.getColor();
    final boolean transparent = sphere.getTransparent();
    
    final OpenglesSphere child = new OpenglesSphere();
    child.setSize(radius);
    child.setDivision(division);
    child.setColor(color);
    child.setTransparent(transparent);
    
    final OpenglesObjectGroup group = new OpenglesObjectGroup();
    
    final Translation sphereTranslation = sphere.getTranslation();
    final Rotation sphereRotation = sphere.getRotation();
    
    if (sphereTranslation != null && sphereRotation != null) {
      final float translationX = sphereTranslation.getX();
      final float translationY = sphereTranslation.getY();
      final float zLocatino = sphereTranslation.getZ();
      final float rotationX = sphereRotation.getX();
      final float rotationY = sphereRotation.getY();
      final float rotationZ = sphereRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, zLocatino);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (sphereTranslation != null) {
      final float translationX = sphereTranslation.getX();
      final float translationY = sphereTranslation.getY();
      final float translationZ = sphereTranslation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (sphereRotation != null) {
      final float rotationX = sphereRotation.getX();
      final float rotationY = sphereRotation.getY();
      final float rotationZ = sphereRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
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
  public static OpenglesObjectGroup create(XMLCone cone) {
    final float radius = cone.getRadisu();
    final float hight = cone.getHeight();
    final int division = cone.getDivision();
    final String color = cone.getColor();
    final boolean transparent = cone.getTransparent();

    final OpenglesCone child = new OpenglesCone();
    child.setColor(color);
    child.setSize(radius, hight);
    child.setDivision(division);
    child.setTransparent(transparent);
    
    final OpenglesObjectGroup group = new OpenglesObjectGroup();

    final Translation coneTranslation = cone.getTranslation();
    final Rotation coneRotation = cone.getRotation();
    
    if (coneTranslation != null && coneRotation != null) {
      final float translationX = coneTranslation.getX();
      final float translationY = coneTranslation.getY();
      final float translationZ = coneTranslation.getZ();
      final float rotationX = coneRotation.getX();
      final float rotationY = coneRotation.getY();
      final float rotationZ = coneRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (coneTranslation != null) {
      final float translationX = coneTranslation.getX();
      final float translationY = coneTranslation.getY();
      final float translationZ = coneTranslation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (coneRotation != null) {
      final float rotationX = coneRotation.getX();
      final float rotationY = coneRotation.getY();
      final float rotationZ = coneRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
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
  public static OpenglesObjectGroup create(XMLTrianglePolygon polygon, List<CoordinateParameter> coordinateParameters) {
    final float[][] points = new float[3][3];
    for (int i = 0; i < 3; i++) {
      final Vertex vertex = polygon.getVertex(i);
      points[i][0] = vertex.getX();
      points[i][1] = vertex.getY();
      points[i][2] = vertex.getZ();
    }
    
    final float[] normalVector = new float[3];
    final Vector3 vector = polygon.getNormalVector();
    normalVector[0] = vector.getX();
    normalVector[1] = vector.getY();
    normalVector[2] = vector.getZ();
    
    final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
    
    final OpenglesTrianglePolygon child = new OpenglesTrianglePolygon();
    child.setColor(color);
    child.setVertices(points);
    child.setNormalVector(normalVector);
    child.setTransparent(transparent);
    
    final OpenglesObjectGroup group = new OpenglesObjectGroup();
    
    if (coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Translation polygonTranslation = polygon.getTranslation();
      final Rotation polygonRotation = polygon.getRotation();
      
      if (polygonTranslation != null && polygonRotation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (polygonTranslation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinate);
      } else if (polygonRotation != null) {
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
      group.setBaseCoordinate(coordinate);
    } else if (coordinateParameters != null) {
      final OpenglesCoordinate coordinate = createCoordinateParameterCoordinate(coordinateParameters);
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
  public static OpenglesObjectGroup create(XMLQuadPolygon polygon, List<CoordinateParameter> coordinateParameters) {
    final float[][] points = new float[4][3];
    for (int i = 0; i < 4; i++) {
      final Vertex vertex = polygon.getVertex(i);
      points[i][0] = vertex.getX();
      points[i][1] = vertex.getY();
      points[i][2] = vertex.getZ();
    }
    
    final float[] normalVector = new float[3];
    final Vector3 vector = polygon.getNormalVector();
    normalVector[0] = vector.getX();
    normalVector[1] = vector.getY();
    normalVector[2] = vector.getZ();
    
    final Matrix4 matrix = polygon.getMatrix();
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
    
    final OpenglesQuadPolygon child = new OpenglesQuadPolygon();
    child.setColor(color);
    child.setVertices(points);
    child.setNormalVector(normalVector);
    child.setTransparent(transparent);
        
    final OpenglesObjectGroup group = new OpenglesObjectGroup();
    
    if (coordinateParameters == null && matrix.getElement(0, 3) == 0.0f && matrix.getElement(1, 3) == 0.0f && matrix.getElement(2, 3) == 0.0f) {
      final Translation polygonTranslation = polygon.getTranslation();
      final Rotation polygonRotation = polygon.getRotation();
      if (polygonTranslation != null && polygonRotation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (polygonTranslation != null) {
        final float translationX = polygonTranslation.getX();
        final float translationY = polygonTranslation.getY();
        final float translationZ = polygonTranslation.getZ();
        final OpenglesCoordinate coordinamte = new OpenglesCoordinate();
        coordinamte.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinamte);
      } else if (polygonRotation != null) {
        final float rotationX = polygonRotation.getX();
        final float rotationY = polygonRotation.getY();
        final float rotationZ = polygonRotation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
    } else if (matrix.getElement(0, 3) != 0.0f || matrix.getElement(1, 3) != 0.0f || matrix.getElement(2, 3) != 0.0f) {
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(matrix.getElement(0, 3), matrix.getElement(1, 3), matrix.getElement(2, 3));
      group.setBaseCoordinate(coordinate);
    } else if (coordinateParameters != null) {
      final OpenglesCoordinate coordinate = createCoordinateParameterCoordinate(coordinateParameters);
      group.setBaseCoordinate(coordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * @param parameters リンクパラメータのリスト
   */
  private static OpenglesCoordinate createCoordinateParameterCoordinate(List<CoordinateParameter> parameters) {
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

    final OpenglesCoordinate coordinate = new OpenglesCoordinate();
    coordinate.setTranslation(translationX, translationY, translationZ);
    coordinate.setRotation(rotationX, rotationY, rotationZ);
    return coordinate;
  }
}


