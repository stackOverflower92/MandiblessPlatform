package game.dialogs;

import java.util.ArrayList;

public class Dialog {
	private int id;
	private ArrayList<DialogNode> nodes;
	
	public Dialog() {
		nodes = new ArrayList<DialogNode>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<DialogNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<DialogNode> nodes) {
		this.nodes = nodes;
	}
}