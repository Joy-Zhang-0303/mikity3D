/*
 * Created on 2006/02/03
 * Copyright (C) 2006 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.Rotation;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLConnector;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * コネクタを使って接続を実行するためのクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $.2006/02/03
 */
public class Connect {
  /** 接続に使用するプリミティブ  */
  private Object primitives[] = new Object[2];

  /** 各コネクタの位置座標  */
  private Location cLoc[] = new Location[2];
  /** 各コネクタの回転  */
  private Rotation cRot[] = new Rotation[2];

  /** 各プリミティブの位置座標  */
  private Location pLoc[] = new Location[2];
  /** 各プリミティブの回転 */
  private Rotation pRot[] = new Rotation[2];

  /** プリミティブ中心〜コネクタの距離 */
  private float length[] = new float[2];

  /** 各コネクタ番号 */
  private int connectorNumber[] = new int[2];

  /**
   * 移動後のプリミティブの位置座標
   */
  private Location newLoc;
  /**
   * 移動後のプリミティブの回転
   */
  private Rotation newRot;

  private ConnectNewRotation cNewRot;

  /**
   * コンストラクター
   */
  public Connect() {
    for (int i = 0; i < 2; i++) {
      this.primitives[i] = new Object();
      this.cLoc[i] = new Location();
      this.cRot[i] = new Rotation();

      this.pLoc[i] = new Location();
      this.pRot[i] = new Rotation();

      this.connectorNumber[i] = 0;
    }

    this.newLoc = new Location();
    this.newRot = new Rotation();

    this.cNewRot = new ConnectNewRotation();
  }

  /**
   * 接続基点となるコネクタN,Sの位置&回転に関する情報を取得する。
   * 
   * @param connector コネクタ
   */
  public void connectorNS(XMLConnector connector) {
    int i = checkNS(connector.loadFlag());

    if (connector.loadLocation() == null) {
      this.cLoc[i].setX(0.0f);
      this.cLoc[i].setY(0.0f);
      this.cLoc[i].setZ(0.0f);
    } else {
      this.cLoc[i].setX(connector.loadLocation().loadX());
      this.cLoc[i].setY(connector.loadLocation().loadY());
      this.cLoc[i].setZ(connector.loadLocation().loadZ());
    }
    if (connector.loadRotation() == null) {
      this.cRot[i].setXrotate(0.0f);
      this.cRot[i].setYrotate(0.0f);
      this.cRot[i].setZrotate(0.0f);
    } else {
      this.cRot[i].setXrotate(connector.loadRotation().loadXrotate());
      this.cRot[i].setYrotate(connector.loadRotation().loadYrotate());
      this.cRot[i].setZrotate(connector.loadRotation().loadZrotate());
    }
    this.length[i] = connector.loadLengthToCenter();
    this.connectorNumber[i] = connector.loadNum();
  }

  /**
   * 選択したコネクタN,Sが所属しているプリミティブの位置情報&回転情報を取得する。
   * 
   * @param prim プリミティブ
   * @param ns 選択したコネクタがNあるいはS
   */
  public void setPrimitiveNS(Object prim, String ns) {
    int i = checkNS(ns);

    this.primitives[i] = prim;

    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;
      boxNS(box, i);
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;
      coneNS(cone, i);
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)prim;
      cylinderNS(cylinder, i);
    } else if (prim instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)prim;
      sphereNS(sphere, i);
    }
  }

//  /**
//   * 選択したコネクタのプリミティブを返す
//   * 
//   * @param ns コネクタの属性
//   * @return　prim　選択したコネクタのプリミティブ
//   */
//  public Object getPrimitiveNS(String ns) {
//    int i = checkNS(ns);
//    return this.primitives[i];
//  }

  /**
   * 直方体の位置情報&回転情報を取得する。
   * 
   * @param box 直方体
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void boxNS(XMLBox box, int i) {
    if (box.loadLocation() == null) {
      this.pLoc[i].setX(0.0f);
      this.pLoc[i].setY(0.0f);
      this.pLoc[i].setZ(0.0f);
    } else {
      this.pLoc[i].setX(box.loadLocation().loadX());
      this.pLoc[i].setY(box.loadLocation().loadY());
      this.pLoc[i].setZ(box.loadLocation().loadZ());
    }
    if (box.loadRotation() == null) {
      this.pRot[i].setXrotate(0.0f);
      this.pRot[i].setYrotate(0.0f);
      this.pRot[i].setZrotate(0.0f);
    } else {
      this.pRot[i].setXrotate(box.loadRotation().loadXrotate());
      this.pRot[i].setYrotate(box.loadRotation().loadYrotate());
      this.pRot[i].setZrotate(box.loadRotation().loadZrotate());
    }
  }

  /**
   * 円錐の位置情報&回転情報を取得する。
   * 
   * @param cone 円錐
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void coneNS(XMLCone cone, int i) {
    if (cone.loadLocation() == null) {
      this.pLoc[i].setX(0.0f);
      this.pLoc[i].setY(0.0f);
      this.pLoc[i].setZ(0.0f);
    } else {
      this.pLoc[i].setX(cone.loadLocation().loadX());
      this.pLoc[i].setY(cone.loadLocation().loadY());
      this.pLoc[i].setZ(cone.loadLocation().loadZ());
    }
    
    if (cone.loadRotation() == null) {
      this.pRot[i].setXrotate(0.0f);
      this.pRot[i].setYrotate(0.0f);
      this.pRot[i].setZrotate(0.0f);
    } else {
      this.pRot[i].setXrotate(cone.loadRotation().loadXrotate());
      this.pRot[i].setYrotate(cone.loadRotation().loadYrotate());
      this.pRot[i].setZrotate(cone.loadRotation().loadZrotate());
    }
  }

  /**
   * 円柱の位置情報&回転情報を取得する。
   * 
   * @param cylinder 円柱
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void cylinderNS(XMLCylinder cylinder, int i) {
    if (cylinder.loadLocation() == null) {
      this.pLoc[i].setX(0.0f);
      this.pLoc[i].setY(0.0f);
      this.pLoc[i].setZ(0.0f);
    } else {
      this.pLoc[i].setX(cylinder.loadLocation().loadX());
      this.pLoc[i].setY(cylinder.loadLocation().loadY());
      this.pLoc[i].setZ(cylinder.loadLocation().loadZ());
    }
    
    if (cylinder.loadRotation() == null) {
      this.pRot[i].setXrotate(0.0f);
      this.pRot[i].setYrotate(0.0f);
      this.pRot[i].setZrotate(0.0f);
    } else {
      this.pRot[i].setXrotate(cylinder.loadRotation().loadXrotate());
      this.pRot[i].setYrotate(cylinder.loadRotation().loadYrotate());
      this.pRot[i].setZrotate(cylinder.loadRotation().loadZrotate());
    }
  }

  /**
   * 球体の位置情報&回転情報を取得する。
   * 
   * @param sphere 球体
   * @param i 選択したコネクタがN(i=0)あるいはS(i=1)
   */
  private void sphereNS(XMLSphere sphere, int i) {
    if (sphere.loadLocation() == null) {
      this.pLoc[i].setX(0.0f);
      this.pLoc[i].setY(0.0f);
      this.pLoc[i].setZ(0.0f);
    } else {
      this.pLoc[i].setX(sphere.loadLocation().loadX());
      this.pLoc[i].setY(sphere.loadLocation().loadY());
      this.pLoc[i].setZ(sphere.loadLocation().loadZ());
    }
    if (sphere.loadRotation() == null) {
      this.pRot[i].setXrotate(0.0f);
      this.pRot[i].setYrotate(0.0f);
      this.pRot[i].setZrotate(0.0f);
    } else {
      this.pRot[i].setXrotate(sphere.loadRotation().loadXrotate());
      this.pRot[i].setYrotate(sphere.loadRotation().loadYrotate());
      this.pRot[i].setZrotate(sphere.loadRotation().loadZrotate());
    }
  }

  /**
   * コネクタの属性を判別する
   * 
   * @param ns コネクタの属性
   * @return　Nならi=0,Sならi=1
   */
  private int checkNS(String ns) {
    if (ns == "N") { //$NON-NLS-1$
      return 0;
    } else if (ns == "S") { //$NON-NLS-1$
      return 1;
    } else {
      return 2;
    }
  }

  /**
   * 接続後のプリミティブSの座標を決定する。
   */
  public void setNewLoc() {
    float rate = (this.length[0] + this.length[1]) / this.length[0];
    this.newLoc.setX((this.cLoc[0].loadX() - this.pLoc[0].loadX()) * rate + this.pLoc[0].loadX());
    this.newLoc.setY((this.cLoc[0].loadY() - this.pLoc[0].loadY()) * rate + this.pLoc[0].loadY());
    this.newLoc.setZ((this.cLoc[0].loadZ() - this.pLoc[0].loadZ()) * rate + this.pLoc[0].loadZ());
  }

  /**
   * 接続後のプリミティブSの回転を決定する。
   */
  public void setNewRot() {
    this.cNewRot.setPrimitiveNRotation(this.pRot[0]);
    this.cNewRot.setNewRot(this.connectorNumber[0], this.connectorNumber[1]);
    this.newRot = this.cNewRot.getNewRot();
  }

  /**
   * コネクタを通じてプリミティブの接続を行う。
   */
  public void connect() {
    if (this.primitives[1] instanceof XMLBox) {
      XMLBox box = (XMLBox)this.primitives[1];
      box.setLocation(checkLoc(this.newLoc));
      box.setRotation(checkRot(this.newRot));
    } else if (this.primitives[1] instanceof XMLCone) {
      XMLCone cone = (XMLCone)this.primitives[1];
      cone.setLocation(checkLoc(this.newLoc));
      cone.setRotation(checkRot(this.newRot));
    } else if (this.primitives[1] instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)this.primitives[1];
      cylinder.setLocation(checkLoc(this.newLoc));
      cylinder.setRotation(checkRot(this.newRot));
    } else if (this.primitives[1] instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)this.primitives[1];
      sphere.setLocation(checkLoc(this.newLoc));
      sphere.setRotation(checkRot(this.newRot));
    }
  }

  /**
   * プリミティブの位置座標におけるパラメータが変化していないとき、各座標に0.0の値を代入する。
   * 
   * @param loc プリミティブの位置座標
   * @return プリミティブの位置座標
   */
  private Location checkLoc(Location loc) {
    if (loc != null) {
      return loc;
    }

    Location loc2 = new Location();
    loc2.setX(0.0f);
    loc2.setY(0.0f);
    loc2.setZ(0.0f);
    return loc2;
  }

  /**
   * プリミティブの各軸の回転角度におけるパラメータが変化していないとき、各軸の回転角度に0.0の値を代入する。
   * 
   * @param rot プリミティブの回転角度
   * @return プリミティブの回転角度
   */
  private Rotation checkRot(Rotation rot) {
    if (rot != null) {
      return rot;
    }

    Rotation rot2 = new Rotation();
    rot2.setXrotate(0.0f);
    rot2.setYrotate(0.0f);
    rot2.setZrotate(0.0f);
    return rot2;
  }
}
