package drawables;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;


public class Column extends Drawable {
	public Column(int sampler0,int texturesOn, int texture0) {
		super(sampler0, -1, texturesOn, texture0, -1);
		// TODO Auto-generated constructor stub
	}

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
	
	private static float tex1[] = {
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		0,0, 1,0, 1,1, 0,1,
		};
	
	static FloatBuffer normals_buffer = Buffers.newDirectFloatBuffer(normals1);
	static FloatBuffer vertices_buffer = Buffers.newDirectFloatBuffer(vertices1);
	static FloatBuffer colors_buffer = Buffers.newDirectFloatBuffer(colors1);
	static FloatBuffer tex_buffer = Buffers.newDirectFloatBuffer(tex1);
	
	public void draw(GL2 gl, int cull) { //float x, float y, float z, int tid, int texturesOn) {
		//gl.glUniform1i(super.texturesOn,1);
		//gl.glBindTexture(GL2.GL_TEXTURE_2D, super.texture0);
		
			
		gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
		gl.glEnableClientState (GL2.GL_TEXTURE_COORD_ARRAY); 
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

		gl.glNormalPointer(GL2.GL_FLOAT, 0, normals_buffer);
		//gl.glColorPointer(3, GL2.GL_FLOAT, 0, colors_buffer);
		gl.glVertexPointer(3, GL2.GL_FLOAT, 0, vertices_buffer);
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, tex_buffer);



		gl.glTranslatef(x, y+6f, z);
		gl.glScalef(0.5f, 4.0f, 0.5f);
		gl.glDrawArrays(GL2.GL_TRIANGLES, 0, 36);



		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glDisableClientState (GL2.GL_TEXTURE_COORD_ARRAY); 
		//gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
	}
	
	@Override
	public void DrawModels(GL2 gl, int cull) {
		// TODO Auto-generated method stub
		draw(gl, cull);
	}
}
