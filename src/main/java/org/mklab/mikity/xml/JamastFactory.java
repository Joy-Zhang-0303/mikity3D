/*
 * Created on 2012/12/01
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.mklab.mikity.gui.Messages;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLQuadPolygon;
import org.mklab.mikity.xml.model.XMLSphere;
import org.mklab.mikity.xml.model.XMLTrianglePolygon;


/**
 * Jamastモデルを生成するクラスです。
 * @author koga
 * @version $Revision$, 2012/12/01
 */
public class JamastFactory {
  /**
   * ファイルを読み込み，データをrootに追加します。
   * @param localFile ファイル
   * @param localRoot Jamastのroot
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  public void importJavaFile(File localFile, Jamast localRoot) throws IOException, JAXBException {
    final Group group = localRoot.loadModel(0).loadGroup(0);
    
    final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    unmarshaller.unmarshal(localFile);

    final Jamast newRoot = unmarshaller.getRoot();
    if (newRoot != null) {
      final Group newGroup = newRoot.loadModel(0).loadGroup(0);

      final XMLBox[] boxs = newGroup.getXMLBox();
      final XMLCone[] cones = newGroup.getXMLCone();
      final XMLCylinder[] cylinders = newGroup.getXMLCylinder();
      final XMLSphere[] spheres = newGroup.getXMLSphere();
      final XMLConnector[] connectors = newGroup.getXMLConnector();
      final XMLTrianglePolygon[] triangles = newGroup.getXMLTrianglePolygon();
      final XMLQuadPolygon[] quads = newGroup.getXMLQuadPolygon();
      final Group[] groups = newGroup.getGroups();
      
      if (boxs != null) {
        for (int i = 0; i < boxs.length; i++) {
          group.addXMLBox(boxs[i]);
        }
      }
      if (cones != null) {
        for (int i = 0; i < cones.length; i++) {
          group.addXMLCone(cones[i]);
        }
      }
      if (cylinders != null) {
        for (int i = 0; i < cylinders.length; i++) {
          group.addXMLCylinder(cylinders[i]);
        }
      }
      if (spheres != null) {
        for (int i = 0; i < spheres.length; i++) {
          group.addXMLSphere(spheres[i]);
        }
      }
      if (connectors != null) {
        for (int i = 0; i < connectors.length; i++) {
          group.addXMLConnector(connectors[i]);
        }
      }
      if (triangles != null) {
        for (int i = 0; i < triangles.length; i++) {
          group.addXMLTrianglePolygon(triangles[i]);
        }
      }
      if (quads != null) {
        for (int i = 0; i < quads.length; i++) {
          group.addXMLQuadPolygon(quads[i]);
        }
      }
      if (groups != null) {
        for (int i = 0; i < groups.length; i++) {
          group.addGroup(groups[i]);
        }
      }
    } else {
      final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
      for (int i = 0; i < groups.length; i++) {
        group.addGroup(groups[i]);
      }
    }
  }

  
  /**
   * Jamastファイルを読み込みます。
   * @param file Jamastファイル
   * @return Jamastモデル
   * @throws IOException ファイルを読み込めない場合
   * @throws JAXBException ファイルを読み込めない場合
   */
  public Jamast loadJamastFile(File file) throws IOException, JAXBException {
    final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    unmarshaller.unmarshal(file);
    
    final Jamast newRoot1 = unmarshaller.getRoot();
    if (newRoot1 != null) {
      return newRoot1;
    }
    
    final Jamast newRoot2 = createEmptyModel();
    final Group group = newRoot2.loadModel(0).loadGroup(0);
    final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
    for (int i = 0; i < groups.length; i++) {
      group.addGroup(groups[i]);
    }

    return newRoot2;
  }
  
  /**
   * 空のモデルを生成します。
   * @return Jamastモデル
   */
  public Jamast createEmptyModel() {
    final JamastConfig config = new JamastConfig();
    final JamastModel model = new JamastModel();
    final Jamast localRoot = new Jamast();
    localRoot.addConfig(config);
    localRoot.addModel(model);
    final Group group = new Group();
    group.setName(Messages.getString("FileNewAction.5")); //$NON-NLS-1$
    model.addGroup(group);
    return localRoot;
  }
}
