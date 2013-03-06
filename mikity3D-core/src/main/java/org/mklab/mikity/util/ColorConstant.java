/*
 * Created on 2004/11/26
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 色彩を表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2004/11/26
 */
public class ColorConstant {
  /** 色の名前と色オブジェクトのマップ */
  private final static Map<String, Color3> colorMap = new HashMap<String, Color3>();

  static {
    colorMap.put("white", new Color3(255, 255, 255)); //$NON-NLS-1$
    colorMap.put("WHITE", new Color3(255, 255, 255)); //$NON-NLS-1$
    colorMap.put("black", new Color3(0, 0, 0)); //$NON-NLS-1$
    colorMap.put("BLACK", new Color3(0, 0, 0)); //$NON-NLS-1$
    colorMap.put("red", new Color3(255, 0, 0)); //$NON-NLS-1$
    colorMap.put("RED", new Color3(255, 0, 0)); //$NON-NLS-1$
    colorMap.put("lightGray", new Color3(192, 192, 192)); //$NON-NLS-1$
    colorMap.put("LIGHTGRAY", new Color3(192, 192, 192)); //$NON-NLS-1$
    colorMap.put("darkGray", new Color3(64, 64, 64)); //$NON-NLS-1$
    colorMap.put("DARKGRAY", new Color3(64, 64, 64)); //$NON-NLS-1$
    colorMap.put("pink", new Color3(255, 175, 175)); //$NON-NLS-1$
    colorMap.put("PINK", new Color3(255, 175, 175)); //$NON-NLS-1$
    colorMap.put("orange", new Color3(255, 200, 0)); //$NON-NLS-1$
    colorMap.put("ORANGE", new Color3(255, 200, 0)); //$NON-NLS-1$
    colorMap.put("yellow", new Color3(255, 255, 0)); //$NON-NLS-1$
    colorMap.put("YELLOW", new Color3(255, 255, 0)); //$NON-NLS-1$
    colorMap.put("green", new Color3(0, 255, 0)); //$NON-NLS-1$
    colorMap.put("GREEN", new Color3(0, 255, 0)); //$NON-NLS-1$
    colorMap.put("magenta", new Color3(255, 0, 255)); //$NON-NLS-1$
    colorMap.put("MAGENTA", new Color3(255, 0, 255)); //$NON-NLS-1$
    colorMap.put("cyan", new Color3(0, 255, 255)); //$NON-NLS-1$
    colorMap.put("CYAN", new Color3(0, 255, 255)); //$NON-NLS-1$
    colorMap.put("blue", new Color3(0, 0, 255)); //$NON-NLS-1$
    colorMap.put("BLUE", new Color3(0, 0, 255)); //$NON-NLS-1$
  }

  /**
   * 名前で指定した色を返します。
   * 
   * @param colorName 色の名前
   * @return 名前で指定された色
   */
  public static Color3 getColor(String colorName) {
    if (colorMap.containsKey(colorName)) {
      return colorMap.get(colorName);
    }
    return new Color3(128, 128, 128);
  }
}
