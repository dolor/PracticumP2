package eindopdracht.client;

import java.util.ArrayList;
import java.util.Observable;

import eindopdracht.client.model.Set;
import eindopdracht.client.model.Turn;
import eindopdracht.client.model.player.Player;
import eindopdracht.model.Board;




public class Game extends Observable{
	
	ArrayList<Player> players;
	Board board;
	Player settingPlayer;
	Player localPlayer;
	
	/**
	 * Maakt een game aan.
	 * @param players De spelers die mee doen. Lijst staat op volgorde.
	 */
	public Game (ArrayList<Player> players)
	{
		System.out.println("Starting a new game!");
		this.players = players;
		
		// geef spelers hun kleur
		int color = 1;
		for (Player player : players)
		{
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
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Board getBoard() {
		System.out.println("Returning " + board.toString());
//		board.drawBoard();
		return this.board;
	}
	
	public void start() {
		for (Player player:players) {
			System.out.println("Starting with players " + player.getName());
		}
		
		this.settingPlayer = players.get(0); // eerste speler is aan de beurt
		System.out.println("Player " + settingPlayer.getName() + " got the turn");
		this.giveSet();
	}
	
	public void setLocalPlayer(Player player) {
		this.localPlayer = player;
	}
	
	public Player getLocalPlayer() {
		return this.localPlayer;
	}
	
	/**
	 * 
	 * @return an arraylist with all the Player objects representing the players currently in the game
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * maakt een set object aan en stuurt dat naar observers
	 */
	public void giveSet()
	{
		System.out.println("Gave the set");
		Set set = new Set(this.getSettingPlayer());
		this.getSettingPlayer().setState(Player.SETTING);
		
		this.localBroadcast(set);
		System.out.println("Broadcasted the set");
	}
	
	/** geeft een turn object naar de observers
	 *
	 */
	public void giveTurn()
	{
		System.out.println("Gave a turn");
		Turn turn = new Turn(this.getSettingPlayer());
		this.getSettingPlayer().setState(Player.TURNING);
		
		this.localBroadcast(turn);
		System.out.println("Broadcasted the turn");
	}
	
	public Player getSettingPlayer()
	{
		return this.settingPlayer;
	}
	
	public void setSettingPlayer(Player player)
	{
		System.out.println("Set the setting player");
		if (this.players.contains(player))
		{
			if (this.getSettingPlayer() != null)
				this.getSettingPlayer().setState(Player.IDLE);
			this.settingPlayer = player;
		}
	}
	
	public void nextSettingPlayer()
	{
		int index = players.indexOf(getSettingPlayer());
		
		if (index + 1 < players.size()) // als de volgende index binnen de range ligt
		{
			index++;
		}
		else // begin bij het begin.
		{
			index = 0;
		}
		
		this.getSettingPlayer().setState(Player.IDLE); // zet de player op idle
		
		this.setSettingPlayer(players.get(index));
		
		this.giveSet();
	}
	
	/**
	 * Determines which color would be player after the given color
	 * @param color current player
	 * @return next player
	 */
	public int nextPlayerForColor(int color) {
		for (Player player:players) {
			if (player.getColor() == color) {
				if (players.indexOf(player) == players.size()-1) {
					//was the last player
					return players.get(0).getColor();
				} else {
					return players.get(players.indexOf(player) + 1).getColor();
				}
			}
		}
		return players.get(0).getColor();
	}
	
	public void set(Set set)
	{
		if (set.getPlayer() == this.getSettingPlayer() && this.getSettingPlayer().getState() == Player.SETTING && !set.isExecuted()) // hij is aan de beurt
		{
			System.out.println("Checking if set should be performed");
			System.out.println("    Set: " + set.toString() + " tile: " + set.getTile());
			//System.out.printf("    Set: %o, %o, %o\n", set.getBlock(), set.getTile(), set.getPlayer().getColor());
			// de zet uitvoeren
			set.setValid(this.board.set(set.getBlock(), set.getTile(), set.getPlayer().getColor()));
			System.out.println("Now actually performing the set! was " + (set.getValid()?"":"NOT ") + "valid :" + set.toString());
			if (set.getValid())
			{
				set.setExecuted(true);
				
				this.localBroadcast(set); // vertel iedereen dat de zet is uitgevoerd
				
				// deel een nieuwe turn uit
				this.giveTurn();
				
			}
			else
			{
				this.localBroadcast(set);
			}
			
		}
	}
	
	public void localBroadcast(Object o)
	{
		System.out.println("Broadcasting " + o.toString());
		this.setChanged();
		this.notifyObservers(o);
	}
	
	public void turn(Turn turn)
	{
		if (turn.getPlayer() == this.getSettingPlayer() && this.getSettingPlayer().getState() == Player.TURNING && !turn.isExecuted()) // hij is aan de beurt
		{
			//TODO set verwerken
			turn.setValid(this.board.turn(turn.getBlock(), turn.getRotation()));
			if (turn.getValid())
			{
				System.out.println("DEBUG: Turn is invalid!");
				turn.setExecuted(true);
				
				this.localBroadcast(turn); // vertel iedereen dat de zet is uitgevoerd
				
				// nieuwe player is aan de beurt
				this.nextSettingPlayer();
			}
			else
			{
				this.localBroadcast(turn);
			}

		}
	}
	
}
