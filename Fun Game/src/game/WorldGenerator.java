package game;

import java.io.Serializable;

import world.Materials;

public abstract class WorldGenerator implements Serializable {
	
	private static final long serialVersionUID = 1377947695798799055L;

	public abstract Materials onBlockGenerated(int x, int y);
	
}
