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
  private static final Map<String, Color4> COLOR_MAP = new HashMap<>();

  static {
    COLOR_MAP.put("white", new Color4(255, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("WHITE", new Color4(255, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("black", new Color4(0, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("BLACK", new Color4(0, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("red", new Color4(255, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("RED", new Color4(255, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("lightGray", new Color4(211, 211, 211)); //$NON-NLS-1$
    COLOR_MAP.put("LIGHTGRAY", new Color4(211, 211, 211)); //$NON-NLS-1$
    COLOR_MAP.put("darkGray", new Color4(169, 169, 169)); //$NON-NLS-1$
    COLOR_MAP.put("DARKGRAY", new Color4(169, 169, 169)); //$NON-NLS-1$
    COLOR_MAP.put("pink", new Color4(255, 192, 203)); //$NON-NLS-1$
    COLOR_MAP.put("PINK", new Color4(255, 192, 203)); //$NON-NLS-1$
    COLOR_MAP.put("orange", new Color4(255, 211, 0)); //$NON-NLS-1$
    COLOR_MAP.put("ORANGE", new Color4(255, 211, 0)); //$NON-NLS-1$
    COLOR_MAP.put("yellow", new Color4(255, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("YELLOW", new Color4(255, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("green", new Color4(0, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("GREEN", new Color4(0, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("magenta", new Color4(255, 0, 255)); //$NON-NLS-1$
    COLOR_MAP.put("MAGENTA", new Color4(255, 0, 255)); //$NON-NLS-1$
    COLOR_MAP.put("cyan", new Color4(0, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("CYAN", new Color4(0, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("blue", new Color4(0, 0, 255)); //$NON-NLS-1$
    COLOR_MAP.put("BLUE", new Color4(0, 0, 255)); //$NON-NLS-1$
  }

  /**
   * 名前で指定した色を返します。
   * 
   * @param colorName 色の名前
   * @return 名前で指定された色
   */
  public static Color4 getColor(String colorName) {
    if (COLOR_MAP.containsKey(colorName)) {
      return COLOR_MAP.get(colorName);
    }
    return new Color4(128, 128, 128);
  }
}
