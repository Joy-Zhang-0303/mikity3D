package org.mklab.mikity.android;

import org.mklab.mikity.android.view.renderer.OpenglesModelRenderer;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * @author ohashi
 * @version $Revision$, 2013/02/05
 */
public class MainActivity extends Activity {

  //private static String TAG = "3Dmikity-android";

  private boolean mIsInitScreenSize;
  GLSurfaceView glView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    //GLViewを取り出す
    this.glView = new GLSurfaceView(this);
    this.glView = (GLSurfaceView)this.findViewById(R.id.glview1);

    //描画のクラスを登録する
    this.glView.setRenderer(new OpenglesModelRenderer());
    this.mIsInitScreenSize = false;

    //任意のタイミングで再描画する設定
    this.glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    Button playButton = (Button)findViewById(R.id.button1);
    Button stopButton = (Button)findViewById(R.id.button2);
    final RedrawHandler handler = new RedrawHandler(100);

    //イベントリスナー
    playButton.setOnClickListener(new View.OnClickListener() {

      //コールバックメソッド
      public void onClick(View view) {
        handler.start();
      }
    });

    stopButton.setOnClickListener(new View.OnClickListener() {

      //コールバックメソッド
      public void onClick(View view) {
        handler.stop();
      }
    });

  }

  /** 表示されるときに呼ばれる */

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {

    //スクリーンサイズ調整が済んでいない場合は調整する
    if (this.mIsInitScreenSize == false) {
      int width = this.glView.getWidth();

      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
      this.glView.setLayoutParams(params);
    }
    super.onWindowFocusChanged(hasFocus);
   }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onResume() {
    super.onResume();
    this.glView.onResume();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPause() {
    super.onPause();
    this.glView.onPause();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    //getMenuInflater().inflate(R.menu.main,menu);
    return true;
  }

  /** 画面を再描画するハンドラー */
  class RedrawHandler extends Handler {

    private int delayTime;
    private int frameRate;

    public RedrawHandler(int frameRate) {
      this.frameRate = frameRate;
    }

    public void start() {
      this.delayTime = 100 / this.frameRate;
      this.sendMessageDelayed(obtainMessage(0), this.delayTime);
    }

    public void stop() {
      this.delayTime = 0;
    }

    @Override
    public void handleMessage(Message msg) {

      MainActivity.this.glView.requestRender();//再描画

      if (this.delayTime == 0) return; // stop
      sendMessageDelayed(obtainMessage(0), this.delayTime);
    }
  }

}
