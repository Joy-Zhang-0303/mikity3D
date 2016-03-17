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
package org.mklab.mikity.model;


/**
 * {@link Coordinate}の種類を表す列挙型です。
 * 
 * @author koga
 * @version $Revision$, 2012/11/05
 */
public enum CoordinateParameterType {
  /** Translation X */
  TRANSLATION_X,
  /** Translation Y */
  TRANSLATION_Y,
  /** Translation Z */
  TRANSLATION_Z,
  /** Rotation X */
  ROTATION_X,
  /** Rotation Y */
  ROTATION_Y,
  /** Rotation Z */
  ROTATION_Z;
  
  /**
   * パラメータの型を返します。
   * @param type 型に対応する文字
   * @return パラメータの型
   */
  public static CoordinateParameterType getType(String type) {
    if (type.equals("translationX")) { //$NON-NLS-1$
      return CoordinateParameterType.TRANSLATION_X;
    } 
    if (type.equals("translationY")) { //$NON-NLS-1$
      return CoordinateParameterType.TRANSLATION_Y;
    } 
    if (type.equals("translationZ")) { //$NON-NLS-1$
      return CoordinateParameterType.TRANSLATION_Z;
    }
    if (type.equals("rotationX")) { //$NON-NLS-1$
      return CoordinateParameterType.ROTATION_X;
    } 
    if (type.equals("rotationY")) { //$NON-NLS-1$
      return CoordinateParameterType.ROTATION_Y;
    } 
    if (type.equals("rotationZ")) { //$NON-NLS-1$
      return CoordinateParameterType.ROTATION_Z;
    } 

    throw new IllegalAccessError(Messages.getString("MovableGroupManager.2")); //$NON-NLS-1$
  }
}
