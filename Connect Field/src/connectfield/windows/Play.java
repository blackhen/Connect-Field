package connectfield.windows;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState {
	TiledMap map_ice;
	private Animation sprite_p, ani_p_up, ani_p_down, ani_p_left, ani_p_right;
	private float x = 0f, y = 0f;
	 /** The collision map indicating which tiles block movement - generated based on tile properties */
    private boolean[][] blocked;
     
	public Play() {
		
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image [] movementUp = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        Image [] movementDown = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        Image [] movementLeft = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        Image [] movementRight = {new Image("data/sprite/KA_Ball.png"), new Image("data/sprite/KA_Ball.png")};
        int [] duration = {300, 300};
        map_ice = new TiledMap("data/tilemap/test.tmx");

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
        sprite_p.draw((int)x, (int)y, 34f, 34f);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_UP))
        {
            
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            
        }
        else if (input.isKeyDown(Input.KEY_LEFT))
        {
            
        }
        else if (input.isKeyDown(Input.KEY_RIGHT))
        {
            
        }
	}

	public int getID() {
		return 0;
	}
}
