package game;

import java.io.Serializable;

import world.Materials;

public class Inventory implements Serializable {
	
	private static final int maxPerSlot = 99;
	
	private static final long serialVersionUID = 8187420434396805662L;
	private Materials[] slots;
	private int[] amounts;
	private int size;
	
	public Inventory(int size) {
		slots = new Materials[size];
		amounts = new int[size];
		for (int i = 0; i<size; i++) {
			slots[i] = Materials.Air;
			amounts[i] = 0;
		}
		this.size = size;
	}
	
	public Materials getMaterial(int slot) {
		return slots[slot];
	}
	
	public int getAmount(int slot) {
		return amounts[slot];
	}
	
	public int getSize() {
		return size;
	}
	
	public void setMaterial(int slot, Materials material) {
		slots[slot] = material;
	}
	
	public void setAmount(int slot, int amount) {
		amounts[slot] = amount;
	}
	
	public boolean addMaterial(Materials material) {
		boolean successful = false;
		for (int i=0; i<size; i++) {
			if (slots[i] == material && amounts[i] < maxPerSlot) {
				amounts[i]++;
				successful = true;
				break;
			}
		}
		if (!successful) {
			for (int i=0; i<size; i++) {
				if (slots[i] == Materials.Air) {
					slots[i] = material;
					amounts[i] = 1;
					successful = true;
					break;
				}
			}
		}
		return successful;
	}
	
	public boolean removeMaterial(int slot) {
		if (slots[slot] == Materials.Air) {
			return false;
		}
		amounts[slot]--;
		if (amounts[slot] == 0) {
			slots[slot] = Materials.Air;
		}
		return true;
	}

}
