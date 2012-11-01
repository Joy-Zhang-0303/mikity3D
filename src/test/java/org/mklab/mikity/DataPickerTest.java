/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.model.DHParameter;
import org.mklab.mikity.picker.ClosenessDataPicker;
import org.mklab.mikity.picker.AbstractDataPicker;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;


/**
 * @author miki
 * @version $Revision: 1.6 $.2005/01/17
 */
public class DataPickerTest {

  Matrix data;

  /**
   * @throws IOException ファイルが読み込めない場合
   */
  @Before
  public void setUp() throws IOException {
    this.data = MatxMatrix.readMatFormat("config/data2.mat"); //$NON-NLS-1$
  }

  /**
   * 
   */
  @Test
  public void testSetup() {
    AbstractDataPicker picker = new ClosenessDataPicker(this.data);
    picker.addMoveTypeDH(DHParameter.D, 2);
    DHParameter param = picker.getDHParameter(13.59);
    assertTrue(8.921 == param.getD());

    param = picker.getDHParameter(2.86033300E+00);
    assertTrue(8.921 == param.getD());

    assertTrue(0.0 == param.getAlpha());

    picker.addMoveTypeDH(DHParameter.ALPHA, 3);
    assertTrue(-0.010995 == param.getAlpha());

  }
}