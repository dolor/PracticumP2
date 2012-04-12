package eindopdracht;

import eindopdracht.util.PTLog;

/**
 * Starts the server
 */
public class Server {

	public static void main(String[] args) {
		if (args.length >= 1) {
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
