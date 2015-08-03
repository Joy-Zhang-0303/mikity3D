/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.sampler;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.Coordinate;
import org.mklab.mikity.model.CoordinateParameterType;
import org.mklab.mikity.model.xml.simplexml.model.RotationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matx.MatxMatrix;


/**
 * {@link ClosenessDataSampler}のテストクラスです。
 * 
 * @author miki
 * @version $Revision: 1.6 $.2005/01/17
 */
public class ClosenessDataPickerTest {
  private DoubleMatrix data;

  /**
   * @throws IOException ファイルが読み込めない場合
   */
  @Before
  public void setUp() throws IOException {
    try (final InputStreamReader input = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("data2.mat"))) { //$NON-NLS-1$
      this.data = (DoubleMatrix)MatxMatrix.readMatFormat(input);
    }
  }

  /**
   * 
   */
  @Test
  public void testGetParameter() {
    final DataSampler sampler = new ClosenessDataSampler(this.data);
    
    sampler.sample(CoordinateParameterType.TRANSLATION_X, 2);
    
    final double t1 = 13.59;
    Coordinate coordinate1 = sampler.getCoordinate(t1);
    
    TranslationModel translation1 = coordinate1.getTranslation();
    
    assertTrue(8.921f == translation1.getX());

    final double t2 = 2.86033300E+00;
    Coordinate coordinate2 = sampler.getCoordinate(t2);
    
    TranslationModel translation2 = coordinate2.getTranslation();
    RotationModel rotation2 = coordinate2.getRotation();
    
    assertTrue(8.921f == translation2.getX());
    assertTrue(0.0 == rotation2.getX());

    sampler.sample(CoordinateParameterType.ROTATION_X, 3);
    
    Coordinate coordinate3 = sampler.getCoordinate(t2);
    
    RotationModel rotation3 = coordinate3.getRotation();
    assertTrue(-0.010995f == rotation3.getX());
  }
}