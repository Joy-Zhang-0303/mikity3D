/*
 * Created on 2015/11/19
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.model.CompositionModel;
import org.mklab.mikity.model.xml.simplexml.model.FacetModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.VertexModel;


/**
 * STL(Stereolithography)を表すクラスです。
 *  
 * @author koga
 * @version $Revision$, 2015/11/19
 */
public class STL {
  /** ヘッダー。 */
  private byte[] header = new byte[80]; 
  
  /** 三角形の数。 */
  private int size;
  
  /** 三角形のデータ。 */
  private Facet[] facets;
  
  /** 名前。 */
  private String name = "stl"; //$NON-NLS-1$
  
  /**
   * 入力ストリームからバイナリ形式のデータを読み込みます。
   * 
   * @param input 入力
   * @return STL
   * @throws IOException データを読み込めない場合
   */
  public static STL loadBinaryData(InputStream input) throws IOException {
    final STL stl = new STL();
    
    try (final DataInputStream dataInput = new DataInputStream(new BufferedInputStream(input))) {
      dataInput.read(stl.header, 0, 80);
      stl.size = Integer.reverseBytes(dataInput.readInt());
      stl.facets = new Facet[stl.size];
      for (int i = 0; i < stl.size; i++) {
        stl.facets[i] = Facet.loadBinaryData(dataInput);
      }
    }
    
    return stl; 
  }

  /**
   * Readerからテキスト形式のデータを読み込みます。
   * 
   * @param input 入力
   * @return STL
   * @throws IOException データを読み込めない場合
   */
  public static STL loadTextData(Reader input) throws IOException {
    final STL stl = new STL();
    
    final BufferedReader reader = new BufferedReader(input);
    final String firstLine = reader.readLine();
    if (firstLine.startsWith("solid ") == false) { //$NON-NLS-1$
      throw new IOException("The first line must start with 'solid'"); //$NON-NLS-1$
    }
    final String[] firstLineWords = firstLine.split(" "); //$NON-NLS-1$
    if (firstLineWords.length != 2) {
      throw new IOException("The first line must have 'solid' and 'name'"); //$NON-NLS-1$
    }
    
    stl.name = firstLineWords[1];
    
    final List<Facet> facetList = new ArrayList<>();
    
    do {
      reader.mark(100);
      final String nextLine = reader.readLine();
      if (nextLine.startsWith("endsolid")) { //$NON-NLS-1$
        break;
      }
      reader.reset();
      
      facetList.add(Facet.loadTextData(reader));
    } while (true);
    
    stl.facets = facetList.toArray(new Facet[facetList.size()]);
      
    return stl;
  }
  
  /**
   * テキストデータであるか判定します。
   * 
   * @param filePath ファイルパス
   * @return テキストデータならばtrue
   * @throws IOException ファイルを読み込めない場合
   */
  public static boolean isTextData(String filePath) throws IOException {
    try (DataInputStream input = new DataInputStream(new FileInputStream(filePath))) {
      final byte c1 = input.readByte();
      final byte c2 = input.readByte();
      final byte c3 = input.readByte();
      final byte c4 = input.readByte();
      final byte c5 = input.readByte();
      final byte c6 = input.readByte();
      
      if (c1 != 's' || c2 != 'o' || c3 != 'l' || c4 != 'i' || c5 != 'd' || c6 != ' ') {
        return false;
      }
    }
    
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      final String firstLine = reader.readLine();
      if (firstLine.startsWith("solid ") == false) { //$NON-NLS-1$
        return false;
      }
      
      final String secondLine = reader.readLine();
      final String[] secondLineWords = secondLine.trim().replaceAll("[ \\t]+", " ").split("[ ]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      if (secondLineWords.length != 5 || secondLineWords[0].equals("facet") == false || secondLineWords[1].equals("normal") == false) { //$NON-NLS-1$ //$NON-NLS-2$
        return false; 
      }
    }
    
    return true;
  }
  
  
  /**
   * ファイルからデータを読み込みます。
   * 
   * @param filePath ファイルパス
   * @return STL
   * @throws IOException ファイルを読み込めない場合
   */
  public static STL load(String filePath) throws IOException {
    if (isTextData(filePath)) {
      return loadTextData(filePath);
    }

    return loadBinaryData(filePath);
  }
  
  /**
   * ファイルからバイナリデータを読み込みます。
   * 
   * @param filePath ファイルパス
   * @return STL
   * @throws IOException ファイルを読み込めない場合
   */
  public static STL loadBinaryData(String filePath) throws IOException {
    try (FileInputStream input = new FileInputStream(filePath)) {
      final STL stl = loadBinaryData(input);
      return stl;
    }
  }
  
  /**
   * ファイルからテキストデータを読み込みます。
   * 
   * @param filePath ファイルパス
   * @return STL
   * @throws IOException ファイルを読み込めない場合
   */
  public static STL loadTextData(String filePath) throws IOException {
    try (FileReader input = new FileReader(filePath)) {
      final STL stl = loadTextData(input);
      return stl;
    }
  }

  
  /**
   * データを標準出力に表示します。
   */
  public void print() {
    for (Facet triangle : this.facets) {
      System.out.println(triangle);
    }
  }
  
  /**
   * Mikity3Dのモデルに変換します。
   * 
   * @return Mikity3Dのモデル
   */
  public Mikity3DModel toMikity3DModel() {
    final CompositionModel composition = new CompositionModel();
    
    for (final Facet facet: this.facets) {
      final float[] v1 = facet.getVertex1();
      final float[] v2 = facet.getVertex2();
      final float[] v3 = facet.getVertex3();
      
      final VertexModel vertex1 = new VertexModel(v1[0]/1000, v1[1]/1000, v1[2]/1000);
      final VertexModel vertex2 = new VertexModel(v2[0]/1000, v2[1]/1000, v2[2]/1000);
      final VertexModel vertex3 = new VertexModel(v3[0]/1000, v3[1]/1000, v3[2]/1000);
      
      final FacetModel facetModel = new FacetModel(vertex1, vertex2, vertex3);
      composition.add(facetModel);
    }

    float xCenter = (composition.getXmax() + composition.getXmin())/2;
    float yCenter = (composition.getYmax() + composition.getYmin())/2;
    float zCenter = (composition.getZmax() + composition.getZmin())/2;
    
    composition.translate(-xCenter, -yCenter, -zCenter);
    xCenter = 0;
    yCenter = 0;
    zCenter = 0;
    
    float compositionSize = Math.max(Math.max(composition.getWidth(), composition.getHeight()), composition.getDepth());
    
    final ConfigurationModel configuration = new ConfigurationModel();
    configuration.setEye(new EyeModel(xCenter + compositionSize*15, yCenter, zCenter));
    configuration.setLookAtPoiint(new LookAtPointModel(xCenter, yCenter, zCenter));
    configuration.setLight(new LightModel(composition.getXmax()*100, composition.getYmax()*100, composition.getZmax()*100));
   
    final GroupModel group = new GroupModel(this.name);
    group.add(composition);
    
    final SceneModel scene = new SceneModel();
    scene.addGroup(group);

    final Mikity3DModel mikity3d = new Mikity3DModel();
    mikity3d.addConfiguration(configuration);
    mikity3d.addScene(scene);
    
    return mikity3d;
  }
  
  /**
   * メインメソッドです。
   * 
   * @param args コマンドライン引数
   * @throws IOException ファイルを読み込めない場合
   */
  public static void main(String[] args) throws IOException {
    final STL stl = STL.load(args[0]);
    stl.print();
    
    final Mikity3DModel mikity3d = stl.toMikity3DModel();
    System.out.println(mikity3d);
  }
}
