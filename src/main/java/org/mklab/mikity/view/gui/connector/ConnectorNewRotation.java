/*
 * $Id: ConnectNewRotation.java,v 1.4 2008/02/29 13:51:56 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.view.gui.connector;

import org.mklab.mikity.model.xml.model.Rotation;


/**
 * コネクタの更新後の回転を設定するクラスです。
 * 
 * @author SHOGO
 * @version $Revision: 1.4 $. 2008/02/29
 */
public class ConnectorNewRotation {
  /** 更新後の回転 */
  private Rotation newRotation;
  /** プリミティブのX軸回転 */
  private float xRatation;
  /** プリミティブのY軸回転 */
  private float yRatation;
  /** プリミティブのZ軸回転 */
  private float zRotation;

  /**
   * コンストラクタ
   */
  public ConnectorNewRotation() {
    this.newRotation = new Rotation();
  }

  /**
   * 基準プリミティブの回転を取得する
   * 
   * @param primitiveNRot 基準プリミティブの回転
   */
  public void setPrimitiveNRotation(Rotation primitiveNRot) {
    this.xRatation = primitiveNRot.loadXrotate();
    this.yRatation = primitiveNRot.loadYrotate();
    this.zRotation = primitiveNRot.loadZrotate();
  }

  /**
   * プリミティブの移動後の回転を番号の組み合わせから更新する
   * 
   * @param n 　コネクタNの番号
   * @param s 　コネクタSの番号
   */
  public void setNewRotation(int n, int s) {
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
    this.newRotation.setXrotate(this.xRatation);
    this.newRotation.setYrotate(this.yRatation);
    this.newRotation.setZrotate(this.zRotation);
  }

  /**
   * 移動後の回転(2)
   */
  private void setRotationN2() {
    this.newRotation.setXrotate(this.xRatation + 180.0f);
    this.newRotation.setYrotate(this.yRatation);
    this.newRotation.setZrotate(this.zRotation);
  }

  /**
   * 移動後の回転(3)
   */
  private void setRotationN3() {
    this.newRotation.setXrotate(-this.yRatation);
    this.newRotation.setYrotate(0.0f);
    this.newRotation.setZrotate(this.zRotation - 90.0f);
  }

  /**
   * 移動後の回転(4)
   */
  private void setRotationN4() {
    this.newRotation.setXrotate(this.yRatation);
    this.newRotation.setYrotate(0.0f);
    this.newRotation.setZrotate(this.zRotation + 90.0f);
  }

  /**
   * 移動後の回転(5)
   */
  private void setRotationN5() {
    this.newRotation.setXrotate(this.xRatation + 90.0f);
    this.newRotation.setYrotate(this.yRatation);
    this.newRotation.setZrotate(this.zRotation);
  }

  /**
   * 移動後の回転(6)
   */
  private void setRotationN6() {
    this.newRotation.setXrotate(this.xRatation - 90.0f);
    this.newRotation.setYrotate(this.yRatation);
    this.newRotation.setZrotate(this.zRotation);
  }

  /**
   * 更新後の回転を返す
   * 
   * @return　newRot　更新後の回転
   */
  public Rotation getNewRotation() {
    return this.newRotation;
  }
}
