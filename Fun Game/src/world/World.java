package world;

import java.util.ArrayList;

import game.Entity;
import game.Player;
import game.WorldGenerator;
import main.FileManager;

public class World {
	
	public ArrayList<Region> regions; //Currently loaded regions
	public WorldDetails details;
	private int centerRegionX = 0;
	private int centerRegionY = 0;
	
	public World(String name, WorldGenerator generator) {
		regions = new ArrayList<Region>();
		details = new WorldDetails();
		details.entities = new ArrayList<Entity>();
		details.name = name;
		details.generator = generator;
		for (int x = -1; x<2; x++) {
			for (int y = -1; y<2; y++) {
				regions.add(generateRegion(x*Region.size, y*Region.size));
			}
		}
		save();
		regions.clear();
		Player p = new Player();
		details.entities.add(p);
		details.player = p;
	}
	
	public World(WorldDetails details) {
		regions = new ArrayList<Region>();
		this.details = details;
	}
	
	public void save() {
		FileManager.saveWorld(this);
		for (var r: regions) {
			FileManager.saveRegion(r, this);
		}
	}
	
	public void unload() {
		save();
		regions.clear();
	}
	
	public void unloadAllRegions() {
		for (var r: regions) {
			FileManager.saveRegion(r, this);
		}
		regions.clear();
	}
	
	public void moveCenterChunk(int x, int y) {
		unloadAllRegions();
		for (int a = -1; a<2; a++) {
			for (int b = -1; b<2; b++) {
				loadRegion(x+(Region.size*a), y+(Region.size*b));
			}
		}
		centerRegionX = Math.floorDiv(x, Region.size)*Region.size;
		centerRegionY = Math.floorDiv(y, Region.size)*Region.size;
		
	}
	
	public int getCenterChunkX() {
		return centerRegionX;
	}
	
	public int getCenterChunkY() {
		return centerRegionY;
	}
	
	public Region loadRegion(int x, int y) {
		Region region = null;
		int OX = Math.floorDiv(x, Region.size)*Region.size;
		int OY = Math.floorDiv(y, Region.size)*Region.size;
		region = FileManager.loadRegion(OX, OY, this);
		if (region != null) {
			regions.add(region);
		}else {
			region = generateRegion(OX, OY);
		}
		return region;
	}
	
	public void unloadRegion(int x, int y) {
		Region region = null;
		int OX = Math.floorDiv(x, Region.size)*Region.size;
		int OY = Math.floorDiv(y, Region.size)*Region.size;
		for (var r: regions) {
			if (r.x == OX && r.y == OY) {
				region = r;
				break;
			}
		}
		if (region != null) {
			unloadRegion(region);
		}
	}
	
	public void unloadRegion(Region r) {
		FileManager.saveRegion(r, this);
		regions.remove(r);
	}
	
	public Materials getBlock(int x, int y) {
		Materials block = null; 
		boolean hadToLoadRegion = false;
		Region region = null;
		int OX = Math.floorDiv(x, Region.size)*Region.size;
		int OY = Math.floorDiv(y, Region.size)*Region.size;
		for (var r: regions) {
			if (r.x == OX && r.y == OY) {
				region = r;
				break;
			}
		}
		if (region == null) {
			region = loadRegion(OX, OY);
			hadToLoadRegion = true;
			if (region == null) {
				region = generateRegion(OX, OY);
			}
		}
		block = region.getBlock(x, y);
		if (block == null) {
			generateChunk(x, y);
			block = region.getBlock(x, y);
		}
		
		if (hadToLoadRegion) {
			unloadRegion(region);
		}
		return block;
	}
	
	public void setBlock(int x, int y, Materials block) {
		boolean hadToLoadRegion = false;
		Region region = null;
		int OX = Math.floorDiv(x, Region.size)*Region.size;
		int OY = Math.floorDiv(y, Region.size)*Region.size;
		for (var r: regions) {
			if (r.x == OX && r.y == OY) {
				region = r;
				break;
			}
		}
		if (region == null) {
			region = loadRegion(OX, OY);
			hadToLoadRegion = true;
			if (region == null) {
				region = generateRegion(OX, OY);
			}
		}
		if (!region.setBlock(x, y, block)) {
			generateChunk(x, y);
			region.setBlock(x, y, block);
		}
		if (hadToLoadRegion) {
			unloadRegion(region);
		}
	}
	
	public Region generateRegion(int x, int y) {
		Region r = new Region(x, y);
		regions.add(r);
		return r;
	}
	
	public void generateChunk(int x, int y) {
		boolean hadToLoadRegion = false;
		Region region = null;
		int OX = Math.floorDiv(x, Region.size)*Region.size;
		int OY = Math.floorDiv(y, Region.size)*Region.size;
		for (var r: regions) {
			if (r.x == OX && r.y == OY) {
				region = r;
				break;
			}
		}
		if (region == null) {
			region = loadRegion(OX, OY);
			hadToLoadRegion = true;
			if (region == null) {
				region = generateRegion(OX, OY);
			}
		}
		region.generateChunk(x, y, details.generator);
		if (hadToLoadRegion) {
			unloadRegion(region);
		}
	}
	
}
