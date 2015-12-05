package org.mklab.mikity.view.renderer.jogl;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.mklab.mikity.model.GroupObjectManager;
import org.mklab.mikity.model.xml.simplexml.ConfigurationModel;
import org.mklab.mikity.model.xml.simplexml.config.BackgroundModel;
import org.mklab.mikity.model.xml.simplexml.config.EyeModel;
import org.mklab.mikity.model.xml.simplexml.config.LightModel;
import org.mklab.mikity.model.xml.simplexml.config.LookAtPointModel;
import org.mklab.mikity.model.xml.simplexml.model.ColorModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.view.renderer.ObjectRenderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;


/**
 * JOGL用のキャンバスを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/11
 */
public class JoglObjectRenderer extends GLJPanel implements ObjectRenderer, GLEventListener, MouseListener, MouseMotionListener {

  /** */
  private static final long serialVersionUID = 5653656698891675370L;

  /** ルートオブジェクト。 */
  private List<JoglGroupObject> rootObjects;

  /** 設定。 */
  private ConfigurationModel configuration;

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
  public JoglObjectRenderer() {
    super(new GLCapabilities(null));

    this.configuration = new ConfigurationModel();
    this.configuration.setEye(new EyeModel(5.0f, 0.0f, 0.0f));
    this.configuration.setLookAtPoiint(new LookAtPointModel(0.0f, 0.0f, 0.0f));
    this.configuration.setLight(new LightModel(10.0f, 10.0f, 20.0f));
    this.configuration.setBackground(new BackgroundModel("white")); //$NON-NLS-1$

    addGLEventListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    gl.glEnable(GL.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    gl.glEnable(GL.GL_CULL_FACE); // 背面除去

    //    gl.glEnable(GL.GL_BLEND);
    //    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    //    gl.glHint(GL2GL3.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
    //    gl.glEnable(GL2GL3.GL_POLYGON_SMOOTH);

    gl.glEnable(GLLightingFunc.GL_LIGHTING); //光源を有効にします 
    gl.glEnable(GLLightingFunc.GL_COLOR_MATERIAL); //(光源がある場合の)カラーを有効にします 
    gl.glEnable(GLLightingFunc.GL_NORMALIZE);
    gl.glEnable(GLLightingFunc.GL_LIGHT0); //0番のライトを有効にします
    gl.glMaterialf(GL.GL_FRONT, GLLightingFunc.GL_SHININESS, 100.0f);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void display(GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    final ColorModel background = this.configuration.getBackground().getColor();
    gl.glClearColor(background.getRf(), background.getGf(), background.getBf(), background.getAlphaf());

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glLoadIdentity();

    final LightModel light = this.configuration.getLight();
    final float[] lightLocation = new float[] {light.getX(), light.getY(), light.getZ(), 1.0f};
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightLocation, 0); // 平行光源を設定します 
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します

    gl.glLoadIdentity();

    final EyeModel eye = this.configuration.getEye();
    final LookAtPointModel lookAtPoint = this.configuration.getLookAtPoint();
    this.glu.gluLookAt(eye.getX(), eye.getY(), eye.getZ(), lookAtPoint.getX(), lookAtPoint.getY(), lookAtPoint.getZ(), 0.0, 0.0, 1.0);

    gl.glTranslatef(0.0f, this.translationY, -this.translationZ);
    gl.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(this.rotationZ, 0.0f, 0.0f, 1.0f);

    gl.glScalef(this.scale, this.scale, this.scale);

    final boolean isShowingBaseCoordinat = this.configuration.getBaseCoordinate().isShowing();

    if (isShowingBaseCoordinat) {
      drawBaseCoordinate(gl);
    }

    if (this.rootObjects != null) {
      for (final JoglGroupObject topGroup : this.rootObjects) {
        topGroup.setShowingAxis(isShowingBaseCoordinat);
        topGroup.display(gl);
      }
    }
  }

  /**
   * 絶対座標を描画します。
   * 
   * @param gl GL
   */
  private void drawBaseCoordinate(final GL2 gl) {
    drawBaseAxis(gl);
    drawGrid(gl);
  }

  /**
   * 色を適用します。
   * 
   * @param gl GL　
   */
  private void applyColor(GL2 gl, ColorModel color) {
    gl.glColor4f(color.getRf(), color.getGf(), color.getBf(), color.getAlphaf());
  }
  
  /**
   * 絶対座標の座標軸を描画します。
   * 
   * @param gl GL
   */
  private void drawBaseAxis(GL2 gl) {
    final float axisMin = -10;
    final float axisMax = 10;
    
    // x軸の描画
    applyColor(gl, new ColorModel("red")); //$NON-NLS-1$
    gl.glBegin(GL.GL_LINES);
    gl.glVertex3f(axisMin, 0, 0);
    gl.glVertex3f(axisMax, 0, 0);
    gl.glEnd();

    // y軸の描画
    applyColor(gl, new ColorModel("blue")); //$NON-NLS-1$
    gl.glBegin(GL.GL_LINES);
    gl.glVertex3f(0, axisMin, 0);
    gl.glVertex3f(0, axisMax, 0);
    gl.glEnd();

    // z軸の描画
    applyColor(gl, new ColorModel("green")); //$NON-NLS-1$
    gl.glBegin(GL.GL_LINES);
    gl.glVertex3f(0, 0, axisMin);
    gl.glVertex3f(0, 0, axisMax);
    gl.glEnd();
  }

  /**
   * グリッドを描画します。
   * 
   * @param gl GL
   */
  private void drawGrid(GL2 gl) {
    applyColor(gl, this.configuration.getBaseCoordinate().getGridColor());
    
    final float gridMin = -1f;
    final float gridMax = 1f;
    final float gridInterval = this.configuration.getBaseCoordinate().getGridInterval();
    
    gl.glBegin(GL.GL_LINES);
    
    float y = gridMin;
    for (int i = 0; y <= gridMax; i++, y = gridMin + i*gridInterval) {
      gl.glVertex3f(gridMin, y, 0);
      gl.glVertex3f(gridMax, y, 0);
    }
    
    float x = gridMin;
    for (int i = 0; x <= gridMax; i++, x = gridMin + i*gridInterval) {
      gl.glVertex3f(x, gridMin, 0);
      gl.glVertex3f(x, gridMax, 0);
    }
    
    gl.glEnd();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRootGroups(List<GroupModel> models, GroupObjectManager manager) {
    this.rootObjects = createGroupObjects(models, manager);
    display();
  }

  /**
   * グループオブジェクトを生成します。
   * 
   * @param groupModels グループ
   * @param manager オブジェクトグループマネージャ
   * @return グループオブジェクト
   */
  private List<JoglGroupObject> createGroupObjects(List<GroupModel> groupModels, GroupObjectManager manager) {
    final JoglObjectFactory factory = new JoglObjectFactory(manager);

    final List<JoglGroupObject> groupObjects = new ArrayList<>();
    for (final GroupModel groupModel : groupModels) {
      final JoglGroupObject groupObject = factory.create(groupModel);
      groupObjects.add(groupObject);
    }
    return groupObjects;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfiguration(ConfigurationModel configuration) {
    if (configuration == null) {
      return;
    }

    this.configuration = configuration;
    display();
  }

  /**
   * 設定を返します。
   * 
   * @return 設定
   */
  @Override
  public ConfigurationModel getConfiguration() {
    return this.configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reshape(GLAutoDrawable drawable, @SuppressWarnings("unused") int x, @SuppressWarnings("unused") int y, int width, int height) {
    final GL2 gl = (GL2)drawable.getGL();
    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
    gl.glLoadIdentity();
    this.glu.gluPerspective(10.0, (double)width / (double)height, 0.1, 1000.0);
    gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
  }

  /**
   * マウス左クリック時の処理　カメラを移動させます。
   * 
   * @param e マウスイベント
   */
  @Override
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
  public void mouseClicked(@SuppressWarnings("unused") MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void mouseEntered(@SuppressWarnings("unused") MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void mouseExited(@SuppressWarnings("unused") MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void mouseReleased(@SuppressWarnings("unused") MouseEvent e) {
    // nothing to do
  }

  /**
   * マウスをドラッグさせるとカメラ座標が移動させます。
   * 
   * {@inheritDoc}
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      this.endPoint = getMousePosition(true);
      if (this.endPoint == null) {
        return;
      }

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
      if (this.endPoint == null) {
        return;
      }

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
      if (this.endPoint == null) {
        return;
      }

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
  public void mouseMoved(@SuppressWarnings("unused") MouseEvent e) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRequiredToCallDisplay() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateDisplay() {
    display();
  }

  /**
   * {@inheritDoc}
   */
  public void dispose(@SuppressWarnings("unused") GLAutoDrawable arg0) {
    // TODO 自動生成されたメソッド・スタブ
  }

  /**
   * 移動・回転・拡大・縮小等の操作をリセットし、初期状態に戻します。
   */
  public void resetToInitialState() {
    this.translationY = 0;
    this.translationZ = 0;
    this.rotationY = 0;
    this.rotationZ = 0;
    this.scale = 1;
  }
}
