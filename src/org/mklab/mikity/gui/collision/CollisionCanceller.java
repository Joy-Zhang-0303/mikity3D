/*
 * $Id: CollisionCanceller.java,v 1.3 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.collision;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Location;
import org.mklab.mikity.xml.model.XMLBox;
import org.mklab.mikity.xml.model.XMLCone;
import org.mklab.mikity.xml.model.XMLCylinder;
import org.mklab.mikity.xml.model.XMLSphere;


/**
 * プリミティブの重複を防止するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.3 $. 2006/07/18
 */
public class CollisionCanceller {

  /**
   * 新しく追加するプリミティブの存在範囲
   */
  private float range;
  /**
   * 新しく追加するプリミティブの位置座標
   */
  private Location loc;

  /**
   * これまで追加したプリミティブの存在範囲
   */
  private ArrayList<PrimitiveRange> rangeList;
  //private float rangeArray[];

  /**
   * これまで追加したプリミティブの位置座標
   */
  private ArrayList<Location> locList;
  //private Location locArray[];

  /**
   * 2つのプリミティブの位置座標間の距離
   */
  private float distance;

  /**
   * プリミティブが重複しているかどうかを表すフラグ
   */
  private boolean collideFlag = false;

  private Composite comp;

  private PrimitiveRange pr;
  private AdjustLocation al;
  private PrimitiveQueue q;

  /**
   * コンストラクタ
   * 
   * @param comp
   */
  public CollisionCanceller(Composite comp) {
    this.locList = new ArrayList<Location>();
    this.rangeList = new ArrayList<PrimitiveRange>();

    this.pr = new PrimitiveRange();
    this.al = new AdjustLocation(this);
    this.q = new PrimitiveQueue();

    this.comp = comp;
  }

  /**
   * 二つのプリミティブが重複するか判別する
   * 
   * @param prim
   *        　追加するプリミティブ
   * @param primLoc
   *        　追加するプリミティブの座標
   * @param group
   *        　グループ
   */
  public void checkCollision(Object prim, Location primLoc, Group group) {
    this.collideFlag = false;
    this.pr.setRange(prim);
    this.range = this.pr.getRange();
    this.loc = checkLoc(getLoc(prim));

    float listRange = 0.0f;
    Location listLoc = null;
    // primLoc = checkLoc(primLoc);
    // 重複を探知するまでリスト内のプリミティブを調査
    if (this.locList.size() > 0) {
      PrimitiveRange primRange = new PrimitiveRange();
      int j = 0;
      do {
        this.pr.setRange(this.rangeList.get(j));
        primRange = this.rangeList.get(j);
        listRange = primRange.getRange();
        listLoc = this.locList.get(j);
        calculateDistance(this.loc, listLoc);
        if (this.range + listRange > this.distance) {
          this.collideFlag = true;
        }
        j++;
      } while (this.collideFlag == false && j < this.locList.size());
    }
    checkCollisionFlag(prim, this.range, checkLoc(primLoc), listRange, listLoc, group);
  }

  /**
   * 重複フラグを確認する。 重複すると判断された場合、位置調整処理に移行。 それ以外は各リストに追加およびモデルの描画。
   * 
   * @param prim
   *        　追加するプリミティブ
   * @param primLoc
   *        　追加するプリミティブの座標
   * @param group
   *        　グループ
   */
  private void checkCollisionFlag(Object prim, float argRange, Location primLoc, float listRange, Location listLoc, Group group) {
    if (this.collideFlag == true) {
      MessageBox msg = new MessageBox(this.comp.getShell(), SWT.ICON_INFORMATION);
      msg.setText(Messages.getString("CollisionCanceller.0")); //$NON-NLS-1$
      msg.setMessage(Messages.getString("CollisionCanceller.1")); //$NON-NLS-1$
      msg.open();
      // al.adjustLoc(range,primLoc,listRange,listLoc,range);
      // checkDuplication(prim,al.getNewLocation(),group);
    } else {
      // primLoc = al.getNewLocation();//なぜかこれがないと無限ループになる…
      addLocationList(primLoc);
      addRangeList(prim);
      addPrimitive(prim, primLoc, group);
    }
  }

  /**
   * プリミティブの位置座標を返す。
   * 
   * @param prim
   *        プリミティブ
   * @return loc 位置座標
   */
  private Location getLoc(Object prim) {
    Location localLocation = new Location();
    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;
      localLocation = box.loadLocation();
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;
      localLocation = cone.loadLocation();
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)prim;
      localLocation = cylinder.loadLocation();
    } else if (prim instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)prim;
      localLocation = sphere.loadLocation();
    }
    return localLocation;
  }

  /**
   * 二つのプリミティブの位置座標間の距離を算出する。
   * 
   * @param loc1
   *        　プリミティブの位置座標1
   * @param loc2
   *        　プリミティブの位置座標2
   */
  private void calculateDistance(Location loc1, Location loc2) {
    this.distance = (float)Math.sqrt(Math.pow((loc2.loadX() - loc1.loadX()), 2) + Math.pow(loc2.loadY() - loc1.loadY(), 2) + Math.pow(loc2.loadZ() - loc1.loadZ(), 2));
  }

  /**
   * プリミティブの位置座標がNULLだった場合、座標(0.0,0.0,0.0)の情報を持つ位置座標を返す。
   * 
   * @param argLocation
   *        　プリミティブの位置座標
   * @return loc　更新したプリミティブの位置座標
   */
  private Location checkLoc(Location argLocation) {
    if (argLocation == null) {
      argLocation = new Location();
      argLocation.setX(0.0f);
      argLocation.setY(0.0f);
      argLocation.setZ(0.0f);
    }
    return argLocation;
  }

  /**
   * プリミティブの位置座標をリストに追加する。
   * 
   * @param argLocation
   *        　プリミティブの位置座標
   */
  private void addLocationList(Location argLocation) {
    Location primLoc = new Location();
    primLoc = argLocation;
    this.locList.add(checkLoc(primLoc));
  }

  /**
   * プリミティブの存在範囲をリストに追加する。
   * 
   * @param prim
   *        　プリミティブ
   */
  private void addRangeList(Object prim) {
    this.pr.setRange(prim);
    this.rangeList.add(this.pr);
  }

  /**
   * ツリーにプリミティブを追加する。
   * 
   * @param prim
   *        　プリミティブ
   * @param group
   *        　プリミティブの所属するクラス
   */
  private void addPrimitive(Object prim, Location primLoc, Group group) {
    if (prim instanceof XMLBox) {
      XMLBox box = (XMLBox)prim;
      box.setLocation(primLoc);
      group.addXMLBox(box);
    } else if (prim instanceof XMLCone) {
      XMLCone cone = (XMLCone)prim;
      cone.setLocation(primLoc);
      group.addXMLCone(cone);
    } else if (prim instanceof XMLCylinder) {
      XMLCylinder cylinder = (XMLCylinder)prim;
      cylinder.setLocation(primLoc);
      group.addXMLCylinder(cylinder);
    } else if (prim instanceof XMLSphere) {
      XMLSphere sphere = (XMLSphere)prim;
      sphere.setLocation(primLoc);
      group.addXMLSphere(sphere);
    }
  }

  /**
   * @param comp
   */
  public void setComposite(Composite comp) {
    this.comp = comp;
  }
}
