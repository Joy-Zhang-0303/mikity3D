/*
 * Created on 2015/05/06
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;
import org.mklab.mikity.model.xml.simplexml.config.Background;
import org.mklab.mikity.model.xml.simplexml.config.DataUnit;
import org.mklab.mikity.model.xml.simplexml.config.Light;
import org.mklab.mikity.model.xml.simplexml.config.ModelUnit;
import org.mklab.mikity.model.xml.simplexml.config.View;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.mikity.model.xml.simplexml.model.Translation;
import org.mklab.mikity.model.xml.simplexml.model.Rotation;
import org.mklab.mikity.model.xml.simplexml.model.XMLBox;
import org.mklab.mikity.model.xml.simplexml.model.XMLCylinder;


/**
 * {@link Mikity3DUnmarshaller}のテストケースクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/05/06
 */
public class SimpleXmlUnmarshallerTest {

  private Mikity3DUnmarshaller unmarshaler = new Mikity3DUnmarshaller();

  /**
   * @throws IOException ファイルを参照できない場合
   * @throws Mikity3dSerializeDeserializeException unmarshallできない場合
   */
  @Before
  public void setUp() throws IOException, Mikity3dSerializeDeserializeException {
    try (final InputStream input = getClass().getClassLoader().getResourceAsStream("Pendulum.m3d")) { //$NON-NLS-1$
      this.unmarshaler.unmarshalFromMikity3DFile(input);
    }
  }

  //  /**
  //   * Test method for {@link org.mklab.mikity.model.xml.simplexml.SimpleXmlUnmarshaller#unmarshalFromMikity3DFile(java.io.File)}.
  //   */
  //  @Test
  //  public void testUnmarshalFromMikity3DFileFile() {
  //    fail("Not yet implemented"); //$NON-NLS-1$
  //  }

  /**
   * Test method for {@link Mikity3DUnmarshaller#unmarshalFromMikity3DFile(java.io.InputStream)}.
   * 
   * @throws IOException ファイルを参照できない場合
   * @throws Mikity3dSerializeDeserializeException unmarshallできない場合
   */
  @Test
  public void testUnmarshalFromMikity3DFileInputStream() throws IOException, Mikity3dSerializeDeserializeException {
    try (final InputStream input = getClass().getClassLoader().getResourceAsStream("Pendulum.m3d")) { //$NON-NLS-1$
      this.unmarshaler.unmarshalFromMikity3DFile(input);
    }
  }

  /**
   * Test method for {@link Mikity3DUnmarshaller#getRoot()}.
   */
  @Test
  public void testGetRoot() {
    final Mikity3d actual = this.unmarshaler.getRoot();
    final Mikity3dConfiguration actualConfiguration = actual.getConfiguration(0);

    final Background actualBackground = actualConfiguration.getBackground();
    final Background expectedBackground = new Background();
    expectedBackground.setColor("white"); //$NON-NLS-1$ 
    assertThat(actualBackground, is(expectedBackground));

    final Light actualLight = actualConfiguration.getLight();
    final Light expectedLight = new Light();
    expectedLight.setX(0.2F);
    expectedLight.setY(-0.8F);
    expectedLight.setZ(-0.8F);
    assertThat(actualLight, is(expectedLight));

    final View actualView = actualConfiguration.getView();
    final View expectedView = new View();
    expectedView.setX(0.0F);
    expectedView.setY(0.0F);
    expectedView.setZ(1.0F);
    expectedView.setRotationX(-0.2);
    expectedView.setRotationY(0.0);
    expectedView.setRotationZ(0.0);
    assertThat(actualView, is(expectedView));

    final ModelUnit actualModelUnit = actualConfiguration.getModelUnit();
    final ModelUnit expectedModelUnit = new ModelUnit();
    expectedModelUnit.setAngle("radian"); //$NON-NLS-1$
    expectedModelUnit.setLength("m"); //$NON-NLS-1$
    assertThat(actualModelUnit, is(expectedModelUnit));
    
    final DataUnit actualDataUnit = actualConfiguration.getDataUnit();
    final DataUnit expectedDataUnit = new DataUnit();
    expectedDataUnit.setAngle("radian"); //$NON-NLS-1$
    expectedDataUnit.setLength("m"); //$NON-NLS-1$
    assertThat(actualDataUnit, is(expectedDataUnit));
    
    final Mikity3dModel actualModel = actual.getModel(0);
    final Group actualGroup = actualModel.getGroup(0);
    
    final Translation actualLocation = actualGroup.getTranslation();
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(0.0F);
    expectedLocation.setZ(0.0F);
    assertThat(actualLocation, is(expectedLocation));
    
    final Rotation actualRotation = actualGroup.getRotation();
    final Rotation expectedRotation = new Rotation();
    expectedRotation.setX(0.0F);
    expectedRotation.setY(0.0F);
    expectedRotation.setZ(0.0F);
    assertThat(actualRotation, is(expectedRotation));
    
    final Group actualDodaiGroup = actualGroup.getGroup(0);
    assertDodaiGroup(actualDodaiGroup);
  }

  private void assertDodaiGroup(Group actualGroup) {
    final Translation actualLocation = actualGroup.getTranslation();
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(0.0F);
    expectedLocation.setZ(0.0F);
    assertThat(actualLocation, is(expectedLocation));

    final Rotation actualRotation = actualGroup.getRotation();
    final Rotation expectedRotation = new Rotation();
    expectedRotation.setX(0.0F);
    expectedRotation.setY(0.0F);
    expectedRotation.setZ(0.0F);
    assertThat(actualRotation, is(expectedRotation));
    
    final XMLBox actualBox1 = actualGroup.getXMLBox(0);
    assertDodaiBox1(actualBox1);

    final XMLBox actualBox2 = actualGroup.getXMLBox(1);
    assertDodaiBox2(actualBox2);

    final XMLBox actualBox3 = actualGroup.getXMLBox(2);
    assertDodaiBox3(actualBox3);

    final XMLBox actualBox4 = actualGroup.getXMLBox(3);
    assertDodaiBox4(actualBox4);

    final Group actualDaishaGroup = actualGroup.getGroup(0);
    assertDaishaGroup(actualDaishaGroup);
  }

  private void assertDodaiBox1(XMLBox actualBox) {
    final XMLBox expectedBox = new XMLBox();
    expectedBox.setTransparent(true);
    expectedBox.setColor("lightGray"); //$NON-NLS-1$
    expectedBox.setWidth(0.02F);
    expectedBox.setHeight(0.27F);
    expectedBox.setDepth(0.02F);
    
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(-0.05F);
    expectedLocation.setY(-0.21F);
    expectedLocation.setZ(0.0F);

    expectedBox.setTranslation(expectedLocation);
    
    assertThat(actualBox, is(expectedBox));
  }

  private void assertDodaiBox2(XMLBox actualBox) {
    final XMLBox expectedBox = new XMLBox();
    expectedBox.setTransparent(true);
    expectedBox.setColor("lightGray"); //$NON-NLS-1$
    expectedBox.setWidth(0.3F);
    expectedBox.setHeight(0.005F);
    expectedBox.setDepth(0.1F);
    
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(-0.3475F);
    expectedLocation.setZ(0.0F);

    expectedBox.setTranslation(expectedLocation);
    
    assertThat(actualBox, is(expectedBox));
  }

  private void assertDodaiBox3(XMLBox actualBox) {
    final XMLBox expectedBox = new XMLBox();
    expectedBox.setTransparent(true);
    expectedBox.setColor("lightGray"); //$NON-NLS-1$
    expectedBox.setWidth(0.02F);
    expectedBox.setHeight(0.27F);
    expectedBox.setDepth(0.02F);
    
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.05F);
    expectedLocation.setY(-0.21F);
    expectedLocation.setZ(0.0F);

    expectedBox.setTranslation(expectedLocation);
    
    assertThat(actualBox, is(expectedBox));
  }

  private void assertDodaiBox4(XMLBox actualBox) {
    final XMLBox expectedBox = new XMLBox();
    expectedBox.setTransparent(true);
    expectedBox.setColor("lightGray"); //$NON-NLS-1$
    expectedBox.setWidth(0.54F);
    expectedBox.setHeight(0.05F);
    expectedBox.setDepth(0.07F);
    
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(-0.05F);
    expectedLocation.setZ(0.0F);

    expectedBox.setTranslation(expectedLocation);
    
    assertThat(actualBox, is(expectedBox));
  }

  
  private void assertDaishaGroup(Group actualGroup) {
    final Translation actualLocation = actualGroup.getTranslation();
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(0.0F);
    expectedLocation.setZ(0.0F);
    assertThat(actualLocation, is(expectedLocation));

    final Rotation actualRotation = actualGroup.getRotation();
    final Rotation expectedRotation = new Rotation();
    expectedRotation.setX(0.0F);
    expectedRotation.setY(0.0F);
    expectedRotation.setZ(0.0F);
    assertThat(actualRotation, is(expectedRotation));

    final LinkData actualLinkData = actualGroup.getLinkData(0);
    final LinkData expectedLinkData = new LinkData();
    expectedLinkData.setNumber(2);
    //expectedLinkData.setBasis(0);
    expectedLinkData.setTarget("translationX"); //$NON-NLS-1$
    assertThat(actualLinkData, is(expectedLinkData));

    final XMLBox actualBox = actualGroup.getXMLBox(0);
    assertDaishaBox(actualBox);
    
    final Group actualSinsiGroup = actualGroup.getGroup(0);
    assertSinsiGroup(actualSinsiGroup);
  }
  
  private void assertDaishaBox(XMLBox actualBox) {
    final XMLBox expectedBox = new XMLBox();
    expectedBox.setTransparent(true);
    expectedBox.setColor("yellow"); //$NON-NLS-1$
    expectedBox.setWidth(0.07F);
    expectedBox.setHeight(0.05F);
    expectedBox.setDepth(0.005F);
    
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(0.0F);
    expectedLocation.setZ(0.02F);

    expectedBox.setTranslation(expectedLocation);
    
    assertThat(actualBox, is(expectedBox));
  }
  
  private void assertSinsiGroup(Group actualGroup) {
    final Translation actualLocation = actualGroup.getTranslation();
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(0.0F);
    expectedLocation.setZ(0.0F);
    assertThat(actualLocation, is(expectedLocation));

    final Rotation actualRotation = actualGroup.getRotation();
    final Rotation expectedRotation = new Rotation();
    expectedRotation.setX(0.0F);
    expectedRotation.setY(0.0F);
    expectedRotation.setZ(0.0F);
    assertThat(actualRotation, is(expectedRotation));
    
    final LinkData actualLinkData = actualGroup.getLinkData(0);
    final LinkData expectedLinkData = new LinkData();
    expectedLinkData.setNumber(3);
    //expectedLinkData.setBasis(0);
    expectedLinkData.setTarget("rotationZ"); //$NON-NLS-1$
    assertThat(actualLinkData, is(expectedLinkData));
    
    final XMLCylinder actualCylinder1 = actualGroup.getXMLCylinder(0);
    assertCylinder1(actualCylinder1);
    
    final XMLCylinder actualCylinder2 = actualGroup.getXMLCylinder(1);
    assertCylinder2(actualCylinder2);

  }

  private void assertCylinder1(XMLCylinder actualCylinder) {
    final XMLCylinder expectedCylinder = new XMLCylinder();
    expectedCylinder.setColor("red"); //$NON-NLS-1$
    expectedCylinder.setDivision(10);
    expectedCylinder.setHeight(0.018F);
    expectedCylinder.setRadius(0.01F);
    
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(0.0F);
    expectedLocation.setZ(0.0325F);

    final Rotation expectedRotation = new Rotation();
    expectedRotation.setX(1.5708F);
    expectedRotation.setY(0.0F);
    expectedRotation.setZ(0.0F);
    
    expectedCylinder.setTranslation(expectedLocation);
    expectedCylinder.setRotation(expectedRotation);
    
    assertThat(actualCylinder, is(expectedCylinder));
  }

  private void assertCylinder2(XMLCylinder actualCylinder) {
    final XMLCylinder expectedCylinder = new XMLCylinder();
    expectedCylinder.setColor("black"); //$NON-NLS-1$
    expectedCylinder.setDivision(10);
    expectedCylinder.setHeight(0.3F);
    expectedCylinder.setRadius(0.0025F);
    
    final Translation expectedLocation = new Translation();
    expectedLocation.setX(0.0F);
    expectedLocation.setY(0.15F);
    expectedLocation.setZ(0.037F);
    
    expectedCylinder.setTranslation(expectedLocation);
    
    assertThat(actualCylinder, is(expectedCylinder));
  }

}
