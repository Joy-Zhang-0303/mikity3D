package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;


/**
 * {@link JoglObject}のグループを表すクラスです。
 * @author iwamoto
 * @version $Revision$, 2012/01/20
 */
public class JoglGroup implements JoglObject {
  
  /** オブジェクトのリスト */
  private List<JoglObject> objects;
  /** 座標系のリスト */
  private List<JoglCoordinate> coodinates;
  
  /**
   * Initialize the generated object of {@link JoglGroup}.
   */
  public JoglGroup(){
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

  /**
   * @see org.mklab.mikity.jogl.JoglObject#display(javax.media.opengl.GL)
   */
  @Override
  public void display(GL gl) {
    for (int i = 0; i < this.objects.size(); i++) {
      JoglCoordinate coordinate = this.coodinates.get(i);
      JoglObject object = this.objects.get(i);
      coordinate.setCoordinate(gl);
      object.display(gl);
    }
  }
}
