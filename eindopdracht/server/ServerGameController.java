package eindopdracht.server;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.server.model.Set;
import eindopdracht.server.model.Turn;
import eindopdracht.model.Board;
import eindopdracht.util.ModelUtil;
import eindopdracht.util.PTLog;
import eindopdracht.util.Protocol;

public class ServerGameController extends Observable {

	public static int gameNumber = 1;

	ArrayList<ServerPlayer> players;
	ServerPlayer settingPlayer; // Player die aan de beurt is
	ServerController server;

	public String name; // Used for log files
	Board board;

	public static int maxTilesFor2Players = 40;
	public static int maxTilesForMorePlayers = 20;

	/**
	 * Create a new game with the given players that will be managed by the
	 * server
	 * 
	 * @param players
	 *            that are in the game
	 * @require players are valid, at least 2 and max 4
	 */
	public ServerGameController(ArrayList<ServerPlayer> players,
			ServerController server) {
		name = "Game_" + gameNumber;
		gameNumber++;

		this.players = players;
		this.board = new Board();
		this.server = server;

		for (ServerPlayer player : players) {
			player.setGame(this);
			this.addObserver(player);

			if (players.size() == 2)
				player.setNumberOfTiles(maxTilesFor2Players);
			else
				player.setNumberOfTiles(maxTilesForMorePlayers);
		}

		for (int i = 0; i < players.size(); i++) {
			// Give all players a color
			players.get(i).setColor(i + 1);
		}
	}

	/**
	 * Start the game
	 * 
	 * @ensure players will be notified
	 */
	public void start() {
		PTLog.log(name, "Game started with " + players.size() + " players!");

		// Tell the players that the game is starting
		String msg = Protocol.START;
		for (ServerPlayer p : players) {
			msg = msg + " " + p.getName();
		}

		this.netBroadcast(msg);

		this.giveSet();
	}

	/**
	 * Called if the server receives chat from a player
	 * 
	 * @param chat
	 * @param player
	 */
	public void chat(String chat, ServerPlayer player) {
		String fullChatString = Protocol.CHAT_SERVER + " [" + player.getName()
				+ "] " + chat;
		this.netBroadcast(fullChatString);
	}

	/**
	 * Creates a set for the next player and broadcasts it
	 * 
	 * @ensure next player will get the set
	 */
	public void giveSet() {
		if (settingPlayer != null) {
			this.settingPlayer = getNextPlayer(settingPlayer);
		} else {
			this.settingPlayer = players.get(0);
		}

		if (!gameEnded() && !outOfTiles()) {
			Set set = new Set(settingPlayer);
			set.setExecuted(false);
			this.localBroadcast(set);
		}
	}

	/**
	 * Creates a turn for the next player and broadcasts it
	 * 
	 * @ensure next player will get the turn
	 */
	public void giveTurn() {
		Turn turn = new Turn(settingPlayer);
		turn.setExecuted(false);
		this.localBroadcast(turn);
	}

	/**
	 * Tries to set.
	 * 
	 * @ensure false if invalid move
	 * @ensure true and will be performed if valid
	 * @param set
	 *            to set
	 */
	public boolean set(Set set) {
		if (set.getPlayer().getState() == ServerPlayer.IDLE) {
			PTLog.log("Game", "Ending game because " + set.getPlayer().getName() + " set before he could");
			this.endGame(set.getPlayer(), ServerController.endDueToCheat);
			return false;
		} else {
			if (!board.set(set.getBlock(), set.getTile(), set.getPlayer()
					.getColor())) {
				board.drawBoard();
				PTLog.log("ServerGame", "Invalid move made: " + set.toString());
				this.endGame(set.getPlayer(), ServerController.endDueToCheat);
				return false;
			} else {
				set.setExecuted(true);
				set.getPlayer().setNumberOfTiles(
						set.getPlayer().getNumberOfTiles() - 1);
				this.localBroadcast(set);

				//Eerst de zet van de ander broadcasten, dan giveSet, die er ook voor zorgt dat YOUR_TURN wordt verstuurd
				this.netBroadcast(Protocol.SET_TILE + " "
						+ ModelUtil.intToLetter(set.getBlock()) + " "
						+ set.getTile() + " " + set.getPlayer().getName());
				if (!this.gameEnded()) {
					this.giveTurn();
				}
				return true;
			}
		}
	}

	/**
	 * Tries to turn.
	 * 
	 * @ensure false if invalid move
	 * @ensure true and will be performed if valid
	 * @param turn
	 *            to perform
	 */
	public boolean turn(Turn turn) {
		if (turn.getPlayer().getState() == ServerPlayer.IDLE) {
			PTLog.log("Game", "Ending game because " + turn.getPlayer().getName() + " turned before he could");
			this.endGame(turn.getPlayer(), ServerController.endDueToCheat);
			return false;
		} else {
			if (!board.turn(turn.getBlock(), turn.getRotation())) {
				board.drawBoard();
				PTLog.log("ServerGame", "Invalid move made: " + turn.toString());
				this.endGame(turn.getPlayer(), ServerController.endDueToCheat);
				return false;
			} else {
				turn.setExecuted(true);
				this.localBroadcast(turn);

				//Eerst de zet van de ander broadcasten, dan giveTurn, die er ook voor zorgt dat YOUR_TURN wordt verstuurd
				this.netBroadcast(Protocol.TURN_BLOCK + " "
						+ ModelUtil.intToLetter(turn.getBlock()) + " "
						+ ModelUtil.intToDirection(turn.getRotation()) + " "
						+ turn.getPlayer().getName());
				if (!this.gameEnded()) {
					this.giveSet();
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
		if (board.gameOver()) {
			PTLog.log(name, "Game has winner");
			String gameOverString = new String(Protocol.END_GAME + " "
					+ ServerController.endDueToWinner);
			for (Integer playerColor : board.getWinners()) {
				for (ServerPlayer player : players) {
					if (player.getColor() == playerColor) {
						gameOverString = gameOverString + " "
								+ player.getName();
					}
				}
			}
			PTLog.log(name, gameOverString);
			this.netBroadcast(gameOverString);
			this.netBroadcast(Protocol.QUIT_SERVER);

			for (ServerPlayer p : players) {
				players.remove(p);
			}
			board.drawBoard();
			server.stopGame(this);
			return true;
		}
		return false;
	}

	public boolean outOfTiles() {
		if (settingPlayer.getNumberOfTiles() <= 0) {
			PTLog.log(name, "Game remised");
			String gameOverString = new String(Protocol.END_GAME + " "
					+ ServerController.endDueToRemise);
			PTLog.log(name, gameOverString);
			this.netBroadcast(gameOverString);

			players.clear();
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
	 * Quit the server. Tell all players that the game is over, and the reason
	 * why.
	 */
	public void endGame(ServerPlayer player, int reason) {
		String playerName = player.getName();
		if (reason == ServerController.endDueToCheat) {
			PTLog.log(name, "Ending game because " + playerName
					+ " set before it was his turn");
			System.exit(0);
		}
		else if (reason == ServerController.endDueToDisconnect) {
			PTLog.log(name, "Ending game because " + playerName
					+ " disconnected");
			players.remove(player);
		} else if (reason == ServerController.endDueToWinner)
			PTLog.log(name, "Ending game because " + playerName + " won!");
		else if (reason == ServerController.endDueToRemise)
			PTLog.log(name, "Ending game because the players are out of moves");

		this.netBroadcast(Protocol.END_GAME + " " + reason + " " + playerName);
		this.netBroadcast(Protocol.QUIT_SERVER);
		server.stopGame(this);
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
	 * @param message
	 *            to broadcast
	 * @ensure all hooked players will receive the message
	 * 
	 */
	public void netBroadcast(String message) {
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
