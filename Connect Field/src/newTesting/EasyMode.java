package newTesting;

import java.util.Stack;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class EasyMode extends BasicGameState {
	
	TiledMap blockMap;
	Image focus;
	
	int posX = 0;
	int posY = 0;
	int score = 1;
	
	boolean[][] board = new boolean[10][10];
	boolean isFirst = true;
	boolean turnBack = false;
	
	String prevKey = "";
	String currentKey = "";
	String cc;
	String test = "";
	
	Stack<String> keyList = new Stack<String>();
	
	public EasyMode(int state) {
	
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		blockMap = new TiledMap("data/tilemap/TileMapForTest.tmx");
		focus = new Image("data/sprite/current.png");
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				board[i][j] = false;
			}
		}
		board[0][0] = true;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		blockMap.render(0,  0);
		focus.draw(posX, posY);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		int tileX = posX / 80;
		int tileY = posY / 60;
		int tileID = blockMap.getTileId(tileX, tileY, 0) + 16;
		String propertyLeft = blockMap.getTileProperty(blockMap.getTileId(tileX, tileY, 0), "blockLeft", "false");
		String propertyRight = blockMap.getTileProperty(blockMap.getTileId(tileX, tileY, 0), "blockRight", "false");
		String propertyTop = blockMap.getTileProperty(blockMap.getTileId(tileX, tileY, 0), "blockTop", "false");
		String propertyDown = blockMap.getTileProperty(blockMap.getTileId(tileX, tileY, 0), "blockDown", "false");
		
		if(tileID > 32) {
			tileID -= 16;
		}
		System.out.println("tileX: " + tileX + " tile Y:" + tileY + " tileID: " + tileID);
		
		//check key up
		if(input.isKeyPressed(input.KEY_UP) && propertyTop.equals("false")) {
			posY -= 60;
			if(posY < 0) {
				posY = 0;
			}
			
			if(isFirst) {
				currentKey = "up";
				isFirst = false;
			}
			else {
				prevKey = currentKey;
				currentKey = "up";
			}
			//update value
			tileX = posX / 80;
			tileY = posY / 60;
			
			if(board[tileX][tileY]) {
				if(prevKey.equals("down") && !turnBack) {
					board[tileX][tileY + 1] = false;
					turnBack = true;
					keyList.pop();
				}
				else if(!keyList.isEmpty() && keyList.peek().equals("down")) {
					board[tileX][tileY + 1] = false;
					keyList.pop();
				}
				else {
					board[tileX][tileY] = false;
					keyList.push("up");
				}
			}
			else {
					keyList.push("up");
					board[tileX][tileY] = true;
					turnBack = false;
			}
			
		}
		
		//check key down
		else if(input.isKeyPressed(input.KEY_DOWN) && propertyDown.equals("false")) {
			posY += 60;
			if(posY > 540) {
				posY = 540;
			}
			
			if(isFirst) {
				currentKey = "down";
				isFirst = false;
			}
			else {
				prevKey = currentKey;
				currentKey = "down";
			}
			//update value
			tileX = posX / 80;
			tileY = posY / 60;
			
			if(board[tileX][tileY]) {
				if(prevKey.equals("up") && !turnBack) {
					board[tileX][tileY - 1] = false;
					turnBack = true;
					keyList.pop();
				}
				else if(!keyList.isEmpty() && keyList.peek().equals("up")) {
					board[tileX][tileY - 1] = false;
					keyList.pop();
				}
				else {
					board[tileX][tileY] = false;
					keyList.push("down");
				}
			}
			else {
				keyList.push("down");
				board[tileX][tileY] = true;
				turnBack = false;
			}
			
		}
		
		//check key left
		else if(input.isKeyPressed(input.KEY_LEFT) && propertyLeft.equals("false")) {
			posX -= 80;
			if(posX < 0) {
				posX = 0;
			}
			
			if(isFirst) {
				currentKey = "left";
				isFirst = false;
			}
			else {
				prevKey = currentKey;
				currentKey = "left";
			}
			//update value
			tileX = posX / 80;
			tileY = posY / 60;
			
			if(board[tileX][tileY]) {
				if(prevKey.equals("right")  && !turnBack) {
					board[tileX + 1][tileY] = false;
					turnBack = true;
					keyList.pop();	
				}
				else if(!keyList.isEmpty() && keyList.peek().equals("right") && turnBack) {
					board[tileX + 1][tileY] = false;
					keyList.pop();
				}
				else {
					board[tileX][tileY] = false;
					keyList.push("left");
				}
			}
			else {
				keyList.push("left");
				board[tileX][tileY] = true;
				turnBack = false;
			}
		}
		
		//check key right
		else if(input.isKeyPressed(input.KEY_RIGHT) && propertyRight.equals("false")) {
			posX += 80;
			if(posX > 720) {
				posX = 720;
			}
			
			if(isFirst) {
				currentKey = "right";
				isFirst = false;
			}
			else {
				prevKey = currentKey;
				currentKey = "right";
			}
			//update value
			tileX = posX / 80;
			tileY = posY / 60;
			
			if(board[tileX][tileY]) {
				if(prevKey.equals("left") && !turnBack) {
					board[tileX - 1][tileY] = false;
					turnBack = true;
					keyList.pop();
				}
				else if(!keyList.isEmpty() && keyList.peek().equals("left") && turnBack) {
					board[tileX - 1][tileY] = false;
					keyList.pop();
				}
				else {
					board[tileX][tileY] = false;
					keyList.push("right");
				}
			}
			else {
				keyList.push("right");
				board[tileX][tileY] = true;
				turnBack = false;
			}
		}

		//update all block
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				int id = blockMap.getTileId(j, i, 0);
				if(board[j][i]) {
					id += 16;
					if(id > 32)
						id -= 16;
				}
				else {
					id -= 16;
					if(id < 1)
						id += 16;
				}
				blockMap.setTileId(j, i, 0, id);
				//System.out.print(board[j][i] + " ");
			}
			//System.out.println();
		}
		
		if(!keyList.isEmpty())
			cc = keyList.peek();
		System.out.println("previous key is " + prevKey + ", current key is " + currentKey + " turn back is " + turnBack + ", current stack is " + cc + test);
		//System.out.println("Left: " + propertyLeft + ", Right:" + propertyRight + ", Top: " + propertyTop + ", Down: " + propertyDown);
		
	}
	
	public int getID() {
		return 0;
	}
}
