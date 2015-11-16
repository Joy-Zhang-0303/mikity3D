package org.mklab.mikity.model.xml.simplexml;

import java.io.File;
import java.io.OutputStream;

import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Simpleを用いてMikity3Dモデルデータを保存するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class Mikity3DMarshaller {
  /** モデルデータのルート */
  private Mikity3DModel root;

  /**
   * 新しく生成された<code>Mikity3DMarshaller</code>オブジェクトを初期化します。
   * @param root モデルデータのルート
   */
  public Mikity3DMarshaller(Mikity3DModel root) {
    this.root = root;
  }

  /**
   * 現在のモデルデータを指定したファイルに保存します。
   * 
   * @param file 保存ファイル
   * @throws Mikity3dSerializeDeserializeException Marshalできない場合
   */
  public void marshal(File file) throws Mikity3dSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      serializer.write(this.root, file);
    } catch (Exception e) {
      throw new Mikity3dSerializeDeserializeException(e);
    }
  }
  
  /**
   * 現在のモデルデータを指定した出力ストリームに出力します。
   * 
   * @param output 出力ストリーム
   * @throws Mikity3dSerializeDeserializeException Marshalできない場合
   */
  public void marshal(OutputStream output) throws Mikity3dSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      serializer.write(this.root, output);
    } catch (Exception e) {
      throw new Mikity3dSerializeDeserializeException(e);
    }
  }

}
