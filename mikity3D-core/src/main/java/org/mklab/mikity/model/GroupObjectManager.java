/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
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
  private List<GroupObjectDataSampler> movingGroups = new ArrayList<>();

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
   * 指定された時刻のデータでオブジェクトグループを更新します。
   * 
   * @param t 時刻
   */
  public void updateObjectGroups(final double t) {
    AbstractDataSampler.clearTimeNumbers();
    
    for (final GroupObjectDataSampler movingGroup : this.movingGroups) {
      final GroupObject objectGroup = movingGroup.groupObject;
      final DataSampler sampler = movingGroup.sampler;
      final Coordinate coordinte = sampler.getCoordinate(t);
      objectGroup.setCoordinate(coordinte);
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
    for (final GroupObject objectGroup : this.groupObjects) {
      final GroupModel group = objectGroup.getGroup();
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
  public void prepareMovingGroups() {
    this.dataSize = 0;
    this.startTime = 0;
    this.stopTime = 0;

    this.movingGroups.clear();

    for (final GroupObject objectGroup : this.groupObjects) {
      final GroupModel group = objectGroup.getGroup();
      if (group == null || group.hasAnimation() == false) {
        continue;
      }
      
      final DataSampler sampler = createSampler(group.getAnimations());
      addMovingGroup(objectGroup, sampler);
    }
  }

  /**
   * 可動グループを追加します。
   * 
   * @param objectGroup オブジェクトグループ
   * @param sampler データ抽出器
   */
  private void addMovingGroup(final GroupObject objectGroup, final DataSampler sampler) {
    this.movingGroups.add(new GroupObjectDataSampler(objectGroup, sampler));
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
   * オブジェクトグループを登録します。
   * 
   * @param objectGroup オブジェクトグループ
   */
  public void addObjectGroup(final GroupObject objectGroup) {
    this.groupObjects.add(objectGroup);
  }
  
  /**
   * 全ての登録されているオブジェクトグループを削除します。
   */
  public void clearObjectGroups() {
    this.groupObjects.clear();
  }

}
