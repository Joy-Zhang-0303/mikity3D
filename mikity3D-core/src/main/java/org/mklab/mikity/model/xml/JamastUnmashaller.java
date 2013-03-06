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
 * JamstのUnmashallerを表すインターフェースです。
 * 
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public interface JamastUnmashaller {

  /**
   * 指定したファイルを読み込みます。
   * 
   * @param file 読込ファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws JamastSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshal(File file) throws IOException, JamastSerializeDeserializeException;

  /**
   * 指定したJAMASTファイルを読み込みます。
   * 
   * @param file JAMASTファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws JamastSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshalFromJamastFile(File file) throws IOException, JamastSerializeDeserializeException;
  
  /**
   * 指定した入力ストリームからJAMASTデータを読み込みます。
   * 
   * @param input 入力ストリーム
   * @throws JamastSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshalFromJamastFile(InputStream input) throws JamastSerializeDeserializeException;
  
  /**
   * 指定したColladaファイルを読み込みます。
   * 
   * @param file Colladaファイル
   * @throws IOException ファイルを読み込めない場合
   * @throws JamastSerializeDeserializeException Unmarshalできない場合
   */
  void unmarshalFromColladaFile(File file) throws IOException, JamastSerializeDeserializeException;
}