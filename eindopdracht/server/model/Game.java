package eindopdracht.server.model;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.model.Board;
import eindopdracht.model.Set;
import eindopdracht.model.Turn;
import eindopdracht.server.Player;
import eindopdracht.util.ModelUtil;

public class Game extends Observable {

	ArrayList<Player> players;
	Player settingPlayer; // Player die aan de beurt is

	Board board;

	private static int endDueToWinner = 1;
	private static int endDueToRemise = 2;
	private static int endDueToCheat = 3;
	private static int endDueToDisconnect = 4;

	public Game(ArrayList<Player> players) {
		this.players = players;
		this.board = new Board();

		for (Player player : players) {
			player.setGame(this);
		}
	}

	/**
	 * Start the game
	 */
	public void start() {
		System.out.println("Game started with " + players.size() + " players!");

		// Tell the players that the game is starting
		String msg = "start";
		for (Player p : players)
			msg = msg + " " + p.getName();

		this.broadcast(msg);

		// TODO write this code
	}

	/**
	 * Tries to set. If invalid move, returns FALSE and broadcasts to all clients.
	 * @param turn
	 * @return
	 */
	public boolean set(Set set) {
		if (!set.getPlayer().equals(settingPlayer) || !settingPlayer.getState() == Player.SETTING) {
			this.invalidTurn(set.getPlayer(), endDueToCheat);
			return false;
		} else {
			if (!board.Set(set.getBlock(), set.getTile(), set.getPlayer()
					.getColor())) {
				this.invalidTurn(set.getPlayer(), endDueToCheat);
				return false;
			} else {
				settingPlayer.setState(Player.TURNING);
				this.broadcast("set_tile "
						+ ModelUtil.intToLetter(set.getBlock()) + " "
						+ set.getTile() + " " + set.getPlayer().getName());
				return true;
			}
		}
	}

	/**
	 * Tries to turn. If invalid move, returns FALSE and broadcasts to all clients.
	 * @param turn
	 * @return
	 */
	public boolean turn(Turn turn) {
		if (turn.getPlayer().getState() == IDLE || !settingPlayer.getState() == Player.TURNING) {
			this.invalidTurn(turn.getPlayer(), endDueToCheat);
			return false;
		} else {
			if (!board.Turn(turn.getBlock(), turn.getRotation())) {
				this.invalidTurn(turn.getPlayer(), endDueToCheat);
				return false;
			} else {
				settingPlayer.setState(Player.IDLE);
				settingPlayer = getNextPlayer(settingPlayer);
				settingPlayer.setState(Player.SETTING);
				this.broadcast("turn_block "
						+ ModelUtil.intToLetter(turn.getBlock()) + " "
						+ ModelUtil.intToDirection(turn.getRotation()));
				return true;
			}
		}
	}
	
	/**
	 * Returns the next player in the list
	 * @param currentPlayer
	 * @return
	 */
	public Player getNextPlayer(Player currentPlayer) {
		Player nextPlayer = players.get(0);
		//Loops through all but the last player, setting the next player in the list
		//as the next player. If it doesn't find the current player, the current
		//player was the last in the list.
		for (int i=0; i<players.size() - 1; i++) {
			if(players.get(i).equals(currentPlayer))
				nextPlayer = players.get(i);
		}
		return nextPlayer;
	}

	public void invalidTurn(eindopdracht.model.Player player, int reason) {
		this.broadcast("end_game " + player.getName() + " " + reason);
	}

	public void broadcast(String message) {
		System.out.println("Broadcasting: " + message);
		for (Player player : players)
			player.sendMessage(message);
	}
}
