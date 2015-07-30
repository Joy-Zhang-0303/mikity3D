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

import org.mklab.mikity.model.sampler.ClosenessDataSampler;
import org.mklab.mikity.model.sampler.DataSampler;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.nfc.matrix.DoubleMatrix;


/**
 * オブジェクトグループを管理するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.10 $.2005/01/14
 */
public class ObjectGroupManager {
  /** オブジェクトグループ。 */
  private static List<ObjectGroup> OBJECT_GROUPS = new ArrayList<>();
  /** 可動グループ。 */
  private List<ObjectGroupDataSampler> movingGroups = new ArrayList<>();

  /** データの個数 */
  private int dataSize;
  /** 開始時間 */
  private double startTime;
  /** 終了時間 */
  private double endTime;

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
    for (final ObjectGroupDataSampler movingGroup : this.movingGroups) {
      final ObjectGroup objectGroup = movingGroup.objectGroup;
      final DataSampler sampler = movingGroup.sampler;
      objectGroup.setCoordinateParameter(sampler.getCoordinateParameter(t));
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
   * 時系列データを設定します。
   * 
   * @param data 時系列データ
   */
  public void setData(final DoubleMatrix data) {
    this.dataSize = 0;
    this.startTime = 0;
    this.endTime = 0;

    this.movingGroups.clear();

    for (final ObjectGroup objectGroup : OBJECT_GROUPS) {
      final GroupModel group = objectGroup.getGroup();
      if (group == null || group.hasAnimation() == false) {
        continue;
      }
      
      final DataSampler sampler = createSampler(data, group.getAnimations());
      addMovingGroup(objectGroup, sampler);
    }
  }
  
  

  /**
   * 可動グループを追加します。
   * 
   * @param objectGroup オブジェクトグループ
   * @param sampler データ抽出器
   */
  private void addMovingGroup(final ObjectGroup objectGroup, final DataSampler sampler) {
    this.movingGroups.add(new ObjectGroupDataSampler(objectGroup, sampler));
    this.dataSize = Math.max(this.dataSize, sampler.getDataSize());
    this.startTime = Math.min(this.startTime, sampler.getStartTime());
    this.endTime = Math.max(this.endTime, sampler.getEndTime());
  }

  /**
   * データ抽出器を生成します。
   * 
   * @param data 時系列データ
   * @param animation アニメーション
   */
  private DataSampler createSampler(final DoubleMatrix data, final AnimationModel[] animations) {
    final DataSampler sampler = new ClosenessDataSampler(data);

    for (final AnimationModel animation : animations) {
      final String target = animation.getTarget();
      final SourceModel source = animation.getSource();
      final int number = source.getNumber();
      final CoordinateParameterType type = CoordinateParameterType.getType(target);
      sampler.sample(type, number);
    }

    return sampler;
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
  public double getEndTime() {
    return this.endTime;
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
   * オブジェクトグループを登録します。
   * 
   * @param objectGroup オブジェクトグループ
   */
  public static void addObjectGroup(final ObjectGroup objectGroup) {
    OBJECT_GROUPS.add(objectGroup);
  }
  
  /**
   * 全ての登録されているオブジェクトグループを削除します。
   */
  public static void clearObjectGroups() {
    OBJECT_GROUPS.clear();
  }

}
