package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * {@link JoglObject}のグループを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/20
 */
public class JoglGroup implements JoglObject {

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _name
   */
  private java.lang.String _name;

  /**
   * Field _XMLBoxList
   */
  private java.util.ArrayList<XMLBox> _XMLBoxList;

  /**
   * Field _XMLCylinderList
   */
  private java.util.ArrayList<XMLCylinder> _XMLCylinderList;

  /**
   * Field _XMLSphereList
   */
  private java.util.ArrayList<XMLSphere> _XMLSphereList;

  /**
   * Field _XMLConeList
   */
  private java.util.ArrayList<XMLCone> _XMLConeList;

  /**
   * Field _XMLConnectorList
   */
  private java.util.ArrayList<XMLConnector> _XMLConnectorList;

  /**
   * Field _XMLTrianglePolygonList
   */
  private java.util.ArrayList<XMLTrianglePolygon> _XMLTrianglePolygonList;

  /**
   * Field _XMLQuadPolygonList
   */
  private java.util.ArrayList<XMLQuadPolygon> _XMLQuadPolygonList;

  /**
   * Field _location
   */
  private org.mklab.mikity.xml.model.Location _location;

  /**
   * Field _rotation
   */
  private org.mklab.mikity.xml.model.Rotation _rotation;

  /**
   * Field _groupList
   */
  private java.util.ArrayList<Group> _groupList;

  /** オブジェクトのリスト */
  private List<JoglObject> objects;
  /** 座標系のリスト */
  private List<JoglCoordinate> coodinates;

  /**
   * Initialize the generated object of {@link JoglGroup}.
   */
  public JoglGroup() {
    this.objects = new ArrayList<JoglObject>();
    this.coodinates = new ArrayList<JoglCoordinate>();
  }

  /**
   * オブジェクトと座標系を合わせて追加します
   * 
   * @param object オブジェクト
   * @param corrdinate 座標系
   */
  public void addModels(JoglCoordinate corrdinate, JoglObject object) {
    this.coodinates.add(corrdinate);
    this.objects.add(object);
  }
  /**
   * @see org.mklab.mikity.jogl.JoglObject#display(javax.media.opengl.GL)
   */
  @Override
  public void display(GL gl) {
    for (int i = 0; i < this.objects.size(); i++) {
      JoglCoordinate coordinate = this.coodinates.get(i);
      coordinate.Transform(gl);
      JoglObject object = this.objects.get(i);
      object.display(gl);
    }
  }

  @Override
  public void addChild(JoglCoordinate coordinate) {
    // TODO Auto-generated method stub
    
  }
}
