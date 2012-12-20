package drawables.modeled;

import javax.media.opengl.GL2;

public class SpotlightA extends Spotlight {
	public SpotlightA(int texturesOn, int lightId) {
		super(texturesOn, lightId);
	}

	@Override
	public void SetLight(GL2 gl) {
		float f1 = Math
				.abs((float) (Math.cos(lapsedTime * 0.00001f / (10 * 3.14))));
		
		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 0.0235f, 0.01f, SHINE_ALL_DIRECTIONS };
		float[] lightDir = { 0, 0, 1, SHINE_ALL_DIRECTIONS };

		float[] lightColorAmbient = { 0.02f, 0.02f, 0.02f, 1f };
		float[] lightColorSpecular = { 1f,  1f, 1f,  1f };

		gl.glLightfv(lightId, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(lightId, GL2.GL_SPOT_DIRECTION, lightDir, 0);
		gl.glLightfv(lightId, GL2.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(lightId, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(lightId, GL2.GL_DIFFUSE, lightColorSpecular, 0);
		gl.glLightf(lightId, GL2.GL_LINEAR_ATTENUATION, 0.009f);
		gl.glLightf(lightId, GL2.GL_QUADRATIC_ATTENUATION, 0.006f);
		gl.glLightf(lightId, GL2.GL_SPOT_CUTOFF, 60 * f1);
		gl.glEnable(lightId);
	}
}
