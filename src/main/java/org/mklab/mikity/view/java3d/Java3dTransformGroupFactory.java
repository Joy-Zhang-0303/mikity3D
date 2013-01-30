package org.mklab.mikity.view.java3d;

import javax.media.j3d.TransformGroup;

import org.mklab.mikity.model.model.CoordinateParameter;
import org.mklab.mikity.model.model.DHParameter;
import org.mklab.mikity.model.model.MovableGroupManager;
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
    final Java3dTransformGroup movableGroup = new Java3dTransformGroup();
    
    for (final XMLBox box : group.getXMLBox()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(box));
    }

    for (final XMLCylinder cylinder : group.getXMLCylinder()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(cylinder));
    }

    for (final XMLSphere sphere : group.getXMLSphere()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(sphere));
    }

    for (final XMLCone cone : group.getXMLCone()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(cone));
    }

    for (final XMLConnector connector : group.getXMLConnector()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(connector));
    }

    for (final XMLTrianglePolygon polygon : group.getXMLTrianglePolygon()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(polygon));
    }

    for (final XMLQuadPolygon polygon : group.getXMLQuadPolygon()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(polygon));
    }

    for (final Group child : group.getGroups()) {
      movableGroup.addChild(Java3dPrimitiveFactory.create(child));
    }

    final LinkData[] links = group.getLinkData();
    for (int i = 0; i < links.length; i++) {
      if (links[i].hasDHParameter()) {
        final DHParameter dhParameter = Util.getDHParameter(links);
        movableGroup.setDHParameter(dhParameter);
        break;
      } else if (links[i].hasCoordinateParameter()) {
        final CoordinateParameter coordinateParameter = Util.getCoordinateParameter(links);
        movableGroup.setCoordinateParameter(coordinateParameter);
        break;
      }
    }

    // MovableGroup と Groupの関連付けを行う
    MovableGroupManager.assignGroup(group, movableGroup);
    
    return movableGroup;
  }
  
}