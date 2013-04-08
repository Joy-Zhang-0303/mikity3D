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
  /** a */
  private double a;
  /** d */
  private double d;
  /** alpha */
  private double alpha;
  /** theta */
  private double theta;
  
  /**
   * 新しく生成された<code>DHParameter</code>オブジェクトを初期化します。
   */
  public DHParameter() {
    this(0, 0, 0, 0);
  }

  /**
   * 新しく生成された<code>DHParameter</code>オブジェクトを初期化します。
   * @param a a
   * @param alpha alpha
   * @param d d
   * @param theta theta
   */
  public DHParameter(final double a, final double alpha, final double d, final double theta) {
    this.a = a;
    this.d = d;
    this.alpha = alpha;
    this.theta = theta;
  }

  /**
   * パラメータaを返します。
   * 
   * @return a パラメータa
   */
  public double getA() {
    return this.a;
  }

  /**
   * パラメータaを設定します。
   * 
   * @param a パラメータa
   */
  public void setA(double a) {
    this.a = a;
  }

  /**
   * パラメータalphaを返します。
   * 
   * @return パラメータalpha
   */
  public double getAlpha() {
    return this.alpha;
  }

  /**
   * パラメータalphaを設定します。
   * 
   * @param alpha パラメータalpha
   */
  public void setAlpha(double alpha) {
    this.alpha = alpha;
  }

  /**
   * パラメータdを返します。
   * 
   * @return パラメータd
   */
  public double getD() {
    return this.d;
  }

  /**
   * パラメータdを設定します。
   * 
   * @param d パラメータd
   */
  public void setD(double d) {
    this.d = d;
  }

  /**
   * パラメータthetaを返します。
   * 
   * @return パラメータtheta
   */
  public double getTheta() {
    return this.theta;
  }

  /**
   * パラメータthetaを設定します。
   * 
   * @param theta パラメータtheta
   */
  public void setTheta(double theta) {
    this.theta = theta;
  }
}
