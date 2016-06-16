package game.character;
 
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import game.enums.Facing;
import game.physics.AABoundingRect;
import game.state.StaticResources;
 
public class Player extends Character {
    public Player(float x, float y) throws SlickException{	
    	super(x, y, StaticResources.PLAYER_NAME);
    	
        setSprite(new Image("data/img/characters/player/player.png"));
 
        setMovingAnimation(new Image[]{new Image("data/img/characters/player/player_1.png"),new Image("data/img/characters/player/player_2.png"),
                                       new Image("data/img/characters/player/player_3.png"),new Image("data/img/characters/player/player_4.png")}
                                       ,125);
        boundingShape = new AABoundingRect(x+3, y, 26, 32);
 
        accelerationSpeed = 0.001f;
        maximumSpeed = 0.15f;
        maximumFallSpeed = 0.3f;
        decelerationSpeed = 0.001f;
    }
    
    public void updateBoundingShape(){
        boundingShape.updatePosition(x+3,y);
    }
}
