/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesBox;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesCone;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesCylinder;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesQuadPolygon;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesSphere;
import org.mklab.mikity.android.view.renderer.opengles.primitive.OpenglesTrianglePolygon;
import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.util.Vector3;


/**
 * OpenGLESのプリミティブを生成するファクトリークラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesPrimitiveFactory {
  /**
   * 与えられたboxを含むグループを生成します。
   * 
   * @param box ボックス
   * @return 与えられたboxを含むグループ
   */
  public static OpenglesObject create(BoxModel box) {
//    final float width = box.getWidth();
//    final float height = box.getHeight();
//    final float depth = box.getDepth();
//    final ColorModel color = box.getColor();
//    final boolean transparent = box.isTransparent();

    final OpenglesBox child = new OpenglesBox(box);
//    child.setColor(color);
//    child.setSize(width, height, depth);
//    child.setTransparent(transparent);

    final TranslationModel translation = box.getTranslation();
    final RotationModel rotation = box.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられたcylinderを含むグループを生成します。
   * 
   * @param cylinder シリンダー
   * @return 与えられたcylinderを含むグループ
   */
  public static OpenglesObject create(CylinderModel cylinder) {
//    final int division = cylinder.getDivision();
//    final float radius = cylinder.getRadius();
//    final float hight = cylinder.getHeight();
//    final ColorModel color = cylinder.getColor();
//    final boolean transparent = cylinder.isTransparent();

    final OpenglesCylinder child = new OpenglesCylinder(cylinder);
//    child.setSize(radius, hight);
//    child.setDivision(division);
//    child.setColor(color);
//    child.setTransparent(transparent);

    final TranslationModel translation = cylinder.getTranslation();
    final RotationModel rotation = cylinder.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられたsphereを含むグループを生成します。
   * 
   * @param sphere 球体
   * @return 与えられたsphereを含むグループ
   */
  public static OpenglesObject create(SphereModel sphere) {
//    final int division = sphere.getDivision();
//    final float radius = sphere.getRadius();
//    final ColorModel color = sphere.getColor();
//    final boolean transparent = sphere.isTransparent();

    final OpenglesSphere child = new OpenglesSphere(sphere);
//    child.setSize(radius);
//    child.setDivision(division);
//    child.setColor(color);
//    child.setTransparent(transparent);

    final TranslationModel translation = sphere.getTranslation();
    final RotationModel rotation = sphere.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられたconeを含むグループを生成します。
   * 
   * @param cone コーン
   * @return 与えられたconeを含むグループ
   */
  public static OpenglesObject create(ConeModel cone) {
//    final float radius = cone.getRadisu();
//    final float hight = cone.getHeight();
//    final int division = cone.getDivision();
//    final ColorModel color = cone.getColor();
//    final boolean transparent = cone.isTransparent();

    final OpenglesCone child = new OpenglesCone(cone);
//    child.setColor(color);
//    child.setSize(radius, hight);
//    child.setDivision(division);
//    child.setTransparent(transparent);

    final TranslationModel translation = cone.getTranslation();
    final RotationModel rotation = cone.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられた三角形ポリゴンを含むグループを生成します。
   * 
   * @param polygon 三角形のポリゴン
   * @return 与えられた三角形ポリゴンを含むグループを生成します。
   */
  public static OpenglesObject create(TrianglePolygonModel polygon) {
//    final float[][] vertices = new float[3][3];
//    for (int i = 0; i < 3; i++) {
//      final VertexModel vertex = polygon.getVertex(i);
//      vertices[i][0] = vertex.getX();
//      vertices[i][1] = vertex.getY();
//      vertices[i][2] = vertex.getZ();
//    }
//
//    final float[] normalVector = new float[3];
//    final Vector3 vector = polygon.getNormalVector();
//    normalVector[0] = vector.getX();
//    normalVector[1] = vector.getY();
//    normalVector[2] = vector.getZ();
//
//    final ColorModel color = polygon.getColor();
//    final boolean transparent = polygon.isTransparent();

    final OpenglesTrianglePolygon child = new OpenglesTrianglePolygon(polygon);
//    child.setColor(color);
//    child.setVertices(vertices);
//    child.setNormalVector(normalVector);
//    child.setTransparent(transparent);

    final TranslationModel translation = polygon.getTranslation();
    final RotationModel rotation = polygon.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられた四角形ポリゴンを含むグループを生成します。
   * 
   * @param polygon 四角形のポリゴン
   * @return　与えられた四角形ポリゴンを含むグループ
   */
  public static OpenglesObject create(QuadPolygonModel polygon) {
//    final float[][] vertices = new float[4][3];
//    for (int i = 0; i < 4; i++) {
//      final VertexModel vertex = polygon.getVertex(i);
//      vertices[i][0] = vertex.getX();
//      vertices[i][1] = vertex.getY();
//      vertices[i][2] = vertex.getZ();
//    }
//
//    final float[] normalVector = new float[3];
//    final Vector3 vector = polygon.getNormalVector();
//    normalVector[0] = vector.getX();
//    normalVector[1] = vector.getY();
//    normalVector[2] = vector.getZ();
//
//    final ColorModel color = polygon.getColor();
//    final boolean transparent = polygon.isTransparent();

    final OpenglesQuadPolygon child = new OpenglesQuadPolygon(polygon);
//    child.setColor(color);
//    child.setVertices(vertices);
//    child.setNormalVector(normalVector);
//    child.setTransparent(transparent);

    final TranslationModel translation = polygon.getTranslation();
    final RotationModel rotation = polygon.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));

    return group;
  }
  

  /**
   * 基準座標を生成します。
   * 
   * @param translation 並進変換
   * @param rotation 回転変換
   * @return 基準座標系
   */
  private static Coordinate createCoordinate(final TranslationModel translation, final RotationModel rotation) {
    if (translation != null && rotation != null) {
      return new Coordinate(translation, rotation);
    } 
    
    if (translation != null) {
      return new Coordinate(translation);
    } 
    
    if (rotation != null) {
      return new Coordinate(rotation);
    }
    
    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }

}
