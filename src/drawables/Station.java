package drawables;

import javax.media.opengl.GL2;

public class Station extends Drawable {


	public Station(int sampler0, int sampler1, int texturesOn, int texture1, int texture2)
	{
		super(sampler0, sampler1, texturesOn, texture1, texture2);
	}
	
	private void drawFace(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 0, 32);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 0, 80);
		gl.glVertex3f(-1, 1, 0);
		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 4, 32);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 1, 80);
		gl.glVertex3f(1, 1, 0);
		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 4, 0);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 1, 0);
		gl.glVertex3f(1, 0, 0);
		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 0, 0);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 0, 0);
		gl.glVertex3f(-1, 0, 0);
		gl.glEnd();
	}

	private void drawExterior(GL2 gl, int texturesOn) {
		gl.glUniform1i(texturesOn,0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		
		float[] rgba = { 0.02f, 0.02f, 0.02f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, new float[] {
				0.4f, 0.4f, 0.4f }, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[] {
				0.4f, 0.4f, 0.4f }, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 1.0f);

		// back
		gl.glPushMatrix();
		gl.glScalef(10, 10, 10);
		gl.glTranslatef(0, 0, 2);

		gl.glRotatef(0, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// front
		gl.glPushMatrix();
		gl.glScalef(10, 10, 10);
		gl.glTranslatef(0, 0, -2);
		gl.glRotatef(180, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// left
		gl.glPushMatrix();
		gl.glScalef(10, 10, 20);
		gl.glTranslatef(1, 0, 0);

		gl.glRotatef(90, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// right
		gl.glPushMatrix();
		gl.glScalef(10, 10, 20);
		gl.glTranslatef(-1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// down
		gl.glPushMatrix();
		// gl.glTranslatef(0, 0, -20);
		gl.glScalef(10, 1, 40);
		gl.glRotatef(90, 1, 0, 0);
		gl.glTranslatef(0, -0.5f, 0.0f);

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
	}

	public void drawInterior(GL2 gl, int cull){ // int tid_peron, int tid_m, int texturesOn, int sampler0, int sampler1) {
		
		float[] rgba = { 0.16f, 0.16f, 0.14f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, new float[] {
				0.6f, 0.6f, 0.4f }, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[] {
				0.3f, 0.3f, 0.1f }, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 5.5f);

		// down
		//gl.glBlendFunc(GL2.GL_ONE, GL2.GL_ONE_MINUS_SRC_ALPHA);
		//gl.glEnable(GL2.GL_BLEND);
		gl.glUniform1i(texturesOn,2);
		 //Assign the first texture to GL_TEXTURE1_ARB
	     gl.glActiveTexture(GL2.GL_TEXTURE0);
	     //gl.glEnable(GL2.GL_TEXTURE_2D);
	     gl.glBindTexture(GL2.GL_TEXTURE_2D, super.texture0);
	     //gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_OBJECT_LINEAR);
	     //gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_OBJECT_LINEAR);
	     //gl.glEnable(GL2.GL_TEXTURE_GEN_S);
	     //gl.glEnable(GL2.GL_TEXTURE_GEN_T);
	   
	    //Assign the first texture to GL_TEXTURE1_ARB
	    gl.glActiveTexture(GL2.GL_TEXTURE1);
	    //gl.glEnable(GL2.GL_TEXTURE_2D);
	    gl.glBindTexture(GL2.GL_TEXTURE_2D, super.texture1);
		
	    gl.glUniform1i(super.sampler0,0);
	    gl.glUniform1i(super.sampler1,1);
		
	     
		gl.glPushMatrix();
		gl.glTranslatef(0, 2.5f, 0.0f);
		// gl.glTranslatef(0, 0, -20);
		gl.glScalef(5, 1, 40);
		gl.glRotatef(90, 1, 0, 0);
		gl.glTranslatef(0, -0.5f, 0.0f);
		drawFace(gl);
		gl.glPopMatrix();
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		gl.glUniform1i(texturesOn,0);
		// left
		gl.glPushMatrix();
		gl.glScalef(10, 2.5f, 20);
		gl.glTranslatef(0.5f, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();

		// left
		gl.glPushMatrix();
		gl.glScalef(10, 2.5f, 20);
		gl.glTranslatef(-0.5f, 0, 0);

		gl.glRotatef(90, 0, 1, 0);
		drawFace(gl);
		gl.glPopMatrix();
	}

	@Override
	public void DrawModels(GL2 gl, int cull) {
		// TODO Auto-generated method stub
		this.drawInterior(gl, cull);
		this.drawExterior(gl, cull);
	}
}
