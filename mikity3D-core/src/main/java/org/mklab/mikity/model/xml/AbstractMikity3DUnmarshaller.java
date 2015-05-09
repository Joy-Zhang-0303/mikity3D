/*
 * Created on 2013/02/05
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;



/**
 * Mikity3DのUnmarshallerの抽象クラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public abstract class AbstractMikity3DUnmarshaller {

//  /**
//   * {@inheritDoc}
//   */
//  public void unmarshal(File file) throws IOException, Mikity3dSerializeDeserializeException {
//    try (final InputStream input = new FileInputStream(file)) {
//      unmarshal(input);
//    }
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  public void unmarshal(InputStream input) throws Mikity3dSerializeDeserializeException, IOException {
//    final StringBuffer data = new StringBuffer();
//
//    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
//      String line;
//      while ((line = reader.readLine()) != null) {
//        data.append(line);
//      }
//    }
//    
//    final InputStream clonedInput = new ByteArrayInputStream(data.toString().getBytes());
//
//    if (data.indexOf("<mikity3d") != -1) { //$NON-NLS-1$
//      unmarshalFromMikity3DFile(clonedInput);
//      return;
//    }
//
//    if (data.indexOf("<collada") != -1 || data.indexOf("<COLLADA") != -1) { //$NON-NLS-1$ //$NON-NLS-2$
//      unmarshalFromColladaFile(clonedInput);
//      return;
//    }
//
//    throw new IllegalArgumentException("Neither mikity3d nor collada data"); //$NON-NLS-1$
//  }

}
