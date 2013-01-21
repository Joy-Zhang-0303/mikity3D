package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.jogl.models.JoglLocationRotation;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.util.Util;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.LinkData;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


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
    
    for (final XMLBox box : group.getXMLBox()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(box));
    }

    for (final XMLCylinder cylinder : group.getXMLCylinder()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(cylinder));
    }

    for (final XMLSphere sphere : group.getXMLSphere()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(sphere));
    }

    for (final XMLCone cone : group.getXMLCone()) {
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
    
    for (final XMLTrianglePolygon polygon : group.getXMLTrianglePolygon()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygon()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final Group child : group.getGroups()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(child));
    }

    final JoglLocationRotation coordinate = createCoordinateOf(group);
    movableGroup.setCoordinate(coordinate);
    
    final String name = group.loadName();
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
  private JoglLocationRotation createCoordinateOf(final Group group) {
    final Location groupLocation = group.getLocation();
    final Rotation groupRotation = group.getRotation();
    
    if (groupLocation != null && groupRotation != null) {
      final float xLocation = groupLocation.loadX();
      final float yLocation = groupLocation.loadY();
      final float zLocation = groupLocation.loadZ();
      final float xRotation = groupRotation.loadXrotate();
      final float yRotation = groupRotation.loadYrotate();
      final float zRotation = groupRotation.loadZrotate();
      final JoglLocationRotation coordinate = new JoglLocationRotation();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      coordinate.setRotation(xRotation, yRotation, zRotation);
      return coordinate;
    } 
    
    if (groupLocation != null) {
      final float xLocation = groupLocation.loadX();
      final float yLocation = groupLocation.loadY();
      final float zLocation = groupLocation.loadZ();
      final JoglLocationRotation coordinate = new JoglLocationRotation();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      return coordinate;
    }
    
    if (groupRotation != null) {
      final float xRotation = groupRotation.loadXrotate();
      final float yRotation = groupRotation.loadYrotate();
      final float zRotation = groupRotation.loadZrotate();
      final JoglLocationRotation coordinate = new JoglLocationRotation();
      coordinate.setRotation(xRotation, yRotation, zRotation);
      return coordinate;
    }

    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
