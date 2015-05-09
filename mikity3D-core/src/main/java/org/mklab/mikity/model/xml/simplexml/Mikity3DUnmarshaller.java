package org.mklab.mikity.model.xml.simplexml;

import java.io.File;
import java.io.InputStream;

import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


/**
 * Simpleを用いてMikity3Dモデルデータを読み込むクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class Mikity3DUnmarshaller {
  /** モデルデータのルート */
  protected Mikity3d root;
//  /** Colladaのグループ */
//  protected Collada collada;

  /**
   * 指定したMikity3DTファイルを読み込みます。
   * 
   * @param file Mikity3DTファイル
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  public void unmarshalFromMikity3DFile(File file) throws Mikity3dSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      this.root = serializer.read(org.mklab.mikity.model.xml.simplexml.Mikity3d.class, file);
    } catch (Exception e) {
      throw new Mikity3dSerializeDeserializeException(e);
    }
  }

  /**
   * 指定した入力ストリームからMikity3Dデータを読み込みます。
   * 
   * @param input 入力ストリーム
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  public void unmarshalFromMikity3DFile(InputStream input) throws Mikity3dSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      this.root = serializer.read(org.mklab.mikity.model.xml.simplexml.Mikity3d.class, input);
    } catch (Exception e) {
      throw new Mikity3dSerializeDeserializeException(e);
    }
  }
  
//  /**
//   * 指定したMikity3DTファイルを読み込みます。
//   * 
//   * @param file Mikity3DTファイル
//   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
//   */
//  @SuppressWarnings("resource")
//  public void unmarshalFromColladaFile(File file) throws Mikity3dSerializeDeserializeException {
//    try {
//      final ColladaFileTransformer transformer = new ColladaFileTransformer();
//      transformer.transform(new FileInputStream(file));
//      final InputStream blender = transformer.getTransformedStream();
//      final Serializer serializer = new Persister();
//      this.collada = serializer.read(org.mklab.mikity.model.xml.simplexml.blender.Collada.class, blender);
//    } catch (Exception e) {
//      throw new Mikity3dSerializeDeserializeException(e);
//    }
//  }
//
//  /**
//   * 指定した入力ストリームからMikity3Dデータを読み込みます。
//   * 
//   * @param input 入力ストリーム
//   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
//   */
//  public void unmarshalFromColladaFile(InputStream input) throws Mikity3dSerializeDeserializeException {
//    try {
//      final ColladaFileTransformer transformer = new ColladaFileTransformer();
//      transformer.transform(input);
//      @SuppressWarnings("resource")
//      final InputStream blender = transformer.getTransformedStream();
//      final Serializer serializer = new Persister();
//      this.collada = serializer.read(org.mklab.mikity.model.xml.simplexml.blender.Collada.class, blender);
//    } catch (Exception e) {
//      throw new Mikity3dSerializeDeserializeException(e);
//    }
//  }

//  /**
//   * Colladaのグループを返します。
//   * 
//   * @return Colladaのグループ
//   */
//  public Group getClolladaGroup() {
//    return this.collada.getColladaPolygonGroup();
//  }

  /**
   * 現在のモデルデータのルートを返します。
   * 
   * @return 現在のモデルデータのルート
   */
  public Mikity3d getRoot() {
    return this.root;
  }

}
