/*
 * Created on 2013/02/05
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Mikity3DのUnmarshallerの抽象クラスです。１
 * 
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public abstract class AbstractMikity3DUnmarshaller implements Mikity3dUnmashaller {

  /**
   * {@inheritDoc}
   */
  public void unmarshal(File file) throws IOException, Mikity3dSerializeDeserializeException {
    try (final InputStream input = new FileInputStream(file)) {
      unmarshal(input);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void unmarshal(InputStream input) throws Mikity3dSerializeDeserializeException, IOException {
    final StringBuffer data = new StringBuffer();

    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
      String line;
      while ((line = reader.readLine()) != null) {
        data.append(line);
      }
    }

    if (data.indexOf("<mikity3d") != -1) { //$NON-NLS-1$
      unmarshalFromMikity3DFile(input);
      return;
    }

    if (data.indexOf("<collada") != -1 || data.indexOf("<COLLADA") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
      unmarshalFromColladaFile(input);
      return;
    }

    throw new IllegalArgumentException("Neither mikity3d nor collada data"); //$NON-NLS-1$
  }

}
