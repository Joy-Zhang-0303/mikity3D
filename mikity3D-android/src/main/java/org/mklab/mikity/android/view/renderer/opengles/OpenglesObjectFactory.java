/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer.opengles;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.Messages;
import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.graphic.GraphicObjectFactory;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
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
  private GroupObjectManager manager;
  
  /**
   * 新しく生成された<code>OpenglesObjectGroupFactory</code>オブジェクトを初期化します。
   * @param manager オブジェクトグループマネージャ
   */
  public OpenglesObjectFactory(GroupObjectManager manager) {
    this.manager = manager;
  }
    
  /**
   * {@link OpenglesGroupObject}を生成します。
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public OpenglesGroupObject create(final GroupModel group) {
    final OpenglesGroupObject objectGroup = OpenglesGroupObject.create(group);

    for (final ObjectModel primitive : group.getPrimitives()) {
      if (primitive instanceof NullModel) {
        continue;
      }
      objectGroup.addElement(create(primitive));
    }

    for (final GroupModel child : group.getGroups()) {
      final OpenglesGroupObject childObjectGroup = create(child);
      objectGroup.addElement(childObjectGroup);
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
  public OpenglesObject create(ObjectModel model) {
    final OpenglesSingleObject primitive = new OpenglesSingleObject(GraphicObjectFactory.create(model));
   
    final TranslationModel translation = model.getTranslation();
    final RotationModel rotation = model.getRotation();

    if (translation == null && rotation == null) {
      return primitive;
    }

    final OpenglesGroupObject group = OpenglesGroupObject.create();
    group.addElement(primitive);
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
