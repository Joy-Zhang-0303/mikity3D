/*
 * Created on 2005/01/12
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import java.util.Map;

import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.xml.simplexml.model.SourceModel;
import org.mklab.nfc.matrix.DoubleMatrix;


/**
 * 現在の再生時刻に近い時間のデータを抽出するクラスです。
 * 
 * @author miki
 * @version $Revision: 1.5 $.2005/01/12
 */
public class ClosenessDataSampler extends AbstractDataSampler { 
  /**
   * 新しく生成された<code>ClosenessDataSampler</code>オブジェクトを初期化します。
   * @param sourceData ソースデータ
   */
  public ClosenessDataSampler(Map<String, DoubleMatrix> sourceData) {
    super(sourceData);
  }
  
  /**
   * {@inheritDoc}
   */
  public Coordinate getCoordinate(double t) {
    final Coordinate parameter = new Coordinate();
   
    for (int i = 0; i < this.types.size(); i++) {
      final CoordinateParameterType type = this.types.get(i);
      
      final SourceModel source = this.sources.get(i);
      final String id = source.getId();
      final int number = source.getNumber();
      final DoubleMatrix data = this.sourceData.get(id);
            
      final int timeNumber;
      if (AbstractDataSampler.timeNumbers.containsKey(id)) {
        timeNumber = AbstractDataSampler.timeNumbers.get(id).intValue();
      } else {
        timeNumber = getTimeNumber(data, t);
        AbstractDataSampler.timeNumbers.put(id, Integer.valueOf(timeNumber));
      }
      
      final double value = data.getDoubleElement(number, timeNumber);
      
      parameter.setValue(type, value);
    }
      
    return parameter;
  }

  /**
   * 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号を返します。
   * 
   * @param data データ
   * @param t 時間
   * @return 与えられた時間に最も近いデータが存在する時刻に対応するデータ番号
   */
  private int getTimeNumber(DoubleMatrix data, double t) {
    final DoubleMatrix times = data.getRowVector(1);
    final DoubleMatrix timeDifferences = times.subtractElementWise(t).absElementWise();
    final int number = timeDifferences.minimumRowWise().getIndices().getIntElement(1);
    return number;
  }
}
