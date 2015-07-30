/*
 * Created on 2015/07/28
 * Copyright (C) 2015 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml.simplexml.model;

import org.simpleframework.xml.Attribute;


/**
 * ソースを表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/07/28
 */
public class SourceModel implements java.io.Serializable, Cloneable {
  /** */
  private static final long serialVersionUID = 1L;

  /** ID。 */
  @Attribute(name="id", required=true)
  private String id;
  
  
  /** データの番号 */
  @Attribute(name="number", required=true)
  private int number;

  /**
   * 新しく生成された<code>SourceModel</code>オブジェクトを初期化します。
   */
  public SourceModel() {
    this.id = "1"; //$NON-NLS-1$
    this.number = 0;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public SourceModel clone() {
    try {
      return (SourceModel)super.clone();
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
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + this.number;
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
    SourceModel other = (SourceModel)obj;
    if (this.id == null) {
      if (other.id != null) return false;
    } else if (!this.id.equals(other.id)) return false;
    if (this.number != other.number) return false;
    return true;
  }

  /**
   * データの番号を返します。
   * 
   * @return データの番号
   */
  public int getNumber() {
    return this.number;
  }
  
  /**
   * データの番号を設定します。
   * 
   * @param number データの番号
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * データの番号をもつか判別します。
   * 
   * @return データの番号をもつならばtrue
   */
  public boolean hasNumber() {
    if (this.number != 0) {
      return true;
    }
    return false;
  }
  
  /**
   * IDを返します。
   * 
   * @return ID
   */
  public String getId() {
    return this.id;
  }
  
  /**
   * IDを設定します。
   * 
   * @param id ID
   */
  public void setId(String id) {
    this.id = id;
  }

}
