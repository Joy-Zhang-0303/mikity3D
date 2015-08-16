/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.graphic.BoxObject;
import org.mklab.mikity.model.graphic.ConeObject;
import org.mklab.mikity.model.graphic.CylinderObject;
import org.mklab.mikity.model.graphic.QuadPolygonObject;
import org.mklab.mikity.model.graphic.SphereObject;
import org.mklab.mikity.model.graphic.TrianglePolygonObject;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;


/**
 * OpenGLESのプリミティブを生成するファクトリークラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesPrimitiveFactory {
  /**
   * 与えられたプリミティブを含むグループを生成します。
   * 
   * @param primitive プリミティブ
   * @return 与えられたプリミティブを含むグループ
   */
  public static OpenglesObject create(PrimitiveModel primitive) {
    final OpenglesPrimitive child;
    
    if (primitive instanceof BoxModel) {
      child = new OpenglesPrimitive(new BoxObject((BoxModel)primitive));
    } else if (primitive instanceof CylinderModel) {
      child = new OpenglesPrimitive(new CylinderObject((CylinderModel)primitive));
    } else if (primitive instanceof ConeModel) {
      child = new OpenglesPrimitive(new ConeObject((ConeModel)primitive));
    } else if (primitive instanceof SphereModel) {
      child = new OpenglesPrimitive(new SphereObject((SphereModel)primitive));
    } else if (primitive instanceof TrianglePolygonModel) {
      child = new OpenglesPrimitive(new TrianglePolygonObject((TrianglePolygonModel)primitive));
    } else if (primitive instanceof QuadPolygonModel) {
      child = new OpenglesPrimitive(new QuadPolygonObject((QuadPolygonModel)primitive));
    } else {
      throw new IllegalArgumentException(primitive.getClass().toString());
    }
   
    final TranslationModel translation = primitive.getTranslation();
    final RotationModel rotation = primitive.getRotation();

    if (translation == null && rotation == null) {
      return child;
    }

    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));
    
    return group;
  }
  
//  /**
//   * 与えられたboxを含むグループを生成します。
//   * 
//   * @param box ボックス
//   * @return 与えられたboxを含むグループ
//   */
//  public static OpenglesObject create(BoxModel box) {
//    final OpenglesPrimitive child = new OpenglesPrimitive(new BoxObject(box));
//
//    final TranslationModel translation = box.getTranslation();
//    final RotationModel rotation = box.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//
//    return group;
//  }
//
//  /**
//   * 与えられたcylinderを含むグループを生成します。
//   * 
//   * @param cylinder シリンダー
//   * @return 与えられたcylinderを含むグループ
//   */
//  public static OpenglesObject create(CylinderModel cylinder) {
//    final OpenglesPrimitive child = new OpenglesPrimitive(new CylinderObject(cylinder));
//
//    final TranslationModel translation = cylinder.getTranslation();
//    final RotationModel rotation = cylinder.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//
//    return group;
//  }
//
//  /**
//   * 与えられたsphereを含むグループを生成します。
//   * 
//   * @param sphere 球体
//   * @return 与えられたsphereを含むグループ
//   */
//  public static OpenglesObject create(SphereModel sphere) {
//    final OpenglesPrimitive child = new OpenglesPrimitive(new SphereObject(sphere));
//
//    final TranslationModel translation = sphere.getTranslation();
//    final RotationModel rotation = sphere.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//
//    return group;
//  }
//
//  /**
//   * 与えられたconeを含むグループを生成します。
//   * 
//   * @param cone コーン
//   * @return 与えられたconeを含むグループ
//   */
//  public static OpenglesObject create(ConeModel cone) {
//    final OpenglesPrimitive child = new OpenglesPrimitive(new ConeObject(cone));
//
//    final TranslationModel translation = cone.getTranslation();
//    final RotationModel rotation = cone.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//
//    return group;
//  }
//
//  /**
//   * 与えられた三角形ポリゴンを含むグループを生成します。
//   * 
//   * @param polygon 三角形のポリゴン
//   * @return 与えられた三角形ポリゴンを含むグループを生成します。
//   */
//  public static OpenglesObject create(TrianglePolygonModel polygon) {
//    final OpenglesPrimitive child = new OpenglesPrimitive(new TrianglePolygonObject(polygon));
//
//    final TranslationModel translation = polygon.getTranslation();
//    final RotationModel rotation = polygon.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//
//    return group;
//  }
//
//  /**
//   * 与えられた四角形ポリゴンを含むグループを生成します。
//   * 
//   * @param polygon 四角形のポリゴン
//   * @return　与えられた四角形ポリゴンを含むグループ
//   */
//  public static OpenglesObject create(QuadPolygonModel polygon) {
//    final OpenglesPrimitive child = new OpenglesPrimitive(new QuadPolygonObject(polygon));
//
//    final TranslationModel translation = polygon.getTranslation();
//    final RotationModel rotation = polygon.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//
//    return group;
//  }
  

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
