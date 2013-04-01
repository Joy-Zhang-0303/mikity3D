package org.mklab.mikity.model.xml.simplexml;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


/**
 * Mikity3Dのデータを表すクラスです。
 * 
 * @version $Revision: 1.2 $ $Date: 2007/08/03 03:30:27 $
 */
@Root(name="mikity3d")
public class Mikity3d implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  /** models */
  @ElementList(type=Mikity3dModel.class, inline=true)
  private List<Mikity3dModel> models;

  /** configurations */
  @ElementList(type=Mikity3dConfiguration.class, inline=true)
  private List<Mikity3dConfiguration> configurations;

  /**
   * 新しく生成された<code>Mikity3d</code>オブジェクトを初期化します。
   */
  public Mikity3d() {
    this.models = new ArrayList<Mikity3dModel>();
    this.configurations = new ArrayList<Mikity3dConfiguration>();
  }

  /**
   * Method addConfig
   * 
   * @param configuration コンフィグ
   */
  public void addConfiguration(Mikity3dConfiguration configuration) {
    this.configurations.add(configuration);
  }

  /**
   * Method addModel
   * 
   * @param model モデル
   */
  public void addModel(Mikity3dModel model) {
    this.models.add(model);
  }

  /**
   * 指定された指数のConfigurationを返します。
   * 
   * @param index インデックス
   * @return 指定された指数のConfiguration
   */
  public Mikity3dConfiguration getConfiguration(int index) {
    if ((index < 0) || (index > this.configurations.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.configurations.get(index);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unqualified-field-access")
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((configurations == null) ? 0 : configurations.hashCode());
    result = prime * result + ((models == null) ? 0 : models.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Mikity3d other = (Mikity3d)obj;
    if (this.configurations == null) {
      if (other.configurations != null) {
        return false;
      }
    } else if (!this.configurations.equals(other.configurations)) {
      return false;
    }
    if (this.models == null) {
      if (other.models != null) {
        return false;
      }
    } else if (!this.models.equals(other.models)) {
      return false;
    }
    return true;
  }

  /**
   * 指定された指数のモデルを返します。
   * 
   * @param index インデックス
   * @return モデル
   */
  public Mikity3dModel getModel(int index) {
    if ((index < 0) || (index > this.models.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.models.get(index);
  }

  /**
   * @return XML data
   */
  public Mikity3d getMikity3dData() {
    return this;
  }
}
