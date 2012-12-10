import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener {

	private Scene scene;

	public Keyboard(Scene scene) {
		this.scene = scene;
	}
	@Override
	public void keyPressed(KeyEvent e) {
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
			
		case KeyEvent.VK_1:
			scene.fog.setOn(!scene.fog.isOn());
			break;
		
		case KeyEvent.VK_2:
			scene.fog.ModifyDensity(1);
			break;
		case KeyEvent.VK_3:
			scene.fog.ModifyDensity(-1);
			break;
			
		case KeyEvent.VK_4:
			scene.fog.ChangeType();
			break;
		case KeyEvent.VK_5:
			//scene.fog.ModifyDensity(-0.1f);
			break;	

		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch(e.getKeyCode())
		{
		
			
		}
	}

}
