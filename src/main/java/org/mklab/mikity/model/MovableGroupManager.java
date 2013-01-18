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
  /** 動かせるグループ */
  private List<MovableGroup> movableGroups = new ArrayList<MovableGroup>();
  /** データ抽出器 */
  private Map<MovableGroup, DataPicker> pickers = new HashMap<MovableGroup, DataPicker>();

  private static Map<Group, MovableGroup> MOVABLE_GROUPS = new HashMap<Group, MovableGroup>();
  
  /** データの個数 */
  private int dataCount;
  /** 開始時間 */
  private double startTime;
  /** 終了時間 */
  private double endTime;
  /** 時系列データ */
  //private Matrix data;

  private Jamast root;

  /** DHパラメータを使用するならばtrue */
  private boolean hasDHParameter = false;
  /** 座標パラメータを使用するならばtrue */
  private boolean hasCoordinateParameter = false;

  /**
   * 新しく生成された<code>MovableGroupManager</code>オブジェクトを初期化します。
   * @param root ルート
   */
  public MovableGroupManager(Jamast root) {
    this.root = root;
  }

  /**
   * 移動可能なグループを追加します。
   * 
   * @param movableGroup グループ
   * @param picker データ抽出器
   */
  private void addMovableGroup(final MovableGroup movableGroup, final DataPicker picker) {
    this.movableGroups.add(movableGroup);
    this.pickers.put(movableGroup, picker);

    this.dataCount = Math.max(this.dataCount, picker.getDataSize());
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
   * @param children 追加するグループ
   * @param data 時系列データ
   */
  private void addGroups(final Group[] children, Matrix data) {
    for (final Group group : children) {
      final MovableGroup movableGroup = MOVABLE_GROUPS.get(group);
      final LinkData[] links = group.getLinkData();
      if (links.length != 0) {
        final DataPicker picker = createPicker(data, links);
        addMovableGroup(movableGroup, picker);
      }
      addGroups(group.getGroups(), data);
    }
  }

  /**
   * 移動可能なグループのリンクデータを設定します。
   * 
   * @param data 時系列デー
   * @param links リンクデータ
   */
  private DataPicker createPicker(final Matrix data, final LinkData[] links) {
    final DataPicker picker = new ClosenessDataPicker(data);

    for (final LinkData link : links) {
      if (link.hasDHParameter()) {
        if (link.hasDataNumber()) {
          final int dataNumber = link.loadDataNumber();
          final String parameterName = link.loadTargetName();
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
          picker.pickupParameter(type, dataNumber);
        }
        
        if (link.hasInitialValue()) {
          final double initialValue = link.loadInitialValue();
          final String parameterName = link.loadTargetName();
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
            throw new IllegalAccessError(Messages.getString("MovableGroupManager.1")); //$NON-NLS-1$
          }
          picker.setParameter(type, initialValue);
        }
      } else if (link.hasCoordinateParameter()) {
        if (link.hasDataNumber()) {
          final int dataNumber = link.loadDataNumber();
          final String parameterName = link.loadTargetName();
          final CoordinateParameterType type;

          if (parameterName.equals("locationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.X;
          } else if (parameterName.equals("locationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.Y;
          } else if (parameterName.equals("locationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.Z;
          } else if (parameterName.equals("rotationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.TH_X;
          } else if (parameterName.equals("rotationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.TH_Y;
          } else if (parameterName.equals("rotationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.TH_Z;
          } else {
            throw new IllegalAccessError(Messages.getString("MovableGroupManager.2")); //$NON-NLS-1$
          }
          picker.pickupParameter(type, dataNumber);
        }
        
        if (link.hasInitialValue()) {
          final double initialValue = link.loadInitialValue();
          final String parameterName = link.loadTargetName();
          final CoordinateParameterType type;

          if (parameterName.equals("locationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.X;
          } else if (parameterName.equals("locationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.Y;
          } else if (parameterName.equals("locationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.Z;
          } else if (parameterName.equals("rotationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.TH_X;
          } else if (parameterName.equals("rotationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.TH_Y;
          } else if (parameterName.equals("rotationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.TH_Z;
          } else {
            throw new IllegalAccessError(Messages.getString("MovableGroupManager.3")); //$NON-NLS-1$
          }
          picker.setParameter(type, initialValue);
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
  public void setData(Matrix data) {
    this.movableGroups.clear();
    addGroups(this.root.loadModel(0).loadGroup(), data);
  }
  
  /**
   * DHパラメータを使用しているか設定します。
   * 
   * @param hasDHParameter DHパラメータ使用するならばtrue
   */
  public void setHasDHParameter(boolean hasDHParameter) {
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
  public void setHasCoordinateParameter(boolean hasCoordinateParameter) {
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
   * @param group グループ
   * @param movableGroup 移動可能なグループ
   */
  public static void assignGroup(final Group group, final MovableGroup movableGroup) {
    MOVABLE_GROUPS.put(group, movableGroup);
  }
}
