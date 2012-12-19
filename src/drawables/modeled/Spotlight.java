package drawables.modeled;

import javax.media.opengl.GL2;

public class Spotlight extends ModeledDrawable{

	protected int lightId;

	public Spotlight(int texturesOn, int lightId) {
		super(0, 0, texturesOn, 0, 0);
		this.lightId = lightId;
	}
	
	
	
	@Override
	public void PreDraw(GL2 gl) {
		gl.glTranslatef(0 + x, 3.6f + y, 0 + z);
		gl.glScalef(40, 40, 40);
		gl.glRotatef(ay, 0, 1, 0);
	}
	
	@Override
	public void DrawModels(GL2 gl, int cull) {
		//gl.glUniform1i(texturesOn,0);
		//gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		super.DrawModel(gl);
	}
}
