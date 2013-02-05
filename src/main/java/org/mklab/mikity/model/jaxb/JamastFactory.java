/*
 * Created on 2012/12/01
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.jaxb;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.mklab.mikity.model.xml.model.Group;
import org.mklab.mikity.model.xml.model.XMLBox;
import org.mklab.mikity.model.xml.model.XMLCone;
import org.mklab.mikity.model.xml.model.XMLCylinder;
import org.mklab.mikity.model.xml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.model.XMLSphere;
import org.mklab.mikity.model.xml.model.XMLTrianglePolygon;


/**
 * Jamastモデルを生成するクラスです。
 * @author koga
 * @version $Revision$, 2012/12/01
 */
public class JamastFactory {
  /**
   * ファイルを読み込み，データをrootに追加します。
   * @param localFile ファイル
   * @param root Jamastのroot
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  public void importJavaFile(File localFile, Jamast root) throws IOException, JAXBException {
    final Group rootGroup = root.getModel(0).getGroup(0);
    
    final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    unmarshaller.unmarshal(localFile);

    final Jamast newRoot = unmarshaller.getRoot();
    if (newRoot != null) {
      final Group newRootGroup = newRoot.getModel(0).getGroup(0);

      final XMLBox[] boxes = newRootGroup.getXMLBox();
      final XMLCone[] cones = newRootGroup.getXMLCone();
      final XMLCylinder[] cylinders = newRootGroup.getXMLCylinder();
      final XMLSphere[] spheres = newRootGroup.getXMLSphere();
      final XMLTrianglePolygon[] trianglePolygons = newRootGroup.getXMLTrianglePolygon();
      final XMLQuadPolygon[] quadPolygons = newRootGroup.getXMLQuadPolygon();
      final Group[] groups = newRootGroup.getGroups();
      
      if (boxes != null) {
        for (int i = 0; i < boxes.length; i++) {
          rootGroup.addXMLBox(boxes[i]);
        }
      }
      if (cones != null) {
        for (int i = 0; i < cones.length; i++) {
          rootGroup.addXMLCone(cones[i]);
        }
      }
      if (cylinders != null) {
        for (int i = 0; i < cylinders.length; i++) {
          rootGroup.addXMLCylinder(cylinders[i]);
        }
      }
      if (spheres != null) {
        for (int i = 0; i < spheres.length; i++) {
          rootGroup.addXMLSphere(spheres[i]);
        }
      }
      if (trianglePolygons != null) {
        for (int i = 0; i < trianglePolygons.length; i++) {
          rootGroup.addXMLTrianglePolygon(trianglePolygons[i]);
        }
      }
      if (quadPolygons != null) {
        for (int i = 0; i < quadPolygons.length; i++) {
          rootGroup.addXMLQuadPolygon(quadPolygons[i]);
        }
      }
      if (groups != null) {
        for (int i = 0; i < groups.length; i++) {
          rootGroup.addGroup(groups[i]);
        }
      }
    } else {
      final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
      for (int i = 0; i < groups.length; i++) {
        rootGroup.addGroup(groups[i]);
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
    
    final Jamast root = unmarshaller.getRoot();
    if (root != null) {
      return root;
    }
    
    final Jamast newRoot = createEmptyModel();
    final Group newRootGroup = newRoot.getModel(0).getGroup(0);
    final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
    for (int i = 0; i < groups.length; i++) {
      newRootGroup.addGroup(groups[i]);
    }

    return newRoot;
  }
  
  /**
   * 空のモデルを生成します。
   * @return Jamastモデル
   */
  public Jamast createEmptyModel() {
    final JamastConfig config = new JamastConfig();
    
    final Group group = new Group();
    group.setName("Group0"); //$NON-NLS-1$
    
    final JamastModel model = new JamastModel();
    model.addGroup(group);
    
    final Jamast root = new Jamast();
    root.addConfig(config);
    root.addModel(model);
    return root;
  }
}
