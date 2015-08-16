package org.mklab.mikity.view.renderer.jogl;

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
 * JOGLのプリミティブを生成するファクトリークラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglPrimitiveFactory {
  /**
   * 与えられたプリミティブを含むグループを生成します。
   * 
   * @param primitive プリミティブ
   * @return 与えられたプリミティブを含むグループ
   */
  public static JoglObject create(PrimitiveModel primitive) {
    final JoglPrimitive child;
    
    if (primitive instanceof BoxModel) {
      child = new JoglPrimitive(new BoxObject((BoxModel)primitive));
    } else if (primitive instanceof CylinderModel) {
      child = new JoglPrimitive(new CylinderObject((CylinderModel)primitive));
    } else if (primitive instanceof ConeModel) {
      child = new JoglPrimitive(new ConeObject((ConeModel)primitive));
    } else if (primitive instanceof SphereModel) {
      child = new JoglPrimitive(new SphereObject((SphereModel)primitive));
    } else if (primitive instanceof TrianglePolygonModel) {
      child = new JoglPrimitive(new TrianglePolygonObject((TrianglePolygonModel)primitive));
    } else if (primitive instanceof QuadPolygonModel) {
      child = new JoglPrimitive(new QuadPolygonObject((QuadPolygonModel)primitive));
    } else {
      throw new IllegalArgumentException(primitive.getClass().toString());
    }
   
    final TranslationModel translation = primitive.getTranslation();
    final RotationModel rotation = primitive.getRotation();

    if (translation == null && rotation == null) {
      return child;
    }

    final JoglObjectGroup group = JoglObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));
    
    return group;
  }
  
//  /**
//   * 与えられたboxを含むグループを生成します。
//   * 
//   * @param primitive ボックス
//   * @return 与えられたboxを含むグループ
//   */
//  public static JoglObject create(BoxModel primitive) {
//    final JoglPrimitive child = new JoglPrimitive(new BoxObject(primitive));
//
//    final TranslationModel translation = primitive.getTranslation();
//    final RotationModel rotation = primitive.getRotation();
//
//    if (translation == null && rotation == null) {
//      return child;
//    }
//
//    final JoglObjectGroup group = JoglObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//    
//    return group;
//  }
//
//  /**
//   * 与えられたcylinderを含むなグループを生成します。
//   * 
//   * @param cylinder シリンダー
//   * @return 与えられたcylinderを含むグループ
//   */
//  public static JoglObject create(CylinderModel cylinder) {
//    final JoglPrimitive child = new JoglPrimitive(new CylinderObject(cylinder));
//
//    final TranslationModel translation = cylinder.getTranslation();
//    final RotationModel rotation = cylinder.getRotation();
//
//    if (translation == null && rotation == null) {
//      return child;
//    }
//
//    final JoglObjectGroup group =  JoglObjectGroup.create();
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
//  public static JoglObject create(SphereModel sphere) {
//    final JoglPrimitive child = new JoglPrimitive(new SphereObject(sphere));
//
//    final TranslationModel translation = sphere.getTranslation();
//    final RotationModel rotation = sphere.getRotation();
//
//    if (translation == null && rotation == null) {
//      return child;
//    }
//
//    final JoglObjectGroup group = JoglObjectGroup.create();
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
//  public static JoglObject create(ConeModel cone) {
//    final JoglPrimitive child = new JoglPrimitive(new ConeObject(cone));
//
//    final TranslationModel translation = cone.getTranslation();
//    final RotationModel rotation = cone.getRotation();
//
//    if (translation == null && rotation == null) {
//      return child;
//    }
//
//    final JoglObjectGroup group = JoglObjectGroup.create();
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
//  public static JoglObject create(TrianglePolygonModel polygon) {
//    final JoglPrimitive child = new JoglPrimitive(new TrianglePolygonObject(polygon));
//
//    final TranslationModel translation = polygon.getTranslation();
//    final RotationModel rotation = polygon.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final JoglObjectGroup group = JoglObjectGroup.create();
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
//  public static JoglObject create(QuadPolygonModel polygon) {
//    final JoglPrimitive child = new JoglPrimitive(new QuadPolygonObject(polygon));
//
//    final TranslationModel translation = polygon.getTranslation();
//    final RotationModel rotation = polygon.getRotation();
//    
//    if (translation == null && rotation == null) {
//      return child;
//    }
//    
//    final JoglObjectGroup group = JoglObjectGroup.create();
//    group.addChild(child);
//    group.setBaseCoordinate(createCoordinate(translation, rotation));
//
//    return group;
//  }
  

  /**
   * 座標を生成します。
   * 
   * @param targetGroup オブジェクトグループ
   * @param translation 並進変換
   * @param rotation 回転変換
   * @return 座標系
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
