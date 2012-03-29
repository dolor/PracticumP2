package eindopdracht.client;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Board;

public class GameController extends Observable {

	ArrayList<Player> players;
	Board board;
	Player settingPlayer;
	Player localPlayer;

	/**
	 * Maakt een game aan.
	 * 
	 * @param players
	 *            De spelers die mee doen. Lijst staat op volgorde.
	 */
	public GameController(ArrayList<Player> players) {
		System.out.println("Starting a new game!");
		this.players = players;

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
		System.out.println("Returning " + board.toString());
		return this.board;
	}

	/**
	 * Starts the game
	 */
	public void start() {
		for (Player player : players) {
			System.out.println("Starting with players " + player.getName());
		}

		this.settingPlayer = players.get(0); // eerste speler is aan de beurt
		System.out.println("Player " + settingPlayer.getName()
				+ " got the turn");
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
		//TODO implement checking for the number of tiles
		System.out.println("Gave the set");
		Set set = new Set(this.getSettingPlayer());
		this.getSettingPlayer().setState(Player.SETTING);

		this.localBroadcast(set);
		System.out.println("Broadcasted the set");
	}

	/**
	 * geeft een turn object naar de observers
	 */
	public void giveTurn() {
		System.out.println("Gave a turn");
		Turn turn = new Turn(this.getSettingPlayer());
		this.getSettingPlayer().setState(Player.TURNING);

		this.localBroadcast(turn);
		System.out.println("Broadcasted the turn");
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
		System.out.println("Set the setting player");
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
			System.out.println("Checking if set should be performed");
			System.out.println("    Set: " + set.toString() + " tile: "
					+ set.getTile());
			// System.out.printf("    Set: %o, %o, %o\n", set.getBlock(),
			// set.getTile(), set.getPlayer().getColor());
			// de zet uitvoeren
			set.setValid(this.board.set(set.getBlock(), set.getTile(), set
					.getPlayer().getColor()));
			System.out.println("Now actually performing the set! was "
					+ (set.getValid() ? "" : "NOT ") + "valid :"
					+ set.toString());
			if (set.getValid()) {
				set.setExecuted(true);

				this.localBroadcast(set); // vertel iedereen dat de zet is
											// uitgevoerd

				// deel een nieuwe turn uit
				this.giveTurn();

			} else {
				this.localBroadcast(set);
			}

		}
	}

	/**
	 * Broadcast the object to all observers
	 * @param o to broadcast
	 */
	public void localBroadcast(Object o) {
		System.out.println("Broadcasting " + o.toString());
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
				System.out.println("DEBUG: Turn is invalid!");
				turn.setExecuted(true);

				this.localBroadcast(turn); // vertel iedereen dat de zet is
											// uitgevoerd

				// nieuwe player is aan de beurt
				this.nextSettingPlayer();
			} else {
				this.localBroadcast(turn);
			}

		}
	}

}
