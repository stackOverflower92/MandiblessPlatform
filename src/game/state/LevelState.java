package game.state;
 
import game.Game;
import game.character.Player;
import game.controller.MouseAndKeyBoardPlayerController;
import game.controller.PlayerController;
import game.enums.GameState;
import game.gui.hud.InGameHud;
import game.level.Level;
import game.physics.Physics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
 
public class LevelState extends BasicGameState {
    private Level level;
    private String startinglevel;
    private Player player;
    private PlayerController playerController;
    private Physics physics;
    private InGameHud hud;
    private int levelId;
    
    public LevelState(String startingLevel, int levelId){
        this.startinglevel = startingLevel;
        this.levelId = levelId;
    }
 
    public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
        //once we initialize our level, we want to load the right level
        level = new Level(startinglevel);
        
        // Check for player spawn position and add it to the level
        player = new Player(
        		getSpawnPointFromTiles(level.getMap()).getX(),
        		getSpawnPointFromTiles(level.getMap()).getY()
        		);
        level.setPlayer(player);
        
        // Initialize controllers
        playerController = new MouseAndKeyBoardPlayerController(player);
        
        // Initialize physics
        physics = new Physics();
        
        // Initialize interface
        hud = new InGameHud(player);
    }
    
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
    	// Update everything
    	playerController.handleInput(container.getInput(), delta);
    	physics.handlePhysics(level, delta);
    	hud.update(container, sbg);
    }
 
    public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
    	// Render current level
        g.scale(Game.SCALE, Game.SCALE);
        level.render(g);
        
        // Render HUD and Graphics last (but not least)
        hud.render(g);
    }
 
    //this method is overridden from basicgamestate and will trigger once you press any key on your keyboard
    public void keyPressed(int key, char code){
        // Check for pause
        if (key == Input.KEY_ESCAPE) {
        	if (Game.GAME_STATE == GameState.PAUSED)
        		Game.GAME_STATE = GameState.RUNNING;
        	else
        		Game.GAME_STATE = GameState.PAUSED;
        }
    }
 
    private  Vector2f getSpawnPointFromTiles(TiledMap map) {
    	int objGroupsCount = map.getObjectGroupCount();
    	
    	for (int gi = 0; gi < objGroupsCount; gi++) {
    		int objCount = map.getObjectCount(gi);
    		
    		for (int oi = 0; oi < objCount; oi++) {
    			if (map.getObjectName(gi, oi).equals(StaticResources.PLAYER_NAME)) {
    				return new Vector2f(
    							map.getObjectX(gi, oi),
    							map.getObjectY(gi, oi)
    						);
    			}
    		}
    	}
    	
    	return new Vector2f(0);
    }
    
    public int getID() {
        //this is the id for changing states
        return levelId;
    }
}