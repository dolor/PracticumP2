package eindopdracht.server.model;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.server.Server;
import eindopdracht.server.model.Set;
import eindopdracht.server.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.util.ModelUtil;

public class ServerGame extends Observable {

	ArrayList<ServerPlayer> players;
	ServerPlayer settingPlayer; // Player die aan de beurt is

	Board board;

	public ServerGame(ArrayList<ServerPlayer> players) {
		this.players = players;
		this.board = new Board();

		for (ServerPlayer player : players) {
			player.setGame(this);
			this.addObserver(player);
			System.out
					.println("Added observer with class " + player.getClass());
		}

		for (int i = 0; i < players.size(); i++) {
			// Give all players a color
			players.get(i).setColor(i + 1);
		}
	}

	/**
	 * Start the game
	 */
	public void start() {
		System.out.println("Game started with " + players.size() + " players!");

		this.giveSet();

		// Tell the players that the game is starting
		String msg = "start";
		for (ServerPlayer p : players) {
			msg = msg + " " + p.getName();
		}

		this.broadcast(msg);

		// TODO write this code
	}

	/**
	 * Creates a set for the next player and broadcasts it
	 */
	public void giveSet() {
		if (settingPlayer != null) {
			this.settingPlayer = getNextPlayer(settingPlayer);
		} else {
			this.settingPlayer = players.get(0);
		}
		Set set = new Set(settingPlayer);
		set.setExecuted(false);
		System.out.println("        Gave SET to " + settingPlayer.getName());
		this.broadcast(set);
	}

	/**
	 * Creates a turn for the next player and broadcasts it
	 * 
	 */
	public void giveTurn() {
		Turn turn = new Turn(settingPlayer);
		turn.setExecuted(false);
		System.out.println("        Gave TURN to " + settingPlayer.getName());
		this.broadcast(turn);
	}

	/**
	 * Tries to set. If invalid move, returns FALSE and broadcasts to all
	 * clients.
	 * 
	 * @param turn
	 * @return
	 */
	public boolean set(Set set) {
		System.out.println(set.toString() + " set, player state was "
				+ set.getPlayer().getState());
		if (set.getPlayer().getState() != ServerPlayer.SETTING) {
			System.out
					.println("Ending game because a player set before it was his turn");
			this.endGame(set.getPlayer(), Server.endDueToCheat);
			return false;
		} else {
			if (!board.set(set.getBlock(), set.getTile(), set.getPlayer()
					.getColor())) {
				System.out
						.println("Ending game because an invalid set was made");
				this.endGame(set.getPlayer(), Server.endDueToCheat);
				return false;
			} else {
				set.setExecuted(true);
				this.broadcast(set);
				this.giveTurn();
				this.broadcast("set_tile "
						+ ModelUtil.intToLetter(set.getBlock()) + " "
						+ set.getTile() + " " + set.getPlayer().getName());
				return true;
			}
		}
	}

	/**
	 * Tries to turn. If invalid move, returns FALSE and broadcasts to all
	 * clients.
	 * 
	 * @param turn
	 * @return
	 */
	public boolean turn(Turn turn) {
		System.out.println(turn.toString() + " turned, player state was "
				+ turn.getPlayer().getState());
		if (turn.getPlayer().getState() != ServerPlayer.TURNING) {
			System.out.println("Invalid player state: " + turn.getPlayer().getState());
			this.endGame(turn.getPlayer(), Server.endDueToCheat);
			return false;
		} else {
			if (!board.turn(turn.getBlock(), turn.getRotation())) {
				System.out
				.println("Ending game because an invalid turn was made");
				this.endGame(turn.getPlayer(), Server.endDueToCheat);
				return false;
			} else {
				turn.setExecuted(true);
				this.broadcast(turn);
				this.giveSet();
				this.broadcast("turn_block "
						+ ModelUtil.intToLetter(turn.getBlock()) + " "
						+ ModelUtil.intToDirection(turn.getRotation()) + " "
						+ turn.getPlayer().getName());
				return true;
			}
		}
	}

	/**
	 * Returns the next player in the list
	 * 
	 * @param currentPlayer
	 * @return
	 */
	public ServerPlayer getNextPlayer(ServerPlayer currentPlayer) {
		ServerPlayer nextPlayer = players.get(0);
		// Loops through all but the last player, setting the next player in the
		// list
		// as the next player. If it doesn't find the current player, the
		// current
		// player was the last in the list.
		for (int i = 0; i < players.size() - 1; i++) {
			if (players.get(i).equals(currentPlayer)) {
				nextPlayer = players.get(i+1);
			}
		}
		return nextPlayer;
	}

	/**
	 * Quit the server. Tell all players that the game is over, and the reason
	 * why.
	 */
	public void endGame(ServerPlayer player, int reason) {
		System.out.println("Ending the game due to reason " + reason);
		this.broadcast("end_game " + player.getName() + " " + reason);
		for (ServerPlayer p : players) {
			players.remove(p);
		}
	}

	/**
	 * Checks if this lobby contains the given player
	 * 
	 * @param player
	 * @return
	 */
	public boolean containsPlayer(ServerPlayer player) {
		for (ServerPlayer p : players) {
			if (p.equals(player))
				return true;
		}
		return false;
	}

	/**
	 * Broadcast a message to all connected players
	 * 
	 * @param message
	 */
	public void broadcast(String message) {
		System.out.println("Broadcasting: " + message);
		for (ServerPlayer player : players)
			player.sendMessage(message);
	}

	/**
	 * Broadcast an object to all joined players through the observable
	 * interface
	 * 
	 * @param object
	 */
	public void broadcast(Object object) {
		this.setChanged();
		this.notifyObservers(object);
	}
}
