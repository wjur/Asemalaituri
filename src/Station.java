import javax.media.opengl.GL2;

public class Station {

	public static void drawFace(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		
		
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 0, 16);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 0, 80);
		//gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 0, 80);
		//gl.glTexCoord2f(0,80);
		
		gl.glVertex3f(-1, 1, 0);
		

		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		
		
		
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 2, 16);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 1, 80);
		//gl.glTexCoord2f(1,80);
		
		gl.glVertex3f(1, 1, 0);
		

		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		
		
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 2, 0);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 1, 0);
		//gl.glTexCoord2f(1,0);
		
		
		gl.glVertex3f(1, 0, 0);

		gl.glColor3f(1, 0, 0);
		gl.glNormal3f(0, 0, -1);
		
		
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE0, 0, 0);
		gl.glMultiTexCoord2f(GL2.GL_TEXTURE1, 0, 0);
		//gl.glTexCoord2f(0,0);
		
		gl.glVertex3f(-1, 0, 0);
		gl.glEnd();
	}

	public static void drawExterior(GL2 gl) {
		
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

	public static void drawInterior(GL2 gl, int tid1, int tid2) {
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
		
		 //Assign the first texture to GL_TEXTURE1_ARB
	     gl.glActiveTexture(GL2.GL_TEXTURE0);
	     gl.glEnable(GL2.GL_TEXTURE_2D);
	     gl.glBindTexture(GL2.GL_TEXTURE_2D, tid2);
	     //gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_OBJECT_LINEAR);
	     //gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_OBJECT_LINEAR);
	     //gl.glEnable(GL2.GL_TEXTURE_GEN_S);
	     //gl.glEnable(GL2.GL_TEXTURE_GEN_T);
	   
	    //Assign the first texture to GL_TEXTURE1_ARB
	    gl.glActiveTexture(GL2.GL_TEXTURE1);
	     gl.glEnable(GL2.GL_TEXTURE_2D);
	     gl.glBindTexture(GL2.GL_TEXTURE_2D, tid1);
	     
	     /*
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_TEXTURE_ENV_MODE,GL2.GL_COMBINE);
	   //you can select different function for rgb and alpha
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_COMBINE_RGB,GL2.GL_ADD);
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_COMBINE_ALPHA,GL2.GL_ADD);
	   //plain addition obviously needs two sources
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_SOURCE0_RGB,GL2.GL_PREVIOUS);
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_SOURCE1_RGB,GL2.GL_TEXTURE);
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_SOURCE0_ALPHA,GL2.GL_PREVIOUS);
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_SOURCE1_ALPHA,GL2.GL_TEXTURE);
	   //operand allows you to invert components or to expand alpha components to rgb
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_OPERAND0_RGB,GL2.GL_SRC_COLOR);
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_OPERAND1_RGB,GL2.GL_SRC_COLOR);
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_OPERAND0_ALPHA,GL2.GL_SRC_ALPHA);
	     gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_OPERAND1_ALPHA,GL2.GL_SRC_ALPHA);
	     */
	     
	     //gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
	     //gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
	     //gl.glEnable(GL2.GL_TEXTURE_GEN_S);
	     //gl.glEnable(GL2.GL_TEXTURE_GEN_T);
	     
		gl.glPushMatrix();
		gl.glTranslatef(0, 2.5f, 0.0f);
		// gl.glTranslatef(0, 0, -20);
		gl.glScalef(5, 1, 40);
		gl.glRotatef(90, 1, 0, 0);
		gl.glTranslatef(0, -0.5f, 0.0f);
		drawFace(gl);
		gl.glPopMatrix();
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		
		//gl.glDisable(GL2.GL_TEXTURE_2D);
		//gl.glDisable(GL2.GL_TEXTURE_2D);
		//gl.glDisable(GL2.GL_TEXTURE_2D);
		//gl.glDisable(GL2.GL_BLEND);
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		
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
}
