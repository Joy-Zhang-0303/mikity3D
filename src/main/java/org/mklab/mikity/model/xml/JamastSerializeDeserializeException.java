/*
 * Created on 2013/02/05
 * Copyright (C) 2013 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.model.xml;


/**
 * Jamastのシリアライズに関する例外を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public class JamastSerializeDeserializeException extends Exception {

  /** */
  private static final long serialVersionUID = 1L;

  /**
   * 新しく生成された<code>JamastSerializeDeserializeException</code>オブジェクトを初期化します。
   * @param e Throwable
   */
  public JamastSerializeDeserializeException(Throwable e) {
    super(e);
  }

  /**
   * 新しく生成された<code>JamastSerializeDeserializeException</code>オブジェクトを初期化します。
   * @param message メッセージ
   * @param e Throwable
   */
  public JamastSerializeDeserializeException(String message, Throwable e) {
    super(message, e);
  }
}
