package drawables.modeled;

import javax.media.opengl.GL2;

public class SpotlightB extends Spotlight {
	public SpotlightB(int texturesOn, int lightId) {
		super(texturesOn, lightId);
	}

	@Override
	public void SetLight(GL2 gl) {
		float f1 = Math.abs((float) (Math.cos(lapsedTime * 0.00001f / (5 * 3.14))));
		float f2 = Math
				.abs((float) (Math.cos(lapsedTime * 0.00001f / (20 * 3.14))));
		float SHINE_ALL_DIRECTIONS = 1;
		float[] lightPos = { 0, 0.0235f, 0.01f, SHINE_ALL_DIRECTIONS };
		float[] lightDir = { 0, 0, 1, SHINE_ALL_DIRECTIONS };

		float[] lightColorAmbient = { 0.02f, 0.02f, 0.02f, 1f };
		float[] lightColorSpecular = { 0f, f1, (1 - f1), 1f };

		// Set light parameters.
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_SPOT_DIRECTION, lightDir, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_SPECULAR, lightColorSpecular, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_DIFFUSE, lightColorSpecular, 0);
		gl.glLightf(GL2.GL_LIGHT3, GL2.GL_LINEAR_ATTENUATION, 0.05f);
		gl.glLightf(GL2.GL_LIGHT3, GL2.GL_QUADRATIC_ATTENUATION, 0.03f);
		gl.glLightf(GL2.GL_LIGHT3, GL2.GL_SPOT_CUTOFF, 60 - 20 * f2);
		gl.glEnable(GL2.GL_LIGHT3);
	}
}
