package org.mklab.mikity.model.xml.jaxb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.mklab.mikity.model.xml.AbstractJamastUnmarshaller;
import org.mklab.mikity.model.xml.ColladaFileTransformer;
import org.mklab.mikity.model.xml.JamastSerializeDeserializeException;
import org.mklab.mikity.model.xml.jaxb.blender.Collada;
import org.mklab.mikity.model.xml.jaxb.model.Group;


/**
 * JAXBを用いてモデルデータを読み込むクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class JAXBUnmarshaller extends AbstractJamastUnmarshaller {
  /** モデルデータのルート */
  protected Jamast root;
  /** Colladaのグループ */
  protected Collada collada;
  
  /**
   * {@inheritDoc}
   */
  public void unmarshalFromJamastFile(File file) throws IOException, JamastSerializeDeserializeException {
    try {
      final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.model.xml.jaxb.Jamast.class);
      final Unmarshaller unmarshaller = context.createUnmarshaller();
      final FileReader reader = new FileReader(file);
      final Jamast jamast = (Jamast)unmarshaller.unmarshal(reader);
      reader.close();
      this.root = jamast;
    } catch (JAXBException e) {
      throw new JamastSerializeDeserializeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void unmarshalFromColladaFile(File file) throws IOException, JamastSerializeDeserializeException {
    try {
      final ColladaFileTransformer transformer = new ColladaFileTransformer(file);
      final File blender = transformer.getTransformedFile();
      final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.model.xml.jaxb.blender.Collada.class);
      final Unmarshaller unmarshaller = context.createUnmarshaller();
      final FileReader reader = new FileReader(blender);
      this.collada = (Collada)unmarshaller.unmarshal(reader);
      reader.close();
    } catch (JAXBException e) {
      throw new JamastSerializeDeserializeException(e);
    }
  }

  /**
   * Colladaのグループを返します。
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
