package org.mklab.mikity.view.renderer.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroup;


/**
 * 可動グループを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglTransformGroup implements MovableGroup {
  /** オブジェクト */
  private List<JoglObject> objects = new ArrayList<>();
  /**可動サブグループ */
  private List<JoglTransformGroup> groups = new ArrayList<>();
  /** 座標系の初期値 */
  private JoglCoordinate baseCoordinate;
  /** 座標系 */
  private JoglCoordinate coordinate = new JoglCoordinate();
  /** 名前 */
  private String name;

  /**
   * オブジェクトを追加します。
   * 
   * @param child オブジェクト
   */
  public void addChild(JoglObject child) {
    this.objects.add(child);
  }

  /**
   * 可動サブグループを追加します。
   * 
   * @param child 可動サブグループ
   */
  public void addChild(JoglTransformGroup child) {
    this.groups.add(child);
  }

  /**
   * 座標系の初期値を設定します。
   * 
   * @param coordinate 座標系の初期値
   */
  public void setBaseCoordinate(JoglCoordinate coordinate) {
    this.baseCoordinate = coordinate; 
  }

  /**
   * GLによる操作(座標変換・描画)を適用します。
   * @param gl GL
   */
  public void display(GL2 gl) {
    gl.glPushMatrix();
    
    if (this.baseCoordinate != null) {
      this.baseCoordinate.apply(gl);
    }

    if (this.coordinate != null) {
      this.coordinate.apply(gl);
    }

    for (final JoglObject object : this.objects) {
      object.display(gl);
    }

    for (final JoglTransformGroup group : this.groups) {
      group.display(gl);
    }
    
    gl.glPopMatrix();
  }

  /**
   * {@inheritDoc}
   */
  public void setDHParameter(DHParameter parameter) {
    if (this.coordinate == null) {
      return;
    }

    /* 座標系Σ(i-1)からΣiへの変換   */
    // 1.xi軸に沿ってa(i-1)だけ並進
    // 2.x(i-1)軸回りにα(i-1)だけ回転
    // 3.ziに沿ってdiだけ並進   
    // 4.zi軸回りにθiだけ回転
    
    final double a = parameter.getA();
    final double d = parameter.getD();
    
    this.coordinate.setTranslation((float)a, 0, (float)d);

    final double alpha = parameter.getAlpha();
    final double theta = parameter.getTheta();

    this.coordinate.setRotation((float)alpha, 0, (float)theta);
  }

  /**
   * {@inheritDoc}
   */
  public void setCoordinateParameter(CoordinateParameter parameter) {
    if (this.coordinate == null) {
      return;
    }
    
    final double x = parameter.getTranslationX();
    final double y = parameter.getTranslationY();
    final double z = parameter.getTranslationZ();
    
    this.coordinate.setTranslation((float)x, (float)y, (float)z);

    final double angleX = parameter.getRotationX();
    final double angleY = parameter.getRotationY();
    final double angleZ = parameter.getRotationZ();
    
    this.coordinate.setRotation((float)angleX, (float)angleY, (float)angleZ);
  }

  /**
   * {@inheritDoc}
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (this.name == null) {
      return super.toString();
    }

    if (this.coordinate == null) {
      return this.name;
    }
    
    return this.name + this.coordinate;
  }
  
}
