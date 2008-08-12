/*
 * Created on 2005/01/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mklab.mikity.picker.ClosenessDataPicker;
import org.mklab.mikity.picker.DataPicker;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;
import org.mklab.mikity.xml.model.Linkdata;
import org.mklab.nfc.Matrix;


/**
 * 動かせるグループの管理をする
 * 
 * @author miki
 * @version $Revision: 1.10 $.2005/01/14
 */
public class MovableGroupManager {

  /** 動かせるグループのリスト */
  private List<IMovableGroup> movableList = new ArrayList<IMovableGroup>();
  private Map<IMovableGroup, DataPicker> pickMap = new HashMap<IMovableGroup, DataPicker>();
  private static Map<Group, MyTransformGroup> groupMap = new HashMap<Group, MyTransformGroup>();
  private int dataCount;
  private double startTime;
  private double endTime;

  private Matrix data;

  private Jamast root;

  private boolean usedDHParam = false;
  private boolean usedLink = false;

  /**
   * コンストラクター
   * 
   * @param root
   */
  public MovableGroupManager(Jamast root) {
    this.root = root;
  }

  /**
   * 移動可能なグループを追加する。
   * 
   * @param tg
   * @param pick
   */
  private void addMovableGroup(final IMovableGroup tg, final DataPicker pick) {
    this.movableList.add(tg);
    this.pickMap.put(tg, pick);

    this.dataCount = Math.max(this.dataCount, pick.getDataCount());
    this.startTime = Math.min(this.startTime, pick.getStartTime());
    this.endTime = Math.max(this.endTime, pick.getEndTime());
  }

  /**
   * 時間ごとのリンクデータを読み取り、アニメーションを実行(DHパラメータ用)
   * 
   * @param time
   *        時間
   */
  public void processStimulusDH(double time) {
    // リストの中に入っているIMovableGroup全てに対して同じ処理を行う
    for (Iterator<IMovableGroup> iter = this.movableList.iterator(); iter.hasNext();) {
      IMovableGroup mg = iter.next();
      DataPicker picker = this.pickMap.get(mg);

      mg.setDHParameter(picker.getDHParameter(time));
    }
  }

  /**
   * 時間ごとのリンクデータを読み取り、アニメーションを実行
   * 
   * @param time
   *        時間
   */
  public void processStimulus(double time) {
    // リストの中に入っているIMovableGroup全てに対して同じ処理を行う
    for (Iterator<IMovableGroup> iter = this.movableList.iterator(); iter.hasNext();) {
      IMovableGroup mg = iter.next();
      DataPicker picker = this.pickMap.get(mg);

      mg.setLinkParameter(picker.getLinkParameter(time));
    }
  }

  /**
   * グループを追加する。
   * 
   * @param group
   *        追加するグループ
   */
  private void addGroup(final Group[] group) {
    for (int i = 0; i < group.length; i++) {
      Group g = group[i];
      MyTransformGroup tg = groupMap.get(g);
      setMovableLinkData(g.loadLinkdata(), tg);
      addGroup(g.loadGroup());
    }
  }

  /**
   * 移動可能なグループリストの設定
   * 
   * @param linkdata
   *        リンクデータ
   * @param tg
   *        TransformGroup
   */
  private void setMovableLinkData(Linkdata[] linkdata, MyTransformGroup tg) {
    if (linkdata.length == 0) {
      return;
    }
    DataPicker picker = new ClosenessDataPicker(this.data);

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
            System.out.println("a,alpha,d,thetaのいづれかを入れてください。");
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
            System.out.println("a,alpha,d,thetaのいづれかを入れてください。");
          }
          picker.setConstantValueDH(setType, constantValue);
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
          picker.setConstantValue(setType, constantValue);
        }
      }
    }
    addMovableGroup(tg, picker);
  }

  /**
   * データの個数を返します。
   * 
   * @return データカウント
   */
  public int getDataCount() {
    return this.dataCount;
  }

  /**
   * 終了時間を取得する。
   * 
   * @return endTime 終了時間
   */
  public double getEndTime() {
    return this.endTime;
  }

  /**
   * 開始時間を取得する。
   * 
   * @return startTime 開始時間
   */
  public double getStartTime() {
    return this.startTime;
  }

  /**
   * 行列データの設定
   * 
   * @param data
   *        行列データ
   */
  public void setData(final Matrix data) {
    this.data = data;
    this.movableList.clear();
    addGroup(this.root.loadModel(0).loadGroup());
  }

  /**
   * @param group
   * @param tg
   */
  public static void assignGroup(final Group group, final MyTransformGroup tg) {
    groupMap.put(group, tg);
  }

  /**
   * DHパラメータの使用の有無を設定
   * 
   * @param used
   *        DHパラメータ使用の有無
   */
  public void setDH(boolean used) {
    this.usedDHParam = used;
  }

  /**
   * DHパラメータの使用の有無を返す
   * 
   * @return　usedDHParam　DHパラメータの使用の有無
   */
  public boolean getDH() {
    return this.usedDHParam;
  }

  /**
   * リンクパラメータの使用の有無を設定
   * 
   * @param used
   *        リンクパラメータ使用の有無
   */
  public void setLink(boolean used) {
    this.usedLink = used;
  }

  /**
   * リンクパラメータの使用の有無を返す
   * 
   * @return　usedLink　リンクパラメータ使用の有無
   */
  public boolean getLink() {
    return this.usedLink;
  }
}
