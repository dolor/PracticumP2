package eindopdracht.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import eindopdracht.server.Server;

public class Network {
	private ArrayList<PlayerHandler> players;
	private Server server;
	
	public Network(int port, Server server) throws IOException{
		this.server = server;
		this.listen(port);
		players = new ArrayList<PlayerHandler>();
	}
	
	/**
	 * Starts listening for connecting players
	 * @param port to listen to
	 * @require valid port
	 * @ensure all incoming connections through the given port will be handled
	 * @throws IOException if the port could not be opened
	 */
	private void listen(int port) throws IOException{
		ServerSocket socket = null;
		
	    socket = new ServerSocket(port);
	    
	    System.out.println("Port " + port + " opened, awaiting connection...");

		boolean tryConnection = true;
	    while (tryConnection) {
	    	try {
				Socket sock = socket.accept();
	    		System.out.println("Found a connection!");
	            PlayerHandler client = new PlayerHandler(sock, server);
	            Thread handlerThread = new Thread(client);
	    		handlerThread.start();
			} catch (IOException e) {
				System.out.println("Connection on port " + port + " failed: " + e.getMessage());
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Sends the passed message to all connected clients
	 * @param msg message to send
	 * @ensure message will be sent to all connected network players
	 */
	public void broadcast(String msg) {
		for (PlayerHandler player:players) {
			player.sendMessage(msg);
		}
	}
}
