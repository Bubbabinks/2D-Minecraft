package game;

import world.Materials;

public class RandomGenerator extends WorldGenerator {
	
	private static final long serialVersionUID = 9055876745186985123L;

	public Materials onBlockGenerated(int x, int y) {
		return ((Math.random())<.5d)?Materials.Air:Materials.Grass;
	}

}
