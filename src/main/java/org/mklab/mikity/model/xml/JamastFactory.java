/*
 * Created on 2012/12/01
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.File;
import java.io.IOException;

import org.mklab.mikity.model.xml.simplexml.Jamast;
import org.mklab.mikity.model.xml.simplexml.JamastConfig;
import org.mklab.mikity.model.xml.simplexml.JamastModel;
import org.mklab.mikity.model.xml.simplexml.SimpleXmlUnmarshaller;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCone;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;
import org.mklab.mikity.model.xml.simplexml.model.XMLQuadPolygon;
import org.mklab.mikity.model.xml.simplexml.model.XMLSphere;
import org.mklab.mikity.model.xml.simplexml.model.XMLTrianglePolygon;


/**
 * Jamastモデルを生成するクラスです。
 * 
 * @author koga
 * @version $Revision$, 2012/12/01
 */
public class JamastFactory {

  /**
   * ファイルを読み込み，データをrootに追加します。
   * 
   * @param file ファイル
   * @param parent Jamastのroot
   * @throws IOException ファイルを読み込めない場合
   * @throws JamastSerializeDeserializeException ファイルを読み込めない場合 
   */
  public void importFile(File file, Jamast parent) throws IOException, JamastSerializeDeserializeException {
    //final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    final SimpleXmlUnmarshaller unmarshaller = new SimpleXmlUnmarshaller();
    unmarshaller.unmarshal(file);
    final Jamast root = unmarshaller.getRoot();

    final Group parentGroup = parent.getModel(0).getGroup(0);
    
    if (root == null) {
      final Group[] groups = unmarshaller.getClolladaGroup().getGroups();
      for (final Group group : groups) {
        parentGroup.addGroup(group);
      }
      return;
    }

    final Group rootGroup = root.getModel(0).getGroup(0);

    final XMLBox[] boxes = rootGroup.getXMLBox();
    final XMLCone[] cones = rootGroup.getXMLCone();
    final XMLCylinder[] cylinders = rootGroup.getXMLCylinder();
    final XMLSphere[] spheres = rootGroup.getXMLSphere();
    final XMLTrianglePolygon[] trianglePolygons = rootGroup.getXMLTrianglePolygon();
    final XMLQuadPolygon[] quadPolygons = rootGroup.getXMLQuadPolygon();
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
   * Jamastファイルを読み込みます。
   * 
   * @param file Jamastファイル
   * @return Jamastモデル
   * @throws IOException ファイルを読み込めない場合
   * @throws JamastSerializeDeserializeException ファイルを読み込めない場合
   */
  public Jamast loadFile(final File file) throws IOException, JamastSerializeDeserializeException {
    //final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    final SimpleXmlUnmarshaller unmarshaller = new SimpleXmlUnmarshaller();
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
   * 
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
