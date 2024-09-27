package entities;


import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity{
	private BufferedImage[][] animations;
	
	//HINH ANH
	private int aniTick, aniIndex;
	private int aniNum = 25;
	//HINH ANH
	
	//SUBIMAGE SIZE
	private int SUB_WIDTH = 64;
	private int SUB_HEIGHT = 40;
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
	
	//CHECK COLLISION 
	private int[][] lvlData; 
	//CHECK COLLISION
	//(có vẻ) đây là cách dễ nhất để check collision giữa một nhân vật và map
	
	//HITBOX START POSITION
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	//HITBOX START POSITION
	
	/*Giải thích:
	 * SUB_WIDTH = 64 và SUB_HEIGHT = 40
	 * giờ để đảm bảo các collision của nhân vật chỉ xảy trên biên hitbox của nhân vật
	 * thì mình cần tạo một hitbox có độ lớn khoảng 20*28, chứa đúng hoạt ảnh "hữu dụng" của nhân vật
	 * 
	 * Khi đó phần bị dư ra, nằm giữa biên bên trên của hitbox và biên bên trên của subimage nhân vật
	 * vào khoảng 4px
	 * Tương tự, phần bị dư ra nằm giữa bên bên trái của hitbox và biên trái của subimage nhân vật
	 * vào khoảng 21px
	 * 
	 * Phần bị dư ra này gọi là offset
	 */
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 20 * Game.SCALE, 28 * Game.SCALE);
	}
	/*
	 * Tư tưởng bây giờ, đó là vị trí (x, y) của nhân vật 
	 * thực ra chính là vị trí ở góc(trái, trên) của hitbox nhân vật
	 * 
	 * Ta làm việc này để tiện thao tác với các biến của hitbox, giúp check collision thuận tiện hơn
	 */
	
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitBox.x - xDrawOffset), (int)(hitBox.y - yDrawOffset), width, height, null);
		/*
		 Bước 1: vẽ hoạt ảnh nhân vật
		 Ta cần xác định vị trí bắt đầu vẽ hoạt ảnh từ trị ví của hitbox
		 
		 Như trên! 
		 hitbox.x - xdrawoffset là vị trí x đầu tiên để vẽ hoạt ảnh
		 hitbox.y - ydrawoffset là vị trí y đầu tiên để vẽ hoạt ảnh
		 độ lớn của hoạt ảnh này là (width, height)
		 đương nhiên, width và height ở đây tương ứng là SUB_WIDTH * SCALE và SUB_HEIGHT * SCALE được truyền vào thông qua constructor
		 */
		
//		System.out.println((int)(hitBox.x - xDrawOffset) + " " + (int)(hitBox.y - yDrawOffset) + " " + hitBox.x + " " + hitBox.y);
		
		drawHitbox(g);
		/*
		 * Bước 2: vẽ hitbox để debug
		 * (hitbox.x, hitbox.y) ở chỗ nào thì vẽ hitbox debug ở chỗ đó (easy) 
		 */
	}

	private void updateAnimationTick() {
		++aniTick;
		if (aniTick >= aniNum) {
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
		
		if (!left && !right && !up && !down) return;
		
		float xSpeed = 0;
		float ySpeed = 0;
		
		if (left && !right)	xSpeed = -playerSpeed;
		else if (right && !left) xSpeed = playerSpeed;
		
		if (up && !down) ySpeed = -playerSpeed;
		else if (down && !up) ySpeed = playerSpeed;
		
		/*
		 Với cài đặt này, nhân vật thậm chí có thể di chuyển
		 theo đường chéo, vì left và up có thể được nhận bằng true
		 cùng một lúc qua hàm keyPressed ở KeyBoardInputs.java
		 */
				
		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.x += xSpeed;
			hitBox.y += ySpeed;
			moving = true;
		}
	} 
	
	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[9][6]; //cac hoat anh la ma tran 9*6 nhu trong anh o resource
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*SUB_WIDTH, j*SUB_HEIGHT, SUB_WIDTH, SUB_HEIGHT);
				/* cai dat: subimage cua image ban dau duoc import vao bien animation[j][i]
					
				Lay subimage, vi tri toa do goc (i*64, j*40)
				Kich co cua subimage trong image la 64*40px
				 */
			}
		}
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
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
}
