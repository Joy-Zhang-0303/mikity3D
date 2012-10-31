package org.mklab.mikity.model;

import org.mklab.mikity.java3d.MyTransformGroup;
import org.mklab.mikity.java3d.PrimitiveFactory;
import org.mklab.mikity.util.Util;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * @author iwamoto
 * @version $Revision$, 2011/12/22
 */
public class TransformGroupFactory {
  
  /**
   * @param group グループ
   * @return トランスフォームグループ
   */
  public static MyTransformGroup create(Group group) {
    MyTransformGroup tg = new MyTransformGroup();
    
    XMLBox[] xmlBox = group.loadXMLBox();
    for (int i = 0; i < xmlBox.length; i++) {
      tg.addChild(PrimitiveFactory.create(xmlBox[i]));
    }

    XMLCylinder[] xmlCylinder = group.loadXMLCylinder();
    for (int i = 0; i < xmlCylinder.length; i++) {
      tg.addChild(PrimitiveFactory.create(xmlCylinder[i]));
    }

    XMLSphere[] xmlSphere = group.loadXMLSphere();
    for (int i = 0; i < xmlSphere.length; i++) {
      tg.addChild(PrimitiveFactory.create(xmlSphere[i]));
    }

    XMLCone[] xmlCone = group.loadXMLCone();
    for (int i = 0; i < xmlCone.length; i++) {
      tg.addChild(PrimitiveFactory.create(xmlCone[i]));
    }

    XMLConnector[] xmlConnector = group.loadXMLConnector();
    for (int i = 0; i < xmlConnector.length; i++) {
      tg.addChild(PrimitiveFactory.create(xmlConnector[i]));
    }

    XMLTrianglePolygon[] xmlTriangle = group.loadXMLTrianglePolygon();
    for (int i = 0; i < xmlTriangle.length; i++) {
      tg.addChild(PrimitiveFactory.create(xmlTriangle[i]));
    }

    XMLQuadPolygon[] xmlQuad = group.loadXMLQuadPolygon();
    for (int i = 0; i < xmlQuad.length; i++) {
      tg.addChild(PrimitiveFactory.create(xmlQuad[i]));
    }

    Group[] groups = group.loadGroup();
    for (int i = 0; i < groups.length; i++) {
      tg.addChild(PrimitiveFactory.create(groups[i]));
    }

    /*
     * DHParameterの設定
     */
    Linkdata[] linkData = group.loadLinkdata();
    for (int i = 0; i < linkData.length; i++) {
      if (linkData[i].hasDH()) {
        // 初期値のDHパラメータを作成
        DHParameter parameter = Util.getDHParameter(linkData);
        tg.setDHParameter(parameter);
        break;
      } else if (linkData[i].hasLink()) {
        // DHパラメータを使わないVer
        CoordinateParameter link = Util.getLinkParameter(linkData);
        tg.setLinkParameter(link);
        break;
      }
    }

    // //MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);
    
    return tg;
  }
  
}