/*
 * Created on 2013/02/05
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;


/**
 * JamastのUnmarshallerの例外を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public class JamastUnmarshallerException extends Exception {

  /** */
  private static final long serialVersionUID = 1L;

  
  /**
   * 新しく生成された<code>JamastUnmarshallerException</code>オブジェクトを初期化します。
   * @param e Throwable
   */
  public JamastUnmarshallerException(Throwable e) {
    super(e);
  }
  
  /**
   * 新しく生成された<code>JamastUnmarshallerException</code>オブジェクトを初期化します。
   * @param message メッセージ
   * @param e Throwable
   */
  public JamastUnmarshallerException(String message, Throwable e) {
    super(message, e);
  }
}
