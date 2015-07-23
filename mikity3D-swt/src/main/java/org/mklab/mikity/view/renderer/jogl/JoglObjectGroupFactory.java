package org.mklab.mikity.view.renderer.jogl;

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
 * {@link JoglObjectGroup}のファクトリークラスです。
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglObjectGroupFactory {
  /**
   * {@link JoglObjectGroup}を生成します。
   * @param group オブジェクトのグループ
   * @return グループ
   */
  public JoglObjectGroup create(final GroupModel group) {
    final JoglObjectGroup objectGroup = JoglObjectGroup.create(group);
    
    for (final BoxModel box : group.getXMLBoxes()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(box));
    }

    for (final CylinderModel cylinder : group.getXMLCylinders()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(cylinder));
    }

    for (final SphereModel sphere : group.getXMLSpheres()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(sphere));
    }

    for (final ConeModel cone : group.getXMLCones()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(cone));
    }
    
    for (final TrianglePolygonModel polygon : group.getXMLTrianglePolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon));
    }

    for (final QuadPolygonModel polygon : group.getXMLQuadPolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon));
    }

    for (final GroupModel child : group.getGroups()) {
      objectGroup.addChild(create(child));
    }

    final JoglCoordinate baseCoordinate = createBaseCoordinateOf(group);
    objectGroup.setBaseCoordinate(baseCoordinate);
    
    final String name = group.getName();
    if (name != null) {
      objectGroup.setName(name);
    }
    
    ObjectGroupManager.addObjectGroup(objectGroup);

    return objectGroup;
  }

  /**
   * グループの座標系を生成します。
   * 
   * @param group オブジェクトのグループ
   * @return グループの座標系
   */
  private JoglCoordinate createBaseCoordinateOf(final GroupModel group) {
    final TranslationModel translation = group.getTranslation();
    final RotationModel rotation = group.getRotation();
    
    if (translation != null && rotation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      return coordinate;
    } 
    
    if (translation != null) {
      final float translationX = translation.getX();
      final float translationY = translation.getY();
      final float translationZ = translation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      return coordinate;
    }
    
    if (rotation != null) {
      final float rotationX = rotation.getX();
      final float rotationY = rotation.getY();
      final float rotationZ = rotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      return coordinate;
    }

    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
