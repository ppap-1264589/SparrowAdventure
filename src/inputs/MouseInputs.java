package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener{
	private GamePanel gamePanel;
	public MouseInputs (GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
//		System.out.println("Dragging");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			gamePanel.getGame().getPlayer().setAttack(true);
		} 
		/*Nếu sự kiện bắt được là nút chuột trái thì
		 nhân vật bắt đầu tấn công! 
		 */
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}
	

}
