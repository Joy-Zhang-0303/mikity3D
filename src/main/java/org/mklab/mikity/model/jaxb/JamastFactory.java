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
   * @param file ファイル
   * @param root Jamastのroot
   * @throws JAXBException ファイルを読み込めない場合 
   * @throws IOException ファイルを読み込めない場合 
   */
  public void importFile(File file, Jamast root) throws IOException, JAXBException {
    final Group rootGroup = root.getModel(0).getGroup(0);
    
    final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    unmarshaller.unmarshal(file);

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
        for (final XMLBox box : boxes) {
          rootGroup.addXMLBox(box);
        }
      }
      if (cones != null) {
        for (final XMLCone cone : cones) {
          rootGroup.addXMLCone(cone);
        }
      }
      if (cylinders != null) {
        for (final XMLCylinder cylinder : cylinders) {
          rootGroup.addXMLCylinder(cylinder);
        }
      }
      if (spheres != null) {
        for (final XMLSphere sphere : spheres) {
          rootGroup.addXMLSphere(sphere);
        }
      }
      if (trianglePolygons != null) {
        for (final XMLTrianglePolygon polygon : trianglePolygons) {
          rootGroup.addXMLTrianglePolygon(polygon);
        }
      }
      if (quadPolygons != null) {
        for (final XMLQuadPolygon polygon : quadPolygons) {
          rootGroup.addXMLQuadPolygon(polygon);
        }
      }
      if (groups != null) {
        for (final Group group : groups) {
          rootGroup.addGroup(group);
        }
      }
    } else {
      final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
      for (final Group group : groups) {
        rootGroup.addGroup(group);
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
  public Jamast loadFile(final File file) throws IOException, JAXBException {
    final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    unmarshaller.unmarshal(file);
    
    final Jamast root = unmarshaller.getRoot();
    if (root != null) {
      return root;
    }
    
    final Jamast newRoot = createEmptyModel();
    final Group newGroup = newRoot.getModel(0).getGroup(0);
    final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
    for (final Group group : groups) {
      newGroup.addGroup(group);
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
