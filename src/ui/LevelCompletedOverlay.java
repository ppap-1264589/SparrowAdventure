package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;
/*
  private Playing playing:  Biến lưu trạng thái trò chơi hiện tại
 private UrmButton menu, next:    Các nút cho giao diện khi hoàn thành màn chơi
 private BufferedImage img:   Hình ảnh hiển thị khi hoàn thành màn chơi
 private int bgX, bgY, bgW, bgH: Các biến để xác định vị trí và kích thước của hình ảnh nền

*/
public class LevelCompletedOverlay {

    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;

    
// hàm khởi tạo, nhận đối tượng Playing để lấy thông tin về trạng thái trò chơi
    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();      // khởi tạo hình ảnh hoàn thành màn chơi
        initButtons();  // khởi tạo các nút "menu" và "next"
    }

    // Phương thức khởi tạo các nút trên giao diện
    private void initButtons() {
        // tính vị trí của các nút dựa trên tỉ lệ màn hình gmae
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);

        // khởi tạo nút "next" và "menu" với kích thước và vị trí phù hợp
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    // Phương thức khởi tạo hình ảnh hiển thị khi hoàn thành màn chơi
    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG); // Tải hình ảnh từ tài nguyên
        bgW = (int) (img.getWidth() * Game.SCALE); // Tính toán chiều rộng của hình ảnh
        bgH = (int) (img.getHeight() * Game.SCALE); // Tính toán chiều cao của hình ảnh
        bgX = Game.GAME_WIDTH / 2 - bgW / 2; // Căn giữa hình ảnh theo chiều ngang
        bgY = (int) (75 * Game.SCALE); // Đặt vị trí y của hình ảnh
    }

    // Phương thức vẽ màn hình khi hoàn thành màn chơi lên giao diện
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // Vẽ nền tối mờ
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT); // Tô toàn bộ màn hình bằng màu đen mờ

        g.drawImage(img, bgX, bgY, bgW, bgH, null); // Vẽ hình ảnh hoàn thành màn chơi

        // Vẽ các nút "next" và "menu" lên màn hình
        next.draw(g);
        menu.draw(g);
    }

    // Phương thức cập nhật trạng thái của các nút
    public void update() {
        next.update();
        menu.update();
    }

    // Phương thức kiểm tra xem chuột có nằm trong vùng của một nút không
    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY()); // kiểm tra nếu tọa độ chuột nằm trong vùng của nút
    }

    // Phương thức xử lý sự kiện khi chuột di chuyển
    public void mouseMoved(MouseEvent e) {
        // ban đầu đặt trạng thái "không nằm trong" cho các nút, tức là  khi bắt đầu xử lý sự kiện di chuyển chuột, hệ thống sẽ mặc định coi như chuột không nằm trên bất kỳ nút nào.
        next.setMouseOver(false);
        menu.setMouseOver(false);

        // kiểm tra nếu chuột di chuyển qua các nút
        if (isIn(menu, e))
            menu.setMouseOver(true); // nếu di chuyển chuột qua nút menu
        else if (isIn(next, e))
            next.setMouseOver(true); // nếu di chuyển chuột qua nút next
    }

    // phương thức xử lý sự kiện khi người dùng thả chuột
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) { // kiểm tra nếu chuột nhả ra trên nút menu
            if (menu.isMousePressed()) {
                playing.resetAll(); //reset toàn bộ trạng thái chơi
                playing.setGamestate(Gamestate.MENU); // đưa người chơi về menu chính
            }
        } else if (isIn(next, e)) { // kiểm tra nếu chuột nhả ra trên nút next
            if (next.isMousePressed()) {
                playing.loadNextLevel(); // tải màn chơi tiếp theo
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex()); // phát nhạc tương ứng với màn chơi tiếp theo
            }
        }

        // reset trạng thái các nút sau khi sự kiện kết thúc
        menu.resetBools();
        next.resetBools();
    }

    // Phương thức xử lý sự kiện khi người dùng nhấn chuột
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true); // Nếu nhấn vào nút menu
        else if (isIn(next, e))
            next.setMousePressed(true); // Nếu nhấn vào nút next
    }

}
