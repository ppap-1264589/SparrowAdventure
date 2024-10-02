package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyBoardInputs implements KeyListener{
	private GamePanel gamePanel;
	
	public KeyBoardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {

	} 
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) { 
		    case KeyEvent.VK_W:
				gamePanel.getGame().getPlayer().setUp(false);
				break;
			case KeyEvent.VK_A:
			    gamePanel.getGame().getPlayer().setLeft(false);
			    break;
		    case KeyEvent.VK_S:
			    gamePanel.getGame().getPlayer().setDown(false);
			    break;
		    case KeyEvent.VK_D:
			    gamePanel.getGame().getPlayer().setRight(false);
			    break;
			case KeyEvent.VK_SPACE:
				gamePanel.getGame().getPlayer().setJump(false);
				break;

		}
	}
	/*
	 Có một vấn đề khi dùng hàm keyRelease thông thường:
	 keyRelease(){
	 	switch (e.getKeyCode()){
	 	case KeyEnvent.VK_W:
	 	case KeyEnvent.VK_A:
		case KeyEnvent.VK_D:
	 	case KeyEnvent.VK_S:
	 		gamePanel.getGame().getPlayer().setMoving(false);
	 		break;
	 	}
	 	
	 Giả sử giờ ta nhấn giữ nút A, sau đó nhấn giữ nút D
	 sau đó, nhả nút A, nút D sẽ không làm nhân vật di chuyển
	 về bên phải nữa
	 Bởi lẽ KeyRelease đã phát hiện nút A nhả ra
	 -> Cần phải cài đặt hàm KeyRelease với 4 setter cho 4 trường hợp
	 up, down, left, right
	 }
	 */
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				gamePanel.getGame().getPlayer().setUp(true);
				break;
		    case KeyEvent.VK_A:
			    gamePanel.getGame().getPlayer().setLeft(true);
			    break;
		    case KeyEvent.VK_S:
			    gamePanel.getGame().getPlayer().setDown(true);
			    break;
		    case KeyEvent.VK_D:
			    gamePanel.getGame().getPlayer().setRight(true);
			    break;
			case KeyEvent.VK_SPACE:
				gamePanel.getGame().getPlayer().setJump(true);
				break;
		}
	}
}
