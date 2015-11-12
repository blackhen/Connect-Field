package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Main extends StateBasedGame {
	
	private static final String title = "Connect Field";
	private final static int SCREEN_WIDTH = 800;
	private final static int SCREEN_HEIGHT = 600;
	public final static int menu = 0;
	public final static int gameLevel = 1;
	public final static int gameStage = 2;
	public final static int play = 3;
	public final static int config = 4;

	public Main(String title) {
		super(title);
		this.addState(new Menu());
		this.addState(new GameLevel());
		this.addState(new GameStage());
		this.addState(new Config());
		this.addState(new Play());
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(gameLevel).init(gc, this);
		this.getState(gameStage).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(config).init(gc, this);
		
		this.enterState(menu);
		
	}
	
	public static void main(String[] args) {
			AppGameContainer gc;
			
			try {
				gc = new AppGameContainer(new Main(title));
				gc.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
				gc.setTargetFrameRate(60);
				gc.setShowFPS(false);
				gc.start();
			} catch(SlickException e) {
				e.printStackTrace();
			}
	
		}
}
