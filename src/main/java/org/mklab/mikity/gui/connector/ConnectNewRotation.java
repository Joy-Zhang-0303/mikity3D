/*
 * $Id: ConnectNewRotation.java,v 1.4 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.gui.connector;

import org.mklab.mikity.xml.model.Rotation;


/**
 * コネクタの更新後の回転を設定するクラス
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2008/02/29
 */
public class ConnectNewRotation {

  /** 更新後の回転 */
  private Rotation newRot;

  /** プリミティブのX軸回転 */
  private float x;
  /** プリミティブのY軸回転 */
  private float y;
  /** プリミティブのZ軸回転 */
  private float z;

  /**
   * コンストラクタ
   */
  public ConnectNewRotation() {
    this.newRot = new Rotation();

  }

  /**
   * 基準プリミティブの回転を取得する
   * 
   * @param primNRot 　基準プリミティブの回転
   */
  public void setPrimitiveNRotation(Rotation primNRot) {
    this.x = primNRot.loadXrotate();
    this.y = primNRot.loadYrotate();
    this.z = primNRot.loadZrotate();
  }

  /**
   * プリミティブの移動後の回転を番号の組み合わせから更新する
   * 
   * @param n 　コネクタNの番号
   * @param s 　コネクタSの番号
   */
  public void setNewRot(int n, int s) {
    if (s == 1) {
      switch (n) {
        case 1:
          setRotationN2();
          break;
        case 2:
          setRotationN1();
          break;
        case 3:
          setRotationN4();
          break;
        case 4:
          setRotationN3();
          break;
        case 5:
          setRotationN6();
          break;
        case 6:
          setRotationN5();
          break;
        default:
          throw new IllegalArgumentException();
      }
    } else if (s == 2) {
      switch (n) {
        case 1:
          setRotationN1();
          break;
        case 2:
          setRotationN2();
          break;
        case 3:
          setRotationN3();
          break;
        case 4:
          setRotationN4();
          break;
        case 5:
          setRotationN5();
          break;
        case 6:
          setRotationN6();
          break;
        default:
          throw new IllegalArgumentException();
      }
    } else if (s == 3) {
      switch (n) {
        case 1:
          setRotationN6();
          break;
        case 2:
          setRotationN5();
          break;
        case 3:
          setRotationN2();
          break;
        case 4:
          setRotationN1();
          break;
        case 5:
          setRotationN4();
          break;
        case 6:
          setRotationN3();
          break;
        default:
          throw new IllegalArgumentException();
      }
    } else if (s == 4) {
      switch (n) {
        case 1:
          setRotationN5();
          break;
        case 2:
          setRotationN6();
          break;
        case 3:
          setRotationN1();
          break;
        case 4:
          setRotationN2();
          break;
        case 5:
          setRotationN3();
          break;
        case 6:
          setRotationN4();
          break;
        default:
          throw new IllegalArgumentException();
      }
    } else if (s == 5) {
      switch (n) {
        case 1:
          setRotationN3();
          break;
        case 2:
          setRotationN4();
          break;
        case 3:
          setRotationN6();
          break;
        case 4:
          setRotationN5();
          break;
        case 5:
          setRotationN2();
          break;
        case 6:
          setRotationN1();
          break;
        default:
          throw new IllegalArgumentException();
      }
    } else if (s == 6) {
      switch (n) {
        case 1:
          setRotationN4();
          break;
        case 2:
          setRotationN3();
          break;
        case 3:
          setRotationN5();
          break;
        case 4:
          setRotationN6();
          break;
        case 5:
          setRotationN1();
          break;
        case 6:
          setRotationN2();
          break;
        default:
          throw new IllegalArgumentException();
      }
    } else {
      // TODO
    }
  }

  /**
   * 移動後の回転(1)
   */
  private void setRotationN1() {
    this.newRot.setXrotate(this.x);
    this.newRot.setYrotate(this.y);
    this.newRot.setZrotate(this.z);
  }

  /**
   * 移動後の回転(2)
   */
  private void setRotationN2() {
    this.newRot.setXrotate(this.x + 180.0f);
    this.newRot.setYrotate(this.y);
    this.newRot.setZrotate(this.z);
  }

  /**
   * 移動後の回転(3)
   */
  private void setRotationN3() {
    this.newRot.setXrotate(-this.y);
    this.newRot.setYrotate(0.0f);
    this.newRot.setZrotate(this.z - 90.0f);
  }

  /**
   * 移動後の回転(4)
   */
  private void setRotationN4() {
    this.newRot.setXrotate(this.y);
    this.newRot.setYrotate(0.0f);
    this.newRot.setZrotate(this.z + 90.0f);
  }

  /**
   * 移動後の回転(5)
   */
  private void setRotationN5() {
    this.newRot.setXrotate(this.x + 90.0f);
    this.newRot.setYrotate(this.y);
    this.newRot.setZrotate(this.z);
  }

  /**
   * 移動後の回転(6)
   */
  private void setRotationN6() {
    this.newRot.setXrotate(this.x - 90.0f);
    this.newRot.setYrotate(this.y);
    this.newRot.setZrotate(this.z);
  }

  /**
   * 更新後の回転を返す
   * 
   * @return　newRot　更新後の回転
   */
  public Rotation getNewRotation() {
    return this.newRot;
  }
}
