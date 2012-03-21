package eindopdracht.server.network;

import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import eindopdracht.model.Command;
import eindopdracht.server.Player;
import eindopdracht.server.Server;
import eindopdracht.util.ModelUtil;

public class PlayerHandler implements Runnable{
	private Player player;
	private Socket socket; //Not currently used
	protected BufferedReader in;
    protected BufferedWriter out;
    private Server server;
	

    /**
     * 
     * @param socket Socket to listen to
     * @param numberOfPlayers Number of players this player wants to play with
     * @throws IOException if the socket's in and out can't be accessed
     */
	public PlayerHandler(Socket socket, Server server) throws IOException {
		this.server = server;
		this.socket = socket;
		this.player = new Player(this);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		Thread handlerThread = new Thread(this);
		handlerThread.start();
	}

	@Override
	public void run() {
		this.listen();
	}
	
	public void listen() {
		try {
        	String next;
        	in.ready();
        	next = in.readLine();
        	while (next != null) {
        		//If null, the connection was terminated
        		this.handleInput(next);
        	}
        } catch (IOException e) {
			System.out.println("Error occured while reading inputstream");
		} catch (NullPointerException e) {
			//TODO Check if this is really necessary
			System.exit(0);
		}
	}
	
	private void handleInput(String input) {
		Command command = new Command(input);
		String c = command.getCommand();
		System.out.println("[PlayerHandler] command received: " + command.toString());
		
		if (c.equals("join")) {
			player.setName(command.getArgs()[0]);
			player.setNumberOfPlayers(Integer.parseInt(command.getArgs()[1]));
			server.addPlayer(player);
		} else if (c.equals("set_tile")) {
			int block = ModelUtil.letterToInt(command.getArg(0));
			int tile = Integer.parseInt(command.getArg(1));
			player.setTile(block, tile);
		} else if (c.equals("turn_block")) {
			int block = ModelUtil.letterToInt(command.getArg(0));
			int direction = ModelUtil.directionToInt(command.getArg(1));
			player.turnBlock(block, direction);
		} else if (c.equals("quit")) {
			player.quit();
		} else if (c.equals("chat")) {
			player.chat(command.getArg(0));
		} else if (c.equals("challenge")) {
			//TODO think of what to do when the player is challenged
			System.out.println("Player was challenged. Not doing anything!");
		} else {
			System.out.println("Unrecognized command received!");
		}
	}
	
	/**
	 * Sends a message over the network to the player on the other end. Automatically adds a
	 * newline to the message.
	 * @param msg
	 */
	public void sendMessage(String msg) {
		try {
			out.write(msg + "\n");
		} catch (IOException e) {
			System.out.println("[Error] error thrown in PlayerHandler sendMessage");
			e.printStackTrace();
		}
	}
	
	
}
