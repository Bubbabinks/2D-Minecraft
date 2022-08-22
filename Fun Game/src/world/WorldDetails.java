package world;

import java.io.Serializable;
import java.util.ArrayList;

import game.Entity;
import game.Player;
import game.WorldGenerator;

public class WorldDetails implements Serializable {
	
	private static final long serialVersionUID = 4098970369434138299L;
	public String name;
	public String fileName;
	public WorldGenerator generator;
	public ArrayList<Entity> entities;
	public Player player;
	
	public String toString() {
		return name + " " + fileName;
	}
	
}
