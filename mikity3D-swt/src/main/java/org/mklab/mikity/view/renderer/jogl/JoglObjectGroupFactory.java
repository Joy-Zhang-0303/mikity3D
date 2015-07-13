package org.mklab.mikity.view.renderer.jogl;

import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;


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
  public JoglObjectGroup create(final Group group) {
    final JoglObjectGroup objectGroup = new JoglObjectGroup();
    
    for (final XMLBox box : group.getXMLBoxes()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(box));
    }

    for (final XMLCylinder cylinder : group.getXMLCylinders()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(cylinder));
    }

    for (final XMLSphere sphere : group.getXMLSpheres()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(sphere));
    }

    for (final XMLCone cone : group.getXMLCones()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(cone));
    }

//    final List<CoordinateParameter> coordinateParameters = new ArrayList<>();
//    
//    final LinkData[] links = group.getLinkData();
//    for (final LinkData link : links) {
//      if (link.hasCoordinateParameter()) {
//        coordinateParameters.add(new CoordinateParameter());
//        break;
//      }
//    }
    
    for (final XMLTrianglePolygon polygon : group.getXMLTrianglePolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon));
    }

    for (final Group child : group.getGroups()) {
      objectGroup.addChild(create(child));
    }

    final JoglCoordinate baseCoordinate = createBaseCoordinateOf(group);
    objectGroup.setBaseCoordinate(baseCoordinate);
    
    final String name = group.getName();
    if (name != null) {
      objectGroup.setName(name);
    }
    
    // オブジェクトのGroupとGroupの関連付けを行う
    ObjectGroupManager.assignGroup(group, objectGroup);

    return objectGroup;
  }

  /**
   * グループの座標系を生成します。
   * 
   * @param group オブジェクトのグループ
   * @return グループの座標系
   */
  private JoglCoordinate createBaseCoordinateOf(final Group group) {
    final Translation translation = group.getTranslation();
    final Rotation rotation = group.getRotation();
    
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
