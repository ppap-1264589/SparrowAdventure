package objects;

import java.awt.geom.Rectangle2D;
import main.Game;

// Sử dụng hằng số từ lớp Constants.Projectiles để lấy giá trị như kích thước và tốc độ
import static utilz.Constants.Projectiles.*;

// Lớp Projectile đại diện cho đối tượng đạn hoặc các vật thể bắn được trong game
public class Projectile {
	
	// hitbox: Hình chữ nhật đại diện cho vùng va chạm của viên đạn
	private Rectangle2D.Float hitbox;
	// dir: Hướng di chuyển của đạn (1: phải, -1: trái)
	private int dir;
	// active: Trạng thái của viên đạn (true: đang tồn tại, false: đã bị vô hiệu)
	private boolean active = true;

	// Constructor nhận vào tọa độ (x, y) và hướng di chuyển của viên đạn
	public Projectile(int x, int y, int dir) {
		// xOffset và yOffset điều chỉnh vị trí hitbox so với vị trí của súng
		int xOffset = (int) (-3 * Game.SCALE); // Mặc định cho đạn di chuyển sang trái
		int yOffset = (int) (5 * Game.SCALE); // Điều chỉnh độ cao để căn chỉnh với hình ảnh đạn

		// Nếu đạn di chuyển sang phải, thay đổi xOffset
		if (dir == 1)
			xOffset = (int) (29 * Game.SCALE);

		// Khởi tạo hitbox cho viên đạn, bao gồm vị trí x, y và kích thước (CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT)
		hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
		
		// Gán hướng di chuyển cho viên đạn
		this.dir = dir;
	}

	// Phương thức updatePos() cập nhật vị trí của viên đạn theo hướng di chuyển và tốc độ
	public void updatePos() {
		hitbox.x += dir * SPEED; // Thay đổi vị trí theo trục x dựa vào hướng (dir) và tốc độ (SPEED)
	}

	// Phương thức setPos() để cập nhật trực tiếp vị trí của viên đạn
	public void setPos(int x, int y) {
		hitbox.x = x;
		hitbox.y = y;
	}

	// Getter để lấy hitbox của viên đạn
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	// Phương thức setActive() để thay đổi trạng thái hoạt động của viên đạn
	public void setActive(boolean active) {
		this.active = active;
	}

	// Kiểm tra trạng thái hoạt động của viên đạn (true: đang hoạt động, false: không còn tồn tại)
	public boolean isActive() {
		return active;
	}
}
