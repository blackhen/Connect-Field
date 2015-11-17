package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Utility.Button;

public class GameLevel extends BasicGameState {
	
	private Image background;
	private Image hf;
	private Button backButton;
	private Button easy_button;
	private Button normal_button;
	private Button hard_button;
	private float posX;
	private float posY;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("data/sprite/background.png");
		hf = new Image("data/sprite/hf_level.png");
		backButton = new Button(new Image("data/sprite/back_button.png"));
		easy_button = new Button(new Image("data/sprite/easy_button.png"));
		normal_button = new Button(new Image("data/sprite/normal_button.png"));
		hard_button = new Button(new Image("data/sprite/hard_button.png"));
		posX = 0;
		posY = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(posX, posY);
		hf.draw(0, 0);
		backButton.render(20, 35);	
		easy_button.render(25, 195);
		normal_button.render(280, 195);
		hard_button.render(535, 195);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		backButton.state();
		easy_button.state();
		normal_button.state();
		hard_button.state();
		
		if(posX < -800 && posY < 600) {
			posX = 0F;
			posY = 0F;
		}
		else {
			posX -= 0.16;
			posY -= 0.12;
		}
		
		if(backButton.isSelected()) {
			sbg.enterState(Main.menu, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(easy_button.isSelected()) {
			GameStage.setLevel("Easy");
			sbg.getState(Main.gameStage).init(gc, sbg);
			sbg.enterState(Main.gameStage, new FadeOutTransition(), new FadeInTransition());
		}
		else if(normal_button.isSelected()) {
			GameStage.setLevel("Normal");
			sbg.getState(Main.gameStage).init(gc, sbg);
			sbg.enterState(Main.gameStage, new FadeOutTransition(), new FadeInTransition());
		}
		else if(hard_button.isSelected()) {
			GameStage.setLevel("Hard");
			sbg.getState(Main.gameStage).init(gc, sbg);
			sbg.enterState(Main.gameStage, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public int getID() {
		return 1;
	}
}
