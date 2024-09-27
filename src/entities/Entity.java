package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitBox; //Rectangle là một class đặc biệt để tạo hitbox cho nhân vật trong game
	/*
	 protected: hai biến x, y này sẽ được sử dụng ở trong 
	 cả những lớp kế thừa từ lớp này
	 */
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void drawHitbox(Graphics g) {
		//Just for debugging the hitbox
		g.setColor(Color.RED);
		g.drawRect((int)hitBox.x, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);
	}
	
	protected void initHitbox(float x, float y, float width, float height) {
		hitBox = new Rectangle2D.Float(x, y, width, height);
	}
	
//	protected void updateHitbox() { 
//		hitBox.x = (int)x;
//		hitBox.y = (int)y;
//	}
	
	/*
	 update tọa độ hitbox gốc của nhân vật
	 protected vì chúng ta cần mở rộng hàm này trong các lớp kết thừa của các lớp hiện tại
	 */
	
	public Rectangle2D.Float getHitbox() {
		return hitBox;
	}
}
/*
abstract class để làm gì ??
để sau này một số lớp khác như Player, Enemy,... sẽ được kế thừa
từ lớp này ra với tư cách là một dàn ý bắt buộc 
 */
