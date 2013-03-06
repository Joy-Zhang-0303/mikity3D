/*
 * $Id: Instance_Geometry.java,v 1.1 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import org.simpleframework.xml.Attribute;


/**
 * @author koga
 * @version $Revision$, 2008/08/10
 */
public class InstanceGeometry {

  @Attribute
  private String geometryURL;

  /**
   * 新しく生成された<code>Instance_Geometry</code>オブジェクトを初期化します。
   */
  public InstanceGeometry() {
  //
  }

  /**
   * @return URL
   */
  public String getGeometryURL() {
    return this.geometryURL;
  }
}
