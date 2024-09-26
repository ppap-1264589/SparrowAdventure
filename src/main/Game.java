package main;

import java.awt.Graphics;

import entities.Player;

public class Game implements Runnable{
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	
	private Thread gameThread; // Luong trong game
	
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Player player;
		
	
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		
		gamePanel.requestFocus();
		/*Tác dụng chính là để gamePanel tập trung vào việc 
		xử lý các sự kiện từ input
		Nếu bỏ hàm này đi thì chương trình 
		sẽ không nhận input (?)
		*/
		
		
		startGameLoop(); //gameloop should be the last after all!		
	}
	
	private void initClasses() {
		player = new Player(200, 200);
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();		
	}
	
	public void update() {
		player.update();
	}
	
	public void render(Graphics g) {
		player.render(g);
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0/FPS_SET; //10^9/FPS
		double timePerUpdate = 1000000000.0/UPS_SET; //10^9/UPS
		
		long previousTime = System.nanoTime();
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while (true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime)/timePerUpdate;
			deltaF += (currentTime - previousTime)/timePerFrame;
			previousTime = currentTime;
			
			if (deltaU >= 1) {
				update();
				++updates;
				--deltaU;
			} //updating
			
			if (deltaF >= 1) {
				gamePanel.repaint();
				--deltaF;
				++frames;
			} //rendering

			
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	
}
