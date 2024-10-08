package utilz;

import main.Game;

import java.security.PublicKey;

public class Constants {

	public static class EnemyConstants {
		public static final int CRABBY = 0;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		public static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * Game.SCALE);

		public static final int CRABBY_DRAWOFFSET_X = (int)(26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int)(9 * Game.SCALE);
		// khoang cach giua sprite va hitbox theo x, y;

		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch (enemyType) {
				case CRABBY:
					switch (enemyState) {
						case IDLE:
							return 9;
						case RUNNING:
							return 6;
						case ATTACK:
							return 7;
						case  HIT:
							return 4;
						case DEAD:
							return 5;
					}
			}
			return 0;
		}
	}

	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int BIG_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);


		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
		public static final int SMALL_CLOUD_WIDTH = (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int)(SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);

	}

	public static class Directions{ //cac huong di chuyen cua nhan vat
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
		
	}
	public static class PlayerConstants{ //Cac trang thai cua nhan vat
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int GROUND = 4;
		public static final int HIT = 5;
		public static final int ATTACK_1 = 6;
		public static final int ATTACK_JUMP_1 = 7;
		public static final int ATTACK_JUMP_2 = 8;
		
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) { //Cho biet co bao nhieu animation trong player_action
				case RUNNING:
					return 6;
				case IDLE:
					return 5;
				case HIT:
					return 4;
				case JUMP: 
				case ATTACK_1: 
				case ATTACK_JUMP_1: 
				case ATTACK_JUMP_2:
					return 3;
				case GROUND:
					return 2;
				case FALLING:
				default:
					return 1;
			}
		}
	}
}
