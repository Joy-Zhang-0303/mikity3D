/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import org.mklab.mikity.model.Messages;
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;

/**
 * {@link OpenglesObjectGroup}のファクトリークラスです。
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesObjectGroupFactory {
  /** オブジェクトグループマネージャ。 */
  private ObjectGroupManager manager;
  
  /**
   * 新しく生成された<code>OpenglesObjectGroupFactory</code>オブジェクトを初期化します。
   * @param manager オブジェクトグループマネージャ
   */
  public OpenglesObjectGroupFactory(ObjectGroupManager manager) {
    this.manager = manager;
  }
    
  /**
   * {@link OpenglesObjectGroup}を生成します。
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public OpenglesObjectGroup create(final GroupModel group) {
    final OpenglesObjectGroup objectGroup = OpenglesObjectGroup.create(group);
    
    for (final BoxModel box : group.getBoxes()) {
      objectGroup.addChild(OpenglesPrimitiveFactory.create(box));
    }

    for (final CylinderModel cylinder : group.getCylinders()) {
      objectGroup.addChild(OpenglesPrimitiveFactory.create(cylinder));
    }

    for (final SphereModel sphere : group.getSpheres()) {
      objectGroup.addChild(OpenglesPrimitiveFactory.create(sphere));
    }

    for (final ConeModel cone : group.getCones()) {
      objectGroup.addChild(OpenglesPrimitiveFactory.create(cone));
    }
    
    for (final TrianglePolygonModel polygon : group.getTrianglePolygons()) {
      objectGroup.addChild(OpenglesPrimitiveFactory.create(polygon));
    }

    for (final QuadPolygonModel polygon : group.getQuadPolygons()) {
      objectGroup.addChild(OpenglesPrimitiveFactory.create(polygon));
    }

    for (final GroupModel child : group.getGroups()) {
      final OpenglesObjectGroup childObjectGroup = create(child);
      objectGroup.addChild(childObjectGroup);
    }

    final OpenglesCoordinate baseCoordinate = createCoordinateOf(group);
    objectGroup.setBaseCoordinate(baseCoordinate);
    
    final String name = group.getName();
    if (name != null) {
      objectGroup.setName(name);
    }
    
    this.manager.addObjectGroup(objectGroup);

    return objectGroup;
  }

  /**
   * グループの座標系を生成します。
   * 
   * @param group オブジェクトのグループ
   * @return グループの座標
   */
  private OpenglesCoordinate createCoordinateOf(final GroupModel group) {
    final TranslationModel translation = group.getTranslation();
    final RotationModel rotation = group.getRotation();
    
    if (translation != null && rotation != null) {
      return new OpenglesCoordinate(translation, rotation);
    } 
    
    if (translation != null) {
      return new OpenglesCoordinate(translation);
    }
    
    if (rotation != null) {
      return new OpenglesCoordinate(rotation);
    }

    throw new IllegalArgumentException(Messages.getString("OpenglesTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
