package eindopdracht;

import eindopdracht.client.MainController;
import eindopdracht.client.gui.PentagoXLWindow;

public class TestClient {
	
	private static String USAGE = "USAGE: String name, int players, boolean humanPlayer, int aiType\naiType: 0=random, 1=intelligent, 2=recursive";
	
	public static void main(String[] args) {
		String name = "";
		int players = 0;
		boolean humanPlayer = false;
		int aiType = 0;
		if (args.length != 4) {
			System.out.println(USAGE);
			System.exit(0);
		} else {
			name = args[0];
			humanPlayer = Boolean.parseBoolean(args[2]);
			try {
				players = Integer.parseInt(args[1]);
				aiType = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				System.out.println(USAGE);
				System.exit(0);
			}
		}
		MainController mc = new MainController();
		mc.connect("localhost", 8888);
		mc.join(name, players, humanPlayer, aiType);
	}
}
