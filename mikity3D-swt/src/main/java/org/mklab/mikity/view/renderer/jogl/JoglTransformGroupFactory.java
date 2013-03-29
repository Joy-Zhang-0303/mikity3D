package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.mikity.model.xml.simplexml.model.Location;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;
import org.mklab.mikity.util.Util;


/**
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglTransformGroupFactory {
  /**
   * @param group グループ
   * @return トランスフォームグループ
   */
  public JoglTransformGroup create(final Group group) {
    final JoglTransformGroup movableGroup = new JoglTransformGroup();
    
    for (final XMLBox box : group.getXMLBoxes()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(box));
    }

    for (final XMLCylinder cylinder : group.getXMLCylinders()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(cylinder));
    }

    for (final XMLSphere sphere : group.getXMLSpheres()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(sphere));
    }

    for (final XMLCone cone : group.getXMLCones()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(cone));
    }

    final List<CoordinateParameter> coordinateParameters = new ArrayList<CoordinateParameter>();
    final List<DHParameter> dhParameters = new ArrayList<DHParameter>();
    
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
      movableGroup.addChild(JoglPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygons()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final Group child : group.getGroups()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(child));
    }

    final JoglCoordinate coordinate = createCoordinateOf(group);
    movableGroup.setCoordinate(coordinate);
    
    final String name = group.getName();
    if (name != null) {
      movableGroup.setName(name);
    }
    
    // MovableGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, movableGroup);

    return movableGroup;
  }

  /**
   * 移動可能なグループの座標を生成します。
   * @param group グループ
   * @return 移動可能なグループの座標
   */
  private JoglCoordinate createCoordinateOf(final Group group) {
    final Location groupLocation = group.getLocation();
    final Rotation groupRotation = group.getRotation();
    
    if (groupLocation != null && groupRotation != null) {
      final float xLocation = groupLocation.getX();
      final float yLocation = groupLocation.getY();
      final float zLocation = groupLocation.getZ();
      final float xRotation = groupRotation.getXrotation();
      final float yRotation = groupRotation.getYrotation();
      final float zRotation = groupRotation.getZrotation();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      coordinate.setRotation(xRotation, yRotation, zRotation);
      return coordinate;
    } 
    
    if (groupLocation != null) {
      final float xLocation = groupLocation.getX();
      final float yLocation = groupLocation.getY();
      final float zLocation = groupLocation.getZ();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      return coordinate;
    }
    
    if (groupRotation != null) {
      final float xRotation = groupRotation.getXrotation();
      final float yRotation = groupRotation.getYrotation();
      final float zRotation = groupRotation.getZrotation();
      final JoglCoordinate coordinate = new JoglCoordinate();
      coordinate.setRotation(xRotation, yRotation, zRotation);
      return coordinate;
    }

    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
