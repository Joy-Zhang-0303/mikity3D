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
  static List<CoordinateParameter> links;
  /** */
  static List<DHParameter> parameters;

  /**
   * @param group グループ
   * @return トランスフォームグループ
   */
  public static JoglTransformGroup create(Group group) {
    JoglTransformGroup tg = new JoglTransformGroup();
    if (links == null) {
      links = new ArrayList<CoordinateParameter>();
    }
    if(parameters == null){
      parameters = new ArrayList<DHParameter>();
    }
    DHParameter dhParameter = new DHParameter();
    CoordinateParameter coordinateParameter = new CoordinateParameter();
    /*
     * DHParameterの設定
     */
    LinkData[] linkData = group.loadLinkData();
    for (int i = 0; i < linkData.length; i++) {
      if (linkData[i].hasDHParameter()) {
        // 初期値のDHパラメータを作成
        dhParameter = Util.getDHParameter(linkData);
        // tg.setDHParameter(parameter);
        parameters.add(dhParameter);
        break;
      } else if (linkData[i].hasCoordinateParameter()) {
        // DHパラメータを使わないVer
        coordinateParameter = Util.getCoordinateParameter(linkData);
        // tg.setLinkParameter(link);
        links.add(coordinateParameter);
        break;
      }
    }

    XMLBox[] boxes = group.loadXMLBox();
    for (int i = 0; i < boxes.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(boxes[i]));
    }

    XMLCylinder[] cylinders = group.loadXMLCylinder();
    for (int i = 0; i < cylinders.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(cylinders[i]));
    }

    XMLSphere[] spheres = group.loadXMLSphere();
    for (int i = 0; i < spheres.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(spheres[i]));
    }

    XMLCone[] cones = group.loadXMLCone();
    for (int i = 0; i < cones.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(cones[i]));
    }

    //    XMLConnector[] xmlConnector = group.loadXMLConnector();
    //    for (int i = 0; i < xmlConnector.length; i++) {
    //      tg.addChild(JoglPrimitiveFactory.create(xmlConnector[i]));
    //    }

    XMLTrianglePolygon[] trianglePolygons = group.loadXMLTrianglePolygon();
    for (int i = 0; i < trianglePolygons.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(trianglePolygons[i], parameters, links));
    }

    XMLQuadPolygon[] quadPolygons = group.loadXMLQuadPolygon();
    for (int i = 0; i < quadPolygons.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(quadPolygons[i], parameters, links));
    }

    Group[] groups = group.loadGroups();
    for (int i = 0; i < groups.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(groups[i]));
    }

    //MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);

    return tg;
  }

}
