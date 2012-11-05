package org.mklab.mikity.model;

import javax.media.j3d.TransformGroup;

import org.mklab.mikity.java3d.MyTransformGroup;
import org.mklab.mikity.java3d.PrimitiveFactory;
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
public class TransformGroupFactory {
  /**
   * {@link TransformGroup}を生成します。
   * @param group グループ
   * @return トランスフォームグループ
   */
  public static MyTransformGroup create(final Group group) {
    final MyTransformGroup tg = new MyTransformGroup();
    
    final XMLBox[] boxes = group.loadXMLBox();
    for (int i = 0; i < boxes.length; i++) {
      tg.addChild(PrimitiveFactory.create(boxes[i]));
    }

    final XMLCylinder[] cylinders = group.loadXMLCylinder();
    for (int i = 0; i < cylinders.length; i++) {
      tg.addChild(PrimitiveFactory.create(cylinders[i]));
    }

    final XMLSphere[] spheres = group.loadXMLSphere();
    for (int i = 0; i < spheres.length; i++) {
      tg.addChild(PrimitiveFactory.create(spheres[i]));
    }

    final XMLCone[] cones = group.loadXMLCone();
    for (int i = 0; i < cones.length; i++) {
      tg.addChild(PrimitiveFactory.create(cones[i]));
    }

    final XMLConnector[] connectors = group.loadXMLConnector();
    for (int i = 0; i < connectors.length; i++) {
      tg.addChild(PrimitiveFactory.create(connectors[i]));
    }

    final XMLTrianglePolygon[] trianglePolygons = group.loadXMLTrianglePolygon();
    for (int i = 0; i < trianglePolygons.length; i++) {
      tg.addChild(PrimitiveFactory.create(trianglePolygons[i]));
    }

    final XMLQuadPolygon[] quadPolygons = group.loadXMLQuadPolygon();
    for (int i = 0; i < quadPolygons.length; i++) {
      tg.addChild(PrimitiveFactory.create(quadPolygons[i]));
    }

    final Group[] groups = group.loadGroup();
    for (int i = 0; i < groups.length; i++) {
      tg.addChild(PrimitiveFactory.create(groups[i]));
    }

    final LinkData[] data = group.loadLinkData();
    for (int i = 0; i < data.length; i++) {
      if (data[i].hasDhParameter()) {
        final DHParameter parameter = Util.getDHParameter(data);
        tg.setDHParameter(parameter);
        break;
      } else if (data[i].hasCoordinateParameter()) {
        final CoordinateParameter parameter = Util.getCoordinateParameter(data);
        tg.setCoordinateParameter(parameter);
        break;
      }
    }

    // MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, tg);
    
    return tg;
  }
  
}