package world;

import java.io.Serializable;

public class Chunk implements Serializable {
	
	public static final int size = 5;
	private static final long serialVersionUID = -3460721699441825197L;
	public Materials[][] blocks;
	public int x;
	public int y;
	
	public Chunk(int x, int y) {
		this.x = x;
		this.y = y;
		blocks = new Materials[size][size];
		for (int a = 0; a<size; a++) {
			for (int b = 0; b<size; b++) {
				blocks[a][b] = Materials.Air;
			}
		}
		
	}
	
	public Materials getBlock(int x, int y) {
		int OX = x-this.x;
		int OY = y-this.y;
		if (OX > size-1 || OX < 0) {
			return null;
		}
		if (OY > size-1 || OY < 0) {
			return null;
		}
		return blocks[OX][OY];
	}
	
	public boolean setBlock(int x, int y, Materials block) {
		int OX = x-this.x;
		int OY = y-this.y;
		if (OX > size-1 || OX < 0) {
			return false;
		}
		if (OY > size-1 || OY < 0) {
			return false;
		}
		blocks[OX][OY] = block;
		return true;
	}
	
}
