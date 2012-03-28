package eindopdracht;

import java.awt.EventQueue;

import eindopdracht.client.gui.PentagoXLWindow;

public class Client {
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
