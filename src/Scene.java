import java.awt.Frame;

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

	private FPSAnimator animator;

	public Scene() {
		// TODO Auto-generated constructor stub
		setFocusable(true);
		addGLEventListener(this);
		animator = new FPSAnimator(this, 30, false);
		animator.start();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glLoadIdentity();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		// positionCamera(gl);
		
		
		//glRotatef(angle, 0, 0, 1);
		// Render colored quad
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(1,0,0); gl.glVertex3f(-1,  1, 1);
		gl.glColor3f(0, 1, 0); gl.glVertex3f( 1,  1, 1);
		gl.glColor3f(0, 0, 1); gl.glVertex3f( 1, -1, 1);
		gl.glColor3f(1, 1, 0); gl.glVertex3f(-1, -1, 1);

		gl.glColor3f(1, 0, 0); gl.glVertex3f(-1,  1, -1);
		gl.glColor3f(0, 1, 0); gl.glVertex3f( 1,  1, -1);
		gl.glColor3f(0, 0, 1); gl.glVertex3f( 1, -1, -1);
		gl.glColor3f(1, 1, 000); gl.glVertex3f(-1, -1, -1);
		/*glColor3ub(255, 000, 000); glVertex2f(-1,  1);
		glColor3ub(000, 255, 000); glVertex2f( 1,  1);
		glColor3ub(000, 000, 255); glVertex2f( 1, -1);
		glColor3ub(255, 255, 000); glVertex2f(-1, -1);*/
		gl.glEnd();
		// Swap buffers (color buffers, makes previous render visible)


	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_DEPTH_TEST);
		//gl.glEnable(GL2.GL_LIGHTING);
		//gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		//gl.glEnable(GL2.GL_CULL_FACE);
		// gl.glEnable(GL.GL_NORMALIZE);
		//gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_TRUE);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU glu = new GLU();

		// gl.glOrtho(-10, 10 ,-10, 10, 0.001,100);
		glu.gluPerspective(100, (double) getWidth() / getHeight(), 0.3, 50);
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
		/*JFrame window = new JFrame();
		window.getContentPane().add(new Scene());
		window.setSize(600, 600);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		double[][] m_arr = {{1,0}, {0,2}};
		Matrix m = new Matrix(m_arr);
		//m.show();
		double[][] v_arr = {{1,2}};
		Matrix v = new Matrix(v_arr);
		//v.show();
		
		(v.times(m)).show();
	}

}
