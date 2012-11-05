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

import org.mklab.mikity.java3d.MyTransformGroup;
import org.mklab.mikity.jogl.JoglTransformGroup;
import org.mklab.mikity.picker.ClosenessDataPicker;
import org.mklab.mikity.picker.DataPicker;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.LinkData;
import org.mklab.nfc.matrix.Matrix;


/**
 * 動かせるグループを管理するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.10 $.2005/01/14
 */
public class MovableGroupManager {
  /** 動かせるグループのリスト */
  private List<MovableGroup> movableGroups = new ArrayList<MovableGroup>();
  private Map<MovableGroup, DataPicker> pickers = new HashMap<MovableGroup, DataPicker>();

  private static Map<Group, MovableGroup> MOVABLE_GROUPS = new HashMap<Group, MovableGroup>();
  
  private int dataCount;
  private double startTime;
  private double endTime;

  private Matrix data;

  private Jamast root;

  private boolean hasDHParameter = false;
  private boolean hasCoordinateParameter = false;

  /**
   * コンストラクター
   * 
   * @param root ルート
   */
  public MovableGroupManager(Jamast root) {
    this.root = root;
  }

  /**
   * 移動可能なグループを追加する。
   * 
   * @param group グループ
   * @param picker データ抽出器
   */
  private void addMovableGroup(final MovableGroup group, final DataPicker picker) {
    this.movableGroups.add(group);
    this.pickers.put(group, picker);

    this.dataCount = Math.max(this.dataCount, picker.getDataCount());
    this.startTime = Math.min(this.startTime, picker.getStartTime());
    this.endTime = Math.max(this.endTime, picker.getEndTime());
  }
  

  /**
   * 時刻毎のリンクデータ(DHパラメータ)を取得し、アニメーションを実行します。
   * 
   * @param t 時刻
   */
  public void performAnimationWithDHParameter(double t) {
    for (final MovableGroup group : this.movableGroups) {
      final DataPicker picker = this.pickers.get(group);
      final DHParameter parameter = picker.getDHParameter(t);
      
      if (parameter != null) {
        group.setDHParameter(parameter);
      }
    }
  }

  /**
   * 時刻毎のリンクデータ(座標パラメータ)を取得し、アニメーションを実行します。
   * 
   * @param t 時刻
   */
  public void performAnimationWithCoordinateParameter(double t) {
    for (final MovableGroup group : this.movableGroups) {
      final DataPicker picker = this.pickers.get(group);
      final CoordinateParameter parameter = picker.getCoordinateParameter(t);
      
      if (parameter != null) {
        group.setCoordinateParameter(parameter);
      }
    }
  }

  /**
   * グループを追加します。
   * 
   * @param groups 追加するグループ
   */
  private void addGroups(final Group[] groups) {
    for (int i = 0; i < groups.length; i++) {
      final Group group = groups[i];
      final MovableGroup tg = MOVABLE_GROUPS.get(group);
      setMovableLinkData(group.loadLinkData(), tg);
      addGroups(group.loadGroups());
    }
  }

  /**
   * 移動可能なグループリストの設定
   * 
   * @param linkData リンクデータ
   * @param group TransformGroup
   */
  private void setMovableLinkData(LinkData[] linkData, MovableGroup group) {
    if (linkData.length == 0) {
      return;
    }
    final DataPicker picker = new ClosenessDataPicker(this.data);

    for (int i = 0; i < linkData.length; i++) {
      if (linkData[i].hasDHParameter()) {
        if (linkData[i].hasColumn()) {
          final int column = linkData[i].loadColumn();
          final String target = linkData[i].loadTarget();
          int type = 0;

          if (target.equals("a")) { //$NON-NLS-1$
            type = DHParameter.A;
          } else if (target.equals("alpha")) { //$NON-NLS-1$
            type = DHParameter.ALPHA;
          } else if (target.equals("d")) { //$NON-NLS-1$
            type = DHParameter.D;
          } else if (target.equals("theta")) { //$NON-NLS-1$
            type = DHParameter.THETA;
          } else {
            throw new IllegalArgumentException(Messages.getString("MovableGroupManager.0")); //$NON-NLS-1$
          }
          picker.addMoveTypeDH(type, column);
        }
        if (linkData[i].hasConst()) {
          final double value = linkData[i].loadConst();
          final String target = linkData[i].loadTarget();
          int type = 0;

          if (target.equals("a")) { //$NON-NLS-1$
            type = DHParameter.A;
          } else if (target.equals("alpha")) { //$NON-NLS-1$
            type = DHParameter.ALPHA;
          } else if (target.equals("d")) { //$NON-NLS-1$
            type = DHParameter.D;
          } else if (target.equals("theta")) { //$NON-NLS-1$
            type = DHParameter.THETA;
          } else {
            throw new IllegalAccessError(Messages.getString("MovableGroupManager.1")); //$NON-NLS-1$
          }
          picker.setDHParameter(type, value);
        }
      } else if (linkData[i].hasCoordinateParameter()) {
        if (linkData[i].hasColumn()) {
          final int column = linkData[i].loadColumn();
          final String target = linkData[i].loadTarget();
          int type = 0;

          if (target.equals("locationX")) { //$NON-NLS-1$
            type = 1;
          } else if (target.equals("locationY")) { //$NON-NLS-1$
            type = 2;
          } else if (target.equals("locationZ")) { //$NON-NLS-1$
            type = 3;
          } else if (target.equals("rotationX")) { //$NON-NLS-1$
            type = 4;
          } else if (target.equals("rotationY")) { //$NON-NLS-1$
            type = 5;
          } else if (target.equals("rotationZ")) { //$NON-NLS-1$
            type = 6;
          } else {
            throw new IllegalAccessError(Messages.getString("MovableGroupManager.2")); //$NON-NLS-1$
          }
          picker.addMoveTypeCoordinate(type, column);
        }
        if (linkData[i].hasConst()) {
          final double value = linkData[i].loadConst();
          final String target = linkData[i].loadTarget();
          int type = 0;

          if (target.equals("locationX")) { //$NON-NLS-1$
            type = 1;
          } else if (target.equals("locationY")) { //$NON-NLS-1$
            type = 2;
          } else if (target.equals("locationZ")) { //$NON-NLS-1$
            type = 3;
          } else if (target.equals("rotationX")) { //$NON-NLS-1$
            type = 4;
          } else if (target.equals("rotationY")) { //$NON-NLS-1$
            type = 5;
          } else if (target.equals("rotationZ")) { //$NON-NLS-1$
            type = 6;
          } else {
            throw new IllegalAccessError(Messages.getString("MovableGroupManager.3")); //$NON-NLS-1$
          }
          picker.setCoordinateParameter(type, value);
        }
      }
    }
    addMovableGroup(group, picker);
  }

  /**
   * データの個数を返します。
   * 
   * @return データの個数
   */
  public int getDataCount() {
    return this.dataCount;
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
    this.data = data;
  }

  /**
   * 移動可能なグループを更新します。
   */
  public void updateMovableGroups() {
    this.movableGroups.clear();
    addGroups(this.root.loadModel(0).loadGroup());
  }
  
  /**
   * DHパラメータの使用の有無を設定します。
   * 
   * @param hasDHParameter DHパラメータ使用の有無
   */
  public void setHasDHParameter(boolean hasDHParameter) {
    this.hasDHParameter = hasDHParameter;
  }

  /**
   * DHパラメータの使用の有無を返します。
   * 
   * @return　DHパラメータの使用の有無
   */
  public boolean hasDHParameter() {
    return this.hasDHParameter;
  }

  /**
   * 座標パラメータの使用の有無を設定します。
   * 
   * @param hasCoordinateParameter 座標パラメータ使用の有無
   */
  public void setHasCoordinateParameter(boolean hasCoordinateParameter) {
    this.hasCoordinateParameter = hasCoordinateParameter;
  }

  /**
   * 座標パラメータの使用の有無を返します。
   * 
   * @return　座標パラメータ使用の有無
   */
  public boolean hasCoordinateParameter() {
    return this.hasCoordinateParameter;
  }
  
  /**
   * @param group グループ
   * @param tg トランスフォームグループ
   */
  public static void assignGroup(final Group group, final MyTransformGroup tg) {
    MOVABLE_GROUPS.put(group, tg);
  }

  /**
   * @param group グループ
   * @param tg トランスフォームグループ
   */
  public static void assignGroup(final Group group, final JoglTransformGroup tg) {
    MOVABLE_GROUPS.put(group, tg);
  }
}
