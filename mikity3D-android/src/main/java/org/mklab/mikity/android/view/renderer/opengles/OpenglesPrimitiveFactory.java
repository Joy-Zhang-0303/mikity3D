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



/**
 * OpenGLESのプリミティブを生成するファクトリークラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesPrimitiveFactory {

//  /**
//   * グループを生成します。
//   * 
//   * @param group オブジェクトのグループ
//   * @return グループ
//   */
//  public static OpenglesObjectGroup create(Group group) {
//    return new OpenglesObjectGroupFactory().create(group);
//  }

  /**
   * 与えられたboxを含むグループを生成します。
   * 
   * @param box ボックス
   * @return 与えられたboxを含むグループ
   */
  public static OpenglesObject create(XMLBox box) {
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

    final Translation tanslation = box.getTranslation();
    final Rotation rotation = box.getRotation();
    
    if (tanslation != null && rotation != null) {
      final float translationX = tanslation.getX();
      final float translationY = tanslation.getY();
      final float translationZ = tanslation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (tanslation != null) {
      final float translationX = tanslation.getX();
      final float translationY = tanslation.getY();
      final float translationZ = tanslation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
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
  public static OpenglesObject create(XMLCylinder cylinder) {
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
  
    final Translation translation = cylinder.getTranslation();
    final Rotation rotation = cylinder.getRotation();
    
    if (translation != null && rotation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
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
  public static OpenglesObject create(XMLSphere sphere) {
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
    
    final Translation translation = sphere.getTranslation();
    final Rotation rotation = sphere.getRotation();
    
    if (translation != null && rotation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float zLocatino = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, zLocatino);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
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
  public static OpenglesObject create(XMLCone cone) {
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

    final Translation translation = cone.getTranslation();
    final Rotation rotation = cone.getRotation();
    
    if (translation != null && rotation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      group.setBaseCoordinate(coordinate);
    } else if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      group.setBaseCoordinate(coordinate);
    } else if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
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
  public static OpenglesObject create(XMLTrianglePolygon polygon, List<CoordinateParameter> coordinateParameters) {
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
    
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
    
    final OpenglesTrianglePolygon child = new OpenglesTrianglePolygon();
    child.setColor(color);
    child.setVertices(vertices);
    child.setNormalVector(normalVector);
    child.setTransparent(transparent);
    
    final OpenglesObjectGroup group = new OpenglesObjectGroup();
    
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
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (translation != null) {
        final float translationX = translation.getX();
        final float translationY = translation.getY();
        final float translationZ = translation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinate);
      } else if (rotation != null) {
        final float rotationX = rotation.getX();
        final float rotationY = rotation.getY();
        final float rotationZ = rotation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
    } else {
      final OpenglesCoordinate baseCoordinate = createBaseCoordinate(coordinateParameters);
      group.setBaseCoordinate(baseCoordinate);
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
  public static OpenglesObject create(XMLQuadPolygon polygon, List<CoordinateParameter> coordinateParameters) {
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
    
    final String color = polygon.getColor();
    final boolean transparent = polygon.getTransparent();
    
    final OpenglesQuadPolygon child = new OpenglesQuadPolygon();
    child.setColor(color);
    child.setVertices(vertices);
    child.setNormalVector(normalVector);
    child.setTransparent(transparent);
        
    final OpenglesObjectGroup group = new OpenglesObjectGroup();
    
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
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setTranslation(translationX, translationY, translationZ);
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      } else if (translation != null) {
        final float translationX = translation.getX();
        final float translationY = translation.getY();
        final float translationZ = translation.getZ();
        final OpenglesCoordinate coordinamte = new OpenglesCoordinate();
        coordinamte.setTranslation(translationX, translationY, translationZ);
        group.setBaseCoordinate(coordinamte);
      } else if (rotation != null) {
        final float rotationX = rotation.getX();
        final float rotationY = rotation.getY();
        final float rotationZ = rotation.getZ();
        final OpenglesCoordinate coordinate = new OpenglesCoordinate();
        coordinate.setRotation(rotationX, rotationY, rotationZ);
        group.setBaseCoordinate(coordinate);
      }
    } else {
      final OpenglesCoordinate baseCoordinate = createBaseCoordinate(coordinateParameters);
      group.setBaseCoordinate(baseCoordinate);
    }

    group.addChild(child);

    return group;
  }

  /**
   * @param parameters リンクパラメータのリスト
   */
  private static OpenglesCoordinate createBaseCoordinate(List<CoordinateParameter> parameters) {
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


