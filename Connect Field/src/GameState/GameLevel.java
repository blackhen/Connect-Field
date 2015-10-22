package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameLevel extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("This is Level", 50, 50);
		g.drawString("1.Easy", 50, 70);
		g.drawString("2.Normal", 50, 90);
		g.drawString("3.Hard", 50, 110);
		g.drawString("4.Exit", 50, 130);
		g.drawString("5.Back", 50, 150);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_1)) {
			GameStage.setLevel("Easy");
			sbg.getState(Main.gameStage).init(gc, sbg);
			sbg.enterState(Main.gameStage);
		}
		else if(input.isKeyPressed(Input.KEY_2)) {
			GameStage.setLevel("Normal");
			sbg.getState(Main.gameStage).init(gc, sbg);
			sbg.enterState(Main.gameStage);
		}
		else if(input.isKeyPressed(Input.KEY_3)) {
			GameStage.setLevel("Hard");
			sbg.getState(Main.gameStage).init(gc, sbg);
			sbg.enterState(Main.gameStage);
		}
		else if(input.isKeyPressed(Input.KEY_4)) {
			System.exit(0);
		}
		else if(input.isKeyPressed(Input.KEY_5)) {
			sbg.enterState(0);
		}
		
	}

	@Override
	public int getID() {
		return 1;
	}

}
