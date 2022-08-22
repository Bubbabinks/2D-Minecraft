package window;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Panel() {
		setPreferredSize(new Dimension(WindowManager.WW, WindowManager.WH));
	}
	
	public void setPanel(JPanel panel) {
		removeAll();
		setLayout(new GridLayout(1,1));
		add(panel);
		panel.setPreferredSize(new Dimension(1000, 750));
		revalidate();
		repaint();
	}
	
}
