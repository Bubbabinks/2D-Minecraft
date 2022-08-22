package game;

import world.Materials;

public class CheckerBoardGenerator extends WorldGenerator {
	
	private static final long serialVersionUID = 6823159039819108080L;
	private Materials even;
	private Materials odd;
	
	public CheckerBoardGenerator(Materials even, Materials odd) {
		this.even = even;
		this.odd = odd;
	}
	
	public Materials onBlockGenerated(int x, int y) {
		return ((x+y)%2 == 0 ? even : odd);
	}
	
	
	
}
