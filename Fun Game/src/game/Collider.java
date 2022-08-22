package game;

import java.util.ArrayList;

import world.Materials;

public class Collider {
	
	private int blockSize;
	private Render render;
	private int HB;
	
	public Collider(Render r) {
		render = r;
		blockSize = Render.blockSize;
		HB = blockSize/2;
	}
	
	/*
	 * directions are
	 * 0: up
	 * 1: right
	 * 2: down
	 * 3: left
	 */
	public int checkCollision(int x, int y, int direction) {
		ArrayList<Block> blocks = render.getBlocks();
		
		if (blocks.size() != 0) {
			for (var b: blocks) {
				if (!b.color.equals(Materials.Air.getColor())) {
					if (direction == 0) {
						if ((x+HB>(b.x*blockSize-HB) && (x-HB)<(b.x*blockSize+HB)) && (y+HB>(b.y*blockSize-HB)) && ((y-HB)<(b.y*blockSize+HB))) {
							return (y+HB)-((b.y*blockSize)-HB);
						}
					}else if (direction == 1) {
						if ((x+HB>(b.x*blockSize-HB) && (x-HB)<(b.x*blockSize+HB)) && (y+HB>(b.y*blockSize-HB)) && ((y-HB)<(b.y*blockSize+HB))) {
							return (x+HB)-((b.x*blockSize)-HB);
						}
					}else if (direction == 2) {
						if ((x+HB>(b.x*blockSize-HB) && (x-HB)<(b.x*blockSize+HB)) && (y+HB>(b.y*blockSize-HB)) && ((y-HB)<(b.y*blockSize+HB))) {
							return ((b.y*blockSize)+HB)-(y-HB);
						}
					}else if (direction == 3) {
						if ((x+HB>(b.x*blockSize-HB) && (x-HB)<(b.x*blockSize+HB)) && (y+HB>(b.y*blockSize-HB)) && ((y-HB)<(b.y*blockSize+HB))) {
							return ((b.x*blockSize)+HB)-(x-HB);
						}
					}
				}
			}
			return -1;
		}else {
			return 0;
		}
	}
	
}
