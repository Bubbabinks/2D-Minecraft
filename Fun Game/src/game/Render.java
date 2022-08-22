package game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import window.DrawCaller;
import window.GamePanel;
import window.WindowManager;
import world.Materials;
import world.Region;
import world.World;
import world.WorldManager;

public class Render implements DrawCaller {
	
	public static final int blockSize = 50, inventorySlotSize = 55, spaceBetweenSlotGap = 10;
	
	private GamePanel gamePanel;
	public int xOffset = 0, yOffset = 1000;
	public int BSW = (WindowManager.WW/blockSize)+5, BSH = (WindowManager.WH/blockSize)+5,
			HSW = WindowManager.WW/2, HSH = WindowManager.WH/2,
			HB = blockSize/2, xB = HSW-HB, yB = HSH-HB;
	
	private ArrayList<Block> blocks;
	
	public Render() {
		blocks = new ArrayList<Block>();
		gamePanel = WindowManager.gamePanel;
		gamePanel.addDrawCaller(this);
		gamePanel.repaint();
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	
	public void onDraw(Graphics g) {
		if (!GameManager.gameStarted) {
			return;
		}
		int xOffset = this.xOffset;
		int yOffset = this.yOffset;
		checkCenterRegion();
		World w = WorldManager.getCurrentWorld();
		
		//Drawing Blocks
		ArrayList<Block> blocks = new ArrayList<Block>();
		for (int x = (xOffset/blockSize)-(BSW/2); x<(xOffset/blockSize)+(BSW/2); x++) {
			for (int y = (yOffset/blockSize)-(BSH/2); y<(yOffset/blockSize)+(BSH/2); y++) {
				blocks.add(new Block(x, y, w.getBlock(x, y).getColor()));
			}
		}
		this.blocks = blocks;
		for (var b: blocks) {
			g.setColor(b.color);
			g.fillRect(xB+(b.x*blockSize)-xOffset, yB+yOffset-(b.y*blockSize), blockSize, blockSize);
		}
		
		//Drawing Entities
		ArrayList<Entity> entities = w.details.entities;
		for (var e: entities) {
			g.setColor(e.color);
			g.fillRect(xB+e.x-this.xOffset, yB+this.yOffset-e.y, blockSize, blockSize);
		}
		
		//Drawing Player's Inventory
		Inventory inventory = w.details.player.inventory;
		int iGrayBlockSize = inventorySlotSize*inventory.getSize()+spaceBetweenSlotGap*(inventory.getSize()+1);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(HSW-iGrayBlockSize/2, 0, iGrayBlockSize, spaceBetweenSlotGap*2+inventorySlotSize);
		for (int i=0; i<inventory.getSize(); i++) {
			if (i == w.details.player.selectedSlot) {
				g.setColor(Color.LIGHT_GRAY);
			}else {
				g.setColor(Color.GRAY);
			}
			int x = HSW-iGrayBlockSize/2+spaceBetweenSlotGap+(i*(spaceBetweenSlotGap+inventorySlotSize));
			g.fillRect(x, spaceBetweenSlotGap, inventorySlotSize, inventorySlotSize);
			Materials m = inventory.getMaterial(i);
			if (m != Materials.Air) {
				g.setColor(m.getColor());
				g.fillRect(x + 5, spaceBetweenSlotGap+5, inventorySlotSize-10, inventorySlotSize-10);
				FontMetrics f = g.getFontMetrics();
				String s = inventory.getAmount(i)+"";
				g.setColor(Color.BLACK);
				g.drawString(s, x+inventorySlotSize/2-f.stringWidth(s)/2, spaceBetweenSlotGap+7+f.getHeight());
			}
		}
	}

	private void checkCenterRegion() {
		if (GameManager.gameStarted) {
			World w = WorldManager.getCurrentWorld();
			int x = Math.floorDiv(xOffset, blockSize*Region.size) * Region.size;
			int y = Math.floorDiv(yOffset, blockSize*Region.size) * Region.size;
			if (x != w.getCenterChunkX() || y != w.getCenterChunkY()) {
				w.moveCenterChunk(x, y);
			}
		}
	}
	
	
	
}
