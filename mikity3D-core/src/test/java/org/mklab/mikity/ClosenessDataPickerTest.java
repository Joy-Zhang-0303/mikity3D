/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.model.DHParameterType;
import org.mklab.mikity.model.picker.ClosenessDataPicker;
import org.mklab.mikity.model.picker.DataPicker;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;


/**
 * {@link ClosenessDataPicker}のテストクラスです。
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
    final DataPicker picker = new ClosenessDataPicker(this.data);
    
    picker.pickup(DHParameterType.D, 2);
    
    final double t1 = 13.59;
    DHParameter param1 = picker.getDHParameter(t1);
    assertTrue(8.921 == param1.getD());

    final double t2 = 2.86033300E+00;
    DHParameter param2 = picker.getDHParameter(t2);
    assertTrue(8.921 == param2.getD());
    assertTrue(0.0 == param2.getAlpha());

    picker.pickup(DHParameterType.ALPHA, 3);
    
    assertTrue(-0.010995 == param2.getAlpha());
  }
}