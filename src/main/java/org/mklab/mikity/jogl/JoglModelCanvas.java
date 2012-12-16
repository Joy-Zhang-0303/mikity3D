package org.mklab.mikity.jogl;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.SwingUtilities;

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

  private float rotx = 0.0f;
  private float roty = 0.0f;
  private float s_rotx;
  private float s_roty;
  private Point start_point;
  private Point end_point;
  private float scale = 0.0f;
  private float s_scale;
  private float translatex = 0.0f;
  private float translatey = 0.0f;
  private float s_translatex;
  private float s_translatey;

  //光源の設定です 
  private float[] lightPosition0 = {0.5f, 1.0f, -1.0f, 1.0f}; // 平行光源1です 
  private float[] lightPosition1 = {-0.5f, 1.0f, -1.0f, 1.0f}; // 平行光源2です 
  private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f}; // 反射光の強さです 
  private float[] lightDiffuse = {0.3f, 0.3f, 0.3f, 1.0f}; // 拡散光の強さです 
  private float[] lightAmbient = {0.2f, 0.2f, 0.2f, 1.0f}; // 環境光の強さです 

  /**
   * Initialize the generated object of {@link JoglModelCanvas}.
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
   * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
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
    
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, this.lightPosition0, 0); // 平行光源を設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, this.lightPosition1, 0); // 平行光源を設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, this.lightSpecular, 0); // 反射光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, this.lightDiffuse, 0); // 拡散光の強さを設定します 
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, this.lightAmbient, 0); // 環境光の強さを設定します
  }

  /**
   * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
   */
  @Override
  public void display(GLAutoDrawable drawable) {
    final GL gl = drawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glEnable(GL.GL_DEPTH_TEST); // 奥行き判定を有効にします 
    gl.glEnable(GL.GL_CULL_FACE); // 裏返ったポリゴンを描画しません 
    gl.glLoadIdentity();

    this.glu.gluLookAt(this.eye[0], this.eye[1], this.eye[2], 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

    gl.glTranslatef(this.translatey, -this.translatex, 0.0f);
    gl.glTranslatef(0.0f, 0.0f, -this.scale);
    gl.glRotatef(this.rotx, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(this.roty, 0.0f, 1.0f, 0.0f);
    
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
      this.s_rotx = this.rotx;
      this.s_roty = this.roty;
      this.start_point = getMousePosition(true);
      if (this.end_point == null) {
        this.end_point = new Point(this.start_point);
      }
    }
    
    if (SwingUtilities.isMiddleMouseButton(e) == true) {
      this.s_scale = this.scale;
      this.start_point = getMousePosition(true);
      if (this.end_point == null) {
        this.end_point = new Point(this.start_point);
      }
    }
    
    if (SwingUtilities.isRightMouseButton(e) == true) {
      this.s_translatex = this.translatex;
      this.s_translatey = this.translatey;
      this.start_point = getMousePosition(true);
      if (this.end_point == null) {
        this.end_point = new Point(this.start_point);
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
      this.end_point = getMousePosition(true);
      if (this.start_point == null) {
        this.start_point = new Point(this.end_point);
      }
      this.rotx = this.s_rotx + (this.end_point.y - this.start_point.y);
      this.roty = this.s_roty + (this.end_point.x - this.start_point.x);
      this.display();
    }
    
    if (SwingUtilities.isMiddleMouseButton(e)) {
      this.end_point = getMousePosition(true);
      if (this.start_point == null) {
        this.start_point = new Point(this.end_point);
      }
      final int z = Math.abs(this.end_point.y - this.start_point.y);
      if (this.end_point.y > this.start_point.y) {
        this.scale = this.s_scale - z / 25.0f;
      } else {
        this.scale = this.s_scale + z / 25.0f;
      }
      this.display();
    }
    
    if (SwingUtilities.isRightMouseButton(e) == true) {
      this.end_point = getMousePosition(true);
      if (this.start_point == null) {
        this.start_point = new Point(this.end_point);
      }
      this.translatex = this.s_translatex + (this.end_point.y - this.start_point.y) / 100.0f;
      this.translatey = this.s_translatey + (this.end_point.x - this.start_point.x) / 100.0f;
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
