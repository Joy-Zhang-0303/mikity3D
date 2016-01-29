package org.mklab.mikity.swt.renderer;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.graphic.GraphicObject;
import org.mklab.mikity.model.graphic.GraphicObjectFactory;
import org.mklab.mikity.model.xml.simplexml.model.AxisModel;
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
   * @param group グループモデル
   * @return グループオブジェクト
   */
  public JoglGroupObject create(final GroupModel group) {
    final JoglGroupObject groupObject = JoglGroupObject.create(group);
    
    for (final ObjectModel object : group.getObjects()) {
      if (object instanceof NullModel) {
        continue;
      }
      groupObject.addElement(create(object));
    }

    for (final GroupModel subGroup : group.getGroups()) {
      final JoglGroupObject subGroupObject = create(subGroup);
      groupObject.addElement(subGroupObject);
    }

    final Coordinate baseCoordinate = createCoordinateOf(group.getTranslation(), group.getRotation());
    groupObject.setBaseCoordinate(baseCoordinate);
    
    final String name = group.getName();
    if (name != null) {
      groupObject.setName(name);
    }
    
    this.manager.addGroupObject(groupObject);

    return groupObject;
  }
  
  /**
   * 与えられたモデルを含むオブジェクトを生成します。
   * 
   * @param model モデル
   * @return 与えられたモデルを含むオブジェクト
   */
  public JoglObject create(ObjectModel model) {
    final float modelMin = Math.min(Math.min(model.getWidth(), model.getDepth()), model.getHeight());
    final float modelMax = Math.max(Math.max(model.getWidth(), model.getDepth()), model.getHeight());
    float axisRadius = Math.min(Math.max(modelMin/16, modelMax/200), modelMin/2);
    axisRadius = axisRadius == 0 ? modelMax/100 : axisRadius;
        
    final GraphicObject axisX = GraphicObjectFactory.create(new AxisModel(axisRadius, Math.max(model.getDepth(), axisRadius*20), 36));
    final GraphicObject axisY = GraphicObjectFactory.create(new AxisModel(axisRadius, Math.max(model.getWidth(), axisRadius*20), 36));
    final GraphicObject axisZ = GraphicObjectFactory.create(new AxisModel(axisRadius, Math.max(model.getHeight()*1.5f, axisRadius*20), 36));
    final GraphicObject[] axies = new GraphicObject[]{axisX, axisY, axisZ};
    
    final JoglSingleObject object = new JoglSingleObject(GraphicObjectFactory.create(model), axies);
   
    final TranslationModel translation = model.getTranslation();
    final RotationModel rotation = model.getRotation();

    if (translation == null && rotation == null) {
      return object;
    }

    final JoglGroupObject group = JoglGroupObject.create();
    group.addElement(object);
    group.setBaseCoordinate(createCoordinateOf(translation, rotation));
    
    return group;
  }

  /**
   * 座標を生成します。
   * 
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
