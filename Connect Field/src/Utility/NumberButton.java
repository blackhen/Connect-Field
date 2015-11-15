package Utility;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class NumberButton {
	
	private Button base;
	private SpriteSheet numbers;
	private Image part1;
	private Image part2;
	private Image lockImg;
	private int[] parts;
	private boolean locked;
	
	public NumberButton(int num, boolean lock) {
		try {
			base = new Button(new Image("data/sprite/number_button.png"));
			numbers = new SpriteSheet("data/sprite/number_sprite.png", 25, 30);
			lockImg = new Image("data/sprite/lock.png");
			parts = new int[2];
			parts[0] = num / 10;
			parts[1] = num % 10;
			part1 = numbers.getSubImage(parts[0], 0);
			part2 = numbers.getSubImage(parts[1], 0);
			locked = lock;
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void state() {
		if(!locked)
			base.state();
	}
	
	public boolean isSelected() {
		return base.isSelected();
	}
	
	public void setSelect(boolean select) {
		base.setSelected(select);
	}
	
	public void setClick(boolean click) {
		base.setClick(click);
	}
	
	public void draw(int x, int y) {
		int xBase = x + base.getWidth() / 2;
		int yBase = y + base.getHeight() / 2;
		base.render(x, y);
		part1.draw(xBase - part1.getWidth() + 2, yBase - (part1.getHeight() / 2));
		part2.draw(xBase - 2, yBase - (part2.getHeight() / 2));
		if(locked)
			lockImg.draw(x, y);
			
	}
	
	
}
