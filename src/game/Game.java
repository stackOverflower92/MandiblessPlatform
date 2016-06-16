package game;
 
import game.enums.GameState;
import game.enums.LevelId;
import game.state.LevelState;
import game.state.MainMenuState;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
 
public class Game extends StateBasedGame {
	// General game status (paused, resumed, etc)
	public static GameState     GAME_STATE    = GameState.RUNNING;
	
    //set the window width and then the height according to a aspect ratio
	// TODO: Pay attention to this stuff, change it to make it comfortable for the user
	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static Dimension screenSize = toolkit.getScreenSize();
	
    public static final int     WINDOW_WIDTH  = (int)screenSize.getWidth();
    public static final int     WINDOW_HEIGTH = WINDOW_WIDTH / 16 * 9;
    public static final boolean FULLSCREEN    = true;
 
    //1280x720 is our base, we use 32x32 tiles but we want it to be 40x40 at 1280x720
    //so our base scale is not 1 but 1.25 actually
    public static final float   SCALE          = (float) (1.25*((double)WINDOW_WIDTH/1280));
    public static final String  GAME_NAME      = "What Matters";
 
    public Game() {
        super(GAME_NAME);
    }
 
    public void initStatesList(GameContainer gc) throws SlickException {
        //create a level state, this state will do the whole logic and rendering for individual levels
    	addState(new MainMenuState());
        addState(new LevelState("level_0", LevelId.LAB));
        this.enterState(LevelId.MAIN_MENU, new FadeInTransition(), new EmptyTransition());
    }
 
    public static void main(String[] args) throws SlickException {
    	// Init new game container
         AppGameContainer app = new AppGameContainer(new Game());
 
         //set the size of the display to the width and height and fullscreen or not
         app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGTH, FULLSCREEN);
         
         //this will attempt to create a frame rate of approximately 60 frames per second
         app.setTargetFrameRate(60);
         app.setShowFPS(false);
         
         // Start game
         app.start();
    }
}