package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.jogl.models.JoglCoordinate;
import org.mklab.mikity.jogl.models.JoglLocation;
import org.mklab.mikity.jogl.models.JoglLocationRotation;
import org.mklab.mikity.jogl.models.JoglRotation;
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
  /** */
  private List<CoordinateParameter> coordinateParameters = new ArrayList<CoordinateParameter>();
  /** */
  private List<DHParameter> dhParameters = new ArrayList<DHParameter>();

  /**
   * @param group グループ
   * @return トランスフォームグループ
   */
  public JoglTransformGroup create(final Group group) {
    final LinkData[] links = group.getLinkData();
    for (final LinkData link : links) {
      if (link.hasDHParameter()) {
        this.dhParameters.add(Util.getDHParameter(links));
        break;
      } else if (link.hasCoordinateParameter()) {
        this.coordinateParameters.add(Util.getCoordinateParameter(links));
        break;
      }
    }

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

    for (final XMLTrianglePolygon polygon : group.getXMLTrianglePolygon()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(polygon, this.dhParameters, this.coordinateParameters));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygon()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(polygon, this.dhParameters, this.coordinateParameters));
    }

    for (final Group child : group.getGroups()) {
      movableGroup.addChild(JoglPrimitiveFactory.create(child));
    }

    final JoglCoordinate coordinate = createCoordinate(group);
    movableGroup.setCoordinate(coordinate);
    
    // MovableGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, movableGroup);

    return movableGroup;
  }

  /**
   * 移動可能なグループの座標を生成します。
   * @param group グループ
   * @return 移動可能なグループの座標
   */
  private JoglCoordinate createCoordinate(final Group group) {
    final Location groupLocation = group.getLocation();
    final Rotation groupRotation = group.getRotation();
    
    if (groupLocation != null && groupRotation != null) {
      final float xLocation = groupLocation.loadX() * 10f;
      final float yLocation = groupLocation.loadY() * 10f;
      final float zLocation = groupLocation.loadZ() * 10f;
      final float xRotation = groupRotation.loadXrotate();
      final float yRotation = groupRotation.loadYrotate();
      final float zRotation = groupRotation.loadZrotate();
      final JoglLocationRotation locationRotation = new JoglLocationRotation();
      locationRotation.setLocation(xLocation, yLocation, zLocation);
      locationRotation.setRotation(xRotation, yRotation, zRotation);
      return locationRotation;
    } 
    
    if (groupLocation != null) {
      final float xLocation = groupLocation.loadX() * 10f;
      final float yLocation = groupLocation.loadY() * 10f;
      final float zLocation = groupLocation.loadZ() * 10f;
      final JoglLocation location = new JoglLocation();
      location.setLocation(xLocation, yLocation, zLocation);
      return location;
    }
    
    if (groupRotation != null) {
      final float xRotation = groupRotation.loadXrotate();
      final float yRotation = groupRotation.loadYrotate();
      final float zRotation = groupRotation.loadZrotate();
      final JoglRotation rotation = new JoglRotation();
      rotation.setRotation(xRotation, yRotation, zRotation);
      return rotation;
    }

    throw new IllegalArgumentException(Messages.getString("JoglTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
