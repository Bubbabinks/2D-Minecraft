package game;

import java.awt.Color;

public class Block {
	
	protected int x, y;
	protected Color color;
	
	//This class is used only for rendering
	public Block(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
}
