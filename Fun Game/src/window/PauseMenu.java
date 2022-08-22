package window;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.GameManager;

public class PauseMenu extends JPanel {

	private static final long serialVersionUID = 8787446948642599978L;
	
	public PauseMenu() {
		setPreferredSize(new Dimension(300, 550));
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTH;
		gc.weighty = 0.5;
		gc.insets = new Insets(30, 0, 0, 10);
		JButton backToGameButton = new JButton("Back To Game");
		backToGameButton.setPreferredSize(new Dimension(225, 50));
		backToGameButton.addActionListener((ActionEvent e) -> {
			WindowManager.gamePanel.requestFocus();
			GameManager.togglePause();
		});
		add(backToGameButton, gc);
		
	}
	
}
