/*
 * Created on 2005/01/25
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;


/**
 * アイコンなどのリソースを管理するクラス
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/25
 */
public class ResourceManager {

  private static Map<String, Image> imageMap = new HashMap<String, Image>();

  /** 再生画像ファイル定数 */
  public static final String PLAYBACK = "playback.gif"; //$NON-NLS-1$
  /** 停止画像ファイル定数 */
  public static final String STOP = "stop.gif"; //$NON-NLS-1$
  /** 速度増画像ファイル定数 */
  public static final String FAST = "fast.gif"; //$NON-NLS-1$
  /** 速度減画像ファイル定数 */
  public static final String SLOW = "slow.gif"; //$NON-NLS-1$

  /**
   * リソース名で指定された画像を返します。 このリソースはクラスパス直下のresourceディレクトリ内を検索します。 リソースが登録されていない場合には新規にリソースを読み込み {@link Map}に登録します。 Mapに登録されている場合にはそのリソースが返されます。
   * 
   * @param key リソース名
   * @return リソース名で指定された画像
   */
  public static Image getImage(String key) {
    if (imageMap.containsKey(key)) {
      return imageMap.get(key);
    }
    try {
      Image img = new Image(null, ResourceManager.class.getResourceAsStream("/" + key)); //$NON-NLS-1$
      imageMap.put(key, img);
      return img;
    } catch (SWTException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * リソースを全て破棄する
   */
  public static void dispose() {
    for (Iterator<String> iter = imageMap.keySet().iterator(); iter.hasNext();) {
      Image key = imageMap.get(iter.next());
      if (!key.isDisposed()) {
        key.dispose();
      }
      imageMap.remove(key);
    }
  }

}