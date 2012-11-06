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


	public Scene() {
		// TODO Auto-generated constructor stub
		setFocusable(true);
		addGLEventListener(this);
		animator = new FPSAnimator(this, 30, false);
		animator.start();
		camera = new Camera();
		width = height = 800;
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glLoadIdentity();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		camera.Update(animator.getLastFPSUpdateTime());
		setGlobalLight(gl);
		
		Station.drawExterior(gl);
		Station.drawInterior(gl);
		for (int i = -2; i<= 2; i++)
			Column.draw(gl, 0, 0, 7*(float)i);
		// drawBin(gl, 4, 0, -1);
		
		
		for (int i = -2; i<= 3; i++)
				drawLamp(gl, 2.35f*(float)i - 2.35f/2.0f , 0, 0);
		
		/*gl.glPushMatrix();
		gl.glScalef(100, 100, 100);
		gl.glRotatef(90, 0, 1, 0);
		spotModel.opengldraw(gl);// .draw(gl);
		gl.glPopMatrix();*/
		
		
		gl.glFlush();
	}

	private void drawLamp(GL2 gl, float x, float y, float z) {
		gl.glPushMatrix();
		gl.glScalef(3, 3, 3);
		gl.glRotatef(90, 0, 1, 0);
		gl.glTranslatef(0+x, 3+y, 0+z);
		lampModel.opengldraw(gl);
		gl.glPopMatrix();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
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

		if (false == loadModels(gl))
		{
			System.exit(1);
		}

		glu.gluPerspective(1, (double) getWidth() / getHeight(), 0.3, 50);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	private Boolean loadModels(GL2 gl) {
		spotModel = ModelLoaderOBJ.LoadModel("./models/spot.obj", "./models/spot.mtl", gl);
		lampModel = ModelLoaderOBJ.LoadModel("./models/lamp.obj", "./models/lamp.mtl", gl);
		if (spotModel == null || lampModel == null)
		{
			return false;
		}
		return true;
	}


	private void setGlobalLight(GL2 gl) {
		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0,0,0.5f, SHINE_ALL_DIRECTIONS };
		float[] lightColorAmbient = { 0.2f, 0.2f, 0.2f, 1f };
		float[] lightColorSpecular = { 0.8f, 0.8f, 0.8f, 1f };

		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
		//gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightColorSpecular, 0);
		gl.glEnable(GL2.GL_LIGHT1);
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
	}

	private void BindMouseKeyboard() {
		// TODO Auto-generated method stub
		Mouse mouse = new Mouse(width, height, this);
		addMouseMotionListener(mouse);
		Keyboard keyboard = new Keyboard(this);
		addKeyListener(keyboard);
	}

}
