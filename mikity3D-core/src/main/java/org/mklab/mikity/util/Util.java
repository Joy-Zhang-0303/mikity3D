/*
 * Created on 2005/02/05
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import org.mklab.mikity.model.CoordinateParameter;
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
   * 座標パラメータを返します。
   * 
   * @param links リンクデータ
   * @return 座標パラメータ
   */
  public static CoordinateParameter getCoordinateParameter(final LinkData[] links) {
    final CoordinateParameter parameter = new CoordinateParameter();

//    for (final LinkData link : links) {
//      // Constが存在する場合
//      if (link.hasBasis()) {
//        final String parameterName = link.getTarget();
//        if (parameterName.equals("locationX")) { //$NON-NLS-1$
//          parameter.setX(link.getBasis() / scale);
//        } else if (parameterName.equals("rotationX")) { //$NON-NLS-1$
//          if (radian) {
//            parameter.setAngleX(link.getBasis());
//          } else {
//            parameter.setAngleX(Math.toRadians(link.getBasis()));
//          }
//        } else if (parameterName.equals("locationY")) { //$NON-NLS-1$
//          parameter.setY(link.getBasis() / scale);
//        } else if (parameterName.equals("rotationY")) { //$NON-NLS-1$
//          if (radian) {
//            parameter.setAngleY(link.getBasis());
//          } else {
//            parameter.setAngleY(Math.toRadians(link.getBasis()));
//          }
//        } else if (parameterName.equals("locationZ")) { //$NON-NLS-1$
//          parameter.setZ(link.getBasis() / scale);
//        } else if (parameterName.equals("rotationZ")) { //$NON-NLS-1$
//          if (radian) {
//            parameter.setAngleZ(link.getBasis());
//          } else {
//            parameter.setAngleZ(Math.toRadians(link.getBasis()));
//          }
//        }
//      }
//    }
    return parameter;
  }
}
