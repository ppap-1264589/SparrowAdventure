package objects;

import main.Game;

public class Spike extends GameObject { 
	// Lớp Spike kế thừa từ lớp GameObject, đại diện cho các đối tượng gai trong game, nếu người chơi chạm vào gai sẽ tử trận luôn.

    //  khởi tạo một đối tượng Spike (constructor)
    public Spike(int x, int y, int objType) {
        // gọi constructor của lớp cha GameObject với các tham số: tọa độ x, y và loại đối tượng (super trong Java được sử dụng để tham chiếu đến lớp cha (superclass) của một lớp con (subclass).)
        super(x, y, objType);
        
        // gọi phương thức để khởi tạo hitbox cho đối tượng Spike với kích thước 32x16
        initHitbox(32, 16);
        
        // đặt giá trị xDrawOffset là 0 (không cần dịch chuyển theo trục x khi vẽ)
        xDrawOffset = 0;
        
        // tính toán yDrawOffset dựa trên tỉ lệ của game (Game.SCALE), dịch chuyển theo trục y một đoạn 16 pixel
        yDrawOffset = (int)(Game.SCALE * 16);
        
        // điều chỉnh vị trí y của hitbox, dịch chuyển xuống theo giá trị yDrawOffset
        hitbox.y += yDrawOffset;
    }
}
