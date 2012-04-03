package eindopdracht;

import java.awt.EventQueue;

import eindopdracht.client.MainController;
import eindopdracht.client.gui.PentagoXLWindow;

	// TODO: Op een quit / end_game van de server moet de client er niet uitklappen
	// TODO: AI crasht als de server niet bestaat.

	// TODO: Geen lege chat versturen
	// TODO: Exit knop werkt niet

public class Client {
	public static void main(String[] args) {
		new MainController();
	}
}
