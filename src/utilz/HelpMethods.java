package utilz;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HelpMethods {
	public static boolean CanMoveHere(float x, float y, float width, float height, int lvlData[][]) { 
		/*Bắt đầu check xem:
		 nhân vật có vị trí (x, y) với độ lớn (của hitbox) là hình chữ nhật w*h, có thể di chuyển
		 đến một vị trí đã có trong lvlData[][] hay không?
		*/
		
		if (!IsSolid(x, y, lvlData)) {
			if (!IsSolid(x + width, y + height, lvlData)) {
				if (!IsSolid(x + width, y, lvlData)) {
					if (!IsSolid(x, y + height, lvlData)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x >= Game.GAME_WIDTH) return true;
		if (y < 0 || y >= Game.GAME_HEIGHT) return true;
		
		float xIndex = x/Game.TILES_SIZE;
		float yIndex = y/Game.TILES_SIZE;
		//in fact: mỗi tọa độ (x, y) của mình, đều tương đương với một cái tile nào đấy trong game
		
		int value = lvlData[(int)yIndex][(int)xIndex];
		/*
		 * Quy ước:
		 * Gốc tọa độ O của window nằm ở góc trên cùng bên trái màn hình
		 * tia Ox đi trái sang phải
		 * tia Oy đi từ TRÊN xuống DƯỚI
		 * thành ra, yIndex là chỉ số hàng, xIndex là chỉ số cột của ma trận lvlData[][]
		 */
		
		if (value >= 48 || value < 0 || value != 11)  return true;
		/*
		 (value >= 48 || value < 0) -> nếu mình di chuyển ra ngoài biên, thì solid -> không đi đâu được nữa
		 value != 11 -> nếu mình không đi vào ô không gian trống -> chạm vào vật thể -> solid
		 */
		
		return false;
	}

	public static float GetEntityXPosNExtToWall(Rectangle2D.Float hitbox, float xSpeed) {
		// Ham nay se tra ve vi tri cua hitbox.x ke canh voi wall nhat co the

		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		// cot ma nhan vat dang dung

		if(xSpeed > 0) {
			// RIGHT
			// Nhan vat dang di ve ben phai


			int tileXPos = currentTile * Game.TILES_SIZE; // Vi tri cua nhan vat theo x cua tile hien tai
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			// Khoang cach giua canh ben trai cua hitbox voi canh ben trai cua currtenTile
			// khi hitbox da sat vao canh ben phai cua currentTile;
			//  |        |
			//  |_____|  |
			//  | kc  |  |
			return tileXPos + xOffset - 1;
			// Tru 1 de hitbox khong overlap voi wall
			// tuc la luon ton tai khoang cach dx = 1 pixel giua bien hitbox voi wall
		}else {
			// LEFT
			// Nhan vat dang di ve ben trai
			return currentTile * Game.TILES_SIZE;
		}

		//xSpeed != 0 vi neu xSpeed == 0 se khong co collision
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		//Ham nay tuong tu ham ben tren nhung doi voi y

		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);// hang ma nhan vat dang dung

		if(airSpeed > 0) { // FALLING - TOUCHING FLOOR
			int tileYPos = currentTile * Game.TILES_SIZE; // Vi tri cua nhan vat theo x cua tile hien tai
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return  tileYPos + yOffset - 1;
		}else { // JUMPING
			return currentTile * Game.TILES_SIZE;
		}
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvldata) {
		// Check the pixel below bottom-left and bottom-right
		if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvldata)) {// Kiem tra diem Duoi Trai cua hitbox
			if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvldata)) {// Kiem tra Duoi phai
				return false;
			}
		}
		return true;
	}
}
