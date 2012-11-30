/*
 * $Id: JAXBMarshaller.java,v 1.4 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.mklab.mikity.xml.blender.Collada;
import org.mklab.mikity.xml.blender.ColladaFileTransformer;
import org.mklab.mikity.xml.model.Group;

/**
 * JAXBを用いてモデルデータの保存・読込を行うクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class JAXBMarshaller {

  /** モデルデータのルート */
  private Jamast root;

  private Collada collada;

  /**
   * コンストラクタ
   */
  public JAXBMarshaller() {
    // nothing to do
  }

  /**
   * コンストラクタ
   * 
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
    final FileWriter write = new FileWriter(file);
    marshaller.marshal(this.root, write);
    write.close();
  }

  /**
   * 指定したファイルを読み込みます。
   * 
   * @param file 読込ファイル
   * @throws JAXBException ファイルを読み込めない場合
   * @throws IOException ファイルを読み込めない場合
   */
  public void unmarshal(File file) throws IOException, JAXBException {
    loadScene(file);
  }

  /**
   * 指定したJAMASTファイルを読み込みます。
   * 
   * @param file JAMASTファイル
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  private void loadJamastFile(File file) throws JAXBException, IOException {
    final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
    final Unmarshaller unmarshaller = context.createUnmarshaller();
    final FileReader reader = new FileReader(file);
    final Jamast jamast = (Jamast)unmarshaller.unmarshal(reader);
    reader.close();
    this.root = jamast;
  }

  /**
   * 指定したJAMASTファイルを読み込みます。
   * 
   * @param file JAMASTファイル
   * @return root
   * @throws JAXBException 例外
   * @throws IOException ファイルの読み込みに失敗した場合
   *
   */
  public Jamast createJamast(File file) throws JAXBException, IOException {
    final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
    final Unmarshaller unmarshaller = context.createUnmarshaller();
    final FileReader reader = new FileReader(file);
    final Jamast jamast = (Jamast)unmarshaller.unmarshal(reader);
    reader.close();
    return jamast;
  }

  /**
   * 指定したBlenderファイルを読み込みます。
   * 
   * @param file Blenderファイル
   * @throws JAXBException ファイルがvalidでない場合
   * @throws IOException ファイルを読み込めない場合
   */
  private void loadBlenderFile(File file) throws JAXBException, IOException {
    //setLoadFile(file);
    final ColladaFileTransformer transformer = new ColladaFileTransformer(file);
    final File blender = transformer.getTransformedFile();
    final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.blender.Collada.class);
    final Unmarshaller unmarshaller = context.createUnmarshaller();
    final FileReader reader = new FileReader(blender);
    this.collada = (Collada)unmarshaller.unmarshal(reader);
    reader.close();
  }

  /**
   * @return belender group
   */
  public Group getClolladaGroup() {
    return this.collada.getColladaPolygonGroup();
  }

  /**
   * 種類を判別し、ファイルを読み込む。
   * 
   * @param file 読込ファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws JAXBException ファイルを読み込めない場合
   */
  private void loadScene(File file) throws IOException, JAXBException {
    final BufferedReader reader = new BufferedReader(new FileReader(file));
    final StringBuffer data = new StringBuffer();

    String line;
    while ((line = reader.readLine()) != null) {
      data.append(line);
    }
    reader.close();

    if (data.indexOf("<jamast") != -1) { //$NON-NLS-1$
      loadJamastFile(file);
      return;
    }

    if (data.indexOf("<collada") != -1 || data.indexOf("<COLLADA") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
      loadBlenderFile(file);
      return;
    }

    throw new IllegalArgumentException("Neither jamast nor collada data"); //$NON-NLS-1$
  }

  /**
   * 現在のモデルデータのルートを返します。
   * 
   * @return 現在のモデルデータのルート
   */
  public Jamast getRoot() {
    return this.root;
  }
}
