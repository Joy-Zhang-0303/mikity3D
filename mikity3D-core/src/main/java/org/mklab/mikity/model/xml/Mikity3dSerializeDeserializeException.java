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
package org.mklab.mikity.model.xml;


/**
 * Mikity3Dのシリアライズに関する例外を表すクラスです。
 * 
 * @author koga
 * @version $Revision$, 2013/02/05
 */
public class Mikity3dSerializeDeserializeException extends Exception {

  /** */
  private static final long serialVersionUID = 1L;

  /**
   * 新しく生成された<code>JamastSerializeDeserializeException</code>オブジェクトを初期化します。
   * @param e Throwable
   */
  public Mikity3dSerializeDeserializeException(Throwable e) {
    super(e);
  }

  /**
   * 新しく生成された<code>JamastSerializeDeserializeException</code>オブジェクトを初期化します。
   * @param message メッセージ
   * @param e Throwable
   */
  public Mikity3dSerializeDeserializeException(String message, Throwable e) {
    super(message, e);
  }
}
