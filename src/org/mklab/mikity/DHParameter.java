/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

/**
 * DHパラメータに関するクラス
 * 
 * @author miki
 * @version $Revision: 1.4 $.2005/01/17
 */
public class DHParameter {

  /**
   * 
   */
  public static final int A = 1;
  /**
   * 
   */
  public static final int ALPHA = 2;
  /**
   * 
   */
  public static final int D = 3;
  /**
   * 
   */
  public static final int THETA = 4;

  double a, d, alpha, theta;

  /**
   * コンストラクター
   */
  public DHParameter() {
    this(0, 0, 0, 0);
  }

  /**
   * コンストラクター
   * 
   * @param a
   * @param alpha
   * @param d
   * @param theta
   */
  public DHParameter(final double a, final double alpha, final double d, final double theta) {
    this.a = a;
    this.d = d;
    this.alpha = alpha;
    this.theta = theta;
  }

  /**
   * パラメータaの値を取得する。
   * 
   * @return a
   */
  public double getA() {
    return this.a;
  }

  /**
   * パラメータaの値を設定する。
   * 
   * @param a
   */
  public void setA(double a) {
    this.a = a;
  }

  /**
   * パラメータalphaの値を取得する。
   * 
   * @return alpha
   */
  public double getAlpha() {
    return this.alpha;
  }

  /**
   * パラメータalphaの値を設定する。
   * 
   * @param alpha
   */
  public void setAlpha(double alpha) {
    this.alpha = alpha;
  }

  /**
   * パラメータdの値を取得する。
   * 
   * @return d
   */
  public double getD() {
    return this.d;
  }

  /**
   * パラメータdの値を設定する。
   * 
   * @param d
   */
  public void setD(double d) {
    this.d = d;
  }

  /**
   * パラメータthetaの値を取得する。
   * 
   * @return theta
   */
  public double getTheta() {
    return this.theta;
  }

  /**
   * パラメータthetaの値を設定する。
   * 
   * @param theta
   */
  public void setTheta(double theta) {
    this.theta = theta;
  }
}