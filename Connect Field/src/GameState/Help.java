package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import Utility.Button;

public class Help extends BasicGameState {
	
	private Image background;
	private Image help_hf;
	private Image htp1;
	private Image htp2;
	private Image[] slide;
	private Button prevButton;
	private Button nextButton;
	private Button backButton;
	
	private int number_slide;
	private float posX;
	private float posY;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("data/sprite/background.png");
		help_hf = new Image("data/sprite/help_hf.png");
		htp1 = new Image("data/sprite/how_to_play_1.png");
		htp2 = new Image("data/sprite/how_to_play_2.png");
		slide = new Image[2];
		slide[0] = htp1;
		slide[1] = htp2;
		prevButton = new Button(new Image("data/sprite/prev_button.png"));
		nextButton = new Button(new Image("data/sprite/next_button.png"));
		backButton = new Button(new Image("data/sprite/back_button.png"));
		posX = 0;
		posY = 0;
		number_slide = 0;
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(posX, posY);
		help_hf.draw(0, 0);
		backButton.render(15, 23);
		nextButton.render(715, 525);
		prevButton.render(25, 525);
		slide[number_slide].draw(0, 0);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		backButton.state();
		nextButton.state();
		prevButton.state();
		
		if(backButton.isSelected()) {
			sbg.enterState(Main.menu, new FadeOutTransition(), new FadeInTransition());
		}
		else if(nextButton.isSelected()) {
			nextButton.setClick(false);
			nextButton.setSelected(false);
			if(number_slide == slide.length - 1)
				number_slide = 0;
			else
				number_slide++;
		}
		else if(prevButton.isSelected()) {
			prevButton.setClick(false);
			prevButton.setSelected(false);
			if(number_slide == 0)
				number_slide = slide.length - 1;
			else
				number_slide--;
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

		return 4;
	}
	
}
