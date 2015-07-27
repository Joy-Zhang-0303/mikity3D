/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.picker;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.CoordinateParameter;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.sampler.ClosenessDataSampler;
import org.mklab.mikity.model.sampler.DataSampler;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;


/**
 * {@link ClosenessDataSampler}のテストクラスです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/01/17
 */
public class ClosenessDataPickerTest {
  private Matrix data;

  /**
   * @throws IOException ファイルが読み込めない場合
   */
  @Before
  public void setUp() throws IOException {
    try (final InputStreamReader input = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("data2.mat"))) { //$NON-NLS-1$
      this.data = MatxMatrix.readMatFormat(input);
    }
  }

  /**
   * 
   */
  @Test
  public void testGetParameter() {
    final DataSampler picker = new ClosenessDataSampler(this.data);
    
    picker.sample(CoordinateParameterType.TRANSLATION_X, 2);
    
    final double t1 = 13.59;
    CoordinateParameter param1 = picker.getCoordinateParameter(t1);
    assertTrue(8.921 == param1.getTranslationX());

    final double t2 = 2.86033300E+00;
    CoordinateParameter  param2 = picker.getCoordinateParameter(t2);
    assertTrue(8.921 == param2.getTranslationX());
    assertTrue(0.0 == param2.getRotationX());

    picker.sample(CoordinateParameterType.ROTATION_X, 3);
    
    assertTrue(-0.010995 == param2.getRotationX());
  }
}