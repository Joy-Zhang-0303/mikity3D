package org.mklab.mikity.model.xml.simplexml;

import java.io.File;

import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Simpleを用いてモデルデータを保存するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class SimpleXmlMarshaller {
  /** モデルデータのルート */
  private Mikity3d root;

  /**
   * 新しく生成された<code>JAXBMarshaller</code>オブジェクトを初期化します。
   * @param root モデルデータのルート
   */
  public SimpleXmlMarshaller(Mikity3d root) {
    this.root = root;
  }

  /**
   * 現在のモデルデータを指定したファイルに保存します。
   * 
   * @param file 保存ファイル
   * @throws Mikity3dSerializeDeserializeException Marshaleできない場合
   */
  public void marshal(File file) throws Mikity3dSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      serializer.write(this.root, file);
    } catch (Exception e) {
      throw new Mikity3dSerializeDeserializeException(e);
    }
  }
}
