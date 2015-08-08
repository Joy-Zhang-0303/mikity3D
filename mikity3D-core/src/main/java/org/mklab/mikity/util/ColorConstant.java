/*
 * Created on 2004/11/26
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
    COLOR_MAP.put("black", new Color4(0, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("red", new Color4(255, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("lightGray", new Color4(211, 211, 211)); //$NON-NLS-1$
    COLOR_MAP.put("darkGray", new Color4(169, 169, 169)); //$NON-NLS-1$
    COLOR_MAP.put("pink", new Color4(255, 192, 203)); //$NON-NLS-1$
    COLOR_MAP.put("orange", new Color4(255, 211, 0)); //$NON-NLS-1$
    COLOR_MAP.put("yellow", new Color4(255, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("green", new Color4(0, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("magenta", new Color4(255, 0, 255)); //$NON-NLS-1$
    COLOR_MAP.put("cyan", new Color4(0, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("blue", new Color4(0, 0, 255)); //$NON-NLS-1$
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
  
  /**
   * 色が登録されているか判定します。
   * @param color 色
   * @return 色が登録されていればtrue
   */
  public static boolean contain(Color4 color) {
    return COLOR_MAP.containsValue(color);
  }
  
  /**
   * 色に対応する色の名前を返します。
   * 
   * @param color 色
   * @return 色の名前
   */
  public static String getColorName(Color4 color) {
    for (Entry<String,Color4> entry : COLOR_MAP.entrySet()) {
      if (entry.getValue().equals(color)) {
        return entry.getKey();
      }
    }

    return null;
  }
}
