package Utility;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Block {
	
	private SpriteSheet blockSprite;
	private Animation[] animation;
	private Animation current;
	private Image[] inUse;
	
	public Block(Image img) {
		blockSprite = new SpriteSheet(img, img.getWidth(), img.getHeight() / 4);
	}
	
	public Block(Animation[] ani) {
		this.animation = ani;
		this.animation[0].stopAt(5);
		this.animation[1].stopAt(5);
		current = ani[0];
	}
	
	public void setState(boolean state) {
		if(state) {
			current = animation[1];
			current.restart();
		}
		else {
			current = animation[0];
			current.restart();
		}
	}
	
	public void setUse(int num) {
		inUse = new Image[2];
		if(num == 0) {
			inUse[0] = blockSprite.getSubImage(0, 0);
			inUse[1] = blockSprite.getSubImage(0, 2);
		}
		else if(num == 1) {
			inUse[0] = blockSprite.getSubImage(0, 1);
			inUse[1] = blockSprite.getSubImage(0, 3);
		}
	}
	
	public void draw(int x, int y, int width, int height) {
		current.draw(x, y, width, height);
	}
	
	public void render(int x, int y, int tx, int ty, boolean state) {
		if(!state) 	inUse[0].draw(x, y, tx, ty);
		else if (state) inUse[1].draw(x, y, tx, ty);
	}
}
