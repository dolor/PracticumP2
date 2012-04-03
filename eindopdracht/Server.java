package eindopdracht;

import eindopdracht.util.PTLog;

public class Server {
	// TODO: quit sturen als de server afgesloten wordt met control-c
	// TODO: als een speler weg gaat zonder exit kan de server dit niet hebben.
	public static void main(String[] args) {
		if (args.length > 1) {
			int port = -1;
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				PTLog.log("Server", "Should either be used without an argument to start " +
								"with port 8888, or provide a valid number as a port.");
			}
			new eindopdracht.server.ServerController(port);
		} else {
			new eindopdracht.server.ServerController();
		}
	}
}
