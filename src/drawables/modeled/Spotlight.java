package drawables.modeled;

import javax.media.opengl.GL2;

public class Spotlight extends ModeledDrawable{

	private int lightId;

	public Spotlight(int texturesOn, int lightId) {
		super(0, 0, texturesOn, 0, 0);
		this.lightId = lightId;
	}
	
	@Override
	public void DrawModels(GL2 gl, int cull) {
		// TODO Auto-generated method stub
		float f1 = Math
				.abs((float) (Math.cos(lapsedTime * 0.00001f / (10 * 3.14))));
		gl.glPushMatrix();
		gl.glTranslatef(0 + x, 3.6f + y, 0 + z);
		gl.glScalef(40, 40, 40);
		gl.glRotatef(ay, 0, 1, 0);
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
		float[] lightColorSpecular = { 1f, 0.5f, 1f, 0.3f };

		// Set light parameters.
		gl.glLightfv(lightId, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(lightId, GL2.GL_SPOT_DIRECTION, lightDir, 0);
		gl.glLightfv(lightId, GL2.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(lightId, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(lightId, GL2.GL_DIFFUSE, lightColorSpecular, 0);
		gl.glLightf(lightId, GL2.GL_LINEAR_ATTENUATION, 0.05f);
		gl.glLightf(lightId, GL2.GL_QUADRATIC_ATTENUATION, 0.03f);
		gl.glLightf(lightId, GL2.GL_SPOT_CUTOFF, 60 * f1);
		gl.glEnable(lightId);
		gl.glUniform1i(texturesOn,0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		super.DrawModel(gl);

		gl.glPopMatrix();
	}
}
