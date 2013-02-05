/*
 * Created on 2005/02/05
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;

/**
 * ユーティリティクラスです。
 * 
 * @author miki
 * @version $Revision: 1.7 $.2005/02/05
 */
public class Util {
  /** */
  public static int scale = 1;
  /** */
  public static boolean radian = true;
  
  /**
   * コンストラクター
   */
  private Util() {
  // nothing to do
  }

  /**
   * DHパラメータを返します。
   * 
   * @param links リンクデータ
   * @return DHパラメータ
   */
  public static DHParameter getDHParameter(final LinkData[] links) {
    final DHParameter parameter = new DHParameter(); // 初期値全てゼロのDHParameter

    for (final LinkData link : links) {
      // Constが存在する場合
      if (link.hasInitialValue()) {
        final String parameterName = link.getTargetName();
        if (parameterName.equals("theta")) { //$NON-NLS-1$
          if (radian) {
            parameter.setTheta(link.getInitialValue());
          } else {
            parameter.setTheta(Math.toRadians(link.getInitialValue()));
          }
        } else if (parameterName.equals("d")) { //$NON-NLS-1$
          parameter.setD(link.getInitialValue() / scale);
        } else if (parameterName.equals("a")) { //$NON-NLS-1$
          parameter.setA(link.getInitialValue() / scale);
        } else if (parameterName.equals("alpha")) { //$NON-NLS-1$
          if (radian) {
            parameter.setAlpha(link.getInitialValue());
          } else {
            parameter.setAlpha(Math.toRadians(link.getInitialValue()));
          }
        }
      }
    }
    return parameter;
  }

  /**
   * 座標パラメータを返します。
   * 
   * @param links リンクデータ
   * @return 座標パラメータ
   */
  public static CoordinateParameter getCoordinateParameter(final LinkData[] links) {
    final CoordinateParameter parameter = new CoordinateParameter();

    for (final LinkData link : links) {
      // Constが存在する場合
      if (link.hasInitialValue()) {
        final String parameterName = link.getTargetName();
        if (parameterName.equals("locationX")) { //$NON-NLS-1$
          parameter.setX(link.getInitialValue() / scale);
        } else if (parameterName.equals("rotationX")) { //$NON-NLS-1$
          if (radian) {
            parameter.setAngleX(link.getInitialValue());
          } else {
            parameter.setAngleX(Math.toRadians(link.getInitialValue()));
          }
        } else if (parameterName.equals("locationY")) { //$NON-NLS-1$
          parameter.setY(link.getInitialValue() / scale);
        } else if (parameterName.equals("rotationY")) { //$NON-NLS-1$
          if (radian) {
            parameter.setAngleY(link.getInitialValue());
          } else {
            parameter.setAngleY(Math.toRadians(link.getInitialValue()));
          }
        } else if (parameterName.equals("locationZ")) { //$NON-NLS-1$
          parameter.setZ(link.getInitialValue() / scale);
        } else if (parameterName.equals("rotationZ")) { //$NON-NLS-1$
          if (radian) {
            parameter.setAngleZ(link.getInitialValue());
          } else {
            parameter.setAngleZ(Math.toRadians(link.getInitialValue()));
          }
        }
      }
    }
    return parameter;
  }
}
