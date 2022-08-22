package world;

import java.io.Serializable;

import game.WorldGenerator;

public class Region implements Serializable {
	
	public static final int size = 50;
	private static int chunksPerRow = size-Chunk.size;
	private static final long serialVersionUID = -7098432150586861178L;
	public Chunk[][] chunks;
	public int x;
	public int y;
	
	public Region(int x, int y) {
		this.x = x;
		this.y = y;
		chunks = new Chunk[chunksPerRow][chunksPerRow];
	}
	
	public Materials getBlock(int x, int y) {
		int OX = x-this.x;
		int OY = y-this.y;
		if (OX/Chunk.size > chunksPerRow-1 || OX/Chunk.size < 0) {
			throw new IndexOutOfBoundsException(OX/Chunk.size);
		}
		if (OY/Chunk.size > chunksPerRow-1 || OY/Chunk.size < 0) {
			throw new IndexOutOfBoundsException(OY/Chunk.size);
		}
		OX = (OX/Chunk.size);
		OY = (OY/Chunk.size);
		if (chunks[OX][OY] == null) {
			return null;
		}
		return chunks[OX][OY].getBlock(x, y);
	}
	
	public boolean setBlock(int x, int y, Materials material) {
		int OX = x-this.x;
		int OY = y-this.y;
		if (OX/Chunk.size > chunksPerRow-1 || OX/Chunk.size < 0) {
			throw new IndexOutOfBoundsException(OX/Chunk.size);
		}
		if (OY/Chunk.size > chunksPerRow-1 || OY/Chunk.size < 0) {
			throw new IndexOutOfBoundsException(OY/Chunk.size);
		}
		OX = (OX/Chunk.size);
		OY = (OY/Chunk.size);
		if (chunks[OX][OY] == null) {
			return false;
		}
		return chunks[OX][OY].setBlock(x, y, material);
	}
	
	public void generateChunk(int x, int y, WorldGenerator gen) {
		int OX = x-this.x;
		int OY = y-this.y;
		if (OX/Chunk.size > chunksPerRow-1 || OX/Chunk.size < 0) {
			throw new IndexOutOfBoundsException(OX/Chunk.size);
		}
		if (OY/Chunk.size > chunksPerRow-1 || OX/Chunk.size < 0) {
			throw new IndexOutOfBoundsException(OY/Chunk.size);
		}
		int cx = Math.floorDiv(x, Chunk.size)*Chunk.size;
		int cy = Math.floorDiv(y, Chunk.size)*Chunk.size;
		Chunk chunk = new Chunk(cx, cy);
		for (int a = 0; a<Chunk.size; a++) {
			for (int b = 0; b<Chunk.size; b++) {
				chunk.blocks[a][b] = gen.onBlockGenerated(cx+a, cy+b);
			}
		}
		OX = (OX/Chunk.size);
		OY = (OY/Chunk.size);
		chunks[OX][OY] = chunk;
		
	}
	
}
