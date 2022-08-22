package window;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 8061903513323468552L;
	private ArrayList<DrawCaller> drawers;
	private PauseMenu pauseMenu;
	
	public GamePanel() {
		setLayout(new GridBagLayout());
		drawers = new ArrayList<DrawCaller>();
		pauseMenu = new PauseMenu();
	}
	
	public void addDrawCaller(DrawCaller drawCaller) {
		drawers.add(drawCaller);
	}
	
	public void setPauseMenu(boolean enabled) {
		if (enabled) {
			GridBagConstraints gc = new GridBagConstraints();
			add(pauseMenu, gc);
			revalidate();
		}else {
			removeAll();
			revalidate();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (var d: drawers) {
			d.onDraw(g);
		}
	}
	
}
