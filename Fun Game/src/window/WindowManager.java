package window;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatDarculaLaf;

import main.Manager;

public class WindowManager {
	
	public static final int WW = 1000, WH = 750;
	
	private static JFrame frame;
	private static Panel mainPanel;
	
	public static GamePanel gamePanel;
	
	public static void init() {
		FlatDarculaLaf.setup();
		
		frame = new JFrame("Fun Game");
		mainPanel = new Panel();
		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				frame.dispose();
				Manager.onApplicationClosing();
			}
		});
		
		frame.setVisible(true);
	}
	
	public static void switchToGamePanel() {
		if (gamePanel == null) {
			gamePanel = new GamePanel();
		}
		mainPanel.setPanel(gamePanel);
	}
	
}
