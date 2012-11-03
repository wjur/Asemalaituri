import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

@SuppressWarnings("serial")
public class Scene extends GLJPanel implements GLEventListener {

	private static int width;
	private static int height;
	private FPSAnimator animator;
	public Camera camera;

	public Scene() {
		// TODO Auto-generated constructor stub
		setFocusable(true);
		addGLEventListener(this);
		animator = new FPSAnimator(this, 30, false);
		animator.start();
		camera = new Camera();
		width = height = 600;
	}

	private void drawStation(GL2 gl) {

		// back
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 20);
		gl.glScalef(10, 10, 10);
		gl.glRotatef(0, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// front
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -20);
		gl.glScalef(10, 10, 10);
		gl.glRotatef(180, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// left
		gl.glPushMatrix();
		gl.glTranslatef(10, 0, 0);
		gl.glScalef(10, 10, 20);
		gl.glRotatef(90, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// right
		gl.glPushMatrix();
		gl.glTranslatef(-10, 0, 0);
		gl.glScalef(10, 10, 20);
		gl.glRotatef(-90, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// down
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -20);
		gl.glRotatef(90, 1, 0, 0);
		// gl.glTranslatef(-10, 0, 0);
		gl.glScalef(10, 40, 1);

		drawFace(gl);
		gl.glPopMatrix();

		// up
		gl.glPushMatrix();
		gl.glTranslatef(0, 10, 20);
		gl.glRotatef(-90, 1, 0, 0);
		// gl.glTranslatef(-10, 0, 0);
		gl.glScalef(10, 40, 1);

		drawFace(gl);
		gl.glPopMatrix();

		/*
		 * gl.glPushMatrix(); gl.glScalef(10, 10, 1); gl.glRotatef(180, 0, 1,
		 * 0); gl.glTranslatef(0, 0, 10); drawFace(gl); gl.glPopMatrix();
		 * 
		 * gl.glPushMatrix(); gl.glScalef(30, 10, 1); gl.glRotatef(23, 0, 1, 0);
		 * gl.glTranslatef(0, 0, 10); drawFace(gl); gl.glPopMatrix();
		 */

		/*
		 * gl.glPushMatrix(); gl.glScalef(10, 10, 1); gl.glTranslatef(0, 0, 20);
		 * gl.glRotatef(180, 0, 1, 0); drawFace(gl); gl.glPopMatrix();
		 */

		/*
		 * gl.glPushMatrix(); gl.glScalef(10, 40, 1); gl.glTranslatef(0, 0, 20);
		 * 
		 * gl.glRotatef(90, 0, 1, 0); drawFace(gl); gl.glPopMatrix();
		 */

		/*
		 * gl.glColor3f(1, 0, 0); //gl.glNormal3f(arg0, arg1, arg2)
		 * gl.glVertex3f(-1, 1, 1);
		 * 
		 * gl.glColor3f(0, 1, 0); gl.glVertex3f(1, 1, 1); gl.glColor3f(0, 0, 1);
		 * gl.glVertex3f(1, -1, 1); gl.glColor3f(1, 1, 0); gl.glVertex3f(-1, -1,
		 * 1);
		 * 
		 * gl.glColor3f(1, 0, 0); gl.glVertex3f(-1, 1, -1); gl.glColor3f(0, 1,
		 * 0); gl.glVertex3f(1, 1, -1); gl.glColor3f(0, 0, 1); gl.glVertex3f(1,
		 * -1, -1); gl.glColor3f(1, 1, 0); gl.glVertex3f(-1, -1, -1);
		 */

	}

	private void drawFace(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glVertex3f(-1, 1, 0);

		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glVertex3f(1, 1, 0);

		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glVertex3f(1, 0, 0);

		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glVertex3f(-1, 0, 0);
		gl.glEnd();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glLoadIdentity();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		camera.Update(animator.getLastFPSUpdateTime());

		drawStation(gl);

		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_DEPTH_TEST);
		// gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		// gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_TRUE);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU glu = new GLU();

		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 0, 0.5f, SHINE_ALL_DIRECTIONS };
		float[] lightColorAmbient = { 0.2f, 0.2f, 0.2f, 1f };
		float[] lightColorSpecular = { 0.8f, 0.8f, 0.8f, 1f };

		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightColorSpecular, 0);

		gl.glShadeModel(GL2.GL_SMOOTH);

		gl.glEnable(GL2.GL_LIGHT1);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, 1);

		float[] rgba = { 0.5f, 0.5f, 0.5f };
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE,
				new float[] { 0.9f, 0, 0 }, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, new float[] { 0.0f,
				0.9f, 0 }, 0);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 55.5f);

		// gl.glOrtho(-10, 10 ,-10, 10, 0.001,100);
		glu.gluPerspective(1, (double) getWidth() / getHeight(), 0.3, 50);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU glu = new GLU();

		// gl.glOrtho(-10, 10 ,-10, 10, 0.001,100);
		glu.gluPerspective(100, (double) getWidth() / getHeight(), 0.1, 100);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame();
		final Scene scene = new Scene();
		window.getContentPane().add(scene);
		window.setSize(width, height);
		window.setVisible(true);
		scene.BindMouseKeyboard();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * double[][] m_arr = {{1,0}, {0,2}}; Matrix m = new Matrix(m_arr);
		 * //m.show(); double[][] v_arr = {{1,2}}; Matrix v = new Matrix(v_arr);
		 * //v.show();
		 * 
		 * (v.times(m)).show();
		 */
	}

	private void BindMouseKeyboard() {
		// TODO Auto-generated method stub
		Mouse mouse = new Mouse(width, height, this);
		addMouseMotionListener(mouse);
		Keyboard keyboard = new Keyboard(this);
		addKeyListener(keyboard);
	}

}
