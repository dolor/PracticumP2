package eindopdracht.server.network;

import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import eindopdracht.model.Command;
import eindopdracht.server.ServerController;
import eindopdracht.server.ServerPlayer;
import eindopdracht.util.ModelUtil;
import eindopdracht.util.PTLog;
import eindopdracht.util.Protocol;

public class PlayerHandler implements Runnable {
	private ServerPlayer player;
	private Socket socket; // Not currently used
	protected BufferedReader in;
	protected BufferedWriter out;
	private ServerController server;
	
	public String name; // Used for logging

	/**
	 * @param socket
	 *            Socket to listen to
	 * @param numberOfPlayers
	 *            Number of players this player wants to play with
	 * @throws IOException
	 *             if the socket's in and/or out can't be accessed
	 * @require socket != null, server != null
	 * @ensure creates a valid playerhandler that handles everything regarding
	 *         the player it is hooked up to across the network
	 */
	public PlayerHandler(Socket socket, ServerController server) throws IOException {
		this.name = "Handler_Empty";
		this.server = server;
		this.socket = socket;
		this.player = new ServerPlayer(this, server);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
	}

	@Override
	public void run() {
		this.listen();
	}

	/** Reads a line from the default input. */
	public String readString(String tekst) {
		System.out.print(tekst);
		String antw = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			antw = in.readLine();
		} catch (IOException e) {
		}

		return (antw == null) ? "" : antw;
	}

	/**
	 * Start listening for input from the socket
	 * 
	 * @ensure all input from the networkplayer will be dealt with
	 */
	public void listen() {
		try {
			String next;
			in.ready();
			next = in.readLine();
			while (next != null) {
				// If null, the connection was terminated
				this.handleInput(next);

				next = in.readLine();
			}
		} catch (IOException e) {
			PTLog.log(name, "Error occured while reading inputstream");
			server.removePlayer(player);
		} /*catch (NullPointerException e) {
			PTLog.log(name, "NullPointerException, quitting");
			System.exit(0);
		}*/
	}

	/**
	 * Handle the input from the network
	 * 
	 * @param input
	 *            string received from the network
	 * @require input != null
	 * @ensure converts the input to a command and handles appropriately, log
	 *         message if it is an invalid command
	 */
	private void handleInput(String input) {
		Command command = new Command(input);
		String c = command.getCommand();

		if (c.equals(Protocol.JOIN)) {
			player.setName(command.getArgs()[0]);
			name = "Handler_" + player.getName();
			player.setNumberOfPlayers(Integer.parseInt(command.getArgs()[1]));
			server.addPlayer(player);
		}

		else if (c.equals(Protocol.SET_TILE)) {
			int block = ModelUtil.letterToInt(command.getArg(0));
			int tile = Integer.parseInt(command.getArg(1));
			player.setTile(block, tile);
		}

		else if (c.equals(Protocol.TURN_BLOCK)) {
			int block = ModelUtil.letterToInt(command.getArg(0));
			int direction = ModelUtil.directionToInt(command.getArg(1));
			player.turnBlock(block, direction);
		}

		else if (c.equals(Protocol.QUIT)) {
			server.removePlayer(player);
			try {
				socket.close();
			} catch (IOException e) {
				PTLog.log(name, "Error thrown while quitting: "
						+ e.getMessage());
			}
		}

		else if (c.equals(Protocol.CHAT)) {
			player.chat(command.getArgString());
		}

		else if (c.equals(Protocol.CHALLENGE)) {
			// If a client tries to challenge, handle the denial immediately to
			// minimize disappointment. Poor guy.
			PTLog.log(name, "Player tried to challenge. Denying!");
			this.sendMessage(Protocol.NO_CHALLENGE);
			this.sendMessage(Protocol.QUIT_SERVER);
		}

		else {
			PTLog.log(name, "Unrecognized command received!");
		}
	}

	/**
	 * Sends a message over the network to the player on the other end.
	 * Automatically adds a newline to the message.
	 * 
	 * @param msg
	 */
	public void sendMessage(String msg) {
		try {
			out.write(msg + "\n");
			out.flush();
		} catch (IOException e) {
			PTLog.log(name, "-[Error] error thrown in PlayerHandler sendMessage");
			e.printStackTrace();
		}
	}

}
