package connectfield.windows;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState {
	private TiledMap map_ice;
	private String tilein;
	private Animation sprite_p, ani_p_up, ani_p_down, ani_p_left, ani_p_right;
	private int x_p = 0, y_p = 0;
	 /** The collision map indicating which tiles block movement - generated based on tile properties */
    private boolean[][] blocked;
     
	public Play(String tile) {
		this.tilein = tile;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image [] movementUp = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        Image [] movementDown = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        Image [] movementLeft = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        Image [] movementRight = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        int [] duration = {300, 300};
        map_ice = new TiledMap(tilein);
         /*
         * false variable means do not auto update the animation.
         * By setting it to false animation will update only when
         * the user presses a key.
         */
        ani_p_up = new Animation(movementUp, duration, false);
        ani_p_down = new Animation(movementDown, duration, false);
        ani_p_left = new Animation(movementLeft, duration, false);
        ani_p_right = new Animation(movementRight, duration, false);

        // Original orientation of the sprite. It will look right.
        sprite_p = ani_p_right;

        // build a collision map based on tile properties in the TileD map
        blocked = new boolean[map_ice.getWidth()][map_ice.getHeight()];
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map_ice.render(0, 0);
        sprite_p.draw((int)x_p, (int)y_p, 60f, 60f);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_UP))
        {
        	sprite_p = ani_p_up;
        	y_p -= 60;
        	changetileid();
        	
        }
        else if (input.isKeyPressed(Input.KEY_DOWN))
        {
        	sprite_p = ani_p_down;
        	y_p += 60;
        	changetileid();
        	
        	
        }
        else if (input.isKeyPressed(Input.KEY_LEFT))
        {
        	sprite_p = ani_p_left;
        	x_p -= 60;
        	changetileid();
        	
        	
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT))
        {
        	sprite_p = ani_p_right;
        	x_p += 60;
        	changetileid();
        	
        	
        }
	}

	
	private void changetileid() {
		if(map_ice.getTileId(x_p/60, y_p/60, 0) == 2){
    		map_ice.setTileId(x_p/60, y_p/60, 0, 13);
    	}
    	else{
    		map_ice.setTileId(x_p/60, y_p/60, 0, 2);
    	}
	}

	public int getID() {
		return 0;
	}
	
	private void pl(String inp){
		System.out.println(inp);
	}
	private void pl(int inp) {
		System.out.println(inp);
	}

}
