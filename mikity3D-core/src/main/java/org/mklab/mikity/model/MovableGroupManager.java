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

import org.mklab.mikity.LogCatPrinter;
import org.mklab.mikity.model.picker.ClosenessDataPicker;
import org.mklab.mikity.model.picker.DataPicker;
import org.mklab.mikity.model.xml.simplexml.Mikity3d;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.model.xml.simplexml.model.LinkData;
import org.mklab.nfc.matrix.Matrix;


/**
 * 可動グループを管理するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.10 $.2005/01/14
 */
public class MovableGroupManager {
  /** グループとと可動グループのマップ */
  private static Map<Group, MovableGroup> MOVABLE_GROUPS = new HashMap<>();
  /** 可動グループ */
  private List<MovableGroup> movableGroups = new ArrayList<>();
  /** データ抽出器 */
  private Map<MovableGroup, DataPicker> pickers = new HashMap<>();
  
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
  
  private LogCatPrinter logCat;

  /**
   * 新しく生成された<code>MovableGroupManager</code>オブジェクトを初期化します。
   * @param root ルート
   */
  public MovableGroupManager(final Mikity3d root) {
    this.root = root;
  }

  /**
   * 可動グループを追加します。
   * 
   * @param movableGroup 可動グループ
   * @param picker データ抽出器
   */
  private void addMovableGroup(final MovableGroup movableGroup, final DataPicker picker) {
    this.movableGroups.add(movableGroup);
    this.pickers.put(movableGroup, picker);
    this.dataSize = Math.max(this.dataSize, picker.getDataSize());
    this.startTime = Math.min(this.startTime, picker.getStartTime());
    this.endTime = Math.max(this.endTime, picker.getEndTime());
  }

  /**
   * 指定された時刻のデータで可動グループを更新します。
   * 
   * @param t 時刻
   */
  public void updateMovableGroupsWithDHParameter(final double t) {
    for (final MovableGroup movableGroup : this.movableGroups) {
      final DataPicker picker = this.pickers.get(movableGroup);
      movableGroup.setDHParameter(picker.getDHParameter(t));
     // this.logCat.printLog(" t:"+t+" A:"+picker.getDHParameter(t).getA()+" Alpha:"+picker.getDHParameter(t).getAlpha()
          //+" D:"+picker.getDHParameter(t).getD()+" Theta:"+picker.getDHParameter(t).getTheta()); //$NON-NLS-1$
    }
  }

  /**
   * 指定された時刻のデータで可動グループを更新します。
   * 
   * @param t 時刻
   */
  public void updateMovableGroupsWithCoordinateParameter(final double t) {
    for (final MovableGroup movableGroup : this.movableGroups) {
      final DataPicker picker = this.pickers.get(movableGroup);
      movableGroup.setCoordinateParameter(picker.getCoordinateParameter(t));
     // this.logCat.printLog(""+picker.getDHParameter(t));
 
    }
  }

  /**
   * 可動グループを登録します。
   * 
   * @param groups グループ
   * @param data 時系列データ
   */
  private void registerMovableGroups(final Group[] groups, final Matrix data) {
    //logCat.printLog("registerMovableGroups"); //$NON-NLS-1$
    for (final Group group : groups) {
      final MovableGroup movableGroup = MOVABLE_GROUPS.get(group);
      final LinkData[] links = group.getLinkData();
      if (links.length != 0) {
        final DataPicker picker = createPicker(data, links);
        addMovableGroup(movableGroup, picker);
      }

      registerMovableGroups(group.getGroups(), data);
    }
  }

  /**
   * データ抽出器を生成します。
   * 
   * @param data 時系列データ
   * @param links リンクデータ
   */
  private DataPicker createPicker(final Matrix data, final LinkData[] links) {
    // this.logCat.printLog("createPicker"); //$NON-NLS-1$
    final DataPicker picker = new ClosenessDataPicker(data);
    for (final LinkData link : links) {
      if (link.hasDHParameter()) {
       // this.logCat.printLog("hasDHParameter"); //$NON-NLS-1$
        if (link.hasColumnNumber()) {
          // this.logCat.printLog("hasColumNumber"); //$NON-NLS-1$
          final int dataNumber = link.getColumnNumber();
          final String parameterName = link.getTargetName();
          // this.logCat.printLog("Number of link:"+links.length+" dataNumber:"+dataNumber  +"parameterName:"+parameterName); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
        
        if (link.hasConstantValue()) {
          final double initialValue = link.getConstantValue();
          final String parameterName = link.getTargetName();
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
        if (link.hasColumnNumber()) {
          final int dataNumber = link.getColumnNumber();
          final String parameterName = link.getTargetName();
          final CoordinateParameterType type;

          if (parameterName.equals("locationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.X;
          } else if (parameterName.equals("locationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.Y;
          } else if (parameterName.equals("locationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.Z;
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
        
        if (link.hasConstantValue()) {
          final double initialValue = link.getConstantValue();
          final String parameterName = link.getTargetName();
          final CoordinateParameterType type;

          if (parameterName.equals("locationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.X;
          } else if (parameterName.equals("locationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.Y;
          } else if (parameterName.equals("locationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.Z;
          } else if (parameterName.equals("rotationX")) { //$NON-NLS-1$
            type = CoordinateParameterType.ROTATION_X;
          } else if (parameterName.equals("rotationY")) { //$NON-NLS-1$
            type = CoordinateParameterType.ROTATION_Y;
          } else if (parameterName.equals("rotationZ")) { //$NON-NLS-1$
            type = CoordinateParameterType.ROTATION_Z;
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

    this.movableGroups.clear();
    registerMovableGroups(this.root.getModel(0).getGroups(), data);
   // this.logCat.printLog("setData"); //$NON-NLS-1$
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
   * グループと可動グループを対応付けします。
   * @param group グループ
   * @param movableGroup 可動グループ
   */
  public static void assignGroup(final Group group, final MovableGroup movableGroup) {
    MOVABLE_GROUPS.put(group, movableGroup);
  }
  
  
  /**
   * LogCatの出力先を設定します。
   * @param logCat Androidのログキャット利用クラス
   */
  public void setLogCat(LogCatPrinter logCat){
    this.logCat = logCat;
  }
  
}
