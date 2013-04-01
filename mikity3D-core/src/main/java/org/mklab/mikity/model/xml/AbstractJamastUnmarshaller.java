/*
 * Created on 2013/02/05
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public abstract class AbstractJamastUnmarshaller implements Mikity3dUnmashaller {
  /**
   * {@inheritDoc}
   * 
   * @throws Mikity3dSerializeDeserializeException Unmarshalできない場合
   */
  public void unmarshal(File file) throws IOException, Mikity3dSerializeDeserializeException {
    final BufferedReader reader = new BufferedReader(new FileReader(file));
    final StringBuffer data = new StringBuffer();

    String line;
    while ((line = reader.readLine()) != null) {
      data.append(line);
    }
    reader.close();

    if (data.indexOf("<mikity3d") != -1) { //$NON-NLS-1$
      unmarshalFromJamastFile(file);
      return;
    }

    if (data.indexOf("<collada") != -1 || data.indexOf("<COLLADA") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
      unmarshalFromColladaFile(file);
      return;
    }

    throw new IllegalArgumentException("Neither mikity3d nor collada data"); //$NON-NLS-1$
  }
}
