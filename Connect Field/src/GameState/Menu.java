package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("This is Menu", 50, 50);
		g.drawString("1.Play", 50, 70);
		g.drawString("2.Config", 50, 90);
		g.drawString("3.Exit", 50, 110);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_1)) {
			sbg.enterState(1);
		}
		else if(input.isKeyPressed(Input.KEY_2)) {
			sbg.enterState(4);
		}
		else if(input.isKeyPressed(Input.KEY_3)) {
			System.exit(0);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
