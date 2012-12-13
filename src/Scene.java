import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Random;
import java.util.Vector;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import drawables.*;
import drawables.modeled.GLModel;
import drawables.modeled.Lamp;
import drawables.modeled.Spotlight;
import drawables.modeled.SpotlightA;
import drawables.modeled.SpotlightB;

@SuppressWarnings("serial")
public class Scene extends GLJPanel implements GLEventListener {

	private static int width;
	private static int height;
	private FPSAnimator animator;
	public Camera camera;
	public Fog fog;
	private Random random;
	
	public int tid_grass;

	long startTime, lastTime;
	long lapsed, delta;
	private int tid_m;
	private int tid_m2;
	private int tid_peron;
	public int texturesOn;
	private int sampler0;
	private int sampler1;
	public int selected_tid_m;
	private int fogOn;
	private Station s;
	
	private Column[] columns;
	private Lamp[] lamps;
	private Spotlight sl;
	private Spotlight s2;
	
	private IntBuffer intBuffer;
	private int fbo;
	private int rbo;
	private int screenTexture;
	private GLU glu;
	
	Vector<Drawable> sceneObjects;
	private int[] cubeMap;
	private Texture cubemaptexture;
	private int tid_burak;
	private int envfbo;
	private int envrbo;
	private int shaderProgram;


	public Scene() {
		setFocusable(true);
		addGLEventListener(this);
		animator = new FPSAnimator(this, 60, false);
		animator.start();
		camera = new Camera();
		width = height = 800;
		random = new Random();
		lastTime = startTime = System.nanoTime();
		sceneObjects = new Vector<Drawable>();
		intBuffer = IntBuffer.allocate(1);
		glu = new GLU();
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glLoadIdentity();
		fog.Apply();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_STENCIL_BUFFER_BIT );

		int nextint = random.nextInt();
		long now = System.nanoTime();
		delta = now - lastTime;
		if (delta - 50000000 > 0)
			delta = 50000000;
		lastTime = now;
		lapsed = now - startTime;
		lapsed /= 1000; // Can I haz milisecondz? 
		delta /= 1000;
		
		renderToEnvMap(gl);
		
		gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, fbo);
		gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, rbo);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, screenTexture);
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0,GL2.GL_RGB, 768, 768, 0,GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, null);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
		gl.glFramebufferTextureARB(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, screenTexture, 0);
		
		gl.glRenderbufferStorage(GL2.GL_RENDERBUFFER, GL2.GL_DEPTH_STENCIL, 800, 800);
		gl.glFramebufferRenderbuffer( GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_RENDERBUFFER, rbo);
		gl.glFramebufferRenderbuffer( GL2.GL_FRAMEBUFFER,  GL2.GL_STENCIL_ATTACHMENT,  GL2.GL_RENDERBUFFER, rbo);
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_STENCIL_BUFFER_BIT );
		
		gl.glPushMatrix();
		Matrix position = new Matrix(new double[][] { { 2.87f, 4.23f, -8.626, 1 } });
		Matrix forward = new Matrix(new double[][] { { 0.2, -0.2, -1, 1 } });
		Matrix up = new Matrix(new double[][] { { 0, 1, 0, 1 } });
		Matrix position_forward = position.minus(forward);
		glu.gluLookAt(position.Get(0, 0), position.Get(0, 1),
				position.Get(0, 2), position_forward.Get(0, 0),
				position_forward.Get(0, 1), position_forward.Get(0, 2),
				up.Get(0, 0), up.Get(0, 1), up.Get(0, 2));
		//System.out.println("*");
		renderWithMirror(gl, true, nextint);
		gl.glPopMatrix();
		gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
		
		camera.Update(delta);
		renderWithMirror(gl, false, nextint);
		
		
		gl.glFlush();
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}
	


	private void renderWithMirror(GL2 gl, boolean fakeCamera, int nextint) {
		
		
		gl.glPushMatrix();
		Drawable.lapsedTime = lapsed;
		

		gl.glColorMask(true,true,true,true);
		gl.glPushMatrix();
		gl.glScalef(1, 1, -1);
		gl.glTranslatef(0, 0, -8);
		gl.glEnable(GL2.GL_CLIP_PLANE0);
		gl.glClipPlane(GL2.GL_CLIP_PLANE0, new double[]{0.0,0.0,-1,4}, 0);
		drawAll(gl, GL2.GL_CW, nextint);
		drawTeapotEnvMap(gl);
		gl.glDisable(GL2.GL_CLIP_PLANE0);
		gl.glPopMatrix();
		

		
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_STENCIL_BUFFER_BIT);
		
		gl.glStencilOp(GL2.GL_REPLACE, GL2.GL_REPLACE, GL2.GL_REPLACE);
		gl.glStencilFunc(GL2.GL_ALWAYS, 1, 1);
		gl.glEnable(GL2.GL_STENCIL_TEST);
		
		
		
		gl.glColorMask(false,false,false,false);
		
		//LUSTRO
		/*
		gl.glFrontFace(GL2.GL_CCW);
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(0,0,0,0.2f);
		gl.glVertex3f(-2.5f, 5f, 4);
		gl.glColor4f(0,0,0,0.2f);
		gl.glVertex3f(2.5f, 5f, 4);
		gl.glColor4f(0,0,0,0.2f);
		gl.glVertex3f(2.5f, -2.5f, 4);
		gl.glColor4f(0,0,0,0.2f);
		gl.glVertex3f(-2.5f, -2.5f, 4);
		gl.glEnd();*/
		
		float DEG2RAD = 3.14159f/180f;
		float xradius = 4;
		float yradius = 2;
		   gl.glEnable(GL2.GL_BLEND);
		   gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_CONSTANT_ALPHA);
		   gl.glBegin(GL2.GL_POLYGON);
		   
		   for (int i=0; i < 360; i++)
		   {
		      //convert degrees into radians
		      float degInRad = i*DEG2RAD;
		      gl.glColor4f(0,0,0,0.5f);
		      gl.glVertex3f((float)(Math.cos(degInRad)*xradius),(float)(Math.sin(degInRad)*yradius)+5, 4);
		   }
		 
		   gl.glEnd();
		   gl.glDisable(GL2.GL_BLEND);
		
		
		gl.glStencilOp(GL2.GL_KEEP, GL2.GL_KEEP, GL2.GL_KEEP);
		gl.glStencilMask(GL2.GL_NOTEQUAL);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glDisable(GL2.GL_STENCIL_TEST);
		
		//gl.glColorMask(false,false,false,false);
		gl.glColorMask(true,true,true,true);
		drawAll(gl, GL2.GL_CCW, nextint);
		drawTeapotEnvMap(gl);
		
		if(!fakeCamera)
		{
			gl.glFrontFace(GL2.GL_CCW);
			gl.glUniform1i(texturesOn,3);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, screenTexture);

			
			gl.glPushMatrix();
			gl.glTranslatef(0, 0, -10);
			gl.glRotatef(45, 1, 0, 0);
			gl.glTranslatef(0, 0, -2);
			gl.glBegin(GL2.GL_TRIANGLES);
			
			
			gl.glColor4f(1, 1, 1,1);
			gl.glTexCoord2d(1,1);
			gl.glVertex3f(-2.5f, 5f, 0);
			
			gl.glColor4f(1, 1, 1,1);
			gl.glTexCoord2d(0,1);
			gl.glVertex3f(2.5f, 5f, 0);
			
			gl.glColor4f(1, 1, 1,1);
			gl.glTexCoord2d(0,0);
			gl.glVertex3f(2.5f, 2.5f, 0);

			
			gl.glColor4f(1, 1, 1,1);
			gl.glTexCoord2d(0,0);
			gl.glVertex3f(2.5f, 2.5f, 0);
			
			gl.glColor4f(1, 1, 1,1);
			gl.glTexCoord2d(1,0);
			gl.glVertex3f(-2.5f, 2.5f, 0);
			
			gl.glColor4f(1, 1, 1,1);
			gl.glTexCoord2d(1,1);
			gl.glVertex3f(-2.5f, 5f, 0);
			
			
			
			gl.glEnd();
			gl.glPopMatrix();
		}
		gl.glDisable(GL2.GL_BLEND);
		gl.glPopMatrix();
	}

	private void drawAll(GL2 gl, int cull, int nextint) {
		gl.glFrontFace(cull);
		setGlobalLight(gl, nextint);
		//s.DrawModels(gl, cull);
		s.setTexture0(selected_tid_m == 0 ? tid_m : tid_m2);
		for (int i = 0; i < sceneObjects.size(); i++) {
			((Drawable)sceneObjects.get(i)).Draw(gl, cull, 1);
		}
		
		for (int i = 0; i < sceneObjects.size(); i++) {
			((Drawable)sceneObjects.get(i)).Draw(gl, cull, 0);
		}
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
		
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_TRUE);
		gl.glLoadIdentity();
		GLU glu = new GLU();

		glu.gluPerspective(1, (double) getWidth() / getHeight(), 0.3, 50);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		

		setShaders(gl);
		loadTextures(gl);
		createObjects(gl);
		
		cubemaptexture = loadCubemap(gl);
		
	}
	
	public static Texture loadCubemap(GL2 gl) {
		Texture cubemap = TextureIO.newTexture(GL2.GL_TEXTURE_CUBE_MAP);
		int[] targets = { GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
				GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
				GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
				GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
				GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
				GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };
		for (int i = 0; i < 6; i++) {

			TextureData data;
			try {
				data = TextureIO.newTextureData(GLProfile.getDefault(), Thread
						.currentThread().getContextClassLoader()
						.getResourceAsStream("./liscie.png"), false, "png");
				cubemap.updateImage(gl, data, targets[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cubemap;
	}
	private static int envTexSize = 256;
	
	//TODO przeformatowac
	protected void drawTeapotEnvMap(GL2 gl) {
		gl.glUseProgram(0);
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		
		cubemaptexture.bind(gl);
		gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_REFLECTION_MAP);
		gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_REFLECTION_MAP);
		gl.glTexGeni(GL2.GL_R, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_REFLECTION_MAP);
		gl.glPushMatrix();
		gl.glColor3d(1, 1, 1);
		gl.glTranslatef(2, 4, 0);
		
		//TODO odkomentowac
		//gl.glTranslated(envSphereCenter[0], envSphereCenter[1],
		//		envSphereCenter[2]);
		
		// gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_TEXTURE_CUBE_MAP);
		gl.glEnable(GL2.GL_TEXTURE_GEN_S);
		gl.glEnable(GL2.GL_TEXTURE_GEN_T);
		gl.glEnable(GL2.GL_TEXTURE_GEN_R);

		GLUT glut = new GLUT();
		// gl.glEnable(GL2.GL_NORMALIZE);
	
		//glut.glutSolidSphere(0.3, 20, 20);
		//glut.glutSolidCube(0.4f);
		gl.glFrontFace(GL2.GL_CW);
		glut.glutSolidTeapot(0.3);
		gl.glPopMatrix();

		gl.glDisable(GL2.GL_TEXTURE_GEN_S);
		gl.glDisable(GL2.GL_TEXTURE_GEN_T);
		gl.glDisable(GL2.GL_TEXTURE_GEN_R);

		gl.glDisable(GL2.GL_TEXTURE_CUBE_MAP);

		gl.glUseProgram(shaderProgram);
	}
	
	private void renderToEnvMap(GL2 gl) {
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glPushAttrib(GL2.GL_VIEWPORT_BIT);
		gl.glViewport(0, 0, envTexSize, envTexSize);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		GLU glu = new GLU();

		glu.gluPerspective(90, 1, 0.1, 40);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		
		
		//TODO odkomentować
		//gl.glTranslated(-envSphereCenter[0], -envSphereCenter[1],
		//		 -envSphereCenter[2]);
		
		
		//gl.glTranslated(cameraPosition[0], cameraPosition[1], cameraPosition[2]);
		 //positionCamera(gl);

		 
		 
		gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, cubemaptexture.getTextureObject(gl));

		//gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MIN_FILTER,
		 //GL2.GL_NEAREST);
		 //gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MAG_FILTER,
		 //GL2.GL_NEAREST);

		// gl.glTexParameteri(target,GL.GL_TEXTURE_WRAP_S,GL2.GL_CLAMP);
		// gl.glTexParameteri(target,GL.GL_TEXTURE_WRAP_T,GL2.GL_CLAMP);
		// null means reserve texture memory, but texels are undefined

		//gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S,
		//		GL2.GL_CLAMP);
		//gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T,
		//		GL2.GL_CLAMP);
		//g//l.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R,
		//		GL2.GL_CLAMP);
		//gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MIN_FILTER,
		//		GL2.GL_LINEAR);
		//gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_MAG_FILTER,
				//GL2.GL_LINEAR);

		gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, envfbo);

		// Attach 2D texture to this FBO

		gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, envrbo);
		gl.glRenderbufferStorage(GL2.GL_RENDERBUFFER, GL2.GL_DEPTH_COMPONENT,
				envTexSize, envTexSize);
		gl.glFramebufferRenderbuffer(GL2.GL_FRAMEBUFFER,
				GL2.GL_DEPTH_ATTACHMENT, GL2.GL_RENDERBUFFER,
				envrbo);
		if (gl.glCheckFramebufferStatus(GL2.GL_FRAMEBUFFER) == GL2.GL_FRAMEBUFFER_COMPLETE)
			;// System.out.println("[Viewer] GL_FRAMEBUFFER_COMPLETE!!");
		else
			System.out.println("..cazzo ^^");

		// gl.glTexEnvf(GL2.GL_TEXTURE_ENV,GL2.GL_TEXTURE_ENV_MODE,
		// GL2.GL_REPLACE);
		for (int i = 0; i < 6; i++) {

			int[] targets = { GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
					GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
					GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
					GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
					GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
					GL2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };
			int target = targets[i];

			gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER,
					GL2.GL_COLOR_ATTACHMENT0, target, cubemaptexture.getTextureObject(gl), 0);

			gl.glClear(GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_COLOR_BUFFER_BIT);

			gl.glPushMatrix();
			switch (i) {
			case 0:
				glu.gluLookAt(0, 0, 0, 1, 0, 0, 0, -1, 0);
				break;
			case 1:
				glu.gluLookAt(0, 0, 0, -1, 0, 0, 0, -1, 0);
				break;
			case 2:
				glu.gluLookAt(0, 0, 0, 0, 1, 0, 0, 0, 1);
				break;
			case 3:
				 glu.gluLookAt(0, 0, 0, 0, -1, 0,  0, 0, -1);
				break;
			case 4:
				 glu.gluLookAt(0, 0, 0, 0, 0, 1, 0, -1, 0);
				break;
			case 5:
				glu. gluLookAt(0, 0, 0, 0, 0, -1, 0, -1, 0);
				break;
			}
			//glu.gluLookAt(0, 0, 0,
			//		forward[0], forward[1],
			//		forward[2], up[0], up[1], up[2]);
			drawAll(gl, GL2.GL_CCW, 0);

			gl.glPopMatrix();
		}
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPopAttrib();
		gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
		gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP, 0);
	}
	
	private void loadTextures(GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glGenTextures(1, intBuffer);
		intBuffer.clear();
		intBuffer.rewind();
		screenTexture = intBuffer.get(0);
		intBuffer.clear();
		
		intBuffer = IntBuffer.allocate(6);
		gl.glGenTextures(6, intBuffer);
		intBuffer.clear();
		intBuffer.rewind();
		cubeMap = intBuffer.array();
		intBuffer.clear();
		intBuffer = IntBuffer.allocate(1);

		tid_grass = TextureLoader.setupTextures("./gfx/liscie.png", gl);
		tid_m = TextureLoader.setupTextures("./gfx/m.png", gl);
		tid_m2 = TextureLoader.setupTextures("./gfx/m2.png", gl);
		tid_peron = TextureLoader.setupTextures("./gfx/peron2.png", gl);
		tid_burak = TextureLoader.setupTextures("./gfx/burakocos.png", gl);
		selected_tid_m = 0;
		
		//tid_grass  = screenTexture;
		
		gl.glGenFramebuffers(1, intBuffer);
		intBuffer.rewind();
		fbo = intBuffer.get(0);
		intBuffer.clear();
		
		gl.glGenFramebuffers(1, intBuffer);
		intBuffer.rewind();
		envfbo = intBuffer.get(0);
		intBuffer.clear();
		
		gl.glGenRenderbuffers(1, intBuffer);
		intBuffer.rewind();
		rbo = intBuffer.get(0);
		
		gl.glGenRenderbuffers(1, intBuffer);
		intBuffer.rewind();
		envrbo = intBuffer.get(0);


		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
	}

	private void createObjects(GL2 gl) {
		s = new Station(sampler0, sampler1, texturesOn, tid_m, tid_peron);
		sceneObjects.add(s);
		
		columns = new Column[5];
		
		for (int i = -2; i <= 2; i++)
		{
			columns[i+2] = new Column(sampler0, texturesOn, tid_grass);
			((Drawable)columns[i+2]).SetPos(0, 0,  7 * (float) i);
			sceneObjects.add(columns[i+2]);
		}
		
		GLModel spotlightmodel = drawables.modeled.ModelLoaderOBJ.LoadModel("./models/spot.obj", "./models/spot.mtl", gl);
		
		//gl, 3, 0, 0, -135
		s2 = new SpotlightB(texturesOn, GL2.GL_LIGHT3);
		s2.SetModel(spotlightmodel);
		//4.5f, 0, 0, -90);
		s2.SetPos(3f, 0, 0);
		s2.SetAngles(0, -135, 0);
		
		sceneObjects.add(s2);
		
		sl = new SpotlightA(texturesOn, GL2.GL_LIGHT2);
		sl.SetModel(spotlightmodel);
		//4.5f, 0, 0, -90);
		sl.SetPos(4.5f, 0, 0);
		sl.SetAngles(0, -90, 0);
		
		sceneObjects.add(sl);
		
		
		GLModel lampmodel = drawables.modeled.ModelLoaderOBJ.LoadModel("./models/lamp.obj", "./models/lamp.mtl", gl);
		lamps = new Lamp[6];
		for (int i = 3; i >= -2; i--)
		{
			lamps[i+2] = new Lamp(texturesOn);
			lamps[i+2].SetPos(2.35f * (float) i - 2.35f / 2.0f, 0, 0);
			//drawLamp(gl, 2.35f * (float) i - 2.35f / 2.0f, 0, 0);
			lamps[i+2].SetModel(lampmodel);
			sceneObjects.add(lamps[i+2]);
		}
		
		
	}

	
	
	private String loadFile(String filename)
	{
		StringBuilder vertexCode = new StringBuilder();
		String line = null ;
		try
		{
		    BufferedReader reader = new BufferedReader(new FileReader(filename));
		    while( (line = reader.readLine()) !=null )
		    {
		    	vertexCode.append(line);
		    	vertexCode.append('\n');
		    }
		    reader.close();
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("unable to load shader from file ["+filename+"]", e);
		}
		
		return vertexCode.toString();
	}

	private void setShaders(GL2 gl) {
		//Create shaders
        //OpenGL ES retuns a index id to be stored for future reference.
        int vertShader = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        int fragShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

        //Compile the vertexShader String into a program.
        String[] vlines = new String[] { loadFile("vertexshader.glsl") };
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
        String[] flines = new String[] { loadFile("fragmentshader.glsl") };
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
        shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertShader);
        gl.glAttachShader(shaderProgram, fragShader);

        //Associate attribute ids with the attribute names inside
        //the vertex shader.
        //gl.glBindAttribLocation(shaderProgram, 0, "attribute_Position");
        //gl.glBindAttribLocation(shaderProgram, 1, "attribute_Color");

        gl.glLinkProgram(shaderProgram);
        gl.glUseProgram(shaderProgram);
        
        
        texturesOn = gl.glGetUniformLocation(shaderProgram, "texturesOn");
        sampler0 = gl.glGetUniformLocation(shaderProgram, "color_texture0");
        sampler1 = gl.glGetUniformLocation(shaderProgram, "color_texture1");
        fogOn = gl.glGetUniformLocation(shaderProgram, "fogOn");
        //gl.glUniform1i(texturesOn,0);
        
        fog = new Fog(fogOn, gl);
        
	}

	private void setGlobalLight(GL2 gl, int nextint) {
		float f1 = (float) (Math.cos(lapsed * 0.00001f / (5 * 3.14)));
		float f2 = (float) (Math.sin(lapsed * 0.00001f / (2 * 3.14)));

		float s = (f1 < 0 && f2 < 0) ? 1 : ((nextint % 100 > 90) ? 1
				: 0);

		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 5, 11f, SHINE_ALL_DIRECTIONS };
		float[] lightColorAmbient = { 0, 0, 0, 1f };
		float[] lightColorDiffuse = { 0.9f*s, 0.9f*s, 0.9f*s, 1.0f };
		float[] lightColorSpecular = { 0.9f*s,0.9f*s,0.9f*s, 1f };

		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightColorDiffuse, 0);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_LINEAR_ATTENUATION, 0.05f);
		gl.glLightf(GL2.GL_LIGHT1, GL2.GL_QUADRATIC_ATTENUATION, 0.03f);
		gl.glEnable(GL2.GL_LIGHT1);

		float[] lightPos2 = { 0, 5, -11f, 1 };
		float[] lightColorAmbient2 = { 0.01f, 0.01f, 0.01f, 1f };
		float[] lightColorDiffuse2 = { 0.9f, 0.9f, 0.9f, 1.0f };
		float[] lightColorSpecular2 = { 0.9f,0.9f,0.9f, 1f };
		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos2, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightColorAmbient2, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightColorSpecular2, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightColorDiffuse2, 0);
		gl.glLightf(GL2.GL_LIGHT0, GL2.GL_LINEAR_ATTENUATION, 0.005f);
		gl.glLightf(GL2.GL_LIGHT0, GL2.GL_QUADRATIC_ATTENUATION, 0.02f);
		gl.glEnable(GL2.GL_LIGHT0);
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
