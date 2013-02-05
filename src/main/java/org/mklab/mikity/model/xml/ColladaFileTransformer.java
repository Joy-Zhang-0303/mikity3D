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
import java.util.ArrayList;
import java.util.List;


/**
 * Blenderデータを読み込む際、ルート要素を小文字に変換するためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class ColladaFileTransformer {

  /** 読み込みファイル(変換前)　*/
  private File fileBefore;
  /** 読み込みファイル(変換後のものを書き込む別ファイル) */
  private File fileAfter;
  /** 変換後の内容を各行ごとにまとめたリスト */
  private List<String> note;

  /**
   * コンストラクタ
   * 
   * @param file 読み込みファイル
   */
  public ColladaFileTransformer(File file) {
    this.fileBefore = file;
    this.note = new ArrayList<String>();
    this.fileAfter = new File("registry.xml"); //$NON-NLS-1$
  }

  /**
   * ルート要素に関する部分のみ内容を変換する。 変換する市内にかかわらず、各行ごとに内容をリストに追加する。
   */
  private void transformFile() {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(this.fileBefore));
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.indexOf("<COLLADA") != -1) { //$NON-NLS-1$
          this.note.add("<collada>"); //$NON-NLS-1$
        } else if (line.indexOf("</COLLADA") != -1) { //$NON-NLS-1$
          this.note.add("</collada>"); //$NON-NLS-1$
        } else {
          this.note.add(line);
        }
      }
      reader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 変換後の内容をファイルに書き込む。
   */
  private void writeTransformedFile() {
    try {
      FileWriter writer = new FileWriter(this.fileAfter);
      for (int i = 0; i < this.note.size(); i++) {
        writer.write(this.note.get(i) + "\n"); //$NON-NLS-1$
      }
      writer.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 変換後のファイルを返す
   * 
   * @return fileAfter　変換後のファイル
   */
  public File getTransformedFile() {
    transformFile();
    writeTransformedFile();
    return this.fileAfter;
  }
}
