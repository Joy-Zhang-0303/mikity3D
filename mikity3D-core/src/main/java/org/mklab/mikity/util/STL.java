/*
 * Created on 2015/11/19
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
   * 入力ストリームからデータを読み込みます。
   * 
   * @param input 入力ストリーム
   * @return STL
   * @throws IOException データを読み込めない場合
   */
  public static STL load(InputStream input) throws IOException {
    final STL stl = new STL();
    
    try (final DataInputStream dataInput = new DataInputStream(new BufferedInputStream(input))) {
      dataInput.read(stl.header, 0, 80);
      stl.size = Integer.reverseBytes(dataInput.readInt());
      stl.facets = new Facet[stl.size];
      for (int i = 0; i < stl.size; i++) {
        stl.facets[i] = Facet.load(dataInput);
      }
    }
    
    return stl; 
  }
  
  /**
   * ファイルからデータを読み込みます。
   * 
   * @param filePath ファイルパス
   * @return STL
   * @throws IOException ファイルを読み込めない場合
   */
  public static STL load(String filePath) throws IOException {
    try (FileInputStream input = new FileInputStream(filePath)) {
      final STL stl = load(input);
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


    final GroupModel group = new GroupModel(this.name);
    group.add(composition);
    
    final SceneModel scene = new SceneModel();
    scene.addGroup(group);
    
    float xCenter = (composition.getXmax() + composition.getXmin())/2;
    float yCenter = (composition.getYmax() + composition.getYmin())/2;
    float zCenter = (composition.getZmax() + composition.getZmin())/2;
    
    float compositionSize = Math.max(Math.max(composition.getWidth(), composition.getHeight()), composition.getDepth());
    
    final ConfigurationModel configuration = new ConfigurationModel();
    configuration.setEye(new EyeModel(xCenter + compositionSize*15, yCenter, zCenter));
    configuration.setLookAtPoiint(new LookAtPointModel(xCenter, yCenter, zCenter));
    configuration.setLight(new LightModel(composition.getXmax()*100, composition.getYmax()*100, composition.getZmax()*100));
  
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
