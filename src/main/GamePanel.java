package main;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import Inputs.KeyBoardInputs;
import Inputs.MouseInputs;

public class GamePanel extends JPanel{ //hinh anh cua game ben trong Jframe
	//VERSION
	private static final long serialVersionUID = 1L;
	//VERSION
	
	//INPUT TU THIET BI NGOAI
	private MouseInputs mouseInputs;
	//INPUT TU THIET BI NGOAI
	
	//CAC TINH NANG LIEN QUAN DEN GAME
	private Game game;
	//CAC TINH NANG LIEN QUAN DEN GAME
	
	public GamePanel(Game game) {
		//INPUT
		mouseInputs = new MouseInputs(this); 
		addKeyListener(new KeyBoardInputs(this)); //nhan dien cac trang thai cua phim bam ban phim
		addMouseListener(mouseInputs); //nhan dien cac trang thai click
		addMouseMotionListener(mouseInputs); //nhan dien cac trang thai move, drag cua con chuot
		//INPUT
		
		//INIT GAME
		this.game = game;
		//INIT GAME
		
		//WINDOW SETTINGS
		setPanelSize();
		//WINDOW SETTINGS
	}
	

	private void setPanelSize() {
		Dimension size = new Dimension(1280, 720);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}
	/*
	 Thực tế, sau khi cài độ lớn màn hình vào khoảng 400px*400px, có một
	 phần nhỏ ở phần rìa phía trên màn hình chính là thanh ghi tiêu đề
	 của trò chơi. Hàm setPanelSize có tác dụng hiệu chỉnh lại
	 sự ngứa mắt đó.
	 */

		
	public void updateGame() {

	}

	
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		//Magic! Can co ham nay de ve duoc hoat anh trong game!
		
		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}
}
