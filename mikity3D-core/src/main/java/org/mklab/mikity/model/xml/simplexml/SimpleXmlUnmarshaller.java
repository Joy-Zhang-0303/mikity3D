package org.mklab.mikity.model.xml.simplexml;

import java.io.File;
import java.io.InputStream;

import org.mklab.mikity.model.xml.AbstractMikity3DUnmarshaller;
import org.mklab.mikity.model.xml.ColladaFileTransformer;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.blender.Collada;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


/**
 * Simple-XMLを用いてモデルデータを読み込むクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class SimpleXmlUnmarshaller extends AbstractMikity3DUnmarshaller {

  /** モデルデータのルート */
  protected Mikity3d root;
  /** Colladaのグループ */
  protected Collada collada;

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  public void unmarshalFromColladaFile(File file) throws Mikity3dSerializeDeserializeException {
    try {
      final ColladaFileTransformer transformer = new ColladaFileTransformer(file);
      final File blender = transformer.getTransformedFile();
      final Serializer serializer = new Persister();
      this.collada = serializer.read(org.mklab.mikity.model.xml.simplexml.blender.Collada.class, blender);
    } catch (Exception e) {
      throw new Mikity3dSerializeDeserializeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void unmarshalFromJamastFile(InputStream input) throws Mikity3dSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      this.root = serializer.read(org.mklab.mikity.model.xml.simplexml.Mikity3d.class, input);
    } catch (Exception e) {
      throw new Mikity3dSerializeDeserializeException(e);
    }
  }
  
  /**
   * Colladaのグループを返します。
   * 
   * @return Colladaのグループ
   */
  public Group getClolladaGroup() {
    return this.collada.getColladaPolygonGroup();
  }

  /**
   * 現在のモデルデータのルートを返します。
   * 
   * @return 現在のモデルデータのルート
   */
  public Mikity3d getRoot() {
    return this.root;
  }
}
