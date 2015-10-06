package newTesting;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Main extends StateBasedGame {
	
	public final static String title = "Connect Field";
	private static final int play = 1;
	private static final int newPlay = 2;
	
	public Main(String title) {
		super(title);
		this.addState(new Play("Easy.tmx", 1));
		this.addState(new newPlay("Normal.tmx", 1));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(play).init(gc,  this);
		this.getState(newPlay).init(gc,  this);
		this.enterState(newPlay);
	}
	
	public static void main(String[] args) {
		AppGameContainer gc;
		
		try {
			gc = new AppGameContainer(new Main(title));
			gc.setDisplayMode(800, 600, false);
			gc.setTargetFrameRate(60);
			gc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}

	}

}
