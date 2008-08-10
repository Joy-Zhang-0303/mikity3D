/*
 * Created on 2004/11/26
 * Copyright (C) 2004 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color3f;


/**
 * 色彩に関するクラス
 * 
 * @author miki
 * @version $Revision: 1.4 $.2004/11/26
 */
public class ColorConstant {

  /**
   * 白を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f white = new Color3f(new Color(255, 255, 255));

  /**
   * 白を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f WHITE = new Color3f(white);

  /**
   * ライトグレイを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f lightGray = new Color3f(new Color(192, 192, 192));

  /**
   * ライトグレイを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f LIGHT_GRAY = new Color3f(lightGray);

  /**
   * グレイを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f gray = new Color3f(new Color(128, 128, 128));

  /**
   * グレイを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f GRAY = new Color3f(gray);

  /**
   * ダークグレイを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f darkGray = new Color3f(new Color(64, 64, 64));

  /**
   * ダークグレイを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f DARK_GRAY = new Color3f(darkGray);

  /**
   * 黒を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f black = new Color3f(new Color(0, 0, 0));

  /**
   * 黒を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f BLACK = new Color3f(black);

  /**
   * 赤を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f red = new Color3f(new Color(255, 0, 0));

  /**
   * 赤を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f RED = new Color3f(red);

  /**
   * ピンクを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f pink = new Color3f(new Color(255, 175, 175));

  /**
   * ピンクを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f PINK = new Color3f(pink);

  /**
   * オレンジを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f orange = new Color3f(new Color(255, 200, 0));

  /**
   * オレンジを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f ORANGE = new Color3f(orange);

  /**
   * 黄を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f yellow = new Color3f(new Color(255, 255, 0));

  /**
   * 黄を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f YELLOW = new Color3f(yellow);

  /**
   * 緑を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f green = new Color3f(new Color(0, 255, 0));

  /**
   * 緑を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f GREEN = new Color3f(green);

  /**
   * マゼンタを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f magenta = new Color3f(new Color(255, 0, 255));

  /**
   * マゼンタを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f MAGENTA = new Color3f(magenta);

  /**
   * シアンを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f cyan = new Color3f(new Color(0, 255, 255));

  /**
   * シアンを表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f CYAN = new Color3f(cyan);

  /**
   * 青を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f blue = new Color3f(new Color(0, 0, 255));

  /**
   * 青を表します。デフォルトの sRGB 領域です。
   */
  public final static Color3f BLUE = new Color3f(blue);

  private final static Map<String, Color3f> colorMap = new HashMap<String, Color3f>();

  // staticをつけると一回しか実行されなくなる。
  static {
    colorMap.put("white", white); //$NON-NLS-1$
    colorMap.put("WHITE", white); //$NON-NLS-1$
    colorMap.put("black", black); //$NON-NLS-1$
    colorMap.put("BLACK", black); //$NON-NLS-1$
    colorMap.put("red", red); //$NON-NLS-1$
    colorMap.put("RED", red); //$NON-NLS-1$
    colorMap.put("lightGray", lightGray); //$NON-NLS-1$
    colorMap.put("LIGHTGRAY", lightGray); //$NON-NLS-1$
    colorMap.put("darkGray", darkGray); //$NON-NLS-1$
    colorMap.put("DARKGRAY", darkGray); //$NON-NLS-1$
    colorMap.put("pink", pink); //$NON-NLS-1$
    colorMap.put("PINK", pink); //$NON-NLS-1$
    colorMap.put("orange", orange); //$NON-NLS-1$
    colorMap.put("ORANGE", orange); //$NON-NLS-1$
    colorMap.put("yellow", yellow); //$NON-NLS-1$
    colorMap.put("YELLOW", yellow); //$NON-NLS-1$
    colorMap.put("green", green); //$NON-NLS-1$
    colorMap.put("GREEN", green); //$NON-NLS-1$
    colorMap.put("magenta", magenta); //$NON-NLS-1$
    colorMap.put("MAGENTA", magenta); //$NON-NLS-1$
    colorMap.put("cyan", cyan); //$NON-NLS-1$
    colorMap.put("CYAN", cyan); //$NON-NLS-1$
    colorMap.put("blue", blue); //$NON-NLS-1$
    colorMap.put("BLUE", blue); //$NON-NLS-1$
  }

  /**
   * 名前で指定した色を返す
   * 
   * @param colorName
   *        色の名前
   * @return 名前で指定された色
   */
  public static Color3f getColor(String colorName) {
    // colorNameが登録されているか調べる
    if (colorMap.containsKey(colorName)) {
      return colorMap.get(colorName);
    }
    // 登録されていなければ標準色(GRAY)にする
    return gray;
  }
}
