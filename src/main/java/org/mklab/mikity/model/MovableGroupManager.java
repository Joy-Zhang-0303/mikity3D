/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mklab.mikity.java3d.MyTransformGroup;
import org.mklab.mikity.jogl.JoglTransformGroup;
import org.mklab.mikity.picker.ClosenessDataPicker;
import org.mklab.mikity.picker.AbstractDataPicker;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;
import org.mklab.nfc.matrix.Matrix;


/**
 * 動かせるグループの管理するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.10 $.2005/01/14
 */
public class MovableGroupManager {

  /** 動かせるグループのリスト */
  private List<MovableGroup> movableGroups = new ArrayList<MovableGroup>();
  private Map<MovableGroup, AbstractDataPicker> pickers = new HashMap<MovableGroup, AbstractDataPicker>();
  private static Map<Group, MovableGroup> groups = new HashMap<Group, MovableGroup>();
  private int dataCount;
  private double startTime;
  private double endTime;

  private Matrix data;

  private Jamast root;

  private boolean usingDHParameter = false;
  private boolean usingCoordinateParameter = false;

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
   * @param group
   * @param picker
   */
  private void addMovableGroup(final MovableGroup group, final AbstractDataPicker picker) {
    this.movableGroups.add(group);
    this.pickers.put(group, picker);

    this.dataCount = Math.max(this.dataCount, picker.getDataCount());
    this.startTime = Math.min(this.startTime, picker.getStartTime());
    this.endTime = Math.max(this.endTime, picker.getEndTime());
  }
  

  /**
   * 時間ごとのリンクデータを読み取り、アニメーションを実行(DHパラメータ用)
   * 
   * @param time 時間
   */
  public void processStimulusDH(double time) {
    // リストの中に入っているIMovableGroup全てに対して同じ処理を行う
    for (Iterator<MovableGroup> iter = this.movableGroups.iterator(); iter.hasNext();) {
      MovableGroup mg = iter.next();
      AbstractDataPicker picker = this.pickers.get(mg);

      if (picker.getDHParameter(time) != null) {
        mg.setDHParameter(picker.getDHParameter(time));
      }
    }
  }

  /**
   * 時間ごとのリンクデータを読み取り、アニメーションを実行
   * 
   * @param time 時間
   */
  public void processStimulus(double time) {
    // リストの中に入っているIMovableGroup全てに対して同じ処理を行う
    for (Iterator<MovableGroup> iter = this.movableGroups.iterator(); iter.hasNext();) {
      MovableGroup mg = iter.next();
      AbstractDataPicker picker = this.pickers.get(mg);

      mg.setLinkParameter(picker.getCoordinateParameter(time));
    }
  }

  /**
   * グループを追加します。
   * 
   * @param group 追加するグループ
   */
  private void addGroup(final Group[] group) {
    for (int i = 0; i < group.length; i++) {
      Group g = group[i];
      MovableGroup tg = groups.get(g);
      setMovableLinkData(g.loadLinkdata(), tg);
      addGroup(g.loadGroup());
    }
  }

  /**
   * 移動可能なグループリストの設定
   * 
   * @param linkdata リンクデータ
   * @param group TransformGroup
   */
  private void setMovableLinkData(Linkdata[] linkdata, MovableGroup group) {
    if (linkdata.length == 0) {
      return;
    }
    AbstractDataPicker picker = new ClosenessDataPicker(this.data);

    for (int i = 0; i < linkdata.length; i++) {
      if (linkdata[i].hasDH()) {
        if (linkdata[i].hasColumn()) {
          int col = linkdata[i].loadColumn();
          String target = linkdata[i].loadTarget();
          int setType = 0;

          if (target.equals("a")) { //$NON-NLS-1$
            setType = DHParameter.A;
          } else if (target.equals("alpha")) { //$NON-NLS-1$
            setType = DHParameter.ALPHA;
          } else if (target.equals("d")) { //$NON-NLS-1$
            setType = DHParameter.D;
          } else if (target.equals("theta")) { //$NON-NLS-1$
            setType = DHParameter.THETA;
          } else {
            System.out.println(Messages.getString("MovableGroupManager.0")); //$NON-NLS-1$
          }
          picker.addMoveTypeDH(setType, col);
        }
        if (linkdata[i].hasConst()) {
          double constantValue = linkdata[i].loadConst();
          String target = linkdata[i].loadTarget();
          int setType = 0;

          if (target.equals("a")) { //$NON-NLS-1$
            setType = DHParameter.A;
          } else if (target.equals("alpha")) { //$NON-NLS-1$
            setType = DHParameter.ALPHA;
          } else if (target.equals("d")) { //$NON-NLS-1$
            setType = DHParameter.D;
          } else if (target.equals("theta")) { //$NON-NLS-1$
            setType = DHParameter.THETA;
          } else {
            System.err.println(Messages.getString("MovableGroupManager.1")); //$NON-NLS-1$
          }
          picker.setConstantDHParameter(setType, constantValue);
        }
      } else if (linkdata[i].hasLink()) {
        if (linkdata[i].hasColumn()) {
          int col = linkdata[i].loadColumn();
          String target = linkdata[i].loadTarget();
          int setType = 0;

          if (target.equals("locationX")) { //$NON-NLS-1$
            setType = 1;
          } else if (target.equals("locationY")) { //$NON-NLS-1$
            setType = 2;
          } else if (target.equals("locationZ")) { //$NON-NLS-1$
            setType = 3;
          } else if (target.equals("rotationX")) { //$NON-NLS-1$
            setType = 4;
          } else if (target.equals("rotationY")) { //$NON-NLS-1$
            setType = 5;
          } else if (target.equals("rotationZ")) { //$NON-NLS-1$
            setType = 6;
          } else {
            //
          }
          picker.addMoveType(setType, col);
        }
        if (linkdata[i].hasConst()) {
          double constantValue = linkdata[i].loadConst();
          String target = linkdata[i].loadTarget();
          int setType = 0;

          if (target.equals("locationX")) { //$NON-NLS-1$
            setType = 1;
          } else if (target.equals("locationY")) { //$NON-NLS-1$
            setType = 2;
          } else if (target.equals("locationZ")) { //$NON-NLS-1$
            setType = 3;
          } else if (target.equals("rotationX")) { //$NON-NLS-1$
            setType = 4;
          } else if (target.equals("rotationY")) { //$NON-NLS-1$
            setType = 5;
          } else if (target.equals("rotationZ")) { //$NON-NLS-1$
            setType = 6;
          } else {
            //
          }
          picker.setConstantCoordinateParameter(setType, constantValue);
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
   * @return endTime 終了時間
   */
  public double getEndTime() {
    return this.endTime;
  }

  /**
   * 開始時間を返します。
   * 
   * @return startTime 開始時間
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
    this.movableGroups.clear();
    addGroup(this.root.loadModel(0).loadGroup());
  }

  /**
   * @param group グループ
   * @param tg トランスフォームグループ
   */
  public static void assignGroup(final Group group, final MyTransformGroup tg) {
    groups.put(group, tg);
  }

  /**
   * @param group グループ
   * @param tg トランスフォームグループ
   */
  public static void assignGroup(final Group group, final JoglTransformGroup tg) {
    groups.put(group, tg);
  }
  
  /**
   * DHパラメータの使用の有無を設定します。
   * 
   * @param using DHパラメータ使用の有無
   */
  public void setUsingDHParameter(boolean using) {
    this.usingDHParameter = using;
  }

  /**
   * DHパラメータの使用の有無を返します。
   * 
   * @return　usedDHParam　DHパラメータの使用の有無
   */
  public boolean isUsingDHParameter() {
    return this.usingDHParameter;
  }

  /**
   * 座標パラメータの使用の有無を設定します。
   * 
   * @param using 座標パラメータ使用の有無
   */
  public void setUsingCoordinateParameter(boolean using) {
    this.usingCoordinateParameter = using;
  }

  /**
   * 座標パラメータの使用の有無を返します。
   * 
   * @return　usedLink　座標パラメータ使用の有無
   */
  public boolean isUingCoordinateParameter() {
    return this.usingCoordinateParameter;
  }
}
