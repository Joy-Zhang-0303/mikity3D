/*
 * Created on 2004/11/26
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color3f;

/**
 * 色彩を表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2004/11/26
 */
public class ColorConstant {
  /** 色の名前と色オブジェクトのマップ */
  private final static Map<String, Color3f> colorMap = new HashMap<String, Color3f>();

  static {
    colorMap.put("white", new Color3f(255, 255, 255)); //$NON-NLS-1$
    colorMap.put("WHITE", new Color3f(255, 255, 255)); //$NON-NLS-1$
    colorMap.put("black", new Color3f(0, 0, 0)); //$NON-NLS-1$
    colorMap.put("BLACK", new Color3f(0, 0, 0)); //$NON-NLS-1$
    colorMap.put("red", new Color3f(255, 0, 0)); //$NON-NLS-1$
    colorMap.put("RED", new Color3f(255, 0, 0)); //$NON-NLS-1$
    colorMap.put("lightGray", new Color3f(192, 192, 192)); //$NON-NLS-1$
    colorMap.put("LIGHTGRAY", new Color3f(192, 192, 192)); //$NON-NLS-1$
    colorMap.put("darkGray", new Color3f(64, 64, 64)); //$NON-NLS-1$
    colorMap.put("DARKGRAY", new Color3f(64, 64, 64)); //$NON-NLS-1$
    colorMap.put("pink", new Color3f(255, 175, 175)); //$NON-NLS-1$
    colorMap.put("PINK", new Color3f(255, 175, 175)); //$NON-NLS-1$
    colorMap.put("orange", new Color3f(255, 200, 0)); //$NON-NLS-1$
    colorMap.put("ORANGE", new Color3f(255, 200, 0)); //$NON-NLS-1$
    colorMap.put("yellow", new Color3f(255, 255, 0)); //$NON-NLS-1$
    colorMap.put("YELLOW", new Color3f(255, 255, 0)); //$NON-NLS-1$
    colorMap.put("green", new Color3f(0, 255, 0)); //$NON-NLS-1$
    colorMap.put("GREEN", new Color3f(0, 255, 0)); //$NON-NLS-1$
    colorMap.put("magenta", new Color3f(255, 0, 255)); //$NON-NLS-1$
    colorMap.put("MAGENTA", new Color3f(255, 0, 255)); //$NON-NLS-1$
    colorMap.put("cyan", new Color3f(0, 255, 255)); //$NON-NLS-1$
    colorMap.put("CYAN", new Color3f(0, 255, 255)); //$NON-NLS-1$
    colorMap.put("blue", new Color3f(0, 0, 255)); //$NON-NLS-1$
    colorMap.put("BLUE", new Color3f(0, 0, 255)); //$NON-NLS-1$
  }

  /**
   * 名前で指定した色を返します。
   * 
   * @param colorName 色の名前
   * @return 名前で指定された色
   */
  public static Color3f getColor(String colorName) {
    if (colorMap.containsKey(colorName)) {
      return colorMap.get(colorName);
    }
    return new Color3f(128, 128, 128);
  }
}
