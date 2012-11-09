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
	
	
	
	static final String vertexShader =
			// For GLSL 1 and 1.1 code i highly recomend to not include a
			// GLSL ES language #version line, GLSL ES section 3.4
			// Many GPU drivers refuse to compile the shader if #version is different from
			// the drivers internal GLSL version.
			"#ifdef GL_ES \n" +
			"precision mediump float; \n" + // Precision Qualifiers
			"precision mediump int; \n" + // GLSL ES section 4.5.2
			"#endif \n" +

			"uniform mat4 uniform_Projection; \n" + // Incomming data used by
			"attribute vec4 attribute_Position; \n" + // the vertex shader
			"attribute vec4 attribute_Color; \n" + // uniform and attributes

			"varying vec4 varying_Color; \n" + // Outgoing varying data
			                                      // sent to the fragment shader
			"void main(void) \n" +
			"{ \n" +
			" varying_Color = attribute_Color; \n" +
			" //gl_Position = uniform_Projection * attribute_Position; \n" +
			" gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n " +
			"} ";

			/* Introducing the OpenGL ES 2 Fragment shader
			*
			* The main loop of the fragment shader gets executed for each visible
			* pixel fragment on the render buffer.
			*
			* vertex-> *
			* (0,1,-1) /f\
			* /ffF\ <- This fragment F gl_FragCoord get interpolated
			* /fffff\ to (0.25,0.25,-1) based on the
			* vertex-> *fffffff* <-vertex three vertex gl_Position.
			* (-1,-1,-1) (1,-1,-1)
			*
			*
			* All incomming "varying" and gl_FragCoord data to the fragment shader
			* gets interpolated based on the vertex positions.
			*
			* The fragment shader produce and store the final color data output into
			* gl_FragColor.
			*
			* Is up to you to set the final colors and calculate lightning here based on
			* supplied position, color and normal data.
			*
			* The whole fragment shader program are a String containing GLSL ES language
			* http://www.khronos.org/registry/gles/specs/2.0/GLSL_ES_Specification_1.0.17.pdf
			* sent to the GPU driver for compilation.
			*/
			static final String fragmentShader =
			"#ifdef GL_ES \n" +
			"precision mediump float; \n" +
			"precision mediump int; \n" +
			"#endif \n" +

			"varying vec4 varying_Color; \n" + //incomming varying data to the
			                                        //frament shader
			                                        //sent from the vertex shader
			"void main (void) \n" +
			"{ \n" +
			" gl_FragColor = varying_Color; \n" +
			" gl_FragColor = vec4(0.5, 0.0, 1.0, 1.0);\n" +
			"} ";
	

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

		//System.out.println("*");
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
		//gl.glEnable(GL2.GL_LIGHTING);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_TRUE);
		gl.glLoadIdentity();
		GLU glu = new GLU();

		if (false == loadModels(gl)) {
			System.exit(1);
		}

		glu.gluPerspective(1, (double) getWidth() / getHeight(), 0.3, 50);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		
		
		
		
		setShaders(gl);
		
	}

	private void setShaders(GL2 gl) {
		//Create shaders
        //OpenGL ES retuns a index id to be stored for future reference.
        int vertShader = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        int fragShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

        //Compile the vertexShader String into a program.
        String[] vlines = new String[] { vertexShader };
        int[] vlengths = new int[] { vlines[0].length() };
        gl.glShaderSource(vertShader, vlines.length, vlines, vlengths, 0);
        gl.glCompileShader(vertShader);

        //Check compile status.
        int[] compiled = new int[1];
        gl.glGetShaderiv(vertShader, GL2.GL_COMPILE_STATUS, compiled,0);
        if(compiled[0]!=0){System.out.println("Horray! vertex shader compiled");}
        else {
            int[] logLength = new int[1];
            gl.glGetShaderiv(vertShader, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(vertShader, logLength[0], (int[])null, 0, log, 0);

            System.err.println("Error compiling the vertex shader: " + new String(log));
            System.exit(1);
        }

        //Compile the fragmentShader String into a program.
        String[] flines = new String[] { fragmentShader };
        int[] flengths = new int[] { flines[0].length() };
        gl.glShaderSource(fragShader, flines.length, flines, flengths, 0);
        gl.glCompileShader(fragShader);

        //Check compile status.
        gl.glGetShaderiv(fragShader, GL2.GL_COMPILE_STATUS, compiled,0);
        if(compiled[0]!=0){System.out.println("Horray! fragment shader compiled");}
        else {
            int[] logLength = new int[1];
            gl.glGetShaderiv(fragShader, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(fragShader, logLength[0], (int[])null, 0, log, 0);

            System.err.println("Error compiling the fragment shader: " + new String(log));
            System.exit(1);
        }

        //Each shaderProgram must have
        //one vertex shader and one fragment shader.
        int shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertShader);
        gl.glAttachShader(shaderProgram, fragShader);

        //Associate attribute ids with the attribute names inside
        //the vertex shader.
        //gl.glBindAttribLocation(shaderProgram, 0, "attribute_Position");
        //gl.glBindAttribLocation(shaderProgram, 1, "attribute_Color");

        gl.glLinkProgram(shaderProgram);
        gl.glUseProgram(shaderProgram);
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
