/*
 * Created on 2013/02/12
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.android.view.renderer;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.Messages;
import org.mklab.mikity.model.MovableGroupManager;
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
 * @author ohashi
 * @version $Revision$, 2013/02/12
 */
public class OpenglesTransformGroupFactory {
  /**
   * @param group グループ
   * @return トランスフォームグループ
   */
  public OpenglesTransformGroup create(final Group group) {
    final OpenglesTransformGroup movableGroup = new OpenglesTransformGroup();
    
    for (final XMLBox box : group.getXMLBoxes()) {
      movableGroup.addChild(OpenglesPrimitiveFactory.create(box));
    }

    for (final XMLCylinder cylinder : group.getXMLCylinders()) {
      movableGroup.addChild(OpenglesPrimitiveFactory.create(cylinder));
    }

    for (final XMLSphere sphere : group.getXMLSpheres()) {
      movableGroup.addChild(OpenglesPrimitiveFactory.create(sphere));
    }

    for (final XMLCone cone : group.getXMLCones()) {
      movableGroup.addChild(OpenglesPrimitiveFactory.create(cone));
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
      movableGroup.addChild(OpenglesPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygons()) {
      movableGroup.addChild(OpenglesPrimitiveFactory.create(polygon, dhParameters, coordinateParameters));
    }

    for (final Group child : group.getGroups()) {
      movableGroup.addChild(OpenglesPrimitiveFactory.create(child));
    }

    final OpenglesCoordinate coordinate = createCoordinateOf(group);
    movableGroup.setInitialCoordinate(coordinate);
    
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
  private OpenglesCoordinate createCoordinateOf(final Group group) {
    final Translation groupLocation = group.getTranslation();
    final Rotation groupRotation = group.getRotation();
    
    if (groupLocation != null && groupRotation != null) {
      final float xLocation = groupLocation.getX();
      final float yLocation = groupLocation.getY();
      final float zLocation = groupLocation.getZ();
      final float xRotation = groupRotation.getX();
      final float yRotation = groupRotation.getY();
      final float zRotation = groupRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      coordinate.setRotation(xRotation, yRotation, zRotation);
      return coordinate;
    } 
    
    if (groupLocation != null) {
      final float xLocation = groupLocation.getX();
      final float yLocation = groupLocation.getY();
      final float zLocation = groupLocation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setLocation(xLocation, yLocation, zLocation);
      return coordinate;
    }
    
    if (groupRotation != null) {
      final float xRotation = groupRotation.getX();
      final float yRotation = groupRotation.getY();
      final float zRotation = groupRotation.getZ();
      final OpenglesCoordinate coordinate = new OpenglesCoordinate();
      coordinate.setRotation(xRotation, yRotation, zRotation);
      return coordinate;
    }

    throw new IllegalArgumentException(Messages.getString("OpenglesTransformGroupFactory.0")); //$NON-NLS-1$
  }
}
