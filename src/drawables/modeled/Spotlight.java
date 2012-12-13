package drawables.modeled;

import javax.media.opengl.GL2;

public class Spotlight extends ModeledDrawable{

	protected int lightId;
	private float[] lightColorAmbient;
	private float[] lightColorSpecular;

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
	public void SetLight(GL2 gl) {

		float f1 = Math
				.abs((float) (Math.cos(lapsedTime * 0.00001f / (10 * 3.14))));
		
		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 0.0235f, 0.01f, SHINE_ALL_DIRECTIONS };
		float[] lightDir = { 0, 0, 1, SHINE_ALL_DIRECTIONS };
		

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
	}
	
	@Override
	public void DrawModels(GL2 gl, int cull) {
		gl.glUniform1i(texturesOn,0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		super.DrawModel(gl);
	}
}
