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

	/**
	 * Maakt een game aan.
	 * 
	 * @param players
	 *            De spelers die mee doen. Lijst staat op volgorde.
	 */
	public GameController(ArrayList<Player> players) {
		PTLog.log("GameController", "Starting a new game!");
		this.players = players;
		
		//Give all players their tiles
		for (Player p:players) {
			if (players.size() == 2)
				p.setNumberOfTiles(40);
			else
				p.setNumberOfTiles(20);
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
		for (Player player : players) {
			PTLog.log("GameController", "Starting with players " + player.getName());
		}

		this.settingPlayer = players.get(0); // eerste speler is aan de beurt
		PTLog.log("GameController", "Player " + settingPlayer.getName()
				+ " got the first turn");
		this.giveSet();
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
	 * maakt een set object aan en stuurt dat naar observers
	 */
	public void giveSet() {
		if (this.getSettingPlayer().getNumberOfTiles() > 0) {
			Set set = new Set(this.getSettingPlayer());
			this.getSettingPlayer().setState(Player.SETTING);

			this.localBroadcast(set);
		} else {
			this.endGame(endDueToRemise);
		}
	}

	/**
	 * geeft een turn object naar de observers
	 */
	public void giveTurn() {
		Turn turn = new Turn(this.getSettingPlayer());
		this.getSettingPlayer().setState(Player.TURNING);
		
		this.localBroadcast(turn);
	}

	/**
	 * 
	 * @return the currently setting player
	 */
	public Player getSettingPlayer() {
		return this.settingPlayer;
	}

	/**
	 * 
	 * @param player
	 *            that is now the setting player
	 */
	public void setSettingPlayer(Player player) {
		if (this.players.contains(player)) {
			if (this.getSettingPlayer() != null)
				this.getSettingPlayer().setState(Player.IDLE);
			this.settingPlayer = player;
		}
	}

	/**
	 * Get the next setting player
	 * 
	 * @ensure player after the current settingPlayer in the player array is set
	 *         as setting
	 */
	public void nextSettingPlayer() {
		int index = players.indexOf(getSettingPlayer());

		if (index + 1 < players.size()) // als de volgende index binnen de range
										// ligt
		{
			index++;
		} else // begin bij het begin.
		{
			index = 0;
		}

		this.getSettingPlayer().setState(Player.IDLE); // zet de player op idle

		this.setSettingPlayer(players.get(index));

		if (!this.gameEnded())
			this.giveSet();
	}

	/**
	 * Perform the given set
	 * @param set to perform
	 */
	public void set(Set set) {
		if (set.getPlayer() == this.getSettingPlayer()
				&& this.getSettingPlayer().getState() == Player.SETTING
				&& !set.isExecuted()) // hij is aan de beurt
		{
			// set.getTile(), set.getPlayer().getColor());
			// de zet uitvoeren
			set.setValid(this.board.set(set.getBlock(), set.getTile(), set
					.getPlayer().getColor()));
			if (set.getValid()) {
				set.setExecuted(true);
				set.getPlayer().setNumberOfTiles(set.getPlayer().getNumberOfTiles() - 1);

				this.localBroadcast(set); // vertel iedereen dat de zet is
											// uitgevoerd

				// deel een nieuwe turn uit
				if (!this.gameEnded())
					this.giveTurn();

			} else {
				PTLog.log("GameController", "Set was invalid!");
				this.localBroadcast(set);
			}

		}
	}

	/**
	 * Broadcast the object to all observers
	 * @param o to broadcast
	 */
	public void localBroadcast(Object o) {
		this.setChanged();
		this.notifyObservers(o);
	}

	/**
	 * Perform the given Turn
	 * @param turn to perform
	 */
	public void turn(Turn turn) {
		if (turn.getPlayer() == this.getSettingPlayer()
				&& this.getSettingPlayer().getState() == Player.TURNING
				&& !turn.isExecuted()) // hij is aan de beurt
		{
			// TODO set verwerken
			turn.setValid(this.board.turn(turn.getBlock(), turn.getRotation()));
			if (turn.getValid()) {
				turn.setExecuted(true);

				this.localBroadcast(turn); // vertel iedereen dat de zet is
											// uitgevoerd

				// nieuwe player is aan de beurt
				this.nextSettingPlayer();
			} else {
				PTLog.log("GameController", "DEBUG: Turn is invalid!");
				this.localBroadcast(turn);
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
	 * @param reason
	 */
	public void endGame(int reason) {
		
	}
}
