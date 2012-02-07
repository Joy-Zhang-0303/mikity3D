package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;


/**
 * JOGLの座標系のグループを表すクラスです。
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglTransformGroup implements JoglCoordinate {
  /** オブジェクトのリスト */
  private JoglObject child;
  /** 座標系のリスト */
  private List<JoglCoordinate> coodinates;
  
  /**
   * Initialize the generated object of {@link JoglTransformGroup}.
   */
  public JoglTransformGroup() {
    this.coodinates = new ArrayList<JoglCoordinate>();
  }
  
  /**
   * オブジェクトを追加します
   * 
   * @param child オブジェクト
   */
  public void setChild(JoglObject child) {
    this.child = child;
  }
  
  /**
   * 座標系を追加します
   * 
   * @param corrdinate 座標系
   */
  public void addCoordinate(JoglCoordinate corrdinate) {
    this.coodinates.add(corrdinate);
  }

  /**
   * @see org.mklab.mikity.jogl.JoglCoordinate#apply(javax.media.opengl.GL)
   */
  @Override
  public void apply(GL gl) {
    for (int i = 0; i < this.coodinates.size(); i++) {
      JoglCoordinate coordinate = this.coodinates.get(i);
      coordinate.apply(gl);
    }
    if (this.child != null) {
      this.child.apply(gl);
    }
  }
}
