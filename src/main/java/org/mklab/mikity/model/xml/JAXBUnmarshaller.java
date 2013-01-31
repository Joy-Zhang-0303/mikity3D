package org.mklab.mikity.model.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.mklab.mikity.model.xml.blender.Collada;
import org.mklab.mikity.model.xml.blender.ColladaFileTransformer;
import org.mklab.mikity.model.xml.model.Group;

/**
 * JAXBを用いてモデルデータを読み込むクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2007/07/10
 */
public class JAXBUnmarshaller {
  /** モデルデータのルート */
  private Jamast root;

  /** Colladaのグループ */
  private Collada collada;

  /**
   * 指定したファイルを読み込みます。
   * 
   * @param file 読込ファイル
   * @throws JAXBException ファイルを読み込めない場合
   * @throws IOException ファイルを読み込めない場合
   */
  public void unmarshal(File file) throws IOException, JAXBException {
    final BufferedReader reader = new BufferedReader(new FileReader(file));
    final StringBuffer data = new StringBuffer();

    String line;
    while ((line = reader.readLine()) != null) {
      data.append(line);
    }
    reader.close();

    if (data.indexOf("<jamast") != -1) { //$NON-NLS-1$
      loadJamastFile(file);
      return;
    }

    if (data.indexOf("<collada") != -1 || data.indexOf("<COLLADA") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
      loadColladaFile(file);
      return;
    }

    throw new IllegalArgumentException("Neither jamast nor collada data"); //$NON-NLS-1$
  }

  /**
   * 指定したJAMASTファイルを読み込みます。
   * 
   * @param file JAMASTファイル
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  private void loadJamastFile(File file) throws JAXBException, IOException {
    final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.model.xml.Jamast.class);
    final Unmarshaller unmarshaller = context.createUnmarshaller();
    final FileReader reader = new FileReader(file);
    final Jamast jamast = (Jamast)unmarshaller.unmarshal(reader);
    reader.close();
    this.root = jamast;
  }

  /**
   * 指定したColladaファイルを読み込みます。
   * 
   * @param file Colladaファイル
   * @throws JAXBException ファイルがvalidでない場合
   * @throws IOException ファイルを読み込めない場合
   */
  private void loadColladaFile(File file) throws JAXBException, IOException {
    final ColladaFileTransformer transformer = new ColladaFileTransformer(file);
    final File blender = transformer.getTransformedFile();
    final JAXBContext context = JAXBContext.newInstance(org.mklab.mikity.model.xml.blender.Collada.class);
    final Unmarshaller unmarshaller = context.createUnmarshaller();
    final FileReader reader = new FileReader(blender);
    this.collada = (Collada)unmarshaller.unmarshal(reader);
    reader.close();
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
