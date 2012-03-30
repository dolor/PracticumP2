package eindopdracht;

import java.awt.EventQueue;

import eindopdracht.client.gui.PentagoXLWindow;

public class Client {
	// TODO: Balletjes, werkend maken, endgame en disconnect
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PentagoXLWindow frame = new PentagoXLWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
