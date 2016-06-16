package game.character;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class NPC extends Character {
	private boolean canTalk;
	private Rectangle interactionArea;
	
	public NPC(float x, float y, String name) throws SlickException {
		super(x, y, name);

		interactionArea = new Rectangle(super.x - 16, super.y, 32, 32);
	}

	public void updateBoundingShape(){
        boundingShape.updatePosition(x + 3, y);
        
        // TODO: Remove this from here, use it as separate method instead
        updateInteractionArea();
    }
	
	public void updateInteractionArea() {
		interactionArea.setX(super.x - 16);
		interactionArea.setY(super.y);
	}
}
