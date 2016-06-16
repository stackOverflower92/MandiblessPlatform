package game.character;
 
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import game.Game;
import game.enums.Facing;
import game.enums.GameState;
import game.level.LevelObject;
import game.state.StaticResources;
 
public abstract class Character extends LevelObject {
	public static int 				    MAX_HP = 100;
	public static int 				    MIN_HP = 0;
	
    protected HashMap<Facing,Image>     sprites;
    protected HashMap<Facing,Animation> movingAnimations;
    protected Facing                    facing;
    protected boolean                   moving = false;

    protected float                     accelerationSpeed = 1;
    protected float                     decelerationSpeed = 1;
    protected float                     maximumSpeed = 1;
    protected float                     jumpSpeed = -0.4f;
    protected float                     climbSpeed = -0.2f;
    protected boolean   				canClimb = false;
    protected boolean					isClimbing = false;
    protected int 						hp = MAX_HP;
    protected boolean					dead = false;
    protected String 					name = "";
    protected boolean					isInFrontOfDoor = false;
 
    public Character(float x, float y, String name) throws SlickException {
        super(x, y);
        
        //in case we forget to set the image, we don't want the game to crash, but it still has to be obvious that something was forgotten
        setSprite(new Image("data/img/placeholder_sprite.png"));
        
        // By default, out character will be facing right
        facing = Facing.RIGHT;
        
        // Set name for this character
        this.name = name;
    }
 
    public void applyGravity(boolean apply, float gravity, int delta) {
    	if (!apply)
    		y_velocity = -gravity * delta;
    }
    
    //move towards x_velocity = 0
    public void decelerate(int delta) {
        if (x_velocity > 0) {
            x_velocity -= decelerationSpeed * delta;
            if(x_velocity < 0)
                x_velocity = 0;
        } else if (x_velocity < 0) {
            x_velocity += decelerationSpeed * delta;
            if (x_velocity > 0)
                x_velocity = 0;
        }
    }
 
    public void incrementLife(int gainHp) {
    	hp += gainHp;
    	
    	if (hp > MAX_HP)
    		hp = MAX_HP;
    }
    
    public void decrementLife(int looseHp) {
    	hp -= looseHp;
    	
    	if (hp <= MIN_HP) {
    		hp = MIN_HP;
    		dead = true;
    	}
    }
    
    public int getLife() {
    	return hp;
    }
    
    public boolean canClimb() {
    	return canClimb;
    }
    
    public void allowClimbing(boolean canClimb) {
    	this.canClimb = canClimb;
    }
    
    public boolean isMoving(){
        return moving;
    }
 
    public void setMoving(boolean b){
        moving = b;
    }
    
    public void jump() {
    	if (Game.GAME_STATE == GameState.RUNNING) {
    		if (onGround)
                y_velocity = jumpSpeed;
    	}
    }
    
    public void climb(int delta, int direction) {
    	if (Game.GAME_STATE == GameState.RUNNING) {
    		// Check direction
        	if (direction > 1)
        		direction = 1;
        	if (direction < -1)
        		direction = -1;
        	
        	if (canClimb) {
        		y_velocity = climbSpeed * direction;
            	
            	moving = true;
            	facing = Facing.LEFT;
            	isClimbing = true;
        	}
    	}
    }
    
    public void moveLeft(int delta){
    	if (Game.GAME_STATE == GameState.RUNNING) {
    		//if we aren't already moving at maximum speed
            if(x_velocity > -maximumSpeed){
                //accelerate
                x_velocity -= accelerationSpeed*delta;
                if(x_velocity < -maximumSpeed){
                    //and if we exceed maximum speed, set it to maximum speed
                    x_velocity = -maximumSpeed;
                }
            }
            moving = true;
            facing = Facing.LEFT;
    	}
    }
 
    public void moveRight(int delta){
    	if (Game.GAME_STATE == GameState.RUNNING) {
    		if(x_velocity < maximumSpeed){
                x_velocity += accelerationSpeed*delta;
                if(x_velocity > maximumSpeed){
                    x_velocity = maximumSpeed;
                }
            }
            moving = true;
            facing = Facing.RIGHT;
    	}
    }
    
    
    public void render(float offset_x, float offset_y){
    	 //draw a moving animation if we have one and we moved within the last 150 milliseconds
        if (movingAnimations != null && moving){
            movingAnimations.get(facing).draw(x-2-offset_x,y-2-offset_y);                
        } else {            
            sprites.get(facing).draw(x-2-offset_x, y-2-offset_y);          
        }
    }
 
    protected void setSprite(Image i) {
        sprites = new HashMap<Facing,Image>();
        sprites.put(Facing.RIGHT, i);
        sprites.put(Facing.LEFT , i.getFlippedCopy(true, false));
    }
    
    protected void setMovingAnimation(Image[] images, int frameDuration) {
        movingAnimations = new HashMap<Facing,Animation>();
 
        //we can just put the right facing in with the default images
        movingAnimations.put(Facing.RIGHT, new Animation(images,frameDuration));
 
        Animation facingLeftAnimation = new Animation();
        
        for(Image i : images){
            facingLeftAnimation.addFrame(i.getFlippedCopy(true, false), frameDuration);
        }
        
        movingAnimations.put(Facing.LEFT, facingLeftAnimation);
    }
}