/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.swt.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * ここで単位の表示を行う予定です。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/02/14 
 */
public class UnitLabel extends Composite {

  private static String modelAngle = "[rad]"; //$NON-NLS-1$
  private static String modelLength = "[m]"; //$NON-NLS-1$
  private static String dataAngle = "[rad]"; //$NON-NLS-1$
  private static String dataLength = "[m]"; //$NON-NLS-1$

  private Label label;

  /**
   * 新しく生成された<code>UnitLabel</code>オブジェクトを初期化します。
   * @param composite コンポジット
   * @param target 対象
   */
  public UnitLabel(Composite composite, String target) {
    super(composite, SWT.NONE);
    
    final GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.setLayout(layout);
    
    final Label localLabel = new Label(this, SWT.NONE);
    localLabel.setText(getUnit(target));
    
    final GridData data = new GridData();
    localLabel.setLayoutData(data);
  }

  /**
   * 単位を返します。
   * 
   * @param target 対象
   * @return Angle or Length
   */
  public static String getUnit(String target) {
    if (target.startsWith("modelAngle")) { //$NON-NLS-1$
      return modelAngle;
    } 
    if (target.startsWith("modelL")) { //$NON-NLS-1$
      return modelLength;
    } 
    if (target.startsWith("dataAngle")) { //$NON-NLS-1$
      return dataAngle;
    } 
    if (target.startsWith("dataLength")) { //$NON-NLS-1$
      return dataLength;
    }

    return " "; //$NON-NLS-1$
  }

  /**
   * モデルの角度を設定します。
   * 
   * @param angle モデルの角度
   */
  public static void setModelAngle(String angle) {
    if (angle.startsWith("degree")) { //$NON-NLS-1$
      modelAngle = "[deg]"; //$NON-NLS-1$
    } else {
      modelAngle = "[rad]"; //$NON-NLS-1$
    }
  }

  /**
   * モデルの長さを設定します。
   * 
   * @param length モデルの長さ
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
   * データの角度を設定します。
   * 
   * @param angle データの角度
   */
  public static void setDataAngle(String angle) {
    if (angle.startsWith("radian")) { //$NON-NLS-1$
      dataAngle = "[rad]"; //$NON-NLS-1$
    } else {
      dataAngle = "[deg]"; //$NON-NLS-1$
    }
  }

  /**
   * データの長さを設定します。
   * 
   * @param length データの長さ
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
   * ラベルを設定します。
   * 
   * @param label ラベル
   */
  public void setLabel(String label) {
    this.label.setText(getUnit(label));
  }

}