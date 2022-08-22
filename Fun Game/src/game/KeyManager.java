package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class KeyManager implements KeyListener {
	
	public HashSet<Integer> keysPressed;
	
	public KeyManager() {
		keysPressed = new HashSet<Integer>();
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		keysPressed.add(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		keysPressed.remove(e.getKeyCode());
	}
	
	
	
}
