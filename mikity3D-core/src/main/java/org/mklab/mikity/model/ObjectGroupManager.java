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

import org.mklab.mikity.model.picker.ClosenessDataPicker;
import org.mklab.mikity.model.picker.DataPicker;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.nfc.matrix.Matrix;


/**
 * オブジェクトグループを管理するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.10 $.2005/01/14
 */
public class ObjectGroupManager {
  /** グループととオブジェクトグループのマップ */
  private static Map<Group, ObjectGroup> OBJECT_GROUPS = new HashMap<>();
  /** 可動グループ */
  private List<ObjectGroup> objectGroups = new ArrayList<>();
  /** データ抽出器 */
  private Map<ObjectGroup, DataPicker> pickers = new HashMap<>();
  
  /** データの個数 */
  private int dataSize;
  /** 開始時間 */
  private double startTime;
  /** 終了時間 */
  private double endTime;

  /** ルートグループ */
  private Mikity3d root;

  /** DHパラメータを使用するならばtrue */
  private boolean hasDHParameter = false;
  /** 座標パラメータを使用するならばtrue */
  private boolean hasCoordinateParameter = false;

  /**
   * 新しく生成された<code>ObjectGroupManager</code>オブジェクトを初期化します。
   * @param root ルート
   */
  public ObjectGroupManager(final Mikity3d root) {
    this.root = root;
  }

  /**
   * 可動グループを追加します。
   * 
   * @param objectGroup オブジェクトグループ
   * @param picker データ抽出器
   */
  private void addObjectGroup(final ObjectGroup objectGroup, final DataPicker picker) {
    this.objectGroups.add(objectGroup);
    this.pickers.put(objectGroup, picker);
    this.dataSize = Math.max(this.dataSize, picker.getDataSize());
    this.startTime = Math.min(this.startTime, picker.getStartTime());
    this.endTime = Math.max(this.endTime, picker.getEndTime());
  }

  /**
   * 指定された時刻のデータで可動グループを更新します。
   * 
   * @param t 時刻
   */
  public void updateObjectGroupsWithDHParameter(final double t) {
    for (final ObjectGroup objectGroup : this.objectGroups) {
      final DataPicker picker = this.pickers.get(objectGroup);
      objectGroup.setDHParameter(picker.getDHParameter(t));
    }
  }

  /**
   * 指定された時刻のデータでオブジェクトグループを更新します。
   * 
   * @param t 時刻
   */
  public void updateObjectGroupsWithCoordinateParameter(final double t) {
    for (final ObjectGroup objectGroup : this.objectGroups) {
      final DataPicker picker = this.pickers.get(objectGroup);
      objectGroup.setCoordinateParameter(picker.getCoordinateParameter(t));
     }
  }

  /**
   * オブジェクトグループを登録します。
   * 
   * @param groups グループ
   * @param data 時系列データ
   */
  private void registerObjectGroups(final Group[] groups, final Matrix data) {
    for (final Group group : groups) {
      final ObjectGroup objectGroup = OBJECT_GROUPS.get(group);
      final LinkData[] links = group.getLinkData();
      if (links.length != 0) {
        final DataPicker picker = createPicker(data, links);
        addObjectGroup(objectGroup, picker);
      }

      registerObjectGroups(group.getGroups(), data);
    }
  }

  /**
   * データ抽出器を生成します。
   * 
   * @param data 時系列データ
   * @param links リンクデータ
   */
  private DataPicker createPicker(final Matrix data, final LinkData[] links) {
    final DataPicker picker = new ClosenessDataPicker(data);
    for (final LinkData link : links) {
      if (link.hasDHParameter()) {
        if (link.hasNumber()) {
          final int dataNumber = link.getNumber();
          final String parameterName = link.getTarget();
          final DHParameterType type;

          if (parameterName.equals("a")) { //$NON-NLS-1$
            type = DHParameterType.A;
          } else if (parameterName.equals("alpha")) { //$NON-NLS-1$
            type = DHParameterType.ALPHA;
          } else if (parameterName.equals("d")) { //$NON-NLS-1$
            type = DHParameterType.D;
          } else if (parameterName.equals("theta")) { //$NON-NLS-1$
            type = DHParameterType.THETA;
          } else {
            throw new IllegalArgumentException(Messages.getString("MovableGroupManager.0")); //$NON-NLS-1$
          }
          picker.pickup(type, dataNumber);
        }
      } else if (link.hasCoordinateParameter()) {
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

    this.objectGroups.clear();
    registerObjectGroups(this.root.getModel(0).getGroups(), data);
  }
  
  /**
   * DHパラメータを使用しているか設定します。
   * 
   * @param hasDHParameter DHパラメータ使用するならばtrue
   */
  public void setHasDHParameter(final boolean hasDHParameter) {
    this.hasDHParameter = hasDHParameter;
  }

  /**
   * DHパラメータを使用しているか判定します
   * 
   * @return　DHパラメータの使用をするならばtrue
   */
  public boolean hasDHParameter() {
    return this.hasDHParameter;
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
   * @param group グループ
   * @param objectGroup オブジェクトグループ
   */
  public static void assignGroup(final Group group, final ObjectGroup objectGroup) {
    OBJECT_GROUPS.put(group, objectGroup);
  }
 
}
