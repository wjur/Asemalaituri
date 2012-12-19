package drawables.modeled;

import javax.media.opengl.GL2;

public class Lamp extends ModeledDrawable {

	public Lamp(int texturesOn) {
		super(0, 0, texturesOn, 0, 0);
	}
	
	@Override
	public void DrawModels(GL2 gl, int cull) {
		// TODO Auto-generated method stub
		//gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		gl.glPushMatrix();
		gl.glScalef(3, 3, 3);
		gl.glRotatef(90, 0, 1, 0);
		gl.glTranslatef(0 + x, 3 + y, 0 + z);
		//gl.glUniform1i(texturesOn,0);
		DrawModel(gl);
		gl.glPopMatrix();
	}
}
