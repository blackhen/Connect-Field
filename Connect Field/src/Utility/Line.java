package Utility;

import org.newdawn.slick.SpriteSheet;

public class Line {
	
	private SpriteSheet sprite;
	private String firstKey;
	private String secondKey;
	private boolean start;
	
	public Line(SpriteSheet img) {
		sprite = img;
		firstKey = "";
		secondKey = "";
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public void addKey(String key) {
		if(start) 
			secondKey = key;
		else if(firstKey.equals("") && secondKey.equals("")) 
			firstKey = key;
		else if(!firstKey.equals("") && secondKey.equals("")) 
			secondKey = key;
	}
	
	public void delKey() {
		if(start) 
			secondKey = "";
		else if(!firstKey.equals("") && !secondKey.equals("")) 
			secondKey = "";
		else if(!firstKey.equals("") && secondKey.equals("")) 
			firstKey = "";
	}
	
	public void draw(int x, int y, int width, int height) {
		if(start) {
			if(secondKey.equals("")) 
				sprite.getSprite(1, 0).draw(x, y, width, height);
			else if(secondKey.equals("up")) 
				sprite.getSprite(2, 0).draw(x, y, width, height);
			else if(secondKey.equals("down")) 
				sprite.getSprite(3, 0).draw(x, y, width, height);
			else if(secondKey.equals("right")) 
				sprite.getSprite(4, 0).draw(x, y, width, height);
			else if(secondKey.equals("left")) 
				sprite.getSprite(5, 0).draw(x, y, width, height);
		}
		else if(!start) {
			if(firstKey.equals("") && secondKey.equals("")) 
				sprite.getSprite(0, 0).draw(x, y, width, height);
			else if(firstKey.equals("up") && secondKey.equals("")) 
				sprite.getSprite(3, 0).draw(x, y, width, height);
			else if(firstKey.equals("down") && secondKey.equals("")) 
				sprite.getSprite(2, 0).draw(x, y, width, height);
			else if(firstKey.equals("left") && secondKey.equals("")) 
				sprite.getSprite(4, 0).draw(x, y, width, height);
			else if(firstKey.equals("right") && secondKey.equals("")) 
				sprite.getSprite(5, 0).draw(x, y, width, height);
			else if((firstKey.equals("up") && secondKey.equals("up")) || 
					(firstKey.equals("down") && secondKey.equals("down"))) 
				sprite.getSprite(6, 0).draw(x, y, width, height);
			else if((firstKey.equals("up") && secondKey.equals("left")) || 
					(firstKey.equals("right") && secondKey.equals("down"))) 
				sprite.getSprite(11, 0).draw(x, y, width, height);
			
			else if((firstKey.equals("up") && secondKey.equals("right")) ||
					(firstKey.equals("left") && secondKey.equals("down"))) 
				sprite.getSprite(10, 0).draw(x, y, width, height);
			
			else if((firstKey.equals("down") && secondKey.equals("left")) || 
					(firstKey.equals("right") && secondKey.equals("up"))) 
				sprite.getSprite(9, 0).draw(x, y, width, height);
			
			else if((firstKey.equals("down") && secondKey.equals("right")) || 
					(firstKey.equals("left") && secondKey.equals("up"))) 
				sprite.getSprite(8, 0).draw(x, y, width, height);
			
			else if((firstKey.equals("left") && secondKey.equals("left")) || 
					(firstKey.equals("right") && secondKey.equals("right"))) 
				sprite.getSprite(7, 0).draw(x, y, width, height);
		}
			
	}
	
	
}
