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
package org.mklab.mikity.control;

/**
 * アニメーションタスクのリスナインターフェースです。
 * 
 * @author mine
 * @version $Revision$, 2010/01/26
 */
public interface AnimationTaskListener {
  
  // TODO キャンセルや、安全に終了したときのリスナも追加予定です。
  
  /**
   * アニメーションの開始時に必ず呼び出されます。<p>
   * 例えば、アニメーションの初期時間が0でない場合でも、つまり途中からの開始であっても呼び出されます。
   */
  void setUpAnimation();
  
  /**
   * アニメーション終了時に必ず呼び出されます。
   */
   void tearDownAnimation();
}
