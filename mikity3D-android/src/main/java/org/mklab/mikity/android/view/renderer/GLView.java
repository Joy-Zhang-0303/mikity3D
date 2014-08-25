package org.mklab.mikity.android.view.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;


/**
 * @author ohashi
 * @version $Revision$, 2013/02/07
 */
public class GLView extends GLSurfaceView {

  OpenglesModelRenderer myRenderer;

  /**
   * 新しく生成された<code>GLView</code>オブジェクトを初期化します。
   * @param context コンテクスト
   */
  public GLView(Context context) {
    super(context);
   // this.myRenderer = new OpenglesModelRenderer();
    setRenderer(this.myRenderer);
  }
}
