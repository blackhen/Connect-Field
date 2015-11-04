package Utility;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Arrow {
	
	private Animation aLeft;
	private Animation aRight;
	private Animation aTop;
	private Animation aDown;
	private Animation aLeftTop;
	private Animation aLeftRight;
	private Animation aLeftDown;
	private Animation aTopRight;
	private Animation aTopDown;
	private Animation aRightDown;
	private Animation aLeftTopRight;
	private Animation aTopRightDown;
	private Animation aRightDownLeft;
	private Animation aDownLeftTop;
	private Animation all;
	private Animation inUse;
	
	public Arrow() {
		try {
			aLeft = new Animation(new SpriteSheet("data/sprite/arrow_left.png", 160, 120), 150);
			aRight = new Animation(new SpriteSheet("data/sprite/arrow_right.png", 160, 120), 150);
			aTop = new Animation(new SpriteSheet("data/sprite/arrow_top.png", 160, 120), 150);
			aDown = new Animation(new SpriteSheet("data/sprite/arrow_down.png", 160, 120), 150);
			aLeftTop = new Animation(new SpriteSheet("data/sprite/arrow_left_top.png", 160, 120), 150);
			aLeftRight = new Animation(new SpriteSheet("data/sprite/arrow_left_right.png", 160, 120), 150);
			aLeftDown = new Animation(new SpriteSheet("data/sprite/arrow_left_down.png", 160, 120), 150);
			aTopRight = new Animation(new SpriteSheet("data/sprite/arrow_top_right.png", 160, 120), 150);
			aTopDown = new Animation(new SpriteSheet("data/sprite/arrow_top_down.png", 160, 120), 150);
			aRightDown = new Animation(new SpriteSheet("data/sprite/arrow_right_down.png", 160, 120), 150);
			aLeftTopRight = new Animation(new SpriteSheet("data/sprite/arrow_left_top_right.png", 160, 120), 150);
			aTopRightDown = new Animation(new SpriteSheet("data/sprite/arrow_top_right_down.png", 160, 120), 150);
			aRightDownLeft = new Animation(new SpriteSheet("data/sprite/arrow_right_down_left.png", 160, 120), 150);
			aDownLeftTop = new Animation(new SpriteSheet("data/sprite/arrow_down_left_top.png", 160, 120), 150);
			all = new Animation(new SpriteSheet("data/sprite/arrow_all.png", 160, 120), 150);
			inUse = all;
		} catch (SlickException e) {
			
			e.printStackTrace();
		}
	}
	
	public void state(boolean stateUp, boolean stateDown, boolean stateLeft, boolean stateRight) {
		if(!stateUp && !stateDown && !stateLeft && !stateRight)
			inUse = all;
		else if(!stateUp && stateDown && stateLeft && stateRight)
			inUse = aTop;
		else if(stateUp && !stateDown && stateLeft && stateRight)
			inUse = aDown;
		else if(stateUp && stateDown && !stateLeft && stateRight)
			inUse = aLeft;
		else if(stateUp && stateDown && stateLeft && !stateRight)
			inUse = aRight;
		else if(!stateUp && !stateDown && stateLeft && stateRight)
			inUse = aTopDown;
		else if(!stateUp && stateDown && !stateLeft && stateRight)
			inUse = aLeftTop;
		else if(!stateUp && stateDown && stateLeft && !stateRight)
			inUse = aTopRight;
		else if(stateUp && !stateDown && !stateLeft && stateRight)
			inUse = aLeftDown;
		else if(stateUp && !stateDown && stateLeft && !stateRight)
			inUse = aRightDown;
		else if(stateUp && stateDown && !stateLeft && !stateRight)
			inUse = aLeftRight;
		else if(!stateUp && !stateDown && !stateLeft && stateRight) 
			inUse = aDownLeftTop;
		else if(!stateUp && !stateDown && stateLeft && !stateRight)
			inUse = aTopRightDown;
		else if(!stateUp && stateDown && !stateLeft && !stateRight)
			inUse = aLeftTopRight;
		else if(stateUp && !stateDown && !stateLeft && !stateRight)
			inUse = aRightDownLeft;
		else if(stateUp && stateDown && stateLeft && stateRight) 
			inUse = all;
	}
	
	public void draw(int x, int y, int width, int height) {
		inUse.draw(x, y, width, height);
	}
}
