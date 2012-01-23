package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/20
 */
public class JoglGroup {
  
  private List<JoglObject> objects;
  private List<JoglCoordinate> coodinates;
  
  /**
   * Initialize the generated object of {@link JoglGroup}.
   * @param object
   * @param coodinate
   */
  public JoglGroup(){
    super();
    this.objects = new ArrayList<JoglObject>();
    this.coodinates = new ArrayList<JoglCoordinate>();
    
  }
  
  /**
   * オブジェクトを追加します。
   * 
   * @param object オブジェクト
   */
  public void addObject(JoglObject object) {
    this.objects.add(object);

  }

  /**
   * 座標系を追加します。
   * 
   * @param coordinate 座標系
   */
  public void addCoordinate(JoglCoordinate coordinate) {
    this.coodinates.add(coordinate);
  }

}
