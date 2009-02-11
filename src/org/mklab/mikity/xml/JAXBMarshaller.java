/*
 * $Id: JAXBMarshaller.java,v 1.4 2008/01/18 08:02:19 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.mklab.mikity.xml.blender.Collada;
import org.mklab.mikity.xml.blender.ColladaFileTransformer;
import org.mklab.mikity.xml.model.Group;


/**
 * JAXBを用いてモデリングデータの保存・読込を行うクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class JAXBMarshaller {

  /**
   * 現在のモデリングデータのルート
   */
  private Jamast root;

  private Collada collada;

  private File f;

  /**
   * コンストラクタ
   */
  public JAXBMarshaller() {
  // nothing to do
  }

  /**
   * コンストラクタ
   * 
   * @param root
   *        　現在のモデリングデータのルート
   */
  public JAXBMarshaller(Jamast root) {
    this.root = root;
  }

  /**
   * 現在のモデリングデータを指定したファイルに保存する
   * 
   * @param file
   *        　保存ファイル
   * @throws IllegalArgumentException
   */
  public void marshal(File file) throws IllegalArgumentException {
    try {
      JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
      Marshaller marshaller = context.createMarshaller();
      marshaller.marshal(this.root, new FileWriter(file));
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 指定したファイルを読み込む
   * 
   * @param file
   *        　読込ファイル
   * @throws IllegalArgumentException
   */
  public void unmarshal(File file) throws IllegalArgumentException {
    loadScene(file);
  }

  /**
   * 指定したJAMASTファイルを読み込む
   * 
   * @param file
   *        　読込JAMASTファイル
   * @throws IllegalArgumentException
   */
  private void loadJamastScene(File file) throws IllegalArgumentException {
    try {
      JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      this.root = (Jamast)unmarshaller.unmarshal(new FileReader(file));
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * 指定したJAMASTファイルを読み込む
   * 
   * @param file
   *        　読込JAMASTファイル
   * @return root
   * @throws IllegalArgumentException
   * @throws JAXBException 
   * @throws FileNotFoundException 
   */
  public Jamast createJamast(File file) throws IllegalArgumentException, JAXBException, FileNotFoundException {
    JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
    Unmarshaller unmarshaller = context.createUnmarshaller();
    return (Jamast)unmarshaller.unmarshal(new FileReader(file));
  }
  
  /**
   * 指定したJAMASTファイルを読み込む
   * 
   * @param file
   *        　読込JAMASTファイル
   * @return root
   * @throws IllegalArgumentException
   */
  public Jamast createJamast(URL file) throws IllegalArgumentException {
    try {
      JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      return (Jamast)unmarshaller.unmarshal(file);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 指定したBlenderファイルを読み込む
   * 
   * @param file
   *        　読込Blenderファイル
   * @throws IllegalArgumentException
   */
  private void loadBlenderScene(File file) throws IllegalArgumentException {
    try {
      setLoadFile(file);
      ColladaFileTransformer cft = new ColladaFileTransformer(file);
      File blender = cft.getTransformedFile();
      JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.blender.Collada.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      this.collada = (Collada)unmarshaller.unmarshal(new FileReader(blender));
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @return belender group
   */
  public Group getBlenderGroup() {
    return this.collada.getBlenderPolygonGroup();
  }

  /**
   * 種類を判別し、ファイルを読み込む。
   * 
   * @param file
   *        読込ファイル
   */
  private void loadScene(File file) {
    try {
      final BufferedReader reader = new BufferedReader(new FileReader(file));
      final StringBuffer data = new StringBuffer();
      
      String line;
      while ((line = reader.readLine()) != null) {
        data.append(line);
      }

      if (data.indexOf("<jamast") != -1) { //$NON-NLS-1$
        loadJamastScene(file);
        return;
      } 
      if (data.indexOf("<collada") != -1 || data.indexOf("<COLLADA") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
        loadBlenderScene(file);
        return;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    throw new IllegalArgumentException("Neither jamast nor collada data"); //$NON-NLS-1$
  }

  /**
   * 現在のモデリングデータのルートを返す
   * 
   * @return 現在のモデリングデータのルート
   */
  public Jamast getRoot() {
    return this.root;
  }

  /**
   * @param f
   */
  private void setLoadFile(File f) {
    this.f = f;
  }

  /**
   * @return file
   */
  public File getLoadFile() {
    return this.f;
  }
}
