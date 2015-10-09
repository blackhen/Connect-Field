package AnimationTesting;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState{
	
	private SpriteSheet alienSprite;
	private Animation alienAnimation;
	private int dulation = 200;
	private Button button;
	private Button openMenu;
	private Button closeMenu;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		alienSprite = new SpriteSheet("data/sprite/alien_spritesheet.png", 32, 32);
		alienAnimation = new Animation(alienSprite, dulation);
		button = new Button(new Image("data/sprite/StartButton.png"), 3);
		openMenu = new Button(new Image("data/sprite/mn_cfb_asave_pbtn00.png"), 3);
		closeMenu = new Button(new Image("data/sprite/mn_cfb_asave_pbtn01.png"), 3);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		alienAnimation.draw(100, 100);
		button.render(200, 200);
		openMenu.render(200, 300);
		closeMenu.render(openMenu.getX() + openMenu.getWidth(), 300);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		alienAnimation.update(delta);
		int mouseX = Mouse.getX();
		int mouseY = Math.abs(Mouse.getY() - 600);
		button.state(mouseX, mouseY);
		openMenu.state(mouseX, mouseY);
		closeMenu.state(mouseX, mouseY);
		
		if(openMenu.isSelect()) {
			closeMenu.setSelect(false);
		}
		if(closeMenu.isSelect()){
			openMenu.setSelect(false);
		}
	}

	@Override
	public int getID() {
		
		return 0;
	}
	
}
