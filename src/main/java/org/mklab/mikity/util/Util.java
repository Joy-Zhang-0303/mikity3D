/*
 * Created on 2005/02/05
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import org.mklab.mikity.java3d.Java3dModelCanvas;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.xml.model.Linkdata;

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
  public static DHParameter getDHParameter(final Linkdata[] linkdata) {
    int scale = Java3dModelCanvas.scale;
    boolean radian = Java3dModelCanvas.radian;
    DHParameter parameter = new DHParameter(); // 初期値全てゼロのDHParameter
    // linkdataが無い場合はlinkdata.lengthが0になる
    for (int i = 0; i < linkdata.length; i++) {
      // Constが存在する場合
      if (linkdata[i].hasConst()) {
        // かつ、targetがthetaであるか、dであるかを判別
        if (linkdata[i].loadTarget().equals("theta")) { //$NON-NLS-1$
          if (radian) {
            parameter.setTheta(linkdata[i].loadConst());
          } else {
            parameter.setTheta(Math.toRadians(linkdata[i].loadConst()));
          }
        } else if (linkdata[i].loadTarget().equals("d")) { //$NON-NLS-1$
          parameter.setD(linkdata[i].loadConst() / scale);
        } else if (linkdata[i].loadTarget().equals("a")) { //$NON-NLS-1$
          parameter.setA(linkdata[i].loadConst() / scale);
        } else if (linkdata[i].loadTarget().equals("alpha")) { //$NON-NLS-1$
          if (radian) {
            parameter.setAlpha(linkdata[i].loadConst());
          } else {
            parameter.setAlpha(Math.toRadians(linkdata[i].loadConst()));
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
  public static CoordinateParameter getCoordinateParameter(final Linkdata[] linkData) {
    int scale = Java3dModelCanvas.scale;
    boolean radian = Java3dModelCanvas.radian;
    CoordinateParameter parameter = new CoordinateParameter();
    
    // linkdataが無い場合はlinkdata.lengthが0になる
    for (int i = 0; i < linkData.length; i++) {
      // Constが存在する場合
      if (linkData[i].hasConst()) {
        // かつ、targetがthetaであるか、dであるかを判別
        if (linkData[i].loadTarget().equals("locationX")) { //$NON-NLS-1$
          parameter.setLocX(linkData[i].loadConst() / scale);
        } else if (linkData[i].loadTarget().equals("rotationX")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotX(linkData[i].loadConst());
          } else {
            parameter.setRotX(Math.toRadians(linkData[i].loadConst()));
          }
        } else if (linkData[i].loadTarget().equals("locationY")) { //$NON-NLS-1$
          parameter.setLocY(linkData[i].loadConst() / scale);
        } else if (linkData[i].loadTarget().equals("rotationY")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotY(linkData[i].loadConst());
          } else {
            parameter.setRotY(Math.toRadians(linkData[i].loadConst()));
          }
        } else if (linkData[i].loadTarget().equals("locationZ")) { //$NON-NLS-1$
          parameter.setLocZ(linkData[i].loadConst() / scale);
        } else if (linkData[i].loadTarget().equals("rotationZ")) { //$NON-NLS-1$
          if (radian) {
            parameter.setRotZ(linkData[i].loadConst());
          } else {
            parameter.setRotZ(Math.toRadians(linkData[i].loadConst()));
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
  public static Linkdata getParameter(final Linkdata[] linkdata, final String key) {
    for (int i = 0; i < linkdata.length; i++) {
      if (linkdata[i].equals(key)) {
        return linkdata[i];
      }
    }
    return null;
  }
}
