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

import com.sun.opengl.util.GLUT;


/**
 * @author iwamoto
 * @version $Revision$, 2012/01/11
 */
public class JoglModelCanvas extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener {
  private GLU glu;
  private GLUT glut;

  private double[] eye = {0.0, 0.0, 30.0};

  private float rotx = 0.0f, roty = 0.0f;
  private float s_rotx, s_roty;
  private Point start_point, end_point;

  private float light_ambient[] = {0.2f, 0.2f, 0.2f, 1.0f}, light_diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f}, light_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};

  private float emerald_ambient[] = {0.0215f, 0.1745f, 0.0215f, 1.0f}, emerald_diffuse[] = {0.07568f, 0.61424f, 0.07568f, 1.0f}, emerald_specular[] = {0.633f, 0.727811f, 0.633f, 1.0f},
      emerald_shininess[] = {76.8f};

  
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
   * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
   */
  @Override
  public void init(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    this.glu = new GLU();
    this.glut = new GLUT();
    //
    gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

    float light_position0[] = {1, 0f, 1.0f, 1.0f, 0.0f};
    float light_position1[] = {-1.0f, 1.0f, 1.0f, 0.0f};

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, light_position0, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, light_position1, 0);

    gl.glEnable(GL.GL_LIGHTING);
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_LIGHT1);
    gl.glEnable(GL.GL_DEPTH_TEST);
  }

  /**
   * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
   */
  @Override
  public void display(GLAutoDrawable drawable) {
    GL gl = drawable.getGL();
    //
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    //gl.glColor3f(1.0f, 1.0f, 1.0f);
    gl.glLoadIdentity();

    this.glu.gluLookAt(this.eye[0], this.eye[1], this.eye[2], 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, this.light_ambient, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, this.light_diffuse, 0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, this.light_specular, 0);

    gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, this.light_ambient, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, this.light_diffuse, 0);
    gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, this.light_specular, 0);

    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, this.emerald_ambient, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, this.emerald_diffuse, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, this.emerald_specular, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, this.emerald_shininess, 0);

    gl.glRotatef(this.rotx, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(this.roty, 0.0f, 1.0f, 0.0f);
    
    this.glut.glutSolidSphere(1.0, 50, 50);

    //this.glut.glutSolidDodecahedron();
    gl.glFlush();
  }

  /**
   * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable, boolean, boolean)
   */
  @Override
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {

  }

  /**
   * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable, int, int, int, int)
   */
  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    GL gl = drawable.getGL();
    //
    gl.glViewport(0, 0, w, h);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    this.glu.gluPerspective(10.0, (double)w / (double)h, 1.0, 100.0);
    gl.glMatrixMode(GL.GL_MODELVIEW);
  }

  /**
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  @Override
  public void mousePressed(MouseEvent e) {
    this.s_rotx = this.rotx;
    this.s_roty = this.roty;
    this.start_point = getMousePosition(true);
  }

  /**
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseClicked(MouseEvent e) {}

  /**
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseEntered(MouseEvent e) {}

  /**
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseExited(MouseEvent e) {}

  /**
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseReleased(MouseEvent e) {}

  /**
   * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    this.end_point = getMousePosition(true);
    this.rotx = this.s_rotx + (this.end_point.y - this.start_point.y);
    this.roty = this.s_roty + (this.end_point.x - this.start_point.x);
    this.display();
  }

  /**
   * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseMoved(MouseEvent e) {}
}
