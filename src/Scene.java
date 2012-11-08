import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class Scene extends GLJPanel implements GLEventListener {

	private static int width;
	private static int height;
	private FPSAnimator animator;
	public Camera camera;

	private GLModel spotModel = null;
	private GLModel lampModel = null;

	private Random random;

	long startTime, lastTime;
	long lapsed, delta;

	public Scene() {
		setFocusable(true);
		addGLEventListener(this);
		animator = new FPSAnimator(this, 60, false);
		animator.start();
		camera = new Camera();
		width = height = 800;
		random = new Random();
		lastTime = startTime = System.nanoTime();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glLoadIdentity();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		long now = System.nanoTime();
		delta = now - lastTime;
		if (delta - 50000000 > 0)
			delta = 50000000;
		lastTime = now;
		lapsed = now - startTime;
		lapsed /= 1000; // Can I haz milisecondz?
		delta /= 1000;

		System.out.println("*");
		camera.Update(delta);
		setGlobalLight(gl);

		Station.drawExterior(gl);
		Station.drawInterior(gl);
		for (int i = -2; i <= 2; i++)
			Column.draw(gl, 0, 0, 7 * (float) i);

		for (int i = -2; i <= 3; i++)
			drawLamp(gl, 2.35f * (float) i - 2.35f / 2.0f, 0, 0);

		drawSpotA(gl, 4.5f, 0, 0, -90);
		drawSpotB(gl, 3, 0, 0, -135);

		gl.glFlush();
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

	private void drawSpotA(GL2 gl, float x, float y, float z, float angle) {
		float f1 = Math
				.abs((float) (Math.cos(lapsed * 0.00001f / (10 * 3.14))));
		gl.glPushMatrix();
		gl.glTranslatef(0 + x, 3.6f + y, 0 + z);
		gl.glScalef(40, 40, 40);
		gl.glRotatef(angle, 0, 1, 0);
		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 0.0235f, 0.01f, SHINE_ALL_DIRECTIONS };
		float[] lightDir = { 0, 0, 1, SHINE_ALL_DIRECTIONS };

		/*
		 * //debug :) gl.glBegin(GL2.GL_QUADS); gl.glColor3f(1, 0, 0);
		 * gl.glNormal3f(0, 0, -1); gl.glVertex3f(-0.01f, 0.0335f, 0.01f);
		 * 
		 * gl.glColor3f(1, 0, 0); gl.glNormal3f(0, 0, -1); gl.glVertex3f(0.01f,
		 * 0.0335f, 0.01f);
		 * 
		 * gl.glColor3f(1, 0, 0); gl.glNormal3f(0, 0, -1); gl.glVertex3f(0.01f,
		 * 0.0135f, 0.01f);
		 * 
		 * gl.glColor3f(1, 0, 0); gl.glNormal3f(0, 0, -1); gl.glVertex3f(-0.01f,
		 * 0.0135f, 0.01f); gl.glEnd();
		 */

		float[] lightColorAmbient = { 0.02f, 0.02f, 0.02f, 1f };
		float[] lightColorSpecular = { 1f, 0.5f, 1f, 1f };

		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT4, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL2.GL_LIGHT4, GL2.GL_SPOT_DIRECTION, lightDir, 0);
		gl.glLightfv(GL2.GL_LIGHT4, GL2.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT4, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT4, GL2.GL_DIFFUSE, lightColorSpecular, 0);
		gl.glLightf(GL2.GL_LIGHT4, GL2.GL_LINEAR_ATTENUATION, 0.05f);
		gl.glLightf(GL2.GL_LIGHT4, GL2.GL_QUADRATIC_ATTENUATION, 0.03f);
		gl.glLightf(GL2.GL_LIGHT4, GL2.GL_SPOT_CUTOFF, 60 * f1);
		gl.glEnable(GL2.GL_LIGHT4);
		drawSpot(gl, x, y, z, angle);

		gl.glPopMatrix();
	}

	private void drawSpotB(GL2 gl, float x, float y, float z, float angle) {
		float f1 = Math.abs((float) (Math.cos(lapsed * 0.00001f / (5 * 3.14))));
		float f2 = Math
				.abs((float) (Math.cos(lapsed * 0.00001f / (20 * 3.14))));
		gl.glPushMatrix();
		gl.glTranslatef(0 + x, 3.6f + y, 0 + z);
		gl.glScalef(40, 40, 40);
		gl.glRotatef(angle, 0, 1, 0);
		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 0.0235f, 0.01f, SHINE_ALL_DIRECTIONS };
		float[] lightDir = { 0, 0, 1, SHINE_ALL_DIRECTIONS };

		float[] lightColorAmbient = { 0.02f, 0.02f, 0.02f, 1f };
		float[] lightColorSpecular = { 0f, f1, (1 - f1), 1f };

		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_SPOT_DIRECTION, lightDir, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_DIFFUSE, lightColorSpecular, 0);
		gl.glLightf(GL2.GL_LIGHT3, GL2.GL_LINEAR_ATTENUATION, 0.05f);
		gl.glLightf(GL2.GL_LIGHT3, GL2.GL_QUADRATIC_ATTENUATION, 0.03f);
		gl.glLightf(GL2.GL_LIGHT3, GL2.GL_SPOT_CUTOFF, 60 - 20 * f2);
		gl.glEnable(GL2.GL_LIGHT3);
		drawSpot(gl, x, y, z, angle);

		gl.glPopMatrix();
	}

	private void drawSpot(GL2 gl, float x, float y, float z, float angle) {

		spotModel.opengldraw(gl);// .draw(gl);

	}

	private void drawLamp(GL2 gl, float x, float y, float z) {
		gl.glPushMatrix();
		gl.glScalef(3, 3, 3);
		gl.glRotatef(90, 0, 1, 0);
		gl.glTranslatef(0 + x, 3 + y, 0 + z);
		lampModel.opengldraw(gl);
		gl.glPopMatrix();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// do nothing
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_TRUE);
		gl.glLoadIdentity();
		GLU glu = new GLU();

		if (false == loadModels(gl)) {
			System.exit(1);
		}

		glu.gluPerspective(1, (double) getWidth() / getHeight(), 0.3, 50);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	private Boolean loadModels(GL2 gl) {
		spotModel = ModelLoaderOBJ.LoadModel("./models/spot.obj",
				"./models/spot.mtl", gl);
		lampModel = ModelLoaderOBJ.LoadModel("./models/lamp.obj",
				"./models/lamp.mtl", gl);
		if (spotModel == null || lampModel == null) {
			return false;
		}
		return true;
	}

	private void setGlobalLight(GL2 gl) {
		float f1 = (float) (Math.cos(lapsed * 0.00001f / (5 * 3.14)));
		float f2 = (float) (Math.sin(lapsed * 0.00001f / (2 * 3.14)));

		float s = (f1 < 0 && f2 < 0) ? 1 : ((random.nextInt() % 100 > 90) ? 1
				: 0);

		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 5, 11f, SHINE_ALL_DIRECTIONS };
		float[] lightColorAmbient = { 0.02f, 0.02f, 0.02f, 1f };
		float[] lightColorSpecular = { 0.7f * s, 0.7f * s, 0.9f * s, 1f };

		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
		// gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightColorSpecular, 0);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_LINEAR_ATTENUATION, 0.05f);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_QUADRATIC_ATTENUATION, 0.03f);
		gl.glEnable(GL2.GL_LIGHT1);

		float[] lightPos2 = { 0, 5, -11f, SHINE_ALL_DIRECTIONS };
		float[] lightColorAmbient2 = { 0.02f, 0.02f, 0.02f, 1f };
		float[] lightColorSpecular2 = { 0.7f, 0.7f, 0.9f, 1f };
		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, lightPos2, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, lightColorAmbient2, 0);
		// gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, lightColorSpecular2, 0);
		gl.glLightf(GL2.GL_LIGHT2, GL2.GL_LINEAR_ATTENUATION, 0.05f);
		gl.glLightf(GL2.GL_LIGHT2, GL2.GL_QUADRATIC_ATTENUATION, 0.03f);
		gl.glEnable(GL2.GL_LIGHT2);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU glu = new GLU();

		glu.gluPerspective(100, (double) getWidth() / getHeight(), 0.1, 100);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

	}

	public static void main(String[] args) {
		JFrame window = new JFrame();
		final Scene scene = new Scene();
		window.getContentPane().add(scene);
		window.setSize(width, height);
		window.setVisible(true);
		scene.BindMouseKeyboard();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void BindMouseKeyboard() {
		Mouse mouse = new Mouse(width, height, this);
		addMouseMotionListener(mouse);
		Keyboard keyboard = new Keyboard(this);
		addKeyListener(keyboard);
	}

}
