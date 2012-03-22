package eindopdracht;

public class Server {
	public static void main(String[] args) {
		if (args.length > 1) {
			int port = -1;
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out
						.println("Should either be used without an argument to start " +
								"with port 8888, or provide a valid number as a port.");
			}
			new eindopdracht.server.Server(port);
		} else {
			new eindopdracht.server.Server();
		}
	}
}
