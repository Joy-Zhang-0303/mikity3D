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
    final LinkData[] linkData = group.getLinkData();
    for (int i = 0; i < linkData.length; i++) {
      if (linkData[i].hasDHParameter()) {
        final DHParameter dhParameter = Util.getDHParameter(linkData);
        this.dhParameters.add(dhParameter);
        break;
      } else if (linkData[i].hasCoordinateParameter()) {
        final CoordinateParameter coordinateParameter = Util.getCoordinateParameter(linkData);
        this.coordinateParameters.add(coordinateParameter);
        break;
      }
    }

    final JoglTransformGroup tg = new JoglTransformGroup();
    
    final XMLBox[] boxes = group.getXMLBox();
    for (int i = 0; i < boxes.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(boxes[i]));
    }

    final XMLCylinder[] cylinders = group.getXMLCylinder();
    for (int i = 0; i < cylinders.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(cylinders[i]));
    }

    final XMLSphere[] spheres = group.getXMLSphere();
    for (int i = 0; i < spheres.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(spheres[i]));
    }

    final XMLCone[] cones = group.getXMLCone();
    for (int i = 0; i < cones.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(cones[i]));
    }

    //    final XMLConnector[] connectors = group.loadXMLConnector();
    //    for (int i = 0; i < Connectors.length; i++) {
    //      tg.addChild(JoglPrimitiveFactory.create(connectors[i]));
    //    }

    final XMLTrianglePolygon[] trianglePolygons = group.getXMLTrianglePolygon();
    for (int i = 0; i < trianglePolygons.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(trianglePolygons[i], this.dhParameters, this.coordinateParameters));
    }

    final XMLQuadPolygon[] quadPolygons = group.getXMLQuadPolygon();
    for (int i = 0; i < quadPolygons.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(quadPolygons[i], this.dhParameters, this.coordinateParameters));
    }

    final Group[] groups = group.getGroups();
    for (int i = 0; i < groups.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(groups[i]));
    }

    //MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);

    return tg;
  }

}
