/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.sampler.ClosenessDataSampler;
import org.mklab.mikity.model.sampler.DataSampler;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.nfc.matrix.Matrix;


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
   * 指定された時刻のデータでオブジェクトグループを更新します。
   * 
   * @param t 時刻
   */
  public void updateObjectGroups(final double t) {
    for (final ObjectGroupDataSampler movingGroup : this.movingGroups) {
      final ObjectGroup group = movingGroup.group;
      final DataSampler sampler = movingGroup.sampler;
      group.setCoordinateParameter(sampler.getCoordinateParameter(t));
    }
  }

  /**
   * 可動グループを登録します。
   * 
   * @param data 時系列データ
   */
  private void registerMovingGroups(final Matrix data) {
    for (final ObjectGroup objectGroup : OBJECT_GROUPS) {
      final GroupModel group = objectGroup.getGroup();
      if (group == null) {
        continue;
      }

      final AnimationModel[] animations = group.getAnimations();
      if (animations.length != 0) {
        final DataSampler sampler = createSampler(data, animations);
        addMovingGroup(objectGroup, sampler);
      }
    }
  }

  /**
   * データ抽出器を生成します。
   * 
   * @param data 時系列データ
   * @param animations リンクデータ
   */
  private DataSampler createSampler(final Matrix data, final AnimationModel[] animations) {
    final DataSampler sampler = new ClosenessDataSampler(data);
    for (final AnimationModel animation : animations) {
      if (animation.exists()) {
        final SourceModel source = animation.getSource();
        final int number = source.getNumber();
        final String target = animation.getTarget();
        final CoordinateParameterType type;

        if (target.equals("translationX")) { //$NON-NLS-1$
          type = CoordinateParameterType.TRANSLATION_X;
        } else if (target.equals("translationY")) { //$NON-NLS-1$
          type = CoordinateParameterType.TRANSLATION_Y;
        } else if (target.equals("translationZ")) { //$NON-NLS-1$
          type = CoordinateParameterType.TRANSLATION_Z;
        } else if (target.equals("rotationX")) { //$NON-NLS-1$
          type = CoordinateParameterType.ROTATION_X;
        } else if (target.equals("rotationY")) { //$NON-NLS-1$
          type = CoordinateParameterType.ROTATION_Y;
        } else if (target.equals("rotationZ")) { //$NON-NLS-1$
          type = CoordinateParameterType.ROTATION_Z;
        } else {
          throw new IllegalAccessError(Messages.getString("MovableGroupManager.2")); //$NON-NLS-1$
        }

        sampler.sample(type, number);
      }
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
   * 時系列データを設定します。
   * 
   * @param data 時系列データ
   */
  public void setData(final Matrix data) {
    this.dataSize = 0;
    this.startTime = 0;
    this.endTime = 0;

    this.movingGroups.clear();
    registerMovingGroups(data);
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
   * グループとオブジェクトグループを対応付けします。
   * 
   * @param objectGroup オブジェクトグループ
   */
  public static void addObjectGroup(final ObjectGroup objectGroup) {
    OBJECT_GROUPS.add(objectGroup);
  }

}
