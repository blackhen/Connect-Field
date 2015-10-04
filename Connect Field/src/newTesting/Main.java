package newTesting;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Main extends StateBasedGame {
	
	public final static String title = "Connect Field";
	public static final int playLevel1 = 0;
	
	public Main(String title) {
		super(title);
		this.addState(new EasyMode(playLevel1));
		
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(playLevel1).init(gc, this);
		this.enterState(playLevel1);
		
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
