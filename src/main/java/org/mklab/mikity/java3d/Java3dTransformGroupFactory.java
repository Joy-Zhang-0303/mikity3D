package org.mklab.mikity.java3d;

import javax.media.j3d.TransformGroup;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.util.Util;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.LinkData;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * {@link TransformGroup}のファクトリークラスです。
 * @author iwamoto
 * @version $Revision$, 2011/12/22
 */
public class Java3dTransformGroupFactory {
  /**
   * {@link TransformGroup}を生成します。
   * @param group グループ
   * @return トランスフォームグループ
   */
  public Java3dTransformGroup create(final Group group) {
    final Java3dTransformGroup tg = new Java3dTransformGroup();
    
    final XMLBox[] boxes = group.getXMLBox();
    for (final XMLBox box : boxes) {
      tg.addChild(Java3dPrimitiveFactory.create(box));
    }

    final XMLCylinder[] cylinders = group.getXMLCylinder();
    for (final XMLCylinder cylinder : cylinders) {
      tg.addChild(Java3dPrimitiveFactory.create(cylinder));
    }

    final XMLSphere[] spheres = group.getXMLSphere();
    for (final XMLSphere sphere : spheres) {
      tg.addChild(Java3dPrimitiveFactory.create(sphere));
    }

    final XMLCone[] cones = group.getXMLCone();
    for (final XMLCone cone : cones) {
      tg.addChild(Java3dPrimitiveFactory.create(cone));
    }

    final XMLConnector[] connectors = group.getXMLConnector();
    for (final XMLConnector connector : connectors) {
      tg.addChild(Java3dPrimitiveFactory.create(connector));
    }

    final XMLTrianglePolygon[] trianglePolygons = group.getXMLTrianglePolygon();
    for (final XMLTrianglePolygon polygon : trianglePolygons) {
      tg.addChild(Java3dPrimitiveFactory.create(polygon));
    }

    final XMLQuadPolygon[] quadPolygons = group.getXMLQuadPolygon();
    for (final XMLQuadPolygon polygon : quadPolygons) {
      tg.addChild(Java3dPrimitiveFactory.create(polygon));
    }

    final Group[] childGroups = group.getGroups();
    for (final Group childGroup : childGroups) {
      tg.addChild(Java3dPrimitiveFactory.create(childGroup));
    }

    final LinkData[] parameters = group.getLinkData();
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i].hasDHParameter()) {
        final DHParameter parameter = Util.getDHParameter(parameters);
        tg.setDHParameter(parameter);
        break;
      } else if (parameters[i].hasCoordinateParameter()) {
        final CoordinateParameter parameter = Util.getCoordinateParameter(parameters);
        tg.setCoordinateParameter(parameter);
        break;
      }
    }

    // MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);
    
    return tg;
  }
  
}