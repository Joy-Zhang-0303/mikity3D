package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
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
    
    for (final XMLBox box : group.getXMLBox()) {
      tg.addChild(JoglPrimitiveFactory.create(box));
    }

    for (final XMLCylinder cylinder : group.getXMLCylinder()) {
      tg.addChild(JoglPrimitiveFactory.create(cylinder));
    }

    for (final XMLSphere sphere : group.getXMLSphere()) {
      tg.addChild(JoglPrimitiveFactory.create(sphere));
    }

    for (final XMLCone cone : group.getXMLCone()) {
      tg.addChild(JoglPrimitiveFactory.create(cone));
    }

    //    final XMLConnector[] connectors = group.loadXMLConnector();
    //    for (int i = 0; i < Connectors.length; i++) {
    //      tg.addChild(JoglPrimitiveFactory.create(connectors[i]));
    //    }

    for (final XMLTrianglePolygon polygon : group.getXMLTrianglePolygon()) {
      tg.addChild(JoglPrimitiveFactory.create(polygon, this.dhParameters, this.coordinateParameters));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygon()) {
      tg.addChild(JoglPrimitiveFactory.create(polygon, this.dhParameters, this.coordinateParameters));
    }

    for (final Group childGroup : group.getGroups()) {
      tg.addChild(JoglPrimitiveFactory.create(childGroup));
    }

    //MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);

    return tg;
  }

}
