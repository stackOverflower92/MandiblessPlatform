import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MandiblessGame extends BasicGame {
	private Image testImage;
	
	public MandiblessGame(String title) {
		super(title);
	}
	
	 public void init(GameContainer arg0) throws SlickException {
        testImage = new Image("data/img/test_image.png");
    }
 
    public void update(GameContainer arg0, int arg1) throws SlickException {
 
    }
 
    public void render(GameContainer arg0, Graphics arg1) throws SlickException {
        testImage.draw(200, 50);
    }
 
    public static void main(String args[]) throws SlickException {
        AppGameContainer app = new AppGameContainer(new MandiblessGame("Setup Test"));
 
        app.setDisplayMode(400, 100, false);
        app.setShowFPS(false);
 
        app.start();
    }
}
