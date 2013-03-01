package org.mklab.mikity.android;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ByteOrder;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;                                                          


/**
 * レンダラー
 * @author ohashi
 * @version $Revision$, 2012/10/29
 */
public class GLRenderer implements GLSurfaceView.Renderer{

  //システム
  private float aspect;//アスペクト比
  private int   angle; //回転角度
  
  //バッファ
  private FloatBuffer vertexBuffer;//頂点バッファ
  private ByteBuffer  indexBuffer; //インデックスバッファ
  private FloatBuffer normalBuffer;//法線バッファ
  
  /**
   *　サーフェイス生成時に呼ばれる 
   */
  public void onSurfaceCreated(GL10 gl10,EGLConfig eglConfig) {
      //頂点配列の有効化
      gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      gl10.glEnableClientState(GL10.GL_NORMAL_ARRAY);
      
      //デプステストの有効化
      gl10.glEnable(GL10.GL_DEPTH_TEST);
      
      //光源の有効化
      gl10.glEnable(GL10.GL_LIGHTING);
      gl10.glEnable(GL10.GL_LIGHT0);
      
      //光源色の指定
      gl10.glLightfv(GL10.GL_LIGHT0,GL10.GL_AMBIENT,
          new float[]{0.2f,0.2f,0.2f,1.0f},0);
      gl10.glLightfv(GL10.GL_LIGHT0,GL10.GL_DIFFUSE,
          new float[]{0.7f,0.7f,0.7f,1.0f},0);
      gl10.glLightfv(GL10.GL_LIGHT0,GL10.GL_SPECULAR,
          new float[]{0.0f,0.0f,0.0f,1.0f},0);
      
      //頂点バッファの生成
      float[] vertexs={
          1.0f, 1.0f, 1.0f,//頂点0
          1.0f, 1.0f,-1.0f,//頂点1
         -1.0f, 1.0f, 1.0f,//頂点2
         -1.0f, 1.0f,-1.0f,//頂点3
          1.0f,-1.0f, 1.0f,//頂点4
          1.0f,-1.0f,-1.0f,//頂点5
         -1.0f,-1.0f, 1.0f,//頂点6
         -1.0f,-1.0f,-1.0f,//頂点7
      };
      this.vertexBuffer=makeFloatBuffer(vertexs);
      
      //インデックスバッファの生成
      byte[] indexs={
          0,1,2,3,6,7,4,5,0,1,//面0
          1,5,3,7,            //面1
          0,2,4,6,            //面2
      };
      this.indexBuffer=makeByteBuffer(indexs);
      
      //法線バッファの生成
      float[] normals={
          1.0f, 1.0f, 1.0f,//頂点0
          1.0f, 1.0f,-1.0f,//頂点1
         -1.0f, 1.0f, 1.0f,//頂点2
         -1.0f, 1.0f,-1.0f,//頂点3
          1.0f,-1.0f, 1.0f,//頂点4
          1.0f,-1.0f,-1.0f,//頂点5
         -1.0f,-1.0f, 1.0f,//頂点6
         -1.0f,-1.0f,-1.0f,//頂点7
      };
      float div=(float)Math.sqrt(
          (1.0f*1.0f)+(1.0f*1.0f)+(1.0f*1.0f));
      for (int i=0;i<normals.length;i++) normals[i]/=div;
      this.normalBuffer=makeFloatBuffer(normals);
  }
  
  /**
   *　画面サイズ変更時に呼ばれる
   */
  public void onSurfaceChanged(GL10 gl10,int w,int h) {
      //ビューポート変換
      gl10.glViewport(0,0,w,h);
      this.aspect=(float)w/(float)h;
  }


  /**
   * 毎フレーム描画時に呼ばれる
   */
  public void onDrawFrame(GL10 gl10) {
      //画面のクリア
      gl10.glClearColor(1.0f,1.0f,1.0f,1.0f);
      gl10.glClear(GL10.GL_COLOR_BUFFER_BIT|
          GL10.GL_DEPTH_BUFFER_BIT);        
      
      //射影変換
      gl10.glMatrixMode(GL10.GL_PROJECTION);
      gl10.glLoadIdentity();
      GLU.gluPerspective(gl10,
          45.0f,  //Y方向の画角
          this.aspect, //アスペクト比
          0.01f,  //ニアクリップ
          100.0f);//ファークリップ
      
      //光源位置の指定
      gl10.glMatrixMode(GL10.GL_MODELVIEW);
      gl10.glLoadIdentity();
      gl10.glLightfv(GL10.GL_LIGHT0,GL10.GL_POSITION,
          new float[]{2.5f,2.5f,0.0f,1.0f},0);
      
      //ビュー変換
      GLU.gluLookAt(gl10,
          0.0f,5.0f,5.0f, //カメラの視点
          0.0f,0.0f,0.0f, //カメラの焦点
          0.0f,1.0f,0.0f);//カメラの上方向

      //モデル変換
      gl10.glRotatef(this.angle++,0,1,0);
      
      //ボックスの描画
      drawBox(gl10);
  }
  
  //ボックスの描画
  private void drawBox(GL10 gl10) {
      //頂点バッファの指定
      gl10.glVertexPointer(3,GL10.GL_FLOAT,0,this.vertexBuffer);
      
      //法線バッファの指定
      gl10.glNormalPointer(GL10.GL_FLOAT,0,this.normalBuffer);
      
      //面0の描画
      setMaterial(gl10,0,0,1,1);
      this.indexBuffer.position(0);
      gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP,
          10,GL10.GL_UNSIGNED_BYTE,this.indexBuffer);
      
      //面1の描画
      setMaterial(gl10,1,1,0,1);
      this.indexBuffer.position(10);
      gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP,
          4,GL10.GL_UNSIGNED_BYTE,this.indexBuffer);

      //面2の描画
      setMaterial(gl10,1,1,0,1);
      this.indexBuffer.position(14);
      gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP,
          4,GL10.GL_UNSIGNED_BYTE,this.indexBuffer);    
  }
  
  //マテリアルの指定
  private static void setMaterial(GL10 gl10,float r,float g,float b,float a) {
      //マテリアルの環境光色の指定
      gl10.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,new float[]{r,g,b,a},0);    
      
      //マテリアルの拡散光色の指定
      gl10.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE,new float[]{r,g,b,a},0);
      
      //マテリアルの鏡面光色と鏡面指数の指定
      gl10.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,new float[]{r,g,b,a},0);
      gl10.glMaterialf(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,0.6f);
  }
  
  //float配列をFloatBufferに変換
  private static FloatBuffer makeFloatBuffer(float[] array) {
      FloatBuffer fb=ByteBuffer.allocateDirect(array.length*4).order(
          ByteOrder.nativeOrder()).asFloatBuffer();
      fb.put(array).position(0);
      return fb;
  }
  
  //byte配列をByteBufferに変換
  private static ByteBuffer makeByteBuffer(byte[] array) {
      ByteBuffer bb=ByteBuffer.allocateDirect(array.length).order(
          ByteOrder.nativeOrder());
      bb.put(array).position(0);
      return bb;
  } 

}