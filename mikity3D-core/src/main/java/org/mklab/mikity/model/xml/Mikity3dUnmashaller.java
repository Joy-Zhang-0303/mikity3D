/*
 * Created on 2013/02/05
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Mikity3DのUnmashallerを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public interface Mikity3dUnmashaller {

  /**
   * 指定したファイルを読み込みます。
   * 
   * @param file 読込ファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshal(File file) throws IOException, Mikity3dSerializeDeserializeException;

  /**
   * 指定した入力ストリームから読み込みます。
   * 
   * @param input 入力ストリーム 
   * @throws IOException 入力ストリームから読み込めない場合
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshal(InputStream input) throws IOException, Mikity3dSerializeDeserializeException;

  /**
   * 指定したMikity3DTファイルを読み込みます。
   * 
   * @param file Mikity3DTファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshalFromMikity3DFile(File file) throws IOException, Mikity3dSerializeDeserializeException;
  
  /**
   * 指定した入力ストリームからMikity3Dデータを読み込みます。
   * 
   * @param input 入力ストリーム
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshalFromMikity3DFile(InputStream input) throws Mikity3dSerializeDeserializeException;
  
  /**
   * 指定したColladaファイルを読み込みます。
   * 
   * @param file Colladaファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshalFromColladaFile(File file) throws IOException, Mikity3dSerializeDeserializeException;
  
  /**
   * 指定した入力ストリームからColladaファイルを読み込みます。
   * 
   * @param input 入力ストリーム
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshalFromColladaFile(InputStream input) throws Mikity3dSerializeDeserializeException;

}