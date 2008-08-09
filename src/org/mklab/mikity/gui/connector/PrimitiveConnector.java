/*
 * Created on 2006/01/31
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.gui.MainWindow;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;

/**
 * プリミティブに適したコネクタを追加するクラス
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/01/31
 */
public class PrimitiveConnector {
  /** プリミティブのパラメータ1*/
  private float param1;
  /** プリミティブのパラメータ2*/
  private float param2;
  /** プリミティブのパラメータ3*/
  private float param3;
  
  /** プリミティブの座標*/
  private Location primLoc;

  /**プリミティブの回転*/
  private Rotation primRot;
  
  /** 直方体のコネクタ*/
  private BoxConnector boxC;
  /** 円錐のコネクタ*/
  private ConeConnector coneC;
  /** 円柱のコネクタ*/
  private CylinderConnector cylinderC;
  /** 球体のコネクタ*/
  private SphereConnector sphereC;

  /**
   * コンストラクター
   */
  public PrimitiveConnector(){
    boxC = new BoxConnector();
    coneC = new ConeConnector();
    cylinderC = new CylinderConnector();
    sphereC = new SphereConnector();
  }
  
  /**
   * プリミティブごとのの各パラメータを取得し、キャンバス上のプリミティブごとに適したコネクタを追加する。
   *コネクタを追加されるプリミティブ
   * @param prim プリミティブ
   */
  public void createPrimitiveConnector(Object prim) {
    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox) prim;
      
      param1 = box.loadXsize();
      param2 = box.loadYsize();
      param3 = box.loadZsize();
      
      primLoc = box.loadLocation();
      checkLoc(primLoc);
      
      primRot = box.loadRotation();
      checkRot(primRot);

      boxC.createBoxConnector(param1,param2,param3,primLoc,primRot);
    }else if(prim instanceof XMLCone){
      XMLCone cone = (XMLCone) prim;
      
      param1 = cone.loadR();
      param2 = cone.loadHeight();

      primLoc = cone.loadLocation();
      checkLoc(primLoc);
     
      primRot = cone.loadRotation();
      checkRot(primRot);
      
      coneC.createConeConnector(param1,param2,primLoc,primRot);
    }else if(prim instanceof XMLCylinder){
      XMLCylinder cylinder = (XMLCylinder) prim;
      
      param1 = cylinder.loadR();
      param2 = cylinder.loadHeight();
      
      primLoc = cylinder.loadLocation();
      checkLoc(primLoc);
      
      primRot = cylinder.loadRotation();
      checkRot(primRot);
      
      cylinderC.createCylinderConnector(param1,param2,primLoc,primRot);
    }else if(prim instanceof XMLSphere){
      XMLSphere sphere = (XMLSphere) prim;
      
      param1 = sphere.loadR();

      primLoc = sphere.loadLocation();
      checkLoc(primLoc);
      
      primRot = sphere.loadRotation();
      checkRot(primRot);
      
      sphereC.createSphereConnector(param1,primLoc,primRot);
    }
  }

  /**
   * プリミティブの位置座標におけるパラメータが変化していないとき、各座標に0.0の値を代入する。
   * @param loc　プリミティブの位置座標
   */
  private void checkLoc(Location loc){
	  if(loc == null){
		  primLoc = new Location();
		  primLoc.setX(0.0f);
		  primLoc.setY(0.0f);
		  primLoc.setZ(0.0f);
	  }
  }
  
  /**
   * プリミティブの各軸の回転角度におけるパラメータが変化していないとき、各軸の回転角度に0.0の値を代入する。
   * @param rot　プリミティブの回転角度
   */
  private void checkRot(Rotation rot){
	  if(rot == null){
		  primRot = new Rotation();
		  primRot.setXrotate(0.0f);
		  primRot.setYrotate(0.0f);
		  primRot.setZrotate(0.0f);
	  }
  }
  
 /**
  * コネクタNを持つグループを生成した後返す
  * @return　newgroup　コネクタNを持つグループ
  */
public Group createConnectorNGroup(){
	    Jamast root = MainWindow.getRoot();
	    Group groupN = root.loadModel(0).loadGroup(0);    
	    Group newgroup = new Group();
	    newgroup.setName("ConnectorN");
	    groupN.addGroup(newgroup);
	    return newgroup;
  }
  
/**
 * コネクタSを持つグループを生成した後返す
 * @return　newgroup　コネクタSを持つグループ
 */
  public Group createConnectorSGroup(){
	    Jamast root = MainWindow.getRoot();
	    Group groupS = root.loadModel(0).loadGroup(0);    
	    Group newgroup = new Group();
	    newgroup.setName("ConnectorS");
	    groupS.addGroup(newgroup);
	    return newgroup;
  }
}
