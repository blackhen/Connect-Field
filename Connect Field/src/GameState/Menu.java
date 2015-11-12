package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Utility.Button;
import Utility.SelectButton;

public class Menu extends BasicGameState {
	
	private Image background;
	private Image border;
	private Image logo;
	private Button[] buttons;
	private SelectButton groupButton;
	private float posX;
	private float posY;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("data/sprite/background.png");
		border = new Image("data/sprite/border.png");
		logo = new Image("data/sprite/logo.png");
		buttons = new  Button[3];
		buttons[0] = new Button(new Image("data/sprite/start.png"));
		buttons[1] = new Button(new Image("data/sprite/config.png"));
		buttons[2] = new Button(new Image("data/sprite/exit.png"));
		groupButton = new SelectButton(buttons, 20, false);
		posX = 0F;
		posY = 0F;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(posX, posY);
		border.draw(0, 0);
		logo.draw(400 - logo.getWidth() / 2, 120);
		groupButton.render(gc, "center", 240);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		groupButton.mouseState();
		if(posX < -800 && posY < 600) {
			posX = 0F;
			posY = 0F;
		}
		else {
			posX -= 0.16;
			posY -= 0.12;
		}
		
		if(groupButton.getState() == 0) {
			sbg.getState(Main.gameLevel).init(gc, sbg);
			sbg.enterState(Main.gameLevel, new FadeOutTransition(), new FadeInTransition());
		}
		else if(groupButton.getState() == 1) {
			sbg.getState(Main.config).init(gc, sbg);
			sbg.enterState(Main.config, new FadeOutTransition(), new FadeInTransition());
		}
		else if(groupButton.getState() == 2) {
			System.exit(0);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
