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

  private static String modelAngle = "[rad]"; //$NON-NLS-1$
  private static String modelLength = "[m]"; //$NON-NLS-1$
  private static String dataAngle = "[rad]"; //$NON-NLS-1$
  private static String dataLength = "[m]"; //$NON-NLS-1$

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
    Label localLabel = new Label(this, SWT.NONE);
    localLabel.setText(getUnit(target));
    GridData data = new GridData();
    localLabel.setLayoutData(data);
  }

  /**
   * 単位を取得する。
   * 
   * @param target
   * @return Angle or Length
   */
  public static String getUnit(String target) {
    if (target.startsWith("modelAngle")) { //$NON-NLS-1$
      return modelAngle;
    } else if (target.startsWith("modelL")) { //$NON-NLS-1$
      return modelLength;
    } else if (target.startsWith("dataAngle")) { //$NON-NLS-1$
      return dataAngle;
    } else if (target.startsWith("dataLength")) { //$NON-NLS-1$
      return dataLength;
    } else {
      return " "; //$NON-NLS-1$
    }
  }

  /**
   * モデルの角度を設定する。
   * 
   * @param angle
   */
  public static void setModelAngle(String angle) {
    if (angle.startsWith("radian")) { //$NON-NLS-1$
      modelAngle = "[rad]"; //$NON-NLS-1$
    } else {
      modelAngle = "[deg]"; //$NON-NLS-1$
    }
  }

  /**
   * モデルの長さを設定する。
   * 
   * @param length
   */
  public static void setModelLength(String length) {
    if (length.startsWith("cm")) { //$NON-NLS-1$
      modelLength = "[cm]"; //$NON-NLS-1$
    } else if (length.startsWith("mm")) { //$NON-NLS-1$
      modelLength = "[mm]"; //$NON-NLS-1$
    } else {
      modelLength = "[m]"; //$NON-NLS-1$
    }
  }

  /**
   * データの角度を設定する
   * 
   * @param angle
   */
  public static void setDataAngle(String angle) {
    if (angle.startsWith("radian")) { //$NON-NLS-1$
      dataAngle = "[rad]"; //$NON-NLS-1$
    } else {
      dataAngle = "[deg]"; //$NON-NLS-1$
    }
  }

  /**
   * データの長さを設定する。
   * 
   * @param length
   */
  public static void setDataLength(String length) {
    if (length.startsWith("cm")) { //$NON-NLS-1$
      dataLength = "[cm]"; //$NON-NLS-1$
    } else if (length.startsWith("mm")) { //$NON-NLS-1$
      dataLength = "[mm]"; //$NON-NLS-1$
    } else {
      dataLength = "[m]"; //$NON-NLS-1$
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