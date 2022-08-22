package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import world.Region;
import world.World;
import world.WorldDetails;
import world.WorldManager;

public class FileManager {
	
	private static final String HOME = System.getProperty("user.home") + "/Documents/Fun Game/";
	private static final File WORLDS = new File(HOME + "WORLDS");
	private static boolean hasInit = false;
	
	public static void init() {
		WORLDS.mkdirs();
	}
	
	public static void checkInit() {
		if (!hasInit) {
			init();
			hasInit = true;
		}
	}
	
	public static ArrayList<World> shortLoadAllWorlds() {
		ArrayList<World> worlds = new ArrayList<World>();
		for (var f: WORLDS.listFiles()) {
			File file = new File(f.getPath()+"/details.info");
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
				WorldDetails details = (WorldDetails)objectInputStream.readObject();
				objectInputStream.close();
				World world = new World(details);
				worlds.add(world);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {}
		}
		return worlds;
	}
	
	public static void saveCurrentWorld() {
		World w = WorldManager.getCurrentWorld();
		if (w != null) {
			String fileName = w.details.fileName;
			if (fileName == null) {
				fileName = generateFileName(WORLDS);
				w.details.fileName = fileName;
				File file = new File(WORLDS.getPath()+"/"+fileName+"/regions");
				file.mkdirs();
			}
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(WORLDS.getPath()+"/"+fileName+"/details.info")));
				objectOutputStream.writeObject(w.details);
				objectOutputStream.close();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {}
		}
	}
	
	public static void saveWorld(World world) {
		if (world != null) {
			String fileName = world.details.fileName;
			if (fileName == null) {
				fileName = generateFileName(WORLDS);
				world.details.fileName = fileName;
				File file = new File(WORLDS.getPath()+"/"+fileName+"/regions");
				file.mkdirs();
			}
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(WORLDS.getPath()+"/"+fileName+"/details.info")));
				objectOutputStream.writeObject(world.details);
				objectOutputStream.close();
			}catch (FileNotFoundException e) {
			}catch (IOException e) {}
		}
	}
	
	private static String generateFileName(File directory) {
		if (directory.isDirectory()) {
			List<String> fileNames = List.of(directory.list());
			int i = 0;
			while (fileNames.contains(i+"")) {
				i++;
			}
			return i+"";
		}
		return null;
	}
	
	public static void saveRegion(Region region) {
		World w = WorldManager.getCurrentWorld();
		if (w != null) {
			if (w.details.fileName == null) {
				saveCurrentWorld();
			}
			File file = new File(WORLDS.getPath()+"/"+w.details.fileName+"/regions/"+region.x+" "+region.y+".region");
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
				objectOutputStream.writeObject(region);
				objectOutputStream.close();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {}
		}
	}
	
	public static void saveRegion(Region region, World world) {
		if (world != null) {
			if (world.details.fileName == null) {
				saveWorld(world);
			}
			File file = new File(WORLDS.getPath()+"/"+world.details.fileName+"/regions/"+region.x+" "+region.y+".region");
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
				objectOutputStream.writeObject(region);
				objectOutputStream.close();
			} catch (FileNotFoundException e) {
			} catch (IOException e) {}
		}
	}
	
	public static Region loadRegion(int x, int y) {
		World w = WorldManager.getCurrentWorld();
		if (w != null) {
			File file = new File(WORLDS.getPath()+"/"+w.details.fileName+"/regions/"+x+" "+y+".region");
			if (file.exists()) {
				try {
					ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
					Region r = null;
					try {
						r = (Region)objectInputStream.readObject();
					} catch (ClassNotFoundException e) {}
					objectInputStream.close();
					return r;
				} catch (FileNotFoundException e) {
				} catch (IOException e) {}
			}
		}
		return null;
	}
	
	public static Region loadRegion(int x, int y, World world) {
		if (world != null) {
			File file = new File(WORLDS.getPath()+"/"+world.details.fileName+"/regions/"+x+" "+y+".region");
			if (file.exists()) {
				try {
					ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
					Region r = null;
					try {
						r = (Region)objectInputStream.readObject();
					} catch (ClassNotFoundException e) {}
					objectInputStream.close();
					return r;
				} catch (FileNotFoundException e) {
				} catch (IOException e) {}
			}
		}
		return null;
	}
}
