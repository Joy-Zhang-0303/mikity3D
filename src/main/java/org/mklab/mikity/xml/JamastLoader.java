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


/**
 * Jamastファイルを読み込むクラスです。
 * @author koga
 * @version $Revision$, 2012/12/01
 */
public class JamastLoader {
  /**
   * Jamastファイルを読み込みます。
   * @param jamastFile Jamastファイル
   * @return JAMAST
   * @throws IOException ファイルを読み込めない場合
   * @throws JAXBException ファイルを読み込めない場合
   */
  public Jamast loadJamastFile(File jamastFile) throws IOException, JAXBException {
    final JAXBUnmarshaller unmarshaller = new JAXBUnmarshaller();
    unmarshaller.unmarshal(jamastFile);
    
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
   * @return root
   */
  private Jamast createEmptyModel() {
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
