package drawables.modeled;

import javax.media.opengl.GL2;

import drawables.Drawable;

public abstract class ModeledDrawable extends Drawable {

	private GLModel model;
	
	public ModeledDrawable(int sampler0, int sampler1, int texturesOn,
			int texture0, int texture1) {
		super(sampler0, sampler1, texturesOn, texture0, texture1);
		// TODO Auto-generated constructor stub
	}
	//
	//ModelLoaderOBJ.LoadModel("./models/spot.obj",
	//		"./models/spot.mtl", gl);
	
	public void SetModel(GLModel model)
	{
		this.model = model;
	}
	
	protected void DrawModel(GL2 gl)
	{
		model.opengldraw(gl);
	}

}
