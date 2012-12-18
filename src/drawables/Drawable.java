package drawables;

import javax.media.opengl.GL2;

public abstract class Drawable {
	
	protected int sampler0;
	protected int sampler1;
	protected int texturesOn;
	protected int texture0;
	protected int texture1;
	
	//pos
	protected float x,y,z;
	//angles
	protected float ax, ay, az;
	private float[] ambient_color;
	private float[] diffuse_color;
	private float[] specular_color;
	private float shininess;
	
	public static long lapsedTime;

	public Drawable(int sampler0, int sampler1, int texturesOn, int texture0, int texture1)
	{
		this.sampler0 = sampler0;
		this.sampler1 = sampler1;
		this.texturesOn = texturesOn;
		this.setTexture0(texture0);
		this.texture1 = texture1;
		x=y=z=ax=ay=az=0;
		ambient_color = new float[] {0,0,0};
		diffuse_color = new float[] {0,0,0};
		specular_color = new float[] {0,0,0};
		shininess = 1;
	}
	
	
	
	public void SetPos(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void SetAngles(float ax, float ay, float az)
	{
		this.ax = ax;
		this.ay = ay;
		this.az = az;
	}

	public void setTexture0(int texture0) {
		this.texture0 = texture0;
	}
	
	public void setTexture1(int texture1) {
		this.texture1 = texture1;
	}
	
	public void PreDraw(GL2 gl)
	{
		
	}
	
	public void PostDraw(GL2 gl)
	{
		
	}


	public void Draw(GL2 gl, int cull, int onlyLight)
	{
		gl.glPushMatrix();
		PreDraw(gl);
		
		if (onlyLight == 1)
		{
			SetLight(gl);
		}
		else
		{
			setMaterials(gl);
			DrawModels(gl, cull);
		}
		PostDraw(gl);
		gl.glPopMatrix();
	}

	public void DrawModels(GL2 gl, int cull) {
	
	}

	public void SetLight(GL2 gl) {
	}
	
	protected void setMaterials(GL2 gl)
	{
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ambient_color, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, diffuse_color, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, specular_color, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess);
	}
	
	public void SetColorsShininess(float a[], float d[], float s[], float frac)
	{
		this.ambient_color = a;
		this.diffuse_color = d;
		this.specular_color = s;
		this.shininess = frac;
	}
	
}
