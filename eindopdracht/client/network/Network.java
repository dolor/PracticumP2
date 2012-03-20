package eindopdracht.client.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import eindopdracht.client.model.Command;

public class Network extends Observable{
	
	private ConnectionHandler handler;
	private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	
	/**
	 * Takes a string of input from a connectionHandler and acts appropriately.
	 * Creates a Command object and passes it through notifyObservers.
	 */
	public void processNetworkInput(String input) {
		System.out.println(input);
		Scanner scanner = new Scanner(input); 
		
		if (!scanner.hasNext())
		{
			System.out.println("Empty command received!");
			System.exit(0);
		}
		String comm = scanner.next();
		String[] args = new String[0];
		int i = 0;
		//Add all the arguments
		while (scanner.hasNext()) {
			args = new String[args.length + 1];
			args[i] = scanner.next();
			i++;
		}
		
		Command command = new Command(comm, args);
		System.out.println(command.toString());
		this.setChanged();
		this.notifyObservers(command);
	}
	
	/**
	 * Try to connect to a server
	 * @param server ip/hostname
	 * @param port port to connect on
	 * @return true if connected, false if not.
	 */
	public boolean Connect(String server, int port) {
		if (isValidHost(server) && isValidPort(port)) {
			try {
				Socket sock = new Socket(server, port);
				handler = new ConnectionHandler(sock, this);
				return true;
			} catch (IOException e) {
				System.out.println("[Error] IOException while trying to open a connection");
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Sends a string as chat to a server, if connected
	 * @param chat
	 */
	public void SendChat(String chat) {
		if (handler != null) 
			handler.sendString("chat " + chat);
		else
			System.out.println("[Error] not connected to a server!");
	}
	
	/**
	 * 
	 * @param name of this client
	 * @param size of the lobby you wish to join
	 */
	public void Join(String name, int size) {
		if (handler != null)
			handler.sendString("join " + name + " " + size);
		else
			System.out.println("[Error] not connected to a server!");
	}
	
	/**
	 * Sets a tile for this player
	 * @param block 0-8
	 * @param tile 0-8
	 */
	public void setTile(int block, int tile) {
		String msg = String.format("set_tile %s %i", letters[block], tile);
		if (handler != null)
			handler.sendString(msg);
		else
			System.out.println("[Error] Not connected to a server!");
	}
	
	/**
	 * Turns the specified block
	 * @param block 0-8, block to be turned
	 * @param rotation 1=CW, 2=CCW
	 */
	public void turnBlock(int block, int rotation) {
		String msg = String.format("turn_block %s %s", letters[block], rotation==1?"CW":"CCW");
		if (handler != null)
			handler.sendString(msg);
		else
			System.out.println("[Error] Not connected to a server!");
	}
	
	/**
	 * Gracefully quits the connection with the server.
	 */
	public void quit() {
		if (handler != null) {
			handler.sendString("quit");
			handler = null;
		}
		else {
			System.out.println("[Error] Not connected to a server!");
		}
	}
	
	/**
	 * Whether or not the host is a valid hostname or IP address.
	 * @param host
	 *
	 */
	private boolean isValidHost (String host) {
		if (host.toLowerCase().equals("localhost")) {
        	try {
        		//If the local host is not a valid address, it will throw an Exception and thus not return true.
				InetAddress.getLocalHost();
				return true;
			} catch (UnknownHostException e) {return false;}
        } else {
        	String[] numStrings = host.split("\\.");
        	byte[] addressBytes = new byte[4];
        	//Loop through the bytes
        	if (numStrings.length == 4) {
        		for (int i = 0; i < 4; i++) {
        			if (Integer.parseInt(numStrings[i]) < 0 || Integer.parseInt(numStrings[i]) > 255)
                		return false;
        		}
        	} else {
        		return false;
        	}
        	return true;
        	//TODO support for IPV6?
        }
        
        
	}
	
	private boolean isValidPort(int port) {
		return (port >= 0 && port <= 65535);
	}
}
