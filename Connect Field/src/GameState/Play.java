package GameState;

import java.io.IOException;
import java.util.Stack;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import Utility.Arrow;
import Utility.Block;
import Utility.Button;
import Utility.Line;
import Utility.SaveData;

public class Play extends BasicGameState {
	
	private TiledMap map;
	private SpriteSheet focus;
	private Arrow arrow;
	private SaveData save;
	private Image backgroundMenu;
	private Image fade;
	private Image pauseText;
	private Image clearText;
	private Button playButton;
	private Button resetButton;
	private Button stageButton;
	private Button homeButton;
	private Button prevButton;
	private Button nextButton;
	
	private int posX;
	private int posY;
	private int blockWidth;
	private int blockHeight;
	private int blockRow;
	private int blockColumn;
	private static int stage;
	
	private Block[][] blockBoard;
	private Line[][] lineBoard;
	private boolean[][] boardState;
	private boolean start;
	private boolean pause;
	private boolean clear;
	private boolean finishedWrite;
	private Stack<String> keyList;
	private Stack<String> keys;
	private Stack<String> booleans;
	
	private static String path = "data/Stage/Easy.tmx";
	private static String line_path = "data/sprite/line_Easy.png";
	private String prevKey = "";
	private String prevAllKey = "";
	private String bool = "";
	

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		arrow = new Arrow();
		map = new TiledMap(path);
		focus = new SpriteSheet("data/sprite/line_Easy.png", 160, 120);
		backgroundMenu = new Image("data/sprite/menu_background.png");
		fade = new Image("data/sprite/pause.png");
		pauseText = new Image("data/sprite/pause_text.png");
		clearText = new Image("data/sprite/gameclear_text.png");
		playButton = new Button(new Image("data/sprite/play_button.png"));
		resetButton = new Button(new Image("data/sprite/reload_button.png"));
		stageButton = new Button(new Image("data/sprite/stage_button.png"));
		homeButton = new Button(new Image("data/sprite/home_button.png"));
		prevButton = new Button(new Image("data/sprite/prev_button.png"));
		nextButton = new Button(new Image("data/sprite/next_Button.png"));
		blockHeight = map.getTileHeight();
		blockWidth = map.getTileWidth();
		blockRow = map.getHeight();
		blockColumn = map.getWidth();
		posX = blockColumn / 2 * blockWidth;
		posY = blockRow / 2 * blockHeight;
		blockBoard = new Block[blockWidth][blockHeight];
		boardState = new boolean[blockWidth][blockHeight];
		lineBoard =  new Line[blockWidth][blockHeight];
		start = false;
		pause = false;
		clear = false;
		finishedWrite = false;
		keyList = new Stack<String>();
		keys = new Stack<String>();
		booleans = new Stack<String>();
		
		for(int row = 0; row < blockRow; row++) {
			for(int col = 0; col < blockColumn; col++) {
				int tileID = map.getTileId(col, row, stage);
				if(map.getTileProperty(tileID, "blocked", "false").equals("true")) 
					boardState[col][row] = true;
				else {
					Animation[] ani = {new Animation(new SpriteSheet("data/sprite/blockReverse.png", 160, 120), 40), 
							new Animation(new SpriteSheet("data/sprite/blockFill.png", 160, 120), 40)};
					ani[0].setCurrentFrame(5);
					blockBoard[col][row] = new Block(ani);
					boardState[col][row] = false;
				}
				
				lineBoard[col][row] = new Line(new SpriteSheet(line_path, blockWidth, blockHeight));
				lineBoard[col][row].setStart(false);
			}
		}
		
		try {
			save = new SaveData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		for(int row = 0; row < blockRow; row++) {
			for(int col = 0; col < blockColumn; col++) {
				if(map.getTileProperty(map.getTileId(col, row, stage), "blocked", "false").equals("false")) {
					blockBoard[col][row].draw(col * blockWidth, row * blockHeight, blockWidth, blockHeight);
				}
				lineBoard[col][row].draw(col * blockWidth, row * blockHeight, blockWidth, blockHeight);
			}
		}
		map.render(0, 0, stage);
		arrow.draw(posX, posY, blockWidth, blockHeight);
		if(!start)
			focus.getSubImage(1, 0).draw(posX, posY, blockWidth, blockHeight);
		
		if(pause || clear) {
			fade.draw(0, 0);
			backgroundMenu.draw(0, 0);
			resetButton.render(800-185, 235);
			homeButton.render(800-135, 310);
			stageButton.render(800-225, 310);
			if(pause) {
				pauseText.draw(100, 300 - pauseText.getHeight() / 2);
				playButton.render(800-270, 235);
			}
			else if(clear) {
				clearText.draw(15, 300 - clearText.getHeight() / 2);
				nextButton.render(700, 235);
				prevButton.render(800-270, 235);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		int tileX = posX / blockWidth;
		int tileY = posY / blockHeight;
		int tileId = map.getTileId(tileX, tileY, stage);
		
		boolean up = map.getTileProperty(tileId, "top", "false").equals("false");
		boolean down = map.getTileProperty(tileId, "down", "false").equals("false");
		boolean left = map.getTileProperty(tileId, "left", "false").equals("false");
		boolean right = map.getTileProperty(tileId, "right", "false").equals("false");
		
		//----------checking for arrow appearance--------//
		arrowChecking(up, down, left, right, tileX, tileY);
		
		if(!keyList.isEmpty()) prevKey = keyList.peek();
		if(!keys.isEmpty()) prevAllKey = keys.peek();
		if(!booleans.isEmpty()) bool = booleans.peek();
		
		if((input.isKeyPressed(Input.KEY_ESCAPE) || input.isMousePressed(1)) && !clear) {
			pause = !pause;
		}
		
		//-------select start block--------//
		if(!start && !pause && !clear) {

			if(input.isKeyPressed(Input.KEY_UP)) {
				posY -= blockHeight;
				if(posY < 0) posY = 0;
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)) {
				posY += blockHeight;
				if(posY > (blockHeight * (blockRow - 1))) posY = (blockHeight * (blockRow - 1));
			}
			else if(input.isKeyPressed(Input.KEY_LEFT)) {
				posX -= blockWidth;
				if(posX < 0) posX = 0;
			}
			else if(input.isKeyPressed(Input.KEY_RIGHT)) {
				posX += blockWidth;
				if(posX > (blockWidth * (blockColumn - 1))) posX = (blockWidth * (blockColumn - 1));
			}
			if(input.isKeyPressed(Input.KEY_ENTER) && !boardState[tileX][tileY]) {
				boardState[tileX][tileY] = true;
				blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
				lineBoard[tileX][tileY].setStart(true);
				start = true;
			}
		}
		//----------end select start block-----------//
		
		//----------play mode--------------//
		else if(start && !pause && !clear) {

			if(input.isKeyPressed(Input.KEY_UP)) {
				if(up) {
					posY -= blockHeight;
					tileY = posY / blockHeight;
					if(!boardState[tileX][tileY]) {
						boardState[tileX][tileY] = true;
						keyList.push("up");
						keys.push("up");
						booleans.push("true");
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						lineBoard[tileX][tileY].addKey("up");
						lineBoard[tileX][tileY + 1].addKey("up");
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("down")) {
							boardState[tileX][tileY + 1] = false;
							keyList.pop();
							keys.push("up");
							booleans.push("false");
							blockBoard[tileX][tileY + 1].setState(boardState[tileX][tileY + 1]);
							lineBoard[tileX][tileY].delKey();
							lineBoard[tileX][tileY + 1].delKey();
						}
						else 
							posY += blockHeight;
					}
				}
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)) {
				if(down) {
					posY += blockHeight;
					tileY = posY / blockHeight;
					if(!boardState[tileX][tileY]) {
						boardState[tileX][tileY] = true;
						keyList.push("down");
						keys.push("down");
						booleans.push("true");
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						lineBoard[tileX][tileY].addKey("down");
						lineBoard[tileX][tileY - 1].addKey("down");
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("up")) {
							boardState[tileX][tileY - 1] = false;
							blockBoard[tileX][tileY - 1].setState(boardState[tileX][tileY - 1]);
							keyList.pop();
							keys.push("down");
							booleans.push("false");
							lineBoard[tileX][tileY].delKey();
							lineBoard[tileX][tileY - 1].delKey();
						}
						else
							posY -= blockHeight;
					}
				}
			}
			else if(input.isKeyPressed(Input.KEY_LEFT)) {
				if(left) {
					posX -= blockWidth;
					tileX = posX / blockWidth;
					if(!boardState[tileX][tileY]) {
						boardState[tileX][tileY] = true;
						keyList.push("left");
						keys.push("left");
						booleans.push("true");
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						lineBoard[tileX][tileY].addKey("left");
						lineBoard[tileX + 1][tileY].addKey("left");
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("right")) {
							boardState[tileX + 1][tileY] = false;
							blockBoard[tileX + 1][tileY].setState(boardState[tileX + 1][tileY]);
							keyList.pop();
							keys.push("left");
							booleans.push("false");
							lineBoard[tileX][tileY].delKey();
							lineBoard[tileX + 1][tileY].delKey();
						}
						else
							posX += blockWidth;
					}
				}
			}
			else if(input.isKeyPressed(Input.KEY_RIGHT)) {
				if(right) {
					posX += blockWidth;
					tileX = posX / blockWidth;
					if(!boardState[tileX][tileY]) {
						boardState[tileX][tileY] = true;
						keyList.push("right");
						keys.push("right");
						booleans.push("true");
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						lineBoard[tileX][tileY].addKey("right");
						lineBoard[tileX - 1][tileY].addKey("right");
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("left")) {
							boardState[tileX - 1][tileY] = false;
							keyList.pop();
							keys.push("right");
							booleans.push("false");
							blockBoard[tileX - 1][tileY].setState(boardState[tileX - 1][tileY]);
							lineBoard[tileX][tileY].delKey();
							lineBoard[tileX - 1][tileY].delKey();
						}
						else
							posX -= blockWidth;
					}
				}
			}
			else if(input.isKeyPressed(Input.KEY_Z)) {
				if(keys.isEmpty()) {
					boardState[tileX][tileY] = false;
					lineBoard[tileX][tileY].delKey();
					lineBoard[tileX][tileY].setStart(false);
					start = false;
					blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
				}
				else if(prevAllKey.equals("up")) {
					posY += blockHeight;
					if(bool.equals("true")) {
						boardState[tileX][tileY] = false;
						lineBoard[tileX][tileY].delKey();
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						tileY = posY / blockHeight;
						lineBoard[tileX][tileY].delKey();
						keyList.pop();
					}
					else { 
						lineBoard[tileX][tileY].addKey("down");
						tileY = posY / blockHeight;
						boardState[tileX][tileY] = true;
						lineBoard[tileX][tileY].addKey("down");
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						keyList.push("down");
					}
					keys.pop();
					booleans.pop();
				}
				else if(prevAllKey.equals("down")) {
					posY -= blockHeight;
					if(bool.equals("true")) {
						boardState[tileX][tileY] = false;
						lineBoard[tileX][tileY].delKey();
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						tileY = posY / blockHeight;
						lineBoard[tileX][tileY].delKey();
						keyList.pop();
					}
					else {
						lineBoard[tileX][tileY].addKey("up");
						tileY = posY / blockHeight;
						boardState[tileX][tileY] = true;
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						lineBoard[tileX][tileY].addKey("up");
						keyList.push("up");
					}
					keys.pop();
					booleans.pop();
				}
				else if(prevAllKey.equals("left")) {
					posX += blockWidth;
					if(bool.equals("true")) {
						boardState[tileX][tileY] = false;
						lineBoard[tileX][tileY].delKey();
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						tileX = posX / blockWidth;
						lineBoard[tileX][tileY].delKey();
						keyList.pop();
					}
					else {
						lineBoard[tileX][tileY].addKey("right");
						tileX = posX / blockWidth;
						boardState[tileX][tileY] = true;
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						lineBoard[tileX][tileY].addKey("right");
						keyList.push("right");
					}
					keys.pop();
					booleans.pop();
				}
				else if(prevAllKey.equals("right")) {
					posX -= blockWidth;
					if(bool.equals("true")) {
						boardState[tileX][tileY] = false;
						lineBoard[tileX][tileY].delKey();
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						tileX = posX / blockWidth;
						lineBoard[tileX][tileY].delKey();
						keyList.pop();
					}
					else {
						lineBoard[tileX][tileY].addKey("left");
						tileX = posX / blockWidth;
						boardState[tileX][tileY] = true;
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
						lineBoard[tileX][tileY].addKey("left");
						keyList.push("left");
					}
					keys.pop();
					booleans.pop();
				}
			}
		}
		else if(pause || clear) {
			if(clear) {
				if(stage != 0)
					prevButton.state();
				if(stage != 19) 
					nextButton.state();
			}
			else if(pause) {
				playButton.state();
			}
			stageButton.state();
			homeButton.state();
			resetButton.state();
		}
		//---------------end play-------------------------------------------------------------//
		
		//---------------checking button state-----------------------//
		if(playButton.isSelected()) {
			playButton.setClick(false);
			playButton.setSelected(false);
			pause = false;
		}
		else if(resetButton.isSelected()) {
			Play.setGame(GameStage.level, stage);
			sbg.getState(Main.play).init(gc, sbg);
			sbg.enterState(Main.play);
		}
		else if(stageButton.isSelected()) {
			sbg.getState(Main.gameStage).init(gc, sbg);;
			sbg.enterState(Main.gameStage, new FadeOutTransition(), new FadeInTransition());
		}
		else if(homeButton.isSelected()) {
			sbg.enterState(Main.menu, new FadeOutTransition(), new FadeInTransition());
		}
		else if(prevButton.isSelected()) {
			stage--;
			Play.setGame(GameStage.level, stage);
			sbg.getState(Main.play).init(gc, sbg);
			sbg.enterState(Main.play, new FadeOutTransition(), new FadeInTransition());
		}
		else if(nextButton.isSelected()) {
			stage++;
			try {
				save.saveData(GameStage.level, stage);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Play.setGame(GameStage.level, stage);
			sbg.getState(Main.play).init(gc, sbg);
			sbg.enterState(Main.play, new FadeOutTransition(), new FadeInTransition());
		}
		//-----------------------------------------------------------//

		if(gameClear()) {
			clear = true;
			if(!finishedWrite && stage != 19) {
				try {
					save.saveData(GameStage.level, stage+1);
					finishedWrite = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		input.clearKeyPressedRecord();
	}

	@Override
	public int getID() {
		return 3;
	}
	
	private void arrowChecking(boolean up, boolean down, boolean left, boolean right, int tileX, int tileY) {
		if(up && down && left && right) {
			arrow.state(boardState[tileX][tileY-1], boardState[tileX][tileY+1], boardState[tileX-1][tileY], boardState[tileX+1][tileY]);
		}
		else if(up && down && left && !right) {
			arrow.state(boardState[tileX][tileY-1], boardState[tileX][tileY+1], boardState[tileX-1][tileY], true);
		}
		else if(up && down && !left && right) {
			arrow.state(boardState[tileX][tileY-1], boardState[tileX][tileY+1], true, boardState[tileX+1][tileY]);
		}
		else if(up && !down && left && right) {
			arrow.state(boardState[tileX][tileY-1], true, boardState[tileX-1][tileY], boardState[tileX+1][tileY]);
		}
		else if(!up && down && left && right) {
			arrow.state(true, boardState[tileX][tileY+1], boardState[tileX-1][tileY], boardState[tileX+1][tileY]);
		}
		else if(!up && !down && left && right) {
			arrow.state(true, true, boardState[tileX-1][tileY], boardState[tileX+1][tileY]);
		}
		else if(!up && down && !left && right) {
			arrow.state(true, boardState[tileX][tileY+1], true, boardState[tileX+1][tileY]);
		}
		else if(!up && down && left && !right) {
			arrow.state(true, boardState[tileX][tileY+1], boardState[tileX-1][tileY], true);
		}
		else if(up && !down && !left && right) {
			arrow.state(boardState[tileX][tileY-1], true, true, boardState[tileX+1][tileY]);
		}
		else if(up && !down && left && !right) {
			arrow.state(boardState[tileX][tileY-1], true, boardState[tileX-1][tileY], true);
		}
		else if(up && down && !left && !right) {
			arrow.state(boardState[tileX][tileY-1], boardState[tileX][tileY+1], true, true);
		}
		else if(!up && !down && !left && right) {
			arrow.state(true, true, true, boardState[tileX+1][tileY]);
		}
		else if(!up && !down && left && !right) {
			arrow.state(true, true, boardState[tileX-1][tileY], true);
		}
		else if(!up && down && !left && !right) {
			arrow.state(true, boardState[tileX][tileY+1], true, true);
		}
		else if(up && !down && !left && !right) {
			arrow.state(boardState[tileX][tileY-1], true, true, true);
		}
		else if(!up && !down && !left && !right) {
			arrow.state(true, true, true, true);
		}
	}
	
	private boolean gameClear() {
		for(int row = 0; row < blockRow; row++) {
			for(int col = 0; col < blockColumn; col++) {
				if(!boardState[row][col])
					return false;
			}
		}
		return true;
	}
	
	public static void setGame(String level, int stages) throws SlickException {
		path = "data/Stage/" + level + ".tmx";
		line_path = "data/sprite/line_" + level + ".png";
		stage = stages;
	}
}