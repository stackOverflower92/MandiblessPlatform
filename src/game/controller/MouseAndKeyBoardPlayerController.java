package game.controller;
 
import game.character.Player;
import game.enums.ClimbDirection;

import org.newdawn.slick.Input;
 
public class MouseAndKeyBoardPlayerController extends PlayerController {
 
    public MouseAndKeyBoardPlayerController(Player player) {
        super(player);
    }
 
    public void handleInput(Input i, int delta) {
        //handle any input from the keyboard and joypad
        handleKeyboardInput(i, delta);
        handleJoypadInput(i, delta);
    }
 
    private void handleKeyboardInput(Input i, int delta){
    	//we can both use the WASD or arrow keys to move around, obviously we can't move both left and right simultaneously
        if (i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
            player.moveLeft(delta);
        } else if (i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
            player.moveRight(delta);
        } else if (i.isKeyDown(Input.KEY_UP) || i.isKeyDown(Input.KEY_W)) {
        	player.climb(delta, ClimbDirection.UP);
        } else if (i.isKeyDown(Input.KEY_DOWN) || i.isKeyDown(Input.KEY_S)) {
        	player.climb(delta, ClimbDirection.DOWN);
        } else {
            //we don't move if we don't press left or right, this will have the effect that our player decelerates
            player.setMoving(false);
        }
        
        if (i.isKeyDown(Input.KEY_SPACE)) {
            player.jump();
        }
    }
    
    private void handleJoypadInput(Input i, int delta) {
    	if (i.getControllerCount() == 1) {
    		if (i.isControllerLeft(0)) {
        		player.moveLeft(delta);
        	} else if (i.isControllerRight(0)) {
        		player.moveRight(delta);
        	} else if (i.isControllerDown(0)) {
        		player.climb(delta, -1);
        	} else if (i.isControllerUp(0)) {
        		player.climb(delta, 1);
        	} else {
        		player.setMoving(false);
        	}
        	
        	if (i.isButton1Pressed(0)) {
        		player.jump();
        	}
    	}
    }
}