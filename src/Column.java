import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;


public class Column {
	private static float vertices1[] = { 1, 1, 1, -1, 1, 1, -1, -1, 1, // v0-v1-v2 (front)
			-1, -1, 1, 1, -1, 1, 1, 1, 1, // v2-v3-v0

			1, 1, 1, 1, -1, 1, 1, -1, -1, // v0-v3-v4 (right)
			1, -1, -1, 1, 1, -1, 1, 1, 1, // v4-v5-v0

			1, 1, 1, 1, 1, -1, -1, 1, -1, // v0-v5-v6 (top)
			-1, 1, -1, -1, 1, 1, 1, 1, 1, // v6-v1-v0

			-1, 1, 1, -1, 1, -1, -1, -1, -1, // v1-v6-v7 (left)
			-1, -1, -1, -1, -1, 1, -1, 1, 1, // v7-v2-v1

			-1, -1, -1, 1, -1, -1, 1, -1, 1, // v7-v4-v3 (bottom)
			1, -1, 1, -1, -1, 1, -1, -1, -1, // v3-v2-v7

			1, -1, -1, -1, -1, -1, -1, 1, -1, // v4-v7-v6 (back)
			-1, 1, -1, 1, 1, -1, 1, -1, -1 }; // v6-v5-v4

	// normal array
	private static  float normals1[] = { 0, 0, 1, 0, 0, 1, 0, 0, 1, // v0-v1-v2 (front)
			0, 0, 1, 0, 0, 1, 0, 0, 1, // v2-v3-v0

			1, 0, 0, 1, 0, 0, 1, 0, 0, // v0-v3-v4 (right)
			1, 0, 0, 1, 0, 0, 1, 0, 0, // v4-v5-v0

			0, 1, 0, 0, 1, 0, 0, 1, 0, // v0-v5-v6 (top)
			0, 1, 0, 0, 1, 0, 0, 1, 0, // v6-v1-v0

			-1, 0, 0, -1, 0, 0, -1, 0, 0, // v1-v6-v7 (left)
			-1, 0, 0, -1, 0, 0, -1, 0, 0, // v7-v2-v1

			0, -1, 0, 0, -1, 0, 0, -1, 0, // v7-v4-v3 (bottom)
			0, -1, 0, 0, -1, 0, 0, -1, 0, // v3-v2-v7

			0, 0, -1, 0, 0, -1, 0, 0, -1, // v4-v7-v6 (back)
			0, 0, -1, 0, 0, -1, 0, 0, -1 }; // v6-v5-v4

	// color array
	private static float colors1[] = { 1, 1, 1, 1, 1, 0, 1, 0, 0, // v0-v1-v2 (front)
			1, 0, 0, 1, 0, 1, 1, 1, 1, // v2-v3-v0

			1, 1, 1, 1, 0, 1, 0, 0, 1, // v0-v3-v4 (right)
			0, 0, 1, 0, 1, 1, 1, 1, 1, // v4-v5-v0

			1, 1, 1, 0, 1, 1, 0, 1, 0, // v0-v5-v6 (top)
			0, 1, 0, 1, 1, 0, 1, 1, 1, // v6-v1-v0

			1, 1, 0, 0, 1, 0, 0, 0, 0, // v1-v6-v7 (left)
			0, 0, 0, 1, 0, 0, 1, 1, 0, // v7-v2-v1

			0, 0, 0, 0, 0, 1, 1, 0, 1, // v7-v4-v3 (bottom)
			1, 0, 1, 1, 0, 0, 0, 0, 0, // v3-v2-v7

			0, 0, 1, 0, 0, 0, 0, 1, 0, // v4-v7-v6 (back)
			0, 1, 0, 0, 1, 1, 0, 0, 1 }; // v6-v5-v4
	
	static FloatBuffer normals_buffer = Buffers.newDirectFloatBuffer(normals1);
	static FloatBuffer vertices_buffer = Buffers.newDirectFloatBuffer(vertices1);
	static FloatBuffer colors_buffer = Buffers.newDirectFloatBuffer(colors1);
	
	public static void draw(GL2 gl, float x, float y, float z) {
		float[] rgba = { 0.6f, 0.6f, 0.6f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE,
				new float[] { 0.6f, 0.6f, 0.6f }, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[] { 0.1f, 0.1f, 0.1f }, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 0.5f);
			
		gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		//gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

		gl.glNormalPointer(GL2.GL_FLOAT, 0, normals_buffer);
		//gl.glColorPointer(3, GL2.GL_FLOAT, 0, colors_buffer);
		gl.glVertexPointer(3, GL2.GL_FLOAT, 0, vertices_buffer);

		gl.glPushMatrix();

		gl.glTranslatef(x, y+6f, z);
		gl.glScalef(0.5f, 4.0f, 0.5f);
		gl.glDrawArrays(GL2.GL_TRIANGLES, 0, 36);

		gl.glPopMatrix();

		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
	}
}