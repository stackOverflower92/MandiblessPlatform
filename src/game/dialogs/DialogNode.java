package game.dialogs;

import java.util.ArrayList;

import game.enums.NodeType;

public class DialogNode {
	private int id;
	private int nextId;
	private NodeType type;
	private String speaker;
	private String text;
	private ArrayList<DialogAnswer> answers;
	
	public DialogNode() {
		answers = new ArrayList<DialogAnswer>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<DialogAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<DialogAnswer> answers) {
		this.answers = answers;
	}
}
