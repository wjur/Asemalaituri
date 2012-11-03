import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	private int dx, dy;
	private Point center;
	private Point mousePos = null;
	private Point currentPos = null;
	private Robot robot = null;
	private Boolean wrap = false;
	private Scene scene;

	public Mouse(int width, int height, Scene scene) {
		this.scene = scene;
		Point scene_pos = scene.getLocationOnScreen();
		center = new Point(scene_pos.x + (scene.getWidth() / 2), scene_pos.y
				+ (scene.getHeight() / 2));
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.

		wrap = true;
		robot.mouseMove(center.x, center.y);

	}

	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		
		if (!wrap) {
			dx = event.getXOnScreen();
			dy = event.getYOnScreen();
			scene.camera.Look(center.x - dx, center.y - dy);
			wrap = true;
			robot.mouseMove(center.x, center.y);
		} else
			wrap = false;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		wrap = true;
		robot.mouseMove(center.x, center.y);
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub

	}

}
