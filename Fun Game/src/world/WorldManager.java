package world;

import java.util.ArrayList;

import game.Render;
import game.WorldGenerator;
import main.FileManager;

public class WorldManager {
	
	private static ArrayList<World> worlds = new ArrayList<World>();
	
	private static World loadedWorld;
	
	public static void init() {
		worlds = FileManager.shortLoadAllWorlds();
	}
	
	public static World getCurrentWorld() {
		return loadedWorld;
	}
	
	public static void setCurrentWorld(World world) {
		if (loadedWorld != null) {
			loadedWorld.unload();
		}
		loadedWorld = world;
		int x = Math.floorDiv(loadedWorld.details.player.pxOffset/Render.blockSize, Region.size) * Region.size;
		int y = Math.floorDiv(loadedWorld.details.player.pyOffset/Render.blockSize, Region.size) * Region.size;
		loadedWorld.moveCenterChunk(x, y);
		loadedWorld.save();
	}
	
	public static World createWorld(String name, WorldGenerator generator) {
		World w = new World(name, generator);
		worlds.add(w);
		return w;
	}
	
	public static World getWorld(String name) {
		World world = null;
		for (var w: worlds) {
			if (w.details.name.equals(name)) {
				world = w;
				break;
			}
		}
		return world;
	}
	
	public static void saveAllWorlds() {
		for (var w: worlds) {
			w.save();
		}
	}
}
