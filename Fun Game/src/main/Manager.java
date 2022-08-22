package main;

import game.GameManager;
import window.WindowManager;
import world.WorldManager;

public class Manager {
	
	public static void main(String args[]) {
		WindowManager.init();
		WorldManager.init();
		WindowManager.switchToGamePanel();
		
		GameManager.init();
	}
	
	public static void onApplicationClosing() {
		WorldManager.saveAllWorlds();
		GameManager.onApplicationClosing();
		
	}
	
}
