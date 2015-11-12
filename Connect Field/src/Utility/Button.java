package Utility;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Button {
	
	private boolean onButton;
	private boolean isClick;
	private boolean isSelect;
	
	private int mouseX;
	private int mouseY;
	private int currentX;
	private int currentY;
	private int buttonWidth;
	private int buttonHeight;
	
	private SpriteSheet buttonSheet;
	Image button;
	
	public Button(Image image) {
		
		buttonWidth = image.getWidth();
		buttonHeight = image.getHeight() / 3;
		buttonSheet = new SpriteSheet(image, buttonWidth, buttonHeight);
		onButton = false;
		isClick = false;
		isSelect = false;
		mouseX = 0;
		mouseY = 0;
		currentX = 0;
		currentY = 0;
		button = buttonSheet.getSprite(0,  0);
	}
	
	public void state() {
		mouseX = Mouse.getX();
		mouseY = Math.abs(Mouse.getY() - 600);
		
		if(mouseX >= currentX && mouseX <= currentX + buttonWidth && 
			mouseY >= currentY && mouseY <= currentY + buttonHeight) {
			onButton = true;
			if(Mouse.isButtonDown(0)) {
				isClick = true;
				if(!Mouse.isButtonDown(1)) {
					isSelect = true;
				}
				else
					isSelect = false;
			}
			else 
				isClick = false;
		}
		else {
			onButton = false;
		}
	}
	
	public void setSelected(boolean select) {
		isSelect = select;
	}
	
	public void setMouseIn(boolean on) {
		onButton = on;
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
	
	public boolean isSelected() {
		return isSelect;
	}
	
	public void render(int x, int y) {
		currentX = x;
		currentY = y;
		if(!onButton && !isClick && !isSelect) {
			button = buttonSheet.getSprite(0, 0);
		}
		else if(onButton && !isClick && !isSelect) {
			button = buttonSheet.getSprite(0, 1);
		}
		else if(isClick && !isSelect) {
			button = buttonSheet.getSprite(0, 2);
		}
		else if(isSelect) {
			button = buttonSheet.getSprite(0, 2);
		}
		button.draw(currentX, currentY);
	}

}
