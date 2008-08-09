/*
 * $Id: Instance_Geometry.java,v 1.1 2007/12/13 10:13:03 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import javax.xml.bind.annotation.XmlAttribute;

public class Instance_Geometry {
	@XmlAttribute
	private String url;
	
	public Instance_Geometry(){

	}
	
	public String loadURL(){
		return url;
	}
}
