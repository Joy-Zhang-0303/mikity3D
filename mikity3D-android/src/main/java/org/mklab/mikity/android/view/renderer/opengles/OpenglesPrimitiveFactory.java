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
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
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
    final float width = box.getWidth();
    final float height = box.getHeight();
    final float depth = box.getDepth();
    final String color = box.getColor();
    final boolean transparent = box.getTransparent();

    final OpenglesBox child = new OpenglesBox();
    child.setColor(color);
    child.setSize(width, height, depth);
    child.setTransparent(transparent);

    final TranslationModel translation = box.getTranslation();
    final RotationModel rotation = box.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createBaseCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられたcylinderを含むグループを生成します。
   * 
   * @param cylinder シリンダー
   * @return 与えられたcylinderを含むグループ
   */
  public static OpenglesObject create(CylinderModel cylinder) {
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

    final TranslationModel translation = cylinder.getTranslation();
    final RotationModel rotation = cylinder.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createBaseCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられたsphereを含むグループを生成します。
   * 
   * @param sphere 球体
   * @return 与えられたsphereを含むグループ
   */
  public static OpenglesObject create(SphereModel sphere) {
    final int division = sphere.getDivision();
    final float radius = sphere.getRadius();
    final String color = sphere.getColor();
    final boolean transparent = sphere.getTransparent();

    final OpenglesSphere child = new OpenglesSphere();
    child.setSize(radius);
    child.setDivision(division);
    child.setColor(color);
    child.setTransparent(transparent);

    final TranslationModel translation = sphere.getTranslation();
    final RotationModel rotation = sphere.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createBaseCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられたconeを含むグループを生成します。
   * 
   * @param cone コーン
   * @return 与えられたconeを含むグループ
   */
  public static OpenglesObject create(ConeModel cone) {
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

    final TranslationModel translation = cone.getTranslation();
    final RotationModel rotation = cone.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createBaseCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられた三角形ポリゴンを含むグループを生成します。
   * 
   * @param polygon 三角形のポリゴン
   * @return 与えられた三角形ポリゴンを含むグループを生成します。
   */
  public static OpenglesObject create(TrianglePolygonModel polygon) {
    final float[][] vertices = new float[3][3];
    for (int i = 0; i < 3; i++) {
      final VertexModel vertex = polygon.getVertex(i);
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

    final TranslationModel translation = polygon.getTranslation();
    final RotationModel rotation = polygon.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createBaseCoordinate(translation, rotation));

    return group;
  }

  /**
   * 与えられた四角形ポリゴンを含むグループを生成します。
   * 
   * @param polygon 四角形のポリゴン
   * @return　与えられた四角形ポリゴンを含むグループ
   */
  public static OpenglesObject create(QuadPolygonModel polygon) {
    final float[][] vertices = new float[4][3];
    for (int i = 0; i < 4; i++) {
      final VertexModel vertex = polygon.getVertex(i);
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

    final TranslationModel translation = polygon.getTranslation();
    final RotationModel rotation = polygon.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createBaseCoordinate(translation, rotation));

    return group;
  }
  

  /**
   * 基準座標を生成します。
   * 
   * @param translation 並進変換
   * @param rotation 回転変換
   * @return 基準座標系
   */
  private static OpenglesCoordinate createBaseCoordinate(final TranslationModel translation, final RotationModel rotation) {
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
      return coordinate;
    } 
    
    if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      return coordinate;
    } 
    
    if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      return coordinate;
    }
    
    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }

}
