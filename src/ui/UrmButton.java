package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

/*
  Lớp UrmButton đại diện cho nút URM (Up, Reset, Menu) trong giao diện tạm dừng.
  Nó thừa kế từ lớp PauseButton và có các chức năng liên quan đến trạng thái của nút (chuột di vào, chuột nhấn).
 */
public class UrmButton extends PauseButton {
    private BufferedImage[] imgs; 
	// Mảng chứa các hình ảnh của nút ở các trạng thái khác nhau
    private int rowIndex, index;  
	// rowIndex: Chỉ mục hàng của nút trong sprite sheet, index: chỉ mục trạng thái của nút
    private boolean mouseOver, mousePressed;  
	// Trạng thái khi chuột di vào và nhấn nút

    /**
     * Hàm khởi tạo UrmButton với vị trí và kích thước cụ thể.
     *   x Tọa độ x của nút.
     *   y Tọa độ y của nút.
     *   width Chiều rộng của nút.
     *   height Chiều cao của nút.
     *   rowIndex Chỉ mục hàng của nút trong sprite sheet.
     */
    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);  
	    // Gọi constructor của lớp cha PauseButton
        this.rowIndex = rowIndex;   
	    // Gán chỉ mục hàng của sprite sheet
        loadImgs(); 
	    // Tải hình ảnh cho nút
    }

    /**
     * Phương thức tải hình ảnh cho nút từ sprite sheet.
     * Sử dụng hằng số URM_DEFAULT_SIZE để cắt hình ảnh tương ứng với từng trạng thái.
     */
    private void loadImgs() {
        // Lấy sprite sheet của URM từ lớp LoadSave
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];  // Có 3 trạng thái cho nút
        for (int i = 0; i < imgs.length; i++) 
            // Cắt từng hình ảnh cho các trạng thái khác nhau từ sprite sheet
            imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
    }

    /**
     * Phương thức cập nhật trạng thái của nút dựa trên tương tác của người dùng.
     * Trạng thái 0: Mặc định, 1: Chuột di vào, 2: Chuột nhấn.
     */
    public void update() {
        index = 0;  // Mặc định là trạng thái bình thường
        if (mouseOver)
            index = 1;  // Nếu chuột di vào, chuyển sang trạng thái 1
        if (mousePressed)
            index = 2;  // Nếu chuột nhấn, chuyển sang trạng thái 2
    }

    /**
     * Phương thức vẽ nút URM lên màn hình với trạng thái hiện tại.
     *   g: Đối tượng Graphics để vẽ hình ảnh.
     */
    public void draw(Graphics g) {
        // Vẽ hình ảnh của nút tại vị trí (x, y) với kích thước URM_SIZE.
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    /**
     * Đặt lại trạng thái chuột (chuột di vào và nhấn nút) về mặc định.
     */
    public void resetBools() {
        mouseOver = false;  // Chuột không còn di vào
        mousePressed = false;  // Chuột không còn nhấn nút
    }

    // Các phương thức getter và setter cho trạng thái chuột

    /**
      Kiểm tra xem chuột có đang di vào nút hay không.
      return true nếu chuột đang di vào, ngược lại false.
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Thiết lập trạng thái chuột di vào nút.
     *   mouseOver Trạng thái chuột di vào.
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Kiểm tra xem nút có đang được nhấn hay không.
     * return true nếu nút đang được nhấn, ngược lại false.
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Thiết lập trạng thái chuột nhấn nút.
     *   mousePressed Trạng thái chuột nhấn nút.
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
