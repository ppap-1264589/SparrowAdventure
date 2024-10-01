/*
Khai báo package chứa lớp này.
Import lớp Graphics để vẽ lên màn hình.
Import lớp BufferedImage để xử lý hình ảnh.
Import lớp LoadSave để tải và lưu tài nguyên.
Import các hằng số liên quan đến nút Pause từ lớp Constants.
*/ 
package ui;

import java.awt.Graphics; 
import java.awt.image.BufferedImage; 

import utilz.LoadSave; 
import static utilz.Constants.UI.PauseButtons.*; 
public class SoundButton extends PauseButton { // Lớp SoundButton kế thừa từ lớp PauseButton.

	private BufferedImage[][] soundImgs; 
	// Mảng 2D để lưu các hình ảnh nút âm thanh.
	private boolean mouseOver, mousePressed; 
	// Các biến để kiểm tra trạng thái di chuột và nhấn chuột.
	private boolean muted;
	// Trạng thái bật/tắt âm thanh.
	private int rowIndex, colIndex; 
	// Chỉ số hàng và cột trong mảng hình ảnh được sử dụng.

	public SoundButton(int x, int y, int width, int height) { 
		// Hàm khởi tạo nút âm thanh với vị trí và kích thước.
		super(x, y, width, height); 
		// Gọi hàm khởi tạo của lớp cha PauseButton.

		loadSoundImgs(); 
		// Tải hình ảnh của các trạng thái nút âm thanh.
	}

	private void loadSoundImgs() { 
		// Phương thức tải các hình ảnh của nút âm thanh.
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS); 
		// Tải hình ảnh từ tập tin sprite.
		soundImgs = new BufferedImage[2][3]; 
		// Khởi tạo mảng 2x3 để chứa các trạng thái hình ảnh (bật/tắt, bình thường, hover, nhấn).
		for (int j = 0; j < soundImgs.length; j++) // Vòng lặp để duyệt qua từng hàng (trạng thái bật/tắt).
			for (int i = 0; i < soundImgs[j].length; i++) // Vòng lặp để duyệt qua từng cột (trạng thái bình thường, hover, nhấn).
				soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
				// Cắt hình ảnh từ sprite theo từng trạng thái (bật/tắt, bình thường, hover, nhấn).
	}

	public void update() { // Cập nhật trạng thái nút âm thanh.
		if (muted) // Nếu âm thanh bị tắt,
			rowIndex = 1; // Chọn hàng thứ 2 trong mảng hình ảnh (hàng hình ảnh nút tắt tiếng).
		else
			rowIndex = 0; // Ngược lại, chọn hàng thứ 1 (hình ảnh nút có tiếng).

		colIndex = 0; // Mặc định, cột là trạng thái bình thường.
		if (mouseOver) // Nếu chuột đang di chuyển qua nút,
			colIndex = 1; // Chọn cột trạng thái hover.
		if (mousePressed) // Nếu chuột đang nhấn vào nút,
			colIndex = 2; // Chọn cột trạng thái nhấn.
	}

	public void resetBools() { 
		// Đặt lại các giá trị biến điều khiển chuột sau khi nhả chuột.
		mouseOver = false;
		mousePressed = false;
	}

	public void draw(Graphics g) {
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null); 
		// Vẽ hình ảnh tương ứng với trạng thái hiện tại của nút.
	}

	public boolean isMouseOver() { 
		return mouseOver;
		// Trả về true nếu chuột đang di chuyển qua nút.
	}

	public void setMouseOver(boolean mouseOver) { 
		this.mouseOver = mouseOver;
		// Đặt trạng thái chuột di chuyển qua nút nhưng không nhấn (đó là trạng thái hover như cập nhật ở những phần trước.)
	}

	public boolean isMousePressed() { 
		return mousePressed; 
		// Trả về true nếu nút đang bị nhấn.
	}

	public void setMousePressed(boolean mousePressed) { 
		this.mousePressed = mousePressed; 
		// Đặt trạng thái chuột nhấn vào nút.
	}

	public boolean isMuted() { 
		return muted; // Trả về true nếu âm thanh đang bị tắt.
	}

	public void setMuted(boolean muted) { 
		this.muted = muted; // Đặt trạng thái tắt/bật âm thanh.
	}

}
