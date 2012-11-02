package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.MovableGroupManager;
import org.mklab.mikity.util.Util;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;
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
    DHParameter parameter = new DHParameter();
    CoordinateParameter link = new CoordinateParameter();
    /*
     * DHParameterの設定
     */
    Linkdata[] linkData = group.loadLinkdata();
    for (int i = 0; i < linkData.length; i++) {
      if (linkData[i].hasDH()) {
        // 初期値のDHパラメータを作成
        parameter = Util.getDHParameter(linkData);
        //        tg.setDHParameter(parameter);
        parameters.add(parameter);
        break;
      } else if (linkData[i].hasLink()) {
        // DHパラメータを使わないVer
        link = Util.getCoordinateParameter(linkData);
        //        tg.setLinkParameter(link);
        links.add(link);
        break;
      }
    }

    XMLBox[] xmlBox = group.loadXMLBox();
    for (int i = 0; i < xmlBox.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(xmlBox[i]));
    }

    XMLCylinder[] xmlCylinder = group.loadXMLCylinder();
    for (int i = 0; i < xmlCylinder.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(xmlCylinder[i]));
    }

    XMLSphere[] xmlSphere = group.loadXMLSphere();
    for (int i = 0; i < xmlSphere.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(xmlSphere[i]));
    }

    XMLCone[] xmlCone = group.loadXMLCone();
    for (int i = 0; i < xmlCone.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(xmlCone[i]));
    }

    //    XMLConnector[] xmlConnector = group.loadXMLConnector();
    //    for (int i = 0; i < xmlConnector.length; i++) {
    //      tg.addChild(JoglPrimitiveFactory.create(xmlConnector[i]));
    //    }

    XMLTrianglePolygon[] xmlTriangle = group.loadXMLTrianglePolygon();
    for (int i = 0; i < xmlTriangle.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(xmlTriangle[i], parameters, links));
    }

    XMLQuadPolygon[] xmlQuad = group.loadXMLQuadPolygon();
    for (int i = 0; i < xmlQuad.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(xmlQuad[i], parameters, links));
    }

    Group[] groups = group.loadGroup();
    for (int i = 0; i < groups.length; i++) {
      tg.addChild(JoglPrimitiveFactory.create(groups[i]));
    }

    //    // //MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);

    return tg;
  }

}
