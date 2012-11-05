import javax.media.opengl.glu.GLU;

public class Camera {
	private Matrix position;
	private Matrix up;
	private Matrix forward;
	private GLU glu;
	private Matrix right;

	private double /*ro,*/ phi;
	
	private int moveForward, moveLeft;

	public Camera() {
		position = new Matrix(new double[][] { { 0, 5, -4, 1 } });
		up = new Matrix(new double[][] { { 0, 1, 0, 1 } });
		right = new Matrix(new double[][] { { -1, 0, 0, 1 } });
		forward = new Matrix(new double[][] { { 0, 0, -1, 1 } });
		glu = new GLU();
		/*ro =*/ phi = 0;
		moveForward = moveLeft = 0;
	}

	public void Update(long l) {
		if (l > 2000)
			l = 2000;
		
		float back = (float)l * 0.0005f;
		if (moveForward != 0)
			position = position.minus(forward.times(back * moveForward));
		
		if (moveLeft != 0)
			position = position.minus(right.times(back * moveLeft));
		
		Matrix position_forward = position.minus(forward);
		glu.gluLookAt(position.Get(0, 0), position.Get(0, 1),
				position.Get(0, 2), position_forward.Get(0, 0),
				position_forward.Get(0, 1), position_forward.Get(0, 2),
				up.Get(0, 0), up.Get(0, 1), up.Get(0, 2));

	}

	public void Look(int dx, int dy) {
		LookLeftRight(dx);
		LookUpDown(dy);
	}

	private void LookUpDown(int px) {
		float step = 0.01f * (float) px;

		double halfPi = Math.PI / 2.0;

		/*if (phi + step >= halfPi) {
			step = (float) (halfPi - phi);
			phi = halfPi;
		} else if (phi + step <= -halfPi) {
			step = (float) (-halfPi - phi);
			phi = -halfPi;
		} else {
			phi += step;
		}*/

		up = Matrix.Rotate(step, right, up);
		forward = Matrix.Rotate(step, right, forward);

	}

	private void LookLeftRight(int px) {
		float step = 0.01f * (float) px;

		/*double pi = Math.PI;

		if (ro + step > pi) {
			step = (float) (pi - ro);
			ro = pi;
		} else if (ro + step < -pi) {
			step = (float) (-pi - ro);
			ro = -pi;
		} else {
			ro += step;
		}*/
		right = Matrix.Rotate(step, up, right);
		forward = Matrix.Rotate(step, up, forward);
	}

	public void MoveForward(int i) {
		moveForward = i;
	}
	
	public void MoveLeft(int i)
	{
		moveLeft = i;
	}
}
