package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

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
        final DHParameter dhParameter = Util.getDHParameter(links);
        this.dhParameters.add(dhParameter);
        break;
      } else if (link.hasCoordinateParameter()) {
        final CoordinateParameter coordinateParameter = Util.getCoordinateParameter(links);
        this.coordinateParameters.add(coordinateParameter);
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

    setCoordinateOfMovableGroup(group, movableGroup);
    
    // MovableGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, movableGroup);

    return movableGroup;
  }

  /**
   * 移動可能なグループの座標を設定します。
   * @param group グループ
   * @param movableGroup 移動可能なグループ
   */
  private void setCoordinateOfMovableGroup(final Group group, final JoglTransformGroup movableGroup) {
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
      movableGroup.setCoordinate(locationRotation);
    } else if (groupLocation != null) {
      final float xLocation = groupLocation.loadX();
      final float yLocation = groupLocation.loadY();
      final float zLocation = groupLocation.loadZ();
      final JoglLocation locationRotation = new JoglLocation();
      locationRotation.setLocation(xLocation, yLocation, zLocation);
      movableGroup.setCoordinate(locationRotation);
    } else if (groupRotation != null) {
      final float xRotation = groupRotation.loadXrotate();
      final float yRotation = groupRotation.loadYrotate();
      final float zRotation = groupRotation.loadZrotate();
      final JoglRotation rotation = new JoglRotation();
      rotation.setRotation(xRotation, yRotation, zRotation);
      movableGroup.setCoordinate(rotation);
    }
  }

}
