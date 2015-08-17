package org.mklab.mikity.view.renderer.jogl;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.graphic.GraphicPrimitiveFactory;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;


/**
 * {@link JoglObject}のファクトリークラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglObjectFactory {
  /** オブジェクトグループマネージャ。 */
  private ObjectGroupManager manager;
  
  /**
   * 新しく生成された<code>JoglObjectGroupFactory</code>オブジェクトを初期化します。
   * @param manager オブジェクトグループマネージャ
   */
  public JoglObjectFactory(ObjectGroupManager manager) {
    this.manager = manager;
  }
  
  /**
   * {@link JoglObjectGroup}を生成します。
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public JoglObjectGroup create(final GroupModel group) {
    final JoglObjectGroup objectGroup = JoglObjectGroup.create(group);
    
    for (final BoxModel box : group.getBoxes()) {
      objectGroup.addChild(create(box));
    }

    for (final CylinderModel cylinder : group.getCylinders()) {
      objectGroup.addChild(create(cylinder));
    }

    for (final SphereModel sphere : group.getSpheres()) {
      objectGroup.addChild(create(sphere));
    }

    for (final ConeModel cone : group.getCones()) {
      objectGroup.addChild(create(cone));
    }
    
    for (final TrianglePolygonModel polygon : group.getTrianglePolygons()) {
      objectGroup.addChild(create(polygon));
    }

    for (final QuadPolygonModel polygon : group.getQuadPolygons()) {
      objectGroup.addChild(create(polygon));
    }

    for (final GroupModel child : group.getGroups()) {
      final JoglObjectGroup childObjectGroup = create(child);
      objectGroup.addChild(childObjectGroup);
    }

    final Coordinate baseCoordinate = createCoordinateOf(group.getTranslation(), group.getRotation());
    objectGroup.setBaseCoordinate(baseCoordinate);
    
    final String name = group.getName();
    if (name != null) {
      objectGroup.setName(name);
    }
    
    this.manager.addObjectGroup(objectGroup);

    return objectGroup;
  }
  
  /**
   * 与えられたモデルを含むプリミティブを生成します。
   * 
   * @param model モデル
   * @return 与えられたモデルを含むプリミティブ
   */
  public JoglObject create(PrimitiveModel model) {
    final JoglPrimitive primitive = new JoglPrimitive(GraphicPrimitiveFactory.create(model));
   
    final TranslationModel translation = model.getTranslation();
    final RotationModel rotation = model.getRotation();

    if (translation == null && rotation == null) {
      return primitive;
    }

    final JoglObjectGroup group = JoglObjectGroup.create();
    group.addChild(primitive);
    group.setBaseCoordinate(createCoordinateOf(translation, rotation));
    
    return group;
  }

//  /**
//   * グループの座標系を生成します。
//   * 
//   * @param group オブジェクトのグループ
//   * @return グループの座標系
//   */
//  private Coordinate createCoordinateOf(final GroupModel group) {
//    final TranslationModel translation = group.getTranslation();
//    final RotationModel rotation = group.getRotation();
//    
//    if (translation != null && rotation != null) {
//      return new Coordinate(translation, rotation);
//    } 
//    
//    if (translation != null) {
//      return new Coordinate(translation);
//    }
//    
//    if (rotation != null) {
//      return new Coordinate(rotation);
//    }
//
//    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
//  }

  /**
   * 座標を生成します。
   * 
   * @param targetGroup オブジェクトグループ
   * @param translation 並進変換
   * @param rotation 回転変換
   * @return 座標系
   */
  private Coordinate createCoordinateOf(final TranslationModel translation, final RotationModel rotation) {
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
