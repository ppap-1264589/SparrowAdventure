package utilz;

import main.Game;

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
}
