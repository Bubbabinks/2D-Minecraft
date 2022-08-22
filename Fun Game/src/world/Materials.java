package world;

import java.awt.Color;
import java.io.Serializable;

public enum Materials implements Serializable {
	
	Air(new Color(43, 161, 216)),
	Grass(new Color(64, 94, 19)),
	Dirt(new Color(81, 60, 10)),
	Stone(new Color(86, 87, 102));
	
	private Color color;
	
	private Materials(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
}
