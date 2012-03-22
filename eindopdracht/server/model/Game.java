package eindopdracht.server.model;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.model.Board;
import eindopdracht.model.Set;
import eindopdracht.model.Turn;
import eindopdracht.server.Player;

public class Game extends Observable {
	
	ArrayList<Player> players;
	Player settingPlayer; //Player die aan de beurt is
	
	Board board;
	
	private static int endDueToWinner = 1;
	private static int endDueToRemise = 2;
	private static int endDueToCheat = 3;
	private static int endDueToDisconnect = 4;
	
	public Game(ArrayList<Player> players) {
		this.players = players;
		this.board = new Board();
		
		for (Player player:players) {
			player.setGame(this);
		}
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
	
	public boolean set(Set set) {
		if (!set.getPlayer().equals(settingPlayer))
			this.invalidTurn(set.getPlayer(), endDueToCheat);
		else {
			if (!board.Set(set.getBlock(), set.getTile(), set.getPlayer().getColor())
					this.invalidTurn(set.getPlayer(), endDueToCheat);
		}
	}
	
	public boolean turn(Turn turn) {
		
	}
	
	public void invalidTurn(eindopdracht.model.Player player, int reason) {
		this.broadcast("end_game " + player.name() + " " + reason);
	}
	
	public void broadcast(String message) {
		for (Player player:players)
			player.sendMessage(message);
	}
}
