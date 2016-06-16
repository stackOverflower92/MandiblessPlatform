package game.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.enums.LevelId;
import game.gui.hud.MainMenuHud;

public class MainMenuState extends BasicGameState {
	private MainMenuHud hud;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		hud = new MainMenuHud();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		hud.render(g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		hud.update(gc, sbg);
	}

	public int getID() {
		return LevelId.MAIN_MENU;
	}
}
