package game;

import world.Materials;

public class TerrainGenerator extends WorldGenerator {
	
	private static final long serialVersionUID = -4083714678488440487L;

	public Materials onBlockGenerated(int x, int y) {
		double p = perlinNoise(x);
		if (y < -20) {
			return Math.random()<.8d?Materials.Stone:Materials.Air;
		}
		if (y < -4+p) {
			return Materials.Stone;
		}
		if (y < -1+p) {
			return Materials.Dirt;
		}
		if (y < 0+p) {
			return Materials.Grass;
		}
		return Materials.Air;
	}
	
	private double perlinNoise(int x) {
		return ((Math.sin(50d*((double)x))+Math.sin(50d*Math.PI*((double)x)))*4d)+
				((Math.sin(30d*((double)x))+Math.sin(30d*Math.PI*((double)x))));
	}
	
	
	
}
