package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
<<<<<<< HEAD
import static utilz.Constants.*;
import static utilz.Constants.Directions.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
=======

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

>>>>>>> 4e7797b88e5162979665150eff9244a73a7697b1
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

<<<<<<< HEAD
    private BufferedImage[][] animations;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] lvlData;
//    private float xDrawOffset = 21 * Game.SCALE;
//    private float yDrawOffset = 4 * Game.SCALE;
=======
	//PLAYER_STATE_MOVING
	private boolean moving = false;
	private boolean left, up, right, down, jump;
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
>>>>>>> 4e7797b88e5162979665150eff9244a73a7697b1

    // Jumping / Gravity
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

<<<<<<< HEAD
    // StatusBarUI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);
    private int healthWidth = healthBarWidth;
=======
	//Thanh HP
	private BufferedImage statusBarImg;

	//AttackBox
	private Rectangle2D.Float attackBox;
	// Neu enemy trong tam danh nay thi enemies se chiu damage

	private int statusBarWidth = (int)(192 * Game.SCALE);
	private int statusBarHeight = (int)(58 * Game.SCALE);

	private int statusBarX = (int)(10 * Game.SCALE);
	private int statusBarY = (int)(10 * Game.SCALE);

	private int healthBarWidth = (int)(150 * Game.SCALE);
	private int healthBarHeight = (int)(4 * Game.SCALE);

	private int healthBarXStart = (int)(34 * Game.SCALE);
	private int healthBarYStart = (int)(14 * Game.SCALE);

	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;

	private int flipX = 0;
	private int flipW = 1;

	//Co so cho y tuong lat anh
	//chi can cho width thanh so am
	// vi du gia su img ban dau co toa do trai tren la (x,y) co do dai width
	// chi can doi width := -width
	// thi no se lat nguoc lai anh giong khi minh soi img vao guong
	// diem (x,y) luc nay se phai thanh toa do Phai Tren cua img
	// con img se bi flip
	// do vay flipX se de kiem soat toa do (x,y) la Trai tren hay Phai Tren
	// con flipW la de dao dau cua width

	private boolean attackChecked;
	private Playing playing;
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimations();
		initHitbox(x, y, (int)(20 * Game.SCALE), (int)(27 * Game.SCALE)); 
		//cast int để không bị lỗi collision liên tục với mặt đất hoặc bờ tường
		initAttackBox();
	}

	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));

	}
	/*
	 * Tư tưởng bây giờ, đó là vị trí (x, y) của nhân vật 
	 * thực ra chính là vị trí ở góc(trái, trên) của hitbox nhân vật
	 * 
	 * Ta làm việc này để tiện thao tác với các biến của hitbox, giúp check collision thuận tiện hơn
	 */
	
	
	public void update() {

		/*if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}*/

		updateHealthBar();
		updateAttackBox();
		updatePos();
		if(attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
	}

	private void checkAttack() {
		if(attackChecked || aniIndex != 1) //ani != 1 tuc la khong trong trang thai tan cong
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
	}

	private void updateAttackBox() {
		// Khi di ve ben nao thi attackbox se o ben do ngay sat player
		if(right) {
			// Khi di ve ben phai thi attackbox cung di theo
			attackBox.x = hitBox.x + hitBox.width + (int)(Game.SCALE * 10);
		}
		else if(left) {
			attackBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 10);
		}
		attackBox.y = hitBox.y + ((int)Game.SCALE * 10);
	}

	private void updateHealthBar() {
		// Luong mau con lai ti le bao nhieu voi thanh mau ban dau
		// de render cho can doi
		healthWidth = (int)((float)healthBarWidth * currentHealth / maxHealth);


	}

	public void render(Graphics g, int xlvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitBox.x - xDrawOffset) - xlvlOffset + flipX, (int)(hitBox.y - yDrawOffset) , width * flipW, height, null);
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
		
		//drawHitbox(g, xlvlOffset);
		/*
		 * Bước 2: vẽ hitbox để debug
		 * (hitbox.x, hitbox.y) ở chỗ nào thì vẽ hitbox debug ở chỗ đó (easy) 
		 */
		//drawAttackBox(g, xlvlOffset);
		drawUI(g);
	}

	private void drawAttackBox(Graphics g, int xlvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - xlvlOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth , healthBarHeight);
	}


	private void updateAnimationTick() {
		++aniTick;
		if (aniTick >= aniNum) {
			aniTick = 0;
			++aniIndex;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
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

		if(inAir) {
			if(airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}
		
		if (attacking) {
			playerAction = ATTACK;
			if(startAni != ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
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
>>>>>>> 4e7797b88e5162979665150eff9244a73a7697b1

    private int powerBarWidth = (int) (104 * Game.SCALE);
    private int powerBarHeight = (int) (2 * Game.SCALE);
    private int powerBarXStart = (int) (44 * Game.SCALE);
    private int powerBarYStart = (int) (34 * Game.SCALE);
    private int powerWidth = powerBarWidth;
    private int powerMaxValue = 200;
    private int powerValue = powerMaxValue;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

<<<<<<< HEAD
    private int tileY = 0;

    private boolean powerAttackActive;
    private int powerAttackTick;
    private int powerGrowSpeed = 15;
    private int powerGrowTick;
=======
		float xSpeed = 0;
		
		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		else if (right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
		}
		if(!inAir) {
			if(!IsEntityOnFloor(hitBox, lvlData)) {
				// Truong hop dang di het duong va roi xuong bac duoi
				inAir = true;
			}
		}
>>>>>>> 4e7797b88e5162979665150eff9244a73a7697b1

    private final PlayerCharacter playerCharacter;
    
    //Khởi tạo đối tượng Player, trạng thái IDLE, ở gamestate playing, máu 100
    public Player(PlayerCharacter playerCharacter, Playing playing) {
        super(0, 0, (int) (playerCharacter.spriteW * Game.SCALE), (int) (playerCharacter.spriteH * Game.SCALE));
        this.playerCharacter = playerCharacter;
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = Game.SCALE * 1.0f;
        animations = LoadSave.loadAnimations(playerCharacter);
        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
        initHitbox(playerCharacter.hitboxW, playerCharacter.hitboxH);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (35 * Game.SCALE), (int) (20 * Game.SCALE));
        resetAttackBox();
    }

    public void update() {
        updateHealthBar();
        updatePowerBar();

        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);

                // Check if player died in air
                if (!IsEntityOnFloor(hitbox, lvlData)) {
                    inAir = true;
                    airSpeed = 0;
                }
            } else if (aniIndex == playerCharacter.getSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else {
                updateAnimationTick();

<<<<<<< HEAD
                // Fall if in air
                if (inAir)
                    if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                        hitbox.y += airSpeed;
                        airSpeed += GRAVITY;
                    } else
                        inAir = false;
=======
	public void changeHealth(int value) {
		currentHealth += value;
		if(currentHealth <= 0) { //Health xuong nho hon 0;
			currentHealth = 0;
			//GameOver

		}else if(currentHealth >= maxHealth) // Health qua luong mau toi da cho phep
			currentHealth = maxHealth;
	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[7][8]; //cac hoat anh la ma tran 9*6 nhu trong anh o resource
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*SUB_WIDTH, j*SUB_HEIGHT, SUB_WIDTH, SUB_HEIGHT);
				/* cai dat: subimage cua image ban dau duoc import vao bien animation[j][i]
					
				Lay subimage, vi tri toa do goc (i*64, j*40)
				Kich co cua subimage trong image la 64*40px
				 */
			}
		}

		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitBox, lvlData))
			inAir = true;
	}
>>>>>>> 4e7797b88e5162979665150eff9244a73a7697b1

            }

            return;
        }

        updateAttackBox();

        if (state == HIT) {
            if (aniIndex <= playerCharacter.getSpriteAmount(state) - 3)
                pushBack(pushBackDir, lvlData, 1.25f);
            updatePushBackDrawOffset();
        } else
            updatePos();

        if (moving) {
            checkPotionTouched();
            checkSpikesTouched();
            checkInsideWater();
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
            if (powerAttackActive) {
                powerAttackTick++;
                if (powerAttackTick >= 35) {
                    powerAttackTick = 0;
                    powerAttackActive = false;
                }
            }
        }

        if (attacking || powerAttackActive)
            checkAttack();

        updateAnimationTick();
        setAnimation();
    }

    private void checkInsideWater() {
        if (IsEntityInWater(hitbox, playing.getLevelManager().getCurrentLevel().getLevelData()))
            currentHealth = 0;
    }

<<<<<<< HEAD
    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;

        if (powerAttackActive)
            attackChecked = false;

        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void setAttackBoxOnRightSide() {
        attackBox.x = hitbox.x + hitbox.width - (int) (Game.SCALE * 5);
    }

    private void setAttackBoxOnLeftSide() {
        attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
    }

    private void updateAttackBox() {
        if (right && left) {
            if (flipW == 1) {
                setAttackBoxOnRightSide();
            } else {
                setAttackBoxOnLeftSide();
            }

        } else if (right || (powerAttackActive && flipW == 1))
            setAttackBoxOnRightSide();
        else if (left || (powerAttackActive && flipW == -1))
            setAttackBoxOnLeftSide();

        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    private void updatePowerBar() {
        powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);
        powerGrowTick++;
        if (powerGrowTick >= powerGrowSpeed) {
            powerGrowTick = 0;
            changePower(1);
        }
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[playerCharacter.getRowIndex(state)][aniIndex], (int) (hitbox.x - playerCharacter.xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - playerCharacter.yDrawOffset + (int) (pushDrawOffset)), width * flipW, height, null);
        drawHitbox(g, lvlOffset);
//		drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        // Background ui
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);

        // Health bar
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

        // Power Bar
        g.setColor(Color.yellow);
        g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= playerCharacter.getSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
                if (state == HIT) {
                    newState(IDLE);
                    airSpeed = 0f;
                    if (!IsFloor(hitbox, 0, lvlData))
                        inAir = true;
                }
            }
        }
    }

    private void setAnimation() {
        int startAni = state;

        if (state == HIT)
            return;

        if (moving)
            state = RUNNING;
        else
            state = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                state = JUMP;
            else
                state = FALLING;
        }

        if (powerAttackActive) {
            state = ATTACK;
            aniIndex = 1;
            aniTick = 0;
            return;
        }

        if (attacking) {
            state = ATTACK;
            if (startAni != ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if (startAni != state)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if (!powerAttackActive)
                if ((!left && !right) || (right && left))
                    return;

        float xSpeed = 0;

        if (left && !right) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right && !left) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (powerAttackActive) {
            if ((!left && !right) || (left && right)) {
                if (flipW == -1)
                    xSpeed = -walkSpeed;
                else
                    xSpeed = walkSpeed;
            }

            xSpeed *= 3;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir && !powerAttackActive) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            hitbox.x += xSpeed;
        else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
            if (powerAttackActive) {
                powerAttackActive = false;
                powerAttackTick = 0;
            }
        }
    }

    public void changeHealth(int value) {
        if (value < 0) {
            if (state == HIT)
                return;
            else
                newState(HIT);
        }

        currentHealth += value;
        currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
    }

    public void changeHealth(int value, Enemy e) {
        if (state == HIT)
            return;
        changeHealth(value);
        pushBackOffsetDir = UP;
        pushDrawOffset = 0;

        if (e.getHitbox().x < hitbox.x)
            pushBackDir = RIGHT;
        else
            pushBackDir = LEFT;
    }

    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int value) {
        powerValue += value;
        powerValue = Math.max(Math.min(powerValue, powerMaxValue), 0);
    }


    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        airSpeed = 0f;
        state = IDLE;
        currentHealth = maxHealth;
        powerAttackActive = false;
        powerAttackTick = 0;
        powerValue = powerMaxValue;

        hitbox.x = x;
        hitbox.y = y;
        resetAttackBox();

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    private void resetAttackBox() {
        if (flipW == 1)
            setAttackBoxOnRightSide();
        else
            setAttackBoxOnLeftSide();
    }

    public int getTileY() {
        return tileY;
    }

    public void powerAttack() {
        if (powerAttackActive)
            return;
        if (powerValue >= 60) {
            powerAttackActive = true;
            changePower(-60);
        }

    }

}
=======
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;
		hitBox.x = x;
		hitBox.y = y;

		if(!IsEntityOnFloor(hitBox, lvlData))
			inAir = true;
	}
}
>>>>>>> 4e7797b88e5162979665150eff9244a73a7697b1
