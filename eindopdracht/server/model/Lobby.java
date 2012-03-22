package eindopdracht.server.model;

import java.util.ArrayList;

import eindopdracht.server.Player;
import eindopdracht.server.Server;

public class Lobby {
	int maxNumberOfPlayers;
	ArrayList<Player> players;
	Server server;
	
	public Lobby(int maxPlayers, Server server) {
		this.server = server;
		this.maxNumberOfPlayers = maxPlayers;
		this.players = new ArrayList<Player>();
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
	public boolean addPlayer(Player player) {
		if (players.size() < maxNumberOfPlayers)
			players.add(player);
		
		/*
		 * Repeats the loop until it finds a valid name
		 */
		int num = 0;
		boolean validName = false;
		while (!validName) {
			boolean numRaised = false;
			for (Player p:players) {
				if (p.name().equals(player.name()) && !p.equals(player)) {
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
			player.setName(player.name() + "_" + num);

		player.sendMessage("connected " + player.name());
				
		if (players.size() == maxNumberOfPlayers) {
			server.startGame(this);
		} else {
			String msg = "players";
			for (Player p:players) {
				if (!p.equals(player))
					msg = msg + " " + p.name();
			}
			player.sendMessage(msg);
		}

		return true;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * Checks if this lobby contains the given player
	 * @param player
	 * @return
	 */
	public boolean containsPlayer(Player player) {
		for (Player p:players) {
			if (p.equals(player))
				return true;
		}
		return false;
	}
	
	/**
	 * Removes the given player
	 * @param player
	 */
	public void removePlayer(Player player) {
		this.players.remove(player);
	}
	
	/**
	 * Broadcast the given message to all players in this lobby
	 * @param message
	 */
	public void broadcast(String message) {
		for (Player player:players) {
			player.sendMessage(message);
		}
	}
}
