package game;

import java.awt.Color;
import java.io.Serializable;

public abstract class Entity implements Serializable {
	
	private static final long serialVersionUID = -2606089500793536196L;
	public int x;
	public int y;
	public Color color;
	
	public Entity(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
}
