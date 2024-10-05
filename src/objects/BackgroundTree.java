
package objects;

import java.util.Random;

public class BackgroundTree {

	// Các biến đại diện cho tọa độ, loại cây, chỉ số hoạt ảnh và chỉ số đếm tick
	private int x, y, type, aniIndex, aniTick;

	// Constructor để khởi tạo đối tượng BackgroundTree với tọa độ (x, y) và loại cây
	public BackgroundTree(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;	

		// Khởi tạo chỉ số hoạt ảnh (aniIndex) với giá trị ngẫu nhiên từ 0 đến 3, để có sự khác biệt giữa các cây và tránh chúng chuyển động đồng bộ.
		Random r = new Random();
		aniIndex = r.nextInt(4); 
		// Tạo ngẫu nhiên một giá trị từ 0 đến 3
	}

	// Phương thức cập nhật chỉ số hoạt ảnh
	public void update() {
		aniTick++; 
		// Tăng chỉ số đếm tick
		// Khi aniTick đạt tới 35, nó sẽ được đặt lại và chuyển sang khung hình hoạt ảnh tiếp theo
		if (aniTick >= 35) {
			aniTick = 0; // Đặt lại chỉ số đếm tick
			aniIndex++; // Chuyển sang khung hoạt ảnh kế tiếp
			// Nếu aniIndex vượt quá 3 (tức là hết khung hoạt ảnh), nó sẽ được đặt lại về 0
			if (aniIndex >= 4)
				aniIndex = 0;
		}
	}

	/*
 Getter: Phương thức này được sử dụng để lấy giá trị của một biến thành viên trong lớp. Nó cho phép truy cập giá trị của biến một cách an toàn mà không cần truy cập trực tiếp.
         Ví dụ: public int getX() { return x; }
       Mục đích: Lấy giá trị biến x.

Setter: Phương thức này được sử dụng để thiết lập giá trị cho một biến thành viên. Nó cho phép kiểm soát và giới hạn giá trị được gán cho biến thông qua logic kiểm tra trước khi thay đổi.
Ví dụ: public void setX(int x) { if (x >= 0) this.x = x; }
Mục đích: Đặt giá trị biến x và kiểm tra tính hợp lệ của giá trị trước khi gán.
	*/
	
	// Phương thức getter cho chỉ số hoạt ảnh
	public int getAniIndex() {
		return aniIndex;
	}

	// Phương thức setter để thay đổi chỉ số hoạt ảnh
	public void setAniIndex(int aniIndex) {
		this.aniIndex = aniIndex;
	}

	// Phương thức getter cho tọa độ x
	public int getX() {
		return x;
	}

	// Phương thức setter để thay đổi tọa độ x
	public void setX(int x) {
		this.x = x;
	}

	// Phương thức getter cho tọa độ y
	public int getY() {
		return y;
	}

	// Phương thức setter để thay đổi tọa độ y
	public void setY(int y) {
		this.y = y;
	}

	// Phương thức getter cho loại cây
	public int getType() {
		return type;
	}

	// Phương thức setter để thay đổi loại cây
	public void setType(int type) {
		this.type = type;
	}
}
