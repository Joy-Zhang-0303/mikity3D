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
import java.net.URL;
import java.util.ArrayList;

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
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
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
    checkFileType(file);
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
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 指定したJAMASTファイルを読み込む
   * 
   * @param file
   *        　読込JAMASTファイル
   * @return root
   * @throws IllegalArgumentException
   */
  public Jamast createJamast(File file) throws IllegalArgumentException {
    try {
      JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.xml.Jamast.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      return (Jamast)unmarshaller.unmarshal(new FileReader(file));
    } catch (JAXBException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
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
      e.printStackTrace();
    }
    return null;
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
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return belender group
   */
  public Group getBlenderGroup() {
    return this.collada.getBlenderPolygonGroup();
  }

  /**
   * 読み込むファイルの種類を判別する
   * 
   * @param file
   *        読込ファイル
   */
  private void checkFileType(File file) {
    try {
      FileReader fileIn = new FileReader(file);
      ArrayList<String> note = new ArrayList<String>();
      BufferedReader br = new BufferedReader(fileIn);
      for (int i = 0; i < 10; i++) {
        note.add(br.readLine());
      }
      if (note.get(0).indexOf("jamast") != -1) { //$NON-NLS-1$
        loadJamastScene(file);
      } else if (note.get(1).indexOf("collada") != -1) { //$NON-NLS-1$
        loadBlenderScene(file);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
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
