package entities;


import static utilz.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Player extends Entity{
	private BufferedImage[][] animations;
	
	//HINH ANH
	private int aniTick, aniIndex;
	private int aniSpeed = 18;
	//HINH ANH
	
	//SUBIMAGE SIZE
	private int SUB_HEIGHT = 64;
	private int SUB_WIDTH = 40;
	//SUBIMAGE SIZE
	
	//PLAYER_ACTION
	private int playerAction = IDLE;
	//PLAYER_ACTION
	

	//PLAYER_STATE_MOVING
	private boolean moving = false;
	private boolean left, up, right, down;
	//PLAYER_STATE_MOVING
	
	//PLAYER_STATE_ATTACKING
	private boolean attacking = false; 
	//PLAYER_STATE_ATTACKING
	
	//MOVING PARAMETERS
	private float playerSpeed = 2.0f;
	//MOVING PARAMETERS
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, width, height, null);
		/*
		Ve ra man hinh subimage tai vi tri toa do (x, y)
		Kich co cua subimage duoc ve ra la 256*160px
		 */
	}

	private void updateAnimationTick() {
		++aniTick;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			++aniIndex;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				/*
				 sau khi chạy hàm setAnimation, cho playerAction = ATTACK_1;
				 hàm updatePos sẽ không có thao tác tương ứng cho ATTACK_1
				 nên khi tấn công, nhân vật sẽ đứng im
				 
				 sau khi chạy xong GetSpriteAmout(ATTACK_1), nhân vật ngừng
				 animation tấn công
				 */
			}
		}		
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		if (moving) {
			playerAction = RUNNING;
		}
		else playerAction = IDLE;
		
		if (attacking) {
			playerAction = ATTACK_1;
		}
		
		if (startAni != playerAction){
			resetAniTick();
		}
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}
	/*
	 chả hạn: khi mình đang ở playerAction = IDLE, mà chuyển sang tấn công
	 thì playerAction sẽ thành ATTACK_1
	 
	 Tuy nhiên rất có thể AniIndex của mình 
	 đang nằm ở một ví trí nào đó khác 0 tại thời điểm mình ATTACK
	 do đó, mỗi khi phát hiện playerAction bị thay đổi, 
	 cần chuyển AniIndex và AniTick về 0 ngay
	 */

	private void updatePos() { //di chuyen theo huong
		moving = false;
		
		if (left && !right) {
			x -= playerSpeed;
			moving = true;
		}
		else if (right && !left) {
			x += playerSpeed;
			moving = true;
		}
		
		if (up && !down) {
			y -= playerSpeed;
			moving = true;
		}
		else if (down && !up) {
			y += playerSpeed;
			moving = true;
		}
	} 
	/*
	 Với cài đặt này, nhân vật thậm chí có thể di chuyển
	 theo đường chéo, vì left và up có thể được nhận bằng true
	 cùng một lúc qua hàm keyPressed ở KeyBoardInputs.java
	 */
	

	
	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[9][6]; //cac hoat anh la ma tran 9*6 nhu trong anh o resource
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*SUB_HEIGHT, j*SUB_WIDTH, SUB_HEIGHT, SUB_WIDTH);
				/* cai dat: subimage cua image ban dau duoc import vao bien animation[j][i]
					
				Lay subimage, vi tri toa do goc (i*64, j*40)
				Kich co cua subimage trong image la 64*40px
				 */
			}
		}
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void resetDirBooleans() {
		left = right = up = down = false;
	}
	
	public void setAttack(boolean attacking) {
		this.attacking = attacking;		
	}
	/*
	 chứ không phải là moving = false
	 vì moving = false rồi thì up, hoặc left, right, down
	 vẫn có thể là true, chúng lại gán moving = true lần nữa trong
	 updatePos()
	 */
}
