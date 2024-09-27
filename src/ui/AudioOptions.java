package ui;

import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utilz.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;

public class AudioOptions {

    // Các nút điều chỉnh âm lượng và âm thanh (nhạc và hiệu ứng âm thanh)
    private VolumeButton volumeButton;
    private SoundButton musicButton, sfxButton;

    // Tham chiếu đến lớp Game, để thay đổi âm thanh trong trò chơi
    private Game game;

    // Constructor nhận đối tượng Game để tạo các nút điều khiển âm thanh
    public AudioOptions(Game game) {
        this.game = game;
        createSoundButtons();  // Tạo các nút nhạc và hiệu ứng âm thanh
        createVolumeButton();  // Tạo thanh điều chỉnh âm lượng
    }

    /* 
     * Tạo thanh điều chỉnh âm lượng (volume slider) 
     * vX và vY là vị trí của thanh trên màn hình, được tính toán theo tỉ lệ Game.SCALE
     */
    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);  /* Vị trí X của thanh điều chỉnh âm lượng */
        int vY = (int) (278 * Game.SCALE);  /* Vị trí Y của thanh điều chỉnh âm lượng */
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT); /* Tạo đối tượng VolumeButton với kích thước cố định */
    }

    // Tạo các nút bật/tắt nhạc và hiệu ứng âm thanh
    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);  // Vị trí X của các nút âm thanh
        int musicY = (int) (140 * Game.SCALE);  // Vị trí Y của nút nhạc
        int sfxY = (int) (186 * Game.SCALE);    // Vị trí Y của nút hiệu ứng âm thanh (SFX)
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);  // Tạo nút nhạc
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);      // Tạo nút hiệu ứng âm thanh
    }

    // Cập nhật trạng thái của các nút điều khiển âm thanh và thanh điều chỉnh âm lượng
    public void update() {
        musicButton.update();  // Cập nhật nút nhạc
        sfxButton.update();    // Cập nhật nút hiệu ứng âm thanh
        volumeButton.update(); // Cập nhật thanh điều chỉnh âm lượng
    }

    // Vẽ (hiển thị) các nút âm thanh và thanh điều chỉnh âm lượng lên màn hình
    public void draw(Graphics g) {
        musicButton.draw(g);  // Vẽ nút nhạc
        sfxButton.draw(g);    // Vẽ nút hiệu ứng âm thanh
        volumeButton.draw(g); // Vẽ thanh điều chỉnh âm lượng
    }

    // Xử lý sự kiện khi kéo thanh điều chỉnh âm lượng
    public void mouseDragged(MouseEvent e) {
        // Nếu thanh âm lượng đang bị nhấn
        if (volumeButton.isMousePressed()) {
            float valueBefore = volumeButton.getFloatValue(); // Lấy giá trị âm lượng trước khi kéo
            volumeButton.changeX(e.getX());  // Thay đổi vị trí nút dựa trên vị trí chuột
            float valueAfter = volumeButton.getFloatValue();  // Lấy giá trị âm lượng sau khi kéo
            // Nếu giá trị âm lượng thay đổi, cập nhật âm lượng trong trò chơi
            if (valueBefore != valueAfter)
                game.getAudioPlayer().setVolume(valueAfter); // Đặt âm lượng mới
        }
    }

    // Xử lý sự kiện khi nhấn chuột vào các nút âm thanh và thanh âm lượng
    public void mousePressed(MouseEvent e) {
        // Kiểm tra xem chuột có nhấn vào nút nhạc không
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);  // Đánh dấu nút nhạc bị nhấn
        // Kiểm tra xem chuột có nhấn vào nút hiệu ứng âm thanh không
        else if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);  // Đánh dấu nút hiệu ứng âm thanh bị nhấn
        // Kiểm tra xem chuột có nhấn vào thanh âm lượng không
        else if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true); // Đánh dấu thanh âm lượng bị nhấn
    }

    // Xử lý sự kiện khi chuột được thả ra
    public void mouseReleased(MouseEvent e) {
        // Nếu chuột thả ra trên nút nhạc
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {  // Kiểm tra nếu nút nhạc bị nhấn
                musicButton.setMuted(!musicButton.isMuted());  // Bật/tắt âm nhạc
                game.getAudioPlayer().toggleSongMute();        // Cập nhật trạng thái âm nhạc trong game
            }
        } 
        // Nếu chuột thả ra trên nút hiệu ứng âm thanh
        else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {  // Kiểm tra nếu nút hiệu ứng âm thanh bị nhấn
                sfxButton.setMuted(!sfxButton.isMuted());  // Bật/tắt hiệu ứng âm thanh
                game.getAudioPlayer().toggleEffectMute();  // Cập nhật trạng thái hiệu ứng âm thanh trong game
            }
        }

        // Reset lại trạng thái nút nhạc và nút hiệu ứng âm thanh sau khi nhấn
        musicButton.resetBools();
        sfxButton.resetBools();
        volumeButton.resetBools();  // Reset lại trạng thái thanh âm lượng
    }

    // Xử lý sự kiện khi di chuyển chuột
    public void mouseMoved(MouseEvent e) {
        // Mặc định không có nút nào bị hover (chuột di qua)
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        // Kiểm tra xem chuột có di qua nút nhạc không
        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);  // Nếu có, đánh dấu là hover
        // Kiểm tra xem chuột có di qua nút hiệu ứng âm thanh không
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);  // Nếu có, đánh dấu là hover
        // Kiểm tra xem chuột có di qua thanh âm lượng không
        else if (isIn(e, volumeButton))
            volumeButton.setMouseOver(true);  // Nếu có, đánh dấu là hover
    }

    // Kiểm tra xem chuột có nằm trong phạm vi của nút hoặc thanh điều chỉnh không
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());  // Kiểm tra vị trí chuột
    }

}
