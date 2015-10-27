package GameState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameStage extends BasicGameState {
	
	private static String level = "";

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		int num = 70;
		String number = "";
		g.drawString("This is Stage", 50, 50);
		for(int i = 1; i < 6; i++) {
			Integer a = new Integer(i);
			
			number = a.toString();
			
			g.drawString(number, 50, num);
			num += 20;
		}
		g.drawString("6.Exit", 50, num);
		g.drawString("7.Back", 50, num+20);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_1)) {
			Play.setGame(level, 0);
			sbg.getState(Main.play).init(gc, sbg);
			sbg.enterState(Main.play);
		}
		else if(input.isKeyPressed(Input.KEY_2)) {
			Play.setGame(level, 1);
			sbg.getState(Main.play).init(gc, sbg);
			sbg.enterState(Main.play);
		}
		else if(input.isKeyPressed(Input.KEY_3)) {
			Play.setGame(level, 2);
			sbg.getState(Main.play).init(gc, sbg);
			sbg.enterState(Main.play);
		}
		else if(input.isKeyPressed(Input.KEY_4)) {
			Play.setGame(level, 3);
			sbg.getState(Main.play).init(gc, sbg);
			sbg.enterState(Main.play);
		}
		else if(input.isKeyPressed(Input.KEY_5)) {
			sbg.enterState(3);
		}
		else if(input.isKeyPressed(Input.KEY_6)) {
			System.exit(0);
		}
		else if(input.isKeyPressed(Input.KEY_7)) {
			sbg.enterState(1);
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
