package org.mklab.mikity.view.renderer.jogl;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.SwingUtilities;

import org.mklab.mikity.model.xml.simplexml.Mikity3dConfiguration;
import org.mklab.mikity.model.xml.simplexml.config.Eye;
import org.mklab.mikity.model.xml.simplexml.config.Light;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPoint;
import org.mklab.mikity.model.xml.simplexml.model.Group;
import org.mklab.mikity.view.renderer.ModelRenderer;

/**
 * JOGL用のキャンバスを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/11
 */
public class JoglModelRenderer extends GLJPanel implements ModelRenderer, GLEventListener, MouseListener, MouseMotionListener {
  /** */
  private static final long serialVersionUID = 5653656698891675370L;

  /** オブジェクトのグループ。 */
  private List<JoglObjectGroup> topGroups;
  
  /** 設定。 */
  private Mikity3dConfiguration configuration;

  /** Y軸周りの回転角度 */
  private float rotationY = 0.0f;
  /** Z軸周りの回転角度 */
  private float rotationZ = 0.0f;
  /** Y軸方向への移動距離 */
  private float translationY = 0.0f;
  /** Z軸方向への移動距離 */
  private float translationZ = 0.0f;
  /** 拡大縮小率 */
  private float scale = 1.0f;

  /** 平行光源1。 */
  private float[] lightLocation0 = {10.0f, 10.0f, 20.0f, 1.0f};
  /** 平行光源2。 */
  //private float[] lightLocation1 = {-0.5f, 1.0f, -1.0f, 1.0f};
  /** 反射光の強さ 。 */
  private float[] lightSpecular = {0.9f, 0.9f, 0.9f, 1.0f};
  /** 拡散光の強さ。 */
  private float[] lightDiffuse = {0.5f, 0.5f, 0.5f, 1.0f};
  /** 環境光の強さ。 */
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f};
  
  /** マウスボタンを押した点 */
  private Point startPoint;
  /** マウスボタンを離した点 */
  private Point endPoint;

  /** Y軸に関する開始回転角度 */
  private float startRotationY;
  /** Z軸に関する終了回転角度 */
  private float startRotationZ;
  /** Y軸方向への移動開始距離 */
  private float startTranslationY;
  /** Z軸方向への移動開始距離 */
  private float startTranslationZ;
  /** 開始拡大縮小率 */
  private float startScale;
  
  private GLU glu = new GLU();

  /**
   * 新しく生成された<code>JoglModelCanvas</code>オブジェクトを初期化します。
   */
  public JoglModelRenderer() {
    super(new GLCapabilities(null));
    
    this.configuration = new Mikity3dConfiguration();
    this.configuration.setEye(new Eye(5.0f, 0.0f, 0.0f));
    this.configuration.setLookAtPoiint(new LookAtPoint(0.0f, 0.0f, 0.0f));
    this.configuration.setLight(new Light(10.0f, 10.0f, 20.0f));
    
    addGLEventListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
  /**
   * {@inheritDoc}
   */
  public void init(GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    gl.glEnable(GL.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    gl.glEnable(GL.GL_CULL_FACE); // 背面除去
    
    gl.glEnable(GLLightingFunc.GL_LIGHTING); //光源を有効にします 
    gl.glEnable(GLLightingFunc.GL_COLOR_MATERIAL); //(光源がある場合の)カラーを有効にします 
    gl.glEnable(GLLightingFunc.GL_NORMALIZE);
    gl.glEnable(GLLightingFunc.GL_LIGHT0); //0番のライトを有効にします
    gl.glMaterialf(GL.GL_FRONT, GLLightingFunc.GL_SHININESS, 100.0f);
  }

  /**
   * {@inheritDoc}
   */
  public void display(GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    
    gl.glLoadIdentity();
    
    final Light light = this.configuration.getLight();
    final float[] lightLocation = new float[]{light.getX(), light.getY(), light.getZ(), 1.0f};
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightLocation, 0); // 平行光源を設定します 
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します

    gl.glLoadIdentity();
    
    final Eye eye = this.configuration.getEye();
    final LookAtPoint lookAtPoint = this.configuration.getLookAtPoint();
    this.glu.gluLookAt(eye.getX(), eye.getY(), eye.getZ(), lookAtPoint.getX(), lookAtPoint.getY(), lookAtPoint.getZ(), 0.0, 0.0, 1.0);
    
    gl.glTranslatef(0.0f, this.translationY, -this.translationZ);
    gl.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(this.rotationZ, 0.0f, 0.0f, 1.0f);
    
    gl.glScalef(this.scale, this.scale, this.scale);
    
    if (this.topGroups != null) {
      for (final JoglObjectGroup topGroup : this.topGroups) {
        topGroup.display(gl);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setChildren(Group[] children) {
    this.topGroups = new JoglModelCreater().create(children);
    display();
  }

  /**
   * {@inheritDoc}
   */
  public void setConfiguration(Mikity3dConfiguration configuration) {
    if (configuration == null) {
      return;
    }
    
    this.configuration = configuration;
    display();
  }
  
  /**
   * 設定を返します。
   * @return 設定
   */
  public Mikity3dConfiguration getConfiguration() {
    return this.configuration;
  }

  /**
   * @param drawable
   * @param modeChanged
   * @param deviceChanged
   */
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    final GL2 gl = (GL2)drawable.getGL();
    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
    gl.glLoadIdentity();
    this.glu.gluPerspective(10.0, (double)width / (double)height, 0.1, 100.0);
    gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
  }

  /**
   * マウス左クリック時の処理　カメラを移動させます。
   * @param e マウスイベント 
   */
  public void mousePressed(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e) == true) {
      this.startRotationY = this.rotationY;
      this.startRotationZ = this.rotationZ;
      this.startPoint = getMousePosition(true);
      if (this.endPoint == null) {
        this.endPoint = new Point(this.startPoint);
      }
      display();
      return;
    }
    
    if (SwingUtilities.isMiddleMouseButton(e) == true) {
      this.startScale = this.scale;
      this.startPoint = getMousePosition(true);
      if (this.endPoint == null) {
        this.endPoint = new Point(this.startPoint);
      }
      display();
      return;
    }
    
    if (SwingUtilities.isRightMouseButton(e) == true) {
      this.startTranslationY = this.translationY;
      this.startTranslationZ = this.translationZ;
      this.startPoint = getMousePosition(true);
      if (this.endPoint == null) {
        this.endPoint = new Point(this.startPoint);
      }
      display();
      return;
    }

  }

  /**
   * {@inheritDoc}
   */
  public void mouseClicked(MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void mouseEntered(MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void mouseExited(MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void mouseReleased(MouseEvent e) {
    // nothing to do
  }

  /**
   * マウスをドラッグさせるとカメラ座標が移動させます。
   * @param e マウスイベント
   */
  public void mouseDragged(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      this.endPoint = getMousePosition(true);
      if (this.startPoint == null) {
        this.startPoint = new Point(this.endPoint);
      }
      this.rotationY = this.startRotationY + (this.endPoint.y - this.startPoint.y);
      this.rotationZ = this.startRotationZ + (this.endPoint.x - this.startPoint.x);
      display();
      return;
    }
    
    if (SwingUtilities.isMiddleMouseButton(e)) {
      this.endPoint = getMousePosition(true);
      if (this.startPoint == null) {
        this.startPoint = new Point(this.endPoint);
      }
      final int dy = Math.abs(this.endPoint.y - this.startPoint.y);
      final float scalingFactor = 50.0f;
      if (this.endPoint.y > this.startPoint.y) {
        this.scale = (float)Math.max(0.01, this.startScale - dy / scalingFactor);
      } else {
        this.scale = this.startScale + dy / scalingFactor;
      }
      display();
      return;
    }
    
    if (SwingUtilities.isRightMouseButton(e) == true) {
      this.endPoint = getMousePosition(true);
      if (this.startPoint == null) {
        this.startPoint = new Point(this.endPoint);
      }
      final float translationFactor = 200.0f;
      this.translationZ = this.startTranslationZ + (this.endPoint.y - this.startPoint.y) / translationFactor;
      this.translationY = this.startTranslationY + (this.endPoint.x - this.startPoint.x) / translationFactor;
      display();
      return;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void mouseMoved(MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public boolean isRequiredToCallDisplay() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void updateDisplay() {
    display();
  }

  /**
   * {@inheritDoc}
   */
  public void dispose(GLAutoDrawable arg0) {
    // TODO 自動生成されたメソッド・スタブ
  }
}
