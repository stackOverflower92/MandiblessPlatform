package game.gui.hud;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.Game;
import game.enums.LevelId;
import game.enums.MainMenuAction;

public class MainMenuHud {
	// Graphic stuff
	private Image background;
	
	// Drawing stuff
	private Rectangle backgroundRect = new Rectangle(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGTH);
	private ShapeFill backgroundRectFill = new GradientFill(0, Game.WINDOW_HEIGTH, Color.black, Game.WINDOW_WIDTH, Game.WINDOW_HEIGTH, new Color(0, 0, 0, 0.4f), true);
	private float topMargin = Game.WINDOW_HEIGTH / 3;
	private float bottomMargin = 20f;
	private TrueTypeFont ttFont;
	private TrueTypeFont ttFontTitle;
	private int currentChoice = 0;
	private Color currentChoiceColor = Color.white;
	private String gameLogo = "WHAT MATTERS";
	private MainMenuChoice choices[] = {
		new MainMenuChoice("Continue",  MainMenuAction.CONTINUE_GAME, true),
		new MainMenuChoice("New Game",  MainMenuAction.NEW_GAME,      true),
		new MainMenuChoice("Load Game", MainMenuAction.LOAD_GAME,     true),
		new MainMenuChoice("Options",   MainMenuAction.OPTIONS,       true),
		new MainMenuChoice("Credits",   MainMenuAction.CREDITS,       true),
		new MainMenuChoice("Exit",      MainMenuAction.EXIT,          true)
	};
	
	public MainMenuHud() {
		// Init font
		ttFont = new TrueTypeFont(new Font("Orbitron", Font.BOLD, 20), false);
		ttFontTitle = new TrueTypeFont(new Font("Orbitron", Font.BOLD, 70), false);
		
		try {
			background = new Image("/data/img/backgrounds/menu.jpg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void update(GameContainer container, StateBasedGame sbg) {
		Input input = container.getInput();
		
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
				
			case NEW_GAME:
				// TODO: Add state IDs handling
				sbg.enterState(LevelId.LAB, new FadeOutTransition(), new FadeInTransition());
				break;
				
			default:
				break;
			}
		}
	}
	
	public void render(Graphics graphics) {
		// Render background
		graphics.drawImage(background, 0f, 0f);
		graphics.drawImage(background, 0f, 0f, Game.WINDOW_WIDTH, Game.WINDOW_HEIGTH, 0f, 0f, background.getWidth(), background.getHeight());
		
		// Render gradient background
		graphics.fill(backgroundRect, backgroundRectFill);
		
		// Render Menu
		drawChoices();
	}
	
	public void drawChoices() {
		float availableVArea = Game.WINDOW_HEIGTH - topMargin - bottomMargin;
		float menuChoiceStringHeight = (float)ttFont.getHeight("Test");
		float occupiedVArea = availableVArea - choices.length * menuChoiceStringHeight;
		float availableSpacingArea = availableVArea - occupiedVArea;
		float singleStringSpacing = (float)(availableSpacingArea / choices.length);
		
		ttFontTitle.drawString((Game.WINDOW_WIDTH / 2) - ttFontTitle.getWidth(gameLogo) / 2, 10, gameLogo);
		
		for (int i = 0; i < choices.length; i++) {
			// Add bottom spacing only if choice is not last one
			if (i == currentChoice) {
				currentChoiceColor = Color.cyan;
			} else {
				currentChoiceColor = Color.white;
			}
			
			ttFont.drawString(
					(Game.WINDOW_WIDTH / 2) - (ttFont.getWidth(choices[i].getTitle()) / 2),
					topMargin + ((ttFont.getHeight(choices[i].getTitle()) + singleStringSpacing) * i), 
					choices[i].getTitle(),
					currentChoiceColor
			);
		}
	}
}
