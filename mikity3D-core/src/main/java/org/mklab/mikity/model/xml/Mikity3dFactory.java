/*
 * Created on 2012/12/01
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.File;
import java.io.InputStream;

import org.mklab.mikity.model.xml.simplexml.ColladaUnmarshaller;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.Mikity3DUnmarshaller;
import org.mklab.mikity.model.xml.simplexml.Mikity3DModel;
import org.mklab.mikity.model.xml.simplexml.SceneModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;


/**
 * Mikity3Dモデルを生成するクラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/01
 */
public class Mikity3dFactory {

  /**
   * ファイルを読み込み，データをrootに追加します。
   * 
   * @param file ファイル
   * @param parent Jamastのroot
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合 
   */
  public void importFile(File file, Mikity3DModel parent) throws Mikity3dSerializeDeserializeException {
    final Mikity3DUnmarshaller unmarshaller = new Mikity3DUnmarshaller();
    unmarshaller.unmarshalFromMikity3DFile(file);
    final Mikity3DModel importedMikity3d = unmarshaller.getRoot();
    final GroupModel[] rootGroups = importedMikity3d.getScene(0).getGroups();
    
    final SceneModel parentMikity3d = parent.getScene(0);
    
    for (GroupModel rootGroup : rootGroups) {
      parentMikity3d.addGroup(rootGroup);
    }
    

//    final BoxModel[] boxes = rootGroup.getXMLBoxes();
//    final ConeModel[] cones = rootGroup.getXMLCones();
//    final CylinderModel[] cylinders = rootGroup.getXMLCylinders();
//    final SphereModel[] spheres = rootGroup.getXMLSpheres();
//    final TrianglePolygonModel[] trianglePolygons = rootGroup.getXMLTrianglePolygons();
//    final QuadPolygonModel[] quadPolygons = rootGroup.getXMLQuadPolygons();
//    final GroupModel[] groups = rootGroup.getGroups();
//
//    if (boxes != null) {
//      for (final BoxModel box : boxes) {
//        parentGroup.addXMLBox(box);
//      }
//    }
//    if (cones != null) {
//      for (final ConeModel cone : cones) {
//        parentGroup.addXMLCone(cone);
//      }
//    }
//    if (cylinders != null) {
//      for (final CylinderModel cylinder : cylinders) {
//        parentGroup.addXMLCylinder(cylinder);
//      }
//    }
//    if (spheres != null) {
//      for (final SphereModel sphere : spheres) {
//        parentGroup.addXMLSphere(sphere);
//      }
//    }
//    if (trianglePolygons != null) {
//      for (final TrianglePolygonModel polygon : trianglePolygons) {
//        parentGroup.addXMLTrianglePolygon(polygon);
//      }
//    }
//    if (quadPolygons != null) {
//      for (final QuadPolygonModel polygon : quadPolygons) {
//        parentGroup.addXMLQuadPolygon(polygon);
//      }
//    }
//    if (groups != null) {
//      for (final GroupModel group : groups) {
//        parentGroup.addGroup(group);
//      }
//    }
  }

  /**
   * Mikity3Dファイルを読み込みます。
   * 
   * @param file Mikity3Dファイル
   * @return Mikity3Dモデル
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public Mikity3DModel loadFile(final File file) throws Mikity3dSerializeDeserializeException {
    final Mikity3DUnmarshaller unmarshaller = new Mikity3DUnmarshaller();
    unmarshaller.unmarshalFromMikity3DFile(file);
    final Mikity3DModel root = unmarshaller.getRoot();
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

    final Mikity3DModel newRoot = createEmptyModel();
    final GroupModel newGroup = newRoot.getScene(0).getGroup(0);
    final GroupModel[] groups = unmarshaller.getClolladaGroup().getGroups();
    for (final GroupModel group : groups) {
      newGroup.add(group);
    }

    return newRoot;
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

    final Mikity3DModel newRoot = createEmptyModel();
    final GroupModel newGroup = newRoot.getScene(0).getGroup(0);
    final GroupModel[] groups = unmarshaller.getClolladaGroup().getGroups();
    for (final GroupModel group : groups) {
      newGroup.add(group);
    }

    return newRoot;
  }
  
  /**
   * 空のモデルを生成します。
   * 
   * @return Mikity3Dモデル
   */
  public Mikity3DModel createEmptyModel() {
    final ConfigurationModel configuration = new ConfigurationModel();
    
    final GroupModel group = new GroupModel();
    group.setName("object"); //$NON-NLS-1$

    final SceneModel model = new SceneModel();
    model.addGroup(group);

    final Mikity3DModel root = new Mikity3DModel();
    root.addConfiguration(configuration);
    root.addScene(model);
    return root;
  }
}
