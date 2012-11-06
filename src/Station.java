import javax.media.opengl.GL2;


public class Station {
	
	public static void drawFace(GL2 gl) {
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
	
	public static void drawExterior(GL2 gl) {
		float[] rgba = { 0.01f, 0.01f, 0.04f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE,
				new float[] { 0.2f, 0.2f, 0.6f }, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[] { 0.1f,
				0.1f, 0.1f }, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 1.5f);
		
		
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
	
	public static void drawInterior(GL2 gl) {
		float[] rgba = { 0.16f, 0.16f, 0.14f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE,
				new float[] { 0.6f, 0.6f, 0.4f }, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[] { 0.6f, 0.6f, 0.4f }, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 1.5f);
		
		// down
		gl.glPushMatrix();
		gl.glTranslatef(0, 2.5f, 0.0f);
		// gl.glTranslatef(0, 0, -20);
		gl.glScalef(5, 1, 40);
		gl.glRotatef(90, 1, 0, 0);
		gl.glTranslatef(0, -0.5f, 0.0f);
		drawFace(gl);
		gl.glPopMatrix();

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
