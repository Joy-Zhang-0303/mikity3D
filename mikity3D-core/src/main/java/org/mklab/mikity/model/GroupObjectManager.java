/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mklab.mikity.model.sampler.AbstractDataSampler;
import org.mklab.mikity.model.sampler.ClosenessDataSampler;
import org.mklab.mikity.model.sampler.DataSampler;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.nfc.matrix.DoubleMatrix;


/**
 * グループオブジェクトを管理するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.10 $.2005/01/14
 */
public class GroupObjectManager {
  /** グループオブジェクト。 */
  private List<GroupObject> groupObjects = new ArrayList<>();
  /** 可動グループオブジェクト。 */
  private List<GroupObjectDataSampler> movingGroupObjects = new ArrayList<>();

  /** データの個数 */
  private int dataSize;
  /** 開始時間 */
  private double startTime;
  /** 終了時間 */
  private double stopTime;

  /** アニメーションが存在するならばtrue */
  private boolean hasAnimation = false;
  
  /** ソース。 */
  private Map<String, DoubleMatrix> sources = new HashMap<>();

  /**
   * 指定された時刻のデータでグループオブジェクトを更新します。
   * 
   * @param t 時刻
   */
  public void updateGroupObjects(final double t) {
    AbstractDataSampler.clearTimeNumbers();
    
    for (final GroupObjectDataSampler movingGroupObject : this.movingGroupObjects) {
      final GroupObject groupObject = movingGroupObject.groupObject;
      final DataSampler sampler = movingGroupObject.sampler;
      final Coordinate coordinte = sampler.getCoordinate(t);
      groupObject.setCoordinate(coordinte);
    }
  }
  
  /**
   * ソースを追加します。
   * 
   * @param id ID
   * @param data データ
   */
  public void addSource(final String id, DoubleMatrix data) {
    this.sources.put(id, data);
  }

  /**
   * 全てのアニメーションのソースが登録されているか判定します
   * 
   * @return 全てのアニメーションのソースが登録されていればtrue
   */
  public boolean isSourceReady() {
    for (final GroupObject groupObject : this.groupObjects) {
      final GroupModel group = groupObject.getGroup();
      if (group == null || group.hasAnimation() == false) {
        continue;
      }
      
      if (isSourceReady(group.getAnimations()) == false) {
        return false;
      }
    }
    
    return true;
  }

  /**
   * 可動グループを準備します。
   */
  public void prepareMovingGroupObjects() {
    this.dataSize = 0;
    this.startTime = 0;
    this.stopTime = 0;

    this.movingGroupObjects.clear();

    for (final GroupObject groupObject : this.groupObjects) {
      final GroupModel group = groupObject.getGroup();
      if (group == null || group.hasAnimation() == false) {
        continue;
      }
      
      final DataSampler sampler = createSampler(group.getAnimations());
      addMovingGroup(groupObject, sampler);
    }
  }

  /**
   * 可動グループオブジェクトを追加します。
   * 
   * @param groupObject グループオブジェクト
   * @param sampler データ抽出器
   */
  private void addMovingGroup(final GroupObject groupObject, final DataSampler sampler) {
    this.movingGroupObjects.add(new GroupObjectDataSampler(groupObject, sampler));
    this.dataSize = Math.max(this.dataSize, sampler.getDataSize());
    this.startTime = Math.min(this.startTime, sampler.getStartTime());
    this.stopTime = Math.max(this.stopTime, sampler.getEndTime());
  }

  /**
   * データ抽出器を生成します。
   * 
   * @param animation アニメーション
   */
  private DataSampler createSampler(final AnimationModel[] animations) {
    final DataSampler sampler = new ClosenessDataSampler(this.sources);

    for (final AnimationModel animation : animations) {
      final String target = animation.getTarget();
      final CoordinateParameterType type = CoordinateParameterType.getType(target);
      
      final SourceModel source = animation.getSource();
      sampler.sample(type, source);
    }

    return sampler;
  }
  
  /**
   * アニメーションのソースが登録されているか判定します。
   * 
   * @param animation アニメーション
   * @return アニメーションのソースが登録されていればtrue
   */
  private boolean isSourceReady(final AnimationModel[] animations) {
    for (final AnimationModel animation : animations) {
      final SourceModel source = animation.getSource();
      final String sourceId = source.getId();
      if (this.sources == null || this.sources.containsKey(sourceId) == false) { 
        return false;
      }
      final DoubleMatrix data = this.sources.get(source.getId());
      if (data.getRowSize() < source.getNumber()) {
        return false;
      }
    }

    return true;
  }

  /**
   * データの個数を返します。
   * 
   * @return データの個数
   */
  public int getDataSize() {
    return this.dataSize;
  }

  /**
   * 終了時間を返します。
   * 
   * @return 終了時間
   */
  public double getStopTime() {
    return this.stopTime;
  }

  /**
   * 開始時間を返します。
   * 
   * @return 開始時間
   */
  public double getStartTime() {
    return this.startTime;
  }

  /**
   * アニメーションが存在するか設定します。
   * 
   * @param hasAnimation アニメーションが存在するならばtrue
   */
  public void setHasAnimation(final boolean hasAnimation) {
    this.hasAnimation = hasAnimation;
  }

  /**
   * アニメーションが存在するから判定します。
   * 
   * @return　アニメーソンが存在するならばtrue
   */
  public boolean hasAnimation() {
    return this.hasAnimation;
  }
  
  /**
   * ソースが登録されているか判定します。
   * 
   * @return ソースが登録されていればtrue
   */
  public boolean hasSource() {
    return this.sources.size() != 0;
  }

  /**
   * グループオブジェクトを登録します。
   * 
   * @param groupObject グループオブジェクト
   */
  public void addGroupObject(final GroupObject groupObject) {
    this.groupObjects.add(groupObject);
  }
  
  /**
   * 全ての登録されているグループオブジェクトを削除します。
   */
  public void clearGroupObjects() {
    this.groupObjects.clear();
  }

}
