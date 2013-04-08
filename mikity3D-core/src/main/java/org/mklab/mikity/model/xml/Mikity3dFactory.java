/*
 * Created on 2012/12/01
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.Mikity3dModel;
import org.mklab.mikity.model.xml.simplexml.SimpleXmlUnmarshaller;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;


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
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合 
   */
  public void importFile(File file, Mikity3d parent) throws IOException, Mikity3dSerializeDeserializeException {
    final SimpleXmlUnmarshaller unmarshaller = new SimpleXmlUnmarshaller();
    unmarshaller.unmarshal(file);
    final Mikity3d root = unmarshaller.getRoot();

    final Group parentGroup = parent.getModel(0).getGroup(0);
    
    if (root == null) {
      final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
      for (final Group group : groups) {
        parentGroup.addGroup(group);
      }
      return;
    }

    final Group rootGroup = root.getModel(0).getGroup(0);

    final XMLBox[] boxes = rootGroup.getXMLBoxes();
    final XMLCone[] cones = rootGroup.getXMLCones();
    final XMLCylinder[] cylinders = rootGroup.getXMLCylinders();
    final XMLSphere[] spheres = rootGroup.getXMLSpheres();
    final XMLTrianglePolygon[] trianglePolygons = rootGroup.getXMLTrianglePolygons();
    final XMLQuadPolygon[] quadPolygons = rootGroup.getXMLQuadPolygons();
    final Group[] groups = rootGroup.getGroups();

    if (boxes != null) {
      for (final XMLBox box : boxes) {
        parentGroup.addXMLBox(box);
      }
    }
    if (cones != null) {
      for (final XMLCone cone : cones) {
        parentGroup.addXMLCone(cone);
      }
    }
    if (cylinders != null) {
      for (final XMLCylinder cylinder : cylinders) {
        parentGroup.addXMLCylinder(cylinder);
      }
    }
    if (spheres != null) {
      for (final XMLSphere sphere : spheres) {
        parentGroup.addXMLSphere(sphere);
      }
    }
    if (trianglePolygons != null) {
      for (final XMLTrianglePolygon polygon : trianglePolygons) {
        parentGroup.addXMLTrianglePolygon(polygon);
      }
    }
    if (quadPolygons != null) {
      for (final XMLQuadPolygon polygon : quadPolygons) {
        parentGroup.addXMLQuadPolygon(polygon);
      }
    }
    if (groups != null) {
      for (final Group group : groups) {
        parentGroup.addGroup(group);
      }
    }
  }

  /**
   * Mikity3Dファイルを読み込みます。
   * 
   * @param file Mikity3Dファイル
   * @return Mikity3Dモデル
   * @throws IOException ファイルを読み込めない場合
   * @throws Mikity3dSerializeDeserializeException ファイルを読み込めない場合
   */
  public Mikity3d loadFile(final File file) throws IOException, Mikity3dSerializeDeserializeException {
    final SimpleXmlUnmarshaller unmarshaller = new SimpleXmlUnmarshaller();
    unmarshaller.unmarshal(file);
    final Mikity3d root = unmarshaller.getRoot();

    if (root != null) {
      return root;
    }

    final Mikity3d newRoot = createEmptyModel();
    final Group newGroup = newRoot.getModel(0).getGroup(0);
    final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
    for (final Group group : groups) {
      newGroup.addGroup(group);
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
  public Mikity3d loadFile(final InputStream input) throws Mikity3dSerializeDeserializeException {
    final SimpleXmlUnmarshaller unmarshaller = new SimpleXmlUnmarshaller();
    unmarshaller.unmarshalFromMikity3DFile(input);
    final Mikity3d root = unmarshaller.getRoot();

    if (root != null) {
      return root;
    }

    final Mikity3d newRoot = createEmptyModel();
    final Group newGroup = newRoot.getModel(0).getGroup(0);
    final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
    for (final Group group : groups) {
      newGroup.addGroup(group);
    }

    return newRoot;
  }

  
  /**
   * 空のモデルを生成します。
   * 
   * @return Mikity3Dモデル
   */
  public Mikity3d createEmptyModel() {
    final Mikity3dConfiguration config = new Mikity3dConfiguration();

    final Group group = new Group();
    group.setName("Group0"); //$NON-NLS-1$

    final Mikity3dModel model = new Mikity3dModel();
    model.addGroup(group);

    final Mikity3d root = new Mikity3d();
    root.addConfiguration(config);
    root.addModel(model);
    return root;
  }
}
