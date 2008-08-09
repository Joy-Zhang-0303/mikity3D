/*
 * Created on 2005/02/05
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.util;

import org.mklab.mikity.DHParameter;
import org.mklab.mikity.LinkParameter;
import org.mklab.mikity.gui.ModelCanvas;
import org.mklab.mikity.xml.model.Linkdata;


/**
 * 有用性に関するクラス
 * @author miki
 * @version $Revision: 1.7 $.2005/02/05
 */
public class Util {

  /**
   * コンストラクター
   */
  private Util(){}
  
  /**
   * DHパラメータを取得する。
   * @param linkdata　リンクデータ
   * @return parameter　DHパラメータ
   */
  public static DHParameter getDHParameter(final Linkdata[] linkdata){
    int scale = ModelCanvas.scale;
    boolean radian = ModelCanvas.radian;
    DHParameter parameter = new DHParameter(); //初期値全てゼロのDHParameter
    //linkdataが無い場合はlinkdata.lengthが0になる
    for (int i = 0; i < linkdata.length; i++) {
      //Constが存在する場合
      if (linkdata[i].hasConst()) {
        //かつ、targetがthetaであるか、dであるかを判別
        if (linkdata[i].loadTarget().equals("theta")) {
          if(radian){
            parameter.setTheta(linkdata[i].loadConst());
          } else{
            parameter.setTheta(Math.toRadians(linkdata[i].loadConst()));
          }
        } else if (linkdata[i].loadTarget().equals("d")) {
        	parameter.setD(linkdata[i].loadConst()/scale);
        } else if (linkdata[i].loadTarget().equals("a")) {
        	parameter.setA(linkdata[i].loadConst()/scale);
        } else if (linkdata[i].loadTarget().equals("alpha")) {
          if(radian){
            parameter.setAlpha(linkdata[i].loadConst());
          }else{
            parameter.setAlpha(Math.toRadians(linkdata[i].loadConst()));
          }
        }
      }
    }
    return parameter;
  }
  
  /**
   * リンクパラメータを取得する。
   * @param linkdata　リンクデータ
   * @return parameter　リンクパラメータ
   */
  public static LinkParameter getLinkParameter(final Linkdata[] linkdata){
	  int scale = ModelCanvas.scale;
	  boolean radian = ModelCanvas.radian;
	  LinkParameter link = new LinkParameter();
//	  linkdataが無い場合はlinkdata.lengthが0になる
	  for (int i = 0; i < linkdata.length; i++) {
		  //Constが存在する場合
		  if (linkdata[i].hasConst()) {
			  //かつ、targetがthetaであるか、dであるかを判別
			  if (linkdata[i].loadTarget().equals("locationX")) {
				  link.setLocX(linkdata[i].loadConst()/scale);
			  } else if (linkdata[i].loadTarget().equals("rotationX")) {
				  if(radian){
					  link.setRotX(linkdata[i].loadConst());
				  }else{
					  link.setRotX(Math.toRadians(linkdata[i].loadConst()));
				  }
			  } else if (linkdata[i].loadTarget().equals("locationY")) {
				  link.setLocY(linkdata[i].loadConst()/scale);
			  } else if (linkdata[i].loadTarget().equals("rotationY")) {
				  if(radian){
					  link.setRotY(linkdata[i].loadConst());
				  }else{
					  link.setRotY(Math.toRadians(linkdata[i].loadConst()));
				  }
			  } else if (linkdata[i].loadTarget().equals("locationZ")) {
				  link.setLocZ(linkdata[i].loadConst()/scale);
			  } else if (linkdata[i].loadTarget().equals("rotationZ")) {
				  if(radian){
					  link.setRotZ(linkdata[i].loadConst());
				  }else{
					  link.setRotZ(Math.toRadians(linkdata[i].loadConst()));
				  }
			  }
		  }
	  }
	  return link;
  }
  
  /**
   * パラメータを取得する。
   * @param linkdata　リンクデータ
   * @param key
   * @return null
   */
  public static Linkdata getParameter(final Linkdata[] linkdata ,final String key){
    for (int i = 0; i < linkdata.length; i++) {
      if(linkdata[i].equals(key)){
        return linkdata[i];
      }
    }
    return null;
  }
}
