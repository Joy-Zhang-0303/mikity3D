package org.mklab.mikity.jogl;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import org.mklab.mikity.jogl.models.JoglCoordinate;
import org.mklab.mikity.jogl.models.JoglLocation;
import org.mklab.mikity.jogl.models.JoglLocationRotation;
import org.mklab.mikity.jogl.models.JoglRotation;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.MovableGroup;


/**
 * JOGLの座標系のグループを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/02/07
 */
public class JoglTransformGroup implements JoglCoordinate, MovableGroup {
  /** オブジェクトのリスト */
  private List<JoglObject> objects;
  /** トランスフォームグループのリスト */
  private List<JoglTransformGroup> groups;
  /** 座標系 */
  private JoglCoordinate coordinate;

  /**
   * 新しく生成された<code>JoglTransformGroup</code>オブジェクトを初期化します。
   */
  public JoglTransformGroup() {
    this.objects = new ArrayList<JoglObject>();
    this.groups = new ArrayList<JoglTransformGroup>();
  }

  /**
   * オブジェクトを追加します。
   * 
   * @param child オブジェクト
   */
  public void addChild(JoglObject child) {
    this.objects.add(child);
  }

  /**
   * トランスフォームグループを追加します。
   * 
   * @param child トランスフォームグループ
   */
  public void addChild(JoglTransformGroup child) {
    this.groups.add(child);
  }

  /**
   * 座標系を設定します。
   * 
   * @param coordinate 座標系
   */
  public void setCoordinate(JoglCoordinate coordinate) {
    this.coordinate = coordinate;
  }

  /**
   * {@inheritDoc}
   */
  public void apply(GL gl) {
    gl.glPushMatrix();
    
    if (this.coordinate != null) {
      this.coordinate.apply(gl);
    }

    for (final JoglObject object : this.objects) {
      object.apply(gl);
    }

    for (final JoglTransformGroup group : this.groups) {
      group.apply(gl);
    }
    
    gl.glPopMatrix();
  }

  /**
   * {@inheritDoc}
   */
  public void setDHParameter(DHParameter parameter) {
    /* 座標系Σ(i-1)からΣiへの変換   */
    
    // 1.xi軸に沿ってa(i-1)だけ並進
    final double a = parameter.getA();
    if (a != 0) {
      if (this.coordinate != null && this.coordinate instanceof JoglLocation) {
        ((JoglLocation)this.coordinate).translate((float)a, 0, 0);
      }
      if (this.coordinate != null && this.coordinate instanceof JoglLocationRotation) {
        ((JoglLocationRotation)this.coordinate).translate((float)a, 0, 0);
      }
    }

    // 2.x(i-1)軸回りにα(i-1)だけ回転
    final double alpha = parameter.getAlpha();
    if (alpha != 0) {
      if (this.coordinate != null && this.coordinate instanceof JoglRotation) {
        ((JoglRotation)this.coordinate).rotate((float)alpha, 0, 0);
      }
      if (this.coordinate != null && this.coordinate instanceof JoglLocationRotation) {
        ((JoglLocationRotation)this.coordinate).rotate((float)alpha, 0, 0);
      }
    }
    
    // 3.ziに沿ってdiだけ並進   
    final double d = parameter.getD();
    if (d != 0) {
      if (this.coordinate != null && this.coordinate instanceof JoglLocation) {
        ((JoglLocation)this.coordinate).translate(0, 0, (float)d);
      }
      if (this.coordinate != null && this.coordinate instanceof JoglLocationRotation) {
        ((JoglLocationRotation)this.coordinate).translate(0, 0, (float)d);
      }
    }

    // 4.zi軸回りにθiだけ回転
    final double theta = parameter.getTheta();
    if (theta != 0) {
      if (this.coordinate != null && this.coordinate instanceof JoglRotation) {
        ((JoglRotation)this.coordinate).rotate(0, 0, (float)theta);
      }
      if (this.coordinate != null && this.coordinate instanceof JoglLocationRotation) {
        ((JoglLocationRotation)this.coordinate).rotate(0, 0, (float)theta);
      }
    }
    
    System.out.println("(a, alpha, d, theta) = (" + a + ", " + alpha + ", " + d + ", " + theta + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    //System.out.println("JoglTransformGroup#setDHParameter()"); //$NON-NLS-1$   
  }

  /**
   * {@inheritDoc}
   */
  public void setCoordinateParameter(CoordinateParameter parameter) {
    final double x = parameter.getX();
    final double y = parameter.getY();
    final double z = parameter.getZ();
    //System.out.println("lx, ly, lz(set)(this)=" + locX + "," + locY + "," + locZ + ":" + this);

    if (x != 0 || y != 0 || z != 0) {
      if (this.coordinate != null && this.coordinate instanceof JoglLocation) {
        ((JoglLocation)this.coordinate).translate((float)x, (float)y, (float)z);
      }
      if (this.coordinate != null && this.coordinate instanceof JoglLocationRotation) {
        ((JoglLocationRotation)this.coordinate).translate((float)x, (float)y, (float)z);
      }
    }

    final double angleX = parameter.getAngleX();
    final double angleY = parameter.getAngleY();
    final double angleZ = parameter.getAngleZ();
    //System.out.println("rx, ry, rz(set)(this)=" + rotX + "," + rotY + "," + rotZ + ":" + this);
    
    if (angleX != 0 || angleY != 0 || angleZ != 0) {
      if (this.coordinate != null && this.coordinate instanceof JoglRotation) {
        ((JoglRotation)this.coordinate).rotate((float)angleX, (float)angleY, (float)angleZ);
      }
      if (this.coordinate != null && this.coordinate instanceof JoglLocationRotation) {
        ((JoglLocationRotation)this.coordinate).rotate((float)angleX, (float)angleY, (float)angleZ);
      }
    }
    
    for (final JoglTransformGroup group : this.groups) {
      group.setCoordinateParameter(parameter);
    }
  }
}
