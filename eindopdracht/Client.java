package eindopdracht;

import java.awt.EventQueue;

import eindopdracht.client.MainController;
import eindopdracht.client.gui.PentagoXLWindow;

	// TODO: AI crasht als de server niet bestaat.
	// TODO: Geen lege chat versturen
	// TODO: Exit knop werkt niet
	// TODO: Diepte van de RecursiveAI2 in de GUI instellen.

/**
 * Starts the client
 */
public class Client {
	public static void main(String[] args) {
		new MainController();
	}
}
