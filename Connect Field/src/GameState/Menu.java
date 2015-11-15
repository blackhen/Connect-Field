package GameState;

import org.lwjgl.input.Mouse;
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
	private SpriteSheet close1;
	private SpriteSheet close2;
	private Button startButton;
	private Button settingButton;
	private Button helpButton;
	private Button exitButton;
	private Button sound;
	private Button fullscreen;
	private Animation subIn;
	private Animation subOut;
	private float posX;
	private float posY;
	private boolean settingIn;
	private boolean clickIn;
	private boolean clickOut;
	private boolean soundOn;
	private boolean fullOn;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("data/sprite/background.png");
		border = new Image("data/sprite/border.png");
		logo = new Image("data/sprite/logo.png");
		close1 = new SpriteSheet("data/sprite/close_button.png", 30, 30);
		close2 = new SpriteSheet("data/sprite/close_button.png", 30, 30);
		startButton = new Button(new Image("data/sprite/play.png"));
		settingButton = new Button(new Image("data/sprite/setting.png"));
		helpButton = new Button(new Image("data/sprite/help.png"));
		exitButton = new Button(new Image("data/sprite/exit.png"));
		subIn = new Animation(new SpriteSheet("data/sprite/sub_setting.png", 210, 70), 25);
		subOut = new Animation(new SpriteSheet("data/sprite/sub_setting2.png", 210, 70), 25);
		sound = new Button(new Image("data/sprite/close_button.png"));
		fullscreen = new Button(new Image("data/sprite/close_button.png"));
		subIn.stopAt(6);
		subOut.stopAt(6);
		subOut.stop();
		subIn.stop();
		posX = 0F;
		posY = 0F;
		settingIn = false;
		clickIn = false;
		clickOut = false;
		soundOn = true;
		fullOn = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(posX, posY);
		border.draw(0, 0);
		logo.draw(400 - logo.getWidth() / 2, 140);
		startButton.render(400 - startButton.getWidth() / 2, 260);
		exitButton.render(20, 20);
		if(!settingIn) {
			subIn.draw(495, 515);
		}
		else {
			subOut.draw(495, 515);
			if(clickIn) {
				sound.render(595, 535);
				fullscreen.render(545, 535);
				if(soundOn) close1.getSubImage(0, 1).draw(595, 535);
				else close1.getSubImage(0, 2).draw(595, 535);
				if(fullOn) close2.getSubImage(0, 1).draw(545, 535);
				else close2.getSubImage(0, 2).draw(545, 535);
			}
		}
		settingButton.render(635, 515);
		helpButton.render(715, 515);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		gc.setFullscreen(fullOn);
		
		startButton.state();
		settingButton.state();
		helpButton.state();
		exitButton.state();
		if(startButton.isSelected()) {
			startButton.setClick(false);
			startButton.setSelected(false);
			sbg.getState(Main.gameLevel).init(gc, sbg);
			sbg.enterState(Main.gameLevel, new FadeOutTransition(), new FadeInTransition());
		}
		
		//-----------------------Setting button click-----------------//
		if(settingButton.isSelected() && !settingIn) {
			subIn.start();
			settingButton.setSelected(false);
			settingButton.setClick(false);
			clickIn = true;
			clickOut = false;
		}
		else if(settingButton.isSelected() && settingIn) {
			subOut.start();
			settingButton.setSelected(false);
			settingButton.setClick(false);
			clickIn = false;
			clickOut = true;
		}
		if(subIn.isStopped() && clickIn && !clickOut) {
			subIn.setCurrentFrame(0);
			subOut.setCurrentFrame(0);
			settingIn = true;
		}
		else if(subOut.isStopped() && !clickIn && clickOut) {
			subOut.stop();
			subIn.stop();
			settingIn = false;
			clickIn = false;
			clickOut = false;
		}
		if(clickIn) {
			sound.state();
			fullscreen.state();
			
			if(sound.isSelected()) {
				soundOn = !soundOn;
				sound.setSelected(false);
				sound.setClick(false);
			}
			if(fullscreen.isSelected()) {
				fullOn = !fullOn;
				fullscreen.setSelected(false);
				fullscreen.setClick(false);
			}
		}
		//----------------------------------------------------------------------//
		
		if(helpButton.isSelected()) {
			
		}
		if(exitButton.isSelected()) {
			System.exit(0);
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
		return 0;
	}

}
