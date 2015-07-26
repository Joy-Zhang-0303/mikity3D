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
public class Mikity3d implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  /** scenes */
  @ElementList(type=SceneModel.class, inline=true)
  private List<SceneModel> scenes;

  /** configurations */
  @ElementList(type=ConfigurationModel.class, inline=true)
  private List<ConfigurationModel> configurations;

  /**
   * 新しく生成された<code>Mikity3d</code>オブジェクトを初期化します。
   */
  public Mikity3d() {
    this.scenes = new ArrayList<>();
    this.configurations = new ArrayList<>();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Mikity3d clone() {
    try {
      final Mikity3d ans = (Mikity3d)super.clone();
      ans.scenes = new ArrayList<>();
      for (final SceneModel scene : this.scenes) {
        ans.scenes.add(scene.clone());
      }
      ans.configurations = new ArrayList<>();
      for (final ConfigurationModel configuration : this.configurations) {
        ans.configurations.add(configuration.clone());
      }

      return ans;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Method addConfig
   * 
   * @param configuration コンフィグ
   */
  public void addConfiguration(ConfigurationModel configuration) {
    this.configurations.add(configuration);
  }

  /**
   * Method addModel
   * 
   * @param scene シーン
   */
  public void addScene(SceneModel scene) {
    this.scenes.add(scene);
  }

  /**
   * 指定された指数のConfigurationを返します。
   * 
   * @param index インデックス
   * @return 指定された指数のConfiguration
   */
  public ConfigurationModel getConfiguration(int index) {
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
    result = prime * result + ((scenes == null) ? 0 : scenes.hashCode());
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
    if (this.scenes == null) {
      if (other.scenes != null) {
        return false;
      }
    } else if (!this.scenes.equals(other.scenes)) {
      return false;
    }
    return true;
  }

  /**
   * 指定された指数のシーンを返します。
   * 
   * @param index インデックス
   * @return シーン
   */
  public SceneModel getScene(int index) {
    if ((index < 0) || (index > this.scenes.size())) {
      throw new IndexOutOfBoundsException();
    }

    return this.scenes.get(index);
  }

  /**
   * @return XML data
   */
  public Mikity3d getMikity3dData() {
    return this;
  }
}
