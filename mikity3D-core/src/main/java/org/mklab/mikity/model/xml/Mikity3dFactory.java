/*
 * Created on 2012/12/01
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.ColladaUnmarshaller;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DUnmarshaller;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.util.STL;


/**
 * Mikity3Dモデルを生成するクラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/01
 */
public class Mikity3dFactory {
  /** 空モデル。 */
  private static Mikity3DModel EMPTY_MODEL;

  /**
   * ファイルを読み込み，データをrootに追加します。
   * 
   * @param file ファイル
   * @param parent Jamastのroot
   * @throws IOException ファイルを読み込めない場合 
   */
  public void importFile(File file, Mikity3DModel parent) throws IOException {
    final Mikity3DModel importedMikity3d;
    
    if (file.getPath().toLowerCase().endsWith(".m3d")) { //$NON-NLS-1$a
      try {
        final Mikity3DUnmarshaller unmarshaller = new Mikity3DUnmarshaller();
        unmarshaller.unmarshalFromMikity3DFile(file);
        importedMikity3d = unmarshaller.getRoot();
      } catch (Mikity3dSerializeDeserializeException e) {
        throw new IOException(e);
      }
    } else if (file.getPath().toLowerCase().endsWith(".stl")) { //$NON-NLS-1$
      importedMikity3d = STL.load(file.getPath()).toMikity3DModel();
    } else {
      throw new IllegalArgumentException("Inappropriate model file."); //$NON-NLS-1$
    }
      
    final List<GroupModel> rootGroups = importedMikity3d.getScene(0).getGroups();
    
    final SceneModel parentMikity3d = parent.getScene(0);
    
    for (GroupModel rootGroup : rootGroups) {
      parentMikity3d.addGroup(rootGroup);
    }
  }

  /**
   * Mikity3Dファイルを読み込みます。
   * 
   * @param file Mikity3Dファイル
   * @return Mikity3Dモデル
   * @throws IOException ファイルを読み込めない場合
   */
  public Mikity3DModel loadFile(final File file) throws IOException {
    final Mikity3DModel root;
    if (file.getPath().toLowerCase().endsWith(".m3d")) { //$NON-NLS-1$
      try {
        final Mikity3DUnmarshaller unmarshaller = new Mikity3DUnmarshaller();
        unmarshaller.unmarshalFromMikity3DFile(file);
        root = unmarshaller.getRoot();
      } catch (Mikity3dSerializeDeserializeException e) {
        throw new IOException(e);
      }
    } else if (file.getPath().toLowerCase().endsWith(".stl")) { //$NON-NLS-1$
      root = STL.load(file.getPath()).toMikity3DModel();
    } else {
      throw new IllegalArgumentException("Inappropriate model file."); //$NON-NLS-1$
    }
          
    return root;
  }

  /**
   * 入力ストリームからMikity3Dデータを読み込みます。
   * 
   * @param input 入力ストリーム
   * @return Mikity3Dモデル
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public Mikity3DModel loadFile(final InputStream input) throws Mikity3dSerializeDeserializeException {
    final Mikity3DUnmarshaller unmarshaller = new Mikity3DUnmarshaller();
    unmarshaller.unmarshalFromMikity3DFile(input);
    final Mikity3DModel root = unmarshaller.getRoot();
    return root;
  }

  /**
   * Mikity3Dファイルを読み込みます。
   * 
   * @param file Mikity3Dファイル
   * @return Mikity3Dモデル
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public Mikity3DModel loadColladFile(final File file) throws Mikity3dSerializeDeserializeException {
    final ColladaUnmarshaller unmarshaller = new ColladaUnmarshaller();
    unmarshaller.unmarshalFromColladaFile(file);

    final Mikity3DModel root = new Mikity3DModel();
    
    final ConfigurationModel configuration = new ConfigurationModel();
    root.addConfiguration(configuration);

    final GroupModel rootGroup = new GroupModel();
    rootGroup.setName("object"); //$NON-NLS-1$

    final SceneModel scene = new SceneModel();
    scene.addGroup(rootGroup);


    final List<GroupModel> groups = unmarshaller.getClolladaGroup().getGroups();
    for (final GroupModel group : groups) {
      rootGroup.add(group);
    }

    return root;
  }

  /**
   * 入力ストリームからMikity3Dデータを読み込みます。
   * 
   * @param input 入力ストリーム
   * @return Mikity3Dモデル
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public Mikity3DModel loadColladaFile(final InputStream input) throws Mikity3dSerializeDeserializeException {
    final ColladaUnmarshaller unmarshaller = new ColladaUnmarshaller();
    unmarshaller.unmarshalFromColladaFile(input);

    final Mikity3DModel root = new Mikity3DModel();
    
    final ConfigurationModel configuration = new ConfigurationModel();
    root.addConfiguration(configuration);

    final GroupModel rootGroup = new GroupModel();
    rootGroup.setName("object"); //$NON-NLS-1$

    final SceneModel scene = new SceneModel();
    scene.addGroup(rootGroup);
    
    final List<GroupModel> groups = unmarshaller.getClolladaGroup().getGroups();
    for (final GroupModel group : groups) {
      rootGroup.add(group);
    }

    return root;
  }
  
  /**
   * 空のモデルを生成します。
   * 
   * @return Mikity3Dモデル
   */
  public static Mikity3DModel getEmptyModel() {
    if (EMPTY_MODEL != null) {
      return EMPTY_MODEL;
    }
    
    final ConfigurationModel configuration = new ConfigurationModel();
    final SceneModel scene = new SceneModel();

    EMPTY_MODEL = new Mikity3DModel();
    EMPTY_MODEL.addConfiguration(configuration);
    EMPTY_MODEL.addScene(scene);
    return EMPTY_MODEL;
  }
  
  /**
   * 新しいモデルを生成します。
   * 
   * @return Mikity3Dモデル
   */
  public static Mikity3DModel createModel() {
    final ConfigurationModel configuration = new ConfigurationModel();
    final SceneModel scene = new SceneModel();

    final Mikity3DModel model = new Mikity3DModel();
    model.addConfiguration(configuration);
    model.addScene(scene);
    return model;
  }
}
