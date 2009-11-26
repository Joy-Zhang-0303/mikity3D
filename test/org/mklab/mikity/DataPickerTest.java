/*
 * Created on 2005/01/17
 * Copyright (C) 2005 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mklab.mikity.picker.ClosenessDataPicker;
import org.mklab.mikity.picker.DataPicker;
import org.mklab.nfc.matrix.Matrix;
import org.mklab.nfc.matx.MatxMatrix;


/**
 * @author miki
 * @version $Revision: 1.6 $.2005/01/17
 */
public class DataPickerTest {

  Matrix data;

  /**
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    this.data = MatxMatrix.readMatFormat("config/data2"); //$NON-NLS-1$
  }

  /**
   * 
   */
  @Test
  public void testSetup() {
    DataPicker picker = new ClosenessDataPicker(this.data);
    picker.addMoveTypeDH(DHParameter.D, 2);
    DHParameter param = picker.getDHParameter(13.59);
    assertTrue(-4.09900000 == param.getD());

    param = picker.getDHParameter(2.86033300E+00);
    assertTrue(-6.00000000E-02 == param.getD());

    assertTrue(0.0 == param.getAlpha());

    picker.addMoveTypeDH(DHParameter.ALPHA, 3);
    assertTrue(-3.29870000E-02 == param.getAlpha());

  }
}