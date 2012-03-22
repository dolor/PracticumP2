package eindopdracht.server.model;

import java.util.ArrayList;

import eindopdracht.server.Server;

public class Lobby {
	int maxNumberOfPlayers;
	ArrayList<ServerPlayer> players;
	Server server;
	
	public Lobby(int maxPlayers, Server server) {
		this.server = server;
		this.maxNumberOfPlayers = maxPlayers;
		this.players = new ArrayList<ServerPlayer>();
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
		if (players.size() < maxNumberOfPlayers)
			players.add(players.size(), player);
		
		/*
		 * Repeats the loop until it finds a valid name
		 */
		int num = 0;
		boolean validName = false;
		while (!validName) {
			boolean numRaised = false;
			for (ServerPlayer p:players) {
				if (p.getName().equals(player.getName()) && !p.equals(player)) {
					numRaised = true;
					num++;
				}
			}
			if (!numRaised)
				validName = true;
		}
		
		/*
		 * Tells the player his current name
		 */
		if (num > 0)
			player.setName(player.getName() + "_" + num);

		System.out.println("connected " + player.getName());
		player.sendMessage("connected " + player.getName());
				
		if (players.size() == maxNumberOfPlayers) {
			server.startGame(this);
		} else {
			String msg = "players";
			for (ServerPlayer p:players) {
				if (!p.equals(player))
					msg = msg + " " + p.getName();
			}
			player.sendMessage(msg);
		}

		return true;
	}
	
	public ArrayList<ServerPlayer> getPlayers() {
		return this.players;
	}
	
	/**
	 * Checks if this lobby contains the given player
	 * @param player
	 * @return
	 */
	public boolean containsPlayer(ServerPlayer player) {
		for (ServerPlayer p:players) {
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
