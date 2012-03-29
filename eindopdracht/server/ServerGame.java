package eindopdracht.server;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.server.model.Set;
import eindopdracht.server.model.Turn;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Board;
import eindopdracht.util.ModelUtil;
import eindopdracht.util.Protocol;

public class ServerGame extends Observable {

	ArrayList<ServerPlayer> players;
	ServerPlayer settingPlayer; // Player die aan de beurt is
	Server server;

	Board board;

	/**
	 * Create a new game with the given players that will be managed by the server
	 * @param players that are in the game
	 * @require players are valid, at least 2 and max 4
	 */
	public ServerGame(ArrayList<ServerPlayer> players, Server server) {
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
	 * @ensure players will be notified
	 */
	public void start() {
		System.out.println("Game started with " + players.size() + " players!");

		this.giveSet();

		// Tell the players that the game is starting
		String msg = Protocol.START;
		for (ServerPlayer p : players) {
			msg = msg + " " + p.getName();
		}

		this.netBroadcast(msg);

		// TODO write this code
	}
	
	/**
	 * Called if the server receives chat from a player
	 * @param chat
	 * @param player
	 */
	public void chat(String chat, ServerPlayer player) {
		String fullChatString = Protocol.CHAT_SERVER + " [" + player.getName() + "] " + chat;
		System.out.println("Protocol says " + Protocol.CHAT_SERVER + " , sending " + fullChatString);
		this.netBroadcast(fullChatString);
	}

	/**
	 * Creates a set for the next player and broadcasts it
	 * @ensure next player will get the set
	 */
	public void giveSet() {
		//TODO implement checking for the number of tiles
		if (settingPlayer != null) {
			this.settingPlayer = getNextPlayer(settingPlayer);
		} else {
			this.settingPlayer = players.get(0);
		}
		Set set = new Set(settingPlayer);
		set.setExecuted(false);
		System.out.println("        Gave SET to " + settingPlayer.getName());
		this.localBroadcast(set);
	}

	/**
	 * Creates a turn for the next player and broadcasts it
	 * @ensure next player will get the turn
	 */
	public void giveTurn() {
		Turn turn = new Turn(settingPlayer);
		turn.setExecuted(false);
		System.out.println("        Gave TURN to " + settingPlayer.getName());
		this.localBroadcast(turn);
	}

	/**
	 * Tries to set. 
	 * @ensure false if invalid move
	 * @ensure true and will be performed if valid
	 * @param set to set
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
				this.localBroadcast(set);
				if (!this.gameEnded()) {
					this.giveTurn();
					this.netBroadcast(Protocol.SET_TILE + " "
							+ ModelUtil.intToLetter(set.getBlock()) + " "
							+ set.getTile() + " " + set.getPlayer().getName());
				}
				return true;
			}
		}
	}

	/**
	 * Tries to turn. 
	 * @ensure false if invalid move
	 * @ensure true and will be performed if valid
	 * @param turn to perform
	 */
	public boolean turn(Turn turn) {
		System.out.println(turn.toString() + " turned, player state was "
				+ turn.getPlayer().getState());
		if (turn.getPlayer().getState() != ServerPlayer.TURNING) {
			System.out.println("Invalid player state: "
					+ turn.getPlayer().getState());
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
				this.localBroadcast(turn);
				if (!this.gameEnded()) {
					this.giveSet();
					this.netBroadcast(Protocol.TURN_BLOK + " "
							+ ModelUtil.intToLetter(turn.getBlock()) + " "
							+ ModelUtil.intToDirection(turn.getRotation())
							+ " " + turn.getPlayer().getName());
				}
				return true;
			}
		}
	}

	/**
	 * Check if the game ended and if it did, end the game
	 * 
	 * @ensure true if the game ended
	 */
	public boolean gameEnded() {
		if (board.GameOver()) {
			System.out.println("GAME IS OVER");
			String gameOverString = new String(Protocol.END_GAME + " "
					+ Server.endDueToWinner);
			for (Integer playerColor : board.GetWinners()) {
				for (ServerPlayer player : players) {
					if (player.getColor() == playerColor) {
						gameOverString = gameOverString + " "
								+ player.getName();
					}
				}
			}
			System.out.println(gameOverString);
			this.netBroadcast(gameOverString);
			
			for (ServerPlayer p : players) {
				players.remove(p);
			}
			server.stopGame(this);
			return true;
		}
		return false;
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
				nextPlayer = players.get(i + 1);
			}
		}
		return nextPlayer;
	}
	
	/**
	 * Removes the players from the game and tells them the game ended
	 * @require player != null
	 * @ensure all hooked players will receive the message that this game is OVER
	 * @param player
	 */
	public void playerLeft(Player player) {
		//TODO implement
	}

	/**
	 * Quit the server. Tell all players that the game is over, and the reason
	 * why.
	 */
	public void endGame(ServerPlayer player, int reason) {
		System.out.println("Ending the game due to reason " + reason);
		this.netBroadcast(Protocol.END_GAME + " " + player.getName() + " "
				+ reason);
		for (ServerPlayer p : players) {
			players.remove(p);
		}
	}

	/**
	 * Checks if this lobby contains the given player
	 * 
	 * @require player != null
	 * @param player
	 * @ensure if game.contains(player) -> true, else false
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
	 * @param message to broadcast
	 * @ensure all hooked players will receive the message
	 * 
	 */
	public void netBroadcast(String message) {
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
	public void localBroadcast(Object object) {
		this.setChanged();
		this.notifyObservers(object);
	}
}
