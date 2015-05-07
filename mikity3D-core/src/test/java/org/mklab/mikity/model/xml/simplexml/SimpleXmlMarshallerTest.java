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
 * {@link SimpleXmlMarshaller}のテストケースクラスです。
 * @author koga
 * @version $Revision$, 2015/05/07
 */
public class SimpleXmlMarshallerTest {
  /** テンポラリフォルダー */
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  
  /**
   * Test method for {@link SimpleXmlMarshaller#marshal(java.io.File)}.
   * @throws IOException ファイルを作成できない場合
   * @throws Mikity3dSerializeDeserializeException アンマーシャルできない場合
   */
  @Test
  public void testMarshal() throws IOException, Mikity3dSerializeDeserializeException {
    final SimpleXmlUnmarshaller unmarshaler1 = new SimpleXmlUnmarshaller();
    try (final InputStream input = getClass().getClassLoader().getResourceAsStream("pendulum.m3d")) { //$NON-NLS-1$
      unmarshaler1.unmarshalFromMikity3DFile(input);
    }

    final Mikity3d expected = unmarshaler1.getRoot();
    final SimpleXmlMarshaller marshaller = new SimpleXmlMarshaller(expected);
    
    final File file = this.temporaryFolder.newFile("afo.m3d"); //$NON-NLS-1$
    marshaller.marshal(file);
    
    final SimpleXmlUnmarshaller unmarshaler2 = new SimpleXmlUnmarshaller();
    unmarshaler2.unmarshalFromMikity3DFile(file);
    
    final Mikity3d actual = unmarshaler2.getRoot();

    assertThat(actual, is(expected));
  }

}
