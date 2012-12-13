package drawables;

import javax.media.opengl.GL2;

public abstract class Drawable {
	
	protected int sampler0;
	protected int sampler1;
	protected int texturesOn;
	protected int texture0;
	protected int texture1;

	public Drawable(int sampler0, int sampler1, int texturesOn, int texture0, int texture1)
	{
		this.sampler0 = sampler0;
		this.sampler1 = sampler1;
		this.texturesOn = texturesOn;
		this.setTexture0(texture0);
		this.texture1 = texture1;
	}

	public void setTexture0(int texture0) {
		this.texture0 = texture0;
	}
	
	public void setTexture1(int texture1) {
		this.texture1 = texture1;
	}


	public void Draw(GL2 gl, int cull, int onlyLight)
	{
		gl.glPushMatrix();
		if (onlyLight == 1)
		{
			SetLight(gl);
		}
		else
		{
			DrawModels(gl, cull);
		}
		gl.glPopMatrix();
	}

	public void DrawModels(GL2 gl, int cull) {
	
	}

	public void SetLight(GL2 gl) {
	}
	
}
