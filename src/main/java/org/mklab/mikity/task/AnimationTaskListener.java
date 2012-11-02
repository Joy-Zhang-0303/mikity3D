/*
 * Created on 2010/01/26
 * Copyright (C) 2010 Koga Laboratory. All rights reserved.
 *
 */
package org.mklab.mikity.task;

/**
 * アニメーションタスクのリスナインターフェースです。
 * 
 * @author mine
 * @version $Revision$, 2010/01/26
 */
public interface AnimationTaskListener {
  
  // TODO キャンセルや、安全に終了したときのリスナも追加
  
  /**
   * アニメーションの開始時に必ず呼び出されます。<p>
   * 例えば、アニメーションの初期時間が0でない場合でも、つまり途中からの開始であっても呼び出されます。
   */
  void taskStarted();
  
  /**
   * アニメーション終了時に必ず呼び出されます。
   */
   void taskDone();
}
