package objects;

public class Grass {

	// Các biến đại diện cho tọa độ x, y và loại cỏ (type)
	private int x, y, type;

	// Constructor để khởi tạo đối tượng Grass với tọa độ (x, y) và loại cỏ (type)
	public Grass(int x, int y, int type) {
		this.x = x;   // Gán giá trị x
		this.y = y;   // Gán giá trị y
		this.type = type; // Gán giá trị type (loại cỏ)
	}
      /*
	Getter: Phương thức này được sử dụng để lấy giá trị của một biến thành viên trong lớp. Nó cho phép truy cập giá trị của biến một cách an toàn mà không cần truy cập trực tiếp.
         Ví dụ: public int getX() { return x; }
       Mục đích: Lấy giá trị biến x.

Setter: Phương thức này được sử dụng để thiết lập giá trị cho một biến thành viên. Nó cho phép kiểm soát và giới hạn giá trị được gán cho biến thông qua logic kiểm tra trước khi thay đổi.
Ví dụ: public void setX(int x) { if (x >= 0) this.x = x; }
Mục đích: Đặt giá trị biến x và kiểm tra tính hợp lệ của giá trị trước khi gán.
	*/
	// Phương thức getter để lấy giá trị tọa độ x của cỏ
	public int getX() {
		return x;
	}

	// Phương thức getter để lấy giá trị tọa độ y của cỏ
	public int getY() {
		return y;
	}

	// Phương thức getter để lấy loại cỏ
	public int getType() {
		return type;
	}
}
