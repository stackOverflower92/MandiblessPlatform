package game.gui.hud;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.character.Player;
import game.enums.GameState;
import game.enums.PauseMenuAction;
import game.Game;
import game.character.Character;

public class InGameHud {
	// HUD sizes and margins
	private float hpIndicatorMarginTop = 20f;
	private float hpIndicatorMarginLeft = 20f;
	private Font font;
	private TrueTypeFont ttFont;
	
	private static String LIFE_COUNTER_NAME = "HP: ";
	private Player player;
	
	// Pause menu settings
	private PauseMenuChoice choices[] = {
			new PauseMenuChoice("Resume Game",       PauseMenuAction.RESUME),
			new PauseMenuChoice("Exit to Windows",   PauseMenuAction.EXIT),
			new PauseMenuChoice("Save Game",         PauseMenuAction.SAVE),
			new PauseMenuChoice("Load Game",         PauseMenuAction.LOAD),
			new PauseMenuChoice("Exit to Main Menu", PauseMenuAction.MAIN_MENU)
	};
	private Rectangle bgRect = new Rectangle(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGTH);
	private ShapeFill bgRectFill = new GradientFill(0, Game.WINDOW_HEIGTH, Color.black, Game.WINDOW_WIDTH, Game.WINDOW_HEIGTH, new Color(0, 0, 0, 0.4f), true);
	private int currentChoice = 0;
	private float topMenuMargin = 150f;
	private float bottomMenuMargin = 70f;
	private Color currentChoiceColor = Color.white;
	
	public InGameHud(Player player) {
		this.player = player;
		font = new Font("Orbitron", Font.BOLD, 20);
		ttFont = new TrueTypeFont(font, false);
	}
	
	public void update(GameContainer container, StateBasedGame sbg) {
		Input input = container.getInput();
		
		// Check for these controls only if game is paused, otherwise they could be in conflict with other controls
		if (Game.GAME_STATE == GameState.PAUSED) {
			if (input.isKeyPressed(Input.KEY_UP)) {
				if (currentChoice == 0)
					currentChoice = choices.length - 1;
				else
					currentChoice--;
			}
			
			if (input.isKeyPressed(Input.KEY_DOWN)) {
				if (currentChoice == choices.length - 1)
					currentChoice = 0;
				else currentChoice++;
			}
			
			if (input.isKeyPressed(Input.KEY_ENTER)) {
				switch (choices[currentChoice].getAction()) {
				case EXIT:
					container.exit();
					break;
					
				case RESUME:
					Game.GAME_STATE = GameState.RUNNING;
					break;
					
				case MAIN_MENU:
					sbg.enterState(0, new FadeOutTransition(), new FadeInTransition());
				default:
					break;
				}
			}
		}
	}
	
	public void render(Graphics graphics) {
		ttFont.drawString(hpIndicatorMarginLeft, hpIndicatorMarginTop, getLifeCounterName(), Color.white);
		
		drawPauseMenu(graphics);
	}
	
	private String getLifeCounterName() {
		return LIFE_COUNTER_NAME + ((Character)player).getLife() + "/" + ((Character)player).MAX_HP;
	}
	
	public void drawPauseMenu(Graphics graphics) {
		if (Game.GAME_STATE == GameState.PAUSED) {
			graphics.drawRect(bgRect.getX(), bgRect.getY(), bgRect.getWidth(), bgRect.getHeight());
			graphics.fill(bgRect, bgRectFill);
			
			drawChoices();
		}
	}
	
	private void drawChoices() {
		float availableVArea = Game.WINDOW_HEIGTH - topMenuMargin - bottomMenuMargin;
		float menuChoiceStringHeight = (float)ttFont.getHeight("Test");
		float occupiedVArea = availableVArea - choices.length * menuChoiceStringHeight;
		float availableSpacingArea = availableVArea - occupiedVArea;
		float singleStringSpacing = (float)(availableSpacingArea / choices.length);
		
		for (int i = 0; i < choices.length; i++) {
			// Add bottom spacing only if choice is not last one
			if (i == currentChoice) {
				currentChoiceColor = Color.cyan;
			} else {
				currentChoiceColor = Color.white;
			}
			
			ttFont.drawString(
					(Game.WINDOW_WIDTH / 2) - (ttFont.getWidth(choices[i].getTitle()) / 2),
					topMenuMargin + ((ttFont.getHeight(choices[i].getTitle()) + singleStringSpacing) * i), 
					choices[i].getTitle(),
					currentChoiceColor
			);
		}
	}
}
