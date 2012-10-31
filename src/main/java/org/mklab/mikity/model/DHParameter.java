/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

/**
 * DHパラメータを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/17
 */
public class DHParameter {
  /** */
  public static final int A = 1;
  /** */
  public static final int ALPHA = 2;
  /** */
  public static final int D = 3;
  /** */
  public static final int THETA = 4;

  /** */
  double a;
  /** */
  double d;
  /** */
  double alpha;
  /** */
  double theta;
  /**
   * コンストラクター
   */
  public DHParameter() {
    this(0, 0, 0, 0);
  }

  /**
   * コンストラクター
   * 
   * @param a 位置
   * @param alpha α
   * @param d D
   * @param theta 角度
   */
  public DHParameter(final double a, final double alpha, final double d, final double theta) {
    this.a = a;
    this.d = d;
    this.alpha = alpha;
    this.theta = theta;
  }

  /**
   * パラメータaの値を返します。
   * 
   * @return a パラメータaの値
   */
  public double getA() {
    return this.a;
  }

  /**
   * パラメータaの値を設定します。
   * 
   * @param a パラメータaの値
   */
  public void setA(double a) {
    this.a = a;
  }

  /**
   * パラメータalphaの値を返します。
   * 
   * @return パラメータalphaの値
   */
  public double getAlpha() {
    return this.alpha;
  }

  /**
   * パラメータalphaの値を設定します。
   * 
   * @param alpha パラメータalpha
   */
  public void setAlpha(double alpha) {
    this.alpha = alpha;
  }

  /**
   * パラメータdの値を返します。
   * 
   * @return パラメータdの値
   */
  public double getD() {
    return this.d;
  }

  /**
   * パラメータdの値を設定します。
   * 
   * @param d パラメータdの値
   */
  public void setD(double d) {
    this.d = d;
  }

  /**
   * パラメータthetaの値を返します。
   * 
   * @return パラメータthetaの値
   */
  public double getTheta() {
    return this.theta;
  }

  /**
   * パラメータthetaの値を設定します。
   * 
   * @param theta パラメータthetaの値
   */
  public void setTheta(double theta) {
    this.theta = theta;
  }
}
