/*
 * Created on 2005/02/05
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import org.mklab.mikity.java3d.Java3dModelCanvas;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.xml.model.LinkData;

/**
 * ユーティリティクラスです。
 * 
 * @author miki
 * @version $Revision: 1.7 $.2005/02/05
 */
public class Util {

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
    int scale = Java3dModelCanvas.scale;
    boolean radian = Java3dModelCanvas.radian;
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
    int scale = Java3dModelCanvas.scale;
    boolean radian = Java3dModelCanvas.radian;
    CoordinateParameter parameter = new CoordinateParameter();
    
    // linkdataが無い場合はlinkdata.lengthが0になる
    for (int i = 0; i < linkData.length; i++) {
      // Constが存在する場合
      if (linkData[i].hasInitialValue()) {
        // かつ、targetがthetaであるか、dであるかを判別
        if (linkData[i].loadTargetName().equals("locationX")) { //$NON-NLS-1$
          parameter.setLocX(linkData[i].loadInitialValue() / scale);
        } else if (linkData[i].loadTargetName().equals("rotationX")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotX(linkData[i].loadInitialValue());
          } else {
            parameter.setRotX(Math.toRadians(linkData[i].loadInitialValue()));
          }
        } else if (linkData[i].loadTargetName().equals("locationY")) { //$NON-NLS-1$
          parameter.setLocY(linkData[i].loadInitialValue() / scale);
        } else if (linkData[i].loadTargetName().equals("rotationY")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotY(linkData[i].loadInitialValue());
          } else {
            parameter.setRotY(Math.toRadians(linkData[i].loadInitialValue()));
          }
        } else if (linkData[i].loadTargetName().equals("locationZ")) { //$NON-NLS-1$
          parameter.setLocZ(linkData[i].loadInitialValue() / scale);
        } else if (linkData[i].loadTargetName().equals("rotationZ")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotZ(linkData[i].loadInitialValue());
          } else {
            parameter.setRotZ(Math.toRadians(linkData[i].loadInitialValue()));
          }
        }
      }
    }
    return parameter;
  }

  /**
   * パラメータを返します。
   * 
   * @param linkdata リンクデータ
   * @param key key
   * @return null
   */
  public static LinkData getParameter(final LinkData[] linkdata, final String key) {
    for (int i = 0; i < linkdata.length; i++) {
      if (linkdata[i].equals(key)) {
        return linkdata[i];
      }
    }
    return null;
  }
}
