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
    final Java3dTransformGroup transformGroup = new Java3dTransformGroup();
    
    for (final XMLBox box : group.getXMLBox()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(box));
    }

    for (final XMLCylinder cylinder : group.getXMLCylinder()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(cylinder));
    }

    for (final XMLSphere sphere : group.getXMLSphere()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(sphere));
    }

    for (final XMLCone cone : group.getXMLCone()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(cone));
    }

    for (final XMLConnector connector : group.getXMLConnector()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(connector));
    }

    for (final XMLTrianglePolygon polygon : group.getXMLTrianglePolygon()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(polygon));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygon()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(polygon));
    }

    for (final Group child : group.getGroups()) {
      transformGroup.addChild(Java3dPrimitiveFactory.create(child));
    }

    final LinkData[] links = group.getLinkData();
    for (int i = 0; i < links.length; i++) {
      if (links[i].hasDHParameter()) {
        final DHParameter parameter = Util.getDHParameter(links);
        transformGroup.setDHParameter(parameter);
        break;
      } else if (links[i].hasCoordinateParameter()) {
        final CoordinateParameter parameter = Util.getCoordinateParameter(links);
        transformGroup.setCoordinateParameter(parameter);
        break;
      }
    }

    // MyTransformGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, transformGroup);
    
    return transformGroup;
  }
  
}