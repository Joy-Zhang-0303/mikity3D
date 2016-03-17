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
package org.mklab.mikity.util;

import java.io.File;
import java.io.IOException;

import org.mklab.nfc.matrix.DoubleMatrix;
import org.mklab.nfc.matx.MatxMatrix;


/**
 * 時系列データを変換するクラスです。
 * 
 * @author koga
 * @version $Revision$, 2015/11/05
 */
public class TimeSeriesDataConverter {
  /**
   * メインメソッドです。
   * 
   * @param args コマンドライン引数。
   * @throws IOException フィルを読み込めない場合
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.println("TimeSeriesDataConverter 'source file' 'destination file'"); //$NON-NLS-1$
      return;
    }
    
    final String sourceFile = args[0];
    final String destinationFile = args[1];
    
    final DoubleMatrix data;
    
    if (sourceFile.toLowerCase().endsWith(".mat")) { //$NON-NLS-1$
      data = (DoubleMatrix)MatxMatrix.readMatFormat(new File(sourceFile));
    } else if (sourceFile.toLowerCase().endsWith(".csv")) { //$NON-NLS-1$
      data = DoubleMatrix.readCsvFormat(new File(sourceFile));
    } else if (sourceFile.toLowerCase().endsWith(".txt")) { //$NON-NLS-1$
      data = DoubleMatrix.readSsvFormat(new File(sourceFile));
    } else {
      throw new IllegalArgumentException("Inappropriate source data format."); //$NON-NLS-1$
    }
    
    if (destinationFile.toLowerCase().endsWith(".mat")) { //$NON-NLS-1$
      data.writeMatFormat(new File(destinationFile));
    } else if (destinationFile.toLowerCase().endsWith(".csv")) { //$NON-NLS-1$
      data.transpose().writeCsvFormat(new File(destinationFile));
    }  else if (destinationFile.toLowerCase().endsWith(".txt")) { //$NON-NLS-1$
      data.transpose().writeSsvFormat(new File(destinationFile));
    }  else {
      throw new IllegalArgumentException("Inappropriate destination data format."); //$NON-NLS-1$
    }
  }
}
