/*
 * Created on 2012/11/05
 * Copyright (C) 2012 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model;


/**
 * {@link CoordinateParameter}の種類を表す列挙型です。
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
