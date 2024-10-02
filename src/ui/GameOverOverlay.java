package ui;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {

    private Playing playing;     // Biến lưu trạng thái trò chơi hiện tại

    
    // hình ảnh hiển thị khi trò chơi kết thúc
    private BufferedImage img;
    
    // các biến để xác định vị trí và kích thước của hình ảnh kết thúc
    private int imgX, imgY, imgW, imgH;
    
    // các nút trên giao diện Game Over: menu và chơi lại
    private UrmButton menu, play;

    // hàm khởi tạo lớp, nhận đối tượng Playing để lấy thông tin về trạng thái trò chơi
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImg();     // Tạo hình ảnh Game Over
        createButtons(); // Tạo các nút cho giao diện Game Over
    }

    // phương thức tạo các nút "menu" và "play"
    private void createButtons() {
        // tính toán vị trí của các nút dựa trên tỉ lệ màn hình trò chơi
        int menuX = (int) (335 * Game.SCALE);
        int playX = (int) (440 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        
        // khởi tạo các nút "menu" và "play" với vị trí và kích thước
        play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    // Phương thức tạo hình ảnh cho màn hình kết thúc
    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN); // Tải hình ảnh từ tài nguyên
        imgW = (int) (img.getWidth() * Game.SCALE); // Tính toán chiều rộng của hình ảnh
        imgH = (int) (img.getHeight() * Game.SCALE); // Tính toán chiều cao của hình ảnh
        imgX = Game.GAME_WIDTH / 2 - imgW / 2; // Căn giữa hình ảnh theo chiều ngang
        imgY = (int) (100 * Game.SCALE); // Đặt vị trí y của hình ảnh
    }

    // Phương thức vẽ màn hình Game Over lên giao diện
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // Vẽ nền tối mờ
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT); // Tô toàn bộ màn hình bằng màu đen mờ

        g.drawImage(img, imgX, imgY, imgW, imgH, null); // Vẽ hình ảnh Game Over

        // Vẽ các nút "menu" và "play" lên màn hình
        menu.draw(g);
        play.draw(g);
    }

    // Phương thức cập nhật trạng thái của các nút
    public void update() {
        menu.update();
        play.update();
    }

    // phương thức kiểm tra xem chuột có nằm trong vùng của một nút không
    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY()); // Kiểm tra nếu tọa độ chuột nằm trong vùng của nút
    }

    // phương thức xử lý sự kiện khi chuột di chuyển
    public void mouseMoved(MouseEvent e) {
        // ban đầu đặt trạng thái "không nằm trong" cho các nút
        play.setMouseOver(false);
        menu.setMouseOver(false);

        // kiểm tra nếu chuột di chuyển qua các nút
        if (isIn(menu, e))
            menu.setMouseOver(true); // Nếu di chuyển chuột qua nút menu
        else if (isIn(play, e))
            play.setMouseOver(true); // Nếu di chuyển chuột qua nút play
    }

    // phương thức xử lý sự kiện khi người dùng thả chuột
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) { // kiểm tra nếu chuột nhả ra trên nút menu
            if (menu.isMousePressed()) {
                playing.resetAll(); // reset toàn bộ trạng thái chơi
                playing.setGamestate(Gamestate.MENU); // đưa người chơi về menu chính
            }
        } else if (isIn(play, e)) { // kiểm tra nếu chuột nhả ra trên nút play
            if (play.isMousePressed()) {
                playing.resetAll(); // reset toàn bộ trạng thái chơi
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex()); // phát lại nhạc tương ứng với cấp độ hiện tại
            }
        }

        // reset trạng thái các nút sau khi sự kiện kết thúc
        menu.resetBools();
        play.resetBools();
    }

    // phương thức xử lý sự kiện khi người dùng nhấn chuột
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true); // Nếu nhấn vào nút menu
        else if (isIn(play, e))
            play.setMousePressed(true); // Nếu nhấn vào nút play
    }

}
