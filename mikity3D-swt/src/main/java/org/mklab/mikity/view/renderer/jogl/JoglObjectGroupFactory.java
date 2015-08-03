package org.mklab.mikity.view.renderer.jogl;

import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.xml.simplexml.model.BoxModel;
import org.mklab.mikity.model.xml.simplexml.model.ConeModel;
import org.mklab.mikity.model.xml.simplexml.model.CylinderModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.QuadPolygonModel;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.SphereModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.model.xml.simplexml.model.TrianglePolygonModel;


/**
 * {@link JoglObjectGroup}のファクトリークラスです。
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglObjectGroupFactory {
  /** オブジェクトグループマネージャ。 */
  private ObjectGroupManager manager;
  
  /**
   * 新しく生成された<code>JoglObjectGroupFactory</code>オブジェクトを初期化します。
   * @param manager オブジェクトグループマネージャ
   */
  public JoglObjectGroupFactory(ObjectGroupManager manager) {
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
      objectGroup.addChild(JoglPrimitiveFactory.create(box));
    }

    for (final CylinderModel cylinder : group.getCylinders()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(cylinder));
    }

    for (final SphereModel sphere : group.getSpheres()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(sphere));
    }

    for (final ConeModel cone : group.getCones()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(cone));
    }
    
    for (final TrianglePolygonModel polygon : group.getTrianglePolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon));
    }

    for (final QuadPolygonModel polygon : group.getQuadPolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon));
    }

    for (final GroupModel child : group.getGroups()) {
      final JoglObjectGroup childObjectGroup = create(child);
      objectGroup.addChild(childObjectGroup);
    }

    final JoglCoordinate baseCoordinate = createCoordinateOf(group);
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
   * @return グループの座標系
   */
  private JoglCoordinate createCoordinateOf(final GroupModel group) {
    final TranslationModel translation = group.getTranslation();
    final RotationModel rotation = group.getRotation();
    
    if (translation != null && rotation != null) {
      return new JoglCoordinate(translation, rotation);
    } 
    
    if (translation != null) {
      return new JoglCoordinate(translation);
    }
    
    if (rotation != null) {
      return new JoglCoordinate(rotation);
    }

    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
