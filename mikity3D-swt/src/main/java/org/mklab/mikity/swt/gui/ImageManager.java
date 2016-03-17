/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.swt.gui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;


/**
 * 画像を管理するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/25
 */
public class ImageManager {

  private static Map<String, Image> images = new HashMap<>();

  /** 再生画像ファイル定数 */
  public static final String PLAYBACK = "playback.gif"; //$NON-NLS-1$
  /** 停止画像ファイル定数 */
  public static final String STOP = "stop.gif"; //$NON-NLS-1$
  /** 速度増画像ファイル定数 */
  public static final String FASTER = "fast.gif"; //$NON-NLS-1$
  /** 速度減画像ファイル定数 */
  public static final String SLOW = "slow.gif"; //$NON-NLS-1$

  /**
   * リソース名で指定された画像を返します。
   * 
   * このリソースはクラスパス直下のresourceディレクトリ内を検索します。 リソースが登録されていない場合には新規にリソースを読み込み {@link Map}に登録します。 Mapに登録されている場合にはそのリソースが返されます。
   * 
   * @param name リソース名
   * @return リソース名で指定された画像
   */
  public static Image getImage(String name) {
    if (images.containsKey(name)) {
      return images.get(name);
    }

    final Image image = new Image(null, ImageManager.class.getResourceAsStream("/" + name)); //$NON-NLS-1$
    images.put(name, image);
    return image;
  }

  /**
   * リソースを全て破棄します。
   */
  public static void dispose() {
    for (final String name : images.keySet()) {
      final Image image = images.get(name);
      if (image.isDisposed() == false) {
        image.dispose();
      }
      images.remove(name);
    }
  }

}