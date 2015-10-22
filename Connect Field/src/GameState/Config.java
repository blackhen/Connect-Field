package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Config extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("This is Config mode", 50, 50);
		g.drawString("1.Back", 50, 70);
		g.drawString("2.Exit", 50, 90);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_1)) {
			sbg.enterState(0);
		}
		else if(input.isKeyPressed(Input.KEY_2)) {
			System.exit(0);
		}
	}

	@Override
	public int getID() {
		return 4;
	}

}
