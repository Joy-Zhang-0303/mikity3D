package org.mklab.mikity.view.renderer.jogl;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;
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
   * 与えられたプリミティブを含むグループを生成します。
   * 
   * @param primitive プリミティブ
   * @return 与えられたプリミティブを含むグループ
   */
  public static JoglObject create(PrimitiveModel primitive) {
    if (primitive instanceof BoxModel) {
      return create((BoxModel)primitive);
    }
    if (primitive instanceof CylinderModel) {
      return create((CylinderModel)primitive);
    }
    if (primitive instanceof ConeModel) {
      return create((ConeModel)primitive);
    }
    if (primitive instanceof SphereModel) {
      return create((SphereModel)primitive);
    }
    if (primitive instanceof TrianglePolygonModel) {
      return create((TrianglePolygonModel)primitive);
    }
    if (primitive instanceof QuadPolygonModel) {
      return create((QuadPolygonModel)primitive);
    }
    
    throw new IllegalArgumentException(primitive.getClass().toString());
  }
  
  /**
   * 与えられたboxを含むグループを生成します。
   * 
   * @param box ボックス
   * @return 与えられたboxを含むグループ
   */
  public static JoglObject create(BoxModel box) {
    final JoglBox child = new JoglBox(box);

    final TranslationModel translation = box.getTranslation();
    final RotationModel rotation = box.getRotation();

    if (translation == null && rotation == null) {
      return child;
    }

    final JoglObjectGroup group = JoglObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));
    
    return group;
  }

  /**
   * 与えられたcylinderを含むなグループを生成します。
   * 
   * @param cylinder シリンダー
   * @return 与えられたcylinderを含むグループ
   */
  public static JoglObject create(CylinderModel cylinder) {
    final JoglCylinder child = new JoglCylinder(cylinder);

    final TranslationModel translation = cylinder.getTranslation();
    final RotationModel rotation = cylinder.getRotation();

    if (translation == null && rotation == null) {
      return child;
    }

    final JoglObjectGroup group =  JoglObjectGroup.create();
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
  public static JoglObject create(SphereModel sphere) {
    final JoglSphere child = new JoglSphere(sphere);

    final TranslationModel translation = sphere.getTranslation();
    final RotationModel rotation = sphere.getRotation();

    if (translation == null && rotation == null) {
      return child;
    }

    final JoglObjectGroup group = JoglObjectGroup.create();
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
  public static JoglObject create(ConeModel cone) {
    final JoglCone child = new JoglCone(cone);

    final TranslationModel translation = cone.getTranslation();
    final RotationModel rotation = cone.getRotation();

    if (translation == null && rotation == null) {
      return child;
    }

    final JoglObjectGroup group = JoglObjectGroup.create();
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
  public static JoglObject create(TrianglePolygonModel polygon) {
    final JoglTrianglePolygon child = new JoglTrianglePolygon(polygon);

    final TranslationModel translation = polygon.getTranslation();
    final RotationModel rotation = polygon.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final JoglObjectGroup group = JoglObjectGroup.create();
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
  public static JoglObject create(QuadPolygonModel polygon) {
    final JoglQuadPolygon child = new JoglQuadPolygon(polygon);

    final TranslationModel translation = polygon.getTranslation();
    final RotationModel rotation = polygon.getRotation();
    
    if (translation == null && rotation == null) {
      return child;
    }
    
    final JoglObjectGroup group = JoglObjectGroup.create();
    group.addChild(child);
    group.setBaseCoordinate(createCoordinate(translation, rotation));

    return group;
  }
  

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
