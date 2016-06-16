package game.gui.hud;

import game.enums.MainMenuAction;

public class MainMenuChoice {
	private String title;
	private MainMenuAction action;
	private boolean visible;
	
	public MainMenuChoice(String title, MainMenuAction action, boolean visible) {
		this.title = title;
		this.action = action;
		this.visible = visible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MainMenuAction getAction() {
		return action;
	}

	public void setAction(MainMenuAction action) {
		this.action = action;
	}
}
