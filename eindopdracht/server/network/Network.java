package eindopdracht.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Network {
	private ArrayList<PlayerHandler> players;
	
	public Network(int port) {
		this.listen(port);
		players = new ArrayList<PlayerHandler>();
	}
	
	/**
	 * Starts listening for connecting players
	 * @param port to listen to
	 */
	private void listen(int port) {
		ServerSocket socket = null;
		
	    try {
	    	socket = new ServerSocket(port);
	    } catch (IOException e) {
	    	System.out.println("Could not listen on port " + port + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	    
	    System.out.println("Port opened, awaiting connection...");

		boolean tryConnection = true;
	    while (tryConnection) {
	    	try {
				Socket sock = socket.accept();
	            PlayerHandler client = new PlayerHandler(sock);
			} catch (IOException e) {
				System.out.println("Connection on port " + port + " failed: " + e.getMessage());
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Sends the passed message to all connected clients
	 * @param msg
	 */
	public void broadcast(String msg) {
		System.out.println("Broadcasting: " + msg);
		for (PlayerHandler player:players) {
			player.sendMessage(msg);
		}
	}
}
