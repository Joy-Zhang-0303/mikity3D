package org.mklab.mikity.model.xml.simplexml;

import java.io.File;
import java.io.InputStream;

import org.mklab.mikity.model.xml.AbstractJamastUnmarshaller;
import org.mklab.mikity.model.xml.ColladaFileTransformer;
import org.mklab.mikity.model.xml.JamastSerializeDeserializeException;
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
public class SimpleXmlUnmarshaller extends AbstractJamastUnmarshaller {

  /** モデルデータのルート */
  protected Jamast root;
  /** Colladaのグループ */
  protected Collada collada;

  /**
   * {@inheritDoc}
   */
  public void unmarshalFromJamastFile(File file) throws JamastSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      this.root = serializer.read(org.mklab.mikity.model.xml.simplexml.Jamast.class, file);
    } catch (Exception e) {
      throw new JamastSerializeDeserializeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void unmarshalFromColladaFile(File file) throws JamastSerializeDeserializeException {
    try {
      final ColladaFileTransformer transformer = new ColladaFileTransformer(file);
      final File blender = transformer.getTransformedFile();
      final Serializer serializer = new Persister();
      this.collada = serializer.read(org.mklab.mikity.model.xml.simplexml.blender.Collada.class, blender);
    } catch (Exception e) {
      throw new JamastSerializeDeserializeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void unmarshalFromJamastFile(InputStream input) throws JamastSerializeDeserializeException {
    try {
      final Serializer serializer = new Persister();
      this.root = serializer.read(org.mklab.mikity.model.xml.simplexml.Jamast.class, input);
    } catch (Exception e) {
      throw new JamastSerializeDeserializeException(e);
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
  public Jamast getRoot() {
    return this.root;
  }
}
