package eindopdracht.server;

import eindopdracht.server.model.Game;
import eindopdracht.server.model.Lobby;
import eindopdracht.server.network.PlayerHandler;

public class Player extends eindopdracht.model.player.Player{
	private String name;
    private int preferredNumberOfPlayers;
    private Lobby lobby;
    private Game game;
    private int color;
    private PlayerHandler handler;
    
    public Player(PlayerHandler handler) {
    	this.handler = handler;
    }

	public void processNetworkInput(String input) {
		System.out.println(input);
	}
	
	public void setNumberOfPlayers(int number) {
		this.preferredNumberOfPlayers = number;
	}
	
	public int preferredNumberOfPlayers() {
		return this.preferredNumberOfPlayers;
	}
	
	public void sendMessage(String message) {
		handler.sendMessage(message);
	}
	
	/*
	 * Handler methods; These methods are called by the network handler and the player will act like he's performing
	 * that specific task.
	 */
	
	/**
	 * Player wants to join a lobby
	 */
	public void join() {
		System.out.println("Now trying to join");
		//TODO write joining code
	}
	
	/**
	 * Player wants to set a tile
	 * @param block
	 * @param tile
	 */
	public void setTile(int block, int tile) {
		System.out.println("Trying to set a tile");
	}
	
	
	/**
	 * Players wants to rotate a block.
	 * @param block block to turn
	 * @param direction 1=CW, 2=CCW
	 */
	public void turnBlock(int block, int direction) {
		System.out.println("Trying to turn a block");
	}
	
	/**
	 * Called if the player wants to quit the server
	 */
	public void quit() {
		System.out.println("Trying to quit. Could not escape!");
		
	}
	
	/**
	 * Player is chatting
	 * @param message
	 */
	public void chat(String message) {
		System.out.println("Player chatting: " + message);
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}

	/**
	 * @param lobby the lobby to set
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
}
