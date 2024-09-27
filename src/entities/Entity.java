package entities;

public abstract class Entity {
	protected float x, y;
	protected int width, height;
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
}
/*
abstract class để làm gì ??
để sau này một số lớp khác như Player, Enemy,... sẽ được kế thừa
từ lớp này ra với tư cách là một dàn ý bắt buộc 
 */
