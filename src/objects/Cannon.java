package objects;

import main.Game;

// Lớp Cannon đại diện cho đối tượng súng thần công trong game, kế thừa từ GameObject
public class Cannon extends GameObject {

	// tileY: Chứa giá trị tọa độ Y trên lưới (grid) của game
	private int tileY;

	// Constructor của Cannon nhận tham số vị trí x, y và loại đối tượng objType
	public Cannon(int x, int y, int objType) {
		// Gọi constructor của lớp cha (GameObject) để khởi tạo vị trí và loại đối tượng
		super(x, y, objType);

		// Tính toán tileY bằng cách chia tọa độ y của đối tượng cho kích thước một ô lưới (TILES_SIZE)
		tileY = y / Game.TILES_SIZE;

		// Khởi tạo hitbox cho súng thần công với kích thước 40x26 pixels
		initHitbox(40, 26);

		// Điều chỉnh vị trí của hitbox theo trục y (lùi xuống một chút để căn chỉnh với hình ảnh)
		hitbox.y += (int) (6 * Game.SCALE);
	      // hitbox.x -= (int) (1 * Game.SCALE); nó sẽ điều chỉnh hitbox theo trục x nếu được mở lại.
	}

	// Phương thức update() được gọi trong vòng lặp game để cập nhật trạng thái của đối tượng
	public void update() {
		// Nếu đối tượng có hoạt ảnh, cập nhật tick để tạo hoạt ảnh
		if (doAnimation)
			updateAnimationTick();
	}

	// Getter để lấy giá trị tileY (tọa độ Y trên lưới) của đối tượng
	public int getTileY() {
		return tileY;
	}
}
