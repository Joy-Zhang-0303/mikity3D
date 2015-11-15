package org.mklab.mikity.view.renderer.jogl;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.graphic.GraphicObjectFactory;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.NullModel;
import org.mklab.mikity.model.xml.simplexml.model.ObjectModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;


/**
 * {@link JoglObject}のファクトリークラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglObjectFactory {
  /** オブジェクトグループマネージャ。 */
  private GroupObjectManager manager;
  
  /**
   * 新しく生成された<code>JoglObjectGroupFactory</code>オブジェクトを初期化します。
   * @param manager オブジェクトグループマネージャ
   */
  public JoglObjectFactory(GroupObjectManager manager) {
    this.manager = manager;
  }
  
  /**
   * {@link JoglGroupObject}を生成します。
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public JoglGroupObject create(final GroupModel group) {
    final JoglGroupObject objectGroup = JoglGroupObject.create(group);
    
    for (final ObjectModel primitive : group.getPrimitives()) {
      if (primitive instanceof NullModel) {
        continue;
      }
      objectGroup.addElement(create(primitive));
    }

    for (final GroupModel child : group.getGroups()) {
      final JoglGroupObject childObjectGroup = create(child);
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
   * 与えられたモデルを含むプリミティブを生成します。
   * 
   * @param model モデル
   * @return 与えられたモデルを含むプリミティブ
   */
  public JoglObject create(ObjectModel model) {
    final JoglSingleObject primitive = new JoglSingleObject(GraphicObjectFactory.create(model));
   
    final TranslationModel translation = model.getTranslation();
    final RotationModel rotation = model.getRotation();

    if (translation == null && rotation == null) {
      return primitive;
    }

    final JoglGroupObject group = JoglGroupObject.create();
    group.addElement(primitive);
    group.setBaseCoordinate(createCoordinateOf(translation, rotation));
    
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
