/*
 * $Id: JAXBMarshaller.java,v 1.4 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * JAXBを用いてモデルデータを保存するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class JAXBMarshaller {
  /** モデルデータのルート */
  private Jamast root;

  /**
   * 新しく生成された<code>JAXBMarshaller</code>オブジェクトを初期化します。
   * @param root モデルデータのルート
   */
  public JAXBMarshaller(Jamast root) {
    this.root = root;
  }

  /**
   * 現在のモデルデータを指定したファイルに保存します。
   * 
   * @param file 保存ファイル
   * @throws JAXBException ファイルに保存できない場合
   * @throws IOException ファイルに保存できない場合
   * @throws IllegalArgumentException 例外
   */
  public void marshal(File file) throws JAXBException, IOException {
    final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
    final Marshaller marshaller = context.createMarshaller();
    final FileWriter writer = new FileWriter(file);
    marshaller.marshal(this.root, writer);
    writer.close();
  }
}
