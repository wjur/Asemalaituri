import javax.media.opengl.GL2;


public class Fog {
	private float FogCol[]={0.4f,0.1f,0.1f}; // Define a nice light grey
	private float near = 0.3f;
	private float far = 50f;
	private float density = 0.03f;
	private boolean on = true;
	
	private int[] ftypes = {GL2.GL_LINEAR, GL2.GL_EXP, GL2.GL_EXP2};
	private int ftype = 1;
	
	public void ModifyDensity(float delta)
	{
		if (delta >= 1)
			density *= 1.5f;
		else
			density /= 1.5f;
		
		if (delta < 0.01f)
			delta = 0.01f;
	}
	
	public void ModifyNear(float delta)
	{
		near += delta;
	}
	
	public void ModifyFar(float delta)
	{
		far += delta;
	}
	
	public void TrunOn(GL2 gl) {
		if (isOn())
		{
			gl.glEnable(GL2.GL_FOG);
			gl.glFogfv(GL2.GL_FOG_COLOR,FogCol,0);     // Set the fog color
			gl.glFogi(GL2.GL_FOG_MODE, ftypes[ftype]); // Note the 'i' after glFog - the GL_LINEAR constant is an integer.
			gl.glFogf(GL2.GL_FOG_START, near);
			gl.glFogf(GL2.GL_FOG_END, far);
			gl.glFogf(GL2.GL_FOG_DENSITY, density);
		}
		else
		{
			gl.glDisable(GL2.GL_FOG);
		}
	}
	
	public void ChangeType()
	{
		ftype += 1;
		ftype = ftype % ftypes.length;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}
}
