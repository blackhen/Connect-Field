package AnimationTesting;

import org.lwjgl.input.Mouse;
import Utility.Block;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState{
	
	private SpriteSheet alienSprite;
	private SpriteSheet slimeDown;
	private Animation alienAnimation;
	private Animation down;
	private int dulation = 100;
	private Button button;
	private Button openMenu;
	private Button closeMenu;
	private Button newMenu;
	private Button[] buttons;
	private SelectButton group;
	private Color colorFilter;
	private Animation block;
	private Animation arrow_right;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		alienSprite = new SpriteSheet("data/sprite/alien_spritesheet.png", 32, 32);
		alienAnimation = new Animation(alienSprite, dulation);
		arrow_right = new Animation(new SpriteSheet("data/sprite/arrow_all.png", 160, 120), 150);
		button = new Button(new Image("data/sprite/StartButton.png"));
		colorFilter = new Color(192, 192, 192);
		slimeDown = new SpriteSheet("data/sprite/downSlime.png", 160, 120, colorFilter);
		down = new Animation(slimeDown, dulation);
		openMenu = new Button(new Image("data/sprite/mn_cfb_asave_pbtn00.png"));
		closeMenu = new Button(new Image("data/sprite/mn_cfb_asave_pbtn01.png"));
		newMenu = new Button(new Image("data/sprite/mn_cfb_asave_pbtn01.png"));
		buttons = new Button[3];
		buttons[0] = openMenu;
		buttons[1] = closeMenu;
		buttons[2] = newMenu;
		group = new SelectButton(buttons, 20, false);
		block = new Animation(new SpriteSheet("data/sprite/BlueBlock.png", 160, 120), 50);
		block.stopAt(5);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		alienAnimation.draw(100, 100);
		down.draw(50, 50);
		button.render(200, 200);
		group.render(150, 150);
		block.draw(100, 100);
		arrow_right.draw(50, 50);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		alienAnimation.update(delta);
		button.state();
		group.state();
		if(input.isKeyPressed(Input.KEY_1)) {
			block.restart();
		}
		
		if(group.getState() == 0)
			System.out.println("Hello Button 1!");
		else if(group.getState() == 1)
			System.out.println("Hello Button 2!");
		else if(group.getState() == 2)
			System.out.println("Hello Button 3!");
		else if(group.getState() == -1)
			System.out.println("No button at all!");
	}

	@Override
	public int getID() {
		
		return 0;
	}
	
}
