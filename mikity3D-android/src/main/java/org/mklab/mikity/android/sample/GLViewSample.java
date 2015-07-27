package org.mklab.mikity.android.sample;

import org.mklab.mikity.android.view.renderer.opengles.OpenglesObjectRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;


/**
 * @author ohashi
 * @version $Revision$, 2013/02/07
 */
public class GLViewSample extends GLSurfaceView {
  private OpenglesObjectRenderer myRenderer;

  /**
   * 新しく生成された<code>GLView</code>オブジェクトを初期化します。
   * @param context コンテクスト
   */
  public GLViewSample(Context context) {
    super(context);
   // this.myRenderer = new OpenglesModelRenderer();
    setRenderer(this.myRenderer);
  }
}
