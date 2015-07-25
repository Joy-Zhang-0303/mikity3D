/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.picker.ClosenessDataPicker;
import org.mklab.mikity.model.picker.DataPicker;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
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
  private List<ObjectGroupDataPicker> movingGroups = new ArrayList<>();
  
  /** データの個数 */
  private int dataSize;
  /** 開始時間 */
  private double startTime;
  /** 終了時間 */
  private double endTime;

  /** 座標パラメータを使用するならばtrue */
  private boolean hasCoordinateParameter = false;

  /**
   * 可動グループを追加します。
   * 
   * @param objectGroup オブジェクトグループ
   * @param picker データ抽出器
   */
  private void addMovingGroup(final ObjectGroup objectGroup, final DataPicker picker) {
    this.movingGroups.add(new ObjectGroupDataPicker(objectGroup, picker));
    this.dataSize = Math.max(this.dataSize, picker.getDataSize());
    this.startTime = Math.min(this.startTime, picker.getStartTime());
    this.endTime = Math.max(this.endTime, picker.getEndTime());
  }

  /**
   * 指定された時刻のデータでオブジェクトグループを更新します。
   * 
   * @param t 時刻
   */
  public void updateObjectGroups(final double t) {
    for (final ObjectGroupDataPicker movingGroup: this.movingGroups) {
      final ObjectGroup objectGroup = movingGroup.objectGroup;
      final DataPicker dataPicker = movingGroup.dataPicker;
      objectGroup.setCoordinateParameter(dataPicker.getCoordinateParameter(t));
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
      
      final AnimationModel[] links =   group.getAnimations();
      if (links.length != 0) {
        final DataPicker picker = createPicker(data, links);
        addMovingGroup(objectGroup, picker);
      }
    }
  }

  /**
   * データ抽出器を生成します。
   * 
   * @param data 時系列データ
   * @param links リンクデータ
   */
  private DataPicker createPicker(final Matrix data, final AnimationModel[] links) {
    final DataPicker picker = new ClosenessDataPicker(data);
    for (final AnimationModel link : links) {
      if (link.hasCoordinateParameter()) {
        if (link.hasNumber()) {
          final int dataNumber = link.getNumber();
          final String parameterName = link.getTarget();
          final CoordinateParameterType type;

          if (parameterName.equals("translationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.TRANSLATION_X;
          } else if (parameterName.equals("translationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.TRANSLATION_Y;
          } else if (parameterName.equals("translationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.TRANSLATION_Z;
          } else if (parameterName.equals("rotationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.ROTATION_X;
          } else if (parameterName.equals("rotationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.ROTATION_Y;
          } else if (parameterName.equals("rotationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.ROTATION_Z;
          } else {
            throw new IllegalAccessError(Messages.getString("MovableGroupManager.2")); //$NON-NLS-1$
          }
          picker.pickup(type, dataNumber);
        }
      }
    }
     
    return picker;
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
   * 座標パラメータを使用しているか設定します。
   * 
   * @param hasCoordinateParameter 座標パラメータ使用するならばtrue
   */
  public void setHasCoordinateParameter(final boolean hasCoordinateParameter) {
    this.hasCoordinateParameter = hasCoordinateParameter;
  }

  /**
   * 座標パラメータを使用しているか判定します。
   * 
   * @return　座標パラメータを使用するならばtrue
   */
  public boolean hasCoordinateParameter() {
    return this.hasCoordinateParameter;
  }
  
  /**
   * グループとオブジェクトグループを対応付けします。
   * @param objectGroup オブジェクトグループ
   */
  public static void addObjectGroup(final ObjectGroup objectGroup) {
    OBJECT_GROUPS.add(objectGroup);
  }
 
}
