package org.mklab.mikity.jogl;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;

import org.mklab.mikity.gui.ModelCanvas;
import org.mklab.mikity.xml.Jamast;
import org.mklab.mikity.xml.model.Group;

/**
 * JOGL用のキャンバスを表すクラスです。
 * 
 * @author iwamoto
 * @version $Revision$, 2012/01/11
 */
public class JoglModelCanvas extends GLJPanel implements ModelCanvas, GLEventListener, MouseListener, MouseMotionListener {
  /** */
  private static final long serialVersionUID = 5653656698891675370L;

  private GLU glu;

  /** オブジェクトのグループ */
  private JoglBranchGroup[] groups;

  private Jamast root;
  
  private double[] eye = {0.0,0.0, 5.0};

  /** X軸に関する回転角度 */
  private float rotationX = 0.0f;
  /** Y軸に関する回転角度 */
  private float rotationY = 0.0f;
  /** X軸方向への移動距離 */
  private float translationX = 0.0f;
  /** Y軸方向への移動距離 */
  private float translationY = 0.0f;
  /** 拡大縮小率 */
  private float scale = 0.0f;

  /** マウスボタンを押した点 */
  private Point startPoint;
  /** マウスボタンを離した点 */
  private Point endPoint;

  /** X軸に関する開始回転角度 */
  private float startRotationX;
  /** Y軸に関する終了回転角度 */
  private float startRotationY;
  /** X軸方向への移動開始距離 */
  private float startTranslationX;
  /** Y軸方向への移動開始距離 */
  private float startTranslationY;
  /** 開始拡大縮小率 */
  private float startScale;

  //光源の設定です 
  private float[] lightLocation0 = {0.5f, 1.0f, -1.0f, 1.0f}; // 平行光源1です 
  private float[] lightLocation1 = {-0.5f, 1.0f, -1.0f, 1.0f}; // 平行光源2です 
  private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f}; // 反射光の強さです 
  private float[] lightDiffuse = {0.3f, 0.3f, 0.3f, 1.0f}; // 拡散光の強さです 
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f}; // 環境光の強さです 

  /**
   * 新しく生成された<code>JoglModelCanvas</code>オブジェクトを初期化します。
   */
  public JoglModelCanvas() {
    super(new GLCapabilities());
    addGLEventListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  /**
   * Initialize the generated object of {@link JoglModelCanvas}.
   * @param root ルート
   */
  public JoglModelCanvas(Jamast root){
    super(new GLCapabilities());
    addGLEventListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    this.root = root;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(GLAutoDrawable drawable) {
    final GL gl = drawable.getGL();
    this.glu = new GLU();

    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    gl.glEnable(GL.GL_LIGHTING); //光源を有効にします 
    gl.glEnable(GL.GL_COLOR_MATERIAL); //カラーマテリアルを有効にします 
    gl.glEnable(GL.GL_LIGHT0); //0番のライトを有効にします
    gl.glEnable(GL.GL_LIGHT1);
    gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 90.0f);
    
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, this.lightLocation0, 0); // 平行光源を設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, this.lightLocation1, 0); // 平行光源を設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void display(GLAutoDrawable drawable) {
    final GL gl = drawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glEnable(GL.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    gl.glEnable(GL.GL_CULL_FACE); // 裏返ったポリゴンを描画しません 
    gl.glLoadIdentity();

    this.glu.gluLookAt(this.eye[0], this.eye[1], this.eye[2], 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

    gl.glTranslatef(this.translationY, -this.translationX, 0.0f);
    gl.glTranslatef(0.0f, 0.0f, -this.scale);
    gl.glRotatef(this.rotationX, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(this.rotationY, 0.0f, 1.0f, 0.0f);
    
    for (final JoglBranchGroup group : this.groups) {
      group.apply(gl);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void load(){
    final Group[] group = this.root.loadModel(0).loadGroup();
    setChild(group);
  }

  /**
   * {@inheritDoc}
   */
  public void setChild(Group[] group) {
    final JoglBranchGroup[] branchGroup = new JoglModelCreater().create(group);
    this.groups = branchGroup;
  }

  /**
   * {@inheritDoc}
   */
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    // nothing to do
  }

  /**
   * {@inheritDoc}
   */
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    final GL gl = drawable.getGL();
    //
    gl.glViewport(0, 0, w, h);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    this.glu.gluPerspective(10.0, (double)w / (double)h, 1.0, 100.0);
    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  /**
   * {@inheritDoc}
   */
  public void mousePressed(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e) == true) {
      this.startRotationX = this.rotationX;
      this.startRotationY = this.rotationY;
      this.startPoint = getMousePosition(true);
      if (this.endPoint == null) {
        this.endPoint = new Point(this.startPoint);
      }
    }
    
    if (SwingUtilities.isMiddleMouseButton(e) == true) {
      this.startScale = this.scale;
      this.startPoint = getMousePosition(true);
      if (this.endPoint == null) {
        this.endPoint = new Point(this.startPoint);
      }
    }
    
    if (SwingUtilities.isRightMouseButton(e) == true) {
      this.startTranslationX = this.translationX;
      this.startTranslationY = this.translationY;
      this.startPoint = getMousePosition(true);
      if (this.endPoint == null) {
        this.endPoint = new Point(this.startPoint);
      }
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
   * {@inheritDoc}
   */
  public void mouseDragged(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      this.endPoint = getMousePosition(true);
      if (this.startPoint == null) {
        this.startPoint = new Point(this.endPoint);
      }
      this.rotationX = this.startRotationX + (this.endPoint.y - this.startPoint.y);
      this.rotationY = this.startRotationY + (this.endPoint.x - this.startPoint.x);
      this.display();
    }
    
    if (SwingUtilities.isMiddleMouseButton(e)) {
      this.endPoint = getMousePosition(true);
      if (this.startPoint == null) {
        this.startPoint = new Point(this.endPoint);
      }
      final int z = Math.abs(this.endPoint.y - this.startPoint.y);
      if (this.endPoint.y > this.startPoint.y) {
        this.scale = this.startScale - z / 25.0f;
      } else {
        this.scale = this.startScale + z / 25.0f;
      }
      this.display();
    }
    
    if (SwingUtilities.isRightMouseButton(e) == true) {
      this.endPoint = getMousePosition(true);
      if (this.startPoint == null) {
        this.startPoint = new Point(this.endPoint);
      }
      this.translationX = this.startTranslationX + (this.endPoint.y - this.startPoint.y) / 100.0f;
      this.translationY = this.startTranslationY + (this.endPoint.x - this.startPoint.x) / 100.0f;
      this.display();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void mouseMoved(MouseEvent e) {
    // nothing to do
  }
}
