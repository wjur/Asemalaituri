import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener {

	private Scene scene;

	public Keyboard(Scene scene) {
		// TODO Auto-generated constructor stub
		this.scene = scene;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			scene.camera.MoveForward(1);
			break;
			
		case KeyEvent.VK_S:
			scene.camera.MoveForward(-1);
			break;
			
		case KeyEvent.VK_A:
			scene.camera.MoveLeft(1);
			break;
			
		case KeyEvent.VK_D:
			scene.camera.MoveLeft(-1);
			break;

		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			scene.camera.MoveForward(0);
			break;
			
		case KeyEvent.VK_S:
			scene.camera.MoveForward(0);
			break;
			
		case KeyEvent.VK_A:
			scene.camera.MoveLeft(0);
			break;
			
		case KeyEvent.VK_D:
			scene.camera.MoveLeft(0);
			break;

		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
