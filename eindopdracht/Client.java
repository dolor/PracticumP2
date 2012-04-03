package eindopdracht;

import java.awt.EventQueue;

import eindopdracht.client.MainController;
import eindopdracht.client.gui.PentagoXLWindow;

	// TODO: Bij het joinen wordt de spelerlijst niet weergegeven
	// TODO: Joinen als je al gejoint ben mag niet
	// TODO: Speler met dezelfde naam? Gaat dat goed
	// TODO: Balletjes, werkend maken, endgame en disconnect
	// TODO: Op een quit / end_game van de server moet de client er niet uitklappen
	// TODO: AI crasht als de server niet bestaat.
	// TODO: Geen lege chat versturen
public class Client {
	public static void main(String[] args) {
		new MainController();
	}
}
