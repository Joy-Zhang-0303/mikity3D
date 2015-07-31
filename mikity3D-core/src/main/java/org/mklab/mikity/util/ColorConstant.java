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
  private static final Map<String, Color3> COLOR_MAP = new HashMap<>();

  static {
    COLOR_MAP.put("white", new Color3(1, 1, 1)); //$NON-NLS-1$
    COLOR_MAP.put("WHITE", new Color3(1, 1, 1)); //$NON-NLS-1$
    COLOR_MAP.put("black", new Color3(0, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("BLACK", new Color3(0, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("red", new Color3(1, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("RED", new Color3(1, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("lightGray", new Color3(0.75f, 0.75f, 0.75f)); //$NON-NLS-1$
    COLOR_MAP.put("LIGHTGRAY", new Color3(0.75f, 0.75f, 0.75f)); //$NON-NLS-1$
    COLOR_MAP.put("darkGray", new Color3(0.25f, 0.25f, 0.25f)); //$NON-NLS-1$
    COLOR_MAP.put("DARKGRAY", new Color3(0.25f, 0.25f, 0.25f)); //$NON-NLS-1$
    COLOR_MAP.put("pink", new Color3(1, 0.69f, 0.69f)); //$NON-NLS-1$
    COLOR_MAP.put("PINK", new Color3(1, 0.69f, 0.69f)); //$NON-NLS-1$
    COLOR_MAP.put("orange", new Color3(1, 0.78f, 0)); //$NON-NLS-1$
    COLOR_MAP.put("ORANGE", new Color3(1, 0.78f, 0)); //$NON-NLS-1$
    COLOR_MAP.put("yellow", new Color3(1, 1, 0)); //$NON-NLS-1$
    COLOR_MAP.put("YELLOW", new Color3(1, 1, 0)); //$NON-NLS-1$
    COLOR_MAP.put("green", new Color3(0, 1, 0)); //$NON-NLS-1$
    COLOR_MAP.put("GREEN", new Color3(0, 1, 0)); //$NON-NLS-1$
    COLOR_MAP.put("magenta", new Color3(1, 0, 1)); //$NON-NLS-1$
    COLOR_MAP.put("MAGENTA", new Color3(1, 0, 1)); //$NON-NLS-1$
    COLOR_MAP.put("cyan", new Color3(0, 1, 1)); //$NON-NLS-1$
    COLOR_MAP.put("CYAN", new Color3(0, 1, 1)); //$NON-NLS-1$
    COLOR_MAP.put("blue", new Color3(0, 0, 1)); //$NON-NLS-1$
    COLOR_MAP.put("BLUE", new Color3(0, 0, 1)); //$NON-NLS-1$
  }

  /**
   * 名前で指定した色を返します。
   * 
   * @param colorName 色の名前
   * @return 名前で指定された色
   */
  public static Color3 getColor(String colorName) {
    if (COLOR_MAP.containsKey(colorName)) {
      return COLOR_MAP.get(colorName);
    }
    return new Color3(0.5f, 0.5f, 0.5f);
  }
}
