package window;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 8061903513323468552L;
	private ArrayList<DrawCaller> drawers;
	
	public GamePanel() {
		drawers = new ArrayList<DrawCaller>();
	}
	
	public void addDrawCaller(DrawCaller drawCaller) {
		drawers.add(drawCaller);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (var d: drawers) {
			d.onDraw(g);
		}
	}
	
}
