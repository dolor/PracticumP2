package eindopdracht.server.model;

import java.util.ArrayList;

import eindopdracht.server.Player;

public class Game {
	
	ArrayList<Player> players;
	
	public Game(ArrayList<Player> players) {
		this.players = players;
	}
	
	/**
	 * Start the game
	 */
	public void start() {
		System.out.println("Game started with " + players.size() + " players!");
		
		//Tell the players that the game is starting
		String msg = "start";
		for (Player p:players)
			msg = msg + " " + p.name();

		this.broadcast(msg);
		
		//TODO write this code
	}
	
	public void broadcast(String message) {
		for (Player player:players)
			player.sendMessage(message);
	}
}
