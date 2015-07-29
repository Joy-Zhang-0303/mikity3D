/*
 * Created on 2005/01/12
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matrix.Matrix;


/**
 * 現在の再生時刻に近い時間のデータを抽出するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/01/12
 */
public class ClosenessDataSampler extends AbstractDataSampler {
  /**
   * 新しく生成された<code>ClosenessDataSampler</code>オブジェクトを初期化します。
   * @param data データ
   */
  public ClosenessDataSampler(Matrix data) {
    super(data);
  }

  /**
   * {@inheritDoc}
   */
  public CoordinateParameter getCoordinateParameter(double t) {
    final CoordinateParameter parameter = new CoordinateParameter();
    
    final int timeNumber = getTimeNumber(t);
    
    for (int i = 0; i < this.types.size(); i++) {
      final CoordinateParameterType type = this.types.get(i);
      final int dataNumber = this.dataNumbers.get(i).intValue();
      final double value = this.data.getDoubleElement(dataNumber, timeNumber);
      
      switch (type) {
        case TRANSLATION_X:
          parameter.setTranslationX(value);
          break;
        case TRANSLATION_Y:
          parameter.setTranslationY(value);
          break;
        case TRANSLATION_Z:
          parameter.setTranslationZ(value);
          break;
        case ROTATION_X:
          parameter.setRotationX(value);
          break;
        case ROTATION_Y:
          parameter.setRotationY(value);
          break;
        case ROTATION_Z:
          parameter.setRotationZ(value);
          break;
        default:
          throw new AssertionError(Messages.getString("DataPicker.1")); //$NON-NLS-1$
      }
    }
      
    return parameter;
  }

  /**
   * 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号を返します。
   * 
   * @param t 時間
   * @return 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号
   */
  private int getTimeNumber(double t) {
    final DoubleMatrix times = this.data.getRowVector(1);
    final DoubleMatrix timeDifferences = times.subtractElementWise(t).absElementWise();
    final int number = timeDifferences.minimumRowWise().getIndices().getIntElement(1);
    return number;
  }
}
