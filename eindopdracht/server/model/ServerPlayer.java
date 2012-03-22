package eindopdracht.server.model;

import eindopdracht.server.network.PlayerHandler;

public class ServerPlayer{
	private String name;
    private int preferredNumberOfPlayers;
    private Lobby lobby;
    private ServerGame game;
	int state;
    private int color;
    private PlayerHandler handler;
	
	public static final int TURNING = 2;
	public static final int SETTING = 1;
	public static final int IDLE = 0;
    
    public ServerPlayer(PlayerHandler handler) {
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
	public ServerGame getGame() {
		return this.game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(ServerGame game) {
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public int getState()
	{
		return this.state;
	}
	public void setState(int state)
	{
		if (state >= 0 && state <= 2)
		{
			this.state = state;
		}
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}
}
