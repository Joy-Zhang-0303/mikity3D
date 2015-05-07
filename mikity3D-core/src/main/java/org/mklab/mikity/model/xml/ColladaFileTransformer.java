/*
 * $Id: ColladaFileTransformer.java,v 1.2 2007/11/30 11:26:40 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Blenderデータを読み込む際、ルート要素を小文字に変換するためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class ColladaFileTransformer {
  /** 読み込みファイル(変換後のものを書き込む別ファイル) */
  private File transformedFile = new File("registry.xml"); //$NON-NLS-1$

  /**
   * ルート要素を小文字に変換し，各行ごとに内容をリストに追加します。
   * @param file 変換対象のファイル
   */
  public void transform(File file) {
    final List<String> contents = new ArrayList<>();
    
    try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
    
    try (final FileWriter writer = new FileWriter(this.transformedFile)) {
      for (int i = 0; i < contents.size(); i++) {
        writer.write(contents.get(i) + "\n"); //$NON-NLS-1$
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

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
    
    try (final FileWriter writer = new FileWriter(this.transformedFile)) {
      for (int i = 0; i < contents.size(); i++) {
        writer.write(contents.get(i) + "\n"); //$NON-NLS-1$
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 変換後のファイルを返します。
   * 
   * @return 変換後のファイル
   */
  public File getTransformedFile() {
    return this.transformedFile;
  }
}
