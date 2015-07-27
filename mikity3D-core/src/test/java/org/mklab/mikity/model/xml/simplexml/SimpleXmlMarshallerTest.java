/*
 * Created on 2015/05/07
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mklab.mikity.model.xml.Mikity3dSerializeDeserializeException;


/**
 * {@link Mikity3DMarshaller}のテストケースクラスです。
 * @author koga
 * @version $Revision$, 2015/05/07
 */
public class SimpleXmlMarshallerTest {
  /** テンポラリフォルダー */
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  
  /**
   * Test method for {@link Mikity3DMarshaller#marshal(java.io.File)}.
   * @throws IOException ファイルを作成できない場合
   * @throws Mikity3dSerializeDeserializeException アンマーシャルできない場合
   */
  @Test
  public void testMarshal() throws IOException, Mikity3dSerializeDeserializeException {
    final Mikity3DUnmarshaller unmarshaller1 = new Mikity3DUnmarshaller();
    try (final InputStream input = getClass().getClassLoader().getResourceAsStream("Pendulum.m3d")) { //$NON-NLS-1$
      unmarshaller1.unmarshalFromMikity3DFile(input);
    }

    final Mikity3DModel expected = unmarshaller1.getRoot();
    final Mikity3DMarshaller marshaller = new Mikity3DMarshaller(expected);
    
    final File file = this.temporaryFolder.newFile("afo.m3d"); //$NON-NLS-1$
    marshaller.marshal(file);
    
    final Mikity3DUnmarshaller unmarshaller2 = new Mikity3DUnmarshaller();
    unmarshaller2.unmarshalFromMikity3DFile(file);
    
    final Mikity3DModel actual = unmarshaller2.getRoot();

    assertThat(actual, is(expected));
  }

}
