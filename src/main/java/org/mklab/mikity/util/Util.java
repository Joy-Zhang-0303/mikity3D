/*
 * Created on 2005/02/05
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.xml.model.LinkData;

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
  public static boolean radian = false;
  
  /**
   * コンストラクター
   */
  private Util() {
  // nothing to do
  }

  /**
   * DHパラメータを返します。
   * 
   * @param linkdata リンクデータ
   * @return DHパラメータ
   */
  public static DHParameter getDHParameter(final LinkData[] linkdata) {
    DHParameter parameter = new DHParameter(); // 初期値全てゼロのDHParameter

    // linkdataが無い場合はlinkdata.lengthが0になる
    for (int i = 0; i < linkdata.length; i++) {
      // Constが存在する場合
      if (linkdata[i].hasInitialValue()) {
        // かつ、targetがthetaであるか、dであるかを判別
        if (linkdata[i].loadTargetName().equals("theta")) { //$NON-NLS-1$
          if (radian) {
            parameter.setTheta(linkdata[i].loadInitialValue());
          } else {
            parameter.setTheta(Math.toRadians(linkdata[i].loadInitialValue()));
          }
        } else if (linkdata[i].loadTargetName().equals("d")) { //$NON-NLS-1$
          parameter.setD(linkdata[i].loadInitialValue() / scale);
        } else if (linkdata[i].loadTargetName().equals("a")) { //$NON-NLS-1$
          parameter.setA(linkdata[i].loadInitialValue() / scale);
        } else if (linkdata[i].loadTargetName().equals("alpha")) { //$NON-NLS-1$
          if (radian) {
            parameter.setAlpha(linkdata[i].loadInitialValue());
          } else {
            parameter.setAlpha(Math.toRadians(linkdata[i].loadInitialValue()));
          }
        }
      }
    }
    return parameter;
  }

  /**
   * 座標パラメータを返します。
   * 
   * @param linkData リンクデータ
   * @return 座標パラメータ
   */
  public static CoordinateParameter getCoordinateParameter(final LinkData[] linkData) {
    final CoordinateParameter parameter = new CoordinateParameter();

    // linkdataが無い場合はlinkdata.lengthが0になる
    for (int i = 0; i < linkData.length; i++) {
      // Constが存在する場合
      if (linkData[i].hasInitialValue()) {
        // かつ、targetがthetaであるか、dであるかを判別
        if (linkData[i].loadTargetName().equals("locationX")) { //$NON-NLS-1$
          parameter.setX(linkData[i].loadInitialValue() / scale);
        } else if (linkData[i].loadTargetName().equals("rotationX")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRationX(linkData[i].loadInitialValue());
          } else {
            parameter.setRationX(Math.toRadians(linkData[i].loadInitialValue()));
          }
        } else if (linkData[i].loadTargetName().equals("locationY")) { //$NON-NLS-1$
          parameter.setY(linkData[i].loadInitialValue() / scale);
        } else if (linkData[i].loadTargetName().equals("rotationY")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotationY(linkData[i].loadInitialValue());
          } else {
            parameter.setRotationY(Math.toRadians(linkData[i].loadInitialValue()));
          }
        } else if (linkData[i].loadTargetName().equals("locationZ")) { //$NON-NLS-1$
          parameter.setZ(linkData[i].loadInitialValue() / scale);
        } else if (linkData[i].loadTargetName().equals("rotationZ")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotationZ(linkData[i].loadInitialValue());
          } else {
            parameter.setRotationZ(Math.toRadians(linkData[i].loadInitialValue()));
          }
        }
      }
    }
    return parameter;
  }
}
