package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Utility.Button;
import Utility.NumberButton;

public class GameStage extends BasicGameState {
	
	private static String level = "";
	private NumberButton[][] numberBoard;
	private Button backButton;
	private Image background;
	private Image hf_stage;
	private float posX;
	private float posY;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		int count = 1;
		numberBoard = new NumberButton[20][20];
		background = new Image("data/sprite/background.png");
		hf_stage = new Image("data/sprite/hf_stage.png");
		backButton = new Button(new Image("data/sprite/back_button.png"));
		posX = 0F;
		posY = 0F;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				numberBoard[i][j] = new NumberButton(count, true);
				count++;
			}
		}
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		int x = 135;
		int y = 145;
		background.draw(posX, posY);
		hf_stage.draw(0, 0);
		backButton.render(30, 30);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				numberBoard[i][j].draw(x, y);
				x += 115;
			}
			x = 130;
			y += 95;
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		backButton.state();
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				numberBoard[i][j].state();
			}
		}
		
		int stage = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				if(numberBoard[i][j].isSelected()) {
					numberBoard[i][j].setClick(false);
					numberBoard[i][j].setSelect(false);
					Play.setGame(level, stage);
					sbg.getState(Main.play).init(gc, sbg);
					sbg.enterState(Main.play, new FadeOutTransition(), new FadeInTransition());
				}
				stage++;
			}
		}
		
		if(backButton.isSelected()) {
			sbg.getState(Main.gameLevel).init(gc, sbg);
			sbg.enterState(Main.gameLevel, new FadeOutTransition(), new FadeInTransition());
		}

		if(posX < -800 && posY < 600) {
			posX = 0F;
			posY = 0F;
		}
		else {
			posX -= 0.16;
			posY -= 0.12;
		}
		
	}

	@Override
	public int getID() {
		return 2;
	}
	
	public static void setLevel(String levels) {
		level = levels;
	}

}
