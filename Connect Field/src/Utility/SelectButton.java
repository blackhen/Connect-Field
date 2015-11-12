package Utility;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class SelectButton {
	
	private Button[] groupButton;
	private int space;
	private int setX;
	private int setY;
	private int num_space;
	private int currentState;
	private boolean horizontal;
	private boolean[] allState;
	
	public SelectButton(Button[] buttons) {
		groupButton = buttons;
		space = 0;
		setX = 0;
		setY = 0;
		currentState = -1;
		horizontal = true;
		allState = new boolean[groupButton.length];
		for(int i = 0; i < allState.length; i++) allState[i] = false;
	}
	
	public SelectButton(Button[] buttons, int space) {
		groupButton = buttons;
		this.space = space;
		setX = 0;
		setY = 0;
		currentState = -1;
		num_space = 0;
		horizontal = true;
		allState = new boolean[groupButton.length];
		for(int i = 0; i < allState.length; i++) allState[i] = false;
	}
	
	public SelectButton(Button[] buttons, boolean horizontal) {
		groupButton = buttons;
		space = 0;
		setX = 0;
		setY = 0;
		currentState = -1;
		num_space = 0;
		this.horizontal = horizontal;
		allState = new boolean[groupButton.length];
		for(int i = 0; i < allState.length; i++) allState[i] = false;
	}
	
	public SelectButton(Button[] buttons, int space, boolean horizontal) {
		groupButton = buttons;
		this.space = space;
		setX = 0;
		setY = 0;
		currentState = -1;
		num_space = 0;
		this.horizontal = horizontal;
		allState = new boolean[groupButton.length];
		for(int i = 0; i < allState.length; i++) allState[i] = false;
	}
	
	public void mouseState() {
		
		int prev = -1;
		int count = 0;
		
		for(int i = 0; i < allState.length; i++) {
			if(allState[i]) {
				prev = i;
			}
		}

		for(int i = 0; i <  groupButton.length; i++) {
			groupButton[i].state();
			
			if(groupButton[i].isSelected() && prev == -1) allState[count] = true;
			else if(groupButton[i].isSelected() && allState[prev]) {
				allState[prev] = false;
				allState[count] = true;
				groupButton[prev].setSelected(false);
			}
			count++;
		}
		
		for(int i = 0; i < groupButton.length; i++) {
			if(allState[i])
				groupButton[i].setSelected(true);
			else
				groupButton[i].setSelected(false);
		}
	}
	
	public int getState() {
		for(int i = 0; i < allState.length; i++)
			if(allState[i])
				return i;
		return -1;
	}
	
	public void keyboardState(GameContainer gc) {
		Input input = gc.getInput();
		
		if(horizontal) {
			if(input.isKeyPressed(Input.KEY_LEFT)) {
				clear();
				shiftLeft();
			}
			else if(input.isKeyPressed(Input.KEY_RIGHT)) {
				clear();
				shiftRight();
			}
		}
		else {
			if(input.isKeyPressed(Input.KEY_UP)) {
				clear();
				shiftLeft();
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)) {
				clear();
				shiftRight();
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)) {
			clear();
			allState[currentState] = true;
			groupButton[currentState].setSelected(true);
		}
		
		input.clearControlPressedRecord();
	}
	
	private void clear() {
		for(int i = 0; i < allState.length; i++) {
			allState[i] = false;
			groupButton[i].setMouseIn(false);
			groupButton[i].setSelected(false);
		}
	}
	
	private void shiftLeft() {
		if(currentState == -1 || currentState == 0) {
			currentState = allState.length - 1;
			clear();
			groupButton[currentState].setMouseIn(true);
		}
		else {
			currentState = currentState - 1;
			clear();
			groupButton[currentState].setMouseIn(true);
		}
	}
	
	private void shiftRight() {
		if(currentState == -1 || currentState == allState.length - 1) {
			currentState = 0;
			clear();
			groupButton[currentState].setMouseIn(true);
		}
		else {
			currentState = currentState + 1;
			clear();
			groupButton[currentState].setMouseIn(true);
		}
	}
	
	public void render(int x, int y) {
		
		for(Button each: groupButton) {
			
			if(horizontal) 	{
				each.render(x + setX + num_space, y);
				setX += groupButton[0].getWidth();
			}
			else {
				each.render(x, y + setY + num_space);
				setY += groupButton[0].getHeight();
			}
			num_space += space;
		}
		
		setX = 0;
		setY = 0;
		num_space = 0;
	}
	
	public void render(GameContainer gc, String position, int height) {
		if(position.equals("center")) {
			int x = gc.getWidth() / 2 - getAllWidth() / 2;
			for(Button each: groupButton) {
				
				if(horizontal) 	{
					each.render(x + setX + num_space, height);
					setX += groupButton[0].getWidth();
				}
				else {
					each.render(x, height + setY + num_space);
					setY += groupButton[0].getHeight();
				}
				num_space += space;
			}
			
			setX = 0;
			setY = 0;
			num_space = 0;
		}
	}
	
	public int getAllWidth() {
		if(horizontal)
			return groupButton[0].getWidth() * groupButton.length + (groupButton.length - 1) * space;
		else
			return groupButton[0].getWidth();
	}
	
	public int getAllHeight() {
		return groupButton[0].getHeight() * groupButton.length + (groupButton.length - 1) * space;
	}
	
}
