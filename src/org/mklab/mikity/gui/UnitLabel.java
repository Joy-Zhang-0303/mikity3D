/*
 * Created on 2005/02/14
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * @author miki
 * @version $Revision: 1.5 $.2005/02/14 ここで単位の表示を行う予定
 */
public class UnitLabel extends Composite {

  private static String modelAngle = "[rad]";
  private static String modelLength = "[m]";
  private static String dataAngle = "[rad]";
  private static String dataLength = "[m]";

  private Label label;

  /**
   * コンストラクター
   * 
   * @param c
   * @param target
   */
  public UnitLabel(Composite c, String target) {
    super(c, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    Label label = new Label(this, SWT.NONE);
    label.setText(getUnit(target));
    GridData data = new GridData();
    label.setLayoutData(data);
  }

  /**
   * 単位を取得する。
   * 
   * @param target
   * @return Angle or Length
   */
  public static String getUnit(String target) {
    if (target.startsWith("modelAngle")) {
      return modelAngle;
    } else if (target.startsWith("modelL")) {
      return modelLength;
    } else if (target.startsWith("dataAngle")) {
      return dataAngle;
    } else if (target.startsWith("dataLength")) {
      return dataLength;
    } else {
      return " ";
    }
  }

  /**
   * モデルの角度を設定する。
   * 
   * @param angle
   */
  public static void setModelAngle(String angle) {
    if (angle.startsWith("radian")) {
      modelAngle = "[rad]";
    } else {
      modelAngle = "[deg]";
    }
  }

  /**
   * モデルの長さを設定する。
   * 
   * @param length
   */
  public static void setModelLength(String length) {
    if (length.startsWith("cm")) {
      modelLength = "[cm]";
    } else if (length.startsWith("mm")) {
      modelLength = "[mm]";
    } else {
      modelLength = "[m]";
    }
  }

  /**
   * データの角度を設定する
   * 
   * @param angle
   */
  public static void setDataAngle(String angle) {
    if (angle.startsWith("radian")) {
      dataAngle = "[rad]";
    } else {
      dataAngle = "[deg]";
    }
  }

  /**
   * データの長さを設定する。
   * 
   * @param length
   */
  public static void setDataLength(String length) {
    if (length.startsWith("cm")) {
      dataLength = "[cm]";
    } else if (length.startsWith("mm")) {
      dataLength = "[mm]";
    } else {
      dataLength = "[m]";
    }
  }

  /**
   * ラベルを設定する。
   * 
   * @param target
   */
  public void setLabel(String target) {
    this.label.setText(getUnit(target));
  }

}