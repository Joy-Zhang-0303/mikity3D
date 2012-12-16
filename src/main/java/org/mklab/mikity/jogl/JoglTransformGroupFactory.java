package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.util.Util;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.LinkData;
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
    final LinkData[] parameters = group.getLinkData();
    for (int i = 0; i < parameters.length; i++) {
      if (parameters[i].hasDHParameter()) {
        final DHParameter dhParameter = Util.getDHParameter(parameters);
        this.dhParameters.add(dhParameter);
        break;
      } else if (parameters[i].hasCoordinateParameter()) {
        final CoordinateParameter coordinateParameter = Util.getCoordinateParameter(parameters);
        this.coordinateParameters.add(coordinateParameter);
        break;
      }
    }

    final JoglTransformGroup tg = new JoglTransformGroup();
    
    final XMLBox[] boxes = group.getXMLBox();
    for (final XMLBox box : boxes) {
      tg.addChild(JoglPrimitiveFactory.create(box));
    }

    final XMLCylinder[] cylinders = group.getXMLCylinder();
    for (final XMLCylinder cylinder : cylinders) {
      tg.addChild(JoglPrimitiveFactory.create(cylinder));
    }

    final XMLSphere[] spheres = group.getXMLSphere();
    for (final XMLSphere sphere : spheres) {
      tg.addChild(JoglPrimitiveFactory.create(sphere));
    }

    final XMLCone[] cones = group.getXMLCone();
    for (final XMLCone cone : cones) {
      tg.addChild(JoglPrimitiveFactory.create(cone));
    }

    //    final XMLConnector[] connectors = group.loadXMLConnector();
    //    for (int i = 0; i < Connectors.length; i++) {
    //      tg.addChild(JoglPrimitiveFactory.create(connectors[i]));
    //    }

    final XMLTrianglePolygon[] trianglePolygons = group.getXMLTrianglePolygon();
    for (final XMLTrianglePolygon polygon : trianglePolygons) {
      tg.addChild(JoglPrimitiveFactory.create(polygon, this.dhParameters, this.coordinateParameters));
    }

    final XMLQuadPolygon[] quadPolygons = group.getXMLQuadPolygon();
    for (final XMLQuadPolygon polygon : quadPolygons) {
      tg.addChild(JoglPrimitiveFactory.create(polygon, this.dhParameters, this.coordinateParameters));
    }

    final Group[] childGroups = group.getGroups();
    for (final Group childGroup : childGroups) {
      tg.addChild(JoglPrimitiveFactory.create(childGroup));
    }

    //MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);

    return tg;
  }

}
