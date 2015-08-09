/*
 * Created on 2004/11/26
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 色彩を表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2004/11/26
 */
public class ColorConstants {
  /** 色の名前と色オブジェクトのマップ */
  private static final Map<String, ColorModel> COLOR_MAP = new HashMap<>();

  static {
    COLOR_MAP.put("white", new ColorModel(255, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("black", new ColorModel(0, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("red", new ColorModel(255, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("lightGray", new ColorModel(211, 211, 211)); //$NON-NLS-1$
    COLOR_MAP.put("darkGray", new ColorModel(169, 169, 169)); //$NON-NLS-1$
    COLOR_MAP.put("pink", new ColorModel(255, 192, 203)); //$NON-NLS-1$
    COLOR_MAP.put("orange", new ColorModel(255, 211, 0)); //$NON-NLS-1$
    COLOR_MAP.put("yellow", new ColorModel(255, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("green", new ColorModel(0, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("magenta", new ColorModel(255, 0, 255)); //$NON-NLS-1$
    COLOR_MAP.put("cyan", new ColorModel(0, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("blue", new ColorModel(0, 0, 255)); //$NON-NLS-1$
  }

  /**
   * 名前で指定した色を返します。
   * 
   * @param colorName 色の名前
   * @return 名前で指定された色
   */
  public static ColorModel getColor(String colorName) {
    if (COLOR_MAP.containsKey(colorName)) {
      return COLOR_MAP.get(colorName);
    }
    return new ColorModel(128, 128, 128);
  }
  
  /**
   * 色が登録されているか判定します。
   * @param color 色
   * @return 色が登録されていればtrue
   */
  public static boolean contain(ColorModel color) {
    return COLOR_MAP.containsValue(color);
  }
  
  /**
   * 色に対応する色の名前を返します。
   * 
   * @param color 色
   * @return 色の名前
   */
  public static String getColorName(ColorModel color) {
    for (Entry<String,ColorModel> entry : COLOR_MAP.entrySet()) {
      if (entry.getValue().equals(color)) {
        return entry.getKey();
      }
    }

    return null;
  }
}
