package eindopdracht.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import eindopdracht.client.Game;
import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.client.model.player.NetworkPlayer;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Command;
import eindopdracht.util.NetworkUtil;
import eindopdracht.util.ModelUtil;
import eindopdracht.util.Protocol;

public class Network extends Observable implements Observer{
	
	private ConnectionHandler handler;
	private ArrayList<NetworkPlayer> networkPlayers;
	private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	private Game game;
	private String localPlayerName;
	
	/**
	 *
	 * @param players all players that are non-local players
	 */
	public void setNetworkPlayers(ArrayList<NetworkPlayer> players) {
		this.networkPlayers = players;
	}
	
	public void addNetworkPlayer(NetworkPlayer player) {
		if (this.networkPlayers == null) {
			networkPlayers = new ArrayList<NetworkPlayer>();
		}
		networkPlayers.add(player);
	}
	
	public void setLocalPlayerName(String name) {
		this.localPlayerName = name;
	}
	
	public String getLocalPlayerName() {
		return this.localPlayerName;
	}
	
	/**
	 * Takes a string of input from a connectionHandler and acts appropriately.
	 * Creates a Command object and passes it through notifyObservers.
	 */
	public void processNetworkInput(String input) {
		System.out.println("Processing input: " + input);
		Command command = new Command(input);
		if (command.getCommand().equals(Protocol.CHAT)) {
			System.out.println("[Chat] " + command.getArg(0));
		} 
		
		//Turn the block
		else if (command.getCommand().equals(Protocol.TURN_BLOK)) {
			String playerName = command.getArg(2);
			System.out.println("Player " + playerName + " turned a block");
			for (NetworkPlayer player:networkPlayers) {
				if (player.getName().equals(playerName)) {
					Turn turn = new Turn(player);
					turn.setBlock(ModelUtil.letterToInt(command.getArg(0)));
					turn.setRotation(ModelUtil.directionToInt(command.getArg(1)));
					
					player.performTurn(turn);
				}
			}
		} 
		
		//Set the tile
		else if (command.getCommand().equals(Protocol.SET_TILE)) {
			String playerName = command.getArg(2);
			System.out.println("Player " + playerName + " set a tile");
			for (NetworkPlayer player:networkPlayers) {
				if (player.getName().equals(playerName)) {
					Set set = new Set(player);
					set.setBlock(ModelUtil.letterToInt(command.getArg(0)));
					set.setTile(Integer.parseInt(command.getArg(1)));
					
					player.performSet(set);
				}
			}
		} 
		
		//Start a new game
		else if (command.getCommand().equals(Protocol.START)) {
			this.setChanged();
			this.notifyObservers(command);
		} 
		
		//Give the turn to the localplayer
		else if (command.getCommand().equals(Protocol.YOUR_TURN)) {
			game.setSettingPlayer(game.getLocalPlayer());
		} 
		
		//Connected to the server
		else if (command.getCommand().equals(Protocol.CONNECTED)) {
			this.setChanged();
			this.notifyObservers(command);
		}
		
		
	}
	
	/**
	 * Keeps reading input from the commandline and sends it directly to the server
	 */
	public void processTerminalInput() {
		boolean stop = false;
    	String userInput;
        while (!stop) {
        	System.out.print(">");
        	userInput = readString("");
			handler.sendString(userInput);
			if (userInput.equals("EXIT")) {
				stop = true;
			}
        }
	}
	
	/**
	 * Try to connect to a server
	 * @param server ip/hostname
	 * @param port port to connect on
	 * @return true if connected, false if not.
	 */
	public boolean connect(String server, int port) {
		if (NetworkUtil.isValidHost(server) && NetworkUtil.isValidPort(port)) {
			try {
				System.out.println("Creating socket, connecting to " + server + "(" + port + ")");
				Socket sock = new Socket(server, port);
				System.out.println("Creating handler");
				handler = new ConnectionHandler(sock, this);
				Thread handlerThread = new Thread(handler);
				handlerThread.start();
				return true;
			} catch (IOException e) {
				System.out.println("[Error] IOException while trying to open a connection: " + e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Just a test main method
	 * @param args
	 */
	public static void main(String[] args) {
		Network net = new Network();
		net.connect("localhost", 8888);
	}
	
	/** Leest een regel tekst van standaardinvoer. */
    static public String readString(String tekst) {
        System.out.print(tekst);
        String antw = null;
        try {
            BufferedReader in = 
                new BufferedReader(new InputStreamReader(System.in));            
            antw = in.readLine();
        } catch (IOException e) {
        }

        return (antw == null) ? "" : antw;
    }
	
	/**
	 * Sends a string as chat to a server, if connected
	 * @param chat
	 */
	public void sendChat(String chat) {
		if (handler != null) 
			handler.sendString(Protocol.CHAT + " " + chat);
		else
			System.out.println("[Error] not connected to a server!");
	}
	
	/**
	 * Asks for the name and preferred number of players, then joins
	 */
	public void join() {
		String name = readString("Name? > ");
		int size = Integer.parseInt(readString("Number of players? > "));
		this.join(name, size);
	}
	
	/**
	 * 
	 * @param name of this client
	 * @param size of the lobby you wish to join
	 */
	public void join(String name, int size) {
		if (handler != null)
			handler.sendString("join " + name + " " + size);
		else
			System.out.println("[Error] not connected to a server!");
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.getClass().equals(Set.class)) {
			Set set = (Set)arg;
			if(set.isExecuted() && set.getPlayer().isLocal())
				this.setTile(set.getBlock(), set.getTile());
		}
		
		else if (arg.getClass().equals(Turn.class)) {
			Turn turn = (Turn)arg;
			if (turn.isExecuted() && turn.getPlayer().isLocal())
				this.turnBlock(turn.getBlock(), turn.getRotation());
		}
	}
	
	/**
	 * Sets a tile for this player
	 * @param block 0-8
	 * @param tile 0-8
	 */
	public void setTile(int block, int tile) {
		String msg = String.format("set_tile "+letters[block]+" "+tile);
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
