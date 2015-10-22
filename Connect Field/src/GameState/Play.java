package GameState;

import Utility.Block;
import java.util.Stack;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState {
	
	private TiledMap map;
	private Image focus;
	private Animation slime;
	
	private int posX;
	private int posY;
	private int blockWidth;
	private int blockHeight;
	private int blockRow;
	private int blockColumn;
	private static int stage;
	
	private Block[][] blockBoard;
	private boolean[][] boardState;
	private boolean start;
	private Stack<String> keyList;
	
	private static String path = "data/Stage/Easy.tmx";
	private String prevKey = "";
	

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap(path);
		focus = new Image("data/sprite/KA_Ball.png");
		blockHeight = map.getTileHeight();
		blockWidth = map.getTileWidth();
		blockRow = map.getHeight();
		blockColumn = map.getWidth();
		posX = 0;
		posY = 0;
		blockBoard = new Block[blockWidth][blockHeight];
		boardState = new boolean[blockWidth][blockHeight];
		start = false;
		keyList = new Stack<String>();
		slime = new Animation(new SpriteSheet(new Image("data/sprite/downSlime.png"), 160, 120), 100);

		for(int row = 0; row < blockRow; row++) {
			for(int col = 0; col < blockColumn; col++) {
				int tileID = map.getTileId(col, row, stage);
				if(map.getTileProperty(tileID, "blocked", "false").equals("true")) 
					boardState[col][row] = true;
				else {
					Animation[] ani = {new Animation(new SpriteSheet(new Image("data/sprite/sBlueBlock.png"), 160, 120), 50), 
							new Animation(new SpriteSheet(new Image("data/sprite/BlueBlock.png"), 160, 120), 50)};
					blockBoard[col][row] = new Block(ani);
					boardState[col][row] = false;
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(int row = 0; row < blockRow; row++) {
			for(int col = 0; col < blockColumn; col++) {
				blockBoard[col][row].draw(col * blockWidth, row * blockHeight, blockWidth, blockHeight);
			}
		}
		map.render(0, 0, stage);
		slime.draw(posX, posY, blockWidth, blockHeight);
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
		
		if(!keyList.isEmpty()) prevKey = keyList.peek();
		
		//-------select start block--------//
		if(!start) {
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
			if(input.isKeyPressed(Input.KEY_ENTER)) {
				boardState[tileX][tileY] = true;
				blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
				start = true;
			}
		}
		//----------end select start block-----------//
		
		//----------play mode--------------//
		else if(start) {
			if(input.isKeyPressed(Input.KEY_UP)) {
				if(up) {
					posY -= blockHeight;
					tileY = posY / blockHeight;
					if(!boardState[tileX][tileY]) {
						boardState[tileX][tileY] = true;
						keyList.push("up");
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("down")) {
							boardState[tileX][tileY + 1] = false;
							keyList.pop();
							blockBoard[tileX][tileY + 1].setState(boardState[tileX][tileY + 1]);
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
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("up")) {
							boardState[tileX][tileY - 1] = false;
							blockBoard[tileX][tileY - 1].setState(boardState[tileX][tileY - 1]);
							keyList.pop();
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
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("right")) {
							boardState[tileX + 1][tileY] = false;
							blockBoard[tileX + 1][tileY].setState(boardState[tileX + 1][tileY]);
							keyList.pop();
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
						blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
					}
					else if(boardState[tileX][tileY]) {
						if(prevKey.equals("left")) {
							boardState[tileX - 1][tileY] = false;
							keyList.pop();
							blockBoard[tileX - 1][tileY].setState(boardState[tileX - 1][tileY]);
						}
						else
							posX -= blockWidth;
					}
				}
			}
			else if(input.isKeyPressed(Input.KEY_Z)) {
				if(keyList.isEmpty()) {
					boardState[tileX][tileY] = false;
					start = false;
				}
				else if(prevKey.equals("up")) {
					posY += blockHeight;
					boardState[tileX][tileY] = false;
					keyList.pop();
				}
				else if(prevKey.equals("down")) {
					posY -= blockHeight;
					boardState[tileX][tileY] = false;
					keyList.pop();
				}
				else if(prevKey.equals("left")) {
					posX += blockWidth;
					boardState[tileX][tileY] = false;
					keyList.pop();
				}
				else if(prevKey.equals("right")) {
					posX -= blockWidth;
					boardState[tileX][tileY] = false;
					keyList.pop();
				}
				blockBoard[tileX][tileY].setState(boardState[tileX][tileY]);
			}
		}
		//---------------end play----------------//
		
		if(input.isKeyPressed(Input.KEY_1)) {
			sbg.enterState(Main.gameStage);
		}
		input.clearKeyPressedRecord();
	}

	@Override
	public int getID() {
		return 3;
	}
	
	
	public static void setGame(String level, int stages) throws SlickException {
		path = "data/Stage/" + level + ".tmx";
		stage = stages;
	}
}