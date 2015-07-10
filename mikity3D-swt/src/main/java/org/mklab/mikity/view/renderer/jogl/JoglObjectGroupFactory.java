package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.ObjectGroupManager;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.util.Util;


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

    final List<CoordinateParameter> coordinateParameters = new ArrayList<>();
    final List<DHParameter> dhParameters = new ArrayList<>();
    
    final LinkData[] links = group.getLinkData();
    for (final LinkData link : links) {
      if (link.hasDHParameter()) {
        dhParameters.add(Util.getDHParameter(links));
        break;
      } else if (link.hasCoordinateParameter()) {
        coordinateParameters.add(Util.getCoordinateParameter(links));
        break;
      }
    }
    
    for (final XMLTrianglePolygon polygon : group.getXMLTrianglePolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygons()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final Group child : group.getGroups()) {
      objectGroup.addChild(JoglPrimitiveFactory.create(child));
    }

    final JoglCoordinate coordinate = createCoordinateOf(group);
    objectGroup.setBaseCoordinate(coordinate);
    
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
  private JoglCoordinate createCoordinateOf(final Group group) {
    final Translation groupTranslation = group.getTranslation();
    final Rotation groupRotation = group.getRotation();
    
    if (groupTranslation != null && groupRotation != null) {
      final float translationX = groupTranslation.getX();
      final float translationY = groupTranslation.getY();
      final float translationZ = groupTranslation.getZ();
      final float rotationX = groupRotation.getX();
      final float rotationY = groupRotation.getY();
      final float rotationZ = groupRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      return coordinate;
    } 
    
    if (groupTranslation != null) {
      final float translationX = groupTranslation.getX();
      final float translationY = groupTranslation.getY();
      final float translationZ = groupTranslation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setTranslation(translationX, translationY, translationZ);
      return coordinate;
    }
    
    if (groupRotation != null) {
      final float rotationX = groupRotation.getX();
      final float rotationY = groupRotation.getY();
      final float rotationZ = groupRotation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(rotationX, rotationY, rotationZ);
      return coordinate;
    }

    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
