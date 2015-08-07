/*
 * Created on 2015/08/05
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import org.mklab.mikity.util.Color4;
import org.mklab.mikity.util.ColorConstant;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;


/**
 * 色を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/08/05
 */
@Root(name="color")
public class ColorModel implements java.io.Serializable, Cloneable {
  /** */
  private static final long serialVersionUID = 1L;

  /** R成分。 */
  @Attribute(name="r", required=false)
  private int r;
  
  /** G成分。 */
  @Attribute(name="g", required=false)
  private int g;
  
  /** B成分。 */
  @Attribute(name="b", required=false)
  private int b;
  
  /** アルファ値。 */
  @Attribute(name="alpha", required=false)
  private int alpha;
  
  /** 名前。 */
  @Attribute(name="name", required=false)
  private String name;
  
  /**
   * 新しく生成された<code>ColorModel</code>オブジェクトを初期化します。
   */
  public ColorModel() {
    this.r = 0;
    this.g = 0;
    this.b = 0;
    this.alpha = 255;
    this.name = "black"; //$NON-NLS-1$
  }
  
  /**
   * 新しく生成された<code>ColorModel</code>オブジェクトを初期化します。
   * @param name 名前
   */
  public ColorModel(String name) {
    this.name = name;
    
    final Color4 color = ColorConstant.getColor(name);
    this.r = color.getR();
    this.g = color.getG();
    this.b = color.getB();
    this.alpha = color.getAlpha();
  }
  
  /**
   * 新しく生成された<code>ColorModel</code>オブジェクトを初期化します。
   * @param r R
   * @param g G
   * @param b B
   */
  public ColorModel(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.alpha = 255;
  }

  /**
   * 新しく生成された<code>ColorModel</code>オブジェクトを初期化します。
   * @param r R
   * @param g G
   * @param b B
   * @param alpha アルファ値
   */
  public ColorModel(int r, int g, int b, int alpha) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.alpha = alpha;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public ColorModel clone() {
    try {
      return (ColorModel)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.alpha;
    result = prime * result + this.b;
    result = prime * result + this.g;
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result = prime * result + this.r;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ColorModel other = (ColorModel)obj;
    if (this.alpha != other.alpha) return false;
    if (this.b != other.b) return false;
    if (this.g != other.g) return false;
    if (this.name == null) {
      if (other.name != null) return false;
    } else if (!this.name.equals(other.name)) return false;
    if (this.r != other.r) return false;
    return true;
  }

  /**
   * R成分を返します。
   * @return R成分
   */
  public int getR() {
    return this.r;
  }
  
  /**
   * G成分を返します。
   * @return G成分
   */
  public int getG() {
    return this.g;
  }
  
  /**
   * B成分を返します。
   * @return B成分
   */
  public int getB() {
    return this.b;
  }
  
  /**
   * alpha値を返します。
   * @return アルファ値
   */
  public int getAlpha() {
    return this.alpha;
  }
  
  /**
   * R成分を返します。
   * @return R成分
   */
  public float getRf() {
    return this.r/255f;
  }
  
  /**
   * G成分を返します。
   * @return G成分
   */
  public float getGf() {
    return this.g/255f;
  }
  
  /**
   * B成分を返します。
   * @return B成分
   */
  public float getBf() {
    return this.b/255f;
  }
  
  /**
   * alpha値を返します。
   * @return アルファ値
   */
  public float getAlphaf() {
    return this.alpha/255f;
  }
  
  /**
   * 名前を返します。
   * 
   * @return 名前
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * R成分を設定します。
   * @param r R成分

   */
  public void setR(int r) {
    this.r = r;
  }
  
  /**
   * G成分を設定します。
   * @param g G成分

   */
  public void setG(int g) {
    this.g = g;
  }

  /**
   * B成分を設定します。
   * @param b B成分

   */
  public void setB(int b) {
    this.b = b;
  }
  
  /**
   * アルファ値を設定します。
   * @param alpha アルファ値

   */
  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }

  /**
   * 名前を設定します。
   * 
   * @param name 名前
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    if (this.name != null && this.name.length() > 0) {
      return this.name;
    }
    return "(" + this.r + ", " + this.g + ", " + this.b + ", " + this.alpha + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
  }
}
