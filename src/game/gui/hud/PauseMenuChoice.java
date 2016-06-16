package game.gui.hud;

import game.enums.PauseMenuAction;

public class PauseMenuChoice {
	private String title;
	private PauseMenuAction action;
	
	public PauseMenuChoice(String title, PauseMenuAction action) {
		this.title = title;
		this.action = action;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PauseMenuAction getAction() {
		return action;
	}

	public void setAction(PauseMenuAction action) {
		this.action = action;
	}
}
