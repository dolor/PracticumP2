package eindopdracht.client;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Board;
import eindopdracht.util.PTLog;

public class GameController extends Observable {

	ArrayList<Player> players;
	Board board;
	Player settingPlayer;
	Player localPlayer;

	public static int endDueToWinner = 1;
	public static int endDueToRemise = 2;
	public static int endDueToCheat = 3;
	public static int endDueToDisconnect = 4;

	public static int maxTilesFor2Players = 40;
	public static int maxTilesForMorePlayers = 20;

	/**
	 * Maakt een game aan.
	 * 
	 * @param players
	 *            De spelers die mee doen. Lijst staat op volgorde.
	 */
	public GameController(ArrayList<Player> players) {
		PTLog.log("GameController", "Starting a new game!");
		this.players = players;

		// Give all players their tiles
		for (Player p : players) {
			if (players.size() == 2)
				p.setNumberOfTiles(maxTilesFor2Players);
			else
				p.setNumberOfTiles(maxTilesForMorePlayers);
		}

		// geef spelers hun kleur
		int color = 1;
		for (Player player : players) {
			// voeg als observer toe
			this.addObserver(player);

			// geef speler de game mee
			player.setGame(this);

			// geef kleur
			player.setColor(color);
			color++;
		}
		this.board = new Board();

		// this.start();
	}

	/**
	 * Set the board
	 * 
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * 
	 * @return this game's board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Starts the game
	 */
	public void start() {
		this.settingPlayer = players.get(0); // eerste speler is aan de
												// beurt
		PTLog.log("GameController", "Player " + settingPlayer.getName()
				+ " got the first turn");
		//if (!settingPlayer.equals(localPlayer))
		//	this.giveSet();
	}

	/**
	 * 
	 * @param player
	 *            the local player for this client
	 */
	public void setLocalPlayer(Player player) {
		this.localPlayer = player;
	}

	/**
	 * 
	 * @return the local player for this client
	 */
	public Player getLocalPlayer() {
		return this.localPlayer;
	}

	/**
	 * 
	 * @return an arraylist with all the Player objects representing the players
	 *         currently in the game
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	/**
	 * gives the localPlayer the set and notifies the observers.
	 * @ensure observers notified that the local player can set
	 */
	public void giveSet() {
		if (localPlayer.getNumberOfTiles() > 0) {
			//Else this player can't set anymore, and the server should end the game soon
			Set set = new Set(localPlayer);
			localPlayer.setState(Player.SETTING);
			this.localBroadcast(set);
		}
	}

	/**
	 * gives the localPlayer the turn and notifies the observers.
	 * @ensure observers notified that the local player can turn
	 */
	public void giveTurn() {
		Turn turn = new Turn(localPlayer);
		localPlayer.setState(Player.TURNING);

		this.localBroadcast(turn);
	}

	/**
	 * Broadcast the object to all observers
	 * 
	 * @param o
	 *            to broadcast
	 */
	public void localBroadcast(Object o) {
		this.setChanged();
		this.notifyObservers(o);
	}

	/**
	 * Perform the given set
	 * 
	 * @param set
	 *            to perform
	 */
	public void set(Set set) {
//		synchronized (this) {
			if (!set.isExecuted()) // hij is aan de beurt
			{
				// set.getTile(), set.getPlayer().getColor());
				// de zet uitvoeren
				set.setValid(this.board.set(set.getBlock(), set.getTile(), set
						.getPlayer().getColor()));
				if (set.getValid()) {
					set.setExecuted(true);
					set.getPlayer().setNumberOfTiles(
							set.getPlayer().getNumberOfTiles() - 1);

					this.localBroadcast(set); // vertel iedereen dat de zet is
												// uitgevoerd

					// deel een nieuwe turn uit
					if (!this.gameEnded() && set.getPlayer().equals(localPlayer))
						this.giveTurn();

				} else {
					PTLog.log("GameController", "Set was invalid!");
					this.localBroadcast(set);
				}
			}
//		}
	}

	/**
	 * Perform the given Turn
	 * 
	 * @param turn
	 *            to perform
	 */
	public void turn(Turn turn) {
		synchronized (this) {
			if (!turn.isExecuted()) // hij is aan de beurt
			{
				// TODO set verwerken
				turn.setValid(this.board.turn(turn.getBlock(),
						turn.getRotation()));
				if (turn.getValid()) {
					turn.setExecuted(true);

					this.localBroadcast(turn); // vertel iedereen dat de zet is
												// uitgevoerd

					// nieuwe player is aan de beurt
					//this.nextSettingPlayer();
				} else {
					PTLog.log("GameController", "DEBUG: Turn is invalid!");
					this.localBroadcast(turn);
				}
			}
		}
	}

	public boolean gameEnded() {
		if (board.GetWinners().size() > 0) {
			this.endGame(endDueToWinner);
			return true;
		} else
			return false;
	}

	/**
	 * Stop the game for the given reason
	 * 
	 * @param reason
	 */
	public void endGame(int reason) {

	}
}
