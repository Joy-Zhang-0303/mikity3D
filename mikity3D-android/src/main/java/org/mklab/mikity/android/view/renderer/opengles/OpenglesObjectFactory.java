/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.Messages;
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.graphic.GraphicPrimitiveFactory;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.PrimitiveModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;

/**
 * {@link OpenglesObject}のファクトリークラスです。
 * 
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesObjectFactory {
  /** オブジェクトグループマネージャ。 */
  private ObjectGroupManager manager;
  
  /**
   * 新しく生成された<code>OpenglesObjectGroupFactory</code>オブジェクトを初期化します。
   * @param manager オブジェクトグループマネージャ
   */
  public OpenglesObjectFactory(ObjectGroupManager manager) {
    this.manager = manager;
  }
    
  /**
   * {@link OpenglesObjectGroup}を生成します。
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public OpenglesObjectGroup create(final GroupModel group) {
    final OpenglesObjectGroup objectGroup = OpenglesObjectGroup.create(group);

    for (final PrimitiveModel primitive : group.getPrimitives()) {
      if (primitive instanceof NullModel) {
        continue;
      }
      objectGroup.addChild(create(primitive));
    }
    
//    for (final BoxModel box : group.getBoxes()) {
//      objectGroup.addChild(create(box));
//    }
//
//    for (final CylinderModel cylinder : group.getCylinders()) {
//      objectGroup.addChild(create(cylinder));
//    }
//
//    for (final SphereModel sphere : group.getSpheres()) {
//      objectGroup.addChild(create(sphere));
//    }
//
//    for (final ConeModel cone : group.getCones()) {
//      objectGroup.addChild(create(cone));
//    }
//    
//    for (final TrianglePolygonModel polygon : group.getTrianglePolygons()) {
//      objectGroup.addChild(create(polygon));
//    }
//
//    for (final QuadPolygonModel polygon : group.getQuadPolygons()) {
//      objectGroup.addChild(create(polygon));
//    }

    for (final GroupModel child : group.getGroups()) {
      final OpenglesObjectGroup childObjectGroup = create(child);
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
   * 与えられたプリミティブを含むグループを生成します。
   * 
   * @param model プリミティブ
   * @return 与えられたプリミティブを含むグループ
   */
  public OpenglesObject create(PrimitiveModel model) {
    final OpenglesPrimitive primitive = new OpenglesPrimitive(GraphicPrimitiveFactory.create(model));
   
    final TranslationModel translation = model.getTranslation();
    final RotationModel rotation = model.getRotation();

    if (translation == null && rotation == null) {
      return primitive;
    }

    final OpenglesObjectGroup group = OpenglesObjectGroup.create();
    group.addChild(primitive);
    group.setBaseCoordinate(createCoordinateOf(translation, rotation));
    
    return group;
  }
  
  /**
   * 基準座標を生成します。
   * 
   * @param translation 並進変換
   * @param rotation 回転変換
   * @return 基準座標系
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
