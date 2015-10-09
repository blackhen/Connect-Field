package AnimationTesting;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Button {
	
	private boolean onButton;
	private boolean isClick;
	private boolean isSelect;
	
	private int currentX;
	private int currentY;
	private int buttonWidth;
	private int buttonHeight;
	private boolean[] state;
	
	private SpriteSheet buttonSheet;
	Image button;
	
	public Button(Image image, int state) {
		
		this.state = new boolean[state];
		buttonWidth = image.getWidth();
		buttonHeight = image.getHeight() / state;
		buttonSheet = new SpriteSheet(image, buttonWidth, buttonHeight);
		onButton = false;
		isClick = false;
		isSelect = false;
		currentX = 0;
		currentY = 0;
		button = buttonSheet.getSprite(0,  0);
		
		for(int i = 0; i < state; i++) {
			if (i == 0)
				this.state[i] = true;
			else
				this.state[i] = false;
		}
	}
	
	public void state(int x, int y) {
		if(x >= currentX && x <= currentX + buttonWidth && 
			y >= currentY && y <= currentY + buttonHeight ) {
			onButton = true;
			button = buttonSheet.getSprite(0, 1);
			if(Mouse.isButtonDown(0)) {
				button = buttonSheet.getSprite(0, 2);
				isClick = true;
				if(!Mouse.isButtonDown(1)) {
					isSelect = true;
				}
			}
			else 
				isClick = false;
		}
		else {
			onButton = false;
			button = buttonSheet.getSprite(0, 0);
		}
		
		if(isSelect)
			button = buttonSheet.getSprite(0, 2);
		else  if(!isSelect && !onButton)
			button = buttonSheet.getSprite(0, 0);
	}
	
	public void setState(int state) {
		if(state == 0) 
			button = buttonSheet.getSprite(0, 0);
		else if (state == 1)
			button = buttonSheet.getSprite(0, 1);
		else
			button = buttonSheet.getSprite(0, 2);
	}
	
	public void setSelect(boolean select) {
		isSelect = select;
	}
	
	public int getWidth() {
		return buttonWidth;
	}
	
	public int getHeight() {
		return buttonHeight;
	}
	
	public int getX() {
		return currentX;
	}
	
	public int getY() {
		return currentY;
	}
	
	public boolean onButton() {
		return onButton;
	}
	
	public boolean isClicking() {
		return isClick;
	}
	
	public boolean isSelect() {
		return isSelect;
	}
	
	public void render(int x, int y) {
		currentX = x;
		currentY = y;
		button.draw(currentX, currentY);
	}

}
