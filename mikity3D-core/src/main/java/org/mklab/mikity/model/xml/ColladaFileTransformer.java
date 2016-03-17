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
package org.mklab.mikity.model.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Blenderデータを読み込む際、ルート要素を小文字に変換するためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class ColladaFileTransformer {
  /** 変換結果のストリーム */
  private InputStream transformedStream;

  /**
   * ルート要素を小文字に変換し，各行ごとに内容をリストに追加します。
   * @param input 変換対象の入力ストリーム
   */
  public void transform(InputStream input) {
    final List<String> contents = new ArrayList<>();
    
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.indexOf("<COLLADA") != -1) { //$NON-NLS-1$
          contents.add("<collada>"); //$NON-NLS-1$
        } else if (line.indexOf("</COLLADA") != -1) { //$NON-NLS-1$
          contents.add("</collada>"); //$NON-NLS-1$
        } else {
          contents.add(line);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    
    try (final Writer writer = new OutputStreamWriter(output)) {
      for (int i = 0; i < contents.size(); i++) {
        writer.write(contents.get(i) + "\n"); //$NON-NLS-1$
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    this.transformedStream = new ByteArrayInputStream(output.toByteArray());
  }

  /**
   * 変換結果のストリームを返します。
   * @return 変換結果のストリーム
   */
  public InputStream getTransformedStream() {
    return this.transformedStream;
  }
}
