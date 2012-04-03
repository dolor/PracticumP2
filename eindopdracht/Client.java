package eindopdracht;

import java.awt.EventQueue;

import eindopdracht.client.MainController;
import eindopdracht.client.gui.PentagoXLWindow;

public class Client{
	// TODO: Bij het joinen wordt de spelerlijst niet weergegeven
	// TODO: Joinen als je al gejoint ben mag niet
	// TODO: Speler met dezelfde naam? Gaat dat goed
	public static void main(String[] args) {
		new MainController();
	}
}
