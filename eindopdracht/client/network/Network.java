package eindopdracht.client.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import eindopdracht.model.Command;
import eindopdracht.util.NetworkUtil;

public class Network extends Observable{
	
	private ConnectionHandler handler;
	private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	
	/**
	 * Takes a string of input from a connectionHandler and acts appropriately.
	 * Creates a Command object and passes it through notifyObservers.
	 */
	public void processNetworkInput(String input) {
		System.out.println(input);
		Command command = new Command(input);
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
		if (NetworkUtil.isValidHost(server) && NetworkUtil.isValidPort(port)) {
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
}
