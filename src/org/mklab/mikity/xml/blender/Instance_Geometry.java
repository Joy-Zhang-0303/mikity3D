/*
 * $Id: Instance_Geometry.java,v 1.1 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import javax.xml.bind.annotation.XmlAttribute;


/**
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class Instance_Geometry {

  @XmlAttribute
  private String url;

  /**
   * 新しく生成された<code>Instance_Geometry</code>オブジェクトを初期化します。
   */
  public Instance_Geometry() {

  }

  /**
   * @return
   */
  public String loadURL() {
    return this.url;
  }
}
