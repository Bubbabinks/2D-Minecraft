package game;

import world.Materials;

public class AirGenerator extends WorldGenerator {

	private static final long serialVersionUID = 9193108539934110108L;

	public Materials onBlockGenerated(int x, int y) {
		return Materials.Air;
	}
	
	
	
}
