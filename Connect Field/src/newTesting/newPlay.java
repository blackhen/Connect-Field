package newTesting;

import java.util.Stack;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class newPlay extends BasicGameState {
	
	TiledMap blockMap;
	Image focus;
	
	private int posX;
	private int posY;
	private int blockWidth;
	private int blockHeight;
	private int blockRow;
	private int blockColumn;
	private int level;
	
	private boolean[][] board;
	private boolean start;
	
	private String path = "data/tilemap/";
	
	Stack<String> keyList = new Stack<String>();
	Stack<String> allKeyList = new Stack<String>();
	Stack<String> boolBoard = new Stack<String>();
	
	public newPlay(String fileName, int level) {
		this.path += fileName;
		this.level = level - 1;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		posX = 0;
		posY = 0;
		
		blockMap = new TiledMap(path);
		blockHeight = blockMap.getTileHeight();
		blockWidth = blockMap.getTileWidth();
		blockRow = blockMap.getHeight();
		blockColumn = blockMap.getWidth();
		
		board = new boolean[blockWidth][blockHeight];
		for(int row = 0; row < blockRow; row++) {
			for(int column = 0; column < blockColumn; column++) {
				board[row][column] = false;
			}
		}
		start = false;
		
		focus = new Image("data/sprite/focus.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		blockMap.render(0, 0, level);
		focus.draw(posX, posY, blockWidth, blockHeight);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		int tileX = posX / blockWidth;
		int tileY = posY / blockHeight;
		int tileId = blockMap.getTileId(tileX, tileY, level);
		
		//get property of current tile to prevent from key pressed
		String propLeft = blockMap.getTileProperty(tileId, "blockLeft", "false");
		String propRight = blockMap.getTileProperty(tileId, "blockRight", "false");
		String propTop = blockMap.getTileProperty(tileId, "blockTop", "false");
		String propDown = blockMap.getTileProperty(tileId, "blockDown", "false");
		
		if(!start) {
			if(input.isKeyPressed(input.KEY_UP)) {
				posY -= blockHeight;
				if(posY < 0)
					posY = 0;
			}
			else if(input.isKeyPressed(input.KEY_DOWN)) {
				posY += blockHeight;
				if(posY > blockHeight * (blockRow - 1))
					posY = blockHeight * (blockRow - 1);
			}
			else if(input.isKeyPressed(input.KEY_LEFT)) {
				posX -= blockWidth;
				if(posX < 0)
					posX = 0;
			}
			else if(input.isKeyPressed(input.KEY_RIGHT)) {
				posX += blockWidth;
				if(posX > blockWidth * (blockColumn - 1))
					posX = blockWidth * (blockColumn - 1);
			}
			else if(input.isKeyPressed(input.KEY_ENTER)) {
				board[tileX][tileY] = true;
				start = true;
			}
		}
		else {
			//check key up pressed
			if(input.isKeyPressed(input.KEY_UP) && propTop.equals("false")) {
				
				allKeyList.push("up");
				posY -= blockHeight;
				if(posY < 0)
					posY = 0;
	
				//update value of tile x and tile y
				tileY = posY / blockHeight;
				
				if(board[tileX][tileY]) {
					board[tileX][tileY] = false;
					boolBoard.push("false");
				}
				else {
					board[tileX][tileY] = true;
					boolBoard.push("true");
				}
			}
			
			//check key down pressed
			else if(input.isKeyPressed(input.KEY_DOWN) && propDown.equals("false")) {
				
				allKeyList.push("down");
				posY += blockHeight;
				if(posY > blockHeight * (blockRow - 1))
					posY = blockHeight * (blockRow - 1);
				
				//update value of tile x and tile y
				tileY = posY / blockHeight;
				
				if(board[tileX][tileY]) {
					board[tileX][tileY] = false;
					boolBoard.push("false");
				}
				else {
					board[tileX][tileY] = true;
					boolBoard.push("true");
				}
			}
			
			//check key left pressed
			else if(input.isKeyPressed(input.KEY_LEFT) && propLeft.equals("false")) {
				
				allKeyList.push("left");
				posX -= blockWidth;
				if(posX < 0)
					posX = 0;
				
				//update value of tile x and tile y
				tileX = posX / blockWidth;
				
				if(board[tileX][tileY]) {
					board[tileX][tileY] = false;
					boolBoard.push("false");
				}
				else {
					board[tileX][tileY] = true;
					boolBoard.push("true");
				}
			}
			
			//check key right pressed
			else if(input.isKeyPressed(input.KEY_RIGHT) && propRight.equals("false")) {
				
				allKeyList.push("right");
				posX += blockWidth;
				if(posX > blockWidth * (blockColumn - 1))
					posX = blockWidth * (blockColumn - 1);
				
				//update value of tile x and tile y
				tileX = posX / blockWidth;
				
				if(board[tileX][tileY]) {
					board[tileX][tileY] = false;
					boolBoard.push("false");
				}
				else {
					board[tileX][tileY] = true;
					boolBoard.push("true");
				}
			}
			
			//undo block
			else if(input.isKeyPressed(input.KEY_Z) && !allKeyList.isEmpty()) {
				String key = allKeyList.pop();
				String boolKey = boolBoard.pop();
				if(key.equals("up")) {
					posY += blockHeight;
					if(boolKey.equals("true"))
						board[tileX][tileY] = false;
					else
						board[tileX][tileY] = true;
				}
				else if(key.equals("down")) {
					posY -= blockHeight;
					if(boolKey.equals("true"))
						board[tileX][tileY] = false;
					else
						board[tileX][tileY] = true;
				}
				else if(key.equals("left")) {
					posX += blockWidth;
					if(boolKey.equals("true"))
						board[tileX][tileY] = false;
					else
						board[tileX][tileY] = true;
				}
				else if(key.equals("right")) {
					posX -= blockWidth;
					if(boolKey.equals("true"))
						board[tileX][tileY] = false;
					else
						board[tileX][tileY] = true;
				}
			}
			
			for(int row = 0; row < blockRow; row++) {
				for(int column = 0; column < blockColumn; column++) {
					int id = blockMap.getTileId(column, row, level);
					
					if(board[column][row]) {
						id += 16;
						if(id > 32)
							id -= 16;
					}
					else {
						id -= 16;
						if(id < 1) {
							id += 16;
						}
					}
					blockMap.setTileId(column, row, level, id);
				}
			}
		}
	}

	@Override
	public int getID() {
		return 2;
	}
	
}
