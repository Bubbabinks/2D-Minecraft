package game;

import java.util.ArrayList;

import window.PhysicsCaller;
import window.WindowManager;
import world.World;
import world.WorldManager;

public class GameManager {
	
	public static final int FPS = 60;
	
	public static Render render;
	public static Collider collider;
	public static KeyManager keyManager;
	
	public static boolean isGamePaused = true;
	public static boolean gameStarted = false;
	private static boolean applicationIsClosing = false;
	private static ArrayList<PhysicsCaller> physicsCallers;
	
	public static void init() {
		physicsCallers = new ArrayList<PhysicsCaller>();
		World w = WorldManager.getWorld("Test World");
		if (w == null) {
			w = WorldManager.createWorld("Test World", new TerrainGenerator());
		}
		WorldManager.setCurrentWorld(w);
		render = new Render();
		collider = new Collider(render);
		WindowManager.gamePanel.requestFocus();
		keyManager = new KeyManager();
		WindowManager.gamePanel.addKeyListener(keyManager);
		w.details.player.init();
		Thread physicsUpdate = new Thread() {
			public void run() {
				while (!applicationIsClosing) {
					if (!isGamePaused) {
						for (var pc: physicsCallers) {
							pc.physicsUpdate();
						}
					}
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {}
				}
			}
		};
		physicsUpdate.start();
		
		Thread RenderingThread = new Thread() {
			public void run() {
				int timeWait = (int)(((double)FPS)/1000d);
				while (!applicationIsClosing) {
					if (!GameManager.isGamePaused) {
						WindowManager.gamePanel.repaint();
					}
					try {
						Thread.sleep(timeWait);
					} catch (InterruptedException e) {}
				}
			}
		};
		RenderingThread.start();
		
		Thread WaitForGameStart = new Thread() {
			public void run() {
				int timeWait = (int)(((double)FPS)/1000d);
				while (isGamePaused) {
					if (WorldManager.getCurrentWorld().regions.size() == 9) {
						isGamePaused = false;
						gameStarted = true;
						break;
					}
					try {
						Thread.sleep(timeWait*3);
					} catch (InterruptedException e) {}
				}
			}
		};
		WaitForGameStart.start();
	}
	
	public static void onApplicationClosing() {
		isGamePaused = true;
		applicationIsClosing = true;
	}
	
	public static void addPhysicsCaller(PhysicsCaller physicsCaller) {
		physicsCallers.add(physicsCaller);
	}
	
}
