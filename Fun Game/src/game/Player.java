package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import window.PhysicsCaller;
import window.WindowManager;
import world.Materials;
import world.WorldManager;

public class Player extends Entity implements PhysicsCaller {
	
	private static final long serialVersionUID = 5114665401352454251L;
	private transient KeyManager keyManager;
	private transient Render render;
	private transient Collider collider;
	
	public Inventory inventory;
	public int selectedSlot = 0;
	
	private int distanceFromEdge = 250;
	private int maxHeight;
	private int maxWidth;
	private int speed = 3;
	private int gravity = 2;
	
	private boolean onGround = false;
	private boolean isJumping = false;
	private int jumpForce = 6;
	private int maxJumpCycles = 40;
	private int jumpCycles = 0;
	
	public int pxOffset = 0;
	public int pyOffset = 1000;
	
	public Player() {
		super(0, 1000, Color.BLACK);
		inventory = new Inventory(9);
		pxOffset = 0;
		pyOffset = 1000;
	}
	
	public void init() {
		keyManager = GameManager.keyManager;
		render = GameManager.render;
		render.xOffset = pxOffset;
		render.yOffset = pyOffset;
		collider = GameManager.collider;
		maxHeight = (WindowManager.WH/2)-distanceFromEdge;
		maxWidth = (WindowManager.WW/2)-distanceFromEdge;
		GameManager.addPhysicsCaller(this);
		WindowManager.gamePanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (e.getButton() == 1) {
					onBreakBlock(e.getPoint());
				}else if (e.getButton() == 3) {
					onPlaceBlock(e.getPoint());
				}
			}
		});
		WindowManager.gamePanel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				int keyCode = e.getKeyCode();
				if (!GameManager.isGamePaused) {
					if (keyCode >= 49 && keyCode <= 57) {
						selectedSlot = keyCode-49;
					}
				}
				if (keyCode == KeyEvent.VK_ESCAPE) {
					GameManager.isGamePaused = !GameManager.isGamePaused;
				}
			}
		});
	}
	
	private void onBreakBlock(Point point) {
		if (!GameManager.isGamePaused) {
			int x = Math.floorDiv(point.x+render.xOffset-render.xB, Render.blockSize);
			int y = Math.floorDiv((-point.y)+render.yOffset+render.yB, Render.blockSize)+1;
			Materials material = WorldManager.getCurrentWorld().getBlock(x, y);
			if (material != Materials.Air) {
				if (inventory.addMaterial(material)) {
					WorldManager.getCurrentWorld().setBlock(x, y, Materials.Air);
				}
			}
		}
	}
	
	private void onPlaceBlock(Point point) {
		if (!GameManager.isGamePaused) {
			int x = Math.floorDiv(point.x+render.xOffset-render.xB, Render.blockSize);
			int y = Math.floorDiv((-point.y)+render.yOffset+render.yB, Render.blockSize)+1;
			if (WorldManager.getCurrentWorld().getBlock(x, y) == Materials.Air) {
				Materials s = inventory.getMaterial(selectedSlot);
				if (inventory.removeMaterial(selectedSlot)) {
					WorldManager.getCurrentWorld().setBlock(x, y, s);
				}
			}
		}
	}
	
	public void physicsUpdate() {
		pxOffset = render.xOffset;
		pyOffset = render.yOffset;
		//Gravity
		int c = collider.checkCollision(x, y-gravity, 2);
		if (c != -1) {
			onGround = true;
		}else {
			onGround = false;
		}
		if (c != 0) {
			int s = gravity;
			if (c != -1) {
				s = s-c;
			}
			if (y-render.yOffset < -maxHeight) {
				render.yOffset = y+maxHeight;
			}
			y -= s;
		}
		
		//Jumping
		if (isJumping) {
			int collisionResult = collider.checkCollision(x, y+jumpForce, 0);
			if (collisionResult != -1) {
				isJumping = false;
			}
			if (collisionResult != 0) {
				int s = jumpForce;
				if (collisionResult != -1) {
					s = s-collisionResult;
				}
				if (y-render.yOffset > maxHeight) {
					render.yOffset = y-maxHeight;
				}
				y += s;
			}
			if (jumpCycles > maxJumpCycles) {
				isJumping = false;
			}
			jumpCycles++;
		}
		
		
		//KeyInput And Movement
		if (keyManager.keysPressed.contains(KeyEvent.VK_W)) {
			if (!isJumping && onGround) {
				isJumping = true;
				jumpCycles = 0;
			}
			
		}
		/* moving down
		if (keyManager.keysPressed.contains(KeyEvent.VK_S)) {
			int collisionResult = collider.checkCollision(x, y-speed, 2);
			if (collisionResult != 0) {
				int s = speed;
				if (collisionResult != -1) {
					s = s-collisionResult;
				}
				if (y-render.yOffset < -maxHeight) {
					render.yOffset = y+maxHeight;
				}
				y -= s;
			}
		}
		*/
		if (keyManager.keysPressed.contains(KeyEvent.VK_D)) {
			int collisionResult = collider.checkCollision(x+speed, y, 1);
			if (collisionResult != 0) {
				int s = speed;
				if (collisionResult != -1) {
					s = s-collisionResult;
				}
				if (x-render.xOffset > maxWidth) {
					render.xOffset = x-maxWidth;
				}
				x += s;
			}
		}
		if (keyManager.keysPressed.contains(KeyEvent.VK_A)) {
			int collisionResult = collider.checkCollision(x-speed, y, 3);
			if (collisionResult != 0) {
				int s = speed;
				if (collisionResult != -1) {
					s = s-collisionResult;
				}
				if (x-render.xOffset < -maxWidth) {
					render.xOffset = x+maxWidth;
				}
				x -= s;
			}
		}
	}
	
}
