package eindopdracht.server;

import java.util.ArrayList;

import eindopdracht.util.PTLog;


public class Lobby {
	
	public static int lobbyNumber = 1;
	
	int maxNumberOfPlayers;
	ArrayList<ServerPlayer> players;
	ServerController server;
	public String name;
	
	/**
	 * Creates a new lobby
	 * @param maxPlayers the maximum amount of players before this lobby starts
	 * @param server the server this lobby runs on
	 */
	public Lobby(int maxPlayers, ServerController server) {
		name = "Lobby_" + lobbyNumber;
		lobbyNumber++;
		
		this.server = server;
		this.maxNumberOfPlayers = maxPlayers;
		this.players = new ArrayList<ServerPlayer>();
		PTLog.log(name, "Opened");
	}
	
	/**
	 * The amount of players that can join this lobby
	 * @return
	 */
	public int maxNumberOfPlayers() {
		return this.maxNumberOfPlayers;
	}
	
	/**
	 * Set the amount of players that can join this lobby
	 * @param players
	 */
	public void setMaxNumberOfPlayers(int players) {
		this.maxNumberOfPlayers = players;
	}
	
	/**
	 * Adds the player to the lobby. Will start a game automatically if 
	 * this player fills the lobby up.
	 */
	public boolean addPlayer(ServerPlayer player) {
		
		/*
		 * Repeats the loop until it finds a valid name
		 */
		int num = 0;
		boolean validName = false;
		while (!validName) {
			boolean numRaised = false;
			for (ServerPlayer p:players) {
				if ((num > 0 && p.getName().equals(player.getName() + "_" + num) || (num == 0 && p.getName().equals(player.getName())))) {
					PTLog.log(name, "Found a player with an identical name!");
					numRaised = true;
					num++;
				}
			}
			if (!numRaised)
				validName = true;
		}
		PTLog.log(name, "Num is now " + num);
		
		if (players.size() < maxNumberOfPlayers)
			players.add(player);
		
		/*
		 * Tells the player his current name
		 */
		if (num > 0) {
			PTLog.log(name, "Player got another name");
			player.setName(player.getName() + "_" + num);
		}

		PTLog.log(name, "Player now has name " + player.getName());
		PTLog.log(name, "connected " + player.getName());
		player.sendMessage("connected " + player.getName());
				
		String msg = "players";
		for (ServerPlayer p:players) {
			msg = msg + " " + p.getName();
		}
		
		PTLog.log(name, "Sending join message: " + msg);
		for (ServerPlayer p:players) 
			p.sendMessage(msg);
		
		if (players.size() == maxNumberOfPlayers) {
			server.startGame(this);
		}

		return true;
	}
	
	/**
	 * 
	 * @return the players in this lobby
	 */
	public ArrayList<ServerPlayer> getPlayers() {
		return this.players;
	}
	
	/**
	 * Checks if this lobby contains the given player
	 * @param player
	 * @ensure true if this lobby contains the player
	 * @ensure false if this server does not contain the player
	 */
	public boolean containsPlayer(ServerPlayer player) {
		for (ServerPlayer p:players) {
			PTLog.log(name, "comparing " + player.getName() + " with " + p.getName());
			if (p.equals(player))
				return true;
		}
		return false;
	}
	
	/**
	 * Removes the given player
	 * @param player
	 */
	public void removePlayer(ServerPlayer player) {
		this.players.remove(player);
	}
	
	/**
	 * Broadcast the given message to all players in this lobby
	 * @param message
	 */
	public void broadcast(String message) {
		for (ServerPlayer player:players) {
			player.sendMessage(message);
		}
	}
}
